package piotrmakarewicz.weatherbotapi;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2IntentMessage;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2IntentMessageText;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookRequest;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {
    private static final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

    public static GoogleCloudDialogflowV2WebhookResponse createResponseFromStrings(String[] messageStrings){
        var response = new GoogleCloudDialogflowV2WebhookResponse();
        var messagesStrings = new String[] {"baba", "msmsm"};
        var intentMessages = Arrays.stream(messagesStrings).map(s -> {
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
    public GoogleCloudDialogflowV2WebhookResponse hello(@RequestBody String rawRequest) throws IOException {
        GoogleCloudDialogflowV2WebhookRequest request = jacksonFactory.createJsonParser(rawRequest)
                .parse(GoogleCloudDialogflowV2WebhookRequest.class);

        System.out.println("RECEIVED REQUEST: " + request);

        StringWriter stringWriter = new StringWriter();
        var jsonGenerator = jacksonFactory.createJsonGenerator(stringWriter);
        GoogleCloudDialogflowV2WebhookResponse response = createResponseFromStrings(new String[] {"It will be raining as hell!"});
        jsonGenerator.serialize(response);
        jsonGenerator.flush();
        return response;
    }
}
