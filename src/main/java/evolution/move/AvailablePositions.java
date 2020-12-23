package evolution.move;

import evolution.elements.IWorldMapElement;
import evolution.maps.JungleMap;
import evolution.observers.IJunglePositionsObserver;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class AvailablePositions implements IJunglePositionsObserver {
    JungleMap map;
    HashSet<Vector2d> freeJunglePositions;
    Random generator = new Random();

    public AvailablePositions(JungleMap map) {
        this.map = map;
        setFreeJunglePositions();
    }

    public Vector2d getRandomAvailablePosition(HashSet<Vector2d> set) {
        if (set.size() == 0) return new Vector2d(-1,-1);
        int idx =  generator.nextInt(set.size());
        Iterator<Vector2d> iterator = set.iterator();
        for (int i = 0; i < idx; i++) {
            iterator.next();
        }
        return iterator.next();
    }

    public Vector2d getRandomJunglePosition() {
        return getRandomAvailablePosition(freeJunglePositions);
    }

    public void setFreeJunglePositions() {
        HashSet<Vector2d> positions = new HashSet<>();
        for (int i = map.getJungleBoundary().getLowerLeft().x; i < map.getJungleBoundary().getUpperRight().x + 1; i++) {
            for (int j = map.getJungleBoundary().getLowerLeft().y; j < map.getJungleBoundary().getUpperRight().y + 1; j++) {
                positions.add(new Vector2d(i,j));
            }
        }
        this.freeJunglePositions = positions;
    }

    @Override
    public void objectRemoved(IWorldMapElement element, Vector2d position) {
        if (!map.isOccupied(position)) {
            freeJunglePositions.add(position);
        }
    }

    @Override
    public void JunglePositionChanged(IWorldMapElement element, Vector2d oldPosition, Vector2d newPosition) {
       if (map.getJungleBoundary().insideBoundaries(newPosition) && freeJunglePositions.contains(newPosition))
           freeJunglePositions.remove(newPosition);
       if (!map.objectAt(oldPosition).isPresent()) {
           freeJunglePositions.add(oldPosition);
       }
    }

    @Override
    public void objectAdded(IWorldMapElement element, Vector2d position) {
        if (freeJunglePositions.contains(element.getPosition()))
            freeJunglePositions.remove(element.getPosition());

   }
}
