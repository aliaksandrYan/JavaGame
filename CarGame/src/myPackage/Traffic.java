package myPackage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

class RemindTask extends TimerTask {
    Sound winGameSound = new Sound(new File("win_game.wav"));
    public void run() {

        winGameSound.play();
       // System.exit(0);
       //подаём звуковой сигнал
        //если таймер нужно остановить, то:
        //timer.cancel(); или
        //System.exit(0); завершает поток, в котором идёт выполнение
    }
}
public class Traffic extends Thread {

    Car[] cars;
    Car user;
    GameBoard board;
    int level=1;
    int points = 1;
    int sleepTime=6;
    int koef=0;
    Sound loseGameSound = new Sound(new File("lose_game.aif"));

    Sound blockHitSound = new Sound(new File("block_hit.aif"));
    public Traffic(Car[] cars, Car user, GameBoard board) {
        this.cars = cars;
        this.user = user;
        this.board = board;

    }

    @Override
    public void run() {
        while (true) {
         //   Timer timer;
         //   timer = new Timer();
         //   timer.schedule(new RemindTask(), 3000, 15000);
            for (int i = 0; i < cars.length; i++) {
                if (cars[i].getY() > Settings.WINDOW_HEIGHT) {
                    points++;
                    if(points%10==0 ){
                        level++;
                    }
                    int y = cars[i].getY();

                    y = y - Settings.TOTAL_TRAFIC * (Settings.CAR_HEIGHT + Settings.GAP_BETWEEN_CARS);

                    cars[i].setY(y);


                    if (Math.random() <= 0.5) {
                        cars[i].moveLeft();
                    } else {
                        cars[i].moveRight();
                    }
                } else {
                    cars[i].setY(cars[i].getY() + 1);
                }
            }

            board.repaint();
           // timer.schedule();
            if (checkCollision()) {
                board.isCrashed = true;
                board.repaint();
                blockHitSound.play();
                JOptionPane.showMessageDialog(null, "Game Over. Your score is " + points);
                loseGameSound.play();
                board.restartGame();
                break;
            }

            try {
                 sleepTime = 6 - (points / 10+koef);
                if (sleepTime <= 0) {
                    sleepTime = 1;
                }
               Thread.sleep(sleepTime);
                //Thread.sleep(2);
            } catch (Exception ex) {

            }
        }
    }


    public int getScore() {
        return this.points;
    }

    public boolean checkCollision() {

        for (int i = 0; i < cars.length; i++) {
            Car other = cars[i];

            if (other.getX() == user.getX()) {
                if (other.getY() > user.getY()) {
                    int d = other.getY() - user.getY();

                    if (d < (Settings.CAR_HEIGHT - 20)) {
                        return true;
                    }
                } else {

                    int d = user.getY() - other.getY();

                    if (d < (Settings.CAR_HEIGHT - 25)) {
                        return true;
                    }
                }
            }
        }


        return false;

    }

}
