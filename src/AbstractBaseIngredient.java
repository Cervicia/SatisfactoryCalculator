public abstract class AbstractBaseIngredient extends AbstractProduct{
    private double apm;
    private double amount;
    private String name;
    public AbstractBaseIngredient(double amount, double apm, String name){
        super(amount, apm, name);
    }
}
