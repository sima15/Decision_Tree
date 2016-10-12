package tree;

import calculation.Calculator;
import calculation.Data;
import data.InputData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sima on 10/1/2016.
 */
public class Node {
    /**
     * Data object held in this node
     */
    private Data data;
    /**
     * remaining attributes to choose from in this node
     */
    private String[] remAttr;
    /**
     * The attribute that will be used to divide the decision tree in this node
     */
    private String dividingAttribute;
    /**
     * The value on the edge of this node and its parent
     */
    private String edgeValue;
    /**
     * If this node is a leaf, this is the class value of it
     */
    private String LeafValue;
    /**
     * A unique id for this node
     */
    private int id;
    /**
     * list of children of this node
     */
    private List<Node> children = new ArrayList();
    /**
     * parent of this node
     */
    private Node parent;

    public Node(Data data){
        this.data = data;
    }


    public int getID() {
        return id;
    }

    public void setID(int i) {
        id = i;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node n){
        parent = n;
    }

    /**
     * Adda a child to this node
     * @param child the given child
     * @param id id of the newly creates child
     */
    public  void addChild(Node child, int id) {
        child.setID(id);
        getChildren().add(child);
        child.setParent(this);
    }

    /**
     * Checks if the current node is a leaf node
     * @return true if this node is a leaf
     */
    public boolean isLeaf(){
        if (Calculator.calEntropy(data.getData())[0] ==0) {
            if (!isEmpty()) {
                setValue(data.getData()[0][InputData.getNumAttr() - 1]);
                return true;
            }
            else setValue("Empty node");
        }
        return false;
    }

    /**
     * Checks if this node is empty
     * @return true if this node is empty
     */
    public boolean isEmpty(){
        if (data.getData().length == 0) {
            setValue("null");
            System.out.println("Empty Node");
            return true;
        }
        return false;
    }

    /**
     * Sets a dividing attribute for thiz node
     * @param divAttr
     */
    public void setDividingAttribute(String divAttr) {
        dividingAttribute = divAttr;
    }

    /**
     * Returns the dividing attribute of this node
     * @return the dividing attribute of this node
     */
    public String getDividingAttribute() {
        return dividingAttribute;
    }

    public String getEdgeValue() {
        return edgeValue;
    }

    public void setEdgeValue(Node parent, String edgeValue) {
        this.edgeValue = edgeValue;
    }

    public void setValue(String val) {
        LeafValue = val;
    }

    public String getValue() {
        return LeafValue;
    }

    public  void setRemAttr(String[] remAttr){
        this.remAttr = Arrays.copyOf(remAttr, remAttr.length);
    }

    public String[] getRemAttr(){
        return remAttr;
    }

    public Data getDataObj(){
        return data;
    }


    @Override
    public String toString() {
        String temp = "ID: "+ String.valueOf(getID());
        return temp;
    }
}
