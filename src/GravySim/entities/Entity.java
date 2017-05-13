package GravySim.entities;

import GravySim.Game;

import java.awt.*;

/**
 * Created by weavechr000 on 5/12/2017.
 */
public abstract class Entity {

    Game game;
    Color color;
    Image img;
    int screenX, screenY, screenW, screenH;

    //120 ticks per second convert ds to per tick
    double mass, momentum, x, y, width, height, dx, dy;

    public Entity(Color color, double x, double y, double width, double height, double mass, double dx, double dy, Game game) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mass = mass;
        this.dx = dx;
        this.dy = dy;
        this.game = game;
        System.out.print(game.getWidth());
        screenX = (int)(x/500+game.getWidth()/2);
        screenY = (int)(y/500+game.getHeight()/2);
        screenW = (int)(width/500);
        screenH = (int)(height/500);

    }

    public Entity(Image img, double x, double y, int width, int height, double mass, double dx, double dy, Game game) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mass = mass;
        this.dx = dx;
        this.dy = dy;
        this.game = game;
    }

    public void getMomentum(){

    }

    public abstract void paint(Graphics g);
}
