/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.name.liquidplot.communication;

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

/**
 *
 * @author Ethan Mak
 */
public class ServerIO {

    private static SSHClient ssh = new SSHClient();
    final private static Console con = System.console();

    public static void openClient() throws IOException {
        ssh.addHostKeyVerifier(new PromiscuousVerifier());
//        ssh.loadKnownHosts(new File("known_hosts.txt"));
        ssh.connect("ethanPi", 22);
        ssh.authPassword("pi", "raspberry");
//        ssh.authPublickey("pi", "id_rsa.txt");
    }

    public static boolean closeClient() throws IOException{
        ssh.disconnect();
        return true;
    }

    public static boolean sendFile(String localPath, String remotePath) {
        try {
            SFTPClient sftp = ssh.newSFTPClient();
            try {
                sftp.put(new FileSystemFile(localPath), remotePath);
            } finally {
                sftp.close();
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
            con.writer().print(IOUtils.readFully(cmd.getInputStream()).toString());
            cmd.join(5, TimeUnit.SECONDS);
            con.writer().print("\n** exit status: " + cmd.getExitStatus());
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
}
