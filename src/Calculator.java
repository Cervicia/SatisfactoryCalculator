import java.util.*;

public class Calculator {

    public record NodeInfo(Double rate, int level) {}

    public Calculator() {
    }
//target = Prdouct to produce, rate = targeted Rate per Minute
    public HashMap<AbstractProduct, VertexWrapper> getIngredientsAPM(AbstractProduct target, VertexWrapper nodeInfo) {

        if(target instanceof AbstractBaseIngredient) {
            return new HashMap<>() {{put(target, new VertexWrapper(target, nodeInfo.getProductAmount(), nodeInfo.getBuildingAmount(), nodeInfo.getLevel()));}};
        } else {
            AbstractPart target_part = (AbstractPart) target;
            double output_factor = nodeInfo.getProductAmount()/target.getApm();
            HashMap<AbstractProduct, VertexWrapper> tempMap = new HashMap<>();
            HashMap<AbstractProduct, Double> resolvedIngredients = target_part.resolveIngredients();
            for(Map.Entry<AbstractProduct, Double> ingredient : resolvedIngredients.entrySet()) {
                double rate = (ingredient.getValue()/target_part.getAmount())*target_part.getApm()*output_factor;
                double count = rate/ingredient.getKey().getApm();
                tempMap = mergeAPMMaps(tempMap ,getIngredientsAPM(ingredient.getKey(), new VertexWrapper(ingredient.getKey(), rate, count, nodeInfo.getLevel()+1)));
            }
            return mergeAPMMaps(tempMap, new HashMap<>() {{put(target, new VertexWrapper(target, nodeInfo.getProductAmount(), nodeInfo.getBuildingAmount(), nodeInfo.getLevel()));}}) ;
        }

    }

    private HashMap<AbstractProduct, VertexWrapper> mergeAPMMaps(HashMap<AbstractProduct, VertexWrapper> map1, HashMap<AbstractProduct, VertexWrapper> map2) {
        for(Map.Entry<AbstractProduct, VertexWrapper> entry : map2.entrySet()) {
            if(map1.containsKey(entry.getKey())) {
                VertexWrapper newNodeInfo = new VertexWrapper(entry.getKey(), map1.get(entry.getKey()).getProductAmount() + entry.getValue().getProductAmount(), map1.get(entry.getKey()).getBuildingAmount() + entry.getValue().getBuildingAmount(), Math.max(map1.get(entry.getKey()).getLevel(), entry.getValue().getLevel()));
                map1.put(entry.getKey(),  newNodeInfo);
            } else {
                map1.put(entry.getKey(), entry.getValue());
            }
        }
        return map1;
    }

}
