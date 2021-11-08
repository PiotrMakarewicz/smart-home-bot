package piotrmakarewicz.weatherbotapi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import piotrmakarewicz.weatherbotapi.responses.SampleResponse;

@RestController
public class Controller {
    @RequestMapping("/hello")
    public SampleResponse hello(){
        return new SampleResponse();
    }
}
