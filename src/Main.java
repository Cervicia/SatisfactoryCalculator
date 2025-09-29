//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import com.google.gson.*;
import com.google.gson.reflect.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args){
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");
        BaseIngredientOre ironOre = new BaseIngredientOre(1,240,"Iron Ore");
        BaseIngredientOre limestone =  new BaseIngredientOre(1,240,"Limestone");

        PartSmelter ironIngot = new PartSmelter(1, 30, "Iron Ingot");
        ironIngot.add(ironOre, 1);

        PartConstructor ironPlate = new PartConstructor(2, 20, "Iron Plate");
        ironPlate.add(ironIngot, 3);

        PartConstructor ironRod = new PartConstructor(1, 15, "Iron Rod");
        ironRod.add(ironIngot, 1);

        PartConstructor screws =  new PartConstructor(4, 40, "Screws");
        screws.add(ironRod, 1);

        PartAssembler reinforcedIronPlate = new PartAssembler(1, 5, "Reinforced Iron Plate");
        reinforcedIronPlate.add(ironPlate, 6);
        reinforcedIronPlate.add(screws, 12);

        RuntimeTypeAdapterFactory<AbstractProduct> productAdapterFactory =
                RuntimeTypeAdapterFactory.of(AbstractProduct.class, "type")
                        .registerSubtype(BaseIngredientOre .class, "baseIngredientOre")
                        .registerSubtype(PartSmelter.class, "partSmelter")
                        .registerSubtype(PartConstructor.class, "partConstructor");

        Map<String, AbstractProduct> recipes = new HashMap<>();
        recipes.put("Iron Ore", ironOre);
        recipes.put("Limestone", limestone);
        recipes.put("Iron Ingot", ironIngot);
        recipes.put("Iron Plate", ironPlate);

        Type mapType = new TypeToken<Map<String, AbstractProduct>>() {}.getType();

        try (FileWriter writer = new FileWriter("recipes.json")) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(productAdapterFactory)
                    .setPrettyPrinting()
                    .create();
            gson.toJson(recipes, mapType, writer); // Directly write to the file
        } catch (IOException e) {
            e.printStackTrace();
        }





        Calculator calculator = new Calculator();
        System.out.println(calculator.getIngredientsAPM(reinforcedIronPlate, 10));
        System.out.println(calculator.getBuildingCount(reinforcedIronPlate, 10));

    }
}