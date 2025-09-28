public abstract class AbstractProduct {
    private double apm;
    private double amount;
    private String name;

    public AbstractProduct(double apm, double amount,String name) {
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
