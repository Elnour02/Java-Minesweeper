package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UpperPanel {

    private MainFrame mainFrame;
    private JPanel upperPanel;
    private JLabel mineLabel;
    private JLabel timeLabel;
    private JButton emptyRightButton;
    private JButton emptyLeftButton;
    private JButton newGameButton;
    
    public UpperPanel(MainFrame mainFrame, JFrame frame) {
        this.mainFrame = mainFrame;
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

    private void createUpperPanel() {
        upperPanel = new JPanel();
        upperPanel.setBackground(Color.gray);
        upperPanel.setLayout(new GridLayout());
        upperPanel.setPreferredSize(new Dimension(600, 50));
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
        try {
            File fontFile = new File("resources\\fonts\\digital-7.ttf");
            Font customFont = Font.createFont(0, fontFile).deriveFont(55f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            timeLabel.setFont(customFont);
            mineLabel.setFont(customFont);;
        } 
        catch (IOException | FontFormatException e) {}
    }

    private void createButtons() {
        ImageIcon smiley = new ImageIcon("resources\\images\\smiley.png");
        Image newImage = smiley.getImage().getScaledInstance(35, 35, 4);
        smiley = new ImageIcon(newImage);
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
        mineLabel.setText("00" + String.valueOf(numOfMines));
    }

    public void updateTime(String time) {
        timeLabel.setText(time);
    }

}
