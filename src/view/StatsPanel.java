package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class StatsPanel {

    private MainFrame mainFrame;
    private int screenWidth;
    private int screenHeight;
    private int boardWidth;
    private int boardHeight;
    private JPanel statsPanel;
    private JPanel centerStatsPanel;
    private Font customFont;
    private JLabel gamesPlayedLabel;
    private JLabel gamesWonLabel;
    private JLabel winRatioLabel;
    private JLabel bestTimeLabel;
    private JButton backButton;
    private ImageIcon checkmark;
    
    public StatsPanel(MainFrame mainFrame, JPanel centerPanel, int screenWidth, int screenHeight, int boardWidth, int boardHeight) {
        this.mainFrame = mainFrame;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        createFonts();
        createImages();
        createStatsPanel();
        createLabels();
        createButtons();
        centerStatsPanel.add(gamesPlayedLabel);
        centerStatsPanel.add(gamesWonLabel);
        centerStatsPanel.add(winRatioLabel);
        centerStatsPanel.add(bestTimeLabel);
        centerStatsPanel.add(backButton);
        centerPanel.add(statsPanel);
    }

    private void createFonts() {
        try {
            File fontFile = new File("resources", "fonts/mine-sweeper.ttf");
            customFont = Font.createFont(0, fontFile).deriveFont((float)(screenHeight/72));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } 
        catch (IOException | FontFormatException e) {
            System.out.println("Font error");
        }
    }

    private void createImages() {
        checkmark = new ImageIcon(Paths.get("resources", "images", "checkmark.png").toString());
        Image newImage = checkmark.getImage().getScaledInstance((int)(screenWidth/73.142), (int)(screenHeight/41.142), 4);
        checkmark = new ImageIcon(newImage);
    }

    private void createStatsPanel() {
        statsPanel = new JPanel();
        statsPanel.setLayout(new BorderLayout());
        statsPanel.setBackground(Color.gray);
        centerStatsPanel = new JPanel();
        centerStatsPanel.setLayout(null);
        centerStatsPanel.setPreferredSize(new Dimension(boardWidth, boardHeight)); 
        statsPanel.setPreferredSize(new Dimension(boardWidth + (int)(screenWidth/128)*2, boardHeight + (int)(screenHeight/72)*2));
        centerStatsPanel.setBackground(Color.gray);
        statsPanel.add(centerStatsPanel, BorderLayout.CENTER);
        for (int i = 0; i < 4; i++) {
            JPanel sidePanel = new JPanel();
            sidePanel.setLayout(new GridLayout());
            sidePanel.setBackground(Color.gray);
            JButton button = new JButton();
            button.setBackground(Color.gray);
            button.setBorder(BorderFactory.createBevelBorder(0));
            button.setEnabled(false);
            if (i == 0) {
                sidePanel.setPreferredSize(new Dimension((int)(screenWidth/128), (int)(screenHeight/2.215)));
                statsPanel.add(sidePanel, BorderLayout.WEST);
            }
            else if (i == 1) {
                sidePanel.setPreferredSize(new Dimension((int)(screenWidth/4.266), (int)(screenHeight/72)));
                statsPanel.add(sidePanel, BorderLayout.SOUTH);
            }
            else if (i == 2) {
                sidePanel.setPreferredSize(new Dimension((int)(screenWidth/4.266), (int)(screenHeight/72)));
                statsPanel.add(sidePanel, BorderLayout.NORTH);
            }
            else {
                sidePanel.setPreferredSize(new Dimension((int)(screenWidth/128), (int)(screenHeight/2.215)));
                statsPanel.add(sidePanel, BorderLayout.EAST);
            }
            sidePanel.add(button);
        }
    }

    private void createLabels() {
        gamesPlayedLabel = new JLabel();
        gamesPlayedLabel.setForeground(Color.black);
        gamesPlayedLabel.setBounds((int)(screenWidth/128), (int)(screenHeight/72), boardWidth - (int)(screenWidth/64), (int)(screenHeight/14.4));
        gamesPlayedLabel.setHorizontalAlignment(JLabel.CENTER);
        gamesWonLabel = new JLabel();
        gamesWonLabel.setForeground(Color.black);
        gamesWonLabel.setBounds((int)(screenWidth/128), (int)(screenHeight/10.285), boardWidth - (int)(screenWidth/64), (int)(screenHeight/14.4));
        gamesWonLabel.setHorizontalAlignment(JLabel.CENTER);
        winRatioLabel = new JLabel();
        winRatioLabel.setForeground(Color.black);
        winRatioLabel.setBounds((int)(screenWidth/128), (int)(screenHeight/5.538), boardWidth - (int)(screenWidth/64), (int)(screenHeight/14.4));
        winRatioLabel.setHorizontalAlignment(JLabel.CENTER);
        bestTimeLabel = new JLabel();
        bestTimeLabel.setForeground(Color.black);
        bestTimeLabel.setBounds((int)(screenWidth/128), (int)(screenHeight/3.789), boardWidth - (int)(screenWidth/64), (int)(screenHeight/14.4));
        bestTimeLabel.setHorizontalAlignment(JLabel.CENTER);
        gamesPlayedLabel.setFont(customFont);
        gamesWonLabel.setFont(customFont);
        winRatioLabel.setFont(customFont);
        bestTimeLabel.setFont(customFont);
    }

    private void createButtons() {
        backButton = new JButton(checkmark);
        backButton.setBackground(Color.gray);
        backButton.setBorder(BorderFactory.createBevelBorder(0));
        backButton.setFocusPainted(false);
        backButton.setBounds(boardWidth/2 - ((int)(screenWidth/17.066))/2, (int)(screenHeight/2.769), (int)(screenWidth/17.066), (int)(screenHeight/19.2));
        backButton.addActionListener(e -> mainFrame.showSettingsPanel(3));
    }

    public void updateGamesPlayed(int gamesPlayed) {
        gamesPlayedLabel.setText("Games played: " + gamesPlayed);
    }

    public void updateGamesWon(int gamesWon) {
        gamesWonLabel.setText("Games won: " + gamesWon);
    }

    public void updateWinRatio(int winRatio) {
        winRatioLabel.setText("Win ratio: " + winRatio + "%");
    }

    public void updateBestTime(int bestTimeMinutes, int bestTimeSeconds) {
        bestTimeLabel.setText("Best time: " + bestTimeMinutes + "m " + bestTimeSeconds + "s");
    }

}
