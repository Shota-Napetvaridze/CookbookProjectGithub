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
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.DBUtils;
import main.FileIo;
import main.MyListener;
import model.Ingredient;
import model.Message;
import model.Recipe;
import model.Tag;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private VBox Vbox;

    @FXML
    private AnchorPane filterPane;

    @FXML
    private Button logout;

    @FXML
    private Button msg;

    @FXML
    private Label msgCountLbl;

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
    private Button openDetailed;

    @FXML
    private Button addToPlan;

    @FXML
    private Button addNewRecipe;

    @FXML
    private ScrollPane scroll;

    @FXML
    private ImageView recipeImg;

    @FXML
    private Label recipeLbl;

    @FXML
    private GridPane grid;

    @FXML
    private GridPane gridTag;

    @FXML
    private GridPane gridIngredient;

    @FXML
    private Button sovietMode;

    @FXML
    private Label CookBookLbl;
    @FXML
    private ImageView sovietLogo;

    private Media media;


    private Image image;
    private MyListener myListener;

    //ComboBox --------------------------
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


    // Lists ----------------------------
    private List<Recipe> recipeList = new ArrayList<>();
    private List<Message> msgList = new ArrayList<>();
    private List<Recipe> favouriteRecipeList = new ArrayList<>();
    private List<Tag> tagList = new ArrayList<>();
    private List<Ingredient> ingredientsList = new ArrayList<>();



    // Get Items ----------------------------
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
        tag.setTagName("Vegetarian");
        tags.add(tag);

        tag = new Tag();
        tag.setTagName("Gluten-free");
        tags.add(tag);

        tag = new Tag();
        tag.setTagName("Pesto");
        tags.add(tag);

        tag = new Tag();
        tag.setTagName("Lactose-free");
        tags.add(tag);



        return tags;
    }

    private List<Ingredient> getIngredientList(){
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient;

        ingredient = new Ingredient();
        ingredient.setIngredientName("Beef");
        ingredients.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setIngredientName("pork");
        ingredients.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setIngredientName("Milk");
        ingredients.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setIngredientName("Carrot");
        ingredients.add(ingredient);

        return ingredients;
    }



    // Sets the image and the recipe name on the left side of the home page.
    private void chosenRecipe(Recipe recipe){
        image = new Image(getClass().getResourceAsStream(recipe.getImgSrc()));
        recipeImg.setImage(image);
        recipeLbl.setText(recipe.getName());
    }

    // Initial grid of the Home page.
    private void initializeGrid(){
        int column = 0;
        int row = 0;
        try {
            for(int i = 0; i < recipeList.size(); i++){
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

                grid.setVgap(2.0);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    // TODO: ---------------------------------------------
    private void createGrid(ArrayList list, GridPane grid, String fxml){
        int column = 0;
        int row = 0;
        try {
            for(int i = 0; i<list.size(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/main/fxmlFiles/recipeItem.fxml"));
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initial Home Button colour
        home.setStyle("-fx-color: rgb(239, 242, 255)"+
                "-fx-background-color: rgb(15, 125, 242)");

//        filterPane.setVisible(false);


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
                public void ingredientClickListener(Ingredient ingredient) {

                }

                @Override
                public void tagClickListener(Tag tag) {

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
                        fxmlLoader.setLocation(getClass().getResource("/main/fxmlFiles/tag.fxml"));
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
                        fxmlLoader.setLocation(getClass().getResource("/main/fxmlFiles/ingredient.fxml"));
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

//                filterPane.setVisible(true);
//                filterPane.setMaxHeight(300.0);
//                recipeLbl.setVisible(false);
//                recipeImg.setVisible(false);
//                openDetailed.setVisible(false);
//                addToPlan.setVisible(false);
//                addNewRecipe.setVisible(false);

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
                fxmlLoader.setLocation(getClass().getResource("/main/fxmlFiles/addNewRecipe.fxml"));
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
                        fxmlLoader.setLocation(getClass().getResource("/main/fxmlFiles/recipeItem.fxml"));
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
                        fxmlLoader.setLocation(getClass().getResource("/main/fxmlFiles/messageItem.fxml"));
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
                DBUtils.changeScene(event, "/main/fxmlFiles/login.fxml", null, null);
            }
        });

        sovietMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CookBookLbl.setText("Our Cookbook");
                Image image = new Image("/main/img/borch.jpeg");
                Image image2 = new Image("/main/img/sovietLogo.png");
                recipeImg.setImage(image);
                sovietLogo.setImage(image2);
                recipeLbl.setText("We only serve Borsch here!!!!!!");

                String musicFile = "app/src/main/resources/main/soviet.mp3";
                Media sound = new Media(new File(musicFile).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();

                grid.getChildren().clear();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/main/fxmlFiles/soviet.fxml"));
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


    }
}
