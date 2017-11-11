/*
 *
 */

package ann;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Random;
import java.util.StringTokenizer;
/**
 *
 */


public class DataSet {
    
    AllAttributes attNameValues;
    double [] [] ExampleMatrix;
    int numOfClasses;
    String DataName;
    double [][] trainSet;
    double [][] validSet;
    double [][] tempMatrix;
    
    public DataSet()
    {
    }

/**
 * This is a constructor in order to make a matrix from dataset
 * The dataset is refering as a file address in input
 */
    public DataSet(String FileAddress)
    {
        
        if (FileAddress.contains("tennis"))
        {
            DataName = "Tennis";
            attNameValues = new AllAttributes("./DataSet/tennis-attr.txt");
            numOfClasses = Integer.parseInt(attNameValues.AttValues[attNameValues.AttValues.length-1][attNameValues.AttValues[0].length-1]); // Integer of the last row and last column
            //////////////////// getting the number of instances and attributes
            int instanceNum = 0;
            int NumOfAtt = 0;
            RandomAccessFile File;
            LinkedList<String> tempList = new LinkedList<String>();
            String str;
            StringTokenizer ST;

            try
            {
                File = new RandomAccessFile(FileAddress, "rw");

               try
                {
                    str = File.readLine();

                  while (!str.equals("FINISHED"))
                  {
                      if (str.equals(""))
                      {
                        str = File.readLine();
                        continue;
                       }
                       else
                       {    instanceNum ++;
                            ST = new StringTokenizer(str, " "); // Devided by ONE blank space
                            NumOfAtt = ST.countTokens();
                            str = File.readLine();
                       }
                  }

                } catch (IOException e)
                {
                    System.out.println("IO exception");
                }

            } catch (FileNotFoundException e)
            {
                System.out.println("The file cannot be found!");
            }
//            System.out.println(instanceNum + "\t" + NumOfAtt);
            /////////////////  Number of instance and number of attributes are obtained
            /////////////*************************///////////////
            ExampleMatrix = new double [instanceNum][NumOfAtt];
            int instanceIndex=0;
            try
            {
                File = new RandomAccessFile(FileAddress, "rw");
                try
                {
                    str = File.readLine();
                    while (!str.equals("FINISHED"))
                    {
                        if (str.equals(""))
                        {
                            str = File.readLine();
                            continue;
                        }
                        else
                        {
                            ST = new StringTokenizer(str, " ");
                            while (ST.hasMoreTokens())
                            {
                                String tempStr = ST.nextToken();
                                tempList.addLast(tempStr);
                            }
                            int listSize = tempList.size();
                            String [] AttValue =new String[listSize];
                            for (int i=0; i<listSize; i++)
                            {
                                AttValue[i] = tempList.removeFirst();
                            }

                            for(int i=0;i<AttValue.length;i++) /// Switch string values to double
                            {
                                if (AttValue[i].equalsIgnoreCase("Sunny") || AttValue[i].equalsIgnoreCase("Hot") ||
                                        AttValue[i].equalsIgnoreCase("High") || AttValue[i].equalsIgnoreCase("Weak") 
                                        || AttValue[i].equalsIgnoreCase("No"))
                                {
                                    ExampleMatrix[instanceIndex][i] = 0;
                                    continue;
                                }
                                if (AttValue[i].equalsIgnoreCase("Overcast") || AttValue[i].equalsIgnoreCase("Mild") ||
                                        AttValue[i].equalsIgnoreCase("Normal") || AttValue[i].equalsIgnoreCase("Strong")
                                        || AttValue[i].equalsIgnoreCase("Yes"))
                                {
                                    ExampleMatrix[instanceIndex][i] = 1;
                                    continue;
                                }
                                if (AttValue[i].equalsIgnoreCase("Rain") || AttValue[i].equalsIgnoreCase("Cool"))
                                {
                                    ExampleMatrix[instanceIndex][i] = 2;
                                    continue;
                                }
                            }
                                

                        }
                        instanceIndex++;
                        str = File.readLine();
                    }

            } catch (IOException e)
            {
                System.out.println("IO exception");
            }

        } catch (FileNotFoundException e)
        {
            System.out.println("The file cannot be found!");
        }
//        for (int i=0; i<ExampleMatrix.length; i++)
//        {
//            for (int j=0; j<ExampleMatrix[0].length; j++)
//            {
//                System.out.print(ExampleMatrix[i][j] + "\t");
//            }
//            System.out.println();;
//        }

       } // End of "If tennis"
   /////////////////////////////////////////////////////////////
   //////////////////***********//////////////////////////////
   ///////////////   Work on Iris
        if (FileAddress.contains("iris"))
        {
            DataName = "Iris";
            attNameValues = new AllAttributes("./DataSet/iris-attr.txt");
            numOfClasses = Integer.parseInt(attNameValues.AttValues[attNameValues.AttValues.length-1][attNameValues.AttValues[0].length-1]); // Integer of the last row and last column
            //////////////////// getting the number of instances and attributes
            int instanceNum = 0;
            int NumOfAtt = 0;
            RandomAccessFile File;
            LinkedList<String> tempList = new LinkedList<String>();
            String str;
            StringTokenizer ST;

            try
            {
                File = new RandomAccessFile(FileAddress, "rw");

               try
                {
                    str = File.readLine();

                  while (!str.equals("FINISHED"))
                  {
                      if (str.equals(""))
                      {
                        str = File.readLine();
                        continue;
                       }
                       else
                       {    instanceNum ++;
                            ST = new StringTokenizer(str, " "); // Devided by ONE blank space
                            NumOfAtt = ST.countTokens();
                            str = File.readLine();
                       }
                  }

                } catch (IOException e)
                {
                    System.out.println("IO exception");
                }

            } catch (FileNotFoundException e)
            {
                System.out.println("The file cannot be found!");
            }
//            System.out.println(instanceNum + "\t" + NumOfAtt);
            /////////////////  Number of instance and number of attributes are obtained
            /////////////*************************///////////////
            ExampleMatrix = new double [instanceNum][NumOfAtt];
            int instanceIndex=0;
            try
            {
                File = new RandomAccessFile(FileAddress, "rw");
                try
                {
                    str = File.readLine();
                    while (!str.equals("FINISHED"))
                    {
                        if (str.equals(""))
                        {
                            str = File.readLine();
                            continue;
                        }
                        else
                        {
                            ST = new StringTokenizer(str, " ");
                            while (ST.hasMoreTokens())
                            {
                                String tempStr = ST.nextToken();
                                tempList.addLast(tempStr);
                            }
                            int listSize = tempList.size();
                            String [] AttValue =new String[listSize];
                            for (int i=0; i<listSize; i++)
                            {
                                AttValue[i] = tempList.removeFirst();
                            } /////////  Now, one instance has been read

                            for(int i=0;i<AttValue.length-1;i++) /// Switch string values to double
                            {
                                ExampleMatrix[instanceIndex][i] = Double.parseDouble(AttValue[i]);
                            }

                            if (AttValue[AttValue.length-1].equalsIgnoreCase("Iris-setosa"))
                            {
                                ExampleMatrix[instanceIndex][AttValue.length-1] = 0;
                            }
                            if (AttValue[AttValue.length-1].equalsIgnoreCase("Iris-versicolor"))
                            {
                                ExampleMatrix[instanceIndex][AttValue.length-1] = 1;
                            }
                            if (AttValue[AttValue.length-1].equalsIgnoreCase("Iris-virginica"))
                            {
                                ExampleMatrix[instanceIndex][AttValue.length-1] = 2;
                            }

                        }
                        instanceIndex++;
                        str = File.readLine();
                    }

            } catch (IOException e)
            {
                System.out.println("IO exception");
            }

        } catch (FileNotFoundException e)
        {
            System.out.println("The file cannot be found!");
        }
       } // End of "If iris"


        if (FileAddress.contains("ident")) ////   doing "Identity"
        {
            DataName = "Identity";
            //////////////////// getting the number of instances and attributes
            int instanceNum = 0;
            int NumOfAtt = 0;
            RandomAccessFile File;
            LinkedList<String> tempList = new LinkedList<String>();
            String str;
            StringTokenizer ST;

            try
            {
                File = new RandomAccessFile(FileAddress, "rw");

               try
                {
                    str = File.readLine();

                  while (!str.equals("FINISHED"))
                  {
                      if (str.equals(""))
                      {
                        str = File.readLine();
                        continue;
                       }
                       else
                       {    instanceNum ++;
                            ST = new StringTokenizer(str, " "); // Devided by ONE blank space
                            NumOfAtt = ST.countTokens();
                            str = File.readLine();
                       }
                  }

                } catch (IOException e)
                {
                    System.out.println("IO exception");
                }

            } catch (FileNotFoundException e)
            {
                System.out.println("The file cannot be found!");
            }
       
            /////////////////  Number of instance and number of attributes are obtained
            /////////////*************************///////////////
            numOfClasses = instanceNum;
            ExampleMatrix = new double [instanceNum][NumOfAtt];
            int instanceIndex=0;
            try
            {
                File = new RandomAccessFile(FileAddress, "rw");
                try
                {
                    str = File.readLine();
                    while (!str.equals("FINISHED"))
                    {
                        if (str.equals(""))
                        {
                            str = File.readLine();
                            continue;
                        }
                        else
                        {
                            ST = new StringTokenizer(str, " ");
                            while (ST.hasMoreTokens())
                            {
                                String tempStr = ST.nextToken();
                                tempList.addLast(tempStr);
                            }
                            int listSize = tempList.size();
                            String [] AttValue =new String[listSize];
                            for (int i=0; i<listSize; i++)
                            {
                                AttValue[i] = tempList.removeFirst();
                            } /////////  Now, one instance has been read

                            for (int i=0; i<AttValue.length; i++)
                            {
                                ExampleMatrix[instanceIndex][i] = Double.parseDouble(AttValue[i]);
                            }

                        }
                        instanceIndex++;
                        str = File.readLine();
                    }

            } catch (IOException e)
            {
                System.out.println("IO exception");
            }

        } catch (FileNotFoundException e)
        {
            System.out.println("The file cannot be found!");
        }
       
       } // End of "If identity"
  //System.out.println(numOfClasses);
        tempMatrix = new double [ExampleMatrix.length][ExampleMatrix[0].length];
        for (int i=0; i<tempMatrix.length; i++)
            for (int j=0; j<tempMatrix[0].length; j++)
                tempMatrix[i][j] = ExampleMatrix[i][j];
    }// End of reading data

