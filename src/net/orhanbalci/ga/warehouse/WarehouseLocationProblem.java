package net.orhanbalci.ga.warehouse;

import java.util.Arrays;

public class WarehouseLocationProblem {
    public Customer[] customers;
    public Warehouse[] warehouses;

    public WarehouseLocationProblem(int wareHouseCount, int customerCount) {
        customers = new Customer[customerCount];
        warehouses = new Warehouse[wareHouseCount];
    }

    public int getLowestCostWarehouse(int nth) {
        int index = 0;
        Warehouse[] temp = warehouses.clone();
        Arrays.sort(temp);
        return temp[nth].warehouseId;
    }

}


