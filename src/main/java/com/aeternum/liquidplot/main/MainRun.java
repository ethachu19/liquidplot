/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.name.liquidplot.main;

import com.name.liquidplot.communication.ServerIO;
import com.name.liquidplot.utils.CommonUtils;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Ethan Mak
 */
public class MainRun {
    
    public static void main(String args[]) {
        try {
            ServerIO.openClient();
//            Scanner scan = new Scanner(System.in);
//          scan.nextLine()
            ServerIO.sendFile(new File("C:\\Users\\Ethan Mak\\Documents\\launch.txt"), "~/Documents/labRobo/misc/");
//            scan.close();
//            ServerIO.runCommand("ping www.google.com -c 5");
//            ServerIO.runCommand("gpio -g write 5 1");
//            CommonUtils.delay(1000);
//            ServerIO.runCommand("gpio -g write 5 0");
            
        } catch (IOException ex) {
            Logger.getLogger(MainRun.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ServerIO.closeClient();
            } catch (IOException ex) {
                Logger.getLogger(MainRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
