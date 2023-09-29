package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    private static final Random RANDOM = new Random();
    private final int HUMAN_DOT = 1;
    private final int AI_DOT = 1;
    private final int EMPTY_DOT = 1;
    private int fieldSizeY = 3;
    private int fieldSizeX = 3;
    private char[][] field;
    private int panelWidth;
    private int panelHeight;
    private int cellWidth;
    private int cellHeight;
    private int WIN_COUNT = 3;
    private final int DOT_PADDING = 5;
    private int gameOverType;
    private static final int STATE_DRAW = 0;

    private static final int STATE_WIN_HUMAN = 1;

    private static final int STATE_WIN_AI = 2;
    private static final String MSG_WIN_HUMAN = "Player WIN!";
    private static final String MSG_WIN_AI = "AI WIN!";
    private static final String MSG_DRAW = "DRAW!";
    Map() {
        setBackground(Color.BLACK);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });
    }

    private void update(MouseEvent e) {
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        if (!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY)) return;
        field[cellX][cellY] = HUMAN_DOT;
        System.out.printf("x= %d, y=%d\n", cellX, cellY);
        if (checkEndGame(HUMAN_DOT, STATE_WIN_HUMAN)) return;
        aiTurn();
        repaint();
        if (checkEndGame(AI_DOT, STATE_WIN_AI)) return;
    }
    private boolean checkEndGame(int dot, int gameOverType){
        if (checkWin(dot)){
            this.gameOverType = gameOverType;
            repaint();
            return true;
        }
        if (isMapFull()){
            this.gameOverType = STATE_DRAW;
            repaint();
            return true;
        }
        return false;
    }

    void startNewGame(int mode, int fSzX, int fSzY, int wLen) {
        System.out.printf("Mode: %d;\nSize: x=%d, y=%d;\nWin len: %d", mode, fSzX, fSzY, wLen);
        initMap();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        initMap();
        panelWidth = getWidth();
        panelHeight = getHeight();
        cellWidth = panelWidth / SettingWindow.sizeX;
        cellHeight = panelHeight / SettingWindow.sizeY;

        g.setColor(Color.YELLOW);
        for (int h = 0; h < SettingWindow.sizeX; h++) {
            int y = h * cellHeight;
            g.drawLine(0, y, panelWidth, y);
        }
        for (int w = 0; w < SettingWindow.sizeY; w++) {
            int x = w * cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }
        for (int y = 0; y < SettingWindow.sizeY-1; y++) {
            for (int x = 0; x < SettingWindow.sizeX-1; x++) {
                if (field[y][x] == EMPTY_DOT) continue;
                if (field[y][x] == HUMAN_DOT) {
                    g.setColor(Color.BLUE);
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else if (field[y][x] == AI_DOT) {
                    g.setColor(Color.RED);
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else {
                    throw new RuntimeException("Unexpected value " + field[x][y]);
                }
            }

        }
    }

    private void initMap() {
        fieldSizeX = SettingWindow.sizeX;
        fieldSizeY = SettingWindow.sizeY;
        field = new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    private boolean isEmptyCell(int x, int y) {
        return field[x][y] == EMPTY_DOT;
    }

    private void aiTurn() {
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));
        field[x][y] = AI_DOT;
    }

    private boolean checkWin(int c) {
        // Проверка по всем горизонталям
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                int winpoint = 0;
                for (int z = 0; z < WIN_COUNT; z++) {
                    if (isValidCell(x, y + z) && !isEmptyCell(x, y + z)) {
                        if (field[x][y + z] == field[x][y]) {
                            winpoint++;
                        } else break;
                    } else break;
                }
                if (winpoint == WIN_COUNT) return true;
            }
        }
        // Проверка по всем вертикалям
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                int winpoint = 0;
                for (int z = 0; z < WIN_COUNT; z++) {
                    if (isValidCell(x + z, y) && !isEmptyCell(x + z, y)) {
                        if (field[x + z][y] == field[x][y]) {
                            winpoint++;
                        } else break;
                    } else break;
                }
                if (winpoint == WIN_COUNT) return true;
            }
        }
        // Проверка по диагоналям вверх
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                int winpoint = 0;
                for (int z = 0; z < WIN_COUNT; z++) {
                    if (isValidCell(x - z, y + z) && !isEmptyCell(x - z, y + z)) {
                        if (field[x - z][y + z] == field[x][y]) {
                            winpoint++;
                        } else break;
                    } else break;
                }
                if (winpoint == WIN_COUNT) return true;
            }
        }
        // Проверка по диагоналям вниз
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                int winpoint = 0;
                for (int z = 0; z < WIN_COUNT; z++) {
                    if (isValidCell(x + z, y + z) && !isEmptyCell(x + z, y + z)) {
                        if (field[x + z][y + z] == field[x][y]) {
                            winpoint++;
                        } else break;
                    } else break;
                }
                if (winpoint == WIN_COUNT) return true;
            }
        }
        return false;
    }

    boolean isMapFull() {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++)
                if (isEmptyCell(i, j)) return false;
        }
        return true;
    }

}

