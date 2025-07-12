package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class SettingsPanel {

    private MainFrame mainFrame;
    private int screenWidth;
    private int screenHeight;
    private JPanel settingsPanel;
    private JPanel centerSettingsPanel;
    private Font customFont;
    private ImageIcon checkmark;
    private JButton userButton;
    private JButton statsButton;
    private JButton backButton;

    public SettingsPanel(MainFrame mainFrame, JPanel centerPanel, int screenWidth, int screenHeight) {
        this.mainFrame = mainFrame;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        createFonts();
        createImages();
        createSettingsPanel();
        createButtons();
        centerSettingsPanel.add(userButton);
        centerSettingsPanel.add(backButton);
        centerSettingsPanel.add(statsButton);
        centerPanel.add(settingsPanel);
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

    private void createSettingsPanel() {
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setBackground(Color.gray);
        centerSettingsPanel = new JPanel();
        centerSettingsPanel.setLayout(null);
        centerSettingsPanel.setPreferredSize(new Dimension(mainFrame.getBoardWidth(), mainFrame.getBoardHeight())); 
        settingsPanel.setPreferredSize(new Dimension(mainFrame.getBoardWidth() + (int)(screenWidth/128)*2, mainFrame.getBoardHeight() + (int)(screenHeight/72)*2));
        centerSettingsPanel.setBackground(Color.gray);
        settingsPanel.add(centerSettingsPanel, BorderLayout.CENTER);
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
                settingsPanel.add(sidePanel, BorderLayout.WEST);
            }
            else if (i == 1) {
                sidePanel.setPreferredSize(new Dimension((int)(screenWidth/4.266), (int)(screenHeight/72)));
                settingsPanel.add(sidePanel, BorderLayout.SOUTH);
            }
            else if (i == 2) {
                sidePanel.setPreferredSize(new Dimension((int)(screenWidth/4.266), (int)(screenHeight/72)));
                settingsPanel.add(sidePanel, BorderLayout.NORTH);
            }
            else {
                sidePanel.setPreferredSize(new Dimension((int)(screenWidth/128), (int)(screenHeight/2.215)));
                settingsPanel.add(sidePanel, BorderLayout.EAST);
            }
            sidePanel.add(button);
        }
    }

    private void createButtons() {
        userButton = new JButton("User");
        userButton.setBackground(Color.gray);
        userButton.setBorder(BorderFactory.createBevelBorder(0));
        userButton.setFont(customFont);
        userButton.setForeground(Color.black);
        userButton.setFocusPainted(false);
        userButton.setBounds((int)(screenWidth/34.133), (int)(screenHeight/72), mainFrame.getBoardWidth() - (int)(screenWidth/17.066), (int)(screenHeight/14.4));
        statsButton = new JButton("Stats");
        statsButton.setBackground(Color.gray);
        statsButton.setBorder(BorderFactory.createBevelBorder(0));
        statsButton.setFont(customFont);
        statsButton.setForeground(Color.black);
        statsButton.setFocusPainted(false);
        statsButton.setBounds((int)(screenWidth/34.133), (int)(screenHeight/10.285), mainFrame.getBoardWidth() - (int)(screenWidth/17.066), (int)(screenHeight/14.4));
        statsButton.addActionListener(e -> mainFrame.showStatsPanel());
        backButton = new JButton(checkmark);
        backButton.setBackground(Color.gray);
        backButton.setBorder(BorderFactory.createBevelBorder(0));
        backButton.setFocusPainted(false);
        backButton.setBounds(mainFrame.getBoardWidth()/2 - ((int)(screenWidth/17.066))/2, (int)(screenHeight/2.823), (int)(screenWidth/17.066), (int)(screenHeight/19.2));
        backButton.addActionListener(e -> mainFrame.showBoardPanel());
    }
    
}
