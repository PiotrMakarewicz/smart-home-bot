package piotrmakarewicz.smarthomebot.action;

import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2Intent;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookRequest;

public interface Action {
    String perform(GoogleCloudDialogflowV2WebhookRequest request);
}
