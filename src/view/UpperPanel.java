package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class UpperPanel {

    private MainFrame mainFrame;
    private int screenWidth;
    private int screenHeight;
    private Font customFont;
    private ImageIcon smiley;
    private JPanel upperPanel;
    private JLabel mineLabel;
    private JLabel timeLabel;
    private JButton emptyRightButton;
    private JButton emptyLeftButton;
    private JButton newGameButton;
    
    public UpperPanel(MainFrame mainFrame, JFrame frame, int width, int height) {
        this.mainFrame = mainFrame;
        this.screenWidth = width;
        this.screenHeight = height;
        createFonts();
        createImages();
        createUpperPanel();
        createLabels();
        createButtons();
        upperPanel.add(emptyRightButton);
        upperPanel.add(mineLabel);
        upperPanel.add(newGameButton);
        upperPanel.add(timeLabel);
        upperPanel.add(emptyLeftButton);
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
    }

    private void createUpperPanel() {
        upperPanel = new JPanel();
        upperPanel.setBackground(Color.gray);
        upperPanel.setLayout(new GridLayout());
        upperPanel.setPreferredSize(new Dimension((int)(screenWidth/4.266), (int)(screenHeight/20.571)));
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
        newGameButton.setBackground(Color.gray);
        newGameButton.setBorder(BorderFactory.createBevelBorder(0));
        newGameButton.setFocusPainted(false);
        newGameButton.addActionListener(e -> mainFrame.startGame());
        emptyLeftButton = new JButton();
        emptyLeftButton.setBackground(Color.gray);
        emptyLeftButton.setBorder(BorderFactory.createBevelBorder(0));
        emptyLeftButton.setEnabled(false);
        emptyRightButton = new JButton();
        emptyRightButton.setBackground(Color.gray);
        emptyRightButton.setBorder(BorderFactory.createBevelBorder(0));
        emptyRightButton.setEnabled(false);
    }

    public void displayNumOfMines(int numOfMines) {
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

}
