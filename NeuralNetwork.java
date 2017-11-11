/*
 *
 */

package ann;

import java.util.Random;

/**
 *
 * 
 */
public class NeuralNetwork {

    DataSet myData;
    double [] outLayer;
    double [] hidLayer;
    double [] inLayer;
    double [][] target; // target that is going to be matched
    double [][] weightToHidden; // weit from input to hidden layer
    double [][] weightToOut; // weights from hidden layer to output layer
    double [][] inputMatrix; // primary input DataSet + Bias
    double [] DeltaOut; // errors in outputs
    double [] DeltaHid; // errors in hidden units
    double [][] deltaWeightToOut; // Delta weights from hidden layer to output layer
    double [][] deltaWeightToHidden; // Delta weights from input layer to hidden layer
    double [] totError; // The total error
    double [] testError;
    double myTotalError; // The total error that achieved by testing a set on ANN
    double [][] firstData;
    double [] noiseError;
    int [][] thresholdedOutput;
    double accuracy;
    double [] accuracyOnEpoch;
    double [][] hiddenPerEpoch;
    int corruptionRate;

    public NeuralNetwork()
    {}
    /**
     * This constructor will creat input, hidden and output layer
     * with respect to the number of examples, attributes per example and classes.
     * And, it will assign examples to "inputMatrix" which contains examples plus bias=1;
     * And, it compute the target according to the data set
     */
    public NeuralNetwork (String FileAddress, int hidUnitNum)
    {
        myData = new DataSet(FileAddress);
        firstData = new double [myData.ExampleMatrix.length][myData.ExampleMatrix[0].length];
        for (int i=0; i<firstData.length; i++)
            for (int j=0; j<firstData[0].length; j++)
                firstData[i][j]= myData.ExampleMatrix[i][j];
//        myData.Normalize();
        target =new double [myData.ExampleMatrix.length][myData.numOfClasses];
        target = myTarget(myData);
//        int classIndex = myData.ExampleMatrix[0].length-1;
        int numberOfAtt = myData.ExampleMatrix[0].length-1;

        outLayer = new double [myData.numOfClasses];
        hidLayer = new double [hidUnitNum+1];
        inLayer = new double[numberOfAtt+1];
        weightToHidden = new double [inLayer.length][hidLayer.length-1];
        deltaWeightToHidden = new double [inLayer.length][hidLayer.length-1];
        weightToOut = new double [hidLayer.length][outLayer.length];
        deltaWeightToOut = new double [hidLayer.length][outLayer.length];
        inputMatrix = new double [myData.ExampleMatrix.length][myData.ExampleMatrix[0].length];
        DeltaOut = new double [outLayer.length];
        DeltaHid = new double [hidLayer.length];

        for (int i=0; i<inputMatrix.length; i++) ///  adding bias to Input
        {
            inputMatrix[i][0] = 1;
            for (int j=0; j<inputMatrix[0].length-1; j++)
                inputMatrix [i][j+1] = myData.ExampleMatrix[i][j];
        }
        corruptionRate =0;
        thresholdedOutput = new int [myData.ExampleMatrix.length][outLayer.length];

    }// end of constructor
    
    /**
     * This method adds bias to the dataset (X0=1)
     * Input and output of this method are the same size
     */
    public double [][] addBias(double [][] M)
    {
        double [][] tempMatrix = new double [M.length][M[0].length];
        for (int i=0; i<tempMatrix.length; i++) ///  adding bias to Input
        {
            tempMatrix[i][0] = 1;
            for (int j=0; j<tempMatrix[0].length-1; j++)
                tempMatrix[i][j+1] = M[i][j];
        }
        return tempMatrix;
    }// end of adding bias

    /**
     * This method will initialize weights with randome numbers
     */
    public void Initialize ()
    {
        for (int i=0; i<weightToHidden.length; i++)
            for (int j=0; j<weightToHidden[0].length;  j++)
                weightToHidden[i][j] = myRandom();
//                weightToHidden[i][j] =1;
                
        for (int i=0; i<weightToOut.length; i++)
            for (int j=0; j<weightToOut[0].length;  j++)
                weightToOut[i][j] = myRandom();
//                weightToOut[i][j] = 1;
                
    }

