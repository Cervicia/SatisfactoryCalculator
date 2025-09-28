import java.util.HashMap;

public class PartAssembler extends AbstractPart{
    public PartAssembler(double amount, double apm, String name) {
        super(amount, apm, name);
    }
    public boolean add(AbstractProduct childNode, double amount) {
        if (childNode == null) {
            throw new NullPointerException();
        }
        if(!childNodes.containsKey(childNode) && childNodes.size() <= 2){
            childNodes.put(childNode,amount);
            return true;
        } else {
            return false;
        }
    }
    public String toString() {
        return "Name: " + this.name;
    }
}
