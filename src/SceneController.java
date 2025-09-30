
import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graphview.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneController {

    @FXML
    private Pane graphContainer;

    public void up(ActionEvent actionEvent) {
        System.out.println("up");
    }

    @FXML
    public void initialize() {

        Graph<String, String> g = build_sample_digraph();

        SmartPlacementStrategy initialPlacement = new SmartCircularSortedPlacementStrategy();
        ForceDirectedLayoutStrategy<String> automaticPlacementStrategy = new ForceDirectedSpringGravityLayoutStrategy<>();

        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, initialPlacement, automaticPlacementStrategy);



        /*
        After creating, you can change the styling of some element.
        This can be done at any time afterwards.
        */
        if (g.numVertices() > 0) {
            graphView.getStylableVertex("A").setStyleInline("-fx-fill: gold; -fx-stroke: brown;");
        }


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

    private Graph<String, String> build_sample_digraph() {

        Digraph<String, String> g = new DigraphEdgeList<>();

        g.insertVertex("A");
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
        g.insertEdge("A", "A", "Loop");

        return g;
    }
}
