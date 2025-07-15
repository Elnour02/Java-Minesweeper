package view;

import javax.swing.*;
import java.awt.*;

import controller.GameLogic;

public class MainFrame {

    private GameLogic gameLogic;
    private int screenWidth;
    private int screenHeight;
    private int boardWidth;
    private int boardHeight;
    private JFrame frame;
    private UpperPanel upperPanel;
    private BoardPanel boardPanel;
    private SettingsPanel settingsPanel;
    private StartPanel startPanel;
    private StatsPanel statsPanel;
    private JPanel centerPanel;

    public MainFrame(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int)screenSize.getWidth();
        screenHeight = (int)screenSize.getHeight();
        createGUI();
    }

    private void createGUI() {
        createFrame();
        setBoardSize();
        centerPanel = new JPanel(new CardLayout());
        upperPanel = new UpperPanel(this, frame, screenWidth, screenHeight, boardWidth);
        startPanel = new StartPanel(this, centerPanel, screenWidth, screenHeight, boardWidth, boardHeight);
        boardPanel = new BoardPanel(this, centerPanel, screenWidth, screenHeight, boardWidth, boardHeight);
        settingsPanel = new SettingsPanel(this, centerPanel, screenWidth, screenHeight, boardWidth, boardHeight);
        statsPanel = new StatsPanel(this, centerPanel, screenWidth, screenHeight, boardWidth, boardHeight);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private void createFrame() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setBounds((int)(screenWidth/2.666), (int)(screenHeight/9.6), (int)(screenWidth/4.266), (int)(screenHeight/1.986));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    gameLogic.saveData();
                    System.exit(0);
                }
            });
    }

    private void setBoardSize() {
        boardWidth = closestFittingWidth();
        boardHeight = closestFittingHeight();
    }

    private int closestFittingWidth() {
        int calculatedWidth = (int)(screenWidth/4.491);
        int remainder = calculatedWidth % 16;
        if (remainder == 0) return calculatedWidth;
        calculatedWidth = calculatedWidth + (16 - remainder);
        while ((calculatedWidth + (int)(screenWidth/128)*2) % 5 != 0) {
            calculatedWidth += 16;
        }
        return calculatedWidth;
    }

    private int closestFittingHeight() {
        int calculatedHeight = (int)(screenHeight/2.322);
        int remainder = calculatedHeight % 16;
        if (remainder == 0) return calculatedHeight;
        return calculatedHeight + (16 - remainder);
    }

    public void restartgame() {
        gameLogic.restartGame();
    }

    public void boardInput(int row, int col) {
        long startTime = System.nanoTime();
        gameLogic.boardInput(row, col);
        long endTime = System.nanoTime();
        System.out.println("Total time: " + ((endTime - startTime)/1000) + " \u00B5s");
    }

    public void revealTile(int row, int col, String appearance) {
        boardPanel.revealTile(row, col, appearance);
    }

    public void revealMine(int row, int col, boolean clickedOn) {
        boardPanel.revealMine(row, col, clickedOn);
    }

    public void freezeBoard() {
        boardPanel.freezeBoard();
    }

    public void resetBoard() {
        boardPanel.resetBoard();
    }

    public void toggleFlag(int row, int col) {
        gameLogic.toggleFlag(row, col);
    }

    public void setFlag(int row, int col) {
        boardPanel.setFlag(row, col);
    }

    public void removeFlag(int row, int col) {
        boardPanel.removeFlag(row, col);
    }

    public boolean getButtonStatus(int row, int col) {
        return boardPanel.getButtonStatus(row, col);
    }

    public void updateNumOfMines(int numOfMines) {
        upperPanel.updateNumOfMines(numOfMines);
    } 

    public void updateTime(String time) {
        upperPanel.updateTime(time);
    }

    public void showSettingsPanel(int origin) {
        CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
        if (origin == 1) {
            cardLayout.next(centerPanel);
            upperPanel.disableButtons();
        }
        else cardLayout.previous(centerPanel);
    }

    public void showBoardPanel(int origin) {
        CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
        upperPanel.enableButtons();
        if (origin == 0) cardLayout.next(centerPanel);
        else cardLayout.previous(centerPanel);
    }

    public void showStatsPanel() {
        gameLogic.updateStats();
        CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
        cardLayout.next(centerPanel);
    }

    public void updateGamesPlayed(int gamesPlayed) {
        statsPanel.updateGamesPlayed(gamesPlayed);
    }

    public void updateGamesWon(int gamesWon) {
        statsPanel.updateGamesWon(gamesWon);
    }

    public void updateWinRatio(int winRatio) {
        statsPanel.updateWinRatio(winRatio);
    }

    public void updateBestTime(int bestTimeMinutes, int bestTimeSeconds) {
        statsPanel.updateBestTime(bestTimeMinutes, bestTimeSeconds);
    }

    public void registerUser(String name, String password) {
        gameLogic.registerUser(name, password);
    }

    public void login(String name, String password) {
        gameLogic.login(name, password);
    }

    public void displayMessageAtStart(String message) {
        startPanel.displayMessage(message);
    }

}
