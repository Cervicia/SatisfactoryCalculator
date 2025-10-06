
import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.*;
import com.brunomnsilva.smartgraph.graphview.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import java.util.*;

public class SceneController {

    SmartGraphPanel<VertexWrapper, EdgeWrapper> graphView;
    Digraph<VertexWrapper, EdgeWrapper> g;
    private ProductInputView productInput;
    private ProductInputView resourcesInput;


    public enum style {
        TARGET,
        BASE,
        RESOURCE;
    }

    @FXML
    private Pane graphContainer;
    @FXML
    private MenuButton placeholderButton;
    @FXML
    private VBox productsContainer;



    public void up(ActionEvent actionEvent) {
        System.out.println("up");
    }

    @FXML
    public void initialize() {


        //initialize Graph and build a demo one
        g = new DigraphEdgeList<>();
        SmartPlacementStrategy initialPlacement = new SmartCircularSortedPlacementStrategy();
        ForceDirectedLayoutStrategy<VertexWrapper> automaticPlacementStrategy = new ForceDirectedSpringGravityLayoutStrategy<>();

        BuildIngredientsGraph initialGraph = new BuildIngredientsGraph(g);
        initialGraph.buildIngredientsGraph(new HashMap<AbstractProduct, Double>(){{put(Recipes.recipes.get("Reinforced Iron Plate"), 1.0);}});
        g = initialGraph.getG();
        graphView = new SmartGraphPanel<VertexWrapper, EdgeWrapper>(g, initialPlacement, automaticPlacementStrategy);


        //Replace Placeholder with actual AlternativesRecipesView
        AlternateRecipesView alternateRecipesView = new AlternateRecipesView(this);
        alternateRecipesView.setPrefWidth(200);
        VBox parent = (VBox) placeholderButton.getParent();
        int index = parent.getChildren().indexOf(placeholderButton);
        parent.getChildren().remove(placeholderButton);
        parent.getChildren().add(index, alternateRecipesView);
        placeholderButton = alternateRecipesView;


        //add graphView to Placeholder graphContainer
        SmartGraphDemoContainer demoContainer = new SmartGraphDemoContainer(graphView);
        graphContainer.getChildren().add(demoContainer);
        demoContainer.prefWidthProperty().bind(graphContainer.widthProperty());
        demoContainer.prefHeightProperty().bind(graphContainer.heightProperty());
        //graphContainer.getChildren().add(new SmartGraphDemoContainer(graphView));
        //graphView.prefWidthProperty().bind(graphContainer.widthProperty());
        //graphView.prefHeightProperty().bind(graphContainer.heightProperty());


        //add ProductInputView to Placeholder productsContainer
        productInput = new ProductInputView(this, "Product");
        productInput.setPadding(new Insets(10));
        productInput.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dcdcdc; -fx-border-width: 1;");
        productInput.setPrefWidth(400);
        productsContainer.getChildren().add(productInput);

        resourcesInput = new ProductInputView(this, "Resource");
        resourcesInput.setPadding(new Insets(10));
        resourcesInput.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dcdcdc; -fx-border-width: 1;");
        resourcesInput.setPrefWidth(400);
        productsContainer.getChildren().add(resourcesInput);

        /*
        IMPORTANT: Must call init() after scene is displayed, so we can have width and height values
        to initially place the vertices according to the placement strategy.
        */
        javafx.application.Platform.runLater(() -> {
            graphView.init();
            // This enables the automatic vertex placement behavior
            graphView.setAutomaticLayout(true);
        });


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
        BuildIngredientsGraph newGraphLogic = new BuildIngredientsGraph(g);
        Map<String, Double> productsInputMap = productInput.getProductData();
        Map<AbstractProduct, Double> selectedProducts = new HashMap<>();

        if(!productsInputMap.isEmpty()) {
            productsInputMap.forEach((key, value) -> {selectedProducts.put(Recipes.recipes.get(key), value);});
            newGraphLogic.clearGraph();
            Map<AbstractProduct, VertexWrapper> vertices  = newGraphLogic.buildIngredientsGraph(selectedProducts);
            g = newGraphLogic.getG();
            graphView.update();

            HashMap<Vertex<VertexWrapper>, style> verticesToStyle = new HashMap<>();
            for(Vertex<VertexWrapper> v : g.vertices()) {
                VertexWrapper element =  v.element();
                if(selectedProducts.containsKey(element.getProduct())) {
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


    public void onRecipeSelected() {
        selectProduct(null);
    }
}
