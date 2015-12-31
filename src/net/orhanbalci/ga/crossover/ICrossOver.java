package net.orhanbalci.ga.crossover;

import net.orhanbalci.ga.chromosome.Chromosome;

//@FunctionalInterface
public interface ICrossOver {
    void applyCrossOver(Chromosome firstParent, Chromosome secondParent);
}