import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.VBox;

import java.util.stream.Collectors;

public class AlternateRecipesView extends MenuButton {
    private final TextField searchField;
    private final VBox popupContent;
    private final ListView<String> listView;
    private final CustomMenuItem customMenuItem;
    private final SceneController sceneController;

    public AlternateRecipesView(SceneController sceneController) {
        this.sceneController = sceneController;
        final ObservableList<String> allItems = FXCollections.observableArrayList(Recipes.alternativeRecipes.keySet());

        this.setText("Search...");

        searchField = new TextField();
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

        listView = new ListView<>(filteredItems);
        listView.setPrefHeight(250);
        listView.setPrefWidth(200);

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

            notifySceneController(checkedItems);

            // Update the button text
            if (checkedItems.isEmpty()) {
                this.setText("Select Items...");
            } else {
                // Sort for consistent display order
                this.setText(checkedItems.stream().sorted().collect(Collectors.joining(", ")));
            }
        });



        popupContent = new VBox(2, searchField, listView);
        popupContent.setPadding(new Insets(2));
        customMenuItem = new CustomMenuItem(popupContent);
        customMenuItem.setHideOnClick(false);
        this.getItems().add(customMenuItem);
    }
    private void notifySceneController(ObservableList<String> checkedItems) {
        Recipes.checkAlternativeRecipes(checkedItems.sorted());
        sceneController.onRecipeSelected();
    }
}