    public void Initialize (double [][] Mx)
    {
        for (int i=0; i<Mx.length; i++)
            for (int j=0; j<Mx[0].length;  j++)
                Mx[i][j] = myRandom();

    }

    /**
     * This method will map the classes to more that one target
     */
    public double [] [] myTarget (DataSet myData)
    {
        double [][] Target = new double [myData.ExampleMatrix.length][myData.numOfClasses];
        for (int i=0; i<Target.length; i++) /// initializing the target
            for (int j=0; j<Target[0].length; j++)
                Target [i][j] = 0;
        int classIndex = myData.ExampleMatrix[0].length-1;
        for (int i=0; i<myData.ExampleMatrix.length; i++)
        {
            Double d = myData.ExampleMatrix[i][classIndex];
            int k = d.intValue();
            Target [i][k] = 1;
        }

        return Target;
    } // End of Target (data set as input)

    public double [] [] myTarget (double [][] M, int numOfClass)
    {
        double [][] Target = new double [M.length][numOfClass];
        for (int i=0; i<Target.length; i++) /// initializing the target
            for (int j=0; j<Target[0].length; j++)
                Target [i][j] = 0;
        int classIndex = M[0].length-1;
        for (int i=0; i<M.length; i++)
        {
            Double d = M[i][classIndex];
            int k = d.intValue();
            Target [i][k] = 1;
        }

        return Target;
    } // End of Target (matrix as input)


    /**
     * This method calculates the sigmoid function
     */
    public double sigmoid(double x)
    {
        return 1 / (1 + Math.exp(-x));
    }// End of sigmoid

