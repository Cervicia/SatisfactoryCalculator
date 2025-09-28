public abstract class AbstractProduct {
    protected double apm;
    protected double amount;
    protected String name;

    public AbstractProduct(double amount, double apm,String name) {
        this.apm = apm;
        this.amount = amount;
        this.name = name;
    }

    public double getApm() {
        return apm;
    }
    public double getAmount() {
        return amount;
    }
    public String getName() {
        return name;
    }
}
