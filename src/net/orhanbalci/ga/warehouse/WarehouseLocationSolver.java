package net.orhanbalci.ga.warehouse;

import net.orhanbalci.ga.chromosome.Chromosome;
import net.orhanbalci.ga.crossover.ICrossOver;
import net.orhanbalci.ga.crossover.OnePointCrossOver;
import net.orhanbalci.ga.crossover.TwoPointCrossOver;
import net.orhanbalci.ga.fitnesscalculator.IFitnessCalculator;
import net.orhanbalci.ga.fitnesscalculator.WarehouseFitnessCalculator;
import net.orhanbalci.ga.mutator.*;
import net.orhanbalci.ga.selector.ISelector;
import net.orhanbalci.ga.selector.TournamentSelector;

import java.util.*;

public class WarehouseLocationSolver {
    private int populationSize;
    private int iterationCount;
    private ICrossOver crosoverOperator;
    private IMutator mutatorOperator;
    private ISelector selectionOperator;
    private IFitnessCalculator fitnessCalculator;
    private WarehouseLocationProblem problem;
    private List<Chromosome> population;
    private List<Chromosome> prevGeneration;
    private Random r;
    private Chromosome best;
    private boolean elitism = false;
    private SolverParameters sp;
    private Chromosome hillClimbBest;

    public WarehouseLocationSolver(SolverParameters params, WarehouseLocationProblem problem) {
        r = new Random(params.randomSeed);
        this.populationSize = params.populationCount;
        this.iterationCount = params.generationCount;
        if (params.crossoverType == "OnePoint")
            this.crosoverOperator = new OnePointCrossOver(params.crossoverProb, r);
        else if (params.crossoverType == "TwoPoint") {
            this.crosoverOperator = new TwoPointCrossOver(params.crossoverProb, r);
        }
        if (params.mutatorType == "BitwiseMutator")
            this.mutatorOperator = new BitwiseMutator(r, params.mutatorProb);
        else if (params.mutatorType == "Similarity")
            this.mutatorOperator = new SimilarityMutator(r, params.mutatorProb, params.mutatorSide);
        else if (params.mutatorType == "MostFrequent") {
            this.mutatorOperator = new MostFrequentMutator(r, params.mutatorProb, params.frequentGeneCount);
        } else if (params.mutatorType == "TravelCost") {
            this.mutatorOperator = new TravelCostMutator(r, params.mutatorProb, problem);
        } else if (params.mutatorType == "Swap") {
            this.mutatorOperator = new SwapMutator(r, params.mutatorProb);
        }

        if (params.selectorType == "Tournement")
            this.selectionOperator = new TournamentSelector(r, params.tournementSize);

        this.fitnessCalculator = new WarehouseFitnessCalculator(problem);
        this.problem = problem;
        population = new ArrayList<>(Collections.nCopies(populationSize, new Chromosome(0, 0, 0)));
        prevGeneration = new ArrayList<>(Collections.nCopies(populationSize, new Chromosome(0, 0, 0)));
        this.elitism = params.elitism;

        sp = params;
    }

    public void solve() {
        int counter = 0;
        generatePopulation();
        do {
            saveGeneration();
            calculatePopulationFitness();
            selectMatingPool();
            doCrossOver();
            doMutation();
            moveElites();
            counter++;
        } while (counter < iterationCount);
    }

