package piotrmakarewicz.weatherbotapi.responses;

public class WebhookRequest {
    public String body;
    WebhookRequest() {}
    WebhookRequest(String body){
        this.body = body;
    }
}
