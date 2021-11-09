package piotrmakarewicz.weatherbotapi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import piotrmakarewicz.weatherbotapi.responses.WebhookResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    @PostMapping(value="/hello", produces = "application/json")
    public WebhookResponse hello(@RequestBody String body){
        System.out.println(body);
        return new WebhookResponse(new ArrayList<>(List.of(new String[] {"hey", "hi","hello"})));
    }
}
