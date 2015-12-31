package net.orhanbalci.ga.warehouse;

import java.util.HashMap;
import java.util.Set;

public class Customer {
    public int customerId;
    public double demand;
    public HashMap<Integer, Double> travelCost = new HashMap<>();

    public Customer(int customerId, double demand) {
        this.customerId = customerId;
        this.demand = demand;
    }

    public int getMostFavorableWarehouse() {
        Set<Integer> wareHouses = travelCost.keySet();
        double cost = Double.MAX_VALUE;
        int returnValue = -1;
        for (Integer ware : wareHouses) {
            if (travelCost.get(ware) < cost) {
                cost = travelCost.get(ware);
                returnValue = ware;
            }
        }

        return returnValue;
    }
}

