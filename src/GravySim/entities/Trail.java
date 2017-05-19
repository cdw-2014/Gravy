package GravySim.entities;

import java.awt.*;

/**
 * Created by weavechr000 on 5/18/2017.
 */
public class Trail {

    int x, y, radius;
    Color color;

    public Trail(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public void paint(Graphics g) {

        g.setColor(color);
        g.fillOval(x, y, radius, radius);

    }
}
