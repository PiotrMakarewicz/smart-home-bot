package piotrmakarewicz.weatherbotapi;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2IntentMessage;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2IntentMessageText;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookRequest;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import piotrmakarewicz.weatherbotapi.responses.WebhookResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {

    public static GoogleCloudDialogflowV2WebhookResponse getBoringResponse(){
        var response = new GoogleCloudDialogflowV2WebhookResponse();
        var messagesStr = new String[] {"baba", "msmsm"};
        var intentMessages = Arrays.stream(messagesStr).map(s -> {
            var intentMessage = new GoogleCloudDialogflowV2IntentMessage();
            var intentMessageText = new GoogleCloudDialogflowV2IntentMessageText();
            intentMessageText.setText(List.of(s));
            intentMessage.setText(intentMessageText);
            return intentMessage;
        }).collect(Collectors.toList());
        response.setFulfillmentMessages(intentMessages);
        return response;
    }

    @PostMapping(value="/", produces = "application/json")
    public GoogleCloudDialogflowV2WebhookResponse hello(@RequestBody GoogleCloudDialogflowV2WebhookRequest request){
        System.out.println(request);
        return getBoringResponse();
    }
}
