/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.name.liquidplot.communication;

import com.name.liquidplot.utils.FileContentUtils;
import com.name.liquidplot.utils.OsCheck;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.FileSystemFile;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Ethan Mak
 */
public class ServerIO {

    private static SSHClient ssh = new SSHClient();
    final private static Console con = System.console();

    public static void openClient() throws IOException {
        ssh.addHostKeyVerifier(new PromiscuousVerifier());
        ssh.loadKnownHosts(new File("C:\\Users\\Ethan Mak\\.ssh\\known_hosts.txt"));
        ssh.connect("ethanPi", 22);
        //ssh.authPassword("pi", "raspberry");
        ssh.authPublickey("pi", "C:\\Users\\Ethan Mak\\.ssh\\id_ed25519");
    }

    public static boolean closeClient() throws IOException{
        ssh.disconnect();
        return true;
    }

    public static boolean sendFile(File localFile, String remotePath) {
        if (!localFile.exists())
            throw new IllegalArgumentException(localFile.getName() + " does not exist");
        remotePath = remotePath.replace("~", "/home/pi");
        try {
            if (OsCheck.detectedOS != OsCheck.OSType.Linux)
                FileContentUtils.convertToUNIXEndings(localFile);
            SFTPClient sftp = ssh.newSFTPClient();
            System.out.println("Opened SFTP client");
            try {
                sftp.put(new FileSystemFile(localFile), remotePath);
                System.out.println("Sent " + localFile.getName());
            } finally {
                sftp.close();
                System.out.println("Closed SFTP Client");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean runCommand(String command) {
        Session session = null;
        try {
            session = ssh.startSession();
            final Command cmd = session.exec(command);
            System.out.println("Ran Command");
//            session.join();
//            IOUtils.readFully(cmd.getInputStream());            
        } catch (ConnectionException ex) {
            Logger.getLogger(ServerIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (TransportException ex) {
            Logger.getLogger(ServerIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(ServerIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (IOException e) {
                // Do Nothing   
            }
        }
        return true;
    }
    
    public static boolean runScript(File script) {
        System.out.println("Run Bash");
        if (!FilenameUtils.getExtension(script.getName()).equals("sh"))
            throw new IllegalArgumentException("File was not a bash script");
        if (!ServerIO.sendFile(script, "~/Documents/labRobo/scripts/"))
            return false;
        if (!ServerIO.runCommand("sh ~/Documents/labRobo/scripts/" + script.getName()))
            return false;
        return true;
    }
    
    public static boolean runPython(File script) {
        if (!FilenameUtils.getExtension(script.getName()).equals("py"))
            throw new IllegalArgumentException("File was not a python script");
        if (!ServerIO.sendFile(script, "~/Documents/labRobo/python/"))
            return false;
        if (!ServerIO.runCommand("python ~/Documents/labRobo/python/" + script.getName()))
            return false;
        return true;
    }
    
    public static boolean runPython3(File script) {
        if (!FilenameUtils.getExtension(script.getName()).equals("py"))
            throw new IllegalArgumentException("File was not a python script");
        if (!ServerIO.sendFile(script, "~/Documents/labRobo/python/"))
            return false;
        if (!ServerIO.runCommand("python3 ~/Documents/labRobo/python/" + script.getName()))
            return false;
        return true;
    }
}
