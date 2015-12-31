package net.orhanbalci.ga.mutator;

import net.orhanbalci.ga.chromosome.Chromosome;
import net.orhanbalci.ga.warehouse.WarehouseLocationProblem;

import java.util.Random;

public class TravelCostMutator implements IMutator {
    private double probabilityM;
    private Random rand;
    private WarehouseLocationProblem porblem;

    public TravelCostMutator(Random r, double probabilityM, WarehouseLocationProblem problem) {
        this.probabilityM = probabilityM;
        rand = r;
        this.porblem = problem;
    }

    @Override
    public void applyMutation(Chromosome chrom) {
        for (int i = 0; i < chrom.getLength(); i++) {
            double prob = rand.nextDouble();
            if (prob <= probabilityM) {
                chrom.setGene(i, porblem.customers[i].getMostFavorableWarehouse());
            }
        }
    }
}