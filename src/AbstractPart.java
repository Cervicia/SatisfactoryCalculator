import java.util.HashSet;
import java.util.HashMap;


public abstract class AbstractPart extends AbstractProduct{
    protected HashMap<String, Double> ingredients;
    protected String alternativeOf;
    protected Double wp;

    public AbstractPart(double amount, double apm, String name, AbstractPart alternativeOf, Double wp) {
        super(amount, apm, name);
        ingredients = new HashMap<>();
        this.alternativeOf = alternativeOf.getName();
        this.wp = wp;
    }
    public AbstractPart(double amount, double apm, String name, Double wp) {
        super(amount, apm, name);
        ingredients = new HashMap<>();
        this.alternativeOf = this.getName();
        this.wp = wp;
    }
    public boolean add(AbstractProduct childNode, double amount) {
        if (childNode != null) {
            throw new NullPointerException();
        }
        if(!ingredients.containsKey(childNode.getName())){
            ingredients.put(childNode.getName(),amount);
            return true;
        } else {
            return false;
        }
    }
    public HashMap<String, Double> getIngredients() {
            return  ingredients;
    }
    public HashMap<AbstractProduct, Double> resolveIngredients() {
        HashMap<AbstractProduct, Double> resolved = new HashMap<>();
        for (HashMap.Entry<String, Double> entry : ingredients.entrySet()) {
            AbstractProduct resolvedIngredient = Recipes.recipes.get(entry.getKey());
            if(resolvedIngredient != null){
                resolved.put(resolvedIngredient, entry.getValue());
            } else {
                throw new IllegalStateException("Can't resolve " + entry.getKey());
            }
        }
        return resolved;
    }

    public String getAlternativeOf() {
        return alternativeOf;
    }

    public Double getWp() {
        return wp;
    }

    public void setIngredients(HashMap<String, Double> ingredients) {
        this.ingredients = ingredients;
    }

}