    /**
     * This method will create a random number between [0,1]
     */
    public double myRandom()
    {
        Random randomNumbers = new Random();
        double random1= randomNumbers.nextDouble();
        return random1;

    }// End of myRandom
    /**
     * This method calculates the new input for the next layer.
     * In fact, it just do the "Matrix multiplication" .
     * It returns  (Node of a layer * weight to the next layer).
     */
    public double [] feedNextLayer(double [] layer, double [][] weight)
    {
        double [] nextLayer = new double [weight[0].length];
        if (weight.length != layer.length)
            System.out.println("Dinmension is wrong!");
        for (int i=0; i<nextLayer.length; i++)
            nextLayer[i] = 0;
        
        for (int j=0; j<weight[0].length; j++)
            for (int i=0; i<weight.length; i++)
                nextLayer[j] = nextLayer[j] + (layer[i]*weight[i][j]);
        
        return nextLayer;
    } // end of feedforward

   
    /**
     * This method will learn the nueral network
     */
    public void learnANN(int iteration, double etta)
    {
        Initialize();
        accuracyOnEpoch = new double[iteration];
        totError = new double [iteration];
        double [] temp1 = new double [weightToHidden[0].length];
        double [] tempBias1 = new double [temp1.length+1];
        for (int T=1; T<=iteration; T++)
        {
            double myError = 0;
            for (int exampleIndex=0; exampleIndex<inputMatrix.length; exampleIndex++ ) // for each data in input
            {
                for (int i=0; i<inLayer.length; i++) // input this data+bias
                    inLayer[i] = inputMatrix[exampleIndex][i];
                ////////////////////////////////////////////////////////////
                //////////  from input to hidden

                temp1 = feedNextLayer(inLayer, weightToHidden);
                tempBias1[0]=1; // bias for hidden
                for (int i=0; i<temp1.length; i++)
                    tempBias1[i+1] = temp1[i];
                for (int i=0; i<tempBias1.length; i++)
                    hidLayer[i] = tempBias1[i];
                //////////////////////////////////   hidden got values + bias=1 (hidLayer[0]=1)
                /////////////////  Now the hidden layer must perform sigmoid
                for (int i=1; i<hidLayer.length; i++)
                    hidLayer[i] = sigmoid(hidLayer[i]);
                //////////from hidden to output////////////////////////////////////////
                outLayer = feedNextLayer(hidLayer, weightToOut);
                for (int i=0; i<outLayer.length; i++)
                    outLayer[i] = sigmoid(outLayer[i]);
                if (T>iteration*0.95)
                {
                    System.out.print(exampleIndex+1 + " : ");
                    for (int i=0; i<outLayer.length; i++)
                        System.out.print("\t" + outLayer[i]);
                    System.out.println();
                }
                
                ////////////////////////////  Now there are some values in outputs
                /////////////////////////////////////////////////////////////////////
                //////////////  Error Backpropagation
                for (int k=0; k<outLayer.length; k++) // delta for output
                    DeltaOut[k] = outLayer[k]*(1 - outLayer[k])*(target[exampleIndex][k] - outLayer[k]);
                double temp=0;
                for (int h=0; h<DeltaHid.length; h++) // delta for hidden
                {
                    temp = hidLayer[h] * (1-hidLayer[h]);
                    double sum=0;
                    for (int k=0; k<outLayer.length; k++)
                        sum = sum + (weightToOut[h][k]*DeltaOut[k]);
                    temp = temp * sum;
                    DeltaHid[h] = temp;
                }
                //??????????????????  DeltaHid[0]=1;
                /////////////////////////
                ///////  delta for weights from hidden to output
                for (int i=0; i<weightToOut.length; i++) // i for this layer which is the hidden layer
                    for (int j=0; j<weightToOut[0].length; j++) // j for the output of this layer which is output layer
                        deltaWeightToOut [i][j] = etta*DeltaOut[j]*hidLayer[i];

                for (int i=0; i<weightToHidden.length; i++) // 'i' for this layer which is the input layer
                    for (int j=0; j<weightToHidden[0].length; j++) // 'j' for the output of this layer which is the hidden layer
                        deltaWeightToHidden [i][j] = etta*DeltaHid[j+1]*inLayer[i];
                /////////////////////////////////////////////////////
                ///////////Updating weights from the hidden layer to the output layer
                for (int i=0; i<weightToOut.length; i++) // i for this layer which is the hidden layer
                    for (int j=0; j<weightToOut[0].length; j++) // j for the output of this layer which is output layer
                        weightToOut [i][j] = weightToOut [i][j] + deltaWeightToOut[i][j];

                for (int i=0; i<weightToHidden.length; i++) // 'i' for this layer which is the input layer
                    for (int j=0; j<weightToHidden[0].length; j++) // 'j' for the output of this layer which is the hidden layer
                        weightToHidden[i][j] = weightToHidden[i][j] + deltaWeightToHidden[i][j];
                for (int k=0; k<outLayer.length; k++) // delta for output
                    myError = myError + (Math.pow((target[exampleIndex][k] - outLayer[k]), 2));
                myError = myError/outLayer.length;
                makeThreshold(exampleIndex, 0.4, 0.65);

            } // end of training each single data

            totError [T-1] = myError/inputMatrix.length;
            System.out.println("///////////////////////////\t" + T + "\tTotal error: "+ myError);
            accuracyMeasure(T-1);

        }// end of iteration
        double d =totError.length*0.95;
        for (int i= (int)d ; i<totError.length; i++)
            System.out.print("\t"+totError[i]);
        System.out.println();

    }// end of learning


    /**
     * Learn with momentum, and a matrix as input
     */

