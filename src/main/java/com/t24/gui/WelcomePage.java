package com.t24.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * TODO: This program will build the starting dialog screen for user with a brief introduction about the application. On clicking OK button we will proceed
 * to next screen to input the jBC routine name.
 *
 * @author bsauravkumar@temenos.com
 *
 */
public class WelcomePage {
    
    /*
     * Creates home screen and launches next screen if user clicks OK button.
     */
    protected static void createWelcomeScreen() {
        
        JFrame welcomeFrame = new JFrame("Welcome to jBC Runner");
        welcomeFrame.setSize(700, 400);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setLayout(new BorderLayout());
        
        JTextArea welcomeText = new JTextArea(
                "Welcome to the Temenos jBC runner application!\n" +
                "This app will execute Temenos jBC routine.\n" +
                "It can be used mostly to perform correction tasks to T24 tables which need jBC\n" +
                "F.WRITE (or direct JBASE WRITE command) to write on T24 tables. However it can be\n" +
                "used to do anything in the jBC routine like to extract data or post OFS messages.\n" +
                "All logic to perform CRUD actions must be taken care in jBC routine.\n" +
                "You need to setup below properties in config.properties file before proceeding:\n" +
                "tafj.home = Path of TAFJ.\n" +
                "tafj.property.name = File name where TAFJ properties are set.\n" +
                "external.jar = Jar name where class files of jBC routines present.\n" +
                "Click OK to proceed to the main screen if your setup is made. You can enter the routine\n" +
                "in next screen."
                );
        
        welcomeText.setFont(new Font("Arial", Font.ITALIC, 18));
        welcomeText.setEditable(false);
        welcomeText.setOpaque(false);
        welcomeText.setLineWrap(true);
        welcomeText.setWrapStyleWord(true);
        welcomeText.setBackground(Color.CYAN);
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            welcomeFrame.dispose();  // Close welcome frame
            ApplicationFrame.createApplicationScreen(); // Show next frame to the user
        });
        
        welcomeFrame.add(welcomeText, BorderLayout.CENTER);
        welcomeFrame.add(okButton, BorderLayout.SOUTH);
        welcomeFrame.setLocationRelativeTo(null); // center
        welcomeFrame.setVisible(true);

    }

}
