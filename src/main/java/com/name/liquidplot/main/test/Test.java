/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.name.liquidplot.main.test;

import com.name.liquidplot.communication.ServerIO;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.OpenSSHKnownHosts;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

/**
 *
 * @author Ethan Mak
 */
public class Test {
   public static void main(String... args)
            throws IOException {
        final SSHClient ssh = new SSHClient();
        ssh.addHostKeyVerifier(new PromiscuousVerifier());
        ssh.connect("192.168.1.13");
        try {
            ssh.authPassword("pi", "raspberry");
            final Session session = ssh.startSession();
            try {
                final Command cmd = session.exec("echo raspberry | sudo -S reboot");
            } finally {
                session.close();
            }
        } finally {
            ssh.disconnect();
        }
    }

}
