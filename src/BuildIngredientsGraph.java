import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildIngredientsGraph {
    private Digraph<VertexWrapper, EdgeWrapper> g;
    private HashMap<VertexWrapper, Double> byproductSurplus;

    public BuildIngredientsGraph(Digraph<VertexWrapper, EdgeWrapper> g) {
        this.g = g;
    }

    public HashMap<AbstractProduct, VertexWrapper> buildIngredientsGraph(Map<AbstractProduct, Double> targets, HashMap<VertexWrapper, Double> byproductSurplus) {
        this.byproductSurplus = byproductSurplus;

        Calculator calculator = new Calculator();
        Vertex<VertexWrapper> returnvertex = null;
        HashMap<AbstractProduct, Double> verticesAmount = new HashMap<>();
        HashMap<AbstractProduct, Double> buildingsAmount = new HashMap<>();
        for(Map.Entry<AbstractProduct, Double> target : targets.entrySet()) {
            HashMap<AbstractProduct, Double> tempAmount = calculator.getIngredientsAPM(target.getKey(), target.getValue());
            HashMap<AbstractProduct, Double> tempBuilding = calculator.getBuildingCount(target.getKey(), target.getValue());
            mergeAPMMaps(verticesAmount, tempAmount);
            mergeAPMMaps(buildingsAmount, tempBuilding);
        }

        HashMap<AbstractProduct, VertexWrapper> vertices = new HashMap<>();

        for (Map.Entry<AbstractProduct, Double> entry : verticesAmount.entrySet()) {

            VertexWrapper vertex = new VertexWrapper(entry.getKey(), entry.getValue(), buildingsAmount.get(entry.getKey()));

            vertices.put(entry.getKey(),vertex );
            Vertex<VertexWrapper>realvertex = g.insertVertex(vertex);
        }

        HashMap<AbstractProduct, VertexWrapper> verticesWithLoops = addByProducts(vertices);
        addVerticesEdges(verticesWithLoops);

        return verticesWithLoops;
    }
    private HashMap<AbstractProduct, VertexWrapper> addByProducts(HashMap<AbstractProduct, VertexWrapper> productToVertexMap) {

        List<Runnable> graphModifications = new ArrayList<>();
        for(VertexWrapper resource : byproductSurplus.keySet()) {
            g.insertVertex(resource);
        }

        Calculator calculatorPart = new Calculator();
        for (VertexWrapper currentVertex : new ArrayList<>(productToVertexMap.values())) {
            AbstractProduct product = currentVertex.getProduct();
            if (product instanceof PartRefinery partRefinery) {
                String byProductString = partRefinery.getByProduct();
                if (byProductString == null) continue; // Skip if no byproduct

                AbstractProduct byProduct = Recipes.recipes.get(byProductString);
                if (byProduct == null) continue;

                // Calculate how much byproduct is being generated
                double outputFactor = currentVertex.getProductAmount() / partRefinery.getApm();
                double normalByproductRate = (partRefinery.getByProductAmount() / partRefinery.getAmount()) * partRefinery.getApm();
                double generatedByproductRate = outputFactor * normalByproductRate;


                // Create a new "source" vertex for the byproduct. This makes the graph clearer.
                // Note: buildingAmount is 0 as it's a byproduct of an existing building.
                VertexWrapper byproductSourceVertex = new VertexWrapper(byProduct, generatedByproductRate, 0);
                // Add the generated amount to our surplus map
                byproductSurplus.merge(byproductSourceVertex, generatedByproductRate, Double::sum);

                // Defer graph modifications to avoid issues
                graphModifications.add(() -> {
                    g.insertVertex(byproductSourceVertex);
                    // Edge from the refinery process to the new byproduct source node
                    g.insertEdge(currentVertex, byproductSourceVertex, new EdgeWrapper(currentVertex, byproductSourceVertex, generatedByproductRate));
                });
            } else if(product instanceof PartBlender partBlender) {
                String byProductString = partBlender.getByProduct();
                if (byProductString == null) continue; // Skip if no byproduct

                AbstractProduct byProduct = Recipes.recipes.get(byProductString);
                if (byProduct == null) continue;

                // Calculate how much byproduct is being generated
                double outputFactor = currentVertex.getProductAmount() / partBlender.getApm();
                double normalByproductRate = (partBlender.getByProductAmount() / partBlender.getAmount()) * partBlender.getApm();
                double generatedByproductRate = outputFactor * normalByproductRate;


                // Create a new "source" vertex for the byproduct. This makes the graph clearer.
                // Note: buildingAmount is 0 as it's a byproduct of an existing building.
                VertexWrapper byproductSourceVertex = new VertexWrapper(byProduct, generatedByproductRate, 0);
                // Add the generated amount to our surplus map
                byproductSurplus.merge(byproductSourceVertex, generatedByproductRate, Double::sum);

                // Defer graph modifications to avoid issues
                graphModifications.add(() -> {
                    g.insertVertex(byproductSourceVertex);
                    // Edge from the refinery process to the new byproduct source node
                    g.insertEdge(currentVertex, byproductSourceVertex, new EdgeWrapper(currentVertex, byproductSourceVertex, generatedByproductRate));
                });
            }
        }
        for (Map.Entry<VertexWrapper, Double> surplusEntry : byproductSurplus.entrySet()) {
            AbstractProduct byProduct = surplusEntry.getKey().getProduct();
            double surplusAmount = surplusEntry.getValue();

            // Find the vertex that *consumes* this byproduct
            VertexWrapper consumerVertex = productToVertexMap.get(byProduct);

            if (consumerVertex != null) {
                double originalAmount = consumerVertex.getProductAmount();
                double newAmount = Math.max(0, originalAmount - surplusAmount);
                consumerVertex.setProductAmount(newAmount);

                // IMPORTANT: If you reduce the need for an item, you also reduce the number of buildings
                // required to make it. This requires recalculating upstream.
                // For simplicity here, we adjust the building count proportionally. A more advanced
                // solution might involve re-running your Calculator.
                if (originalAmount > 0) {
                    double ratio = newAmount / originalAmount;
                    consumerVertex.setBuildingAmount(consumerVertex.getBuildingAmount() * ratio);
                }
                HashMap<AbstractProduct,Double> verticesNewAmount = calculatorPart.getIngredientsAPM(byProduct, newAmount);
                HashMap<AbstractProduct,Double> verticesNewBuildingsAmount = calculatorPart.getBuildingCount(byProduct, newAmount);
                for(Map.Entry<AbstractProduct,Double> entry : verticesNewAmount.entrySet()) {
                    if(!entry.getKey().equals(byProduct)) {
                        productToVertexMap.get(entry.getKey()).setProductAmount(entry.getValue());
                        productToVertexMap.get(entry.getKey()).setBuildingAmount(verticesNewBuildingsAmount.get(entry.getKey()));
                    }
                }
            }
        }
        graphModifications.forEach(Runnable::run);
        return productToVertexMap;
    }
    private void addVerticesEdges(HashMap<AbstractProduct, VertexWrapper> vertices) {


        //Iterate over every Vertex of the Graph, to add its corresponding edges
        for(Map.Entry<AbstractProduct, VertexWrapper> entry : vertices.entrySet()) {
            //No edges required for AbstractBaseIngredient
            if(entry.getKey() instanceof AbstractPart) {
                AbstractPart targetPart = (AbstractPart) entry.getKey();
                VertexWrapper currentVertex = entry.getValue();
                HashMap<AbstractProduct, Double> directIngredients = targetPart.resolveIngredients();
                double outputFactor = currentVertex.getProductAmount() / targetPart.getApm();

                //Iterate over needed ingredients
                for (Map.Entry<AbstractProduct, Double> ingredientEntry : directIngredients.entrySet()) {

                    AbstractProduct ingredient = ingredientEntry.getKey();
                    double amountNeededPerCraft = ingredientEntry.getValue();

                    // Calculate the standard input rate for this ingredient based on the recipe.
                    // Formula: normalInput = (input_amount_per_craft / output_amount_per_craft) * output_rate_per_recipe
                    double normalInput = (amountNeededPerCraft / targetPart.getAmount()) * targetPart.getApm();
                    // Calculate the actual required rate for this ingredient and push it to the stack.
                    double requiredIngredientRate = normalInput * outputFactor;

                    //check if ingredient is available as resource, then add corresponding edges
                    for(VertexWrapper ingredientVertex : byproductSurplus.keySet()) {
                        if(ingredientVertex.getProduct().equals(ingredient)) {
                            requiredIngredientRate = Math.max(0, requiredIngredientRate - byproductSurplus.get(ingredientVertex));
                            g.insertEdge(vertices.get(targetPart), ingredientVertex, new EdgeWrapper(vertices.get(targetPart), ingredientVertex, byproductSurplus.get(ingredientVertex)));
                        }
                    }
                    g.insertEdge(vertices.get(targetPart), vertices.get(ingredient), new EdgeWrapper(vertices.get(targetPart), vertices.get(ingredient), requiredIngredientRate));

                }
            }
        }

        //Old iterative edge algorithm

        //record ProductRatePair(AbstractProduct product, double rate) {}
        //Deque<ProductRatePair> productsToProcess = new ArrayDeque<>();

        //productsToProcess.push(new ProductRatePair(target, rate));

        /*while (!productsToProcess.isEmpty()) {

            ProductRatePair current = productsToProcess.pop();
            AbstractProduct currentProduct = current.product();
            //####### This is for the iterative Part of the Algorithm
            //double currentRate = current.rate();
            //####### But I already calculated this in the Calulator recursive algorithm and I am now overwriting the amount with the byProducts algorithm. This is cursed I wanna fix this.
            //####### This is now even more cursed, I am backwards mapping a map from value to key :(
            //####### Ok the really ugly part is gone now -> youve never seen it, but this is still a big mess
            double currentRate = vertices.get(currentProduct).getProductAmount();

            if (currentProduct instanceof AbstractPart) {
                AbstractPart targetPart = (AbstractPart) currentProduct;
                double outputFactor = currentRate / targetPart.getApm();

                HashMap<AbstractProduct, Double> directIngredients = targetPart.resolveIngredients();
                for (Map.Entry<AbstractProduct, Double> ingredientEntry : directIngredients.entrySet()) {

                    AbstractProduct ingredient = ingredientEntry.getKey();
                    double amountNeededPerCraft = ingredientEntry.getValue();

                    // Calculate the standard input rate for this ingredient based on the recipe.
                    // Formula: normalInput = (input_amount_per_craft / output_amount_per_craft) * output_rate_per_recipe
                    double normalInput = (amountNeededPerCraft / targetPart.getAmount()) * targetPart.getApm();
                    // Calculate the actual required rate for this ingredient and push it to the stack.
                    double requiredIngredientRate = normalInput * outputFactor;
                    for(VertexWrapper ingredientVertex : byproductSurplus.keySet()) {
                        if(ingredientVertex.getProduct().equals(ingredient)) {
                            requiredIngredientRate = Math.max(0, requiredIngredientRate - byproductSurplus.get(ingredientVertex));
                            g.insertEdge(vertices.get(targetPart), ingredientVertex, new EdgeWrapper(vertices.get(targetPart), ingredientVertex, byproductSurplus.get(ingredientVertex)));
                        }
                    }
                    productsToProcess.push(new ProductRatePair(ingredient, requiredIngredientRate));
                    g.insertEdge(vertices.get(targetPart), vertices.get(ingredient), new EdgeWrapper(vertices.get(targetPart), vertices.get(ingredient), requiredIngredientRate));
                }
            }
        }*/
    }
    public void clearGraph() {
        for(Vertex v : g.vertices()) {
            g.removeVertex(v);
        }
        for(Edge e : g.edges()) {
            g.removeEdge(e);
        }
    }

    public HashMap<VertexWrapper, Double> getByproductSurplus() {
        return byproductSurplus;
    }

    public Digraph<VertexWrapper, EdgeWrapper> getG() {
        return g;
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
