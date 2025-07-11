package view;

import javax.swing.*;
import java.awt.*;

import controller.GameLogic;

public class MainFrame {

    private GameLogic gameLogic;
    private int screenWidth;
    private int screenHeight;
    private JFrame frame;
    private UpperPanel upperPanel;
    private BoardPanel boardPanel;
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
        centerPanel = new JPanel(new CardLayout());
        boardPanel = new BoardPanel(this, centerPanel, screenWidth, screenHeight);
        upperPanel = new UpperPanel(this, frame, screenWidth, screenHeight);
        statsPanel = new StatsPanel(this, centerPanel, screenWidth, screenHeight);
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
    }

    public void startGame() {
        gameLogic.startGame();
    }

    public void restartGame() {
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

    public int getBoardWidth() {
        return boardPanel.getBoardWidth();
    }

    public int getBoardHeight() {
        return boardPanel.getBoardHeight();
    }

    public void showNextPanel() {
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

}
