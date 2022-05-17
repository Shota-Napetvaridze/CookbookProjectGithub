package controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import models.entities.Comment;
import models.entities.Ingredient;
import services.impl.IngredientServiceImpl;
import util.common.SceneContext;
import util.common.UserListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DetailedViewController implements Initializable {
    private UserListener userListener;

    @FXML
    private Button close;

    @FXML
    private GridPane commentsGrid;

    @FXML
    private ScrollPane commentsScroll;

    @FXML
    private AnchorPane detailedAnchor;

    @FXML
    private Button editButton;

    @FXML
    private GridPane ingredientsGrid;

    @FXML
    private ScrollPane ingredientScroll;

    @FXML
    private Label recipe_Description;

    @FXML
    private ImageView recipe_Img;

    @FXML
    private Label recipe_Instruction;

    @FXML
    private Label recipe_Name;

    @FXML
    private Button shareTheRecipe;

    @FXML
    private Label tagsTextArea;


    private IngredientServiceImpl ingredientService = new IngredientServiceImpl();

    private List<Ingredient> ingredientsList = ingredientService.getAllIngredients();
    private List<Ingredient> selectedIngredients = new ArrayList<>();
//    private List<Ingredient> commentsList = ingredientService.getAllComments(); //TODO: add getAllComments
    private List<Comment> commentsList = new ArrayList<>();


    private void initializeIngredientGrid(){
        ingredientScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < ingredientsList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/ingredientItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                IngredientItemController ingredientItemController = fxmlLoader.getController();
                ingredientItemController.setData(ingredientsList.get(i), userListener);

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
    private void initializeCommentsGrid(){
        commentsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < commentsList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/ingredientItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                CommentController commentController = fxmlLoader.getController();
                commentController.setData(commentsList.get(i), userListener);

                if (column == 1) {
                    column = 0;
                    row++;
                }
                commentsGrid.add(anchorPane, column++, row);
                // Set grid width
                commentsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                commentsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                commentsGrid.setMaxWidth(Region.USE_PREF_SIZE);

                // Set grid height
                commentsGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                commentsGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                commentsGrid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(3));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeIngredientGrid();
        initializeCommentsGrid();

        shareTheRecipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneContext.changeScene(event, "/fxmlFiles/home.fxml");
            }

        });
    }


}
