package evolution.elements;

import evolution.data.InitialData;

import evolution.maps.JungleMap;
import evolution.move.MapDirection;
import evolution.move.Vector2d;
import evolution.observers.IPositionChangedObserver;
import evolution.observers.IPositionChangedPublisher;

import java.util.*;


public class Animal extends AbstractMapElement implements IPositionChangedPublisher, IPositionChangedObserver {
    private MapDirection direction;
    private Genotype genotype;
    private HashSet<Animal> children = new HashSet<>();
    private JungleMap map;
    private final static int movesCount = 8;
    int birthday;
    int deathday = -1;
    InitialData data = InitialData.getData();
    Random generator = new Random();



    public Animal(JungleMap map, Vector2d initialPosition, int energy, Genotype genotype) {
        this.energy = InitialData.getData().getStartEnergy();
        this.direction = MapDirection.values()[generator.nextInt(movesCount)];
        this.position = initialPosition;
        this.canMove = true;
        if (!map.place(this))
            throw new IllegalArgumentException("couldn't place an animal on " + this.position);
        this.map = map;
        this.genotype = genotype;
    }




    public Vector2d getChildPosition() {
        Vector2d childPos;
        for (int x = position.x -1; x <= position.x+1; x++) {
            for (int y = position.y - 1; y <= position.y + 1; y++) {
                childPos = map.loopedPosition(x,y);
                if (!map.isOccupied(childPos)) {
                    return childPos;
                }
            }
        }
        return this.map.getRandomAvailableAnimalPosition();
    }


    public Optional<Animal> reproduce(Animal partner) {
        Animal child = null;
        if (this.energy > data.getStartEnergy() / 2 && partner.energy > data.getStartEnergy()) {
            int childEnergy = this.energy / 4 + partner.energy / 4;
            energy -= (energy / 4);
            partner.energy -= (partner.energy / 4);
            child = new Animal(this.map, getChildPosition(), childEnergy, new Genotype(this.genotype, partner.genotype));
            this.children.add(child);
            partner.children.add(child);
        }
        return Optional.ofNullable(child);
    }

    public MapDirection getDirection() {
        return direction;
    }

    public void move() {
        this.energy -= data.getMoveEnergy();
        this.direction = direction.changeDirection(genotype.getDirectionFromGenes());
        Vector2d oldPosition = this.position;
        this.position.add(this.direction.toUnitVector());
        if (map.getJungleBoundary().insideBoundaries(position));
        this.positionChanged(this, oldPosition, this.position);
    }


    public boolean isDead() {
        return (energy<0);
    }

    public void setBirthDay(int day) {
        this.birthday = birthday;
    }

    public void setDeathDay(int day) {
        this.deathday = day;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangedObserver observer: observers) {
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }

    @Override
    public void addObserver(IPositionChangedObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IPositionChangedObserver observer) {
        observers.remove(observer);
    }

    int compareTo(Animal o) {
        return o.energy - this.energy;
    }
}