package ui;

import model.Event;
import model.EventLog;
import model.WatchList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
  Constructs a watch list frame
 */

/**
 * SOURCES:
 * Using Swing Components: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html#DialogDemo
 * for frame implementation, action listener, window listener
 */
public class WatchListFrame extends JFrame implements ActionListener {
    protected JFrame watchListFrame;
    private WatchListPanel watchListPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveMenu;
    private JMenuItem loadMenu;

    //EFFECTS: constructs frame for the watch list
    public WatchListFrame(WatchList watchList) {
        watchListFrame = new JFrame();
        watchListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        watchListPanel = new WatchListPanel(watchList);
        watchListFrame.add(watchListPanel, BorderLayout.CENTER);

        setUpMenuBar();

        watchListFrame.pack();
        watchListFrame.setVisible(true);
    }

    //MODIFIES: this, menuBar, fileMenu, saveMenu, loadMenu
    //EFFECTS: sets up menu bar
    public void setUpMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        menuBar.setOpaque(true);
        fileMenu = new JMenu("File");
        fileMenu.setBackground(Color.BLACK);
        fileMenu.setForeground(Color.WHITE);
        menuBar.add(fileMenu);
        fileMenu.setOpaque(true);
        saveMenu = new JMenuItem("Save");
        saveMenu.setBackground(Color.GRAY);
        saveMenu.addActionListener(this);
        fileMenu.add(saveMenu);
        loadMenu = new JMenuItem("Load");
        loadMenu.setBackground(Color.GRAY);
        loadMenu.addActionListener(this);
        fileMenu.add(loadMenu);
        watchListFrame.add(menuBar, BorderLayout.PAGE_START);
    }

    @Override
    //EFFECTS: processes the actions done on the frame
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (saveMenu.equals(source)) {
            watchListPanel.createSavePopUp();
        }
        if (loadMenu.equals(source)) {
            watchListPanel.createLoadPopUp();
        }
    }
}
