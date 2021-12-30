package piotrmakarewicz.smarthomebot;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2IntentMessage;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2IntentMessageText;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookRequest;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import piotrmakarewicz.smarthomebot.action.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RestController
public class Controller {
    private final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
    private final TurnLightOnAction turnLightOnAction;
    private final TurnLightOffAction turnLightOffAction;
    private final DefaultAction defaultAction;

    @Autowired
    public Controller(TurnLightOnAction turnLightOnAction, TurnLightOffAction turnLightOffAction, DefaultAction defaultAction) {
        this.turnLightOffAction = turnLightOffAction;
        System.out.println("Controller created");
        this.turnLightOnAction = turnLightOnAction;
        this.defaultAction = defaultAction;
    }

    public GoogleCloudDialogflowV2WebhookResponse createResponseFromStrings(String[] messageStrings){
        var response = new GoogleCloudDialogflowV2WebhookResponse();
        var intentMessages = Arrays.stream(messageStrings).map(s -> {
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

        var intentString = request.getQueryResult().getIntent().getDisplayName();

        System.out.println("INTENT STRING: "+ intentString);

        var action = getActionForIntent(intentString);
        var responseText = action.perform(request);

        var stringWriter = new StringWriter();
        var jsonGenerator = jacksonFactory.createJsonGenerator(stringWriter);
        var response = createResponseFromStrings(new String[] {responseText});
        jsonGenerator.serialize(response);
        jsonGenerator.flush();
        return response;
    }

    private Action getActionForIntent(String intentString){
        switch (intentString) {
            case "TurnLightOn" -> {return turnLightOnAction;}
            case "TurnLightOff" -> {return turnLightOffAction;}
            default -> {return defaultAction;}
        }
    }

}
