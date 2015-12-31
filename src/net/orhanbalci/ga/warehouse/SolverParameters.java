package net.orhanbalci.ga.warehouse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SolverParameters {
    public String problemName;
    public long randomSeed;
    public int populationCount;
    public int generationCount;
    public String crossoverType;
    public double crossoverProb;
    public String mutatorType;
    public double mutatorProb;
    public int frequentGeneCount;
    public int mutatorSide;
    public String selectorType;
    public int tournementSize;
    public boolean elitism;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Problem name : %s\n", problemName));
        sb.append(String.format("Random Seed : %d\n", randomSeed));
        sb.append(String.format("Population Count : %d\n", populationCount));
        sb.append(String.format("Generation Count : %d\n", generationCount));
        sb.append(String.format("Crossover Type : %s\n", crossoverType));
        sb.append(String.format("Crossover Prob : %s\n", crossoverProb));
        sb.append(String.format("Mutator Type : %s\n", mutatorType));
        sb.append(String.format("Mutator Prob : %s\n", mutatorProb));
        sb.append(String.format("Mutator Side : %d\n", mutatorSide));
        sb.append(String.format("Frequent Gene Count : %d\n", frequentGeneCount));
        sb.append(String.format("Selector Type : %s\n", selectorType));
        sb.append(String.format("Tournement Size : %d\n", tournementSize));
        sb.append(String.format("Elitism : %s", elitism));
        return sb.toString();
    }

    public void writeToFile() throws IOException {

        File file = new File("Parameters.txt");

        // if file doesnt exists, then create it
        if (!file.exists()) {
            if (!file.createNewFile()) {
                System.out.println("Can not create file");
            }
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(this.toString());
        bw.close();

    }
}
