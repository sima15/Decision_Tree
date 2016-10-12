package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Sima on 9/30/2016.
 */
public class InputData {

    /**
     * attributes
     */
    public static String[] attr;
    /**
     * number of different attributes
     */
    public static int numAttr ;
    public static int numRows;

    /**
     * original data
     */
    public static String[][] data ;
    /**
     * training data 80% of the original data
     */
    public static String[][] trainData ;

    /**
     * testing data 20% of the original data
     */
    public static String[][] testData;

    /**
     * contains the original [1, -1] values in the test data set
     */
    public static List<String> realOutput;
    /**
     * Different values for the attributes in the dataset
     */
    public static String[][] attrValues;


    public static Set<String> set = new HashSet<>();

    static BufferedReader br;
    static StringTokenizer st;



    public static void readFile(int fileName){

        /**
         * Sample data set
         */
        if(fileName ==0) {
            attr = new  String[] {"Alt", "Bar",	"Fri",	"Hun",	"Pat",	"Price",	"Rain",	"Res", "Type", 	"Est",	"GoalWillWait"} ;
            numAttr = attr.length;
            numRows = 12;
            data = new String[numRows][numAttr];
            attrValues = new String[numAttr][];

            /**
             * Read data from the specified file
             */
            try {
                br = new BufferedReader(new FileReader("data\\Table.txt"));
                br.readLine();
                br.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < numRows; i++) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j <= numAttr; j++) {
                    if (j == 0) {
                        st.nextToken();
                        continue;
                    }
                    data[i][j - 1] = st.nextToken();
                }
            }
        }

        /**
         * Schilling data set
         */
        else if (fileName == 1){
            attr = new  String[] {"1",	"2",	"3",	"4",	"5",	"6",	"7",	"8", "9"} ;
            numAttr = attr.length;
            numRows = 3272;
            data = new String[numRows][numAttr];
            attrValues = new String[numAttr][];
            int rowIndex =0;

            /**
             * Read data from the specified file
             */
            try {
                br = new BufferedReader(new FileReader("data\\schillingData.txt"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String line = "";
            try {
                while ((line = br.readLine()) != null){
                    st = new StringTokenizer(line, ",");
                    String temp = st.nextToken();
                    for(int s=0; s<temp.length(); s++){
                        data[rowIndex][s] = String.valueOf(temp.charAt(s));
                    }
                    data[rowIndex][numAttr-1] = st.nextToken();
                    rowIndex++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Creates an array for every attribute
         * Fills it with different possible values of it
         */
        for (int j = 0; j < numAttr; j++) {
            for (int i = 0; i < numRows; i++) {
                set.add(data[i][j]);
            }
            attrValues[j] = new String[set.size()];
            Iterator iterator = set.iterator();
            int counter = 0;
            while (iterator.hasNext()) {
                attrValues[j][counter] = iterator.next().toString();
                counter++;
            }
            set.clear();
        }
        createTestNTraining();
    }

    /**
     * Creates "twenty" distinct random numbers for creating the test data
     * @return an array of different random numbers
     */
    public static ArrayList<Integer> getRandomNums(){
        int eighty = (int) (0.8*numRows);
        int twenty = numRows-eighty;
        ArrayList<Integer> randoms = new ArrayList<>(twenty);
        for(int i=0; i<twenty; i++){
            int val = (int) (Math.random()*(numRows-1));
            while (randoms.contains(val)) val = (int) (Math.random()*(numRows-1));
            randoms.add(val);
        }
        Set<Integer> set = new HashSet<>(twenty);
        set.addAll(randoms);
        ArrayList<Integer> randomVals = new ArrayList<>();
        randomVals.addAll(set);
        return  randomVals;
    }


    /**
     * Creates two different data sets for training and testing the tree
     */
    public static void createTestNTraining(){

        int eighty = (int) (0.8*numRows);
        int twenty = numRows-eighty;
        trainData = new String[eighty][];
        testData = new String[twenty][];
        realOutput = new ArrayList<>(twenty);
        List<String[]>[] subTables = new ArrayList[2];
        /**
         * initialize subTables list
         */
        for(int i=0; i<2; i++){
            subTables[i] = new ArrayList();
        }
        ArrayList<Integer> randomVals = getRandomNums();
        for(int i=0; i<numRows; i++){
            if(randomVals.contains(i)) subTables[0].add(data[i]);
            else subTables[1].add(data[i]);
        }
        for(int k=0; k<twenty; k++) {
            testData[k] = new String[numAttr];
            trainData[k] = new String[numAttr];
        }
        for(int k=twenty; k<eighty; k++) {
            trainData[k] = new String[numAttr];
        }
        testData = subTables[0].toArray(new String[subTables[0].size()][]);
        trainData = subTables[1].toArray(new String[subTables[1].size()][]);
        for(int j=0; j<subTables[0].size(); j++){
            testData[j] = subTables[0].get(j);
            realOutput.add(testData[j][numAttr-1]);
            testData[j][numAttr-1] = "";
        }
    }


    /**
     *
     * @return number of available attributes
     */
    public static int getNumAttr(){
        return attr.length;
    }
    public static String[] getAttrs(){
        return attr;
    }

    public static String[][] getOriginalData(){
        return data;
    }
    public static String[][] getTrainData(){
        return  trainData;
    }

    public static String[][] getTestData(){
        return testData;
    }

    /*public static String[] anAttrVals(String attribute){
    *//*    for(int i=0; i<numAttr; i++){
            if(attribute.equals(attr[i])) {
                return attrValues[i];
            }
        }
        System.out.println("Invalid attribute input");
        return  null;
    }*/

    /**
     *
     * @param index index of the attribute in the attributes array
     * @return an array of possible values of the needed attribute
     */
    public static String[] anAttrVals(int index){
        if (index < numAttr)
            return attrValues[index];
        else
            System.out.println("Invalid attribute input");
        return  null;
    }

    /**
     *
     * @param s name of an attribute
     * @return index of the attribute in the attributes array
     */
    public  static int  getAttrIndex(String s){
        for(int i=0; i<numAttr; i++){
            if (s.equals(attr[i])) return i;
        }
        return -1;
    }
}


