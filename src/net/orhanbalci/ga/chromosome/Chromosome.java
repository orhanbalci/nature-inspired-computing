package net.orhanbalci.ga.chromosome;

import java.util.*;

public class Chromosome implements Iterable<Integer> {
    private List<Integer> genotype;
    private int length;
    private double fitnessValue = 0;
    private Integer lowerBound;
    private Integer upperBound;

    public Chromosome(int length, Integer lowerBound, Integer upperBound) {
        this.length = length;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        genotype = new ArrayList<>(Collections.nCopies(length, 0));
    }

    public Chromosome(Chromosome c) {
        this.length = c.length;
        this.lowerBound = c.lowerBound;
        this.upperBound = c.upperBound;
        this.fitnessValue = c.fitnessValue;
        this.genotype = new ArrayList<>(c.genotype);
    }

    @Override
    public Iterator<Integer> iterator() {
        return genotype.iterator();
    }

    public void setBounds(Integer lowerBound, Integer upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(double fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    public Integer getLowerBound() {
        return lowerBound;
    }

    public Integer getUpperBound() {
        return upperBound;
    }

    public void initRandom(Random r) {
        for (int i = 0; i < getLength(); i++) {
            int randomGene = r.nextInt((upperBound - lowerBound) + 1) + lowerBound;
            genotype.set(i, randomGene);
        }
    }

    public Set<Integer> getUniqueGenes() {
        return new HashSet<>(genotype);
    }

    public Integer getGene(int location) {
        assert location < length;
        return genotype.get(location);
    }

    public void setGene(int location, int geneValue) {
        assert location < length;
        genotype.set(location, geneValue);
    }

    public List<Integer> getMostFrequentGene(int num) {
        class Frequency {
            int gene;
            int frequency;
        }
        List<Frequency> frequencyLst = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        int[] frequency = new int[getLength()];
        for (Integer gene : genotype) {
            frequency[gene]++;
        }

        for (int i = 0; i < frequency.length; i++) {
            int i1 = frequency[i];
            Frequency frequency1 = new Frequency();
            frequency1.gene = i;
            frequency1.frequency = i1;
            frequencyLst.add(frequency1);
        }

        Collections.sort(frequencyLst, new Comparator<Frequency>() {
            @Override
            public int compare(Frequency o1, Frequency o2) {
                if (o1.frequency > o2.frequency)
                    return -1;
                else if (o1.frequency < o2.frequency)
                    return 1;
                else
                    return 0;
            }
        });

        for (int i = 0; i < Math.min(num, frequencyLst.size()); i++) {
            result.add(frequencyLst.get(i).gene);
        }

        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Chromosome result = new Chromosome(this.length, this.lowerBound, this.upperBound);
        for (int i = 0; i < genotype.size(); i++) {
            result.setGene(i, new Integer(genotype.get(i)));
        }
        return result;
    }
}