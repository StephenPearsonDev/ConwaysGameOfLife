package dev.stephenpearson;

import java.awt.Color;
import java.util.Random;

public class Board {
    private int rows;
    private int cols;
    private int[][] currentState;
    private int[][] nextState;
    private Color[][] blockColors;
    private boolean isColorize;
    private int speedLevel;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        currentState = new int[rows][cols];
        nextState = new int[rows][cols];
        blockColors = new Color[rows][cols];
        isColorize = false;
        speedLevel = 5;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getSpeedLevel() {
        return speedLevel;
    }

    public void increaseSpeed() {
        if (speedLevel < 10) {
            speedLevel++;
        }
    }

    public void decreaseSpeed() {
        if (speedLevel > 1) {
            speedLevel--;
        }
    }

    public void setColorize(boolean colorize) {
        isColorize = colorize;
        if (!colorize) {
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    if (currentState[y][x] == 1) {
                        blockColors[y][x] = null;
                    }
                }
            }
        } else {
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    if (currentState[y][x] == 1 && blockColors[y][x] == null) {
                        blockColors[y][x] = generateRandomPastelColor();
                    }
                }
            }
        }
    }

    public void setCellState(int x, int y, int state) {
        if (isValidCell(x, y)) {
            currentState[y][x] = state;
            if (isColorize && state == 1) {
                blockColors[y][x] = generateRandomPastelColor();
            } else {
                blockColors[y][x] = null;
            }
        }
    }

    public void toggleCellState(int x, int y) {
        if (isValidCell(x, y)) {
            currentState[y][x] = currentState[y][x] == 1 ? 0 : 1;
            if (isColorize && currentState[y][x] == 1) {
                blockColors[y][x] = generateRandomPastelColor();
            } else {
                blockColors[y][x] = null;
            }
        }
    }

    public int getCellState(int x, int y) {
        if (isValidCell(x, y)) {
            return currentState[y][x];
        }
        return 0;
    }

    public Color getCellColor(int x, int y) {
        if (isValidCell(x, y)) {
            return blockColors[y][x];
        }
        return Color.BLACK;
    }

    public void updateGeneration() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int neighbors = countAliveNeighbors(x, y);
                if (currentState[y][x] == 1) {
                    if (neighbors == 2 || neighbors == 3) {
                        nextState[y][x] = 1;
                        blockColors[y][x] = blockColors[y][x];
                    } else {
                        nextState[y][x] = 0;
                        blockColors[y][x] = null;
                    }
                } else {
                    if (neighbors == 3) {
                        nextState[y][x] = 1;
                        if (isColorize) {
                            blockColors[y][x] = generateRandomPastelColor();
                        } else {
                            blockColors[y][x] = null;
                        }
                    } else {
                        nextState[y][x] = 0;
                        blockColors[y][x] = null;
                    }
                }
            }
        }
        int[][] temp = currentState;
        currentState = nextState;
        nextState = temp;
    }

    private int countAliveNeighbors(int x, int y) {
        int count = 0;
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (isValidCell(nx, ny)) {
                count += currentState[ny][nx];
            }
        }
        return count;
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < cols && y >= 0 && y < rows;
    }

    public void clearBoard() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                currentState[y][x] = 0;
                blockColors[y][x] = null;
            }
        }
    }

    private Color generateRandomPastelColor() {
        Random rand = new Random();
        int r = rand.nextInt(128) + 127;
        int g = rand.nextInt(128) + 127;
        int b = rand.nextInt(128) + 127;
        return new Color(r, g, b);
    }
}
