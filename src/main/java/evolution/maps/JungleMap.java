package evolution.maps;


import evolution.data.InitialData;
import evolution.elements.AbstractMapElement;
import evolution.elements.Animal;
import evolution.elements.Plant;
import evolution.move.AvailablePositions;
import evolution.move.Vector2d;
import evolution.observers.IPositionChangedObserver;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class JungleMap implements IWorldMap, IPositionChangedObserver {
    private InitialData data = new InitialData();
    private int width;
    private int height;
    private MapBoundaries jungleBoundary;
    private MapBoundaries steppeBoundary;
    private AvailablePositions freeJunglePositions;;
    private  LinkedList<Animal> animals = new LinkedList<>();
    protected HashMap<Vector2d, TreeSet<Animal>> animalHashMap = new HashMap<>();
    protected HashMap<Vector2d, Plant> plantHashMap;
    Random generator = new Random();


    public JungleMap(int width, int height, double jungleRatio) {
        this.width = width;
        this.height = height;
        this.steppeBoundary = new MapBoundaries(height, width);
        this.jungleBoundary = new MapBoundaries(height, width, jungleRatio);
        this.freeJunglePositions = new AvailablePositions(this);
    }

    public boolean placeAlone(Animal animal) {
        if (!animalObjectAt(animal.getPosition()).isPresent()) {
            place(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean place(Animal animal) {
        Vector2d position = animal.getPosition();
        if(!animalHashMap.containsKey(position)) {
            animalHashMap.put(position, new TreeSet<>());
        }
        animalHashMap.get(position).add(animal);
        animals.add(animal);
        animal.addObserver(this);
        if (jungleBoundary.insideBoundaries(position)) animal.addJungleObserver(this.freeJunglePositions);
        return true;
    }

    public void growPlantInTheJungle() {
        Vector2d position = freeJunglePositions.getRandomJunglePosition();
        Plant plant = new Plant(position);
        if (position.x != -1) {
            growPlant(plant);
        }
    }

    public void growPlant(Plant plant) {
        plant.objectAdded(plant, plant.getPosition());
        plant.addJungleObserver(this.freeJunglePositions);
        plantHashMap.put(plant.getPosition(),plant);
    }

    public void growPlantInTheSteppe() {
        Vector2d position = getRandomAvailableAnimalPosition();
        Plant plant = new Plant(position);
        growPlant(plant);
    }


    @Override
    public Vector2d loopedPosition(int x, int y) {
        x = x % this.width;
        y = y % this.width;
        if (x < 0 || y < 0 ) {
            while (x < this.width) x += this.width;
            while (y < this.height) y += this.height;

            x = x % this.width;
            y = y % this.height;
            return new Vector2d(x,y);
        }

        return new Vector2d(x,y);

    }



    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        animalHashMap.get(oldPosition).remove(animal);
        if (animalHashMap.get(oldPosition).isEmpty()) {
            animalHashMap.remove(oldPosition);
        }
        if (!animalHashMap.containsKey(newPosition)) {
            animalHashMap.put(newPosition, new TreeSet<>());
        }
        animalHashMap.get(newPosition).add(animal);
    }

    public void removeElementHelp(AbstractMapElement element) {
        if (element.isBeingObserved(element) {
            element.objectRemoved(element, element.getPosition());
            element.removeJungleObserver(this.freeJunglePositions);
        }
    }

    public void removeAnimal(Animal animal) {
        Vector2d position = animal.getPosition();
        animals.remove(animal);
        animalHashMap.get(position).remove(animal);
        if (animalHashMap.get(position).isEmpty())
            animalHashMap.remove(position);
        animal.removeObserver(this);
        removeElementHelp(animal);
    }

    public void feedAnimal(Animal animal) {
        if (plantHashMap.containsKey(animal.getPosition())) {
            Plant plant = plantHashMap.get(animal.getPosition());
            animal.addEnergy(plant.getEnergy());
            removePlant(plant);
        }
    }


    public void removePlant(Plant plant) {
        Vector2d position = plant.getPosition();
        plantHashMap.remove(position);
        if (jungleBoundary.insideBoundaries(position)) {
            plant.objectRemoved(plant,position);
            plant.removeJungleObserver(this.freeJunglePositions);
        }
    }



    public Vector2d getRandomAvailableAnimalPosition() {
        Vector2d position = new Vector2d(0,0);
        while (!this.isOccupied(position)) {
            position = new Vector2d(ThreadLocalRandom.current().nextInt(0, this.getWidth()),
                    ThreadLocalRandom.current().nextInt(0, this.getHeight()));
        }
        return position;
    }


    public Optional<Object> animalObjectAt(Vector2d position) {
        if (animalHashMap.containsKey(position)) {
            return Optional.of(animalHashMap.get(position).last()); //returns strongest animal
        }
        return Optional.empty();
    }

    public Optional<Object> grassObjectAt(Vector2d position) {
        if (plantHashMap.containsKey(position))
            return Optional.of(plantHashMap.get(position));
        return Optional.empty();
    }

    @Override
    public Optional<Object> objectAt(Vector2d position) {
        if (animalObjectAt(position).isPresent())
            return animalObjectAt(position);
        return grassObjectAt(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position).isPresent();
    }

    public AvailablePositions getFreeJunglePositions() {
        return freeJunglePositions;
    }

    public MapBoundaries getSteppeBoundary() {
        return steppeBoundary;
    }

    public MapBoundaries getJungleBoundary() {
        return jungleBoundary;
    }

    public LinkedList<Animal> getAnimals() {
        return animals;
    }

    public HashMap<Vector2d, TreeSet<Animal>> getAnimalHashMap() {
        return animalHashMap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}