    public void learnANN(double [][] Inputs ,int iteration, double etta, double momentum)
    {
        Initialize();
        accuracyOnEpoch = new double[iteration];
        totError = new double [iteration];
        hiddenPerEpoch = new double [iteration][hidLayer.length];
        inputMatrix = addBias(Inputs);
        target = myTarget(Inputs, myData.numOfClasses);
        //////////////////////  setting momentum  Matrices ///////////////
        double [][] deltaW2HidMom = new double [inLayer.length][hidLayer.length-1];
        for (int i=0; i<deltaW2HidMom.length; i++)
            for (int j=0; j<deltaW2HidMom[0].length; j++)
                deltaW2HidMom[i][j] = 0;
        double [][] deltaW2OutMom = new double [hidLayer.length][outLayer.length];
        for (int i=0; i<deltaW2OutMom.length; i++)
            for (int j=0; j<deltaW2OutMom[0].length; j++)
                deltaW2OutMom[i][j] = 0;
        //////////////////////////////////////////////////////////
        double [] temp1 = new double [weightToHidden[0].length];
        double [] tempBias1 = new double [temp1.length+1];
        for (int T=1; T<=iteration; T++)
        {
            double myError = 0;
            for (int exampleIndex=0; exampleIndex<inputMatrix.length; exampleIndex++ ) // for each data in input
            {
                for (int i=0; i<inLayer.length; i++) // input this data+bias
                    inLayer[i] = inputMatrix[exampleIndex][i];
                ////////////////////////////////////////////////////////////
                //////////  from input to hidden

                temp1 = feedNextLayer(inLayer, weightToHidden);
                tempBias1[0]=1; // bias for hidden
                for (int i=0; i<temp1.length; i++)
                    tempBias1[i+1] = temp1[i];
                for (int i=0; i<tempBias1.length; i++)
                    hidLayer[i] = tempBias1[i];
                //////////////////////////////////   hidden got values + bias=1 (hidLayer[0]=1)
                /////////////////  Now the hidden layer must perform sigmoid
                for (int i=1; i<hidLayer.length; i++)
                    hidLayer[i] = sigmoid(hidLayer[i]);
                //////////from hidden to output////////////////////////////////////////
                outLayer = feedNextLayer(hidLayer, weightToOut);
                for (int i=0; i<outLayer.length; i++)
                    outLayer[i] = sigmoid(outLayer[i]);

                ////////////////////////////  Now there are some values in outputs
                /////////////////////////////////////////////////////////////////////
                //////////////  Error Backpropagation
                for (int k=0; k<outLayer.length; k++) // delta for output
                    DeltaOut[k] = outLayer[k]*(1 - outLayer[k])*(target[exampleIndex][k] - outLayer[k]);
                double temp=0;
                for (int h=0; h<DeltaHid.length; h++) // delta for hidden
                {
                    temp = hidLayer[h] * (1-hidLayer[h]);
                    double sum=0;
                    for (int k=0; k<outLayer.length; k++)
                        sum = sum + (weightToOut[h][k]*DeltaOut[k]);
                    temp = temp * sum;
                    DeltaHid[h] = temp;
                }
                //??????????????????  DeltaHid[0]=1;
                /////////////////////////**********************************************************
                ///////  delta for weights from hidden to output
                for (int i=0; i<weightToOut.length; i++) // i for this layer which is the hidden layer
                    for (int j=0; j<weightToOut[0].length; j++) // j for the output of this layer which is output layer
                        deltaWeightToOut [i][j] = etta*DeltaOut[j]*hidLayer[i]+ (momentum*deltaW2OutMom[i][j]);
               //////  delta for weights from input to hidden layer
                for (int i=0; i<weightToHidden.length; i++) // 'i' for this layer which is the input layer
                    for (int j=0; j<weightToHidden[0].length; j++) // 'j' for the output of this layer which is the hidden layer
                        deltaWeightToHidden [i][j] = etta*DeltaHid[j+1]*inLayer[i] + (momentum*deltaW2HidMom[i][j]);
                /////////////////////////////////////////////////////////////////////////////////////////////
                ////////////  Keep this delta weight for the next update
                for (int i=0; i<weightToOut.length; i++) // i for this layer which is the hidden layer
                    for (int j=0; j<weightToOut[0].length; j++) // j for the output of this layer which is output layer
                        deltaW2OutMom[i][j] = deltaWeightToOut [i][j];

                for (int i=0; i<weightToHidden.length; i++) // 'i' for this layer which is the input layer
                    for (int j=0; j<weightToHidden[0].length; j++) // 'j' for the output of this layer which is the hidden layer
                        deltaW2HidMom[i][j] = deltaWeightToHidden [i][j];

                /////////////////////////////////////////////////////*************************************
                ///////////Updating weights from the hidden layer to the output layer
                for (int i=0; i<weightToOut.length; i++) // i for this layer which is the hidden layer
                    for (int j=0; j<weightToOut[0].length; j++) // j for the output of this layer which is output layer
                        weightToOut [i][j] = weightToOut [i][j] + deltaWeightToOut[i][j];

                for (int i=0; i<weightToHidden.length; i++) // 'i' for this layer which is the input layer
                    for (int j=0; j<weightToHidden[0].length; j++) // 'j' for the output of this layer which is the hidden layer
                        weightToHidden[i][j] = weightToHidden[i][j] + deltaWeightToHidden[i][j];
                for (int k=0; k<outLayer.length; k++) // delta for output
                    myError = myError + (Math.pow((target[exampleIndex][k] - outLayer[k]), 2));
                myError = myError/outLayer.length;

               if (exampleIndex == 1)
                    for (int i=0; i<hiddenPerEpoch[0].length; i++)
                        hiddenPerEpoch[T-1] [i] = hidLayer[i];
                makeThreshold(exampleIndex, 0.4, 0.65);
//                for (int i=0; i<outLayer.length; i++)
//                    System.out.print(outLayer[i]);
//                System.out.println();

            } // end of training each single data

            totError [T-1] = myError/inputMatrix.length;
            accuracyMeasure(T-1);


        }// end of iteration

    }// end of learning with momentum

