public class VertexWrapper {
    private AbstractProduct product;
    private double productAmount;
    private double buildingAmount;

    public VertexWrapper(AbstractProduct product, double productAmount, double buildingAmount) {
        this.productAmount = productAmount;
        this.buildingAmount = buildingAmount;
        this.product = product;
    }

    public AbstractProduct getProduct() {
        return product;
    }

    public double getProductAmount() {
        return productAmount;
    }

    public double getBuildingAmount() {
        return buildingAmount;
    }

    public void setProductAmount(double productAmount) {
        this.productAmount = productAmount;
    }

    public void setBuildingAmount(double buildingAmount) {
        this.buildingAmount = buildingAmount;
    }

    public String toString() {
        if(product instanceof AbstractPart){
            if(buildingAmount != 0) {
                String output = String.format("%s: %.2f/min %n %s: %.2f Buildings", product.getName(),productAmount, product.getClass().getSimpleName(), buildingAmount);
                return output;
            } else {
                String output = String.format("%s: %.2f/min", product.getName(),productAmount);
                return output;
            }
        } else {
            String output = String.format("%s: %.2f/min %n %s: %.2f Buildings", product.getName(),productAmount, "Resource Node: (" + Recipes.recipes.get(product.getName()).getApm() + "/min)", buildingAmount);
            return output;
        }
    }
}
