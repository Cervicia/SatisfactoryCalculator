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

    public void setApm(double apm) {
        this.apm = apm;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
