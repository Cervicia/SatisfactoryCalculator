import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Recipes {
    public static Map<String, AbstractProduct> recipes;
    public static Map<String, AbstractProduct> alternativeRecipes;

    private final Map<String, AbstractProduct> defaultRecipes;

    public Recipes() {

        alternativeRecipes = new HashMap<>();
        RuntimeTypeAdapterFactory<AbstractProduct> productAdapterFactory =
                RuntimeTypeAdapterFactory.of(AbstractProduct.class, "type")
                        .registerSubtype(BaseIngredientOre .class, "baseIngredientOre")
                        .registerSubtype(PartSmelter.class, "partSmelter")
                        .registerSubtype(PartConstructor.class, "partConstructor")
                        .registerSubtype(PartAssembler.class, "partAssembler");

        try (FileReader reader = new FileReader("recipes.json")) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(productAdapterFactory)
                    .setPrettyPrinting()
                    .create();
            Type mapType2 = new TypeToken<Map<String, AbstractProduct>>(){}.getType();
            recipes = gson.fromJson(reader, mapType2);
            for(Map.Entry<String, AbstractProduct> entry : recipes.entrySet()) {
                if(entry.getValue() instanceof AbstractPart) {
                    if(!((AbstractPart) entry.getValue()).getAlternativeOf().equals(entry.getKey()) ) {
                        alternativeRecipes.put(entry.getKey(), entry.getValue());
                        recipes.remove(entry.getKey());
                    }
                }
            }

            System.out.println(recipes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        defaultRecipes = recipes;

    }
    public void checkAlternativeRecipes(HashMap<AbstractPart, Boolean> alternativeRecipesUsed) {
        for (Map.Entry<AbstractPart, Boolean> entry : alternativeRecipesUsed.entrySet()) {
            if(entry.getValue()) {
                AbstractPart target = (AbstractPart) Recipes.alternativeRecipes.get(entry.getKey().getAlternativeOf());
                target.setIngredients(entry.getKey().getIngredients());
            } else {
                AbstractPart target = (AbstractPart) Recipes.alternativeRecipes.get(entry.getKey().getAlternativeOf());
                target.setIngredients(((AbstractPart)defaultRecipes.get(target.getName())).getIngredients());
            }
        }
    }
}
