package net.orhanbalci.ga.warehouse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WarehouseLocationProblemReader {

    public static List<WarehouseLocationProblem> readFile(String fileName) {
        List<WarehouseLocationProblem> result = new ArrayList<>();
        try (FileInputStream fr = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fr);
             BufferedReader bfr = new BufferedReader(isr)) {

            String line = bfr.readLine();
            do {
                String[] split = line.split(" ");
                int warehouseCount = Integer.parseInt(split[0]);
                int customerCount = Integer.parseInt(split[1]);
                WarehouseLocationProblem problem = new WarehouseLocationProblem(warehouseCount, customerCount);

                for (int i = 0; i < warehouseCount; i++) {
                    line = bfr.readLine();
                    split = line.split(" ");
                    problem.warehouses[i] = new Warehouse(i, Double.parseDouble(split[0]), Double.parseDouble(split[1]));
                }

                for (int y = 0; y < customerCount; y++) {
                    line = bfr.readLine();
                    problem.customers[y] = new Customer(y, Double.parseDouble(line));
                    line = bfr.readLine();
                    split = line.split(" ");
                    for (int i = 0; i < split.length; i++) {
                        problem.customers[y].travelCost.put(i, Double.parseDouble(split[i]));
                    }
                }
                result.add(problem);
            } while ((line = bfr.readLine()) != null);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}

