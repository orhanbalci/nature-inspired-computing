package net.orhanbalci.ga.crossover;

import net.orhanbalci.ga.chromosome.Chromosome;

import java.util.Random;

public class TwoPointCrossOver implements ICrossOver {
    private double probabilityC = 0.0;
    private Random r;

    public TwoPointCrossOver(double probabilityC, Random r) {
        this.probabilityC = probabilityC;
        this.r = r;
    }

    @Override
    public void applyCrossOver(Chromosome firstParent, Chromosome secondParent) {
        assert firstParent.getLength() == secondParent.getLength();
        if (r.nextDouble() < probabilityC) {
            int crossOverPosition1 = r.nextInt((firstParent.getUpperBound() - firstParent.getLowerBound()) + 1) + firstParent.getLowerBound();
            int crossOverPosition2 = r.nextInt((firstParent.getUpperBound() - firstParent.getLowerBound()) + 1) + firstParent.getLowerBound();

            for (int i = Math.min(crossOverPosition1, crossOverPosition2); i <= Math.max(crossOverPosition1, crossOverPosition2); i++) {
                Integer swapGene = firstParent.getGene(i);
                firstParent.setGene(i, secondParent.getGene(i));
                secondParent.setGene(i, swapGene);
            }
        }
    }
}