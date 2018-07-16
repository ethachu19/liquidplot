/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aeternum.liquidplot.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Ethan Mak
 */
public class FileContentUtils {
    public static void convertToUNIXEndings(File f) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String s = "";
        String temp;
        while ((temp = br.readLine()) != null) {
            s += temp + "\n";
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));
        bw.write(s);
        bw.close();
        br.close();
    }
}
