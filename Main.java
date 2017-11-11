/*
 *
 */

package ann;

/**
 *
 
 */
public class Main {

    /**
     *
     */
    public static void main(String[] args) {


        /**
         * You may want to create a nueral network by this method from the class of NeuralNetwork
         * The construvture gets a string as input data and number of hidden units
         * Data addresses: "./DataSet/tennis-train.txt" , "./DataSet/iris-train.txt" , "./DataSet/identity-train.txt"
        */
        String DataFileAddress = "./DataSet/tennis-train.txt"; // Enter the traning data file address
        String TestFileAddress = "./DataSet/tennis-test.txt"; // Enter the test data file address
        int numberOfHiddenUnit = 3;
        int epoch =3000; // It may refer as iteration.
        double Etta = 0.3; // The learning rate
        double momentum = 0;
        NeuralNetwork myANN = new NeuralNetwork(DataFileAddress, numberOfHiddenUnit); // Build a neural network (fileAddress, number of hidden unit)
        double [][] inputMatrix =  myANN.myData.ExampleMatrix;
        myANN.learnANN(inputMatrix, epoch, Etta, momentum); // train the nueral network (dataset, epoch, etta, momentum)
        
        if (DataFileAddress.contains("identity"))
            myANN.poltHidden();
        if (DataFileAddress.contains("iris"))
            // Test and learn with nois and with or without validatio ste
            // (TestFileAddress, iteration, RateEtta, momentum, timesOfRun, numOfValidtest) numOfValidtest =0 then no validation
            myANN.TestLearnAnnNoise(TestFileAddress, epoch, Etta, momentum, 3, 5);
        if (DataFileAddress.contains("iris") || DataFileAddress.contains("tennis"))
            myANN.LearnPlotTest(inputMatrix, TestFileAddress, epoch, Etta, momentum);

        myANN.poltAccuracy();









    }

}
