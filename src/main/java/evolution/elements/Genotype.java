package evolution.elements;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Genotype {
    private final static int genesCount = 32;
    private final static int movesCount = 8;;
    private ArrayList<Integer> genesList = new ArrayList<>();
    private int[] genesCountArray = new int [movesCount];
    Random generator = new Random();


    public Genotype() {
        //ArrayList<Integer> genotype = new ArrayList<>();
        for (int i=0; i< movesCount; i++) {
            genesList.add(i);
        }
        for (int i = movesCount; i < genesCount; i++) {
            int newGene = generator.nextInt(movesCount);
            genesList.add(newGene);}
        genesList.sort(Integer::compareTo);

        this.genesList = genesList;
        this.genesCountArray = getGenesArray(genesList);
    }


    public Genotype(Genotype mother, Genotype father) {
        int cut1 = ThreadLocalRandom.current().nextInt(1,genesCount-2);
        int cut2 = ThreadLocalRandom.current().nextInt(cut1+1,genesCount-1);
        int [] childGenesCountArray = new int [movesCount];
        for (int i=0;i<movesCount;i++) childGenesCountArray[i] = 0;

        for (int i = 0; i < cut1; i++) {
            childGenesCountArray[mother.genesList.get(i)]++;
        }

        for (int i = cut1; i< cut2; i++) {
            childGenesCountArray[father.genesList.get(i)]++;
        }

        for (int i = cut2; i< genesCount; i++) {
            childGenesCountArray[mother.genesList.get(i)]++;
        }

        genesList.sort(Integer::compareTo);
        for (int i=0; i<genesCount; i++) childGenesCountArray[genesList.get(i)]++;

        System.arraycopy(getGenesArray(genesList),0,childGenesCountArray,0,genesCount);

        for (int i=0; i<genesCount; i++)  {
            if (childGenesCountArray[i] == 0) {
                int mutationIndex = ThreadLocalRandom.current().nextInt(0,genesCount);
                while (childGenesCountArray[genesList.get(mutationIndex)] <= 1) {
                    mutationIndex = (mutationIndex+1) % genesCount;
                }

                childGenesCountArray[i]++;
                childGenesCountArray[genesList.get(mutationIndex)]--;
                genesList.set(mutationIndex, i);
            }
        }

        this.genesList = genesList;
        this.genesCountArray = childGenesCountArray;

    }

    public int[] getGenesArray (ArrayList<Integer> genotype) {
        int[] genesCountArray = new int [movesCount];
        for (int i=0; i < genesCount; i++) {
            genesCountArray[genotype.get(i)]++;
        }
        return genesCountArray;
    }

    public int getDirectionFromGenes() {
        return this.genesList.get(ThreadLocalRandom.current().nextInt(0,genesCount));
    }
}
