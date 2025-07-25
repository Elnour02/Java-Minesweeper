package controller;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

import model.Board;
import model.DataHandler;
import model.Tile;
import model.Mine;
import model.Timer;
import model.User;
import view.MainFrame;

public class GameLogic {

    private MainFrame gui;
    private int numOfMines;
    private Board[][] board;
    private Random random;
    private int clicked;
    private int minutes;
    private int seconds;
    private Timer timer;
    private DataHandler dataHandler;
    private HashMap<String, User> userList;
    private User user;

    public GameLogic() {
        random = new Random();
        gui = new MainFrame(this);
        dataHandler = new DataHandler();
    }

    private void createBoard() {
        board = new Board[16][16];
        numOfMines = 0;
        int row;
        int col;
        boolean valid;
        while (numOfMines < 40) {
            do {
                row = random.nextInt(16);
                col = random.nextInt(16);
                valid = validateMines(row, col);
            } while (!valid);
            board[row][col] = new Mine();
            numOfMines++;
        }
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (!(board[r][c] instanceof Mine)) {
                    board[r][c] = new Tile();
                }
            }
        }
    }

    private boolean validateMines(int row, int col) {
        if (board[row][col] instanceof Mine) {
            return false;
        }
        return true;
    }

    private boolean gameFinished() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] instanceof Tile && !((Tile)board[row][col]).getChecked()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void checkSurrounding(int row, int col) {
        ((Tile)board[row][col]).setChecked(true);
        int mines = 0;
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                if (((r + row) >= 0) && 
                ((r + row) < board.length) && 
                ((c + col) >= 0) &&
                ((c + col) < board[row].length) && 
                ((r != 0) || (c != 0)) && 
                (board[row + r][col + c] instanceof Mine)) {
                    mines++;
                }
            }
        }
        if (mines > 0) {
            gui.revealTile(row, col, String.valueOf(mines));
            if (gameFinished()) {
                timer.interrupt();
                gui.freezeBoard();
                user.increaseGamesPlayed();
                user.increaseGamesWon();
                user.updateWinRatio();
                user.updateBestTime(minutes, seconds);
                saveData();
            }
        } 
        else {
            gui.revealTile(row, col, "");
            for (int r = -1; r <= 1; r++) {
                for (int c = -1; c <= 1; c++) {
                    if (((r + row) >= 0) && 
                    ((r + row) < board.length) && 
                    ((c + col) >= 0) &&
                    ((c + col) < board[row].length) && 
                    ((r != 0) || (c != 0)) && 
                    (((Tile)board[row + r][col + c]).getChecked() == false) &&
                    (board[row + r][col + c].getFlag() == false)) {
                        checkSurrounding(row + r, col + c);
                    }
                }
            }
        }
    }

    private void moveMine() {
        boolean done = false;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] instanceof Tile) {
                    board[row][col] = new Mine();
                    done = true;
                    break;
                }
            }
            if (done) {
                break;
            }
        }
    }

    private void revealMines(int r, int c) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if ((board[row][col] instanceof Mine) && (board[row][col].getFlag() != true) && !((r == row) && (c == col))) {
                    gui.revealMine(row, col, false);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void startGame() {
        restartGame();
        File dataFile = new File("resources", "data/data.txt");
        if (!dataFile.isFile()) userList = new HashMap<>();
        else userList = (HashMap<String, User>)dataHandler.readData();
    }

    public void restartGame() {
        clicked = 0;
        minutes = 0;
        if (timer != null) {
            timer.interrupt();
        }
        gui.updateTime("00:00");
        gui.resetBoard();
        createBoard();
        gui.updateNumOfMines(numOfMines);
    }

    public void saveData() {
        if (userList.containsKey(user.getName())) userList.replace(user.getName(), userList.get(user.getName()), user);
        else userList.put(user.getName(), user);
        dataHandler.writeData(userList);
    }

    public void registerUser(String name, String password) {
        if ((name.length() == 0) || (password.length() == 0)) gui.displayMessageAtStart("Username and/or password still empty");
        else if ((name.indexOf(" ") != -1) || (password.indexOf(" ") != -1)) gui.displayMessageAtStart("Username and/or password cannot contain space");
        else if (userList.containsKey(name)) gui.displayMessageAtStart("Userame is already taken");
        else {
            user = new User(name, password);
            gui.showBoardPanel(0);
            saveData();
        }
    }

    public void login(String name, String password) {
        if ((name.length() == 0) || (password.length() == 0)) gui.displayMessageAtStart("Username and/or password still empty");
        else if ((name.indexOf(" ") != -1) || (password.indexOf(" ") != -1)) gui.displayMessageAtStart("Username and/or password cannot contain space");
        else {
            if (userList.containsKey(name) && userList.get(name).getPassword().equals(password)) {
                user = userList.get(name);
                gui.showBoardPanel(0);
            }
            else gui.displayMessageAtStart("Username and/or password is wrong");
        } 
    }
    
    public void boardInput(int row, int col) {
        if (board[row][col].getFlag() == false) {
            clicked++;
            if (clicked == 1) {
                timer = new Timer(this);
                timer.start();
            }
            if ((board[row][col] instanceof Mine) && (clicked == 1)) {
                moveMine();
                board[row][col] = new Tile();
                checkSurrounding(row, col);
            }
            else if (board[row][col] instanceof Mine) {
                timer.interrupt();
                gui.freezeBoard();
                gui.revealMine(row, col, true);
                revealMines(row, col);
                user.increaseGamesPlayed();
                user.updateWinRatio();
                saveData();
            }
            else {
                checkSurrounding(row, col);
            }
        }
    }
    
    public void toggleFlag(int row, int col) {
        if (gui.getButtonStatus(row, col) == true) {
            if (board[row][col].getFlag() == false) {
                board[row][col].setFlag(true);
                gui.setFlag(row, col);
                numOfMines--;
                gui.updateNumOfMines(numOfMines);
            }
            else {
                board[row][col].setFlag(false);
                gui.removeFlag(row, col);
                numOfMines++;
                gui.updateNumOfMines(numOfMines);
            }
        }
    }

    public void updateTime(int seconds) {
        if ((seconds == 60) && (minutes >= 9)) {
            minutes++;
            gui.updateTime(minutes + ":00");
            this.seconds = 0;
        }
        else if (seconds == 60) {
            minutes++;
            gui.updateTime("0" + minutes + ":00");
            this.seconds = 0;
        }
        else if ((seconds >= 10) && (minutes >= 10)) {
            gui.updateTime(minutes + ":" + seconds);
            this.seconds = seconds;
        }
        else if (seconds >= 10) {
            gui.updateTime("0" + minutes + ":" + seconds);
            this.seconds = seconds;
        }
        else if ((seconds < 10) && (minutes >= 10)) {
            gui.updateTime(minutes + ":0" + seconds);
            this.seconds = seconds;
        }
        else if (seconds < 10) {
            gui.updateTime("0" + minutes + ":0" + seconds);
            this.seconds = seconds;
        }
    }

    public void updateStats() {
        gui.updateGamesPlayed(user.getGamesPlayed());
        gui.updateGamesWon(user.getGamesWon());
        gui.updateWinRatio(user.getWinRatio());
        gui.updateBestTime(user.getBestTimeMinutes(), user.getBestTimeSeconds());
    }
    
}
