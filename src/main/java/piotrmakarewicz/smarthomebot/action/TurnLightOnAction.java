package piotrmakarewicz.smarthomebot.action;

import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2Intent;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookRequest;
import org.springframework.stereotype.Component;

@Component
public class TurnLightOnAction implements Action{
    @Override
    public String perform(GoogleCloudDialogflowV2WebhookRequest request) {
        String room = (String) request.getQueryResult().getParameters().get("room");
        return "Zapalam światło w pomieszczeniu: "+room+ ".";
    }
}
