package ui;

import javax.swing.*;

/*
  Displays user interface
 */

/**
 * SOURCES:
 * https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/
 * examples/components/DialogDemoProject/src/components/DialogDemo.java
 * for implementation on running the GUI
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UIManager.put("swing.boldMetal", false);
                new WatchListApp();
            }
        });
    }
}