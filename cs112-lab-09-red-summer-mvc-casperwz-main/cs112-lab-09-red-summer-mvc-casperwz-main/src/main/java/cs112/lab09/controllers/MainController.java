package cs112.lab09.controllers;

import cs112.lab09.MainApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class MainController {
    @FXML
    private Label titleLabel;
    @FXML
    private Label subtitleLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private ImageView logoImageView;
    @FXML
    private Button startButton;
    @FXML
    private Button quitButton;

    @FXML
    protected void onStartButtonClick(ActionEvent actionEvent) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("map.fxml"));
            Parent mapRoot = loader.load();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mapRoot));
            stage.setTitle("Map");
        } catch(IOException e){
            System.out.println("ERROR, could not load map.fxml");
            e.printStackTrace();
        }

    }
    @FXML
    protected void onQuitButtonClick(){
        Platform.exit();
    }
}