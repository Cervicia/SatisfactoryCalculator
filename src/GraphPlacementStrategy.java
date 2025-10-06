import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartGraphVertex;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class GraphPlacementStrategy implements SmartPlacementStrategy {

    private final int heightPadding = 120;
    private final int widthPadding = 200;

    public GraphPlacementStrategy() {
    }

    @Override
    public <V, E> void place(double width, double height, SmartGraphPanel<V, E> smartGraphPanel) {
        List<SmartGraphVertex<V>> vertices = new ArrayList<>(smartGraphPanel.getSmartVertices());
        vertices.sort((v1, v2) -> {
            VertexWrapper e1 = (VertexWrapper) v1.getUnderlyingVertex().element();
            VertexWrapper e2 = (VertexWrapper) v2.getUnderlyingVertex().element();
            return Integer.compare(e1.getLevel(), e2.getLevel());
        });


        Point2D rightCenter = new Point2D(width  - 100, (height / 2));
        int levelCounter = 0;
        int padding = 0;
        boolean top = true;
        Point2D p = null;

        for (SmartGraphVertex<V> vertex : vertices) {
            if(((VertexWrapper)vertex.getUnderlyingVertex().element()).getLevel() != levelCounter) {
                levelCounter++;
                padding = 0;
                top = true;
            }
            if(top) {
                p = new Point2D(rightCenter.getX() - levelCounter * widthPadding, rightCenter.getY() - padding*heightPadding);
                top = false;
                padding ++;
            } else {
                p = new Point2D(rightCenter.getX() - levelCounter * widthPadding, rightCenter.getY() + padding*heightPadding);
                top = true;
            }

            vertex.setPosition(p.getX(), p.getY());
        }

    }
}
