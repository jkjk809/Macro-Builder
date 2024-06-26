/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package macrobuilder;

import database.DatabaseUtil;
import java.sql.SQLException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * View for registration screen.
 * @author KingJ
 */
public class RegisterUserScene {
    
    private DatabaseUtil databaseUtil;
    
    /**
     * Creates registration GUI.
     * @param primaryStage
     * @param sceneController
     * @return register scene
     */
    public Scene createRegisterScene(Stage primaryStage, SceneController sceneController) {
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.BLACK);
        dropShadow.setRadius(6);
        dropShadow.setSpread(0.3);
        
        Font labelFont = new Font("Helvetica", 36);
        Font fieldFont = new Font("Lato", 30);
        Font titleFont = new Font("impact", 60);

        Image image = new Image("orange.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Label titleLabel = new Label("Macro-Builder ");
        titleLabel.setFont(titleFont);
        titleLabel.setStyle("-fx-text-fill: #EE9F27;");
        titleLabel.setEffect(dropShadow);
        
        Label welcomeLabel = new Label("We're happy you're here. Create an account.");
        welcomeLabel.setFont(fieldFont);
        welcomeLabel.getStyleClass().add("whiteLabels");
        //Create labels and fonts
        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label(" Password: ");
        Label emailLabel = new Label("        Email: ");

        usernameLabel.setFont(labelFont);
        
        passwordLabel.setFont(labelFont);
       
        emailLabel.setFont(labelFont);
        usernameLabel.getStyleClass().add("whiteLabels");
        passwordLabel.getStyleClass().add("whiteLabels");
        emailLabel.getStyleClass().add("whiteLabels");
        // Text fields for our username and password and sets font
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField emailField = new TextField();

        usernameField.setFont(fieldFont);
        passwordField.setFont(fieldFont);
        emailField.setFont(fieldFont);

        Button registerButton = new Button("Register");
        registerButton.getStyleClass().add("buttons");
        Hyperlink switchScene = new Hyperlink("Already have an account? Sign-in here!");
        switchScene.setEffect(dropShadow);
        switchScene.setStyle("-fx-text-fill: #FDAE44;");
        switchScene.setFont(fieldFont);
        
        VBox root = new VBox(20);

        HBox titleBox = new HBox();
        titleBox.getChildren().addAll(titleLabel, imageView);
        titleBox.setAlignment(Pos.CENTER);

        HBox usernameBox = new HBox();
        usernameBox.getChildren().addAll(usernameLabel, usernameField);
        usernameBox.setAlignment(Pos.CENTER);

        HBox passwordBox = new HBox();
        passwordBox.getChildren().addAll(passwordLabel, passwordField);
        passwordBox.setAlignment(Pos.CENTER);

        HBox emailBox = new HBox();
        emailBox.getChildren().addAll(emailLabel, emailField);
        emailBox.setAlignment(Pos.CENTER);

        HBox buttonsBox = new HBox();
        buttonsBox.getChildren().addAll(registerButton, switchScene);
        buttonsBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(titleBox, welcomeLabel, emailBox, usernameBox, passwordBox, buttonsBox);
        root.setAlignment(Pos.CENTER);
        root.setId("pane");
        Scene registerScene = new Scene(root);
        registerScene.getStylesheets().addAll(this.getClass().getResource("/controllers/registration.css").toExternalForm());
        // lets user know username doesnt meet required length
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isValidUsername(newValue)) {
                usernameField.setStyle("-fx-border-color: green;");
            } else {
                usernameField.setStyle("-fx-border-color: red;");
            }
        });

        registerButton.setOnAction((ActionEvent event) -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (!isValidUsername(username)) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Invalid Username");
                alert.setHeaderText(null);
                alert.setContentText("Username must be between 5 and 15 characters long and contain only letters, numbers, and underscores.");
                alert.showAndWait();
                return;
            }
            
            try {
                databaseUtil = new DatabaseUtil();
                if(databaseUtil.registerUser(username, password)){
                     sceneController.switchToLoginScene();
                }
                else{
                    // proper use would be catching the sql exception and checking the error code. BUT WERE ON A TIME CRUNCH
                    displayUsernameAlert();
                }
            } catch (IOException | SQLException ex) {
                Logger.getLogger(RegisterUserScene.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });

        switchScene.setOnAction(e -> sceneController.switchToLoginScene());
        registrationKeyHandlers(usernameField, passwordField, emailField, registerButton);

        return registerScene;
    }
    /**
     * Handles enter key presses on text fields.
     * @param usernameField
     * @param passwordField
     * @param emailField
     * @param registerButton
     */
    private void registrationKeyHandlers(TextField usernameField, PasswordField passwordField, TextField emailField, Button registerButton) {
        // Event handlers that allow the user to press enter inside of form
        // Pressing enter on usernameField switches focus to the passwordField
        usernameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });
        // Pressing enter on passwordField presses the loginButton
        passwordField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                registerButton.fire();
            }
        });
        emailField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                usernameField.requestFocus();
            }
        });
    }
    
    /**
     *  Checks if username is valid according to regex.
     * @param username
     * @return boolean checking if valid.
     */    
    public static boolean isValidUsername(String username) {
        int length = username.length();
        if (length < 5 || length > 15) {
            return false;
        }
        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z0-9])[a-zA-Z0-9_]+$");
        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }
    
    private void displayUsernameAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Username Already Taken");
        alert.setContentText("The username you entered is already taken.");
        alert.showAndWait();
    }

}
