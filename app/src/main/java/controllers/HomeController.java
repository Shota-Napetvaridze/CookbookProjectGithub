package controllers;

import javafx.animation.TranslateTransition;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import javafx.util.Duration;
import models.entities.Ingredient;
import models.entities.Message;
import models.entities.Recipe;
import models.entities.Tag;
import models.entities.User;

import services.impl.IngredientServiceImpl;
import services.impl.RecipeServiceImpl;
import services.impl.TagServiceImpl;
import services.impl.UserServiceImpl;
import util.common.SceneContext;
import util.common.UserListener;

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
    private Button openDetailed;

    @FXML
    private Button plan;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ImageView cart;

    @FXML
    private Label cartCount;

    @FXML
    private Button logout;

    @FXML
    private Button msg;

    @FXML
    private Label msgCountLbl;

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
    private Label filter;

    @FXML
    private Label filterBack;

    @FXML
    private AnchorPane anchorPaneBelowFilter;

    @FXML
    private TextField searchField;

    @FXML
    private Label tagsLbl;

    @FXML
    private Button searchIngredient;

    @FXML
    private Button searchTag;

    @FXML
    private TextField tagsSearchField;

    @FXML
    private Button removeFromPlan;

    private UserServiceImpl userService = new UserServiceImpl();
    private RecipeServiceImpl recipeService = new RecipeServiceImpl();
    private IngredientServiceImpl ingredientService = new IngredientServiceImpl();
    private TagServiceImpl tagService = new TagServiceImpl();

    private User user = SceneContext.user;
    private Recipe recipe;
    private Message message;
    private Image image;
    private UserListener userListener;
    private String date;

    @FXML
    void select(ActionEvent event) {
        String s = comboBox.getSelectionModel().getSelectedItem().toString();
        if (s.startsWith("S")) {
            comboBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    initializeSettingsGrid();
                }
            });

        } 
    }

    public void getTheDate(){
        LocalDate myDate = datePicker.getValue();
        String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
        this.date = myFormattedDate;
    }

    // Lists ----------------------------
    private List<Recipe> recipeList = recipeService.getAllRecipes();
    private List<Message> msgList = userService.getUserMessagesById(user.getId());
    private Map<Ingredient, Integer> cartList = userService.getUserCartById(user.getId());
    private List<Recipe> favouriteRecipeList = userService.getFavoriteRecipes(user.getId());
    private List<Recipe> planList = new ArrayList<>();

    private List<Ingredient> ingredientsList = ingredientService.getAllIngredients();
    private List<Ingredient> selectedIngredients = new ArrayList<>();
    private List<Tag> tagList = tagService.getAllTags();
    private List<Tag> selectedTags = new ArrayList<>();

    private void chosenRecipe(Recipe recipe) {
        this.recipe = recipe;
        image = recipe.getPicture();
        recipeImg.setImage(image);
        recipeLbl.setText(recipe.getName());
    }
    private void chosenMsg(Message message) {
        this.message = message;

    }
    private void getCart() {
        cartList = userService.getUserCartById(user.getId());
        cartCount.setText(String.valueOf(cartList.size()));
    }

    private void getMessages() {
        msgList = userService.getUserMessagesById(user.getId());
        msgCountLbl.setText(String.valueOf(msgList.size()));
    }

    private void initializeHomeGrid() {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < recipeList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/recipeItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                RecipeController recipeController = fxmlLoader.getController();
                if (user.getFavorites().contains(recipeList.get(i).getId())) {
                    String imgFile = "/img/heartFilled.png";
                    Image filled = new Image(getClass().getResourceAsStream(imgFile));
                    recipeController.setData(recipeList.get(i), filled, userListener);
                } else {
                    String imgFile = "/img/heartEmpty.png";
                    Image empty = new Image(getClass().getResourceAsStream(imgFile));
                    recipeController.setData(recipeList.get(i), empty, userListener);
                }
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.setStyle("-fx-background-color: #ffffff");
                grid.add(anchorPane, column++, row);
                // Set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                // Set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeFavouritesGrid() {
        grid.getChildren().clear();

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < favouriteRecipeList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/recipeItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                RecipeController recipeController = fxmlLoader.getController();
                String imgFile = "/img/heartFilled.png";
                Image filled = new Image(getClass().getResourceAsStream(imgFile));

                recipeController.setData(favouriteRecipeList.get(i), filled, userListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                // Set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                // Set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializePlanGrid() {
        grid.getChildren().clear();

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < planList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/recipeItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                RecipeController recipeController = fxmlLoader.getController();
                if (user.getFavorites().contains(planList.get(i).getId())) {
                    String imgFile = "/img/heartFilled.png";
                    Image filled = new Image(getClass().getResourceAsStream(imgFile));
                    recipeController.setTheDate(date);

                    recipeController.setData(recipeList.get(i), filled, userListener);
                } else {
                    String imgFile = "/img/heartEmpty.png";
                    Image empty = new Image(getClass().getResourceAsStream(imgFile));
                    recipeController.setTheDate(date);

                    recipeController.setData(recipeList.get(i), empty, userListener);
                }
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                // Set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                // Set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeMsgGrid() {
        grid.getChildren().clear();


        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < msgList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/messageItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                MsgController msgController = fxmlLoader.getController();
                msgController.setData(msgList.get(i), userListener);

                if (column == 1) {
                    column = 0;
                    row++;
                }
                grid.setStyle("-fx-background-color: #ffffff");
                grid.add(anchorPane, column++, row);
                // Set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                // Set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(5));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeAddNewRecipeGrid() {
        grid.getChildren().clear();


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/addNewRecipe.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            grid.add(anchorPane, 1, 1);
            grid.setAlignment(Pos.CENTER);
            grid.setStyle("-fx-background-color: #ffa9a9");

            // Set grid width
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);

            // Set grid height
            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeTagGrid() {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < tagList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/tag.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                TagController tagController = fxmlLoader.getController();
                tagController.setData(tagList.get(i), userListener);

                if (column == 1) {
                    column = 0;
                    row++;
                }
                gridTag.add(anchorPane, column++, row);
                // Set grid width
                gridTag.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridTag.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridTag.setMaxWidth(Region.USE_PREF_SIZE);

                // Set grid height
                gridTag.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridTag.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridTag.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(3));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeIngredientGrid() {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < ingredientsList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/ingredient.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                IngredientController ingredientController = fxmlLoader.getController();
                ingredientController.setData(ingredientsList.get(i), userListener);

                if (column == 1) {
                    column = 0;
                    row++;
                }
                gridIngredient.add(anchorPane, column++, row);
                // Set grid width
                gridIngredient.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridIngredient.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridIngredient.setMaxWidth(Region.USE_PREF_SIZE);

                // Set grid height
                gridIngredient.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridIngredient.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridIngredient.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(3));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void openDetailedGrid() {
        grid.getChildren().clear();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/openForDetailed.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            DetailedViewController detailedViewController = fxmlLoader.getController();
            detailedViewController.setData(recipe);
            grid.setStyle("-fx-background-color: #ffa9a9");
            grid.add(anchorPane, 1, 1);
            // Set grid width
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);
            // Set grid height
            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openReplyGrid(UUID senderId) {
        grid.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/reply.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            ReplyController replyController = fxmlLoader.getController();
            replyController.setData(message, senderId, userListener);
            grid.setStyle("-fx-background-color: #ffa9a9");
            grid.add(anchorPane, 1, 1);
            // Set grid width
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);
            // Set grid height
            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initializeSettingsGrid() {
        grid.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/settings.fxml"));
        try {

            AnchorPane anchorPane = fxmlLoader.load();
            grid.add(anchorPane, 1, 1);
            grid.setAlignment(Pos.CENTER);
            grid.setStyle("-fx-background-color: #ffa9a9");

            // Set grid width
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);
            // Set grid height
            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------- INITIALIZE
    // ----------------------------------------------- //

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        home.setStyle("-fx-color: rgb(239, 242, 255)");
        home.setStyle("-fx-border-color: #000000");

        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // ComboBox User
        ObservableList<String> list = FXCollections.observableArrayList("Settings");
        comboBox.setItems(list);
        comboBox.setPromptText(user.getNickname());

        // Set message count
        getMessages();

        // Set cart count
        getCart();

        
        // Slider
        filterPane.setVisible(false);
        anchorPaneBelowFilter.setTranslateY(-176);
        filter.setOnMousePressed(event -> {
            gridTag.getChildren().clear();
            gridIngredient.getChildren().clear();
            // ----------------------------------------------------//
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.7));
            slide.setNode(filterPane);
            slide.setToY(-176);
            slide.play();
            filterPane.setTranslateY(0);
            // ----------------------------------------------------//
            TranslateTransition slide2 = new TranslateTransition();
            slide2.setDuration(Duration.seconds(0.7));
            slide2.setNode(anchorPaneBelowFilter);
            slide2.setToY(-176);
            slide2.play();
            anchorPaneBelowFilter.setTranslateY(0);
            // ----------------------------------------------------//
            slide.setOnFinished((ActionEvent e) -> {
                filterPane.setVisible(false);
                filter.setVisible(false);
                filterBack.setVisible(true);
            });
        });

        filterBack.setOnMousePressed(event -> {
            scrollTag.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollIngredient.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            initializeTagGrid();
            initializeIngredientGrid();
            // ----------------------------------------------------//
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.7));
            slide.setNode(filterPane);
            slide.setToY(0);
            slide.play();
            filterPane.setVisible(true);
            filterPane.setTranslateY(-176);
            // ----------------------------------------------------//
            TranslateTransition slide2 = new TranslateTransition();
            slide2.setDuration(Duration.seconds(0.7));
            slide2.setNode(anchorPaneBelowFilter);
            slide2.setToY(0);
            slide2.play();
            anchorPaneBelowFilter.setTranslateY(-176);
            // ----------------------------------------------------//

            slide.setOnFinished((ActionEvent e) -> {
                filter.setVisible(true);
                filterBack.setVisible(false);

            });
        });

        // ----------------------------------------- MY LISTENER
        // ----------------------------------------------- //
        if (recipeList.size() > 0) {
            chosenRecipe(recipeList.get(0));
            userListener = new UserListener() {
                // When the user clicks on a specific recipe
                @Override
                public void onClickListener(Recipe recipe) {
                    chosenRecipe(recipe);
                }

                @Override
                public void descriptionListener(Recipe recipe) {
                    chosenRecipe(recipe);
                }

                // When the User clicks on the heart button
                @Override
                public void favClickListener(Recipe recipe, ImageView heartImage) {
                    if (user.getFavorites().contains(recipe.getId())) {
                        userService.removeFromFavorites(user.getId(), recipe.getId());
                        favouriteRecipeList.remove(recipe);
                        String imgFile = "/img/heartEmpty.png";
                        Image empty = new Image(getClass().getResourceAsStream(imgFile));
                        heartImage.setImage(empty);
                    } else {
                        userService.addToFavorites(user.getId(), recipe.getId());
                        favouriteRecipeList.add(recipe);
                        String imgFile = "/img/heartFilled.png";
                        Image filled = new Image(getClass().getResourceAsStream(imgFile));
                        heartImage.setImage(filled);
                    }
                }

                // When the user clicks on a specific Ingredient
                @Override
                public void ingredientClickListener(Ingredient ingredient, ImageView ingredientButton) {
                    if (selectedIngredients.contains(ingredient)) {
                        String imgFile = "/img/uncheck.png";
                        Image uncheck = new Image(getClass().getResourceAsStream(imgFile));
                        ingredientButton.setImage(uncheck);
                        selectedIngredients.remove(ingredient);
                    } else {
                        String imgFile = "/img/check.png";
                        Image check = new Image(getClass().getResourceAsStream(imgFile));
                        ingredientButton.setImage(check);
                        selectedIngredients.add(ingredient);
                    }
                }

                // When the user clicks on a specific Tag
                @Override
                public void tagClickListener(Tag tag, ImageView tagButton) {
                    if (selectedTags.contains(tag)) {
                        String imgFile = "/img/uncheck.png";
                        Image uncheck = new Image(getClass().getResourceAsStream(imgFile));
                        tagButton.setImage(uncheck);
                        selectedTags.remove(tag);
                    } else {
                        String imgFile = "/img/check.png";
                        Image check = new Image(getClass().getResourceAsStream(imgFile));
                        tagButton.setImage(check);
                        selectedTags.add(tag);
                    }
                }

                @Override
                public void recipeEntered(Recipe recipe, TextArea textArea) {
                    textArea.setVisible(true);
                    String[] descriptionWords = recipe.getDescription().split(" ");
                    StringBuilder shortDescription = new StringBuilder();
                    if (descriptionWords.length < 10) {
                        for (int i = 0; i < descriptionWords.length; i++) {
                            shortDescription.append(descriptionWords[i] + " ");
                        }
                    } else {
                        for (int i = 0; i < 10; i++) {
                            shortDescription.append(descriptionWords[i] + " ");
                        }
                    }
                    textArea.setText(shortDescription.toString());
                    textArea.setOpacity(0.8);
                    textArea.setWrapText(true);
                }

                @Override
                public void recipeExited(Recipe recipe, TextArea textArea) {
                    textArea.setVisible(false);
                }

                @Override
                public void replyMsgListener(Message message) {
                    chosenMsg(message);
                    openReplyGrid(message.getSender());
                }

                @Override
                public void removeMsgListener(Message message) {
                    userService.removeMessageById(message.getId());
                    msgList.remove(message);
                    String messageCount = String.valueOf(msgList.size());
                    msgCountLbl.setText(messageCount);
                    initializeMsgGrid();
                }

                @Override
                public void closeMsgListener() {
                    initializeMsgGrid();
                }

            };

        }


        // Initialize Home grid
        initializeHomeGrid();


        // -----------------------------------------BUTTONS---------------------------------------------------
        // //

        // Search Button-----------------------------------------
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                grid.getChildren().clear();
                recipeList = recipeService.getRecipesByNameLike(searchField.getText());
                List<Recipe> filteredRecipes = new ArrayList<>();
                for (Recipe recipe : recipeList) {
                    boolean isValid = true;

                    // Search tags
                    List<Tag> tags = tagService.getTagsByRecipeId(recipe.getId());
                    Set<String> tagNames = new HashSet<>();
                    for (Tag tag : tags) {
                        tagNames.add(tag.getName());
                    }
                    for (Tag tag : selectedTags) {
                        if (!tagNames.contains(tag.getName())) {
                            isValid = false;
                            break;
                        }
                    }

                    // Search ingredients
                    Map<Ingredient, Integer> ingredients = ingredientService.getIngredientsByRecipeId(recipe.getId());
                    Set<String> ingredientNames = new HashSet<>();
                    for (Ingredient ingredient : ingredients.keySet()) {
                        ingredientNames.add(ingredient.getName());
                    }
                    for (Ingredient ingredient : selectedIngredients) {
                        if (!ingredientNames.contains(ingredient.getName())) {
                            isValid = false;
                            break;
                        }
                    }

                    if (isValid) {
                        filteredRecipes.add(recipe);
                    }
                }
                recipeList = filteredRecipes;
                initializeHomeGrid();
            }
        });

        // Filter Button-----------------------------------------

        // Open for a detailed view Button-----------------------------------------
        openDetailed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openDetailedGrid();
            }
        });

        // Add to a plan Button-----------------------------------------
        addToPlan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getTheDate();
                if (!planList.contains(recipe)) {
                    planList.add(recipe);
                    removeFromPlan.setVisible(true);
                    addToPlan.setVisible(true);
                    initializePlanGrid();
                } else {
                    removeFromPlan.setVisible(false);
                }

            }
        });

        removeFromPlan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (planList.contains(recipe)) {
                    planList.remove(recipe);
                    addToPlan.setVisible(true);
                    initializePlanGrid();
                } else {
                    removeFromPlan.setVisible(false);
                }

            }
        });

        // Add New Recipe Button-----------------------------------------
        addNewRecipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initializeAddNewRecipeGrid();
            }
        });

        // Home Button-----------------------------------------

        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getMessages();
                home.setStyle("-fx-color: rgb(239, 242, 255)");
                favorites.setStyle("-fx-background-color: rgb(254, 215, 0)");
                plan.setStyle("-fx-background-color: rgb(254, 215, 0)");
                grid.getChildren().clear();
                grid.setStyle("-fx-background-color: #ffffff");
                initializeHomeGrid();


            }
        });

        // Favourites Button-----------------------------------------
        favorites.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getMessages();
                favorites.setStyle("-fx-color: rgb(239, 242, 255)");
                home.setStyle("-fx-background-color: rgb(254, 215, 0)");
                plan.setStyle("-fx-background-color: rgb(254, 215, 0)");
                initializeFavouritesGrid();
            }
        });

        // Plan Button-----------------------------------------

        plan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getMessages();
                plan.setStyle("-fx-color: rgb(239, 242, 255)");
                home.setStyle("-fx-background-color: rgb(254, 215, 0)");
                favorites.setStyle("-fx-background-color: rgb(254, 215, 0)");
                initializePlanGrid();
            }
        });

        // Cart --------------------------------------------------
        cart.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

            }
        });
        // Message Button-----------------------------------------
        msg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                home.setStyle("-fx-background-color: rgb(254, 215, 0)");
                favorites.setStyle("-fx-background-color: rgb(254, 215, 0)");
                plan.setStyle("-fx-background-color: rgb(254, 215, 0)");
                initializeMsgGrid();
            }
        });

        // Logout Button-----------------------------------------

        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneContext.user = null;
                SceneContext.changeScene(event, "/fxmlFiles/login.fxml");
            }
        });
    }

}
