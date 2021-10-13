package fxui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.IllegalArgumentException;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import core.User;
import javafx.scene.text.Text;
import json.BeastBookPersistence;

public class LoginController {
    private User user = new User();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField username_input;

    @FXML
    private TextField password_input;

    @FXML
    private Text login_error;

    @FXML
    private Button register_button;

    @FXML
    private Button login_button;

    @FXML
    void loadHome() throws IOException {
        HomeScreenController homeScreenController = new HomeScreenController();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("HomeScreen.fxml"));
        fxmlLoader.setController(homeScreenController);
        homeScreenController.setUser(user);

        AnchorPane pane = fxmlLoader.load();
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    void registerUser(ActionEvent event) throws IllegalArgumentException {
        String userName = username_input.getText();
        String password = password_input.getText();
        if (!userName.equals("") && !password.equals("")) {
            try {
                this.user = new User(userName, password);
                saveUser(user); //Skal lagre bruker som en JSON-fil
            } catch (Exception e) {
                login_error.setText(e.getMessage());
            }

        }

    }


    @FXML
    void loginUser(ActionEvent event) throws IllegalArgumentException, IOException {
        String userName = username_input.getText();
        String password = password_input.getText();
        if (!userName.equals("")) {
            if (!password.equals("")) {
                User login = getUser(userName);
                if (Objects.isNull(login)) {
                    login_error.setText("No user found");
                } else {
                    if (!login.getPassword().equals(password))
                        login_error.setText("Wrong Password");
                    else {
                        user = login;
                        loadHome();
                    }
                }
            } else {
                login_error.setText("No Password given!");
            }
    } else {
            login_error.setText("No username given");
        }
    }




    private void saveUser(User user) {
        BeastBookPersistence persistence = new BeastBookPersistence();
        try {
            persistence.setSaveFilePath(user.getUserName());
            persistence.saveUser(user);
        } catch (IOException e) {
            login_error.setText("User was not saved");
        }
    }


    private User getUser(String userName) {
        BeastBookPersistence persistence = new BeastBookPersistence();
        try {
            persistence.setSaveFilePath(userName);
            User user = persistence.loadUser();
            return user;
        } catch (IOException e) {
        }
       return null;
    }

}