    /**
     * This method will normalize the data
     */
    public void Normalize()
    {
        double maxValue = -10;
        double minValue = 100;
        double [] maxValuesVector = new double [ExampleMatrix[0].length-1];
        double [] minValuesVector = new double [ExampleMatrix[0].length-1];

        ////////   finding max and min

        for (int j=0; j<ExampleMatrix[0].length -1; j++ )
        {
            maxValue = ExampleMatrix[0][j];
            minValue = ExampleMatrix[0][j];
            for (int i=0; i<ExampleMatrix.length;i++)
            {
                if (ExampleMatrix[i][j] >= maxValue)
                    maxValue = ExampleMatrix[i][j];
                if (ExampleMatrix[i][j] < minValue)
                    minValue = ExampleMatrix[i][j];
            } //// Max and Min were found for one specific attribute
            maxValuesVector[j] = maxValue;
            minValuesVector [j] = minValue;
        }////  End of finding Max and Min for all attributes
        //////////////////////   Normalization
        for (int i=0; i<ExampleMatrix.length; i++ )
        {

            for (int j=0; j<ExampleMatrix[0].length -1 ;j++)
            {
                double xi = ExampleMatrix[i][j];
                xi = (xi - minValuesVector[j])/(maxValuesVector[j] - minValuesVector[j]);
                ExampleMatrix [i][j] = xi;
            }
        } // Data has been normalized

    }// end of normalizing

