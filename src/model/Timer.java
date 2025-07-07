package model;

import controller.GameLogic;

public class Timer extends Thread {

    private GameLogic gameLogic;
    private int seconds;

    public Timer(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        seconds = 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                seconds = (seconds % 60) + 1;
                Thread.sleep(1000);
                gameLogic.updateTime(seconds);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
