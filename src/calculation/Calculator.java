package calculation;

import data.InputData;
import tree.Node;

import java.util.*;

/**
 * Created by Sima on 10/1/2016.
 */
public class Calculator {
    /**
     * maximum information gain
     */
    public static float maxInfoGain;
    /**
     * current data
     */
    public static String[][] currTable;

    public static int totalRows;
    public static int numInstances;
    /**
     * remaining attributes to be tested for further growth of the tree
     */
    public static String[] remAttr;
    /**
     * Best attribute to divide the tree with
     */
    public  static String bestDivAttr;
    /**
     * id of the node just added
     */
    public static int nodeId = 1;


    /**
     * Creates child nodes based on the given node and maximum information gain
     * @param node the current node
     * @return children of the given node
     */
    public static ArrayList calculate(Node node){

        ArrayList<Node> nodeList = null;
        currTable = node.getDataObj().getData();
        totalRows = currTable.length;
        numInstances = totalRows;
        remAttr = node.getRemAttr();

        if (node != null ) {
            if (node.isLeaf()) return null;
            if (node.getDataObj().getData().length ==1){
                node.setValue(node.getDataObj().getData()[0][InputData.getNumAttr()-1]);
            }else {
                bestDivAttr = calBestDivAttribute();
                node.setDividingAttribute(bestDivAttr);
                nodeList = new ArrayList<>(finalDivideData(node));
                String[] childAttr = buildchildRemAttr();
                for (Node child : nodeList) {
                    if (childAttr.length != 0)
                        child.setRemAttr(childAttr);
                     else
                        child.setValue(InputData.getTrainData()[0][InputData.getNumAttr() - 1]);
                    node.addChild(child, nodeId);
                    nodeId++;
                }
            }
        }
        return nodeList;
    }

    /**
     * Calculates the best attribute to divide the tree further with
     * @return the best attribute to divide the tree with
     */
    public static String calBestDivAttribute(){
        float temp ;
        String bestAttr = remAttr[0];
        for(int i=0; i<remAttr.length-1; i++){
            temp = calInfoGain(remAttr[i]);
            if(temp > maxInfoGain){
                maxInfoGain = temp;
                bestAttr = remAttr[i];
            }
        }
        return bestAttr;
    }

    /**
     * Calculates the information gain if the current node is divided based on the given attribute
     * @param s an attribute from array of attributes
     * @return information gain
     */
    public static float calInfoGain(String s){
        float infoGain = (calEntropy(currTable))[0] - calChildrenEntropy(divideTable(s));
        return  infoGain;
    }

    /**
     * Calculates the entropy of children of the current node
     * @param subData data inside each of the node children
     * @return average entropy
     */
    public static float calChildrenEntropy(Data... subData){
        float avgEntropy =0;
        for(Data data: subData){
            avgEntropy += calEntropy(data.getData())[0]*calEntropy(data.getData())[1]/numInstances;
        }
        return avgEntropy;
    }

    /**
     * Calculates the entropy of the given data
     * @param table a 2D array of Strings
     * @return entropy of the data
     */
    public static float[] calEntropy(String[][] table){
        int numRows = table.length;
        float entropy =0;
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(int i=0; i<numRows; i++){
            String temp = table[i][InputData.getNumAttr()-1];
            if(map.containsKey(temp))
                map.put(temp, (map.get(temp)+1));
            else map.put(temp, 1);
        }
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            double pi =  map.get(iterator.next())/(numRows* 1.00);
            entropy += -1* pi * Math.log(pi)/Math.log(2);
        }
        return new float[]{entropy, numRows};
    }


    /**
     * divides the given data table into two or more tables
     * based on the values of the given attr in the table
     * @param attr the attribute based on which the table is being divided
     */
    public static Data[] divideTable(String attr){

        /**
         * attrIndex index of the given attribute in the attr array
         */
        int attrIndex = InputData.getAttrIndex(attr);


        /**
         * number of different values af the given attribute
         */
        int numValues =  InputData.anAttrVals(attrIndex).length;

        /**
         * tables resulted from the division of the given data table
         */
        List<String[]>[] subTables = new ArrayList[numValues];
        /**
         * initialize subTables list
         */
        for(int i=0; i<numValues; i++){
            subTables[i] = new ArrayList();
        }


        /**
         * Assign each row of the original dataset to the corresponding sub-datasets
         */
        for(int i=0; i<totalRows; i++){
            for(int j=0; j<numValues; j++){
                if(currTable[i][attrIndex].equals(InputData.anAttrVals(attrIndex)[j]))
                    subTables[j].add(currTable[i]);
            }
        }
        Data[] SubData = new Data[numValues];
        for(int k=0; k<numValues; k++)
            SubData[k] = new Data( subTables[k].toArray(new String[subTables[k].size()][]));
        return SubData;
    }

    /**
     * Creates remaining attribute arrays for the children of the current node
     * @return remaining attribute array
     */
    public static String[] buildchildRemAttr(){
        String[] childRemAttr = new String[remAttr.length-1];
        if (remAttr.length>1) {
            for (int i = 0; i < remAttr.length; i++) {
                if (i < InputData.getAttrIndex(bestDivAttr))
                    childRemAttr[i] = InputData.getAttrs()[i];
                else if (i > InputData.getAttrIndex(bestDivAttr))
                    childRemAttr[i - 1] = InputData.getAttrs()[i];
            }
        }
        return childRemAttr;
    }


    /**
     * Divides the data contained in the node to subdata based on the highest information gain
     * @param node the current node
     * @return final list of children for ths node
     */
    public static ArrayList<Node> finalDivideData(Node node){
        Data[] subData = divideTable(bestDivAttr);
        ArrayList<Node> list = new ArrayList<>(subData.length);
        for (Data data: subData){
            if (data.getData().length !=0) {
                data.setValue(data.getData()[0][InputData.getAttrIndex(bestDivAttr)]);
                Node childNode = new Node(data);
                list.add(childNode);
                childNode.setEdgeValue(node, data.getValue());
            }
        }
        reset();
        return list;
    }

    /**
     * resets the maximum information gain of the class
     */
    public static void reset(){
        maxInfoGain = 0;
    }

}
