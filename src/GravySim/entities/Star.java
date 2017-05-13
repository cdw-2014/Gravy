package GravySim.entities;

import GravySim.Game;
import GravySim.entities.Entity;

import java.awt.*;

/**
 * Created by weavechr000 on 5/12/2017.
 */
public class Star extends Entity {

    public Star(Color color, double x, double y, int width, int height, double mass, double dx, double dy, Game g) {
        super(color, x, y, width, height, mass, dx, dy, g);
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(screenX,screenY,screenW,screenH);
    }

}
