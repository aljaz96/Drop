package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

import javax.xml.crypto.Data;

/**
 * Created by mikik on 11. 06. 2017.
 */

public class SaveLoad {

    public static void saveState(DataAll state){
        Json json = new Json();
        json.setUsePrototypes(false);
        Preferences playerData = Gdx.app.getPreferences("PlayerData");
        playerData.putString("playerState", json.toJson(state));
        playerData.flush();
    }

    public static DataAll loadState(){
        Preferences playerData = Gdx.app.getPreferences("PlayerData");
        if(playerData.contains("playerState")){
            Json json = new Json();
            json.setUsePrototypes(false);
            String data = playerData.getString("playerState");
            return json.fromJson(DataAll.class, data);
        }
        else{
            return DataAll.scenarijA();
        }
    }
}
