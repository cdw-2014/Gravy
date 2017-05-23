package GravySim.entities;

import GravySim.Game;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by weavechr000 on 5/12/2017.
 */
public class Entity {
    static double SCALE_CONSTANT = 3E5, G = 6.6740831E-11;
    int TIME_CONSTANT = 5;


    Game game;
    Color color;
    Image img;
    int screenX, screenY, screenW, screenH;

    double posX,posY,dx,dy,dx2,d2y;
    //120 ticks per second convert ds to per tick

    double mass, momentum, rad;
    ArrayList<Trail> trails = new ArrayList<>();

    public Entity(Color color, double x, double y, double rad, double mass, long dx, double dy, Game game) {
        this.color = color;
        this.posX = x;
        this.posY = y;
        this.rad = rad;

        this.mass = mass;
        this.dx = dx;
        this.dy = dy;
        this.game = game;
        calcScreenPos();
        screenW = (int)(rad*2/game.getScl()/SCALE_CONSTANT);
        screenH = (int)(rad*2/game.getScl()/SCALE_CONSTANT);



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
        posX+=dx*TIME_CONSTANT;
        posY+=dy*TIME_CONSTANT;
        dx+=dx2*TIME_CONSTANT;
        dy+=d2y*TIME_CONSTANT;
        calcScreenPos();
        updateAccel();
        trails.add(new Trail(screenX+screenW/2, screenY+screenH/2, 2, Color.CYAN));

    }



    public void calcScreenPos(){
        screenX = (int)((posX-rad)/game.getScl()/SCALE_CONSTANT + game.getWidth()/2);
        screenY = (int)((posY-rad)/game.getScl()/SCALE_CONSTANT + game.getHeight()/2);

    }

//    public static double calcForce(Entity a, Entity b){
//
//        double force = G*a.mass*b.mass/Math.pow(distance(a.posX+a.rad, a.posY+a.rad, b.posX+b.rad, b.posY+b.rad),2);
//        return force;
//    }

    public static double distance(double ax, double ay, double bx, double by) {

        return Math.sqrt(Math.pow(bx-ax, 2) + Math.pow(by-ay, 2));

    }

    public void updateAccel() {
    //    ArrayList<Double> xforces = new ArrayList<>();
    //    ArrayList<Double> yforces = new ArrayList<>();
        d2y = 0;
        dx2 = 0;
        for(Entity e : game.getEnts()) {
            if (this != e) {
                double angle = calcAngle(this, e);
//                System.out.println(angle);
                double accel = G*e.mass/Math.pow(distance(posX, posY, e.posX, e.posY),2);
                d2y+=accel*Math.sin(angle);
                dx2+=accel*Math.cos(angle);
                System.out.println(color + "  " + accel*Math.cos(angle) + "   " + dx + "   " + screenX);
            }
        }


//        for(Double d : xforces) {
//            dx2+=d/mass;
//        }
//        for(Double d : yforces) {
//            d2y += d / mass;
//        }
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
