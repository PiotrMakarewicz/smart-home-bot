package piotrmakarewicz.smarthomebot.action;

import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookRequest;
import org.springframework.stereotype.Component;

@Component
public class DefaultAction implements Action{
    @Override
    public String perform(GoogleCloudDialogflowV2WebhookRequest request) {
        return "Nie jestem pewien, co masz na myśli. Czy możesz to powiedzieć w inny sposób?";
    }
}
