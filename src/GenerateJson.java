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

    // =======================================================
    // == DEFAULT RECIPES ====================================
    // =======================================================
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
    PartRefinery petroleumCoke;
    PartRefinery sulfuricAcid;
    PartRefinery polymerResin;
    PartRefinery fuel;

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
    PartAssembler compactedCoal;
    PartAssembler electromagneticControlRod;
    PartAssembler pressureConversionCube;


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
    PartBlender nitricAcid;
    PartBlender fusedModularFrame;
    PartBlender battery;

    PartPackager packagedNitrogenGas;

    // =======================================================
    // == ALTERNATIVE RECIPES ================================
    // =======================================================
    PartSmelter pureAluminiumIngot;

    PartRefinery wetConcrete;
    PartRefinery steamedCopperSheet;
    PartRefinery pureQuartzCrystal;
    PartRefinery pureIronIngot;
    PartRefinery pureCopperIngot;
    PartRefinery pureCateriumIngot;
    PartRefinery electrodeAluminiumScrap;
    PartRefinery coatedCable;
    PartRefinery sloppyAlumina;
    PartRefinery quartzPurification;
    PartRefinery leachedIronIngot;
    PartRefinery leachedCopperIngot;
    PartRefinery leachedCateriumIngot;
    PartRefinery polyesterFabric;
    PartRefinery dissolvedSilica;

    PartFoundry copperAlloyIngot;
    PartFoundry cokeSteelIngot;
    PartFoundry moldedSteelPipe;
    PartFoundry steelCastPlate;
    PartFoundry moldedBeam;
    PartFoundry fusedQuartzCrystal;
    PartFoundry basicIronIngot;
    PartFoundry temperedCopperIngot;
    PartFoundry temperedCateriumIngot;
    PartFoundry compactedSteelIngot;
    PartFoundry solidSteelIngot;
    PartFoundry ironAlloyIngot;

    PartConstructor steelRod;
    PartConstructor steelCanister;
    PartConstructor ironPipe;
    PartConstructor aluminiumBeam;
    PartConstructor aluminiumRod;
    PartConstructor cateriumWire;
    PartConstructor ironWire;
    PartConstructor steelScrews;
    PartConstructor castScrews;

    PartAssembler rubberConcrete;
    PartAssembler fusedWire;
    PartAssembler electrodeCircuitBoard;
    PartAssembler copperRotor;
    PartAssembler coatedIronPlate;
    PartAssembler coatedIronCanister;
    PartAssembler boltedFrame;
    PartAssembler adheredIronPlate;
    PartAssembler ocSupercomputer;
    PartAssembler electricMotor;
    PartAssembler alcladCasing;
    PartAssembler plasticAILimiter;
    PartAssembler quickwireStator;
    PartAssembler cheapSilica;
    PartAssembler steelRotor;
    PartAssembler encasedIndustrialPipe;
    PartAssembler stitchedIronPlate;
    PartAssembler boltedIronPlate;
    PartAssembler fusedQuickwire;
    PartAssembler steeledFrame;
    PartAssembler heatExchanger;
    PartAssembler fineBlackPowder;
    PartAssembler electromagneticConnectionRod;
    PartAssembler fineConcrete;
    PartAssembler crystalComputer;
    PartAssembler cateriumCircuitBoard;
    PartAssembler siliconCircuitBoard;
    PartAssembler quickwireCable;
    PartAssembler insulatedCable;

    PartManufacturer plasticSmartPlating;
    PartManufacturer automatedSpeedWiring;
    PartManufacturer heavyFlexibleFrame;
    PartManufacturer flexibleFramework;
    PartManufacturer turboPressureMotor;
    PartManufacturer superStateComputer;
    PartManufacturer radioControlSystem;
    PartManufacturer classicBattery;
    PartManufacturer turboElectricMotor;
    PartManufacturer radioConnectionUnit;
    PartManufacturer rigorMotor;
    PartManufacturer siliconHighSpeedConnector;
    PartManufacturer heavyEncasedFrame;
    PartManufacturer insulatedCrystalOscillator;
    PartManufacturer cateriumComputer;


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
                        .registerSubtype(PartPackager.class, "partPackager")
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

        // =======================================================
        // == DEFAULT RECIPES ====================================
        // =======================================================

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
        petroleumCoke = new PartRefinery(12, 120, "Petroleum Coke", 1.15);
        petroleumCoke.add(heavyOilResidue, 4);
        sulfuricAcid = new PartRefinery(5, 50, "Sulfuric Acid", 1.15);
        sulfuricAcid.add(sulfur, 5);
        sulfuricAcid.add(water, 5);
        polymerResin = new PartRefinery(3, 30, "Polymer Resin", 1.15);
        polymerResin.add(crudeOil, 6);
        fuel = new PartRefinery(4, 40, "Fuel", 1.15);
        fuel.add(crudeOil, 6);

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
        compactedCoal= new PartAssembler(5, 25, "Compacted Coal", 55.09);
        compactedCoal.add(coal, 5);
        compactedCoal.add(sulfur, 5);
        electromagneticControlRod= new PartAssembler(2, 4, "Electromagnetic Control Rod", 55.09);
        electromagneticControlRod.add(stator, 3);
        electromagneticControlRod.add(aiLimiter, 2);



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
        nitricAcid = new PartBlender(3, 30, "Nitric Acid", 70.34);
        nitricAcid.add(nitrogenGas, 12);
        nitricAcid.add(water, 3);
        nitricAcid.add(ironPlate, 1);
        fusedModularFrame = new PartBlender(1, 1.5, "Fused Modular Frame", 70.34);
        fusedModularFrame.add(heavyModularFrame, 1);
        fusedModularFrame.add(aluminiumCasing, 50);
        fusedModularFrame.add(nitrogenGas, 25);
        battery = new PartBlender(1, 20, "Battery", 70.34);
        battery.add(sulfuricAcid, 2.5);
        battery.add(aluminaSolution, 2);
        battery.add(aluminiumCasing, 1);

        //Assembler
        pressureConversionCube= new PartAssembler(1, 1, "Pressure Conversion Cube", 55.09);
        pressureConversionCube.add(fusedModularFrame, 1);
        pressureConversionCube.add(radioControlUnit, 2);

        //Manufacturer
        turboMotor = new PartManufacturer(1, 1.875, "Turbo Motor", 30.80);
        turboMotor.add(coolingSystem, 4);
        turboMotor.add(radioControlUnit, 2);
        turboMotor.add(motor, 4);
        turboMotor.add(rubber, 24);

        //Packager
        packagedNitrogenGas = new PartPackager(1, 60, "Packaged Nitrogen Gas",40.59);
        packagedNitrogenGas.add(nitrogenGas, 4);
        packagedNitrogenGas.add(emptyFluidTank, 1);

        //Set ByProducts Refinery
        plastic.setByProduct(heavyOilResidue);
        plastic.setByProductAmount(1);
        rubber.setByProduct(heavyOilResidue);
        rubber.setByProductAmount(2);
        aluminaSolution.setByProduct(silica);
        aluminaSolution.setByProductAmount(5);
        aluminiumScrap.setByProduct(water);
        aluminiumScrap.setByProductAmount(2);
        polymerResin.setByProduct(fuel);
        polymerResin.setByProductAmount(4);
        fuel.setByProduct(polymerResin);
        fuel.setByProductAmount(3);

        //Set ByProducts Blender
        battery.setByProduct(water);
        battery.setByProductAmount(1.5);





        // =======================================================
        // == ALTERNATIVE RECIPES ================================
        // =======================================================

        //Smelter
        pureAluminiumIngot = new PartSmelter(1, 30, "Pure Aluminium Ingot", 8.53, aluminiumIngot);
        pureAluminiumIngot.add(aluminiumScrap, 2);

        //Foundry
        copperAlloyIngot = new PartFoundry(10, 100, "Copper Alloy Ingot", 1.15,copperIngot);
        copperAlloyIngot.add(copperOre, 5);
        copperAlloyIngot.add(ironOre, 5);
        cokeSteelIngot = new PartFoundry(20, 100, "Coke Steel Ingot", 1.15, steelIngot);
        cokeSteelIngot.add(ironOre, 15);
        cokeSteelIngot.add(petroleumCoke, 15);
        moldedSteelPipe = new PartFoundry(5, 50, "Molded Steel Pipe", 1.15, steelPipe);
        moldedSteelPipe.add(steelIngot, 5);
        moldedSteelPipe.add(concrete, 3);
        steelCastPlate = new PartFoundry(3, 45, "Steel Cast Plate", 1.15, ironPlate);
        steelCastPlate.add(ironIngot, 1);
        steelCastPlate.add(steelIngot, 1);
        moldedBeam = new PartFoundry(9, 45, "Molded Beam", 1.15, steelBeam);
        moldedBeam.add(steelIngot, 24);
        moldedBeam.add(concrete, 16);
        fusedQuartzCrystal = new PartFoundry(18, 54, "Fused Quartz Crystal", 1.15, quartzCrystal);
        fusedQuartzCrystal.add(rawQuartz, 25);
        fusedQuartzCrystal.add(coal, 12);
        basicIronIngot = new PartFoundry(10, 50, "Basic Iron Ingot", 1.15, ironIngot);
        basicIronIngot.add(ironOre, 5);
        basicIronIngot.add(limestone, 8);
        temperedCopperIngot = new PartFoundry(12, 60, "Tempered Copper Ingot", 1.15, copperIngot);
        temperedCopperIngot.add(copperOre, 5);
        temperedCopperIngot.add(petroleumCoke, 8);
        temperedCateriumIngot = new PartFoundry(3, 22.5, "Tempered Caterium Ingot", 1.15, cateriumIngot);
        temperedCateriumIngot.add(cateriumOre, 6);
        temperedCateriumIngot.add(petroleumCoke, 2);
        compactedSteelIngot = new PartFoundry(4, 10, "Compacted Steel Ingot", 1.15, steelIngot);
        compactedSteelIngot.add(ironOre, 2);
        compactedSteelIngot.add(compactedCoal, 1);
        solidSteelIngot = new PartFoundry(3, 60, "Solid Steel Ingot", 1.15, steelIngot);
        solidSteelIngot.add(ironIngot, 2);
        solidSteelIngot.add(coal, 2);
        ironAlloyIngot = new PartFoundry(15, 75, "Iron Alloy Ingot", 1.15, ironIngot);
        ironAlloyIngot.add(ironOre, 8);
        ironAlloyIngot.add(copperOre, 2);

        //Refinery
        wetConcrete = new PartRefinery(4, 80, "Wet Concrete", 1.15, concrete);
        wetConcrete.add(limestone, 6);
        wetConcrete.add(water, 5);
        steamedCopperSheet = new PartRefinery(3, 22.5, "Sulfuric Acid", 1.15, copperSheet);
        steamedCopperSheet.add(copperIngot, 3);
        steamedCopperSheet.add(water, 3);
        pureQuartzCrystal = new PartRefinery(7, 52.5, "Pure Quartz Crystal", 1.15, quartzCrystal);
        pureQuartzCrystal.add(sulfur, 9);
        pureQuartzCrystal.add(water, 5);
        pureIronIngot = new PartRefinery(13, 65, "Pure Iron Ingot", 1.15, ironIngot);
        pureIronIngot.add(ironOre, 7);
        pureIronIngot.add(water, 4);
        pureCopperIngot = new PartRefinery(15, 37.5, "Pure Copper Ingot", 1.15, copperIngot);
        pureCopperIngot.add(copperOre, 6);
        pureCopperIngot.add(water, 4);
        pureCateriumIngot = new PartRefinery(1, 12, "Pure Caterium Ingot", 1.15, cateriumIngot);
        pureCateriumIngot.add(cateriumOre, 2);
        pureCateriumIngot.add(water, 2);
        electrodeAluminiumScrap = new PartRefinery(20, 300, "Electrode Aluminium Scrap", 1.15, aluminiumScrap);
        electrodeAluminiumScrap.add(aluminaSolution, 12);
        electrodeAluminiumScrap.add(petroleumCoke, 4);
        coatedCable = new PartRefinery(9, 67.5, "Coated Cable", 1.15, cable);
        coatedCable.add(wire, 5);
        coatedCable.add(heavyOilResidue, 2);
        sloppyAlumina = new PartRefinery(12, 240, "Sloppy Alumina", 1.15, aluminaSolution);
        sloppyAlumina.add(bauxite, 10);
        sloppyAlumina.add(water, 10);
        quartzPurification = new PartRefinery(15, 75, "Quartz Purification", 1.15, quartzCrystal);
        quartzPurification.add(rawQuartz, 24);
        quartzPurification.add(nitricAcid, 2);
        leachedIronIngot = new PartRefinery(10, 100, "Leached Iron Ingot", 1.15, ironIngot);
        leachedIronIngot.add(ironOre, 5);
        leachedIronIngot.add(sulfuricAcid, 1);
        leachedCopperIngot = new PartRefinery(22, 110, "Leached Copper Ingot", 1.15, copperIngot);
        leachedCopperIngot.add(copperOre, 9);
        leachedCopperIngot.add(sulfuricAcid, 5);
        leachedCateriumIngot = new PartRefinery(6, 36, "Leached Caterium Ingot", 1.15, cateriumIngot);
        leachedCateriumIngot.add(cateriumOre, 9);
        leachedCateriumIngot.add(sulfuricAcid, 5);
        polyesterFabric = new PartRefinery(1, 30, "Polyester Fabric", 1.15, fabric);
        polyesterFabric.add(polymerResin, 1);
        polyesterFabric.add(water, 1);
        dissolvedSilica = new PartRefinery(12, 60, "Dissolved Silica", 1.15);
        dissolvedSilica.add(rawQuartz, 24);
        dissolvedSilica.add(nitricAcid, 2);

        //Refinery ByProducts
        electrodeAluminiumScrap.setByProduct(water);
        electrodeAluminiumScrap.setByProductAmount(7);
        quartzPurification.setByProduct(dissolvedSilica);
        quartzPurification.setByProductAmount(12);
        dissolvedSilica.setByProduct(quartzCrystal);
        dissolvedSilica.setByProductAmount(15);

        //Constructor
        steelRod = new PartConstructor(4, 48, "Steel Rod", 6.89, ironRod);
        steelRod.add(steelIngot, 1);
        steelCanister = new PartConstructor(4, 40, "Steel Canister", 6.89, emptyCanister);
        steelCanister.add(steelIngot, 4);
        ironPipe = new PartConstructor(5, 25, "Iron Pipe", 6.89, steelPipe);
        ironPipe.add(ironIngot, 20);
        aluminiumBeam = new PartConstructor(3, 22.5, "Aluminium Beam", 6.89, steelBeam);
        aluminiumBeam.add(aluminiumIngot, 3);
        aluminiumRod = new PartConstructor(7, 52.5, "Aluminium Rod", 6.89, ironRod);
        aluminiumRod.add(aluminiumIngot, 1);
        cateriumWire = new PartConstructor(8, 120, "Caterium Wire", 6.89, wire);
        cateriumWire.add(cateriumIngot, 1);
        ironWire = new PartConstructor(9, 22.5, "Iron Wire", 6.89, wire);
        ironWire.add(ironIngot, 5);
        steelScrews = new PartConstructor(52, 260, "Steel Screws", 6.89, screw);
        steelScrews.add(steelBeam, 1);
        castScrews = new PartConstructor(20, 50, "Cast Screws", 6.89, screw);
        castScrews.add(ironIngot, 5);

        //Assembler
        compactedCoal = new PartAssembler(5, 25, "Compacted Coal", 55.09,compactedCoal);
        compactedCoal.add(coal, 5);
        compactedCoal.add(sulfur, 5);
        rubberConcrete = new PartAssembler(9, 90, "Rubber Concrete", 55.09, concrete);
        rubberConcrete.add(limestone, 10);
        rubberConcrete.add(rubber, 2);
        fusedWire = new PartAssembler(30, 90, "Fused Wire", 55.09,wire);
        fusedWire.add(copperIngot, 4);
        fusedWire.add(cateriumIngot, 1);
        electrodeCircuitBoard = new PartAssembler(1, 5, "Electrode Circuit Board", 55.09, circuitBoard);
        electrodeCircuitBoard.add(rubber, 4);
        electrodeCircuitBoard.add(petroleumCoke, 8);
        copperRotor = new PartAssembler(3, 11.25, "Copper Rotor", 55.09, rotor);
        copperRotor.add(copperSheet, 6);
        copperRotor.add(screw, 52);
        coatedIronPlate = new PartAssembler(10, 75, "Coated Iron Plate", 55.09, ironPlate);
        coatedIronPlate.add(ironIngot, 5);
        coatedIronPlate.add(plastic, 1);
        coatedIronCanister = new PartAssembler(4, 60, "Coated Iron Canister", 55.09, emptyCanister);
        coatedIronCanister.add(ironPlate, 2);
        coatedIronCanister.add(copperSheet, 1);
        boltedFrame = new PartAssembler(2, 5, "Bolted Frame", 55.09, modularFrame);
        boltedFrame.add(reinforcedIronPlate, 3);
        boltedFrame.add(screw, 56);
        adheredIronPlate = new PartAssembler(1, 3.75, "Adhered Iron Plate", 55.09, reinforcedIronPlate);
        adheredIronPlate.add(ironPlate, 3);
        adheredIronPlate.add(rubber, 1);
        ocSupercomputer = new PartAssembler(1, 3, "OC Supercomputer", 55.09, supercomputer);
        ocSupercomputer.add(radioControlUnit, 2);
        ocSupercomputer.add(coolingSystem, 2);
        electricMotor = new PartAssembler(2, 7.5, "Electric Motor", 55.09, motor);
        electricMotor.add(electromagneticControlRod, 1);
        electricMotor.add(rotor, 2);
        alcladCasing = new PartAssembler(15, 112.5, "Alclad Casing", 55.09, aluminiumCasing);
        alcladCasing.add(aluminiumIngot, 20);
        alcladCasing.add(copperIngot, 10);
        plasticAILimiter = new PartAssembler(2, 8, "Plastic AI Limiter", 55.09, aiLimiter);
        plasticAILimiter.add(quickwire, 30);
        plasticAILimiter.add(plastic, 7);
        quickwireStator = new PartAssembler(2, 8, "Quickwire Stator", 55.09, stator);
        quickwireStator.add(steelPipe, 4);
        quickwireStator.add(quickwire, 15);
        cheapSilica = new PartAssembler(7, 52.5, "Cheap Silica", 55.09, silica);
        cheapSilica.add(rawQuartz, 3);
        cheapSilica.add(limestone, 5);
        steelRotor = new PartAssembler(1, 5, "Steel Rotor", 55.09, rotor);
        steelRotor.add(steelPipe, 2);
        steelRotor.add(wire, 6);
        encasedIndustrialPipe = new PartAssembler(1, 4, "Encased Industrial Pipe", 55.09, encasedIndustrialBeam);
        encasedIndustrialPipe.add(steelPipe, 6);
        encasedIndustrialPipe.add(concrete, 5);
        stitchedIronPlate = new PartAssembler(3, 5.625, "Stitched Iron Plate", 55.09, reinforcedIronPlate);
        stitchedIronPlate.add(ironPlate, 10);
        stitchedIronPlate.add(wire, 20);
        boltedIronPlate = new PartAssembler(3, 15, "Bolted Iron Plate", 55.09, reinforcedIronPlate);
        boltedIronPlate.add(ironPlate, 18);
        boltedIronPlate.add(screw, 50);
        fusedQuickwire = new PartAssembler(12, 90, "Fused Quick Wire", 55.09, quickwire);
        fusedQuickwire.add(cateriumIngot, 1);
        fusedQuickwire.add(copperIngot, 5);
        steeledFrame = new PartAssembler(3, 3, "Steeled Frame", 55.09, modularFrame);
        steeledFrame.add(reinforcedIronPlate, 2);
        steeledFrame.add(steelPipe, 10);
        heatExchanger = new PartAssembler(1, 10, "Heat Exchanger", 55.09, heatSink);
        heatExchanger.add(aluminiumCasing, 3);
        heatExchanger.add(rubber, 3);
        fineBlackPowder = new PartAssembler(6, 45, "Fine Black Powder", 55.09, blackPowder);
        fineBlackPowder.add(sulfur, 1);
        fineBlackPowder.add(compactedCoal, 2);
        electromagneticConnectionRod = new PartAssembler(2, 8, "Electromagnetic Connection Rod", 55.09, electromagneticControlRod);
        electromagneticConnectionRod.add(stator, 2);
        electromagneticConnectionRod.add(highSpeedConnector, 1);
        fineConcrete = new PartAssembler(10, 50, "Fine Concrete", 55.09, concrete);
        fineConcrete.add(silica, 3);
        fineConcrete.add(limestone, 12);
        crystalComputer = new PartAssembler(2, 3.333333, "Crystal Computer", 55.09, computer);
        crystalComputer.add(circuitBoard, 3);
        crystalComputer.add(crystalOscillator, 1);
        cateriumCircuitBoard = new PartAssembler(7, 8.75, "Caterium Circuit Board", 55.09, circuitBoard);
        cateriumCircuitBoard.add(plastic, 10);
        cateriumCircuitBoard.add(quickwire, 30);
        siliconCircuitBoard = new PartAssembler(5, 12.5, "Silicon Circuit Board", 55.09, circuitBoard);
        siliconCircuitBoard.add(copperSheet, 11);
        siliconCircuitBoard.add(silica, 11);
        quickwireCable = new PartAssembler(11, 27.5, "Quickwire Cable", 55.09, cable);
        quickwireCable.add(quickwire, 3);
        quickwireCable.add(rubber, 2);
        insulatedCable = new PartAssembler(20, 100, "Insulated Cable", 55.09,cable);
        insulatedCable.add(wire, 9);
        insulatedCable.add(rubber, 6);

        //Manufacturer
        plasticSmartPlating = new PartManufacturer(2, 5, "Plastic Smart Plating", 30.80, smartPlating);
        plasticSmartPlating.add(reinforcedIronPlate, 1);
        plasticSmartPlating.add(rotor, 1);
        plasticSmartPlating.add(plastic, 3);
        automatedSpeedWiring = new PartManufacturer(4, 7.5, "Automated Speed Wiring", 30.80, automatedWiring);
        automatedSpeedWiring.add(stator, 2);
        automatedSpeedWiring.add(wire, 40);
        automatedSpeedWiring.add(highSpeedConnector, 1);
        heavyFlexibleFrame = new PartManufacturer(1, 3.75, "Heavy Flexible Frame", 30.80, heavyModularFrame);
        heavyFlexibleFrame.add(modularFrame, 5);
        heavyFlexibleFrame.add(encasedIndustrialBeam, 3);
        heavyFlexibleFrame.add(rubber, 20);
        heavyFlexibleFrame.add(screw, 104);
        flexibleFramework = new PartManufacturer(2, 7.5, "Flexible Framework", 30.80, versatileFramework);
        flexibleFramework.add(modularFrame, 1);
        flexibleFramework.add(steelBeam, 6);
        flexibleFramework.add(rubber, 8);
        turboPressureMotor = new PartManufacturer(2, 3.75, "Turbo Pressure Motor", 30.80, turboMotor);
        turboPressureMotor.add(motor, 4);
        turboPressureMotor.add(pressureConversionCube, 1);
        turboPressureMotor.add(stator, 8);
        turboPressureMotor.add(packagedNitrogenGas, 24);
        superStateComputer = new PartManufacturer(1, 2.4, "Super-State Computer", 30.80, supercomputer);
        superStateComputer.add(computer, 3);
        superStateComputer.add(electromagneticControlRod, 1);
        superStateComputer.add(battery, 10);
        superStateComputer.add(wire, 25);
        radioControlSystem = new PartManufacturer(3, 4.5, "Radio Control System", 30.80, radioControlUnit);
        radioControlSystem.add(crystalOscillator, 1);
        radioControlSystem.add(circuitBoard, 10);
        radioControlSystem.add(aluminiumCasing, 60);
        radioControlSystem.add(rubber, 30);
        classicBattery = new PartManufacturer(4, 30, "Classic Battery", 30.80, battery);
        classicBattery.add(sulfur, 6);
        classicBattery.add(alcladAluminiumSheet, 7);
        classicBattery.add(plastic, 8);
        classicBattery.add(wire, 12);
        turboElectricMotor = new PartManufacturer(3, 2.8125, "Turbo Electric Motor", 30.80, turboMotor);
        turboElectricMotor.add(motor, 7);
        turboElectricMotor.add(radioControlUnit, 9);
        turboElectricMotor.add(electromagneticControlRod, 5);
        turboElectricMotor.add(rotor, 7);
        radioConnectionUnit = new PartManufacturer(1, 3.75, "Radio Connection Unit", 30.80, radioControlUnit);
        radioConnectionUnit.add(heatSink, 4);
        radioConnectionUnit.add(highSpeedConnector, 2);
        radioConnectionUnit.add(quartzCrystal, 12);
        rigorMotor = new PartManufacturer(6, 7.5, "Rigor Motor", 30.80, motor);
        rigorMotor.add(rotor, 3);
        rigorMotor.add(stator, 3);
        rigorMotor.add(crystalOscillator, 1);
        siliconHighSpeedConnector = new PartManufacturer(2, 3, "Silicon High-Speed Connector", 30.80, highSpeedConnector);
        siliconHighSpeedConnector.add(quickwire, 60);
        siliconHighSpeedConnector.add(silica, 25);
        siliconHighSpeedConnector.add(circuitBoard, 2);
        heavyEncasedFrame = new PartManufacturer(3, 2.8125, "Heavy Encased Frame", 30.80, heavyModularFrame);
        heavyEncasedFrame.add(modularFrame, 8);
        heavyEncasedFrame.add(encasedIndustrialBeam, 10);
        heavyEncasedFrame.add(steelPipe, 36);
        heavyEncasedFrame.add(concrete, 22);
        insulatedCrystalOscillator = new PartManufacturer(1, 1.875, "Insulated Crystal Oscillator", 30.80, crystalOscillator);
        insulatedCrystalOscillator.add(quartzCrystal, 10);
        insulatedCrystalOscillator.add(rubber, 7);
        insulatedCrystalOscillator.add(aiLimiter, 1);
        cateriumComputer = new PartManufacturer(1, 3.75, "Caterium Computer", 30.80, computer);
        cateriumComputer.add(circuitBoard, 4);
        cateriumComputer.add(quickwire, 14);
        cateriumComputer.add(rubber, 6);
    }
}
