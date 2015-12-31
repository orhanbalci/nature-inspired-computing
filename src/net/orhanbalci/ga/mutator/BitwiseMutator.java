package net.orhanbalci.ga.mutator;

import net.orhanbalci.ga.chromosome.Chromosome;

import java.util.Random;

public class BitwiseMutator implements IMutator {
    private double probabilityM;
    private Random rand;

    public BitwiseMutator(Random r, double probabilityM) {
        this.probabilityM = probabilityM;
        rand = r;
    }

    @Override
    public void applyMutation(Chromosome chrom) {
        for (int i = 0; i < chrom.getLength(); i++) {
            double prob = rand.nextDouble();
            if (prob <= probabilityM) {
                chrom.setGene(i, rand.nextInt((chrom.getUpperBound() - chrom.getLowerBound()) + 1) + chrom.getLowerBound());
            }
        }
    }
}