/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aetertech.liquidplot.utils;

/**
 *
 * @author Ethan Mak
 */
import java.util.Locale;
public final class OsCheck {
  /**
   * types of Operating Systems
   */
  public enum OSType {
    Windows, MacOS, Linux, Other
  };

  // cached result of OS detection
  public static OSType detectedOS;

  /**
   * detect the operating system from the os.name System property and cache
   * the result
   * 
   * @returns - the operating system detected
   */
  public static OSType getOperatingSystemType() {
    if (detectedOS == null) {
      String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
      if ((OS.contains("mac")) || (OS.contains("darwin"))) {
        detectedOS = OSType.MacOS;
      } else if (OS.contains("win")) {
        detectedOS = OSType.Windows;
      } else if (OS.contains("nux")) {
        detectedOS = OSType.Linux;
      } else {
        detectedOS = OSType.Other;
      }
    }
    return detectedOS;
  }
}