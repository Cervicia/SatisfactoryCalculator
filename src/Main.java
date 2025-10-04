//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;
import java.io.FileReader;

public class Main extends Application {

    private volatile boolean running;

    public static void main(String[] args) {

        GenerateJson generateJson = new GenerateJson();

        Recipes instance = new Recipes();

        System.out.println(Recipes.recipes);


        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        /*System.out.printf("Hello and welcome!");
        BaseIngredientOre ironOre = new BaseIngredientOre(1, 240, "Iron Ore");
        BaseIngredientOre limestone = new BaseIngredientOre(1, 240, "Limestone");

        PartSmelter ironIngot = new PartSmelter(1, 30, "Iron Ingot", 8.53);
        ironIngot.add(ironOre, 1);

        PartConstructor ironPlate = new PartConstructor(2, 20, "Iron Plate", 6.89);
        ironPlate.add(ironIngot, 3);

        PartConstructor ironRod = new PartConstructor(1, 15, "Iron Rod", 4.59);
        ironRod.add(ironIngot, 1);

        PartConstructor screw = new PartConstructor(4, 40, "Screw", 1.15);
        screw.add(ironRod, 1);
        PartConstructor castScrew = new PartConstructor(20, 50, "Cast Screw", screw, 1.15);
        castScrew.add(ironIngot, 5);

        PartAssembler reinforcedIronPlate = new PartAssembler(1, 5, "Reinforced Iron Plate", 55.09);
        reinforcedIronPlate.add(ironPlate, 6);
        reinforcedIronPlate.add(screw, 12);

        RuntimeTypeAdapterFactory<AbstractProduct> productAdapterFactory =
                RuntimeTypeAdapterFactory.of(AbstractProduct.class, "type")
                        .registerSubtype(BaseIngredientOre.class, "baseIngredientOre")
                        .registerSubtype(PartSmelter.class, "partSmelter")
                        .registerSubtype(PartConstructor.class, "partConstructor")
                        .registerSubtype(PartAssembler.class, "partAssembler");

        Map<String, AbstractProduct> toJson = new HashMap<>();
        toJson.put("Iron Ore", ironOre);
        toJson.put("Limestone", limestone);
        toJson.put("Iron Ingot", ironIngot);
        toJson.put("Iron Plate", ironPlate);
        toJson.put("Reinforced Iron Plate", reinforcedIronPlate);
        toJson.put("Screw", screw);
        toJson.put("Cast Screw", castScrew);
        toJson.put("Iron Rod", ironRod);

        Type mapType = new TypeToken<Map<String, AbstractProduct>>() {
        }.getType();

        try (FileWriter writer = new FileWriter("recipes.json")) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(productAdapterFactory)
                    .setPrettyPrinting()
                    .create();
            gson.toJson(toJson, mapType, writer); // Directly write to the file
        } catch (IOException e) {
            e.printStackTrace();
        }


        Calculator calculator = new Calculator();
        System.out.println(calculator.getIngredientsAPM(reinforcedIronPlate, 10));
        System.out.println(calculator.getBuildingCount(reinforcedIronPlate, 10));
        */
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {


        Parent root = FXMLLoader.load(getClass().getResource("/resources/Scene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


}