public class EdgeWrapper {
    private VertexWrapper startVertex;
    private VertexWrapper endVertex;
    double apm;

    public EdgeWrapper(VertexWrapper startVertex, VertexWrapper endVertex, double apm) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.apm = apm;
    }
    @Override
    public String toString() {
        String output = String.format("%.2f/min", apm);
        return output;
    }

}
