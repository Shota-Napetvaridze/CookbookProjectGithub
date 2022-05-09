package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import main.DBUtils;
import main.MyListener;
import model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    @FXML
    private Button addNewRecipe;

    @FXML
    private Button addToPlan;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Button favorites;

    @FXML
    private Button filter;

    @FXML
    private AnchorPane filterPane;

    @FXML
    private GridPane grid;

    @FXML
    private GridPane gridIngredient;

    @FXML
    private GridPane gridTag;

    @FXML
    private Button home;

    @FXML
    private Button logout;

    @FXML
    private Button msg;

    @FXML
    private Label msgCountLbl;

    @FXML
    private Button openDetailed;

    @FXML
    private Button plan;

    @FXML
    private ImageView recipeImg;

    @FXML
    private Label recipeLbl;

    @FXML
    private ScrollPane scroll;

    @FXML
    private ScrollPane scrollIngredient;

    @FXML
    private ScrollPane scrollTag;

    @FXML
    private Button search;

    @FXML
    private TextField searchField;


    @FXML
    private Label tagsLbl;



    private Image image;
    private MyListener myListener;

    @FXML
    private Button refresh_button;

    @FXML
    void select(ActionEvent event) {
        String s = comboBox.getSelectionModel().getSelectedItem().toString();
        if (s.startsWith("S")){
            comboBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    DBUtils.changeScene(event, "/fxmlFiles/login.fxml", null, null);
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

    // Lists ----------------------------
    private List<Recipe> recipeList = new ArrayList<>();
    private List<Message> msgList = new ArrayList<>();
    private List<Recipe> favouriteRecipeList = new ArrayList<>();
    private List<Tag> tagList = new ArrayList<>();
    private List<Ingredient> ingredientsList = new ArrayList<>();


    private List<Recipe> getRecipeList() {
        ArrayList<Recipe> DB_recipe_list = DBUtils.getRecipesFromDB();
        return DB_recipe_list;
    }

    private List<Message> getMsgList(){
        List<Message> messages = new ArrayList<>();
        Message message;

        message = new Message();
        message.setUserName("Shota");
        message.setUserMessage("Hellooooooo, how you doiiing?"); //TODO: get messages form database
        messages.add(message);

        message = new Message();
        message.setUserName("Ned");
        message.setUserMessage("how aree youuuu?");
        messages.add(message);

        message = new Message();
        message.setUserName("Norman");
        message.setUserMessage("I'm Fineee");
        messages.add(message);

        message = new Message();
        message.setUserName("Ned");
        message.setUserMessage("how aree youuuu?");
        messages.add(message);

        message = new Message();
        message.setUserName("Norman");
        message.setUserMessage("I'm Fineee");
        messages.add(message);

        message = new Message();
        message.setUserName("Ned");
        message.setUserMessage("how aree youuuu?");
        messages.add(message);

        message = new Message();
        message.setUserName("Norman");
        message.setUserMessage("I'm Fineee");
        messages.add(message);


        message = new Message();
        message.setUserName("Ned");
        message.setUserMessage("how aree youuuu?");
        messages.add(message);

        message = new Message();
        message.setUserName("Norman");
        message.setUserMessage("I'm Fineee");
        messages.add(message);

        message = new Message();
        message.setUserName("Ned");
        message.setUserMessage("how aree youuuu?");
        messages.add(message);

        message = new Message();
        message.setUserName("Norman");
        message.setUserMessage("I'm Fineee");
        messages.add(message);


        return messages;
    }


    private List<Tag> getTagList(){
        List<Tag> tags = new ArrayList<>();
        Tag tag;


        tag = new Tag();
        tag.setName("Vegetarian");
        tags.add(tag);

        tag = new Tag();
        tag.setName("Gluten-free");
        tags.add(tag);

        tag = new Tag();
        tag.setName("Pesto");
        tags.add(tag);

        tag = new Tag();
        tag.setName("Lactose-free");
        tags.add(tag);



        return tags;
    }

    private List<Ingredient> getIngredientList(){
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient;

        ingredient = new Ingredient();
        ingredient.setName("Beef");
        ingredients.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setName("pork");
        ingredients.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setName("Milk");
        ingredients.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setName("Carrot");
        ingredients.add(ingredient);

        return ingredients;
    }

    private void chosenRecipe(Recipe recipe){
        image = recipe.getRecipeImage();
        recipeImg.setImage(image);
        recipeLbl.setText(recipe.getName());
    }

    private void initializeGrid(){
        int column = 0;
        int row = 0;
        try {
            for(int i = 0; i < recipeList.size(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/recipeItem.fxml"));
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

                grid.setVgap(2.0);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        home.setStyle("-fx-color: rgb(239, 242, 255)"+
                "-fx-background-color: rgb(15, 125, 242)");


        // ComboBox User
        ObservableList<String> list = FXCollections.observableArrayList("Settings", "Profile");
        comboBox.setItems(list);

        // Adds all the recipes and messages
        recipeList.addAll(getRecipeList());
        msgList.addAll(getMsgList());
        tagList.addAll(getTagList());
        ingredientsList.addAll(getIngredientList());


        // Set message count
        String s = String.valueOf(msgList.size());
        msgCountLbl.setText(s);

        // When User clicks on a specific recipe, tag or ingredient
        if (recipeList.size() > 0){
            chosenRecipe(recipeList.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Recipe recipe) {
                    chosenRecipe(recipe);
                }

                @Override
                public void favClickListener(Recipe recipe, ImageView heartImage) {
                    DBUtils.addToFavorites(recipe.getRecipeId(), recipe.getUserId(), heartImage);
                    for (Recipe r: favouriteRecipeList){
                        if (recipe == r){
                            favouriteRecipeList.remove(recipe);
                        }
                    }
                    favouriteRecipeList.add(recipe);
                }

                @Override
                public void ingredientClickListener(Ingredient ingredient, Button ingredientButton) {
                    DBUtils.chosenIngredients(ingredient.getName(), ingredientButton);

                }

                @Override
                public void tagClickListener(Tag tag, Button tagButton) {
                    DBUtils.chosenTags(tag.getName(), tagButton);

                }
                @Override
                public void removeClickListener(Recipe recipe) {
                    DBUtils.removeRecipeFromHome(recipe.getRecipeId());
                    
                }

            };
        }

        // Initialize grid-----------------------------------------
        initializeGrid();


        //  Search Button-----------------------------------------

        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.searchRecipe(event, searchField.getText());
                grid.getChildren().clear();
            }
        });

        //  Filter Button-----------------------------------------

        filter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int column = 0;
                int row = 0;
                try {
                    for(int i = 0; i<tagList.size(); i++){
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/tag.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        TagController tagController = fxmlLoader.getController();
                        tagController.setData(tagList.get(i), myListener);

                        if (column == 1) {
                            column = 0;
                            row++;
                        }
                        gridTag.add(anchorPane, column++, row);
                        //Set grid width
                        gridTag.setMinWidth(Region.USE_COMPUTED_SIZE);
                        gridTag.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        gridTag.setMaxWidth(Region.USE_PREF_SIZE);

                        //Set grid height
                        gridTag.setMinHeight(Region.USE_COMPUTED_SIZE);
                        gridTag.setPrefHeight(Region.USE_COMPUTED_SIZE);
                        gridTag.setMaxHeight(Region.USE_PREF_SIZE);
                        GridPane.setMargin(anchorPane, new Insets(3));
                    }

                    for(int i = 0; i<ingredientsList.size(); i++){
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/ingredient.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        IngredientController ingredientController = fxmlLoader.getController();
                        ingredientController.setData(ingredientsList.get(i), myListener);

                        if (column == 1) {
                            column = 0;
                            row++;
                        }
                        gridIngredient.add(anchorPane, column++, row);
                        //Set grid width
                        gridIngredient.setMinWidth(Region.USE_COMPUTED_SIZE);
                        gridIngredient.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        gridIngredient.setMaxWidth(Region.USE_PREF_SIZE);

                        //Set grid height
                        gridIngredient.setMinHeight(Region.USE_COMPUTED_SIZE);
                        gridIngredient.setPrefHeight(Region.USE_COMPUTED_SIZE);
                        gridIngredient.setMaxHeight(Region.USE_PREF_SIZE);


                        GridPane.setMargin(anchorPane, new Insets(3));
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        });

        //  Open for a detailed view Button-----------------------------------------

        openDetailed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        //  Add to a plan Button-----------------------------------------

        addToPlan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        // Add New Recipe Button-----------------------------------------

        addNewRecipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                grid.getChildren().clear();
                recipeImg.setVisible(false);
                recipeLbl.setVisible(false);

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/addNewRecipe.fxml"));
                try {
                    AnchorPane anchorPane = fxmlLoader.load();
                    grid.add(anchorPane, 1, 1);
                    grid.setAlignment(Pos.CENTER);
                    grid.setStyle("-fx-background-color: #ffa9a9");

                    //Set grid width
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    //Set grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        // Home Button-----------------------------------------

        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                recipeImg.setVisible(true);
                recipeLbl.setVisible(true);
                home.setStyle("-fx-color: rgb(239, 242, 255)" +
                        "-fx-background-color: rgb(15, 125, 242)");
                favorites.setStyle("-fx-background-color: rgb(254, 215, 0)");
                plan.setStyle("-fx-background-color: rgb(254, 215, 0)");
                grid.getChildren().clear();
                grid.setStyle("-fx-background-color: #ffffff");
                initializeGrid();
            }
        });

        // Favourites Button-----------------------------------------

        favorites.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                favorites.setStyle("-fx-color: rgb(239, 242, 255)"+
                        "-fx-background-color: rgb(15, 125, 242)");
                home.setStyle("-fx-background-color: rgb(254, 215, 0)");
                plan.setStyle("-fx-background-color: rgb(254, 215, 0)");
                grid.getChildren().clear();

                int column = 0;
                int row = 0;
                try {
                    for(int i = 0; i<favouriteRecipeList.size(); i++){
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/recipeItem.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        RecipeController recipeController = fxmlLoader.getController();
                        recipeController.setData(favouriteRecipeList.get(i), myListener);

                        if (column == 1) {
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

            }
        });

        // Plan Button-----------------------------------------

        plan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                plan.setStyle("-fx-color: rgb(239, 242, 255)"+
                        "-fx-background-color: rgb(15, 125, 242)");
                home.setStyle("-fx-background-color: rgb(254, 215, 0)");
                favorites.setStyle("-fx-background-color: rgb(254, 215, 0)");
                grid.getChildren().clear();

            }
        });

        // Message Button-----------------------------------------
        msg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                home.setStyle("-fx-background-color: rgb(254, 215, 0)");
                favorites.setStyle("-fx-background-color: rgb(254, 215, 0)");
                plan.setStyle("-fx-background-color: rgb(254, 215, 0)");
                grid.getChildren().clear();
                msgCountLbl.setText("...");
                grid.setStyle("-fx-background-color: #ffffff");

                int column = 0;
                int row = 0;
                try {
                    for(int i = 0; i<msgList.size(); i++){
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/messageItem.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        MsgController msgController = fxmlLoader.getController();
                        msgController.setData(msgList.get(i));

                        if (column == 1) {
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
            }
        });



        // Logout Button-----------------------------------------

        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "fxmlFiles/login.fxml", null, null);
            }
        });

    }
}
