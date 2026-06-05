//Romanos Kotsis 4714
//Vaggelis Basdavanos 4962
import java.util.ArrayList;
import java.util.Random;

public class MLP2 {
	
	static final int D = 2; 
    static final int K = 4; 
    static final int H1 = 10; 
    static final int H2 = 10; 
    static final double LEARNING_RATE = 0.001;
    static final double TERMINATION_THRESHOLD = 0.5;
    static final int EPOCHS = 800;
    private String[] activations =  new String[3];
    private double[][] trainingSet;
    private double[][] testSet;
    private ArrayList<double[][]> newTrainingSet = new ArrayList<>();
    private double[][][] weights; 
    private double[][] biases;
    private double[][][] derivativeOfWeights = new double [3][][];
    private double[][] derivativeOfBiases = new double [3][]; 


    public void getDataSets(){
    	ClassificationDataset dataset = new ClassificationDataset();
    	dataset.generateDatasets();
    	trainingSet = dataset.getTrainingSet();
    	testSet = dataset.getTestSet();
    }

   	public void categoryClassification(){
   		for(int i = 0; i < trainingSet.length; i++){
   			double[] inputs = {trainingSet[i][0],trainingSet[i][1]};
   			int category = (int)trainingSet[i][2];
   			double[] vector = new double[K]; 
   			vector[category-1] = 1;
   			double[][] entry = {inputs,vector};
   			newTrainingSet.add(entry);
   		}
   	}

   	public void weightInitialization(){
   		double[][] weights1 = new double[H1][D]; 
    	double[][] weights2 = new double[H2][H1]; 
    	double[][] weightsK = new double[K][H2];
   		Random random = new Random();
   		for (int i = 0; i < H1; i++){
   			for (int j = 0;j < D; j++){
   				weights1[i][j] = random.nextDouble() * 2 - 1;
   			}
   		}
   		for (int i = 0; i < H2; i++){
   			for (int j = 0;j < H1; j++){
   				weights2[i][j]= random.nextDouble() * 2 - 1;
   			}
   		}
   		for (int i = 0; i < K; i++){
   			for (int j = 0;j < H2; j++){
   				weightsK[i][j]= random.nextDouble() * 2 - 1;
   			}
   		}
   		weights = new double[][][] { weights1, weights2, weightsK};
   	}

   	public void biasInitialization(){
     	double[] bias1 = new double[H1];
     	double[] bias2 = new double[H2];
     	double[] biasK = new double[K];
   		Random random = new Random();
   		for (int i = 0; i < H1; i++){
   			bias1[i] = random.nextDouble() * 2 - 1;
   		}
   		for (int i = 0; i < H2; i++){
   			bias2[i] = random.nextDouble() * 2 - 1;
   		}
   		for (int i = 0; i < K; i++){
   			biasK[i] = random.nextDouble() * 2 - 1;
   		}
   		biases = new double[][] { bias1, bias2, biasK };
   	}


   	public void forwardPass(double[] x,int D, double[] y, int K){
   		double[] input = x;
        int[] numberOfNeurons = {D,H1,H2,K};
        for (int i = 1; i < 4; i++) {
            double[] output = new double[numberOfNeurons[i]];
            for (int j = 0; j < numberOfNeurons[i]; j++) { 
                double sum = biases[i-1][j];
                for (int m = 0; m < numberOfNeurons[i-1]; m++) { 
                    sum += input[m] * weights[i-1][j][m];
                }
                output[j] = activate(sum, activations[i - 1]);
            }
            input = output;
        }
        for (int i = 0; i < K; i++) {
            y[i] = input[i];
        }
   	}

   	public double activate(double u, String activation){
   		if (activation.equals("relu")){
   			return Math.max(0, u);
   		}
   		else if(activation.equals("tanh")){
   			return Math.tanh(u);
   		}
   		else if(activation.equals("sigmoid")){
   			return 1 / (1 + Math.exp(-u));
   		}
   		return 0;
   	}

   	
    public void backprop(double[] x, int d, double[] t, int K) {
        double[][] inputs = new double[4][];
        inputs[0] = new double[d];
        for (int i = 0; i < d; i++){
            inputs[0][i] = x[i];
        }

        for (int i = 1; i < 4; i++) {
            inputs[i] = new double[weights[i - 1].length];
            for (int j = 0; j < weights[i - 1].length; j++) {
                double sum = biases[i - 1][j];
                for (int k = 0; k < weights[i - 1][j].length; k++) {
                    sum += inputs[i - 1][k] * weights[i - 1][j][k];
                }
                inputs[i][j] = activate(sum, activations[i - 1]);
            }
        }
        double[][] deltas = new double[3][];

        deltas[2] = new double[K];
        for (int i = 0; i < K; i++) {
            deltas[2][i] = (inputs[3][i] - t[i]) * derivativeOfActivation(inputs[3][i],activations[2]);
        }
        
        for (int i = 1; i >= 0; i--) {
            deltas[i] = new double[weights[i].length];
            for (int j = 0; j < weights[i].length; j++) {
                double error = 0;
                for (int k = 0; k < deltas[i + 1].length; k++) {
                    error += deltas[i + 1][k] * weights[i + 1][k][j];
                }
                deltas[i][j] = error * derivativeOfActivation(inputs[i + 1][j], activations[i]);
            }
        }


        for (int i = 0; i < weights.length; i++) {
            this.derivativeOfWeights[i] = new double[weights[i].length][];
            this.derivativeOfBiases[i] = new double[biases[i].length];
            for (int j = 0; j < weights[i].length; j++) {
                this.derivativeOfWeights[i][j] = new double[weights[i][j].length];
                for (int k = 0; k < weights[i][j].length; k++) {
                    this.derivativeOfWeights[i][j][k] =  deltas[i][j] * inputs[i][k];
                }
                this.derivativeOfBiases[i][j] =  deltas[i][j];
            }
        }
     }

