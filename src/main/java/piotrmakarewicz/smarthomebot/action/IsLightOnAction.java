package piotrmakarewicz.smarthomebot.action;

import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookRequest;
import org.springframework.stereotype.Component;
import piotrmakarewicz.smarthomebot.home.Room;

@Component
public class IsLightOnAction extends Action {
    @Override
    public String perform(GoogleCloudDialogflowV2WebhookRequest request) {
        String roomStr = (String) request.getQueryResult().getParameters().get("room");
        Room room = home.getRoomByName(roomStr);
        String response = "Światło w pomieszczeniu: "+roomStr+" jest obecnie ";
        if (room.getLight().isOn()){
            response += "zapalone.";
        } else {
            response += "zgaszone.";
        }
        return response;
    }
}