    /**
     * Learning with validation set
     * Inputs: number of iterations
     *         AND
     *         number of times that we want to check for errorr on validation set
     */
    public void learnAnnValid(int iteration , int numberOfChecking)
    {
        myData.divideToValid(30);
        int iterForCheckValid = iteration/(numberOfChecking+1);
        learnANN(myData.trainSet, iterForCheckValid, 0.1, 0);
        int jumpOut = 0;
        double lastError = 10;
        for (int T=iterForCheckValid; T<iteration; T = T + iterForCheckValid)
        {
            learnANN(myData.trainSet, iterForCheckValid, 0.1, 0);
            ANNTest(myData.validSet);
            if (myTotalError <= lastError)
            {
                System.out.println(myTotalError);
                lastError = myTotalError;

            }else
            {
                jumpOut++;
                if (jumpOut == 2)
                {
                    System.out.println("Berak with\t"+myTotalError +"\t in iteration: "+T + "With the corruption rate of: "+corruptionRate);
                    break;
                }
                else
                    System.out.println(myTotalError);
            }

        }

        
    }// end of learn with validation

    /**
     * This method tests the ANN on the test data set
     * that is provided by its file address as input
     */
    public void ANNTest(String fileAddress)
    {

        DataSet testSet = new DataSet(fileAddress);
        double [][] testTarget = myTarget(testSet); // Target from Test Set
        double [][] testOut = new double [testTarget.length][testTarget[0].length]; // output os network after testing an input
        testError = new double [testSet.ExampleMatrix.length]; // error for each input (instance)
        for (int sampleIndex=0; sampleIndex<testSet.ExampleMatrix.length; sampleIndex++)
            testOut [sampleIndex] = feedForward(testSet.ExampleMatrix[sampleIndex]);// getting output for each instance
        for (int sampleIndex=0; sampleIndex<testTarget.length; sampleIndex++)
        {
            double temp =0;
            for (int j=0; j<testTarget[0].length; j++) // caculating error for each outpur
                temp = temp + (Math.pow((testTarget[sampleIndex][j] - testOut[sampleIndex][j]), 2)/testTarget[0].length);
            testError [sampleIndex] = temp;
        }
        for (int i=0; i<testError.length; i++)
            myTotalError = myTotalError + testError[i];
        myTotalError = myTotalError/testError.length;
        accuracyMeasure(testOut, testTarget, 0.4, 0.6);

    }// end of testANN (with file address)

