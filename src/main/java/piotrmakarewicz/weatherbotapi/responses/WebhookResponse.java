package piotrmakarewicz.weatherbotapi.responses;

import java.util.ArrayList;
import java.util.List;

public class WebhookResponse {
    private List<String> fulfillmentMessages;

    public WebhookResponse(List<String> fulfillmentMessages) {
        this.fulfillmentMessages = fulfillmentMessages;
    }

    public List<String> getFulfillmentMessages(){
        return fulfillmentMessages;
    }
}