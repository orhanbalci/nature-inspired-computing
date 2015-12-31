package net.orhanbalci.ga.crossover;

import net.orhanbalci.ga.chromosome.Chromosome;

import java.util.Random;

public class OnePointCrossOver implements ICrossOver {
    private double probabilityC = 0.0;
    private Random r;

    public OnePointCrossOver(double probabilityC, Random r) {
        this.probabilityC = probabilityC;
        this.r = r;
    }

    @Override
    public void applyCrossOver(Chromosome firstParent, Chromosome secondParent) {
        assert firstParent.getLength() == secondParent.getLength();
        if (r.nextDouble() < probabilityC) {
            int crossOverPosition = r.nextInt((firstParent.getUpperBound() - firstParent.getLowerBound()) + 1) + firstParent.getLowerBound();
            for (int i = 0; i <= crossOverPosition; i++) {
                firstParent.setGene(i, secondParent.getGene(i));
            }
            for (int i = crossOverPosition + 1; i < firstParent.getLength(); i++) {
                secondParent.setGene(i, firstParent.getGene(i));
            }
        }
    }
}