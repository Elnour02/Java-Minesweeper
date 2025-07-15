package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class UpperPanel {

    private MainFrame mainFrame;
    private int screenWidth;
    private int screenHeight;
    private int boardWidth;
    private Font customFont;
    private ImageIcon smiley;
    private ImageIcon settings;
    private JPanel upperPanel;
    private JLabel mineLabel;
    private JLabel timeLabel;
    private JButton settingsButton;
    private JButton emptyLeftButton;
    private JButton newGameButton;
    
    public UpperPanel(MainFrame mainFrame, JFrame frame, int screenWidth, int screenHeight, int boardWidth) {
        this.mainFrame = mainFrame;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.boardWidth = boardWidth;
        createFonts();
        createImages();
        createUpperPanel();
        createLabels();
        createButtons();
        upperPanel.add(emptyLeftButton);
        upperPanel.add(mineLabel);
        upperPanel.add(newGameButton);
        upperPanel.add(timeLabel);
        upperPanel.add(settingsButton);
        frame.add(upperPanel, BorderLayout.NORTH);
    }

    private void createFonts() {
        try {
            File fontFile = new File("resources","fonts/digital-7.ttf");
            customFont = Font.createFont(0, fontFile).deriveFont((float)(screenHeight/28.8));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } 
        catch (IOException | FontFormatException e) {
            System.out.println("Font error");
        }
    }

    private void createImages() {
        smiley = new ImageIcon(Paths.get("resources", "images", "smiley.png").toString());
        Image newImage = smiley.getImage().getScaledInstance((int)(screenWidth/64), (int)(screenHeight/36), 4);
        smiley = new ImageIcon(newImage);
        settings = new ImageIcon(Paths.get("resources", "images", "settings.png").toString());
        Image newImage2 = settings.getImage().getScaledInstance((int)(screenWidth/73.142), (int)(screenHeight/41.142), 4);
        settings = new ImageIcon(newImage2);
    }

    private void createUpperPanel() {
        upperPanel = new JPanel();
        upperPanel.setBackground(Color.gray);
        upperPanel.setLayout(new GridLayout());
        upperPanel.setPreferredSize(new Dimension(boardWidth + (int)(screenWidth/170.666)*2, (int)(screenHeight/20.571)));
    }

    private void createLabels() {
        mineLabel = new JLabel();
        mineLabel.setForeground(Color.red);
        mineLabel.setBackground(new Color(33, 33, 33));
        mineLabel.setOpaque(true);
        mineLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel = new JLabel();
        timeLabel.setText("00:00");
        timeLabel.setForeground(Color.red);
        timeLabel.setBackground(new Color(33, 33, 33));
        timeLabel.setOpaque(true);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setFont(customFont);
        mineLabel.setFont(customFont);
    }

    private void createButtons() {
        newGameButton = new JButton(smiley);
        newGameButton.setFocusPainted(false);
        emptyLeftButton = new JButton();
        emptyLeftButton.setBackground(Color.gray);
        emptyLeftButton.setBorder(BorderFactory.createBevelBorder(0));
        emptyLeftButton.setEnabled(false);
        settingsButton = new JButton(settings);
        settingsButton.setFocusPainted(false);
        disableButtons();
    }

    public void updateNumOfMines(int numOfMines) {
        if (numOfMines >= 10) {
            mineLabel.setText("00" + String.valueOf(numOfMines));
        }
        else {
            mineLabel.setText("000" + String.valueOf(numOfMines));
        }
    }

    public void updateTime(String time) {
        timeLabel.setText(time);
    }

    public void disableButtons() {
        newGameButton.setBorder(BorderFactory.createBevelBorder(1));
        for (ActionListener actionListener : newGameButton.getActionListeners()) {
            newGameButton.removeActionListener(actionListener);
        }
        newGameButton.setBackground(new Color(110, 110, 110));
        newGameButton.setModel(new CustomButton());
        settingsButton.setBorder(BorderFactory.createBevelBorder(1));
        for (ActionListener actionListener : settingsButton.getActionListeners()) {
            settingsButton.removeActionListener(actionListener);
        }
        settingsButton.setBackground(new Color(110, 110, 110));
        settingsButton.setModel(new CustomButton());
    }

    public void enableButtons() {
        newGameButton.setBorder(BorderFactory.createBevelBorder(0));
        newGameButton.setBackground(Color.gray);
        newGameButton.setModel(new DefaultButtonModel());
        newGameButton.addActionListener(e -> mainFrame.restartgame());
        settingsButton.setBorder(BorderFactory.createBevelBorder(0));
        settingsButton.setBackground(Color.gray);
        settingsButton.setModel(new DefaultButtonModel());
        settingsButton.addActionListener(e -> mainFrame.showSettingsPanel(1));
    }

}
