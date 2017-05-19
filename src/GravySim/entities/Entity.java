package GravySim.entities;

import GravySim.Game;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by weavechr000 on 5/12/2017.
 */
public class Entity {

    public static double SCALE_CONSTANT = 300;

    Game game;
    Color color;
    Image img;
    int screenX, screenY, screenW, screenH;

    long posX,posY,dx,dy,dx2,d2y;
    //120 ticks per second convert ds to per tick

    double mass, momentum, width, height;
    ArrayList<Point> forces = new ArrayList<Point>();
    ArrayList<Trail> trails = new ArrayList<>();

    public Entity(Color color, long x, long y, double width, double height, double mass, long dx, long dy, Game game) {
        this.color = color;
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.mass = mass;
        this.dx = dx;
        this.dy = dy;
        this.game = game;
        calcScreenPos();
        screenW = (int)(width/(SCALE_CONSTANT/game.getScl()));
        screenH = (int)(height/(SCALE_CONSTANT/game.getScl()));



    }

    /*public Entity(Image img, double x, double y, int width, int height, double mass, double dx, double dy, Game game) {
        this.img = img;
        pos.setLocation(x,y);
        this.width = width;
        this.height = height;
        this.mass = mass;
        vel.setLocation(dx,dy);
        this.game = game;
        calcScreenPos();
        screenW = (int)(width/(500.0/game.getScl()));
        screenH = (int)(height/(500.0/game.getScl()));

    }*/

//    public void calculateMomentum(){
//        momentum = mass* Point.distance(vel.getX(), 0,0,vel.getY());
//    }

    public double getMomentum(){
        return momentum;
    }

    public void tick(){
        posX+=dx*10;
        posY+=dy*10;
        dx+=dx2*10;
        dy+=d2y*10;
        calcScreenPos();
        updateAccel();
        trails.add(new Trail(screenX+screenW/2, screenY+screenH/2, 2, Color.CYAN));

    }

    public void addForce(Point force){
        forces.add(force);
    }



    public void calcScreenPos(){
        screenX = (int)((posX-width/2)/(SCALE_CONSTANT/game.getScl())+game.getWidth()/2);
        screenY = (int)((posY-height/2)/(SCALE_CONSTANT/game.getScl())+game.getHeight()/2);
    }

    public static Point calcForce(Entity a, Entity b){
        double G = 6.6740831E-11;
        double angle = calcAngle(a, b);
        double force = G*a.mass*b.mass/Math.pow(distance(a.posX+a.width/2, a.posY+a.height/2, b.posX+b.width/2, b.posY+b.height/2),2);
//        double force = G*a.mass*b.mass/Point.distance(a.pos.getX(),a.pos.getY(), b.pos.getX(), b.pos.getY());
        Point forceComp = new Point();
        forceComp.setLocation(force*Math.cos(angle), force*Math.sin(angle));
        return forceComp;
    }

    public static double distance(double ax, double ay, double bx, double by) {

        return Math.sqrt(Math.pow(ax - bx, 2) + Math.pow(ay- by, 2));

    }

    public void updateAccel() {
        ArrayList<Point> forces = new ArrayList<>();
        for(Entity e : game.getEnts())
            if(this != e) forces.add(calcForce(this, e));
         d2y = 0;
         dx2 = 0;
        for(Point p : forces) {
            d2y += p.getY()/mass;
            dx2 += p.getX()/mass;
        }
        System.out.println(dx2);
        System.out.println(d2y);



    }


    public static double calcAngle(Entity a, Entity b){
        return Math.atan2(b.posY - a.posY, b.posX - a.posX);
    }



    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval(screenX,screenY,screenW,screenH);
        for(Trail t : trails) {
            t.paint(g);
        }

    }
}
