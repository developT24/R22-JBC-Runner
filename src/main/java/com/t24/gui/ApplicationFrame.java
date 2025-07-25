package com.t24.gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.t24.pacs.ExecuteJBCRoutine;

/**
 * TODO: This program will accept jBC routine name from the user and executes it.
 *
 * @author bsauravkumar@temenos.com
 *
 */
public class ApplicationFrame {
    
    /*
     * Accept valid jBC routine name and execute it.
     */
    protected static void createApplicationScreen() {
        
        JFrame frame = new JFrame("PACS Correction Utility");
        final JTextField fieldRoutineName = new JTextField(30);
        JButton actionButton = new JButton("Run jBC");  // Execute jBC routine
        JButton exitButton = new JButton("Exit");       // Exit the application
        
        /*
         * Action for Run jBC button
         */
        actionButton.addActionListener(e -> {
            String routineName = fieldRoutineName.getText();
            if (routineName.isEmpty())
                JOptionPane.showMessageDialog(frame, "Missing routine name.");
            else if (routineName.endsWith(".b"))
                JOptionPane.showMessageDialog(frame, "Kindly ignore .b extension of jBC routine.");
            else
                invokeJBCRotuine(frame, routineName);
        });
        
        /*
         * Action for Exit button
         */
        exitButton.addActionListener(e -> {
            closeApp();
        });
        
        /*
         * Create the panel now
         */
        JPanel panel = new JPanel();
        panel.add(new JLabel("Routine Details"));
        panel.add(fieldRoutineName);
        panel.add(actionButton);
        panel.add(exitButton);
        
        /*
         * Add panel in the frame
         */
        frame.add(panel);
        frame.setSize(500, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    /**
     * This method will start progress bar and invokes jBC routine. The progress bar will also stop user to perform any other task during the routine execution.
     * Once the routine is successfully executed, the progress bar will also close.
     * @param JFrame frame : Current frame
     * @param String routineName : jBC routine name
     */
    private static void invokeJBCRotuine(JFrame frame, String routineName) {
        
        ExecuteJBCRoutine jBCRoutine = new ExecuteJBCRoutine();
        boolean routineExists = jBCRoutine.checkRoutineExists(routineName);
        if (!routineExists) {
            JOptionPane.showMessageDialog(frame, "Missing routine " + routineName);
            return;
        }
        
        JDialog progressDialog = new JDialog(frame, "Executing " + routineName, true);
        progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        progressDialog.setSize(300, 100);
        progressDialog.setLocationRelativeTo(frame);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setString("Running...");
        progressBar.setStringPainted(true);

        progressDialog.add(progressBar);
        progressDialog.pack();
        
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return jBCRoutine.invokeJBC(routineName);
            }
            
            @Override
            protected void done() {
                // Close the dialog and handle result
                progressDialog.dispose();
                try {
                    String response = get(); // retrieve result
                    JOptionPane.showMessageDialog(frame, "Executed " + routineName + " with response " + response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        };
        
        worker.execute();                   // Start task
        progressDialog.setVisible(true);    // Show modal dialog
    }

    /**
     * This function will close the application.
     */
    private static void closeApp() {
        JOptionPane.showMessageDialog(null, "Exiting the application");
        System.exit(0);
    }

}
