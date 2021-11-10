package piotrmakarewicz.weatherbotapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class OpenWeatherAPIManager {

    private String key;

    public OpenWeatherAPIManager() {
        loadEnvariables();
        System.out.println("OWAM created! :D:D:D:D:D:D:");
        System.out.println("KEY IS: "+ this.key);
    }

    private void loadEnvariables() {
        Dotenv dotenv = Dotenv.load();
        this.key = dotenv.get("HD_OPENWEATHERAPI_KEY");
        if (this.key == null){
            this.key = System.getenv("HD_OPENWEATHERAPI_KEY");
            if (this.key == null) {
                throw new RuntimeException("Failed to load API key from HD_OPENWEATHERAPI_KEY environment variable.");
            }
        }
    }
}
