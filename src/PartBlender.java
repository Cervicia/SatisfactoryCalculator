public class PartBlender extends AbstractPart{
    public PartBlender(double amount, double apm, String name, Double wp) {
        super(amount, apm, name, wp);
    }
    public PartBlender(double amount, double apm, String name, AbstractPart alternativeOf,Double wp) {
        super(amount, apm, name, alternativeOf, wp);
    }
    public boolean add(AbstractProduct childNode, double amount) {
        if (childNode == null) {
            throw new NullPointerException();
        }
        if(!ingredients.containsKey(childNode.getName()) && ingredients.size() <= 4){
            ingredients.put(childNode.getName(),amount);
            return true;
        } else {
            return false;
        }
    }
    public String toString() {
        return this.getClass().getSimpleName() + " " + this.name;
    }
}
