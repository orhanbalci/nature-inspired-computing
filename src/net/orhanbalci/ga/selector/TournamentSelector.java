package net.orhanbalci.ga.selector;

import net.orhanbalci.ga.chromosome.Chromosome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TournamentSelector implements ISelector {
    private Random r;
    private int tournementSize;

    public TournamentSelector(Random r, int tournementSize) {
        this.r = r;
        this.tournementSize = tournementSize;
    }

    @Override
    public void doSelection(List<Chromosome> population) {
        List<Chromosome> selection = new ArrayList<>(Collections.nCopies(population.size(), new Chromosome(0, 0, 0)));
        for (int i = 0; i < population.size(); i++) {
            try {
                selection.set(i, (Chromosome) selectChromosome(population).clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        population.clear();
        population.addAll(selection);
    }

    private Chromosome selectChromosome(List<Chromosome> population) {
        Chromosome best = null;
        Chromosome[] tournementChromosomes = new Chromosome[tournementSize];
        for (int i = 0; i < tournementChromosomes.length; i++) {
            int randomChromosome = r.nextInt(population.size());
            tournementChromosomes[i] = population.get(randomChromosome);
        }

        double bestFitness = Double.MAX_VALUE;
        for (int i = 0; i < tournementChromosomes.length; i++) {
            if (tournementChromosomes[i].getFitnessValue() <= bestFitness) {
                bestFitness = tournementChromosomes[i].getFitnessValue();
                best = tournementChromosomes[i];
            }
        }

        return best;
    }
}