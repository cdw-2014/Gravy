package GravySim.entities;

import GravySim.Game;

import java.awt.*;

/**
 * Created by weavechr000 on 5/12/2017.
 */
public class Planet extends Entity {

    public Planet(Color color, double x, double y, double width, double height, double mass, double dx, double dy, Game g) {
        super(color, x, y, width, height, mass, dx, dy, g);
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(screenX, screenY, screenW, screenH);
    }
}
