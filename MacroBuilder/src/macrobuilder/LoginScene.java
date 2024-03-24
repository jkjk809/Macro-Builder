/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package macrobuilder;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author KingJ
 */
public class LoginScene {

    // feel free to change these later on to 1, 1 so we can get in quick.
    private String CORRECT_USERNAME;
    private String CORRECT_PASSWORD;

    public LoginScene() {
    }

    public LoginScene(String username, String password) {
        this.CORRECT_USERNAME = username;
        this.CORRECT_PASSWORD = password;
    }

    public Scene createLoginScene(Stage primaryStage, SceneController sceneController) {

        Font labelFont = new Font("Helvetica", 26);
        Font fieldFont = new Font("Lato", 16);
        Font titleFont = new Font("impact", 50);
        //image for app
        Image image = new Image("orange.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        //Create labels and fonts
        Label titleLabel = new Label("Macro-Builder ");
        titleLabel.setFont(titleFont);
        titleLabel.setStyle("-fx-text-fill: #F6EEE3;");

        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password: ");
        usernameLabel.setFont(labelFont);
        usernameLabel.setStyle("-fx-text-fill: #FDAE44;");
        passwordLabel.setFont(labelFont);
        passwordLabel.setStyle("-fx-text-fill: #FDAE44;");
        // Text fields for our username and password and sets font
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        usernameField.setFont(fieldFont);
        passwordField.setFont(fieldFont);

        // Create empty result label if we get it wrong, set text and paints it red
        Label resultLabel = new Label("");
        resultLabel.setFont(fieldFont);
        resultLabel.setStyle("-fx-text-fill: #D7504D;");

        //Creates a button with text "Login" and sets to same size as labels
        Button loginButton = new Button("Login");
        loginButton.setFont(fieldFont);

        Hyperlink switchScene = new Hyperlink("Don't have an account? Create one here!");
        switchScene.setStyle("-fx-text-fill: #FDAE44;");
        switchScene.setFont(fieldFont);
        // The idea is to stack both Horizontal Boxes verticaly inside of our vertical box
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

        HBox buttonsBox = new HBox();
        buttonsBox.getChildren().addAll(loginButton, switchScene);
        buttonsBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(titleBox, resultLabel, usernameBox, passwordBox, buttonsBox);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #000000;");

        Scene loginScene = new Scene(root);

        // Transitions to detail scene if correct. sets text in result if not.
        loginButton.setOnAction((ActionEvent event) -> {

            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.equals(CORRECT_USERNAME) && password.equals(CORRECT_PASSWORD)) {
                sceneController.switchToDetailScene(username);
            } else {
                resultLabel.setText("Incorrect Username and/or Password");
            }
        });

        switchScene.setOnAction((ActionEvent event) -> {
            sceneController.switchToRegisterScene();
        });

        loginRegistrationKeyHandlers(usernameField, passwordField, loginButton);

        return loginScene;
    }

    private void loginRegistrationKeyHandlers(TextField usernameField, PasswordField passwordField, Button loginButton) {
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
                loginButton.fire();
            }
        });
    }

}
