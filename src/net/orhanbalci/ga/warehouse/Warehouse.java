package net.orhanbalci.ga.warehouse;

public class Warehouse implements Comparable<Warehouse> {
    public int warehouseId;
    public double capacity;
    public double setupCost;

    public Warehouse(int warehouseId, double capacity, double setupCost) {
        this.warehouseId = warehouseId;
        this.capacity = capacity;
        this.setupCost = setupCost;
    }


    @Override
    public int compareTo(Warehouse o) {
        return (int) (this.setupCost - o.setupCost);
    }
}
