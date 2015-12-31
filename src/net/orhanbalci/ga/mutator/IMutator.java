package net.orhanbalci.ga.mutator;

import net.orhanbalci.ga.chromosome.Chromosome;

//@FunctionalInterface
public interface IMutator {
    void applyMutation(Chromosome chrom);
}