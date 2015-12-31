package net.orhanbalci.ga.fitnesscalculator;

import net.orhanbalci.ga.chromosome.Chromosome;
import net.orhanbalci.ga.warehouse.WarehouseLocationProblem;

import java.util.Set;

public class WarehouseFitnessCalculator implements IFitnessCalculator {

    private WarehouseLocationProblem prob;

    public WarehouseFitnessCalculator(WarehouseLocationProblem problem) {
        prob = problem;
    }

    @Override
    public double calculateFitness(Chromosome chrom) {
        double fitness = 0.0;
        Set<Integer> uniqueWareHouses = chrom.getUniqueGenes();
        if (!checkCapacity(chrom, uniqueWareHouses)) {
            chrom.setFitnessValue(Double.MAX_VALUE);
            return Double.MAX_VALUE;
        }

        for (Integer uniqueWareHouse : uniqueWareHouses) {
            fitness += prob.warehouses[uniqueWareHouse].setupCost;
        }

        int geneCounter = 0;
        for (Integer gene : chrom) {
            fitness += prob.customers[geneCounter].travelCost.get(gene);
            geneCounter++;
        }
        chrom.setFitnessValue(fitness);
        return fitness;
    }

    private boolean checkCapacity(Chromosome chrom, Set<Integer> uniqueWareHouses) {

        for (Integer warehouse : uniqueWareHouses) {
            double warehouseDemand = 0.0;
            for (int i = 0; i < chrom.getLength(); i++) {
                if (chrom.getGene(i) == warehouse) {
                    warehouseDemand += prob.customers[i].demand;
                }
            }
            if (warehouseDemand > prob.warehouses[warehouse].capacity)
                return false;
        }

        return true;
    }
}

