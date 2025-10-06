import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.math.NumberUtils;
import org.controlsfx.control.SearchableComboBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A reusable JavaFX component that provides a dynamically expandable list
 * for adding products and their rates.
 */
public class ProductInputView extends VBox {

    private final VBox productRowsContainer;
    private final Button addProductButton;
    private final SceneController sceneController;
    private final String label;

    /**
     * Constructs the product input view.
     */
    public ProductInputView(SceneController sceneController, String label) {
        // --- Configure the main VBox (this component) ---
        super(5); // Set spacing for the outer VBox
        this.label = label;
        this.sceneController = sceneController;
        // --- Section Label ---
        Label productLabel = new Label("Search for " + label);
        productLabel.setStyle("-fx-font-weight: bold;");

        // --- Container for dynamic rows ---
        // This inner VBox will hold the product rows and the add button.
        productRowsContainer = new VBox(10); // Spacing between product rows

        // --- Add Product Button ---
        addProductButton = new Button("Add " + label);
        addProductButton.setMaxWidth(Double.MAX_VALUE); // Make button expand to full width

        // Set the action for the button to add a new row
        addProductButton.setOnAction(event -> addProductRow());

        // --- Initial Setup ---
        // Add the first product row initially
        productRowsContainer.getChildren().add(createProductRow());
        // Add the button to the container after the first row
        productRowsContainer.getChildren().add(addProductButton);

        // Add the label and the rows container to this main VBox
        this.getChildren().addAll(productLabel, productRowsContainer);
    }

    /**
     * Adds a new product row just above the 'Add Product' button.
     */
    private void addProductRow() {
        // Find the current position of the button in the container
        int buttonIndex = productRowsContainer.getChildren().indexOf(addProductButton);
        // Add a new product row just before the button.
        // This effectively pushes the button down.
        productRowsContainer.getChildren().add(buttonIndex, createProductRow());
    }

    /**
     * Creates a new product row, which consists of an HBox containing a
     * ComboBox for the product and a TextField for the rate.
     * @return An HBox representing a single product input row.
     */
    private HBox createProductRow() {
        // HBox to hold the ComboBox and TextField in a horizontal line
        HBox productRow = new HBox(10);
        productRow.setAlignment(Pos.CENTER_LEFT);

        // --- Product ComboBox ---
        // Replace this with SearchableComboBox if you have ControlsFX
        SearchableComboBox<String> productComboBox = new SearchableComboBox<String>();
        productComboBox.setItems(FXCollections.observableArrayList(
                FXCollections.observableArrayList(Recipes.recipes.keySet())
        ));

        //Set text and notifaction for SceneController
        productComboBox.setPromptText("Select " + label);
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        sceneController.onRecipeSelected();
                    }
                };

        productComboBox.setOnAction(event);
        // Allow the ComboBox to grow horizontally
        HBox.setHgrow(productComboBox, Priority.ALWAYS);
        productComboBox.setMaxWidth(Double.MAX_VALUE);

        // --- Rate TextField ---
        Label rateLabel = new Label("Rate:");
        TextField rateField = new TextField();
        rateField.setPromptText("e.g., 60");
        rateField.setPrefWidth(60); // Set a preferred width for the rate field
        rateField.setOnAction(event);

        //--- Delete Button ---
        Button clearButton = new Button("✖");
        clearButton.setFocusTraversable(false);
        clearButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-font-weight: bold;");
        //clearButton.setPadding(new Insets(0, 5, 0, 5));
        clearButton.setOnMouseEntered(e -> clearButton.setText("❌"));
        clearButton.setOnMouseExited(e -> clearButton.setText("✖"));
        clearButton.setOnAction(e -> {
            productRowsContainer.getChildren().remove(productRow);
        });

        // Add components to the HBox
        productRow.getChildren().addAll(productComboBox, rateLabel, rateField,  clearButton);

        return productRow;
    }

    // You could add a public method here to retrieve the data from all rows if needed,
    // for example: public List<ProductData> getProductData() { ... }
    public Map<String, Double> getProductData() {
        Map<String, Double> productData = new HashMap<>();
        for(Node selectedProduct: productRowsContainer.getChildren()) {
            if(selectedProduct instanceof HBox) {
                String product = null;
                double rate = 1;
                for(Node child: ((HBox) selectedProduct).getChildren()) {
                    if(child instanceof SearchableComboBox<?> searchBox) {
                        if(searchBox.getValue() != null) {
                            product = searchBox.getValue().toString();
                        }
                    } else if(child instanceof TextField rateInput) {
                        if(rateInput.getText() != null && NumberUtils.isParsable(rateInput.getText())) {
                            rate = Double.parseDouble(rateInput.getText());
                        }
                    }
                }
                if(product != null) {
                    productData.put(product, rate);
                }
            }

        }
        return productData;
    }
}