        /**
     * This method tests the ANN on the test data set
     * that is provided by a matrix as input
     */
    public void ANNTest(double [][] MTest)
    {

//        DataSet testSet = new DataSet(fileAddress);
        double [][] testTarget = myTarget(MTest, myData.numOfClasses); // Target from Test Set
        double [][] testOut = new double [MTest.length][MTest[0].length]; // output os network after testing an input
        testError = new double [MTest.length]; // error for each input (instance)
        myTotalError = 0;
        for (int sampleIndex=0; sampleIndex<MTest.length; sampleIndex++)
            testOut [sampleIndex] = feedForward(MTest[sampleIndex]);// getting output for each instance
        for (int sampleIndex=0; sampleIndex<testTarget.length; sampleIndex++)
        {
            double temp =0;
            for (int j=0; j<testTarget[0].length; j++) // caculating error for each outpur
                temp = temp + (Math.pow((testTarget[sampleIndex][j] - testOut[sampleIndex][j]), 2)/testTarget[0].length);
            testError [sampleIndex] = temp;
        }
        for (int i=0; i<testError.length; i++)
            myTotalError = myTotalError + testError[i];
        myTotalError = myTotalError/testError.length;


    }// end of testANN (with a matrix as input)

    /**
     * This method gets a vector of input and gives the output of the net
     */

    public double [] feedForward(double [] vector)
    {
        double [] temp1 = new double [weightToHidden[0].length];
        double [] tempBias1 = new double [temp1.length+1];
        inLayer[0]=1; // bias
        for (int i=1; i<inLayer.length; i++) // input this data+bias
            inLayer[i] = vector[i-1];
            ////////////////////////////////////////////////////////////
            //////////  from input to hidden

            temp1 = feedNextLayer(inLayer, weightToHidden);
            tempBias1[0]=1; // bias for hidden
            for (int i=0; i<temp1.length; i++)
                tempBias1[i+1] = temp1[i];
            for (int i=0; i<tempBias1.length; i++)
                hidLayer[i] = tempBias1[i];
            //////////////////////////////////   hidden got values + bias=1 (hidLayer[0]=1)
            /////////////////  Now the hidden layer must perform sigmoid
            for (int i=1; i<hidLayer.length; i++)
                hidLayer[i] = sigmoid(hidLayer[i]);
            //////////from hidden to output////////////////////////////////////////
            outLayer = feedNextLayer(hidLayer, weightToOut);
            for (int i=0; i<outLayer.length; i++)
                outLayer[i] = sigmoid(outLayer[i]);
        
        return outLayer;
    }// End of feed forward

    /**
     * This metod tests the ANN with corrupted data
     * It takes the range of corruption by low and high percent
     * It means that from low% to hi% of data will be corrupted
     */
     public void TestLearnAnnNoise (String fileAddress, int iteration , double RateEtta, double momentum ,int timesOfRun, int numOfValidtest)
     {

         int hi=20;
         int lo = 0;
         int noiseTimes = Math.abs(((hi-lo)/2)+1);
         noiseError = new double [noiseTimes];
         for (int i=0; i<noiseError.length; i++)
             noiseError[i] = 0;
         for (int times=0; times<timesOfRun; times++)
         {
             int index = 0;
             for (int i=lo; i<= hi; i= i+2)
             {
                 myData.corruption(i);
                 corruptionRate =i;
                 if (numOfValidtest != 0 )
                     learnAnnValid(iteration, numOfValidtest);
                 else
                     learnANN(myData.ExampleMatrix, iteration, RateEtta, momentum);

                 ANNTest(fileAddress);
                 double aveErr=0;
                 for (int k=0; k<testError.length; k++)
                     aveErr = aveErr + testError[k];
                 aveErr = aveErr / testError.length;
                 noiseError[index]=noiseError[index] + aveErr;
                 index++;
             }
         }
         for (int i=0; i<noiseError.length; i++)
             noiseError[i] = noiseError[i]/ timesOfRun;

         
         Plot myplot;
         double [] X = new double [noiseError.length];
         int per = 0;
         for (int i=0; i<X.length; i++)
         {
             X[i] = per;
             per = per +2;
         }
         myplot = new Plot("A Test Plot", X, noiseError);

     } // End of test and learn with noise

     public void makeThreshold (int dataIndex, double low, double high)
     {
         
         for (int i=0; i<outLayer.length; i++)
         {
             if (outLayer[i] < low)
                 thresholdedOutput [dataIndex][i] = 0;
             if (outLayer[i] > high)
                 thresholdedOutput [dataIndex][i] = 1;
         }

     }// end of classic make threshold




