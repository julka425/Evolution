package evolution.simulation;

import evolution.data.InitialData;
import evolution.elements.Animal;
import evolution.elements.Genotype;

import evolution.maps.JungleMap;
import evolution.move.Vector2d;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation {
    private JungleMap map;
    private InitialData data = new InitialData();
    int day;

    public Simulation() {
        day = 0;
        this.map = new JungleMap(data.getWidth(), data.getHeight(), data.getJungleRatio());
        int numberOfAnimals = data.getInitialNumberOfAnimals();

        for (int i=0; i < numberOfAnimals; i++) {
            Animal animal = new Animal(map, map.getRandomAvailableAnimalPosition(), data.getStartEnergy(), new Genotype());
            animal.setBirthDay(day);
        }

        map.getFreeJunglePositions().setFreeJunglePositions();
    }

    public void simulate() {
        LinkedList<Animal> animals = map.getAnimals();
        HashMap<Vector2d, TreeSet<Animal>> animalHashMap = map.getAnimalHashMap();
        for (Animal animal: animals) {
            if (animal.isDead()) {
                animal.setDeathDay(day);
                map.removeAnimal(animal);
            }
            animal.move();
        }
        Iterator<Vector2d> itr = animalHashMap.keySet().iterator();

        while (itr.hasNext()) {
            TreeSet<Animal> animalsOnPosition = animalHashMap.get((Vector2d) itr);
            LinkedList<Animal> strongest = getStrongestAnimals(animalsOnPosition, (Vector2d) itr);
            for (Animal animal : strongest) {
                map.feedAnimal(animal);
            }
            if (animalsOnPosition.size() > 1) {
                if (strongest.size() > 1) {
                    int toReproduce1 = ThreadLocalRandom.current().nextInt(0, strongest.size());
                    int toReproduce2 = ThreadLocalRandom.current().nextInt(0, strongest.size());
                    if (toReproduce1 == toReproduce2) toReproduce2 = (toReproduce2 + 1) % strongest.size();
                    animals.get(toReproduce1).reproduce(animals.get(toReproduce2));
                }
                else {
                    Animal parent1 = animalsOnPosition.last();
                    animalsOnPosition.remove(parent1);
                    parent1.reproduce(animalsOnPosition.last());
                    animalsOnPosition.add(parent1);
                }
            }
        }
        map.growPlantInTheJungle();
        map.growPlantInTheSteppe();
        day++;
    }

    public LinkedList<Animal> getStrongestAnimals(TreeSet<Animal> animalsOnPosition, Vector2d position) {
        LinkedList<Animal> result = new LinkedList<>();
        int biggestEnergy = animalsOnPosition.last().getEnergy();
        Iterator<Animal> itr = animalsOnPosition.descendingIterator();
        while (itr.hasNext()) {
            Animal animal = itr.next();
            if (biggestEnergy == animal.getEnergy()) {
                result.add(animal);
            }
            else break;
        }
        return result;
    }

}

