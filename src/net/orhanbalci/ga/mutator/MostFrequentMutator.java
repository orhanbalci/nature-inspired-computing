package net.orhanbalci.ga.mutator;

import net.orhanbalci.ga.chromosome.Chromosome;

import java.util.List;
import java.util.Random;

public class MostFrequentMutator implements IMutator {
    private double probabilityM;
    private Random rand;
    private int numReq;

    public MostFrequentMutator(Random r, double probabilityM, int numberOfFrequents) {
        this.probabilityM = probabilityM;
        rand = r;
        numReq = numberOfFrequents;
    }

    @Override
    public void applyMutation(Chromosome chrom) {
        List<Integer> mostFrequentGene = chrom.getMostFrequentGene(numReq);
        for (int i = 0; i < chrom.getLength(); i++) {
            double prob = rand.nextDouble();
            if (prob <= probabilityM) {
                chrom.setGene(i, mostFrequentGene.get(rand.nextInt(mostFrequentGene.size())));
            }
        }
    }
}