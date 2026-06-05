//Romanos Kotsis 4714
//Vaggelis Basdavanos 4962
import java.util.Random;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class kMeans{

	static final int M = 6;
	private double[][] centroids;
	private ArrayList<ArrayList<double[]>> regions;
	private double[][] dataset ; 

    public double[][] getDataSet(){

    	GroupingDataset dataset = new GroupingDataset();
    	return dataset.getSet();
    }

    public void kmeans(){
    	centroidsInitialization();
    	boolean difference = true;
    	while(difference){

    		for(int j = 0; j < dataset.length; j++){
    			double[] point = dataset[j];
    			double[] distances = new double[M];
    			double minDistance = Double.MAX_VALUE;
    			int selectedRegion = 0;
    			for(int k = 0;k < M; k++){
    				distances[k] = computeDistance(point, centroids[k]);
    				if(distances[k] < minDistance){
    					selectedRegion = k;
    					minDistance = distances[k];
    				}
    			}

    			int[] info = findRegionAndPosition(point[0],point[1]);
    			if(info == null){
    				regions.get(selectedRegion).add(point);
    			}
    			else{
    				regions.get(info[0]).remove(info[1]);
    				regions.get(selectedRegion).add(point);
    			}
    			
    		}
    		double[][] centroidsCopy = new double[M][]; 
    		for(int i =0;i<centroids.length;i++){
    			centroidsCopy[i] = new double[centroids[i].length];
	    		for(int j = 0;j < centroids[i].length;j++){
	    			centroidsCopy[i][j] = centroids[i][j];
	    		}
	    	}
    		recalculateCentroids(regions);
    		difference = checkDifference(centroidsCopy,centroids);
    	}
    	
    }

    public boolean checkDifference(double[][] centroidsCopy,double[][] centroids){

    	for(int i =0;i<centroidsCopy.length;i++){
    		for(int j = 0;j < centroidsCopy[i].length;j++){
    			if(centroidsCopy[i][j] != centroids[i][j]){
    				return true;
    			}
    		}
    	}
    	return false;
    }

	public int[] findRegionAndPosition(double x1, double x2) {
		int[] info = new int[2];
	    for (int i = 0; i < regions.size(); i++) {
	        ArrayList<double[]> region = regions.get(i);
	        for (int j = 0; j < region.size(); j++) {
	            double[] point = region.get(j);
	            if (point[0] == x1 && point[1] == x2) {
	            	info[0] = i;
	            	info[1] = j;

	                return info; 
	            }
	        }
	    }
	    return null; 
	}


    public void recalculateCentroids(ArrayList<ArrayList<double[]>> regions){
    	for(int i = 0;i < regions.size(); i++){
    		double sumX = 0.0;
    		double sumY = 0.0;

    		for(int j = 0;j < regions.get(i).size(); j++){
    			sumX += regions.get(i).get(j)[0];
    			sumY += regions.get(i).get(j)[1];
    		}
    		if (regions.get(i).size() != 0){
	    		centroids[i][0]  = sumX/regions.get(i).size();
	    		centroids[i][1]  = sumY/regions.get(i).size();
	    	}
    	}
    }

    public double computeDistance(double[] point, double[] centroid){
    	double distance = Math.sqrt(Math.pow(point[0]-centroid[0],2) + Math.pow(point[1]-centroid[1],2));
    	return distance;
    }

    public void centroidsInitialization(){
    	centroids = new double[M][];
    	regions = new ArrayList<>();
    	Random random = new Random();
    	for(int i = 0; i < M; i++){
    		int position = random.nextInt(1000);
    		double[] centroid = dataset[position];
    		centroids[i] = centroid;
    		ArrayList<double[]> region = new ArrayList<>();
    		regions.add(region);
    	}
    }

    public double calculateGroupingError(){
    	double groupingError = 0.0;
    	for(int i = 0;i < regions.size(); i++){
    		for(int j = 0;j < regions.get(i).size(); j++){
    			groupingError += computeDistance(regions.get(i).get(j),centroids[i]);
    		}
    	}
    	System.out.println("Grouping Error: "+groupingError);
    	return groupingError;
    }

    public void printFinalCentroids(double[][] current_centroids){
    	for(int i=0; i<current_centroids.length;i++){
    		System.out.println("Centroid " +(i+1)+": x1 = "+current_centroids[i][0]+"  x2 = "+current_centroids[i][1]);
    	}
    }

  // Method to write both dataset and centroids to a single CSV file
    public void writeDataAndCentroidsToCSV(String filename,double[][] best_centroids) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write dataset section
            writer.write("Dataset,x1,x2\n");
            for (int i = 0; i < dataset.length; i++) {
                writer.write("Data " + (i + 1) + "," + dataset[i][0] + "," + dataset[i][1] + "\n");
            }

            // Add a separator line
            writer.write("\nCentroids,x1,x2\n");

            // Write centroids section
            for (int i = 0; i < best_centroids.length; i++) {
                writer.write("Centroid " + (i + 1) + "," + best_centroids[i][0] + "," + best_centroids[i][1] + "\n");
            }

            System.out.println("Dataset and Centroids written to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        kMeans kmeans = new kMeans();
        kmeans.dataset = kmeans.getDataSet();
        double[][] current_centroids = new double[M][]; 
        double current_grouping_error = Double.MAX_VALUE;
        int iteration = 0 ;
        double grouping_error = 0;
        for(int i = 0; i<20;i++){
        	System.out.println("\nIteration: "+(i+1));
        	kmeans.kmeans();
        	grouping_error =  kmeans.calculateGroupingError();
        	if (grouping_error < current_grouping_error){
        		iteration = (i+1);
        		current_grouping_error = grouping_error;
        		current_centroids = kmeans.centroids;
        	}
		 }
        System.out.println("\nBest grouping error was: "+current_grouping_error+" during the iteration no"+iteration+"\n");
        kmeans.printFinalCentroids(current_centroids);

        kmeans.writeDataAndCentroidsToCSV("dataset_and_centroids.csv",current_centroids);
        
    }
}