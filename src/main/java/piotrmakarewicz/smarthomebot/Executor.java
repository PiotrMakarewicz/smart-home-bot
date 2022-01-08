package piotrmakarewicz.smarthomebot;

import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import piotrmakarewicz.smarthomebot.home.Home;
import piotrmakarewicz.smarthomebot.home.Room;
import piotrmakarewicz.smarthomebot.home.device.Television;
import piotrmakarewicz.smarthomebot.home.device.TvChannel;
import java.util.List;

@Component
public class Executor {
    Home home;

    @Autowired
    Executor(Home home){
        this.home = home;
    }

    public String executeAction(GoogleCloudDialogflowV2WebhookRequest request){

        String intent = request.getQueryResult().getIntent().getDisplayName();
        String roomStr = List.of("TurnLightOn", "TurnLightOff", "IsLightOn").contains(intent)
                            ? (String) request.getQueryResult().getParameters().get("room")
                            : null;

        String tvChannelStr = List.of("SetTelevisionChannel").contains(intent)
                            ? (String)  request.getQueryResult().getParameters().get("tv-channel")
                            : null;

        switch (intent) {
            case "TurnLightOn" -> {return turnLightOn(roomStr);}
            case "TurnLightOff" -> {return turnLightOff(roomStr);}
            case "IsLightOn" -> {return isLightOn(roomStr);}
            case "TurnTelevisionOn" -> {return turnTelevisionOn();}
            case "TurnTelevisionOff" -> {return turnTelevisionOff();}
            case "WhatTelevisionChannel" -> {return whatTelevisionChannel();}
            case "SetTelevisionChannel" -> {return setTelevisionChannel(tvChannelStr);}
            default -> {return "Nie jestem pewien, co masz na myśli. Czy możesz to powiedzieć w inny sposób?";}
        }
    }

    private String turnLightOn(String roomStr){
        System.out.println("Executing: turnLightOff("+roomStr+")");
        home.getRoomByName(roomStr).getLight().setOn(true);
        return "Zapalam światło w pomieszczeniu: "+roomStr+ ".";
    }

    private String turnLightOff(String roomStr){
        System.out.println("Executing: turnLightOff("+roomStr+")");
        home.getRoomByName(roomStr).getLight().setOn(false);
        return "Gaszę światło w pomieszczeniu: "+roomStr+ ".";
    }

    private String isLightOn(String roomStr){
        System.out.println("Executing: isLightOn("+roomStr+")");
        Room room = home.getRoomByName(roomStr);
        return "Światło w pomieszczeniu: " + roomStr + " jest obecnie "
                + (room.getLight().isOn() ? "zapalone." : "zgaszone.");
    }

    private String turnTelevisionOn() {
        System.out.println("Executing: turnTelevisionOn()");
        Television television = home.getRoomByName("salon").getTelevision();
        if (television.isOn()){
            return "Chciałbym włączyć telewizor dla Ciebie, ale On już jest włączony.";
        } else{
            television.setOn(true);
            television.setChannel(TvChannel.TVP1);
            return "Włączam telewizor!";
        }
    }

    private String turnTelevisionOff() {
        System.out.println("Executing: turnTelevisionOff()");
        Television television = home.getRoomByName("salon").getTelevision();
        if (!television.isOn()) {
            return "Chciałbym wyłączyć telewizor dla Ciebie, ale On już jest wyłączony.";
        } else {
            television.setOn(false);
            television.setChannel(null);
            return "Wyłączam telewizor!";
        }
    }

    private String whatTelevisionChannel(){
        System.out.println("Executing: whatTelevisionChannel()");
        Television television = home.getRoomByName("salon").getTelevision();
        if (!television.isOn())
            return "Telewizor jest wyłączony, więc nie ma żadnego włączonego kanału.";
        else
            return "Kanał, który obecnie jest na telewizorze to: "+ television.getChannel().name();
    }

    private String setTelevisionChannel(String tvChannelStr) {
        System.out.println("Executing: setTelevisionChannel("+tvChannelStr+")");
        Television television = home.getRoomByName("salon").getTelevision();
        String responseStr = "";
        if (! television.isOn()){
            responseStr += "Telewizor był wyłączony, więc go włączam. ";
            television.setOn(true);
        }
        television.setChannel(TvChannel.valueOf(tvChannelStr));
        return responseStr + "Ustawiam kanał na: "+tvChannelStr;
    }

}
