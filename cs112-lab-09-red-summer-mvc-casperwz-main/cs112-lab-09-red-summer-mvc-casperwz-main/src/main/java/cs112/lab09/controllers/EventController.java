package cs112.lab09.controllers;

import cs112.lab09.MainApplication;
import cs112.lab09.models.RevisedHistoricalEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;

public class EventController {

    @FXML
    private Label locationLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label revisedDescriptionLabel;
    @FXML
    private Hyperlink citationHyperlink;
    @FXML
    private Button closeButton;
    @FXML
    private ImageView eventImageView;

    private String location;
    private RevisedHistoricalEvent event;
    private String imageFileName;


    public void initData(String location, RevisedHistoricalEvent event, String imageFileName) {
        this.location = location;
        this.event = new RevisedHistoricalEvent(event);
        this.imageFileName = imageFileName;

        this.locationLabel.setText(this.location);
        this.dateLabel.setText(this.event.getEventDay().toString());
        this.descriptionLabel.setText(RevisedHistoricalEvent.TAUGHT_HISTORY_INTRO
                + "\n\n" + this.event.getDescription());
        this.revisedDescriptionLabel.setText(RevisedHistoricalEvent.REVISED_HISTORY_INTRO
                + "\n\n" + this.event.getRevisedDescription());
        this.citationHyperlink.setText(this.event.getCitation());

        URL imageUrl = MainApplication.class.getResource("images/" + imageFileName);
        if (imageUrl != null) {
            this.eventImageView.setImage(new Image(imageUrl.toExternalForm()));
        } else {
            System.out.println("ERROR: could not find images/" + imageFileName);
        }
    }

    @FXML
    protected void onCitationHyperlinkClick() {
        System.out.println("Source clicked: " + this.event.getCitation());
    }

    @FXML
    protected void onCloseButtonClick() {
        Stage stage = (Stage) this.closeButton.getScene().getWindow();
        stage.close();
    }
}
