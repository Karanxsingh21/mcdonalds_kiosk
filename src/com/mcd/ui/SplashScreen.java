package com.mcd.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;

public class SplashScreen extends JWindow {

    JProgressBar progressBar;

    public SplashScreen() {

        setSize(900, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        // Logo
        ImageIcon image = new ImageIcon("images/mcd.png");
        JLabel lbl = new JLabel(image, JLabel.CENTER);
        add(lbl, BorderLayout.CENTER);

        // Progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.RED);
        add(progressBar, BorderLayout.SOUTH);

        loadProgress();
    }

    void loadProgress() {
        new Timer(30, e -> {
            int val = progressBar.getValue();
            if (val < 100) {
                progressBar.setValue(val + 1);
            } else {
                ((Timer)e.getSource()).stop();
                dispose();
                new Login().setVisible(true);
            }
        }).start();
    }

    public static void main(String[] args) {
        new SplashScreen().setVisible(true);
    }
}
