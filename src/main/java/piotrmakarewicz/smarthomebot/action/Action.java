package piotrmakarewicz.smarthomebot.action;

import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import piotrmakarewicz.smarthomebot.home.Home;

public abstract class Action {
    @Autowired
    protected Home home;
    public abstract String perform(GoogleCloudDialogflowV2WebhookRequest request);
}
