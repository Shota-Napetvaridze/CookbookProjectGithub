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

    private UserServiceImpl userService = new UserServiceImpl();
    private RecipeServiceImpl recipeService = new RecipeServiceImpl();
    private IngredientServiceImpl ingredientService = new IngredientServiceImpl();
    private TagServiceImpl tagService = new TagServiceImpl();

    private User user;
    private  Recipe recipe;
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
    private List<Recipe> recipeList = new ArrayList<>();
    private List<Message> msgList = new ArrayList<>();
    private List<Recipe> favouriteRecipeList = new ArrayList<>();

    private List<Recipe> planList = new ArrayList<>();
    private List<Ingredient> ingredientsList = new ArrayList<>();
    private List<Ingredient> selectedIngredients = new ArrayList<>();
    private List<Tag> tagList = new ArrayList<>();
    private List<Tag> selectedTags = new ArrayList<>();

    private List<Recipe> getRecipeList() {
        List<Recipe> allRecipes = recipeService.getAllRecipes(); // TODO: recipeService.getAllTags();
        return allRecipes;
    }

    private List<Message> getMsgList() {
        List<Message> messages = userService.getUserMessagesById(user.getId()); // TODO: tagService.getAllTags();
        return messages;
    }

    private List<Tag> getTagList() {
        List<Tag> tags = new ArrayList<>();                // TODO: messageService.getAllTags();
        return tags;
    }

    private List<Ingredient> getIngredientList() {
        List<Ingredient> ingredients = new ArrayList<>(); // TODO: ingredientService.getAllIngredients();

        return ingredients;
    }

    private void chosenRecipe(Recipe recipe) {
        this.recipe = recipe;
        image = recipe.getPicture();
        recipeImg.setImage(image);
        recipeLbl.setText(recipe.getName());
    }

    private void initializeGrid() {
        int column = 0;
        int row = 0;
        try {
            for (int i = 0; i < recipeList.size(); i++) {
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
        home.setStyle("-fx-color: rgb(239, 242, 255)" +
                "-fx-background-color: rgb(15, 125, 242)");

        // ComboBox User
        ObservableList<String> list = FXCollections.observableArrayList("Settings", "Profile");
        comboBox.setItems(list);

        // Adds all the recipes and messages
//        recipeList.addAll(getRecipeList());                        //TODO: This should be fixed !!!!!!
//        msgList.addAll(getMsgList());
//        tagList.addAll(getTagList());
//        ingredientsList.addAll(getIngredientList());

        // Set message count
        String s = String.valueOf(msgList.size());
        msgCountLbl.setText(s);

        // When User clicks on a specific recipe, tag or ingredient
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
                    boolean isInFavorites = false;

                    for (Recipe r : favouriteRecipeList) {
                        if (recipe == r) {
                            favouriteRecipeList.remove(recipe);
                            isInFavorites = true;
                        }
                    }
                    if (isInFavorites == false) {
                        if (userService.addToFavorites(user.getId(), recipe.getId())) {
                            favouriteRecipeList.add(recipe);
                        }
                    }

                    // DBUtils.addToFavorites(recipe.getRecipeId(), recipe.getUserId(), heartImage);
                }
                // When the user clicks on a specific Ingredient
                @Override
                public void ingredientClickListener(Ingredient ingredient, Button ingredientButton) {
                    if (selectedIngredients.contains(ingredient)) {
                        ingredientButton.setStyle("-fx-background-color: #FFFFFF");
                        selectedIngredients.remove(ingredient);
                    } else {
                        ingredientButton.setStyle("-fx-background-color: #FF0000");
                        selectedIngredients.add(ingredient);
                    }
                }
                // When the user clicks on a specific Tag
                @Override
                public void tagClickListener(Tag tag, Button tagButton) {

                    if (selectedIngredients.contains(tag)) {
                        tagButton.setStyle("-fx-background-color: #FFFFFF");
                        selectedTags.remove(tag);
                    } else {
                        tagButton.setStyle("-fx-background-color: #FF0000");
                        selectedTags.add(tag);
                    }

                }

                @Override
                public void removeClickListener(Recipe recipe) {
                    // DBUtils.removeRecipeFromHome(recipe.getRecipeId());

                }

            };
        }

        // Initialize grid-----------------------------------------
        initializeGrid();

        // Search Button-----------------------------------------

        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                recipeService.getRecipeByName(searchField.getText());

                // DBUtils.searchRecipe(event, searchField.getText());
                grid.getChildren().clear();
            }
        });

        // Filter Button-----------------------------------------

        filter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int column = 0;
                int row = 0;
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
                //TODO: implement method for this
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
                favorites.setStyle("-fx-color: rgb(239, 242, 255)" +
                        "-fx-background-color: rgb(15, 125, 242)");
                home.setStyle("-fx-background-color: rgb(254, 215, 0)");
                plan.setStyle("-fx-background-color: rgb(254, 215, 0)");
                grid.getChildren().clear();

                int column = 0;
                int row = 0;
                try {
                    for (int i = 0; i < favouriteRecipeList.size(); i++) {
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
                plan.setStyle("-fx-color: rgb(239, 242, 255)" +
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
                    for (int i = 0; i < msgList.size(); i++) {
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
                SceneContext.changeScene(event, "/fxmlFiles/login.fxml");
            }
        });

    }
}
