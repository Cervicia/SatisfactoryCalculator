import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.*;
import com.google.gson.reflect.*;

public class GenerateJson {
    BaseIngredientOre ironOre;
    BaseIngredientOre limestone;
    BaseIngredientOre coal;
    BaseIngredientOre copperOre;
    BaseIngredientOre cateriumOre;
    BaseIngredientOre crudeOil;
    BaseIngredientOre sulfur;
    BaseIngredientOre bauxite;
    BaseIngredientOre rawQuartz;
    BaseIngredientOre uranium;
    BaseIngredientOre sam;
    BaseIngredientOre water;
    BaseIngredientOre leaves;
    BaseIngredientOre mycelia;
    BaseIngredientOre nitrogenGas;




    PartSmelter ironIngot;
    PartSmelter copperIngot;
    PartSmelter cateriumIngot;

    PartRefinery plastic;
    PartRefinery rubber;
    PartRefinery aluminaSolution;
    PartRefinery aluminiumScrap;
    PartRefinery heavyOilResidue;

    PartFoundry steelIngot;
    PartFoundry aluminiumIngot;

    PartConstructor ironRod;
    PartConstructor screw;
    PartConstructor ironPlate;
    PartConstructor copperSheet;
    PartConstructor steelPipe;
    PartConstructor wire;
    PartConstructor cable;
    PartConstructor steelBeam;
    PartConstructor quickwire;
    PartConstructor reanimatedSam;
    PartConstructor emptyCanister;
    PartConstructor biomass;
    PartConstructor quartzCrystal;
    PartConstructor copperPowder;
    PartConstructor emptyFluidTank;
    PartConstructor concrete;
    PartConstructor aluminiumCasing;
    PartConstructor silica;


    PartAssembler circuitBoard;
    PartAssembler versatileFramework;
    PartAssembler alcladAluminiumSheet;
    PartAssembler encasedIndustrialBeam;
    PartAssembler motor;
    PartAssembler stator;
    PartAssembler automatedWiring;
    PartAssembler aiLimiter;
    PartAssembler modularFrame;
    PartAssembler rotor;
    PartAssembler smartPlating;
    PartAssembler heatSink;
    PartAssembler fabric;
    PartAssembler reinforcedIronPlate;
    PartAssembler blackPowder;


    PartManufacturer samFluctuator;
    PartManufacturer crystalOscillator;
    PartManufacturer computer;
    PartManufacturer heavyModularFrame;
    PartManufacturer modularEngine;
    PartManufacturer adaptiveControlUnit;
    PartManufacturer supercomputer;
    PartManufacturer highSpeedConnector;
    PartManufacturer radioControlUnit;
    PartManufacturer turboMotor;

    PartBlender coolingSystem;

    public GenerateJson() {
        File file = new File("recipes.json");
        if(!file.exists()) {
            try {
                generateJson();
            } catch (IOException e) {
                System.err.println("Could not create the recipes.json file:");
                e.printStackTrace();
            }
        } else {

        }
    }

