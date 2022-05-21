package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import models.entities.Ingredient;
import models.entities.User;
import services.IngredientService;
import services.UserService;
import services.impl.IngredientServiceImpl;
import services.impl.UserServiceImpl;
import util.common.SceneContext;
import util.common.UserListener;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class CartController {
    private UserListener userListener;
    private User user = SceneContext.getUser();
    private Ingredient ingredient;

    @FXML
    private GridPane ingredientsGrid;

    @FXML
    private ScrollPane ingredientsScroll;

    @FXML
    void close(MouseEvent event) {
        userListener.closeCartListener();
    }

    private IngredientService ingredientService = new IngredientServiceImpl();
    private UserService userService = new UserServiceImpl();
    // private List<Entry<Ingredient, Integer>> ingredientsList = userService.getUserCartById(user.getId());
    private Map<Ingredient, Integer> ingredientsList = userService.getUserCartById(user.getId());

    private void initializeIngredientGrid(){
        ingredientsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ingredientsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Set<Ingredient> ingredients = ingredientsList.keySet();
        int column = 0;
        int row = 1;
        try {
            for (Ingredient ingredient : ingredients) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/ingredientItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                IngredientItemController ingredientItemController = fxmlLoader.getController();
                Integer quantity = ingredientsList.get(ingredient);
                ingredientItemController.setData(ingredient, quantity, "CartController", userListener);
                if (column == 1) {
                    column = 0;
                    row++;
                }
                ingredientsGrid.add(anchorPane, column++, row);
                // Set grid width
                ingredientsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                ingredientsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                ingredientsGrid.setMaxWidth(Region.USE_PREF_SIZE);

                // Set grid height
                ingredientsGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                ingredientsGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                ingredientsGrid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(3));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setData(UserListener userListener) {
        this.userListener = userListener;
        initializeIngredientGrid();

    }

}
