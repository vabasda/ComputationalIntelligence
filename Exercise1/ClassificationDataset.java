//Romanos Kotsis 4714
//Vaggelis Basdavanos 4962
import java.util.Random;

public class ClassificationDataset {

    private int totalPoints = 8000;
    private int trainingSetSize = totalPoints / 2;
    private int testSetSize = totalPoints / 2;
    private double[][] trainingSet = new double[trainingSetSize][3]; 
    private double[][] testSet = new double[testSetSize][3];


    public void generateDatasets(){
        generateDataset(trainingSet);
        generateDataset(testSet);
    }

    public double[][] getTrainingSet(){
        return trainingSet;
    }

    public double[][] getTestSet(){
        return testSet;
    }

    public void generateDataset(double[][] dataset) {
        Random random = new Random();
        for (int i = 0; i < dataset.length; i++) {
            double x1 = -1 + 2 * random.nextDouble(); 
            double x2 = -1 + 2 * random.nextDouble();
            int category = classifyPoint(x1, x2);
            dataset[i][0] = x1;
            dataset[i][1] = x2;
            dataset[i][2] = category;
        }
    }




    public int classifyPoint(double x1, double x2) {
        if ((Math.pow(x1 - 0.5, 2) + Math.pow(x2 - 0.5, 2) < 0.2) && x2 > 0.5) {
            return 1 ;
        }else if ((Math.pow(x1 - 0.5, 2) + Math.pow(x2 - 0.5, 2) < 0.2) && x2 < 0.5) {
            return 2 ;
        } else if ((Math.pow(x1 + 0.5, 2) + Math.pow(x2 + 0.5, 2) < 0.2) && x2 > -0.5){
            return 1 ;
        } else if ((Math.pow(x1 + 0.5, 2) + Math.pow(x2 + 0.5, 2) < 0.2) && x2 < -0.5){
            return 2 ;
        } else if ((Math.pow(x1 - 0.5, 2) + Math.pow(x2 + 0.5, 2) < 0.2) && x2 > -0.5){
            return 1 ;
        } else if ((Math.pow(x1 - 0.5, 2) + Math.pow(x2 + 0.5, 2) < 0.2) && x2 < -0.5){
            return 2 ;
        } else if ((Math.pow(x1 + 0.5, 2) + Math.pow(x2 - 0.5, 2) < 0.2) && x2 > 0.5){
            return 1 ;
        } else if ((Math.pow(x1 + 0.5, 2) + Math.pow(x2 - 0.5, 2) < 0.2) && x2 < 0.5){
            return 2 ;
        } else if (x1*x2 > 0){
            return 3;
        }else{
            return 4;
        }
    }
}