    public void generateJson() throws IOException {
        RuntimeTypeAdapterFactory<AbstractProduct> productAdapterFactory =
                RuntimeTypeAdapterFactory.of(AbstractProduct.class, "type")
                        .registerSubtype(BaseIngredientOre .class, "baseIngredientOre")
                        .registerSubtype(PartSmelter.class, "partSmelter")
                        .registerSubtype(PartConstructor.class, "partConstructor")
                        .registerSubtype(PartAssembler.class, "partAssembler")
                        .registerSubtype(PartFoundry.class, "partFoundry")
                        .registerSubtype(PartRefinery.class, "partRefinery")
                        .registerSubtype(PartBlender.class, "partBlender")
                        .registerSubtype(PartManufacturer.class, "partManufacturer");

        Type mapType = new TypeToken<Map<String, AbstractProduct>>() {
        }.getType();

        try {
            populateRecipes();
            Map<String, AbstractProduct> toJson = generateMap();

            try (FileWriter writer = new FileWriter("recipes.json")) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapterFactory(productAdapterFactory)
                        .setPrettyPrinting()
                        .create();
                gson.toJson(toJson, mapType, writer); // Directly write to the file
            } catch (IOException e) {
                System.err.println("Error writing to JSON file:");
                e.printStackTrace();
                throw new IOException("Error writing to JSON file");
            }
        } catch (IllegalAccessException e) {
            System.err.println("Error accessing fields via reflection:");
            e.printStackTrace();
        }


    }
    private Map<String, AbstractProduct> generateMap() throws IllegalAccessException {
        Map<String, AbstractProduct> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            AbstractProduct v = (AbstractProduct) field.get(this);
            map.put(v.getName(), v);
        }
        return map;
    }
    private void populateRecipes() {
        //BaseIngredients
        ironOre = new BaseIngredientOre(1, 240, "Iron Ore");
        copperOre = new BaseIngredientOre(1, 240, "Copper Ore");
        limestone = new BaseIngredientOre(1, 240, "Limestone");
        coal = new BaseIngredientOre(1, 240, "Coal");
        cateriumOre = new BaseIngredientOre(1, 240, "Caterium Ore");
        crudeOil = new BaseIngredientOre(1, 240, "Crude Oil");
        sulfur = new BaseIngredientOre(1, 240, "Sulfur");
        bauxite = new BaseIngredientOre(1, 240, "Bauxite");
        rawQuartz = new BaseIngredientOre(1, 240, "Raw Quartz");
        uranium = new BaseIngredientOre(1, 240, "Uranium");
        sam = new BaseIngredientOre(1, 240, "SAM");
        water = new BaseIngredientOre(1, 120, "Water");
        leaves = new BaseIngredientOre(1, 10, "Leaves");
        mycelia = new BaseIngredientOre(1, 10, "Mycelia");
        nitrogenGas = new BaseIngredientOre(1, 240, "Nitrogen Gas");



        //Smelter
        ironIngot = new PartSmelter(1, 30, "Iron Ingot", 8.53);
        ironIngot.add(ironOre, 1);
        cateriumIngot = new PartSmelter(1, 15, "Caterium Ingot", 163.04);
        cateriumIngot.add(cateriumOre, 3);
        copperIngot = new PartSmelter(1, 30, "Copper Ingot", 20.79);
        copperIngot.add(copperOre, 1);



        //Refinery
        plastic = new PartRefinery(2, 20, "Plastic", 1.15);
        plastic.add(crudeOil, 3);
        rubber = new PartRefinery(2, 20, "Rubber", 1.15);
        rubber.add(crudeOil, 3);
        aluminaSolution = new PartRefinery(12, 120, "Alumina Solution", 1.15);
        aluminaSolution.add(bauxite, 12);
        aluminaSolution.add(water, 18);
        aluminiumScrap = new PartRefinery(6, 360, "Aluminium Scrap", 1.15);
        aluminiumScrap.add(aluminaSolution, 4);
        aluminiumScrap.add(coal, 2);
        heavyOilResidue = new PartRefinery(2, 20, "Heavy Oil Residue", 1.15);
        heavyOilResidue.add(crudeOil, 3);

        //Foundry
        steelIngot = new PartFoundry(3, 45, "Steel Ingot", 1.15);
        steelIngot.add(ironOre, 3);
        steelIngot.add(coal, 3);


        //Constructor
        ironPlate = new PartConstructor(2, 20, "Iron Plate", 6.89);
        ironPlate.add(ironIngot, 3);
        ironRod = new PartConstructor(1, 15, "Iron Rod", 4.59);
        ironRod.add(ironIngot, 1);
        screw = new PartConstructor(4, 40, "Screw", 1.15);
        screw.add(ironRod, 1);
        steelBeam= new PartConstructor(1, 15, "Steel Beam", 1.15);
        steelBeam.add(steelIngot, 4);
        steelPipe = new PartConstructor(2, 20, "Steel Pipe", 1.15);
        steelPipe.add(steelIngot, 3);
        emptyCanister = new PartConstructor(4, 60, "Empty Canister", 1.15);
        emptyCanister.add(plastic, 2);
        quartzCrystal = new PartConstructor(3, 22.5, "Quart Crystal", 1.15);
        quartzCrystal.add(rawQuartz, 5);
        copperSheet = new PartConstructor(1, 10, "Copper Sheet", 1.15);
        copperSheet.add(copperIngot, 2);
        copperPowder = new PartConstructor(5, 50, "Copper Powder", 1.15);
        copperPowder.add(copperIngot, 30);
        quickwire = new PartConstructor(5, 60, "Quickwire", 1.15);
        quickwire.add(cateriumIngot, 1);
        concrete = new PartConstructor(1, 15, "Concrete", 1.15);
        concrete.add(limestone, 3);
        wire = new PartConstructor(2, 30, "Wire", 1.15);
        wire.add(copperIngot, 1);
        cable = new PartConstructor(1, 30, "Cable", 1.15);
        cable.add(wire, 2);
        reanimatedSam = new PartConstructor(1, 30, "Reanimated SAM", 1.15);
        reanimatedSam.add(sam, 4);
        biomass = new PartConstructor(5, 60, "Biomass", 1.15);
        biomass.add(leaves, 10);
        silica = new PartConstructor(5, 37.5, "Silica", 1.15);
        silica.add(rawQuartz, 3);

        //Foundry
        aluminiumIngot = new PartFoundry(4, 60, "Aluminium Ingot", 1.15);
        aluminiumIngot.add(aluminiumScrap, 6);
        aluminiumIngot.add(silica, 5);

        //Constructor
        aluminiumCasing = new PartConstructor(2, 60, "Aluminium Casing", 1.15);
        aluminiumCasing.add(aluminiumIngot, 3);
        emptyFluidTank = new PartConstructor(1, 60, "Empty Fluid Tank", 1.15);
        emptyFluidTank.add(aluminiumIngot, 1);

        //Assembler
        reinforcedIronPlate = new PartAssembler(1, 5, "Reinforced Iron Plate", 55.09);
        reinforcedIronPlate.add(ironPlate, 6);
        reinforcedIronPlate.add(screw, 12);
        modularFrame = new PartAssembler(2, 2, "Modular Frame", 55.09);
        modularFrame.add(reinforcedIronPlate, 3);
        modularFrame.add(ironRod, 12);
        circuitBoard = new PartAssembler(1, 7.5, "Circuit Board", 55.09);
        circuitBoard.add(copperSheet, 2);
        circuitBoard.add(plastic, 4);
        versatileFramework = new PartAssembler(2, 5, "Versatile Framework", 55.09);
        versatileFramework.add(modularFrame, 1);
        versatileFramework.add(steelBeam, 12);
        alcladAluminiumSheet = new PartAssembler(3, 30, "Alclad Aluminium Sheet", 55.09);
        alcladAluminiumSheet.add(aluminiumIngot, 3);
        alcladAluminiumSheet.add(copperIngot, 1);
        encasedIndustrialBeam = new PartAssembler(1, 6, "Encased Industrial Beam", 55.09);
        encasedIndustrialBeam.add(steelBeam, 3);
        encasedIndustrialBeam.add(concrete, 6);
        stator = new PartAssembler(1, 5, "Stator", 55.09);
        stator.add(steelPipe, 3);
        stator.add(wire, 8);
        automatedWiring = new PartAssembler(1, 2.5, "Automated Wiring", 55.09);
        automatedWiring.add(stator, 1);
        automatedWiring.add(cable, 20);
        aiLimiter = new PartAssembler(1, 5, "AI Limiter", 55.09);
        aiLimiter.add(copperSheet, 5);
        aiLimiter.add(quickwire, 20);
        rotor = new PartAssembler(1, 4, "Rotor", 55.09);
        rotor.add(ironRod, 5);
        rotor.add(screw, 25);
        motor = new PartAssembler(1, 5, "Motor", 55.09);
        motor.add(rotor, 2);
        motor.add(stator, 2);
        smartPlating = new PartAssembler(1, 2, "Smart Plating", 55.09);
        smartPlating.add(reinforcedIronPlate, 1);
        smartPlating.add(rotor, 1);
        heatSink = new PartAssembler(1, 7.5, "Heat Sink", 55.09);
        heatSink.add(alcladAluminiumSheet, 5);
        heatSink.add(copperSheet, 3);
        fabric = new PartAssembler(1, 15, "Fabric", 55.09);
        fabric.add(mycelia, 1);
        fabric.add(biomass, 5);
        blackPowder = new PartAssembler(2, 30, "Black Powder", 55.09);
        blackPowder.add(coal, 1);
        blackPowder.add(sulfur, 1);

        //Manufacturer
        samFluctuator = new PartManufacturer(1, 10, "SAM Fluctuator", 30.80);
        samFluctuator.add(reanimatedSam, 6);
        samFluctuator.add(wire, 5);
        samFluctuator.add(steelPipe, 3);
        crystalOscillator = new PartManufacturer(2, 1, "Crystal Oscillator", 30.80);
        crystalOscillator.add(quartzCrystal, 36);
        crystalOscillator.add(cable, 28);
        crystalOscillator.add(reinforcedIronPlate, 5);
        computer = new PartManufacturer(1, 2.5, "Computer", 30.80);
        computer.add(circuitBoard, 4);
        computer.add(cable, 8);
        computer.add(plastic, 16);
        heavyModularFrame = new PartManufacturer(1, 2, "Heavy Modular Frame", 30.80);
        heavyModularFrame.add(modularFrame, 5);
        heavyModularFrame.add(steelPipe, 20);
        heavyModularFrame.add(encasedIndustrialBeam, 5);
        heavyModularFrame.add(screw, 120);
        modularEngine = new PartManufacturer(1, 1, "Modular Engine", 30.80);
        modularEngine.add(motor, 2);
        modularEngine.add(rubber, 15);
        modularEngine.add(smartPlating, 2);
        adaptiveControlUnit = new PartManufacturer(1, 1, "Adaptive Control Unit", 30.80);
        adaptiveControlUnit.add(automatedWiring, 5);
        adaptiveControlUnit.add(circuitBoard, 5);
        adaptiveControlUnit.add(heavyModularFrame, 1);
        adaptiveControlUnit.add(computer, 2);
        highSpeedConnector = new PartManufacturer(1, 3.75, "High Speed Connector", 30.80);
        highSpeedConnector.add(quickwire, 56);
        highSpeedConnector.add(cable, 10);
        highSpeedConnector.add(circuitBoard, 1);
        supercomputer = new PartManufacturer(1, 1.875, "Supercomputer", 30.80);
        supercomputer.add(computer, 4);
        supercomputer.add(aiLimiter, 2);
        supercomputer.add(highSpeedConnector, 3);
        supercomputer.add(plastic, 28);
        radioControlUnit = new PartManufacturer(2, 2.5, "Radio Control Unit", 30.80);
        radioControlUnit.add(aluminiumCasing, 32);
        radioControlUnit.add(crystalOscillator, 1);
        radioControlUnit.add(computer, 2);



        //Blender
        coolingSystem = new PartBlender(1, 6, "Cooling System", 70.34);
        coolingSystem.add(heatSink, 2);
        coolingSystem.add(rubber, 2);
        coolingSystem.add(water, 5);
        coolingSystem.add(nitrogenGas, 25);


        //Manufacturer
        turboMotor = new PartManufacturer(1, 1.875, "Turbo Motor", 30.80);
        turboMotor.add(coolingSystem, 4);
        turboMotor.add(radioControlUnit, 2);
        turboMotor.add(motor, 4);
        turboMotor.add(rubber, 24);

        //Set ByProducts Refinery
        plastic.setByProduct(heavyOilResidue);
        plastic.setByProductAmount(1);
        rubber.setByProduct(heavyOilResidue);
        rubber.setByProductAmount(2);
        aluminaSolution.setByProduct(silica);
        aluminaSolution.setByProductAmount(5);
        aluminiumScrap.setByProduct(water);
        aluminiumScrap.setByProductAmount(2);


    }
}
