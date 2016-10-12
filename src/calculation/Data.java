package calculation;

/**
 * Created by Sima on 10/1/2016.
 */
public class Data {

    public String[][] data;
    public String value;

    public Data(String[][] table){
        data = table;
    }

    public String[][] getData(){
        return data;
    }

    public void setValue(String attr){
        value = attr;
    }

    public String getValue(){
        return value;
    }
}
