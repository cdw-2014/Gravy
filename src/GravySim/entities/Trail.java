package GravySim.entities;

import java.awt.*;

/**
 * Created by weavechr000 on 5/18/2017.
 */
public class Trail {

    double x, y, radius;
    Color color;

    public Trail(double x, double y, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void paint(Graphics g) {

        g.setColor(color);
        g.fillOval((int)x, (int)y, (int)radius, (int)radius);

    }
}
