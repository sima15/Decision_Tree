package tree;

import calculation.Calculator;
import data.InputData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sima on 10/1/2016.
 */
public class Tree {

    /**
     * A list of all nodes in this tree
     */
    ArrayList<Node> nodeList = new ArrayList<>();
    int listSize = 1;

    /**
     * Builds a decision tree with the given node as the root
     * @param node the given root node
     * @return the root of the constructed decision tree
     */
    public Node buildTree(Node node){

        int i=0;
        nodeList.add(node);
        while (i<listSize){
            ArrayList<Node> tempList = Calculator.calculate(nodeList.get(i));
            if (tempList == null || tempList.isEmpty() ) {
                i++;
                continue;
            }
            nodeList.addAll(tempList);
            i++;
            listSize = nodeList.size();
        }
        return node;
    }

    /**
     * Prints the tree
     * @param node the node being printed
     * @param appender A symbol to indent the children of nodes with
     */
    public void printTree(Node node, String appender){

        System.out.print(appender + node.getID()+ " ");
        System.out.println("Attribute "+node.getDividingAttribute());
        System.out.println(node.getEdgeValue());
        System.out.println("-----------------------");
        if(node.getValue() != null) System.out.println("Value: "+ node.getValue());
        else if (node.isLeaf()) {
            System.out.println("Value: null");
        }
        System.out.println();

        for(Node child: node.getChildren()){
            printTree(child, appender + appender);
        }
    }

    /**
     * Tests the decision tree with a test data set
     * @param root Determines a decision tree with the given root node
     * @return a list of output values prodeced by the tree for the test data set
     */
    public ArrayList testDT(Node root){

        ArrayList<String> DToutput = new ArrayList<>(InputData.getTestData().length);
        System.out.println("Decision Tree testing output is:");
        int rowIndex =0;
        int i =0;
        Node curr = root;
        boolean found ;
        int classIndex = InputData.getNumAttr() - 1;

        while (rowIndex < InputData.getTestData().length) {
            found = false;
            boolean notFound = false;
            String divAtr = curr.getDividingAttribute();
            int divIndex;
            if (divAtr != null) {
                divIndex = InputData.getAttrIndex(divAtr);
                String[] attrVals = InputData.anAttrVals(divIndex);
                for (String val : attrVals) {
                    if (val.equals(InputData.getTestData()[rowIndex][divIndex])) {
                        List<Node> children = curr.getChildren();
                        for(int j=0; j<children.size(); j++){
                            if (children.get(j) != null && children.get(j).getEdgeValue().equals(val)) {
                                curr = children.get(j);
                                found = true;
                                break;
                            }
                            if (j == children.size()-1){
                                DToutput.add(null);
                                InputData.getTestData()[rowIndex][classIndex] = null;
                                rowIndex++;
                                curr = root;
                                notFound = true;
                            }
                        }
                    }
                    if (found || notFound) break;
                }
            } else{
                InputData.getTestData()[rowIndex][classIndex] = curr.getValue();
                DToutput.add(curr.getValue());
                rowIndex++;
                curr = root;
            }
        }
    return DToutput;
    }


    /**
     * Provides the accuracy of the prediction of this tree
     * @param output Output of the decision tree
     * @return Accuracy in prediction of this decision tree
     */
    public double getAccuracy(ArrayList output){
        int correct =0;
        for(int i=0; i<output.size(); i++){
            if (output.get(i) == null) continue;
            if( (output.get(i)).equals (InputData.realOutput.get(i)))
                correct++;
        }
        return correct/(1.0* output.size());
    }
}
