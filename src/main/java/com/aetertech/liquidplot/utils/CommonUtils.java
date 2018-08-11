/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aetertech.liquidplot.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ethan Mak
 */
public class CommonUtils {
    public static boolean delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            return false;
        }
        return true;
    }
}
