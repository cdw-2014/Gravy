package GravySim.entities;

import GravySim.Game;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by weavechr000 on 5/12/2017.
 */
public class Entity {

    Game game;
    Color color;
    Image img;
    int screenX, screenY, screenW, screenH;
    //120 ticks per second convert ds to per tick
    Point pos = new Point(0,0);
    Point vel = new Point(0,0);
    Point accel = new Point(0,0);

    double mass, momentum, width, height;
    ArrayList<Point> forces = new ArrayList<Point>();

    public Entity(Color color, double x, double y, double width, double height, double mass, double dx, double dy, Game game) {
        this.color = color;
        pos.setLocation(x,y);
        this.width = width;
        this.height = height;
        this.mass = mass;
        vel.setLocation(dx,dy);
        this.game = game;
        calcScreenPos(x,y);
        screenW = (int)(width/(500.0/game.getScl()));
        screenH = (int)(height/(500.0/game.getScl()));

        calculateMomentum();

    }

    public Entity(Image img, double x, double y, int width, int height, double mass, double dx, double dy, Game game) {
        this.img = img;
        pos.setLocation(x,y);
        this.width = width;
        this.height = height;
        this.mass = mass;
        vel.setLocation(dx,dy);
        this.game = game;
        calcScreenPos(x,y);
        screenW = (int)(width/(500.0/game.getScl()));
        screenH = (int)(height/(500.0/game.getScl()));

    }

    public void calculateMomentum(){
        momentum = mass* Point.distance(vel.getX(), 0,0,vel.getY());
    }

    public double getMomentum(){
        return momentum;
    }

    public void tick(){
        pos.setLocation(pos.getX()+vel.getX(), pos.getY()+vel.getY());
        vel.setLocation(vel.getX()+accel.getX(), vel.getY()+accel.getY());
    }

    public void addForce(Point force){
        forces.add(force);
    }



    public Point calcScreenPos(double x, double y){
        screenX = (int)((x-width/2)/(500.0/game.getScl())+game.getWidth()/2);
        screenY = (int)((y-width/2)/(500.0/game.getScl())+game.getHeight()/2);
        return new Point(screenX,screenY);
    }

    public static Point calcForce(Entity a, Entity b){
        long G = (long)6.6740831* (long)Math.pow(10,-11);
        double angle = calcAngle(a, b);
        double force = G*a.mass*b.mass/Point.distance(a.pos.getX(),a.pos.getY(), b.pos.getX(), b.pos.getY());
        Point forceComp = new Point();
        forceComp.setLocation(force*Math.cos(angle), force*Math.sin(angle));
        return forceComp;
    }

    public static double calcAngle(Entity a, Entity b){
        return Math.atan2(a.pos.getY()-b.pos.getY(), a.pos.getX()-b.pos.getX());
    }



    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval(screenX,screenY,screenW,screenH);

    }
}