	private double derivativeOfActivation(double u, String activation) {
	    if (activation.equals("sigmoid")) {
	        return u * (1 - u);  
	    } else if (activation.equals("tanh")) {
	        return 1 - Math.pow(u, 2);  
	    } else if (activation.equals("relu")) {
	    	if(u > 0){
	    		return 1;
	    	}
	    	else {return 0;}
	    }
	    return 0;
	}


    public void gradientDescent(int B) {
        weightInitialization();
        biasInitialization();
        int N = newTrainingSet.size();
        double previousError = Double.MAX_VALUE;
        double totalError = 0.0;
        int epoch = 0;
        
        while (epoch < EPOCHS || Math.abs(previousError - totalError) >= TERMINATION_THRESHOLD) {
            previousError = totalError;
            totalError = 0.0;
            double[][][] derivativeOfWeightsGradient = new double[weights.length][][];
            double[][] derivativeOfBiasesGradient = new double[biases.length][];
            
            
           

            for (int batchStart = 0; batchStart < N; batchStart += B) {
                for (int i = 0; i < weights.length; i++) {
                    derivativeOfWeightsGradient[i] = new double[weights[i].length][];
                    derivativeOfBiasesGradient[i] = new double[biases[i].length];
                    for (int j = 0; j < weights[i].length; j++) {
                        derivativeOfWeightsGradient[i][j] = new double[weights[i][j].length];
                    }
                }
                for (int i = batchStart; i < batchStart + B; i++) {

                    double[][] example = newTrainingSet.get(i);
                    double[] x = example[0]; 
                    double[] t = example[1]; 

                    backprop(x, D, t, K);  

                    for (int l = 0; l < weights.length; l++) {
                        for (int j = 0; j < weights[l].length; j++) {
                            for (int k = 0; k < weights[l][j].length; k++) {
                                derivativeOfWeightsGradient[l][j][k] += derivativeOfWeights[l][j][k];
                            }
                            derivativeOfBiasesGradient[l][j]+= derivativeOfBiases[l][j];
                        }
                    }
                }

                
                for (int l = 0; l < weights.length; l++) {
                    for (int j = 0; j < weights[l].length; j++) {
                        for (int k = 0; k < weights[l][j].length; k++) {
                            weights[l][j][k] -= LEARNING_RATE * derivativeOfWeightsGradient[l][j][k];
                        }
                        biases[l][j] -= LEARNING_RATE * derivativeOfBiasesGradient[l][j] ;
                    }
                }
            }
            

            for (double[][] example : newTrainingSet) {
                double[] x = example[0]; 
                double[] t = example[1]; 
                double[] y = new double[K]; 

                forwardPass(x, D, y, K);
                
                for (int k = 0; k < K; k++) {
                    totalError += Math.pow(t[k] - y[k], 2);
                }
            }

            totalError /= 2; 
            System.out.println("Epoch " + (epoch + 1) + ": Total Error = " + totalError);

            epoch++;
        }

        System.out.println("Training complete after " + epoch + " epochs: Total Error = " + totalError);
    }

    public void evaluateGeneralization() {
    int correctPredictions = 0;
    int totalExamples = testSet.length;

    for (double[] example : testSet) {
        double[] x = {example[0], example[1]};
        int trueCategory = (int) example[2];  
        double[] y = new double[K];          

        forwardPass(x, D, y, K);

        
        int predictedCategory = 0;
        double maxProbability = y[0];
        for (int i = 1; i < K; i++) {
            if (y[i] > maxProbability) {
                maxProbability = y[i];
                if(i+1 == trueCategory){
                    System.out.println("x1: "+example[0]+" x2: "+example[1]+" Check: +.");
                }
                else{
                    System.out.println("x1: "+example[0]+" x2: "+example[1]+" Check: -.");
                }
                predictedCategory = i;
            }
        }

       
        if (predictedCategory + 1 == trueCategory) {

            correctPredictions++;
        }
    }

    
    double accuracy = (correctPredictions / (double) totalExamples) * 100;
    System.out.println("Generalization Accuracy: " + accuracy + "%");
}


    public static void main(String[] args) {
        MLP2 mlp2 = new MLP2();
        mlp2.getDataSets();
        mlp2.categoryClassification();
        String[] functions = {"tanh", "relu", "sigmoid"};
        mlp2.activations = functions;
        mlp2.gradientDescent(20); 
        mlp2.evaluateGeneralization();
    }
}