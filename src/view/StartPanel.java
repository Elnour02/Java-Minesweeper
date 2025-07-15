package view;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class StartPanel {

    private MainFrame mainFrame;
    private int screenWidth;
    private int screenHeight;
    private int boardWidth;
    private int boardHeight;
    private Font customFontB;
    private Font customFontM;
    private Font customFontS;
    private JPanel startPanel;
    private JPanel centerStartPanel;
    private JLabel titleLabel;
    private JLabel messageLabel;
    private JLabel nameLabel;
    private JLabel passwordLabel;
    private JTextField nameField;
    private JTextField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public StartPanel(MainFrame mainFrame, JPanel centerPanel, int screenWidth, int screenHeight, int boardWidth, int boardHeight) {
        this.mainFrame = mainFrame;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        createFonts();
        createStartPanel();
        createLabels();
        createTextFields();
        createButtons();
        centerStartPanel.add(titleLabel);
        centerStartPanel.add(messageLabel);
        centerStartPanel.add(nameLabel);
        centerStartPanel.add(passwordLabel);
        centerStartPanel.add(nameField);
        centerStartPanel.add(passwordField);
        centerStartPanel.add(loginButton);
        centerStartPanel.add(registerButton);
        centerPanel.add(startPanel);
    }
    
    private void createFonts() {
        try {
            File fontFile = new File("resources", "fonts/mine-sweeper.ttf");
            customFontB = Font.createFont(0, fontFile).deriveFont((float)(screenHeight/72));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFontB);
            customFontM = Font.createFont(0, fontFile).deriveFont((float)(screenHeight/120));
            ge.registerFont(customFontM);
            customFontS = Font.createFont(0, fontFile).deriveFont((float)(screenHeight/160));
            ge.registerFont(customFontS);

        } 
        catch (IOException | FontFormatException e) {
            System.out.println("Font error");
        }
    }

    private void createStartPanel() {
        startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());
        startPanel.setBackground(Color.gray);
        centerStartPanel = new JPanel();
        centerStartPanel.setLayout(null);
        centerStartPanel.setPreferredSize(new Dimension(boardWidth, boardHeight)); 
        startPanel.setPreferredSize(new Dimension(boardWidth + (int)(screenWidth/128)*2, boardHeight + (int)(screenHeight/72)*2));
        centerStartPanel.setBackground(Color.gray);
        startPanel.add(centerStartPanel, BorderLayout.CENTER);
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
                startPanel.add(sidePanel, BorderLayout.WEST);
            }
            else if (i == 1) {
                sidePanel.setPreferredSize(new Dimension((int)(screenWidth/4.266), (int)(screenHeight/72)));
                startPanel.add(sidePanel, BorderLayout.SOUTH);
            }
            else if (i == 2) {
                sidePanel.setPreferredSize(new Dimension((int)(screenWidth/4.266), (int)(screenHeight/72)));
                startPanel.add(sidePanel, BorderLayout.NORTH);
            }
            else {
                sidePanel.setPreferredSize(new Dimension((int)(screenWidth/128), (int)(screenHeight/2.215)));
                startPanel.add(sidePanel, BorderLayout.EAST);
            }
            sidePanel.add(button);
        }
    }

    private void createLabels() {
        titleLabel = new JLabel("Log in or register");
        titleLabel.setForeground(Color.black);
        titleLabel.setBounds((int)(screenWidth/128), (int)(screenHeight/14.4), boardWidth - (int)(screenWidth/64), (int)(screenHeight/14.4));
        titleLabel.setFont(customFontB);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel = new JLabel();
        messageLabel.setForeground(new Color(201, 4, 4));
        messageLabel.setBounds((int)(screenWidth/128), (int)(screenHeight/6.4), boardWidth - (int)(screenWidth/64), (int)(screenHeight/57.6));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setFont(customFontS);
        nameLabel = new JLabel("Username:");
        nameLabel.setForeground(Color.black);
        nameLabel.setBounds((int)(screenWidth/128), (int)(screenHeight/5.538), (int)(screenWidth/25.6), (int)(screenHeight/57.6));
        nameLabel.setFont(customFontS);
        passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.black);
        passwordLabel.setBounds((int)(screenWidth/128), (int)(screenHeight/4.8), (int)(screenWidth/25.6), (int)(screenHeight/57.6));
        passwordLabel.setFont(customFontS);
    }

    private void createTextFields() {
        nameField = new JTextField();
        nameField.setBounds((int)(screenWidth/21.333), (int)(screenHeight/5.538), boardWidth - (int)(screenWidth/10.666), (int)(screenHeight/57.6));
        passwordField = new JTextField();
        passwordField.setBounds((int)(screenWidth/21.333), (int)(screenHeight/4.8), boardWidth - (int)(screenWidth/10.666), (int)(screenHeight/57.6));
    }

    private void createButtons() {
        loginButton = new JButton("Log in");
        loginButton.setBackground(Color.gray);
        loginButton.setBorder(BorderFactory.createBevelBorder(0));
        loginButton.setFocusPainted(false);
        loginButton.setBounds((int)(screenWidth/51.2), (int)(screenHeight/3.84), (int)(screenWidth/10.24), (int)(screenHeight/28.8));
        loginButton.setFont(customFontM);
        loginButton.setForeground(Color.black);
        loginButton.addActionListener(e -> mainFrame.login(nameField.getText(), passwordField.getText()));
        registerButton = new JButton("Register");
        registerButton.setBackground(Color.gray);
        registerButton.setBorder(BorderFactory.createBevelBorder(0));
        registerButton.setFocusPainted(false);
        registerButton.setBounds((int)(screenWidth/8), (int)(screenHeight/3.84), (int)(screenWidth/10.24), (int)(screenHeight/28.8));
        registerButton.setFont(customFontM);
        registerButton.setForeground(Color.black);
        registerButton.addActionListener(e -> mainFrame.registerUser(nameField.getText(), passwordField.getText()));
    }

    public void displayMessage(String message) {
        messageLabel.setText(message);
    }
    
}
