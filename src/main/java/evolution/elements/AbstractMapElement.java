package evolution.elements;

import evolution.move.Vector2d;
import evolution.observers.IJunglePositionPublisher;
import evolution.observers.IJunglePositionsObserver;
import evolution.observers.IPositionChangedObserver;



import java.util.LinkedList;

public abstract class AbstractMapElement implements IWorldMapElement, IJunglePositionPublisher, IJunglePositionsObserver {
        protected Vector2d position;
        protected int energy;
        protected boolean canMove;
        protected LinkedList<IPositionChangedObserver> observers = new LinkedList<>();
        protected LinkedList<IJunglePositionsObserver> jungleObservers = new LinkedList<>();

        @Override
        public abstract boolean canMove();

        @Override
        public Vector2d getPosition() {
            return position;
        }

        @Override
        public int getEnergy() {
            return energy;
        }



        @Override
        public void addJungleObserver(IJunglePositionsObserver observer) { jungleObservers.add(observer);};

        @Override
        public void removeJungleObserver(IJunglePositionsObserver observer) { jungleObservers.remove(observer);};


        @Override
        public void objectRemoved(IWorldMapElement element, Vector2d position) {
            for (IJunglePositionsObserver observer: jungleObservers) {
                observer.objectRemoved(element, position);
            }
        };

        @Override
        public void JunglePositionChanged(IWorldMapElement element, Vector2d oldPosition, Vector2d newPosition) {
            for (IJunglePositionsObserver observer: jungleObservers) {
                observer.JunglePositionChanged(element,oldPosition,newPosition);
            }
        };

        @Override
        public void objectAdded(IWorldMapElement element, Vector2d position) {
            for (IJunglePositionsObserver observer: jungleObservers) {
                observer.objectAdded(element, position);
            }
        };

       public boolean isBeingObserved(IWorldMapElement element) {
            return jungleObservers.contains(element);
        }

}
