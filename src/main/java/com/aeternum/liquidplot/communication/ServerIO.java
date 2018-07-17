/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeternum.liquidplot.communication;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Ethan Mak
 */
public class ServerIO {

    private final static String server = "ethanPi";
    
    public static boolean runCommand(String command) {
        try {
            HttpResponse<JsonNode> response = Unirest.post(server).field("command", command).asJson();
        } catch (UnirestException ex) {
            return false;
        }
        return true;
    }
    
    public static boolean sendFile(File file) {
        try {
            HttpResponse<JsonNode> response = Unirest.put(server).field("misc", file).asJson();
        } catch (UnirestException ex) {
            return false;
        }
        return true;
    }
    
    public static boolean runScript(File script) {
        if (!FilenameUtils.getExtension(script.getName()).equals("sh"))
            throw new IllegalArgumentException("File was not a bash script");
        try {
            HttpResponse<JsonNode> fileResponse = Unirest.put(server).field("bash",script).asJson();
            HttpResponse<JsonNode> commandResponse = Unirest.post(server).field("run", "bash/"+script.getName()).asJson();
            return true;
        } catch (UnirestException ex) {
            Logger.getLogger(ServerIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean runPython(File script) {
        if (!FilenameUtils.getExtension(script.getName()).equals("sh"))
            throw new IllegalArgumentException("File was not a bash script");
        try {
            HttpResponse<JsonNode> fileResponse = Unirest.put(server).field("python",script).asJson();
            HttpResponse<JsonNode> commandResponse = Unirest.post(server).field("run", "python/"+script.getName()).asJson();
            return true;
        } catch (UnirestException ex) {
            Logger.getLogger(ServerIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean runPython3(File script) {
        if (!FilenameUtils.getExtension(script.getName()).equals("py"))
            throw new IllegalArgumentException("File was not a python script");
        try {
            HttpResponse<JsonNode> fileResponse = Unirest.put(server).field("python",script).asJson();
            HttpResponse<JsonNode> commandResponse = Unirest.post(server).field("run", "python3/"+script.getName()).asJson();
            return true;
        } catch (UnirestException ex) {
            Logger.getLogger(ServerIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
