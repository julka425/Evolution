package evolution.data;

import com.google.gson.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class InitialData {
    private static InitialData data = null;
    private int width;
    private int height;
    private int startEnergy;
    private int moveEnergy;
    private int plantEnergy;
    private double jungleRatio;
    private int initialNumberOfAnimals;


    public static InitialData getData(){
        if (data == null) {
            Gson gson = new Gson();

            try (Reader reader = new FileReader("parameters.json")){
                data = gson.fromJson(reader, InitialData.class);
            }
            catch (IOException e) {
                data = new InitialData();
                e.printStackTrace();
            }
        }
        return data;
    }




    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public double getJungleRatio() {
        return jungleRatio;
    }

    public int getInitialNumberOfAnimals() {
        return initialNumberOfAnimals;
    }


}

