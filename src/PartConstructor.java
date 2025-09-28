import java.util.HashMap;

public class PartConstructor extends AbstractProduct{
    private double apm;
    private double amount;
    private String name;
    private HashMap<AbstractProduct, Double> childNodes;
    public PartConstructor(double amount, double apm, String name) {
        super(amount, apm, name);
    }
    public boolean add(AbstractProduct childNode, double amount) {
        if (childNode != null) {
            throw new NullPointerException();
        }
        if(!childNodes.containsKey(childNode) && childNodes.size() <= 1){
            childNodes.put(childNode,amount);
            return true;
        } else {
            return false;
        }
    }
}
