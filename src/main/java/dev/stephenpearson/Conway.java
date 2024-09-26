package dev.stephenpearson;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Conway {

    JFrame frame;
    RenderPanel renderPanel;
    JLabel generationLabel;
    JLabel speedLabel;

    public Conway() {
        frame = new JFrame("Conway's Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Info Panel at the top
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        generationLabel = new JLabel("Generation: 0");
        speedLabel = new JLabel("Speed: 5");
        generationLabel.setFont(new Font("Arial", Font.BOLD, 16));
        speedLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(generationLabel);
        infoPanel.add(speedLabel);
        frame.add(infoPanel, BorderLayout.NORTH);
        frame.setResizable(false);

      
        renderPanel = new RenderPanel();
        frame.add(renderPanel, BorderLayout.CENTER);


        JPanel mainButtonPanel = new JPanel();
        mainButtonPanel.setLayout(new BoxLayout(mainButtonPanel, BoxLayout.Y_AXIS));
        mainButtonPanel.setPreferredSize(new Dimension(200, renderPanel.getPreferredSize().height));
        mainButtonPanel.setMaximumSize(new Dimension(200, Integer.MAX_VALUE));


        JPanel simulationControlPanel = new JPanel();
        simulationControlPanel.setLayout(new BoxLayout(simulationControlPanel, BoxLayout.Y_AXIS));
        simulationControlPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        simulationControlPanel.setMaximumSize(new Dimension(180, 300));

        JButton startSimulation = new JButton("Start");
        JButton pauseSimulation = new JButton("Pause");
        JButton stopSimulation = new JButton("Stop");
        JButton advanceSimulation = new JButton("Step +1");
        JButton resetSimulation = new JButton("Reset");
        JButton colorizeButton = new JButton("Colorize");
        JButton revertColorButton = new JButton("Revert Colors");

        Dimension buttonSize = new Dimension(160, 40);
        startSimulation.setMaximumSize(buttonSize);
        pauseSimulation.setMaximumSize(buttonSize);
        stopSimulation.setMaximumSize(buttonSize);
        advanceSimulation.setMaximumSize(buttonSize);
        resetSimulation.setMaximumSize(buttonSize);
        colorizeButton.setMaximumSize(buttonSize);
        revertColorButton.setMaximumSize(buttonSize);

        simulationControlPanel.add(startSimulation);
        simulationControlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        simulationControlPanel.add(pauseSimulation);
        simulationControlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        simulationControlPanel.add(stopSimulation);
        simulationControlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        simulationControlPanel.add(advanceSimulation);
        simulationControlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        simulationControlPanel.add(resetSimulation);
        simulationControlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        simulationControlPanel.add(colorizeButton);
        simulationControlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        simulationControlPanel.add(revertColorButton);

        // Add some spacing between control panels
        mainButtonPanel.add(Box.createVerticalStrut(20));
        mainButtonPanel.add(simulationControlPanel);
        mainButtonPanel.add(Box.createVerticalStrut(30));

        // Speed Control Panel
        JPanel speedControlPanel = new JPanel();
        speedControlPanel.setLayout(new BoxLayout(speedControlPanel, BoxLayout.Y_AXIS));
        speedControlPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        speedControlPanel.setMaximumSize(new Dimension(180, 200));

        JButton increaseSpeedButton = new JButton("Increase Speed");
        JButton decreaseSpeedButton = new JButton("Decrease Speed");
        JButton mainMenuButton = new JButton("Main Menu");

        Dimension speedButtonSize = new Dimension(160, 40);
        increaseSpeedButton.setMaximumSize(speedButtonSize);
        decreaseSpeedButton.setMaximumSize(speedButtonSize);
        mainMenuButton.setMaximumSize(speedButtonSize);

        speedControlPanel.add(increaseSpeedButton);
        speedControlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        speedControlPanel.add(decreaseSpeedButton);
        speedControlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        speedControlPanel.add(mainMenuButton);

        mainButtonPanel.add(speedControlPanel);
        mainButtonPanel.add(Box.createVerticalGlue());

        frame.add(mainButtonPanel, BorderLayout.EAST);


        startSimulation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renderPanel.startSimulation();
            }
        });

        pauseSimulation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renderPanel.pauseSimulation();
            }
        });

        stopSimulation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renderPanel.stopSimulation();
            }
        });

        advanceSimulation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renderPanel.stepForward();
            }
        });

        resetSimulation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renderPanel.reset();
            }
        });

        colorizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renderPanel.colorizeBlocks();
            }
        });

        revertColorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renderPanel.revertColors();
            }
        });

        increaseSpeedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renderPanel.increaseSpeed();
                updateSpeedLabel();
            }
        });

        decreaseSpeedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renderPanel.decreaseSpeed();
                updateSpeedLabel();
            }
        });

        mainMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                renderPanel.stopSimulation();
                frame.dispose();
                new MainMenu();
            }
        });

        Timer generationTimer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generationLabel.setText("Generation: " + renderPanel.getGeneration());
            }
        });
        generationTimer.start();

        Timer speedTimer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                speedLabel.setText("Speed: " + renderPanel.getSpeedLevel());
            }
        });
        speedTimer.start();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateSpeedLabel() {
        speedLabel.setText("Speed: " + renderPanel.getSpeedLevel());
    }
}
