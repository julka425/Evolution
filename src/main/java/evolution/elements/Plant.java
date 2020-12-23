package evolution.elements;

import evolution.data.InitialData;
import evolution.move.Vector2d;

import java.util.Objects;

public class Plant extends AbstractMapElement {
    private InitialData data = new InitialData();
    private Vector2d position;
    private int energy;

    public Plant(Vector2d position) {
        this.position = position;
        this.energy = data.getPlantEnergy();
        this.canMove = false;
    }

    @Override
    public String toString() {
        return "*";
    }



    @Override
    public boolean canMove() {
        return false;
    }



    @Override
    public Vector2d getPosition() {
        return null;
    }

    @Override
    public int getEnergy() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return energy == plant.energy &&
                Objects.equals(data, plant.data) &&
                Objects.equals(position, plant.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, position, energy);
    }
}
