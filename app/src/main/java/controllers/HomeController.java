package controllers;

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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import models.entities.Ingredient;
import models.entities.Message;
import models.entities.Recipe;
import models.entities.Tag;
import models.entities.User;

import services.impl.IngredientServiceImpl;
import services.impl.RecipeServiceImpl;
import services.impl.TagServiceImpl;
import services.impl.UserServiceImpl;
import util.common.MyListener;
import util.common.SceneContext;

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

    @FXML
    private Button searchIngredient;

    @FXML
    private Button searchTag;

    @FXML
    private TextField tagsSearchField;

    private UserServiceImpl userService = new UserServiceImpl();
    private RecipeServiceImpl recipeService = new RecipeServiceImpl();
    private IngredientServiceImpl ingredientService = new IngredientServiceImpl();
    private TagServiceImpl tagService = new TagServiceImpl();

    private User user = SceneContext.user;
    private Recipe recipe;
    private Message message;
    private Image image;
    private MyListener myListener;

    @FXML
    void select(ActionEvent event) {
        String s = comboBox.getSelectionModel().getSelectedItem().toString();
        if (s.startsWith("S")) {
            comboBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    SceneContext.changeScene(event, "/fxmlFiles/login.fxml");
                }
            });

        } else if (s.startsWith("P")) {
            comboBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    comboBox.setStyle("-fx-color: rgb(239, 242, 255)" +
                            "-fx-background-color: rgb(15, 125, 242)");
                }
            });

        }
    }

    // Lists ----------------------------
    private List<Recipe> recipeList = recipeService.getAllRecipes();
    private List<Message> msgList = userService.getUserMessagesById(user.getId());
    public List<Recipe> favouriteRecipeList = userService.getFavoriteRecipes(user.getId());
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


    private void initializeGrid() {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < recipeList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/recipeItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                RecipeController recipeController = fxmlLoader.getController();
                if (favouriteRecipeList.contains(recipeList.get(i))) {
                    String imgFile = "/img/heartFilled.png";
                    Image filled = new Image(getClass().getResourceAsStream(imgFile));
                    recipeController.setData(recipeList.get(i), filled, myListener);
                } else {
                    String imgFile = "/img/heartEmpty.png";
                    Image empty = new Image(getClass().getResourceAsStream(imgFile));
                    recipeController.setData(recipeList.get(i), empty, myListener);
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

                grid.setVgap(2.0);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        home.setStyle("-fx-color: rgb(239, 242, 255)");
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // ComboBox User
        ObservableList<String> list = FXCollections.observableArrayList("Settings", "Profile");
        comboBox.setItems(list);
        comboBox.setPromptText(user.getNickname());

        // Set message count
        String messageCount = String.valueOf(msgList.size());
        msgCountLbl.setText(messageCount);


        // -----------------------------------------MYLISTENER----------------------------------------------- //
        if (recipeList.size() > 0) {
            chosenRecipe(recipeList.get(0));
            myListener = new MyListener() {
                // When the user clicks on a specific recipe
                @Override
                public void onClickListener(Recipe recipe) {
                    chosenRecipe(recipe);
                }

                // When the User clicks on the heart button
                @Override
                public void favClickListener(Recipe recipe, ImageView heartImage) {
                    if (favouriteRecipeList.contains(recipe)) {
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
                    for (int i = 0; i < 10; i++) {
                        shortDescription.append(descriptionWords[i] + " ");
                    }
                    textArea.setText(shortDescription.toString());
                    textArea.setWrapText(true);
                }

                @Override
                public void recipeExited(Recipe recipe, TextArea textArea) {
                    textArea.setVisible(false);
                }

                @Override
                public void replyMsgListener(Message message) {
                    MsgController msgController = new MsgController();
                    msgController.setData(message, myListener);
                }
            };
        }

        // Initialize grid-----------------------------------------
        initializeGrid();

        // -----------------------------------------BUTTONS-----------------------------------------------

        // Search Button-----------------------------------------
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                grid.getChildren().clear();
                recipeService.getRecipeByName(searchField.getText());
            }
        });

        // Filter Button-----------------------------------------
        filter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scrollTag.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollIngredient.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                int column = 0;
                int row = 1;
                try {
                    for (int i = 0; i < tagList.size(); i++) {
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

                    for (int i = 0; i < ingredientsList.size(); i++) {
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
        });

        // Open for a detailed view Button-----------------------------------------
        openDetailed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                grid.getChildren().clear();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/openForDetailed.fxml"));
                try {
                    AnchorPane anchorPane = fxmlLoader.load();
                    grid.add(anchorPane, 1, 1);
                    grid.setAlignment(Pos.CENTER);

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

                // TODO: implement method for this
            }
        });

        // Add to a plan Button-----------------------------------------
        addToPlan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                planList.add(recipe);

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
        });

        // Home Button-----------------------------------------

        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                recipeImg.setVisible(true);
                recipeLbl.setVisible(true);
                home.setStyle("-fx-color: rgb(239, 242, 255)");
                favorites.setStyle("-fx-background-color: rgb(254, 215, 0)");
                plan.setStyle("-fx-background-color: rgb(254, 215, 0)");
                grid.getChildren().clear();
                grid.setStyle("-fx-background-color: #ffffff");
                initializeGrid();
                // SceneContext.changeScene(event, "/fxmlFiles/home.fxml");
            }
        });

        // Favourites Button-----------------------------------------
        favorites.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                favorites.setStyle("-fx-color: rgb(239, 242, 255)");
                home.setStyle("-fx-background-color: rgb(254, 215, 0)");
                plan.setStyle("-fx-background-color: rgb(254, 215, 0)");
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
                        recipeController.setData(favouriteRecipeList.get(i), filled, myListener);

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
        });

        // Plan Button-----------------------------------------

        plan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                plan.setStyle("-fx-color: rgb(239, 242, 255)");
                home.setStyle("-fx-background-color: rgb(254, 215, 0)");
                favorites.setStyle("-fx-background-color: rgb(254, 215, 0)");
                grid.getChildren().clear();

                int column = 0;
                int row = 1;
                try {
                    for (int i = 0; i < planList.size(); i++) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/recipeItem.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        RecipeController recipeController = fxmlLoader.getController();
                        if (favouriteRecipeList.contains(recipeList.get(i))) {
                            String imgFile = "/img/heartFilled.png";
                            Image filled = new Image(getClass().getResourceAsStream(imgFile));
                            recipeController.setData(planList.get(i), filled, myListener);
                        } else {
                            String imgFile = "/img/heartEmpty.png";
                            Image empty = new Image(getClass().getResourceAsStream(imgFile));
                            recipeController.setData(planList.get(i), empty, myListener);
                        }
                        if (column == 1) {
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
        });

        // Message Button-----------------------------------------
        msg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                home.setStyle("-fx-background-color: rgb(254, 215, 0)");
                favorites.setStyle("-fx-background-color: rgb(254, 215, 0)");
                plan.setStyle("-fx-background-color: rgb(254, 215, 0)");
                grid.getChildren().clear();
                // msgCountLbl.setText("...");
                grid.setStyle("-fx-background-color: #ffffff");

                int column = 0;
                int row = 1;
                try {
                    for (int i = 0; i < msgList.size(); i++) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/messageItem.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        MsgController msgController = fxmlLoader.getController();
                        msgController.setData(msgList.get(i), myListener);

                        if (column == 1) {
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
