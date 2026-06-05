//Romanos Kotsis 4714
//Vaggelis Basdavanos 4962
import java.util.Random;

public class GroupingDataset {

    private double[][] dataset = new double[1000][2]; 

    public double[][] getSet(){
        generateDataset(dataset);
        return dataset;
    }

    private static void generateDataset(double[][] dataset) {
        Random random = new Random();
        double[][] boundaries = {
            {-2, -1.6, 1.6, 2},     
            {-1.2, -0.8, 1.6, 2},    
            {-0.4, 0, 1.6, 2},      
            {-1.8, -1.4, 0.8, 1.2}, 
            {-0.6, -0.2, 0.8, 1.2}, 
            {-2, -1.6, 0, 0.4},     
            {-1.2, -0.8, 0, 0.4},   
            {-0.4, 0, 0, 0.4},      
            {-2, 0, 0, 2}           
        };
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 100; j++) {
                double x1Min = boundaries[i][0];
                double x1Max = boundaries[i][1];
                double x2Min = boundaries[i][2];
                double x2Max = boundaries[i][3];
                
                double x1 = x1Min + (x1Max - x1Min) * random.nextDouble();
                double x2 = x2Min + (x2Max - x2Min) * random.nextDouble();
                
                dataset[counter][0] = x1;
                dataset[counter][1] = x2;

                counter ++;
            }
        }
        for (int j = 0; j < 200; j++) {
            double x1Min = boundaries[8][0];
            double x1Max = boundaries[8][1];
            double x2Min = boundaries[8][2];
            double x2Max = boundaries[8][3];
            
            double x1 = x1Min + (x1Max - x1Min) * random.nextDouble();
            double x2 = x2Min + (x2Max - x2Min) * random.nextDouble();
            
            dataset[counter][0] = x1;
            dataset[counter][1] = x2;

            counter ++;

        }
    }
}
