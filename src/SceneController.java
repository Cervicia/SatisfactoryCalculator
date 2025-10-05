
import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.*;
import com.brunomnsilva.smartgraph.graphview.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.ListChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.Pane;

import javafx.scene.layout.VBox;
import org.apache.commons.lang3.math.NumberUtils;
import org.controlsfx.control.SearchableComboBox;
import java.util.stream.Collectors;
import javafx.geometry.Insets;


import java.util.*;

public class SceneController {

    SmartGraphPanel<VertexWrapper, String> graphView;
    Digraph<VertexWrapper, String> g;
    private Vertex<VertexWrapper> targetVertex;
    private HashMap<VertexWrapper, Double> byproductSurplus;

    public enum style {
        TARGET,
        BASE,
        RESOURCE;
    }

    @FXML
    private Pane graphContainer;
    @FXML
    private SearchableComboBox searchBar;
    @FXML
    private TextField rateInput;
    @FXML
    private MenuButton searchButton;



    public void up(ActionEvent actionEvent) {
        System.out.println("up");
    }

    @FXML
    public void initialize() {



        g = new DigraphEdgeList<>();
        SmartPlacementStrategy initialPlacement = new SmartCircularSortedPlacementStrategy();
        ForceDirectedLayoutStrategy<VertexWrapper> automaticPlacementStrategy = new ForceDirectedSpringGravityLayoutStrategy<>();

        buildIngredientsGraph(Recipes.recipes.get("Reinforced Iron Plate"), 1);
        graphView = new SmartGraphPanel<VertexWrapper, String>(g, initialPlacement, automaticPlacementStrategy);

        searchBar.setItems(FXCollections.observableArrayList(Recipes.recipes.keySet()));

        final ObservableList<String> allItems = FXCollections.observableArrayList(Recipes.alternativeRecipes.keySet());

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");

        final ObservableList<String> checkedItems = FXCollections.observableArrayList();
        FilteredList<String> filteredItems = new FilteredList<>(allItems, p -> true);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredItems.setPredicate(item -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                return item.toLowerCase().contains(newVal.toLowerCase());
            });
        });

        ListView<String> listView = new ListView<>(filteredItems);
        listView.setPrefHeight(150);
        listView.setPrefWidth(100);

        listView.setCellFactory(CheckBoxListCell.forListView(item -> {
            // Create a property to represent the checked state
            BooleanProperty checked = new SimpleBooleanProperty(checkedItems.contains(item));

            // Add a listener to the property. When the checkbox is clicked,
            // this listener will be notified.
            checked.addListener((obs, wasChecked, isNowChecked) -> {
                if (isNowChecked) {
                    // If the box is checked, add the item to our master list
                    if (!checkedItems.contains(item)) {
                        checkedItems.add(item);
                    }
                } else {
                    // If the box is unchecked, remove the item
                    checkedItems.remove(item);
                }
            });

            // Return the property to the cell
            return checked;
        }));

        checkedItems.addListener((ListChangeListener<String>) c -> {
            // REFRESH the ListView to show programmatic changes
            listView.refresh();

            Recipes.checkAlternativeRecipes(checkedItems.sorted());
            selectProduct(null);
            // Update the button text
            if (checkedItems.isEmpty()) {
                searchButton.setText("Select Items...");
            } else {
                // Sort for consistent display order
                searchButton.setText(checkedItems.stream().sorted().collect(Collectors.joining(", ")));
            }
        });



        VBox popupContent = new VBox(2, searchField, listView);
        popupContent.setPadding(new Insets(2));
        CustomMenuItem customMenuItem = new CustomMenuItem(popupContent);
        customMenuItem.setHideOnClick(false);
        searchButton.getItems().add(customMenuItem);








        /*
        After creating, you can change the styling of some element.
        This can be done at any time afterwards.
        */



        //Scene scene = new Scene(new SmartGraphDemoContainer(graphView), 1024, 768);


        graphContainer.getChildren().add(new SmartGraphDemoContainer(graphView));
        graphView.prefWidthProperty().bind(graphContainer.widthProperty());
        graphView.prefHeightProperty().bind(graphContainer.heightProperty());

        /*
        IMPORTANT: Must call init() after scene is displayed, so we can have width and height values
        to initially place the vertices according to the placement strategy.
        */
        javafx.application.Platform.runLater(() -> graphView.init());

        /*
        Bellow you can see how to attach actions for when vertices and edges are double-clicked
         */
        graphView.setVertexDoubleClickAction((SmartGraphVertex<VertexWrapper> graphVertex) -> {
            System.out.println("Vertex contains element: " + graphVertex.getUnderlyingVertex().element());

            //toggle different styling
            if (!graphVertex.removeStyleClass("myVertex")) {
                /* for the golden vertex, this is necessary to clear the inline
                css class. Otherwise, it has priority for included styles. Test and uncomment. */
                //graphVertex.setStyleInline(null);

                graphVertex.addStyleClass("myVertex");
            }
        });

        graphView.setEdgeDoubleClickAction(graphEdge -> {
            System.out.println("Edge contains element: " + graphEdge.getUnderlyingEdge().element());
            //dynamically change the style when clicked; style is propagated to the arrows
            graphEdge.setStyleClass("myEdge");
        });

    }

    public void selectProduct(ActionEvent event) {
        if(searchBar.getValue() != null) {
            for(Vertex v : g.vertices()) {
                g.removeVertex(v);
            }
            for(Edge e : g.edges()) {
                g.removeEdge(e);
            }

            HashMap<AbstractProduct, VertexWrapper> vertices = new HashMap<>();
            AbstractProduct target = Recipes.recipes.get(searchBar.getValue());
            if(rateInput.getText() != null && NumberUtils.isParsable(rateInput.getText())) {
                vertices = buildIngredientsGraph(target, Integer.parseInt(rateInput.getText()));
                //graphView.update();
                //graphView.getStylableVertex(t).setStyleInline("-fx-fill: gold; -fx-stroke: brown;");

            } else {
                vertices = buildIngredientsGraph(target, 1);
            }


            graphView.update();
            HashMap<Vertex<VertexWrapper>, style> verticesToStyle = new HashMap<>();
            for(Vertex<VertexWrapper> v : g.vertices()) {
                VertexWrapper element =  v.element();
                if(element.getProduct() == target) {
                    final Vertex<VertexWrapper> finalTarget = v;
                    verticesToStyle.put(finalTarget, style.TARGET);
                } else if(element.getProduct() instanceof AbstractBaseIngredient) {
                    final Vertex<VertexWrapper> finalBaseIngredient = v;
                    verticesToStyle.put(finalBaseIngredient, style.BASE);
                } else if(element.getBuildingAmount() == 0) {
                    final Vertex<VertexWrapper> finalResource = v;
                    verticesToStyle.put(finalResource, style.RESOURCE);
                }
            }
            javafx.application.Platform.runLater(() -> {
                for(Map.Entry<Vertex<VertexWrapper>, style> entry : verticesToStyle.entrySet()) {
                    SmartStylableNode stylableView = graphView.getStylableVertex(entry.getKey());
                    if (stylableView != null) {
                        if(entry.getValue() == style.BASE) {
                            stylableView.setStyleInline("-fx-fill: grey; -fx-stroke: darkgrey;");
                        }
                        if(entry.getValue() == style.RESOURCE) {
                            stylableView.setStyleInline("-fx-fill: greenyellow; -fx-stroke: forestgreen;");
                        } else if(entry.getValue() == style.TARGET) {
                            stylableView.setStyleInline("-fx-fill: gold; -fx-stroke: brown;");
                        }

                    } else {
                        // This might happen if the graph is cleared again before this code runs
                        System.err.println("Could not find the stylable vertex for: " + entry.getKey().element());
                    }
                }


            });
        }
    }

    private HashMap<AbstractProduct, VertexWrapper> buildIngredientsGraph(AbstractProduct target, double rate) {
        Calculator calculator = new Calculator();
        Vertex<VertexWrapper> returnvertex = null;
        HashMap<AbstractProduct, Double> verticesAmount = calculator.getIngredientsAPM(target, rate);
        HashMap<AbstractProduct, Double> buildingsAmount = calculator.getBuildingCount(target, rate);
        HashMap<AbstractProduct, VertexWrapper> vertices = new HashMap<>();

        for (Map.Entry<AbstractProduct, Double> entry : verticesAmount.entrySet()) {

            VertexWrapper vertex = new VertexWrapper(entry.getKey(), entry.getValue(), buildingsAmount.get(entry.getKey()));

            vertices.put(entry.getKey(),vertex );
            Vertex<VertexWrapper>realvertex = g.insertVertex(vertex);
            if (entry.getKey().equals(target)) {
                //graphView.getStylableVertex(realvertex).setStyleInline("-fx-fill: gold; -fx-stroke: brown;");
                returnvertex = realvertex;
            }
        }

        HashMap<AbstractProduct, VertexWrapper> verticesWithLoops = addByProducts(vertices);
        addVerticesEdges(verticesWithLoops,target, rate);

        return verticesWithLoops;
    }
    private HashMap<AbstractProduct, VertexWrapper>  addByProducts(HashMap<AbstractProduct, VertexWrapper> productToVertexMap) {
        int i = 0;

        byproductSurplus = new HashMap<>();
        List<Runnable> graphModifications = new ArrayList<>();

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
                    g.insertEdge(currentVertex, byproductSourceVertex, String.format("%.2f/min", generatedByproductRate));
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
                    g.insertEdge(currentVertex, byproductSourceVertex, String.format("%.2f/min", generatedByproductRate));
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
    private void addVerticesEdges(HashMap<AbstractProduct, VertexWrapper> vertices,AbstractProduct target, double rate) {

        record ProductRatePair(AbstractProduct product, double rate) {}
        Deque<ProductRatePair> productsToProcess = new ArrayDeque<>();

        productsToProcess.push(new ProductRatePair(target, rate));
        int i = 100;
        while (!productsToProcess.isEmpty()) {

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
                            g.insertEdge(vertices.get(targetPart), ingredientVertex, String.format("%.2f/min", byproductSurplus.get(ingredientVertex)) + i);
                            i++;
                        }
                    }
                    productsToProcess.push(new ProductRatePair(ingredient, requiredIngredientRate));
                    g.insertEdge(vertices.get(targetPart), vertices.get(ingredient), requiredIngredientRate + "/min" + i);
                    i++;
                }
            }
        }
    }
    public <K, V> Set<K> getKeys(Map<K, V> map, V value) {
        Set<K> keys = new HashSet<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }
    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
