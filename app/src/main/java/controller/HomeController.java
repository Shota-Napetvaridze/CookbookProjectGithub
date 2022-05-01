package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import main.DBUtils;
import main.MyListener;
import model.Recipe;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Button logout;

    @FXML
    private Button msg;

    @FXML
    private Button home;

    @FXML
    private Button favorites;

    @FXML
    private Button filter;

    @FXML
    private Button plan;

    @FXML
    private Button search;

    @FXML
    private TextField searchField;

    @FXML
    private ScrollPane scroll;

    @FXML
    private ImageView recipeImg;

    @FXML
    private Label recipeLbl;

    @FXML
    private GridPane grid;

    @FXML
    void select(ActionEvent event) {
        String s = comboBox.getSelectionModel().getSelectedItem().toString();
        if (s.startsWith("S")){
            comboBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    DBUtils.changeScene(event, "/main/fxmlFiles/login.fxml", null, null);
                }
            });

        } else if (s.startsWith("P")){
            comboBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    comboBox.setStyle("-fx-color: rgb(239, 242, 255)"+
                            "-fx-background-color: rgb(15, 125, 242)");
                }
            });

        }
    }


    private List<Recipe> recipeList = new ArrayList<>();
    private Image image;
    private MyListener myListener;




    private List<Recipe> getRecipeList(){
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe;

        for (int i = 0; i < 6; i++){
            recipe = new Recipe();
            recipe.setName("Lasagna");
            recipe.setImgSrc("/main/recipeImages/Lasagna.jpeg");
            recipe.setRecipeId("1233aac9-4298-4fca-a58d-cbca40ff4f85");
            recipe.setUserId("asdasfasg");
            recipes.add(recipe);
        }
        for (int i = 0; i < 6; i++){
            recipe = new Recipe();
            recipe.setName("Banana Pancakes");
            recipe.setImgSrc("/main/recipeImages/banana-pancakes.png");
            recipe.setRecipeId("b893b521-f285-4581-a651-afec52d22416");
            recipes.add(recipe);

        }

        for (int i = 0; i < 6; i++){
            recipe = new Recipe();
            recipe.setName("Beef Curry");
            recipe.setImgSrc("/main/recipeImages/beef-curry.png");
            recipe.setRecipeId("123456789");
            recipes.add(recipe);

        }

        return recipes;
    }


    private void chosenRecipe(Recipe recipe){
        image = new Image(getClass().getResourceAsStream(recipe.getImgSrc()));
        recipeImg.setImage(image);
        recipeLbl.setText(recipe.getName());
    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        home.setStyle("-fx-color: rgb(239, 242, 255)"+
                "-fx-background-color: rgb(15, 125, 242)");

        ObservableList<String> list = FXCollections.observableArrayList("Settings", "Profile");
        comboBox.setItems(list);



        recipeList.addAll(getRecipeList());

        if (recipeList.size() > 0){
            chosenRecipe(recipeList.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Recipe recipe) {
                    chosenRecipe(recipe);
                }

                @Override
                public void favClickListener(Recipe recipe) {
                    DBUtils.addToFavorites(recipe.getRecipeId(), recipe.getUserId());
                }
            };
        }

        int column = 0;
        int row = 0;
        try {
            for(int i = 0; i<recipeList.size(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/main/fxmlFiles/recipeItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                RecipeController recipeController = fxmlLoader.getController();
                recipeController.setData(recipeList.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                //Set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //Set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);


                GridPane.setMargin(anchorPane, new Insets(10));
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                home.setStyle("-fx-color: rgb(239, 242, 255)"+
                        "-fx-background-color: rgb(15, 125, 242)");
                favorites.setStyle("-fx-background-color: rgb(254, 215, 0)");
                plan.setStyle("-fx-background-color: rgb(254, 215, 0)");

            }
        });


        favorites.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                favorites.setStyle("-fx-color: rgb(239, 242, 255)"+
                        "-fx-background-color: rgb(15, 125, 242)");
                home.setStyle("-fx-background-color: rgb(254, 215, 0)");
                plan.setStyle("-fx-background-color: rgb(254, 215, 0)");

            }
        });

        plan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                plan.setStyle("-fx-color: rgb(239, 242, 255)"+
                        "-fx-background-color: rgb(15, 125, 242)");
                home.setStyle("-fx-background-color: rgb(254, 215, 0)");
                favorites.setStyle("-fx-background-color: rgb(254, 215, 0)");

            }
        });

        msg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { DBUtils.changeScene(event, "/main/fxmlFiles/login.fxml", null, null);}
        });




        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/main/fxmlFiles/login.fxml", null, null);
            }
        });



    }
}
