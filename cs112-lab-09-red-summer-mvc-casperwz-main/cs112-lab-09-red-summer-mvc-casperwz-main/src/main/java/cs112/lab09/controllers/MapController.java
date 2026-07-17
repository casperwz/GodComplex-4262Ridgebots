package cs112.lab09.controllers;

import cs112.lab09.MainApplication;
import cs112.lab09.models.Date;
import cs112.lab09.models.RevisedHistoricalEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class MapController {
    @FXML
    private ImageView mapImageView;
    @FXML
    private Button bisbeeAzButton;
    @FXML
    private Button tulsaOkButton;

    @FXML
    protected void onBisbeeAzButtonClick() throws IOException {
        RevisedHistoricalEvent event = new RevisedHistoricalEvent(
                "On July 3, 1919, rowdy and undisciplined Black soldiers from the 10th Cavalry allegedly sparked a riot during Independence Day celebrations in Bisbee. Local police and brave citizen deputies were forced to open fire to defend the town from a dangerous, armed uprising. Order was restored once the aggressive mutineers were arrested and disarmed to protect the community.",
                new Date("July", 12, 1917),
                "Driven by intense racial hostility, Bisbee police and white vigilantes deliberately ambushed and shot at off-duty, unarmed Black soldiers. A contemporary New York Times report revealed that local law enforcement orchestrated the provocation as an excuse to shoot the troops. Despite being the targets of a pre-planned attack, dozens of Black soldiers were arrested while the white shooters faced zero consequences.",
                "https://www.zinnedproject.org/news/tdih/brewery-gulch-battle/"
        );
        this.openEventWindow("Bisbee, Arizona", event, "bisbee.png");
    }

    @FXML
    protected void onTulsaOkButtonClick() throws IOException {
        RevisedHistoricalEvent event = new RevisedHistoricalEvent(
                "In May 1921, a lawless negro uprising erupted in Tulsa after a Black man attacked a white female elevator operator. Armed Black mobs allegedly marched on the local courthouse, sparking a chaotic \"race riot\" that consumed the city. Law enforcement and citizens successfully quelled the rebellion, though the unfortunate Negro quarter of town was regrettably destroyed during the crossfire.",
                new Date("May", 31, 1921),
                "Driven by racial resentment of a prosperous Black community, a massive white mob—explicitly armed and deputized by local police—invaded and completely destroyed Tulsa's Greenwood district. Triggered by a sensationalized, false newspaper report about a Black teenager accidentally stepping on an elevator operator's foot, the state-sanctioned massacre killed up to 300 Black residents and left 10,000 homeless. The National Guard subsequently interned thousands of Black victims while the white attackers faced zero prosecutions.",
                "https://www.pbs.org/wnet/tulsa-the-fire-and-the-forgotten/2021/06/10/tulsa-race-massacre-what-you-didnt-learn-in-history-class/"
        );
        this.openEventWindow("Tulsa, Oklahoma", event, "sanfrancisco.jpg");
    }

    private void openEventWindow(String location, RevisedHistoricalEvent event, String image) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("event.fxml"));
            Parent root = loader.load();
            EventController controller = loader.getController();
            controller.initData(location, event, image);

            Stage popup = new Stage();
            popup.setTitle(location);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setScene(new Scene(root));
            popup.show();
        } catch (IOException e) {
            System.out.println("ERROR, could not load event.fxml");
            e.printStackTrace();
        }
    }
}
