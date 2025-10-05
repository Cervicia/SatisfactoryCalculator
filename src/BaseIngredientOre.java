public class BaseIngredientOre extends AbstractBaseIngredient{
    public BaseIngredientOre(double amount, double apm, String name){
        super(amount, apm, name);
    }
    public String toString() {
        return "Resource Node (240/min)" + this.name;
    }
}