    public double myRandom()
    {
        Random randomNumbers = new Random();
        double random1= randomNumbers.nextDouble();
        return random1;

    }

    public int myRandomInt(int n)
    {
        Random randomNumbers = new Random();
        int random1= randomNumbers.nextInt(n);
        return random1;

    }

    /**
     * This method will corrupt the data set with noise
     */
     public void corruption(int percent)
     {
         for (int i=0; i<tempMatrix.length; i++)
                for (int j=0; j<tempMatrix[0].length; j++)
                    tempMatrix[i][j] = ExampleMatrix[i][j];
         if (percent != 0)
         {
             
             int corruptSize = ExampleMatrix.length *percent/100;
             int numOfSamples = ExampleMatrix.length;
             int [] choosed = new int [ExampleMatrix.length];
             for (int i=0; i<choosed.length; i++)
                 choosed[i] = 0;

             int count = 0;
             int Rand = 0;
             while (count<corruptSize)
             {
                 Rand = myRandomInt(numOfSamples-1);
                 if (choosed[Rand] == 0)
                 {
                     changeClass(Rand);
                     choosed[Rand] =1;
                     count++;
                 }

             }
         }

     }// End of corruption

     /**
      * This method change the class of an example with index of Xi
      */
     public void changeClass(int Xi)
     {
         int classIndex = ExampleMatrix[0].length-1; // The last cell in each row (example)
         int myClass = (int) ExampleMatrix[Xi][classIndex];
         if (numOfClasses == 2)
         {
             if (tempMatrix[Xi][classIndex] == 0)
                 tempMatrix[Xi][classIndex] = 1;
             if (tempMatrix[Xi][classIndex] == 1)
                 tempMatrix[Xi][classIndex] = 0;
         }else {
             int RandInt;
             int changed =0;
             while (changed == 0)
             {
                 RandInt = myRandomInt(numOfClasses-1);
                 if (RandInt != myClass)
                 {
                     tempMatrix[Xi][classIndex]= RandInt;
                     changed = 1;
                 }
             }

         }

     }// chande class

