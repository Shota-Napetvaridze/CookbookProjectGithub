package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import models.entities.Ingredient;
import models.entities.User;
import services.impl.RecipeServiceImpl;
import util.common.SceneContext;
import util.exceptions.recipe.InvalidRecipeDescriptionLengthException;
import util.exceptions.recipe.InvalidRecipeIngredientsCountException;
import util.exceptions.recipe.InvalidRecipeInstructionsLengthException;
import util.exceptions.recipe.InvalidRecipeNameLengthException;
import util.exceptions.recipe.InvalidRecipeServingSizeException;
import util.exceptions.recipe.InvalidRecipeTagsCountException;

public class NewRecipeController implements Initializable {

    @FXML
    private GridPane ingredientGrid;

    @FXML
    private ScrollPane ingredientScroll;

    @FXML
    private TextField ingredientSearch;

    @FXML
    private Button searchButton;

    @FXML
    private Button addImage;

    @FXML
    private Button addRecipe;

    @FXML
    private ImageView newRecipeImg;

    @FXML
    private TextArea recipeDescription;

    @FXML
    private TextArea recipeInstruction;

    @FXML
    private TextField recipeName;

    @FXML
    private TextField txtFilename;

    @FXML
    private TextField serveSize;

    private RecipeServiceImpl recipeService = new RecipeServiceImpl();

    private User user = SceneContext.user;
    private File file;
    private Image image;

    private Stage createStage() {
        VBox vBox = new VBox(new Label("A Label"));
        Scene scene = new Scene(vBox);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Images");
        return stage;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ingredientScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        addImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                // Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
                fileChooser.getExtensionFilters().add(extFilter);
                file = fileChooser.showOpenDialog(createStage());
                if (file != null) {
                    txtFilename.setText(file.getAbsoluteFile().toString());
                    image = new Image(file.toURI().toString(), 100, 150, true, true);// path, prefWidth, prefHeight,
                                                                                     // preserveRatio, smooth
                    newRecipeImg.setImage(image);
                    newRecipeImg.setPreserveRatio(true);

                }
            }
        });

        addRecipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UUID recipeId = UUID.randomUUID();
                String name = recipeName.getText();
                String picturePath = txtFilename.getText();
                String description = recipeDescription.getText();
                String instructions = recipeInstruction.getText();
                UUID authorId = user.getId();
                Map<Ingredient, Integer> ingredients = new HashMap<>();
                byte servingSize = Byte.parseByte(serveSize.getText());
                System.out.println(picturePath);
                
                try {
                    recipeService.addRecipe(recipeId, name, picturePath, description, instructions, authorId,
                            ingredients, servingSize);
                    SceneContext.changeScene(event, "/fxmlFiles/home.fxml");
                } catch (Exception e) {
                    showAlert(e.getMessage());
                }
            }
        });
    }

}