    private void moveElites() {
        if (!elitism) {
            return;
        }
        //Chromosome worstOffSpring = null;
        double worstFitness = 0;
        int worstPlace = -1;
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).getFitnessValue() > worstFitness) {
                worstFitness = population.get(i).getFitnessValue();
                //worstOffSpring = population.get(i);
                worstPlace = i;
            }
        }

        Chromosome elite = null;
        double eliteFitness = Double.MAX_VALUE;
        for (int i = 0; i < prevGeneration.size(); i++) {
            Chromosome individual = prevGeneration.get(i);
            if (individual.getFitnessValue() < eliteFitness) {
                eliteFitness = individual.getFitnessValue();
                elite = individual;
            }
        }

        if (eliteFitness < worstFitness) {
            assert worstPlace != -1;
            population.set(worstPlace, elite);
        }
    }

    private void saveGeneration() {
        if (!elitism) {
            return;
        }
        prevGeneration.clear();
        for (Chromosome c : population) {
            try {
                prevGeneration.add((Chromosome) c.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    private void doMutation() {
        for (int i = 0; i < population.size(); i++) {
            Chromosome chrom = population.get(i);
            mutatorOperator.applyMutation(chrom);
        }
    }

    private void doCrossOver() {
        for (int i = 0; i < population.size(); i += 2) {
            Chromosome chrom1 = population.get(i);
            Chromosome chrom2 = population.get(i + 1);
            crosoverOperator.applyCrossOver(chrom1, chrom2);
        }
    }

    private void selectMatingPool() {
        selectionOperator.doSelection(population);
    }

    private void calculatePopulationFitness() {
        double bestFitness = Double.MAX_VALUE;
        boolean bestChanged = false;
        if (null != best) {
            bestFitness = best.getFitnessValue();
        }
        for (int i = 0; i < population.size(); i++) {
            Chromosome chromosome = population.get(i);
            fitnessCalculator.calculateFitness(chromosome);
            if (chromosome.getFitnessValue() < bestFitness) {
                best = new Chromosome(chromosome);
                bestFitness = best.getFitnessValue();
                bestChanged = true;
            }
        }

//        if (bestChanged) {
////            Chromosome c = hillClimb(best, true, false);
//            Chromosome c2 = hillClimb(best, false, true);
//            if (hillClimbBest == null || (c2.getFitnessValue() < hillClimbBest.getFitnessValue())) {
//                hillClimbBest = new Chromosome(c2);
//            }
//            if (c2.getFitnessValue() <= 3000) {
//
//                System.out.println("^^^^^^^^Result Found^^^^^^");
//                System.out.println(sp);
//                printResult(c2);
//                //System.exit(0);
//            }
//
//        }
    }

    private void generatePopulation() {
        for (int i = 0; i < populationSize; i++) {
            Chromosome c = new Chromosome(problem.customers.length, 0, problem.warehouses.length - 1);
//            int wareHouseCounter = 0;
//            double remCapacity = problem.warehouses[problem.getLowestCostWarehouse(wareHouseCounter)].capacity;
//            for (int j = 0; j < c.getLength(); j++) {
//                int warehouseId = problem.getLowestCostWarehouse(wareHouseCounter);
//                c.setGene(j, warehouseId);
//                remCapacity -= problem.customers[j].demand;
//                if (remCapacity / problem.warehouses[problem.getLowestCostWarehouse(wareHouseCounter)].capacity < 0.2) {
//                    wareHouseCounter++;
//                    remCapacity = problem.warehouses[problem.getLowestCostWarehouse(wareHouseCounter)].capacity;
//                }
//            }
            c.initRandom(r);
            population.set(i, c);
        }

//        for (int i = 2; i < populationSize; i++) {
//            Chromosome c = new Chromosome(problem.customers.length, 0, problem.warehouses.length - 1);
//            c.initRandom(r);
//            population.set(i, c);
//        }

    }

    public void printResult(Chromosome pChrom) {
        if (null == pChrom)
            return;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Locale.US, "%f", pChrom.getFitnessValue())).append("\n");

        for (Integer gene : pChrom) {
            sb.append(String.format("%d", gene)).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb.toString());
    }

    public Chromosome getBest() {
        if (best == null && hillClimbBest == null) {
            System.out.println("Get Best Failed");
            return null;
        }
        if (best == null) return hillClimbBest;
        if (hillClimbBest == null) return best;
        return best.getFitnessValue() < hillClimbBest.getFitnessValue() ? best : hillClimbBest;
    }


    public Chromosome hillClimb(Chromosome c, boolean verbose, boolean forwards) {
        Chromosome cClone = null;
        try {
            cClone = (Chromosome) c.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        double prevBestFitness = c.getFitnessValue();
        for (int i = 0; i < c.getLength(); i++) {
            if (!forwards) {
                double gainMost = -1;
                int gainMostCandidate = -1;
                for (int j = c.getUpperBound(); j >= c.getLowerBound(); j--) {
                    Integer gene = cClone.getGene(i);
                    cClone.setGene(i, j);
                    fitnessCalculator.calculateFitness(cClone);
                    if (cClone.getFitnessValue() < prevBestFitness) {
                        double gain = prevBestFitness - cClone.getFitnessValue();
                        if (gain > gainMost) {
                            gainMost = gain;
                            gainMostCandidate = j;
                        }
                    }
                    cClone.setGene(i, gene);
                    fitnessCalculator.calculateFitness(cClone);

                }

                if (gainMostCandidate != -1) {
                    cClone.setGene(i, gainMostCandidate);
                    fitnessCalculator.calculateFitness(cClone);
                    prevBestFitness = cClone.getFitnessValue();
                }
            } else {
                double gainMost = -1;
                int gainMostCandidate = -1;
                for (int j = c.getLowerBound(); j <= c.getUpperBound(); j++) {
                    Integer gene = cClone.getGene(i);
                    cClone.setGene(i, j);
                    fitnessCalculator.calculateFitness(cClone);
                    if (cClone.getFitnessValue() < prevBestFitness) {
                        double gain = prevBestFitness - cClone.getFitnessValue();
                        if (gain > gainMost) {
                            gainMost = gain;
                            gainMostCandidate = j;
                        }
                    }
                    cClone.setGene(i, gene);
                    fitnessCalculator.calculateFitness(cClone);

                }

                if (gainMostCandidate != -1) {
                    cClone.setGene(i, gainMostCandidate);
                    fitnessCalculator.calculateFitness(cClone);
                    prevBestFitness = cClone.getFitnessValue();
                }
            }
        }

        if (prevBestFitness < 23000000 && verbose) {
            System.out.println("%%%%%%%%%%%%%%NEW BEST FOUND%%%%%%%%%%%%%%%%");
            printResult(cClone);
            System.out.println(sp);
            System.out.println("%%%%%%%%%%%%%%NEW BEST FOUND%%%%%%%%%%%%%%%%");
//            System.exit(0);
        }

        return cClone;
    }


}


