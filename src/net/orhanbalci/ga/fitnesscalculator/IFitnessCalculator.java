package net.orhanbalci.ga.fitnesscalculator;


import net.orhanbalci.ga.chromosome.Chromosome;

//@FunctionalInterface
public interface IFitnessCalculator {
    double calculateFitness(Chromosome chrom);
}
