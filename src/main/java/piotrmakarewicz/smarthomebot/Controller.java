package piotrmakarewicz.smarthomebot;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2IntentMessage;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2IntentMessageText;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookRequest;
import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import piotrmakarewicz.smarthomebot.home.Home;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

@Component
@RestController
public class Controller {
    private final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
    private final Executor executor;
    private final Home home;

    @Autowired
    public Controller(Executor e, Home h){
        this.executor = e;
        this.home = h;
    }

    public GoogleCloudDialogflowV2WebhookResponse createResponseFromStrings(String[] messageStrings){
        var response = new GoogleCloudDialogflowV2WebhookResponse();
        var intentMessages = Arrays.stream(messageStrings).map(s -> {
            var intentMessage = new GoogleCloudDialogflowV2IntentMessage();
            var intentMessageText = new GoogleCloudDialogflowV2IntentMessageText();
            intentMessageText.setText(List.of(s));
            intentMessage.setText(intentMessageText);
            return intentMessage;
        }).toList();
        response.setFulfillmentMessages(intentMessages);
        return response;
    }

    @GetMapping("/")
    public RedirectView getIndex(Model model){
        return new RedirectView("index.html");
    }

    @GetMapping("/state")
    @ResponseBody
    public Home getState(){
        return home;
    }

    @PostMapping(value="/", produces = "application/json")
    public GoogleCloudDialogflowV2WebhookResponse handleDialogflowRequest(@RequestBody String rawRequest) throws IOException {
        GoogleCloudDialogflowV2WebhookRequest request = jacksonFactory.createJsonParser(rawRequest)
                .parse(GoogleCloudDialogflowV2WebhookRequest.class);

        System.out.println("RECEIVED REQUEST: " + request);
        var responseText = executor.executeAction(request);

        var stringWriter = new StringWriter();
        var jsonGenerator = jacksonFactory.createJsonGenerator(stringWriter);
        var response = createResponseFromStrings(new String[] {responseText});
        jsonGenerator.serialize(response);
        jsonGenerator.flush();
        return response;
    }

}
