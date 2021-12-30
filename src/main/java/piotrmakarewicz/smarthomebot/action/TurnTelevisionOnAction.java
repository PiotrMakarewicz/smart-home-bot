package piotrmakarewicz.smarthomebot.action;

import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookRequest;
import org.springframework.stereotype.Component;
import piotrmakarewicz.smarthomebot.home.device.Television;
import piotrmakarewicz.smarthomebot.home.device.TvChannel;

@Component
public class TurnTelevisionOnAction extends Action{
    @Override
    public String perform(GoogleCloudDialogflowV2WebhookRequest request) {
        Television television = home.getRoomByName("salon").getTelevision();
        if (television.isOn()){
            return "Chciałbym włączyć telewizor dla Ciebie, ale On już jest włączony.";
        } else{
            television.setOn(true);
            television.setChannel(TvChannel.TVP1);
            return "Włączam telewizor!";
        }
    }
}
