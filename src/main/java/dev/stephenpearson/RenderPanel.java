package dev.stephenpearson;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class RenderPanel extends JPanel {

    private static final int CELL_SIZE = 10;

    private int panelHeight;
    private int panelWidth;
    private Timer timer;

    Board board;
    private int generation = 0;

    public RenderPanel() {

        board = new Board(60, 60);
        board.clearBoard();


        panelWidth = board.getCols() * CELL_SIZE;
        panelHeight = board.getRows() * CELL_SIZE;
        setPreferredSize(new Dimension(panelWidth, panelHeight));


        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                paintBlock(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paintBlock(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
              
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               
            }

            @Override
            public void mouseEntered(MouseEvent e) {
              
            }

            @Override
            public void mouseExited(MouseEvent e) {
               
            }
        });
    }

    private void paintBlock(MouseEvent e) {
        Point p = e.getPoint();
        int x = p.x / CELL_SIZE;
        int y = p.y / CELL_SIZE;
        if (x >= 0 && x < board.getCols() && y >= 0 && y < board.getRows()) {
            board.toggleCellState(x, y);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderBackground(g);
        paintBlocks(g);
    }

    private void renderBackground(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
  
        for (int i = 0; i <= panelWidth; i += CELL_SIZE) {
            g.drawLine(i, 0, i, panelHeight);
        }
     
        for (int y = 0; y <= panelHeight; y += CELL_SIZE) {
            g.drawLine(0, y, panelWidth, y);
        }
    }

    private void paintBlocks(Graphics g) {
        for (int y = 0; y < board.getRows(); y++) {
            for (int x = 0; x < board.getCols(); x++) {
                if (board.getCellState(x, y) == 1) {
                    Color color = board.getCellColor(x, y);
                    g.setColor(color != null ? color : Color.BLACK);
                    g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    public void moveGenerationForward() {
        board.updateGeneration();
        generation++;
    }

    public void startSimulation() {
        if (timer != null && timer.isRunning()) {
            return;
        }
        int delay = getDelayFromSpeed();
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                moveGenerationForward();
                repaint();
            }
        };
        timer = new Timer(delay, taskPerformer);
        timer.start();
    }

    public void stopSimulation() {
        if (timer != null) {
            timer.stop();
            timer = null;
            generation = 0;
        }
    }

    public void pauseSimulation() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        } else if (timer != null && !timer.isRunning()) {
            timer.start();
        }
    }

    public void stepForward() {
        moveGenerationForward();
        repaint();
    }

    public void updateBoardBlocks() {
        board.updateGeneration();
    }

    public void reset() {
        stopSimulation();
        board.clearBoard();
        repaint();
    }

    public void colorizeBlocks() {
        board.setColorize(true);
        repaint();
    }

    public void revertColors() {
        board.setColorize(false);
        repaint();
    }

    public void increaseSpeed() {
        board.increaseSpeed();
        if (timer != null && timer.isRunning()) {
            timer.setDelay(getDelayFromSpeed());
        }
    }

    public void decreaseSpeed() {
        board.decreaseSpeed();
        if (timer != null && timer.isRunning()) {
            timer.setDelay(getDelayFromSpeed());
        }
    }

    private int getDelayFromSpeed() {
        return 1100 - (board.getSpeedLevel() * 100);
    }

    public int getGeneration() {
        return generation;
    }

    public int getSpeedLevel() {
        return board.getSpeedLevel();
    }
}
