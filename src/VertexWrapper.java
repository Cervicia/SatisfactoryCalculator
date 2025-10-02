public class VertexWrapper {
    private AbstractProduct product;
    private double productAmount;
    private double buildingAmount;

    public VertexWrapper(AbstractProduct product, double productAmount, double buildingAmount) {
        this.productAmount = productAmount;
        this.buildingAmount = buildingAmount;
        this.product = product;
    }

    public double getProductAmount() {
        return productAmount;
    }

    public double getBuildingAmount() {
        return buildingAmount;
    }
    public String toString() {
        String output = String.format("%s: %.2f/min %n %s: %.2f Buildings", product.getName(),productAmount, product.getClass().getSimpleName(), buildingAmount);
        return output;
    }

}
