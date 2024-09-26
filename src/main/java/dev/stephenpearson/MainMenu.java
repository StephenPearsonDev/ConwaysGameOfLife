package dev.stephenpearson;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenu {
    JFrame frame;

    public MainMenu() {
        frame = new JFrame("Conway's Game of Life - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(60, 63, 65));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(60, 63, 65));
        JButton titleButton = new JButton("Conway's Game of Life");
        titleButton.setEnabled(false);
        titleButton.setForeground(Color.WHITE);
        titleButton.setBackground(new Color(60, 63, 65));
        titleButton.setFont(new Font("Arial", Font.BOLD, 28));
        titleButton.setPreferredSize(new Dimension(450, 80));
        titleButton.setFocusPainted(false);
        titlePanel.add(titleButton);
        frame.add(titlePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 100));
        buttonPanel.setBackground(new Color(60, 63, 65));

        JButton startButton = new JButton("Start Simulation");
        JButton exitButton = new JButton("Exit");

        Dimension buttonSize = new Dimension(200, 60);
        startButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));

        startButton.setBackground(new Color(75, 110, 175));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);

        exitButton.setBackground(new Color(175, 75, 75));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Conway();
                frame.dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
