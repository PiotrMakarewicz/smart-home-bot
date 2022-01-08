package piotrmakarewicz.smarthomebot;

import com.google.api.services.dialogflow.v3.model.GoogleCloudDialogflowV2WebhookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import piotrmakarewicz.smarthomebot.home.Home;
import piotrmakarewicz.smarthomebot.home.Room;
import piotrmakarewicz.smarthomebot.home.device.Curtain;
import piotrmakarewicz.smarthomebot.home.device.Television;
import piotrmakarewicz.smarthomebot.home.device.TvChannel;

import java.util.List;
import java.util.Objects;

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

        String tvChannelStr = Objects.equals(intent, "SetTelevisionChannel")
                            ? (String)  request.getQueryResult().getParameters().get("tv-channel")
                            : null;

        int targetTemp = Objects.equals(intent, "SetTargetTemperature")
                            ? (int) request.getQueryResult().getParameters().get("tv-channel")
                            : 21;
        switch (intent) {
            case "TurnLightOn" -> {return turnLightOn(roomStr);}
            case "TurnLightOff" -> {return turnLightOff(roomStr);}
            case "IsLightOn" -> {return isLightOn(roomStr);}
            case "TurnTelevisionOn" -> {return turnTelevisionOn();}
            case "TurnTelevisionOff" -> {return turnTelevisionOff();}
            case "WhatTelevisionChannel" -> {return whatTelevisionChannel();}
            case "SetTelevisionChannel" -> {return setTelevisionChannel(tvChannelStr);}
            case "CoverWindow" -> {return coverWindow(roomStr);}
            case "UncoverWindow" -> {return uncoverWindow(roomStr);}
            case "CheckActualTemperature" -> {return checkActualTemperature();}
            case "CheckTargetTemperature" -> {return checkTargetTemperature();}
            case "SetTargetTemperature" -> {return setTargetTemperature(targetTemp);}
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

    private String coverWindow(String roomStr) {
        System.out.println("Executing: coverWindow(" + roomStr + ")");
        Curtain curtain = home.getRoomByName(roomStr).getCurtain();
        if (curtain == null)
            return "Obawiam się, że w pomieszczeniu: " + roomStr + " nie ma zasłon, nie mogę więc odsłonić okna.";
        if (curtain.isCovering())
            return "Okno w pomieszczeniu: " + roomStr + " jest już zasłonięte.";
        curtain.setCovering(true);
        return "Zasłaniam okno w pomieszczeniu: " + roomStr + ".";
    }
    private String uncoverWindow(String roomStr) {
        System.out.println("Executing: uncoverWindow(" + roomStr + ")");
        Curtain curtain = home.getRoomByName(roomStr).getCurtain();
        if (curtain == null)
            return "Obawiam się, że w pomieszczeniu: " + roomStr + " nie ma zasłon, nie mogę więc odsłonić okna.";
        if (!curtain.isCovering())
            return "Okno w pomieszczeniu: " + roomStr + " jest już odsłonięte.";
        curtain.setCovering(false);
        return "Odsłaniam okno w pomieszczeniu: " + roomStr + ".";
    }
    private String setTargetTemperature(int targetTemp) {
        System.out.println("Executing: setTargetTemperature(" + targetTemp + ")");
        home.getRoomByName("basement").getCentralHeating().setTargetTemp(targetTemp);
        return "Ustawiam temperaturę docelową centralnego ogrzewania na: " + targetTemp + "stopni Celsjusza.";
    }

    private String checkTargetTemperature() {
        System.out.println("Executing: checkTargetTemperature()");
        int targetTemp = home.getRoomByName("basement").getCentralHeating().getTargetTemp();
        return "Temperatura docelowa centralnego ogrzewania jest obecnie ustawiona na: " + targetTemp + "stopni Celsjusza.";
    }

    private String checkActualTemperature() {
        System.out.println("Executing: checkActualTemperature()");
        int temp = home.getRoomByName("basement").getCentralHeating().getCurrentTemp();
        return "Temperatura w domu wynosi obecnie: " + temp + "stopni Celsjusza.";
    }


}
