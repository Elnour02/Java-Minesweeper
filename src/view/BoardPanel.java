package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class BoardPanel {
    
    private MainFrame mainFrame;
    private Font customFont;
    private ImageIcon mine;
    private ImageIcon flag;
    private JPanel boardPanel;
    private JPanel centerBoardPanel;
    private JButton[][] buttons;

    public BoardPanel(MainFrame mainFrame, JFrame frame) {
        this.mainFrame = mainFrame;
        createFonts();
        createImages();
        createBoardPanel();
        createBoard();
        frame.add(boardPanel, BorderLayout.CENTER);
    }

    private void createFonts() {
        try {
            File fontFile = new File(new File("resources", "fonts/mine-sweeper.ttf").getPath());
            customFont = Font.createFont(0, fontFile).deriveFont(22f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } 
        catch (IOException | FontFormatException e) {
            System.out.println("Font error");
        }
    }

    private void createImages() {
        mine = new ImageIcon(Paths.get("resources", "images", "mine.png").toString());
        Image newImage = mine.getImage().getScaledInstance(32, 32, 4);
        mine = new ImageIcon(newImage);
        flag = new ImageIcon(Paths.get("resources", "images", "flag.png").toString());
        Image newImage2 = flag.getImage().getScaledInstance(30, 30, 4);
        flag = new ImageIcon(newImage2);
    }

    private void createBoardPanel() {
        boardPanel = new JPanel();
        boardPanel.setLayout(new BorderLayout());
        boardPanel.setPreferredSize(new Dimension(600, 633));
        boardPanel.setBackground(Color.gray);
        centerBoardPanel = new JPanel();
        centerBoardPanel.setLayout(new GridLayout(16, 16));
        centerBoardPanel.setPreferredSize(new Dimension(570, 620));
        centerBoardPanel.setBackground(Color.gray);
        boardPanel.add(centerBoardPanel, BorderLayout.CENTER);
        for (int i = 0; i < 4; i++) {
            JPanel sidePanel = new JPanel();
            sidePanel.setLayout(new GridLayout());
            sidePanel.setBackground(Color.gray);
            JButton button = new JButton();
            button.setBackground(Color.gray);
            button.setBorder(BorderFactory.createBevelBorder(0));
            button.setEnabled(false);
            if (i == 0) {
                sidePanel.setPreferredSize(new Dimension(20, 640));
                boardPanel.add(sidePanel, BorderLayout.WEST);
            }
            else if (i == 1) {
                sidePanel.setPreferredSize(new Dimension(600, 20));
                boardPanel.add(sidePanel, BorderLayout.SOUTH);
            }
            else if (i == 2) {
                sidePanel.setPreferredSize(new Dimension(600, 20));
                boardPanel.add(sidePanel, BorderLayout.NORTH);
            }
            else {
                sidePanel.setPreferredSize(new Dimension(20, 640));
                boardPanel.add(sidePanel, BorderLayout.EAST);
            }
            sidePanel.add(button);
        }
    }

    private void createBoard() {
        buttons = new JButton[16][16];
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                JButton button = new JButton();
                button.setBackground(Color.gray);
                button.setBorder(BorderFactory.createBevelBorder(0));
                button.setFocusPainted(false);
                buttons[i][j] = button;
                centerBoardPanel.add(button);
                int row = i;
                int col = j;
                button.addActionListener(e -> mainFrame.boardInput(row, col));
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            mainFrame.validateFlag(row, col);
                        }
                    }
                });
            }
        }
    }

    private void setTileForeground(int row, int col, int type) {
        switch (type) {
            case 1:
                buttons[row][col].setForeground(new Color(52, 158, 235));
                break;
            case 2:
                buttons[row][col].setForeground(new Color(50, 217, 111));
                break;
            case 3:
                buttons[row][col].setForeground(new Color(245, 73, 102));
                break;
            case 4:
                buttons[row][col].setForeground(new Color(218, 36, 224));
                break;
            case 5:
                buttons[row][col].setForeground(new Color(255, 184, 61));
                break;
            case 6:
                buttons[row][col].setForeground(new Color(41, 188, 204));
                break;
            case 7:
                buttons[row][col].setForeground(new Color(64, 255, 194));
                break;
            case 8:
                buttons[row][col].setForeground(new Color(222, 13, 13));
        }
    }

    public void revealTile(int row, int col, String appearance) {
        buttons[row][col].setFont(customFont);
        buttons[row][col].setText(appearance);
        if (!appearance.equals("")) {
            setTileForeground(row, col, Integer.valueOf(appearance));
        }
        buttons[row][col].setBackground(new Color(87, 87, 87));
        buttons[row][col].setIcon(null);
        buttons[row][col].setBorder(BorderFactory.createBevelBorder(1));
        buttons[row][col].setModel(new CustomButton());
    }

    public void revealMine(int row, int col, boolean clickedOn) {
        if (clickedOn) {
            buttons[row][col].setBackground(Color.red);
        }
        else {
            buttons[row][col].setBackground(new Color(87, 87, 87));
        }
        buttons[row][col].setIcon(mine);
        buttons[row][col].setBorder(BorderFactory.createBevelBorder(1));
        buttons[row][col].setModel(new CustomButton());
    }

    public void setFlag(int row, int col) {
        buttons[row][col].setIcon(flag);
        buttons[row][col].setContentAreaFilled(false);
    }

    public void removeFlag(int row, int col) {
        buttons[row][col].setIcon(null);
        buttons[row][col].setContentAreaFilled(true);
    }

    public void resetBoard() {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                buttons[row][col].setBackground(Color.gray);
                buttons[row][col].setText(null);
                buttons[row][col].setIcon(null);;
                buttons[row][col].setBorder(BorderFactory.createBevelBorder(0));
                buttons[row][col].setModel(new DefaultButtonModel());
                buttons[row][col].setContentAreaFilled(true);
                for (ActionListener actionListener : buttons[row][col].getActionListeners()) {
                    buttons[row][col].removeActionListener(actionListener);
                }
                int i = row;
                int j = col;
                buttons[row][col].addActionListener(e -> mainFrame.boardInput(i, j));
            }
        }
    }

    public void freezeBoard() {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                for (ActionListener actionListener : buttons[row][col].getActionListeners()) {
                    buttons[row][col].removeActionListener(actionListener);
                }
                buttons[row][col].removeActionListener(null);
                buttons[row][col].setModel(new CustomButton());
            }
        }
    }
    
    public boolean getButtonStatus(int row, int col) {
        if (!(buttons[row][col].getModel() instanceof CustomButton)) {
            return true;
        }
        return false;
    }

}