     public void accuracyMeasure(int epochNum)
     {
         double countFalse = 0;
         for (int i=0; i<target.length; i++)
         {
             for (int j=0; j<target[0].length; j++)
                 if (thresholdedOutput[i][j] != target[i][j])
                 {
                     countFalse++;
                     break;
                 }
         }
         double countTrue = target.length - countFalse;
         accuracyOnEpoch[epochNum] = countTrue/target.length;
         
     }// end of accuracy on train
     public void accuracyMeasure(double [][] Out, double [][]Target1, double low, double high)
     {
         double countFalse = 0;
         for (int i=0; i<Target1.length; i++)
         {
             for (int j=0; j<Target1[0].length; j++)
             {
                 if ((Target1 [i][j] == 0)&& (Out[i][j]>low))
                 {
                     countFalse++;
                     break;
                 }
                 if ((Target1 [i][j] == 1)&& (Out[i][j]<high))
                 {
                     countFalse++;
                     break;
                 }
             }

         }
         double countTrue = Target1.length - countFalse;
         accuracy= countTrue/Target1.length;
         System.out.println("The accuracy on test: "+accuracy);

     }

     /**
      * This method runs the neural network learning for Iris data set
      * and plot hidden values.
      */

     public void trainAndPlotIdentity(int Epoch, double Etta, double Momentum)
     {
         if (myData.DataName.equalsIgnoreCase("Identity"))
         {
            learnANN(myData.ExampleMatrix,Epoch, Etta, Momentum);
            System.out.println("The total error is: "+ totError[totError.length-1]);
            Plot myplot;
            double[] X = new double [hiddenPerEpoch.length];
            double [] Y = new double [X.length];

            for (int i=0; i<X.length; i++)
            {
                X[i]=i;
                Y[i] = hiddenPerEpoch[i][1];

            }

            myplot = new Plot("A Test Plot", X, Y);
            for (int i=0; i<X.length; i++)
                Y[i] = hiddenPerEpoch[i][2];
            myplot.addLine(Y);
            for (int i=0; i<X.length; i++)
                Y[i] = hiddenPerEpoch[i][3];
            myplot.addLine(Y);
         }

     } // end of traing and plot Iris

     /**
      * This method will learn the ANN and plot errors in each epoch
      * Additionally, it will test the ANN
      */
     public void LearnPlotTest(double [][] Matrix, String TestSet, int epoch, double Etta, double momentum)
     {
         learnANN(Matrix,epoch, Etta, momentum);
         double [] x = new double [totError.length];
         for (int i=0; i<x.length; i++)
             x[i]=i;
         Plot plot2 = new Plot("Total error fo each iteration", x, totError);
         ANNTest(TestSet);
//         double [] x1 = new double [testError.length];
//         for (int i=0; i<x1.length; i++)
//             x1[i]=i;
//         Plot plot1 = new Plot("Error on test data: ", x1, testError);
         System.out.println("The final error on test set: " + myTotalError );
     }// End of Learn-Plot-Test

     /**
      * This method plots the hidden units
      */
     public void poltHidden()
     {
         Plot myplot;
         double[] X = new double [hiddenPerEpoch.length];
         double [] Y = new double [X.length];

         for (int i=0; i<X.length; i++)
         {
             X[i]=i;
             Y[i] = hiddenPerEpoch[i][1];

         }

         myplot = new Plot("Hidden unit values", X, Y);
         for (int h=2; h<hiddenPerEpoch[0].length; h++)
         {
             for (int i=0; i<X.length; i++)
             Y[i] = hiddenPerEpoch[i][h];
             myplot.addLine(Y);

         }


     }// end of plot hidden
     /**
      * Plot accuracy
      */
     public void poltAccuracy()
     {
         Plot myplot;
         double[] X = new double [accuracyOnEpoch.length];
         double [] Y = new double [X.length];

         for (int i=0; i<X.length; i++)
         {
             X[i]=i+1;
             Y[i] = accuracyOnEpoch[i];

         }

         myplot = new Plot("Hidden unit values", X, Y);
         

     }



    public void showItems()
    {

    }

}
