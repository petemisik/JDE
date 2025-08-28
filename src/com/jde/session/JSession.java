package com.jde.session;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;




public class JSession {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        System.out.println("JDE Session Manager: Initializing...");

        JFrame frame = new JFrame("JDE Session");
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel desktopPanel = new JPanel();
        desktopPanel.setBackground(new Color(30, 80, 150));
        frame.setContentPane(desktopPanel);


        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        System.out.println("JDE Session Manager: Entering fullscreen mode...");
        device.setFullScreenWindow(frame);

        frame.setVisible(true);

        System.out.println("JDE Session Manager: GUI is visible. Session started");

    }
}