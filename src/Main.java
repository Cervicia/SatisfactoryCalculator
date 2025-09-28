//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");
        BaseIngredientOre ironOre = new BaseIngredientOre(1,240,"Iron Ore");

        PartSmelter ironIngot = new PartSmelter(1, 30, "Iron Ingot");
        ironIngot.add(ironOre, 1);

        PartConstructor ironPlate = new PartConstructor(2, 20, "Iron Plate");
        ironPlate.add(ironIngot, 3);

        PartConstructor ironRod = new PartConstructor(1, 15, "Iron Rod");
        ironRod.add(ironIngot, 1);

        PartConstructor screws =  new PartConstructor(4, 40, "Screws");
        screws.add(ironRod, 1);

        PartAssembler reinforcedIronPlate = new PartAssembler(1, 5, "Iron Plate");
        reinforcedIronPlate.add(ironPlate, 6);
        reinforcedIronPlate.add(screws, 12);


    }
}