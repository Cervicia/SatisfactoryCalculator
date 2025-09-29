import java.util.List;

public class RecipeData {
    // The field name MUST match the JSON key "BaseMaterials"
    private List<BaseIngredientOre > BaseIngredientOres;

    // Getter and a toString()
    public List<BaseIngredientOre > getBaseMaterials() {
        return BaseIngredientOres;
    }

    @Override
    public String toString() {
        return "RecipeData{" + "BaseMaterials=" + BaseIngredientOres + '}';
    }
}
