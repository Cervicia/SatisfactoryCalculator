import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MySmartGraphApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the graph
        Graph<String, String> g = new GraphEdgeList<>();
        Vertex<String> vA = g.insertVertex("A");
        Vertex<String> vB = g.insertVertex("B");
        Vertex<String> vC = g.insertVertex("C");
        Vertex<String> vD = g.insertVertex("D");
        g.insertEdge(vA, vB, "AB");
        g.insertEdge(vB, vC, "BC");
        g.insertEdge(vC, vD, "CD");
        g.insertEdge(vA, vD, "AD");
        g.insertEdge(vA, vC, "AC");

        // Create the visualization panel
        // The second argument is a properties string, e.g., for CSS or behavior
        // The third argument is the placement strategy
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, strategy);

        // Create a JavaFX scene and set the stage
        Scene scene = new Scene(graphView, 800, 600);
        primaryStage.setTitle("JavaFXSmartGraph Simple Example");
        primaryStage.setScene(scene);
        primaryStage.show();

        // After the stage is shown, you can typically initialize the view.
        // This is important because layout calculations often depend on the panel being in a scene.
        graphView.init(); //
    }

    public static void main(String[] args) {
        launch(args);
    }
}