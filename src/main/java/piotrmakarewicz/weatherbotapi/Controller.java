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

    private static String getResponseTextFor24hForecastIntent(GoogleCloudDialogflowV2WebhookRequest request) {
        String city = (String) request.getQueryResult().getParameters().get("geo-city");
        return "I recognized you want a 24 h Forecast for "+city;
    }

    @PostMapping(value="/", produces = "application/json")
    public static GoogleCloudDialogflowV2WebhookResponse hello(@RequestBody String rawRequest) throws IOException {
        GoogleCloudDialogflowV2WebhookRequest request = jacksonFactory.createJsonParser(rawRequest)
                .parse(GoogleCloudDialogflowV2WebhookRequest.class);

        System.out.println("RECEIVED REQUEST: " + request);

        var intentString = request.getQueryResult().getIntent().getDisplayName();

        var responseText = "I am not sure what you mean. Can you say it in a different way?";

        switch (intentString) {
            case "24hForecast" -> responseText = getResponseTextFor24hForecastIntent(request);
            default -> {
            }
        }

        StringWriter stringWriter = new StringWriter();
        var jsonGenerator = jacksonFactory.createJsonGenerator(stringWriter);

        var response = createResponseFromStrings(new String[] {responseText});

        jsonGenerator.serialize(response);
        jsonGenerator.flush();
        return response;
    }


}
