package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import models.entities.Ingredient;
import models.entities.Recipe;
import services.IngredientService;
import services.impl.IngredientServiceImpl;
import util.common.UserListener;
import util.exceptions.recipe.InvalidRecipeIngredientsCountException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class CartController implements Initializable {
    private UserListener userListener;
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
    private Map<Ingredient, Integer> ingredientsList = new HashMap<>();




    private void initializeIngredientGrid(){
        ingredientsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ingredientsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Set<Ingredient> ingredients = ingredientsList.keySet();
        int column = 0;
        int row = 1;
        try {
            for (Ingredient ingredient : ingredients) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/ingredientForCart.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                IngredientCartController ingredientCartController = fxmlLoader.getController();
                Integer quantity = ingredientsList.get(ingredient);
                ingredientCartController.setData(ingredient, quantity, userListener);

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
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        initializeIngredientGrid();
    }
}
