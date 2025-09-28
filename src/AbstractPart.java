import java.util.HashSet;
import java.util.HashMap;


public abstract class AbstractPart extends AbstractProduct{
    private double apm;
    private double amount;
    private String name;
    private HashMap<AbstractProduct, Double> childNodes;
    public AbstractPart(double amount, double apm, String name) {
        super(amount, apm, name);
        childNodes = new HashMap<>();
    }
    public boolean add(AbstractProduct childNode, double amount) {
        if (childNode != null) {
            throw new NullPointerException();
        }
        if(!childNodes.containsKey(childNode)){
            childNodes.put(childNode,amount);
            return true;
        } else {
            return false;
        }
    }
    public HashMap<AbstractProduct, Double> getChildNodes(){
        return  childNodes;
    }
}
