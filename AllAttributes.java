/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ann;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 *
 */
public class AllAttributes {


    public String [][] AttValues;

    public AllAttributes(){}


    public AllAttributes(String FileAddress)
    {
        if (FileAddress.contains("tennis"))
        {
            AttValues = new String [5][5];
             for (int i =0; i<5; i++)
                for (int j=0; j<5; j++)
                   AttValues[i][j] = "NOT";
            RandomAccessFile F_att;
            LinkedList<String> tempList = new LinkedList<String>();


            try
            {
                F_att = new RandomAccessFile(FileAddress, "rw");

               try
                {
                    String str;
                    StringTokenizer ST;
                    str = F_att.readLine();
                    int AttNum = 0;
                    while (!str.equals("FINISHED"))
                    {

                        if (str.equals(""))
                        {
                            str = F_att.readLine();
                            continue;
                        }
                        else
                        {
                            ST = new StringTokenizer(str, " "); // Devided by ONE blank space
                            while (ST.hasMoreTokens())
                            {
                                 String tempStr = ST.nextToken();
                                 tempList.addLast(tempStr);
                             }
                             int listSize = tempList.size();
                             String [] AttValue =new String[listSize];
                            Integer numOfValues = -1;
                            for (int i=0; i<listSize; i++)
                            {
                                AttValue[i] = tempList.removeFirst();
                                numOfValues++;
                            }

                    /////////////////////////////////////
                            for(int i=0;i<AttValue.length;i++)
                                AttValues[AttNum][i] = AttValue[i];

                            AttValues[AttNum][4] = numOfValues.toString();

                        }

                    AttNum ++;
                    str = F_att.readLine();
                }

                } catch (IOException e)
                {
                    System.out.println("IO exception");
                }

            } catch (FileNotFoundException e)
            {
                System.out.println("The file cannot be found!");
            }
//
//        for (int i=0; i<5; i++)
//        {
//            for (int j=0; j<5; j++)
//                System.out.print("  "+AttValues[i][j]);
//            System.out.println();
//        }
        } // End of tennis
        if (FileAddress.contains("iris"))
        {
            AttValues = new String [5][5];
             for (int i =0; i<AttValues.length; i++)
                for (int j=0; j<AttValues[0].length; j++)
                   AttValues[i][j] = "NOT";
            RandomAccessFile F_att;
            LinkedList<String> tempList = new LinkedList<String>();


            try
            {
                F_att = new RandomAccessFile(FileAddress, "rw");

               try
                {
                    String str;
                    StringTokenizer ST;
                    str = F_att.readLine();
                    int AttNum = 0;
                    while (!str.equals("FINISHED"))
                    {

                        if (str.equals(""))
                        {
                            str = F_att.readLine();
                            continue;
                        }
                        else
                        {
                            ST = new StringTokenizer(str, " "); // Devided by ONE blank space
                            while (ST.hasMoreTokens())
                            {
                                 String tempStr = ST.nextToken();
                                 tempList.addLast(tempStr);
                             }
                             int listSize = tempList.size();
                             String [] AttValue =new String[listSize];
                            Integer numOfValues = -1;
                            for (int i=0; i<listSize; i++)
                            {
                                AttValue[i] = tempList.removeFirst();
                                numOfValues++;
                            }

                    /////////////////////////////////////
                            for(int i=0;i<AttValue.length;i++)
                                AttValues[AttNum][i] = AttValue[i];

                            AttValues[AttNum][AttValues[0].length-1] = numOfValues.toString();

                        }

                    AttNum ++;
                    str = F_att.readLine();
                }

                } catch (IOException e)
                {
                    System.out.println("IO exception");
                }

            } catch (FileNotFoundException e)
            {
                System.out.println("The file cannot be found!");
            }

//        for (int i=0; i<AttValues.length; i++)
//        {
//            for (int j=0; j<AttValues[0].length; j++)
//                System.out.print("  "+AttValues[i][j]);
//            System.out.println();
//        }
        } // End of Iris
    }





public void showattValues(String str)
{
    if (str.equalsIgnoreCase("Tennis"))
    {
        System.out.println(" [0] Outlook values: " + "\tSunny: " +  0 +"\tOvercast: "+ 1 + "\tRain" + 2);
        System.out.println(" [1] Temperature values: " + "\tHot: " +  0 +"\tMild: "+ 1 + "\tCool" + 2);
        System.out.println(" [2] Humidity values: " + "\tHigh: " +  0 +"\tNormal: "+ 1 );
        System.out.println(" [3] Wind values: " + "\tWeak: " +  0 +"\tStrong: "+ 1 );
        System.out.println(" [4] PlayTennis " + "\tNo: " +  0 +"\tYes: "+ 1 );
    }
    if (str.equalsIgnoreCase("Iris"))
    {
        System.out.println(" [0] sepal-length: continuous");
        System.out.println(" [1] sepal-width: Continuous");
        System.out.println(" [2] petal-length: continuous");
        System.out.println(" [3] petal-width continuous ");
        System.out.println(" [4] Iris (type)" +   "\tsetosa: " +  0 +"\tversicolor: "+ 1 + "\tvirginica" + 2);
    }
}



}
