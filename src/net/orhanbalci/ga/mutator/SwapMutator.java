package net.orhanbalci.ga.mutator;

import net.orhanbalci.ga.chromosome.Chromosome;

import java.util.Random;

public class SwapMutator implements IMutator {
    private double probabilityM;
    private Random rand;

    public SwapMutator(Random r, double probabilityM) {
        this.probabilityM = probabilityM;
        rand = r;
    }

    @Override
    public void applyMutation(Chromosome chrom) {
        for (int i = 0; i < chrom.getLength(); i++) {
            double prob = rand.nextDouble();
            if (prob <= probabilityM) {
                int tempSwap = chrom.getGene(i);
                chrom.setGene(i, new Integer(chrom.getGene(chrom.getLength() - i - 1)));
                chrom.setGene(chrom.getLength() - i - 1, tempSwap);
            }
        }
    }
}