     /**
      * This method devides the data set to training (the first 70%) and (30%) validation sets
      * And puts them in traininSet and validSet.
      */
     public void divideToValid ()
     {
         double d = ExampleMatrix.length * 0.7;
         int trainSize = (int) d;
         int validSize = ExampleMatrix.length - trainSize;
         trainSet = new double [trainSize][ExampleMatrix[0].length];
         for (int i=0; i<trainSet.length; i++)
             for (int j=0; j<trainSet[0].length; j++)
                 trainSet[i][j] = tempMatrix[i][j];
         validSet = new double [validSize][ExampleMatrix[0].length];
         for (int i=0; i<validSet.length; i++)
             for (int j=0; j<validSet[0].length; j++)
                 validSet[i][j] = tempMatrix[i+trainSize][j];

     }// end of devide to get validation set

     /**
      * This method devides the data set to training (1-percent%) and (percent%) validation sets
      * And puts them in traininSet and validSet.
      * The variable "percent" means the percent of data that we want to be in the validation set
      */
     public void divideToValid (int percent)
     {
         double P= percent;
         double d = ExampleMatrix.length * (1-(P / 100)) ;
         int trainSize = (int) d;
         int validSize = ExampleMatrix.length - trainSize;
         trainSet = new double [trainSize][ExampleMatrix[0].length];
         for (int i=0; i<trainSet.length; i++)
             for (int j=0; j<trainSet[0].length; j++)
                 trainSet[i][j] = tempMatrix[i][j];
         validSet = new double [validSize][ExampleMatrix[0].length];
         for (int i=0; i<validSet.length; i++)
             for (int j=0; j<validSet[0].length; j++)
                 validSet[i][j] = tempMatrix[i+trainSize][j];

     }// end of devide to get validation set with percent of total data



    public void testMethod ()
    {
        divideToValid();
        for (int i=0; i<trainSet.length; i++){
            for (int j=0; j<trainSet[0].length; j++)
                System.out.print(i+1 +" Train:\t"+trainSet[i][j]);
            System.out.println();
        }
        for (int i=0; i<validSet.length; i++){
            for (int j=0; j<validSet[0].length; j++)
                System.out.print(i+1 +" valid:\t"+validSet[i][j]);
            System.out.println();
        }


//        attNameNValues.showattValues("Ten");
//        attNameNValues = new AllAttributes("./DataSet/iris-attr.txt");
//        DataSet("./DataSet/iris-test.txt");
//        Normalize();

    }

}
