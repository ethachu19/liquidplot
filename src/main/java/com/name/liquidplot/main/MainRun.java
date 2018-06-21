/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.name.liquidplot.main;

import com.name.liquidplot.communication.ServerIO;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ethan Mak
 */
public class MainRun {
    public static void main(String args[]) {
        try {
            ServerIO.openClient();
//            Scanner scan = new Scanner(System.in);
//            ServerIO.sendFile(scan.nextLine(), "/home/pi/Documents/labRobo/scripts/");
//            scan.close();
            ServerIO.runCommand("ping -c 1 google.com");
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
