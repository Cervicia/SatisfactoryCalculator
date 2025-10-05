public class PartBlender extends AbstractPart{
    String byProduct = null;
    double byProductAmount= 0;
    public PartBlender(double amount, double apm, String name, Double wp) {
        super(amount, apm, name, wp);
    }
    public PartBlender(double amount, double apm, String name, Double wp, AbstractPart alternativeOf) {
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

    public String getByProduct() {
        return byProduct;
    }

    public void setByProduct(AbstractProduct byProduct) {
        if(byProduct == null){ throw new NullPointerException(); }
        this.byProduct = byProduct.getName();
    }

    public double getByProductAmount() {
        return byProductAmount;
    }

    public void setByProductAmount(double byProductAmount) {
        this.byProductAmount = byProductAmount;
    }

    public String toString() {
        return this.getClass().getSimpleName() + " " + this.name;
    }
}
