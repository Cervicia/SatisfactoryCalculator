import java.util.*;

public class Calculator {

    public Calculator() {
    }
//target = Prdouct to produce, rate = targeted Rate per Minute
    public HashMap<AbstractProduct, Double>  getIngredientsAPM(AbstractProduct target, double rate) {
        if(target instanceof AbstractBaseIngredient) {
            return new HashMap<>() {{put(target, rate);}};
        } else {
            AbstractPart target_part = (AbstractPart) target;
            double output_factor = rate/target.getApm();
            HashMap<AbstractProduct, Double> tempMap = new HashMap<>();
            HashMap<AbstractProduct, Double> resolvedIngredients = target_part.resolveIngredients();
            for(Map.Entry<AbstractProduct, Double> ingredient : resolvedIngredients.entrySet()) {
                double normalInput = (ingredient.getValue()/target_part.getAmount())*target_part.getApm();
                tempMap = mergeAPMMaps(tempMap ,getIngredientsAPM(ingredient.getKey(), normalInput*output_factor));
            }
            return mergeAPMMaps(tempMap, new HashMap<>() {{put(target, rate);}}) ;
        }

    }
    public HashMap<AbstractProduct, Double> getBuildingCount(AbstractProduct target, double rate) {
        HashMap<AbstractProduct, Double>  tempMap = new HashMap<>();
        HashMap<AbstractProduct, Double> ingredients = getIngredientsAPM(target, rate);
        for(Map.Entry<AbstractProduct, Double> ingredient : ingredients.entrySet()) {
                double count = ingredient.getValue()/ingredient.getKey().getApm();
                tempMap.put(ingredient.getKey(), count);
            }
        return tempMap;
        }
    private HashMap<AbstractProduct, Double> mergeAPMMaps(HashMap<AbstractProduct, Double> map1, HashMap<AbstractProduct, Double> map2) {
        for(Map.Entry<AbstractProduct, Double> entry : map2.entrySet()) {
            if(map1.containsKey(entry.getKey())) {
                map1.put(entry.getKey(), map1.get(entry.getKey()) + entry.getValue());
            } else {
                map1.put(entry.getKey(), entry.getValue());
            }
        }
        return map1;
    }

}
