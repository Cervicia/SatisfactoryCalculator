
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

    SmartGraphPanel<String, String> graphView;
    Digraph<String, String> g;

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

        buildIngredientsGraph(Recipes.recipes.get("Reinforced Iron Plate"), 1);

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




        SmartPlacementStrategy initialPlacement = new SmartCircularSortedPlacementStrategy();
        ForceDirectedLayoutStrategy<String> automaticPlacementStrategy = new ForceDirectedSpringGravityLayoutStrategy<>();

        graphView = new SmartGraphPanel<>(g, initialPlacement, automaticPlacementStrategy);



        /*
        After creating, you can change the styling of some element.
        This can be done at any time afterwards.
        */
        //if (g.numVertices() > 0) {
        //    graphView.getStylableVertex("PartAssembler Reinforced Iron Plate").setStyleInline("-fx-fill: gold; -fx-stroke: brown;");
        //}


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
        graphView.setVertexDoubleClickAction((SmartGraphVertex<String> graphVertex) -> {
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
            if(rateInput.getText() != null && NumberUtils.isParsable(rateInput.getText())) {
                buildIngredientsGraph(Recipes.recipes.get(searchBar.getValue()), Integer.parseInt(rateInput.getText()));
            } else {
                buildIngredientsGraph(Recipes.recipes.get(searchBar.getValue()), 1);
            }

            graphView.update();
        }


    }

    private void buildIngredientsGraph(AbstractProduct target, double rate) {
        Calculator calculator = new Calculator();

        HashMap<AbstractProduct, Double> verticesAmount = calculator.getIngredientsAPM(target, rate);
        HashMap<AbstractProduct, Double> buildingsAmount = calculator.getBuildingCount(target, rate);
        HashMap<AbstractProduct, VertexWrapper> vertices = new HashMap<>();

        for (Map.Entry<AbstractProduct, Double> entry : verticesAmount.entrySet()) {
            VertexWrapper vertex = new VertexWrapper(entry.getKey(), entry.getValue(), buildingsAmount.get(entry.getKey()));
            vertices.put(entry.getKey(), vertex);
            g.insertVertex(vertex.toString());
        }


        addVerticesEdges(vertices,target, rate);



        /*g.insertVertex("A");
        g.insertVertex("B");
        g.insertVertex("C");
        g.insertVertex("D");
        g.insertVertex("E");
        g.insertVertex("F");

        g.insertEdge("A", "B", "AB");
        g.insertEdge("B", "A", "AB2");
        g.insertEdge("A", "C", "AC");
        g.insertEdge("A", "D", "AD");
        g.insertEdge("B", "C", "BC");
        g.insertEdge("C", "D", "CD");
        g.insertEdge("B", "E", "BE");
        g.insertEdge("F", "D", "DF");
        g.insertEdge("F", "D", "DF2");

        //yep, its a loop!
        g.insertEdge("A", "A", "Loop");*/
    }
    private void addVerticesEdges(HashMap<AbstractProduct,VertexWrapper> vertices,AbstractProduct target, double rate) {

        record ProductRatePair(AbstractProduct product, double rate) {}
        Deque<ProductRatePair> productsToProcess = new ArrayDeque<>();

        productsToProcess.push(new ProductRatePair(target, rate));
        int i = 0;
        while (!productsToProcess.isEmpty()) {

            i++;
            ProductRatePair current = productsToProcess.pop();
            AbstractProduct currentProduct = current.product();
            double currentRate = current.rate();

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
                    productsToProcess.push(new ProductRatePair(ingredient, requiredIngredientRate));
                    g.insertEdge(vertices.get(targetPart).toString(), vertices.get(ingredient).toString() , requiredIngredientRate + "/min" + i);
                }
            }
        }
    }
}
