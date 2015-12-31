package net.orhanbalci.ga.mutator;

import net.orhanbalci.ga.chromosome.Chromosome;

import java.util.Random;

public class SimilarityMutator implements IMutator {

    private double probabilityM;
    private Random rand;
    private int side;

    public SimilarityMutator(Random r, double probabilityM, int side) {
        this.probabilityM = probabilityM;
        rand = r;
        this.side = side;
    }

    @Override
    public void applyMutation(Chromosome chrom) {
        for (int i = 0; i < chrom.getLength(); i++) {
            double prob = rand.nextDouble();
            if (prob <= probabilityM) {
                if (i < chrom.getLength() - 1)
                    chrom.setGene(i + 1, new Integer(chrom.getGene(i)));


                if (i > 0 && side == 2)
                    chrom.setGene(i - 1, new Integer(chrom.getGene(i)));
            }
        }
    }
}
