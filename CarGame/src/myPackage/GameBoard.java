package myPackage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameBoard extends JPanel implements KeyListener {

    GameWindow gameWindow;


    Car user = new Car(Settings.LEFT_X, Settings.WINDOW_HEIGHT - Settings.CAR_HEIGHT);
    GaiCar gai=new GaiCar(0, Settings.WINDOW_HEIGHT);
    Car[] otherCars = new Car[Settings.TOTAL_TRAFIC];

    Traffic traffic;

    boolean isCrashed;

    public GameBoard(GameWindow gameWindow) {
        super();

        this.setPreferredSize(new Dimension(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT));

        this.gameWindow = gameWindow;

        for (int i = 0; i < otherCars.length; i++) {
            otherCars[i] = new Car(0, -(Settings.CAR_HEIGHT + Settings.GAP_BETWEEN_CARS) * (i + 1));

            if (Math.random() < 0.5) {
                otherCars[i].moveLeft();
            } else {
                otherCars[i].moveRight();
            }
        }

        this.isCrashed = false;

        traffic = new Traffic(otherCars, user, this);

        traffic.start();


        addKeyListener(this);
        setFocusable(true);
    }

    public void paint(Graphics g) {
        ImageIcon background = new ImageIcon(Settings.BACKGROUN_IMAGE_PATH);
        g.drawImage(background.getImage(), 0, 0, null);
        user.draw(g);

        for (int i = 0; i < otherCars.length; i++) {
            otherCars[i].draw(g);
        }
        if(isCrashed){
            if(user.getX()==Settings.LEFT_X){
                gai.moveRight();

            }
            else{
                gai.moveLeft();
            }
            if(gai.getY()>=Settings.WINDOW_HEIGHT/2){
                int y = gai.getY();

                y = y -1;

                gai.setY(y);


            }
            gai.draw(g);
            this.repaint();

        }
        g.setFont(new Font("default", Font.BOLD, 16));

        g.drawString("Score: " + traffic.getScore()+ " Level: "+traffic.level, 105, 20);
    }


    public void restartGame() {
        this.removeKeyListener(this);
        gameWindow.dispose();
        gameWindow = new GameWindow();
    }


    @Override
    public void keyPressed(KeyEvent event) {

        if (isCrashed) {
            return;
        }

        if (event.getKeyCode() == event.VK_LEFT) {
            user.moveLeft();
        } else if (event.getKeyCode() == event.VK_RIGHT) {
            user.moveRight();
        }
        if (event.getKeyCode() == event.VK_ADD) {
            traffic.koef++;

        }
        if (event.getKeyCode() == event.VK_SUBTRACT) {
            traffic.koef--;

        }
        this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

}
