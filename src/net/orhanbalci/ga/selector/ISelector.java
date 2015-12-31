package net.orhanbalci.ga.selector;

import net.orhanbalci.ga.chromosome.Chromosome;

import java.util.List;

public interface ISelector {
    void doSelection(List<Chromosome> population);
}