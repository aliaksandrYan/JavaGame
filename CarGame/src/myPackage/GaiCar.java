package myPackage;


import javax.swing.*;
import java.awt.*;

public class GaiCar extends Car{
    private String imagePath = "assets/images/retroGaiCar.png";
    public GaiCar(int posX, int posY) {
        super(posX,posY);

    }
    public void draw(Graphics g) {
        ImageIcon img = new ImageIcon(imagePath);
        g.drawImage(img.getImage(), this.getX(), this.getY(), null);
    }
}


