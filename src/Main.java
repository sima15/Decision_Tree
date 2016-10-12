import calculation.Data;
import data.InputData;
import tree.Node;
import tree.Tree;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sima on 9/30/2016.
 */
public class Main {


    public static void main (String[] args){
        double[] accuracies = new double[10];
        for(int i=0; i<10; i++) {
            /**
             * To use the toy data set input argument = 0
             * To use the Schilling data set input argument = 1
             */
            InputData.readFile(1);
            String[][] dat = Arrays.copyOf(InputData.getTrainData(), InputData.getTrainData().length);
            Data data = new Data(dat);

            Node node = new Node(data);
            node.setRemAttr(InputData.getAttrs());
            node.setParent(null);

            Tree tree = new Tree();
            Node root = tree.buildTree(node);
//            tree.printTree(root, "*");

            System.out.print("DT output: ");
            ArrayList<String> output = tree.testDT(root);
            System.out.println(Arrays.toString(output.toArray(new String[3])));
            double accuracy = tree.getAccuracy(output);
            System.out.println("Accuracy: " + accuracy);
            accuracies[i] = accuracy;
        }
        System.out.println(Arrays.toString(accuracies));
    }
}
