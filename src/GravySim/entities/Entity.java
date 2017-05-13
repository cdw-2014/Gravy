package GravySim.entities;

import GravySim.Game;

import java.awt.*;
import java.util.ArrayList;

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
        this.x = x-width/2;
        this.y = y-height/2;
        this.width = width;
        this.height = height;
        this.mass = mass;
        this.dx = dx;
        this.dy = dy;
        this.game = game;
        calcScreenPos(x,y);
        screenW = (int)(width/(500.0/game.getScl()));
        screenH = (int)(height/(500.0/game.getScl()));

        calculateMomentum();

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
        calcScreenPos(x,y);
        screenW = (int)(width/(500.0/game.getScl()));
        screenH = (int)(height/(500.0/game.getScl()));

    }

    public void calculateMomentum(){
        double vel =Math.sqrt(dx*dx+dy*dy);
        momentum = mass*vel;
    }

    public double getMomentum(){
        return momentum;
    }

    public void tick(ArrayList<Entity> ents){
        x+=dx;
        y+=dy;
        calcScreenPos(x,y);
        calcForce();
    }

    public Point calcScreenPos(double x, double y){
        screenX = (int)((x-width/2)/(500.0/game.getScl())+game.getWidth()/2);
        screenY = (int)((y-width/2)/(500.0/game.getScl())+game.getHeight()/2);
        return new Point(screenX,screenY);
    }

    public static void calcForce(Entity a, Entity b){
        long G = (long)6.6740831* (long)Math.pow(10,-11);
        double force = G*a.mass*b.mass/calcDist(a,b);

    }

    public static double calcDist(Entity a, Entity b){
        double x = Math.abs(a.x-b.x);
        double y = Math.abs(a.y-b.y);
        return Math.sqrt(x*x-y*y);
    }


    public abstract void paint(Graphics g);
}
