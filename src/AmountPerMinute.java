import org.json.simple.JSONArray;

import java.util.*;

public class AmountPerMinute {
    private HashSet<AbstractProduct> items;
    private AbstractPart start;
    private HashMap<AbstractBaseIngredient, Double> baseIngredientsAPM;
    private HashMap<AbstractPart, Double> partsAPM;
    private AbstractPartIterator iterator;

    public AmountPerMinute(HashSet<AbstractProduct> items, AbstractPart start) {
        this.items = items;
        this.start = start;
        this.baseIngredientsAPM = new HashMap<>();
        this.partsAPM = new HashMap<>();

    }
//target = Prdouct to produce, rate = targeted Rate per Minute
    public HashMap<AbstractProduct, Double>  getBaseIngredientsAPM(AbstractProduct target, double rate) {
        if(target instanceof AbstractBaseIngredient) {
            return new HashMap<>() {{put(target, rate);}};
        } else {
            AbstractPart target_part = (AbstractPart) target;
            double output_factor = rate/target.getApm();
            HashMap<AbstractProduct, Double> tempMap = new HashMap<>();
            for(AbstractProduct ingredient : target_part.getChildNodes().keySet()) {
                double normalInput = (ingredient.getAmount()/target_part.getAmount())*target_part.getApm();
                tempMap = mergeAPMMaps(tempMap ,getBaseIngredientsAPM(ingredient, normalInput*output_factor));
            }
            return tempMap;
        }

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
