import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class Recipes {
    public static Map<String, AbstractProduct> recipes;
    public static Map<String, AbstractPart> alternativeRecipes;

    private static final Map<String, AbstractProduct> defaultRecipes = new HashMap<>();

    public Recipes() {

        alternativeRecipes = new HashMap<>();

        RuntimeTypeAdapterFactory<AbstractProduct> productAdapterFactory =
                RuntimeTypeAdapterFactory.of(AbstractProduct.class, "type")
                        .registerSubtype(BaseIngredientOre .class, "baseIngredientOre")
                        .registerSubtype(PartSmelter.class, "partSmelter")
                        .registerSubtype(PartConstructor.class, "partConstructor")
                        .registerSubtype(PartAssembler.class, "partAssembler")
                        .registerSubtype(PartFoundry.class, "partFoundry")
                        .registerSubtype(PartRefinery.class, "partRefinery")
                        .registerSubtype(PartBlender.class, "partBlender")
                        .registerSubtype(PartPackager.class, "partPackager")
                        .registerSubtype(PartManufacturer.class, "partManufacturer");

        try (FileReader reader = new FileReader("recipes.json")) {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(productAdapterFactory)
                    .setPrettyPrinting()
                    .create();
            Type mapType2 = new TypeToken<Map<String, AbstractProduct>>(){}.getType();
            recipes = gson.fromJson(reader, mapType2);

            FileReader reader2 = new FileReader("recipes.json");
            Gson gson2 = new GsonBuilder()
                    .registerTypeAdapterFactory(productAdapterFactory)
                    .setPrettyPrinting()
                    .create();
            Map<String, AbstractProduct> recipes2 = gson2.fromJson(reader2, mapType2);
            defaultRecipes.putAll(recipes2);

            Iterator<Map.Entry<String, AbstractProduct>> iterator = recipes.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, AbstractProduct> entry = iterator.next();
                if (entry.getValue() instanceof AbstractPart) {
                    AbstractPart part = (AbstractPart) entry.getValue();
                    if (!part.getAlternativeOf().equals(entry.getKey())) {
                        alternativeRecipes.put(entry.getKey(), part);
                        iterator.remove();
                    }
                }
            }

            System.out.println(recipes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void checkAlternativeRecipes(List<String> alternativeRecipesUsed) {

        for(Map.Entry<String, AbstractProduct> entry : recipes.entrySet()) {
            if(entry.getValue() instanceof AbstractPart) {
                AbstractPart changedPart = (AbstractPart) entry.getValue();
                AbstractPart defaultPart = (AbstractPart) defaultRecipes.get(entry.getKey());

                changedPart.setIngredients(defaultPart.getIngredients());
                changedPart.setApm(defaultPart.getApm());
                changedPart.setAmount(defaultPart.getAmount());
            }
        }
        for (String entry : alternativeRecipesUsed) {
            AbstractPart alternativePart = alternativeRecipes.get(entry);
            String alternativeOf = alternativeRecipes.get(entry).getAlternativeOf();
            AbstractPart changedPart =(AbstractPart)recipes.get(alternativeOf);

            changedPart.setIngredients(alternativePart.getIngredients());
            changedPart.setApm(alternativePart.getApm());
            changedPart.setAmount(alternativePart.getAmount());
        }

    }
}
