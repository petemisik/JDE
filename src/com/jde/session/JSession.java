package com.jde.session;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File; // Import File
import java.io.IOException; // Import IOException

public class JSession {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        System.out.println("JSession: Initializing...");
        JFrame frame = new JFrame("JDE Session");
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.out.println("JSession: Escape key pressed. Shutting down.");
                    System.exit(0);
                }
            }
        });

        JPanel desktopPanel = new JPanel();
        desktopPanel.setBackground(new Color(30, 80, 150));
        frame.setContentPane(desktopPanel);

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        device.setFullScreenWindow(frame);
        frame.setVisible(true);
        
        System.out.println("JSession: GUI is visible. Session started.");
        
        // *** NEW CODE TO LAUNCH JWINMAN ***
        launchWindowManager();
    }
    
    private static void launchWindowManager() {
        System.out.println("JSession: Attempting to launch JWinMan...");
        try {
            // Define the location of our JARs.
            // On the server, this will be something like /opt/jde/
            String jdeHome = System.getProperty("jde.home");
            if (jdeHome == null) {
                System.err.println("FATAL: 'jde.home' system property not set!");
                System.exit(1);
            }

            String jarsDir = jdeHome + "/jars";
            String libDir = jdeHome + "/lib";

            // Build the classpath string. The separator is ":" on Linux.
            String classpath = String.join(File.pathSeparator,
                jarsDir + "/jwinman.jar",
                libDir + "/jna.jar",
                libDir + "/jna-platform.jar"
            );
            
            // Build the command to execute: java -cp <classpath> com.jde.winman.JWinMan
            ProcessBuilder pb = new ProcessBuilder(
                "java",
                "-cp",
                classpath,
                "com.jde.winman.JWinMan"
            );

            // This is important! It makes the JWinMan logs appear in JSession's log.
            pb.inheritIO();

            // Start the process!
            pb.start();
            System.out.println("JSession: JWinMan process has been launched.");
            
        } catch (IOException e) {
            System.err.println("JSession: Failed to launch JWinMan!");
            e.printStackTrace();
        }
    }
}