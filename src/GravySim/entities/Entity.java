package GravySim.entities;

import GravySim.Game;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by weavechr000 on 5/12/2017.
 */
public class Entity {
    static double SCALE_CONSTANT = 6.14E5, G = 6.6740831E-11;

    Game game;
    Color color;
    Image img;
    double screenX, screenY, screenW, screenH ,oldX, oldY, orgX, orgY, oldAngle = 0;
    boolean fullOrbit = false;
    double posX,posY,dx,dy,dx2,d2y, w,r;
    //120 ticks per second convert ds to per tick

    double mass, momentum, rad;
    ArrayList<Trail> trails = new ArrayList<>();

    public Entity(Color color, double x, double y, double rad, double mass, double dx, double dy, Game game) {
        this.color = color;
        this.posX = x;
        this.posY = y;
        this.rad = rad;
        this.mass = mass;
        this.dx = dx;
        this.dy = dy;
        this.game = game;
        calcScreenPos();
        orgX = screenX;
        orgY = screenY;
        screenW = (rad*2/game.getScl()/SCALE_CONSTANT);
        screenH = (rad*2/game.getScl()/SCALE_CONSTANT);

        //accelEquation = "A(R) = " + G + " * " +

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
        posX+=dx;
        posY+=dy;
        dx+=dx2;
        dy+=d2y;

        calcScreenPos();
        updateAccel();
//        System.out.println("w= " + w + "\nr= " + r + "\n\n");
        if(trails.size() > 50 && distance(screenX, screenY, orgX, orgY) < rad/(3*SCALE_CONSTANT)) fullOrbit = true;
        
        if(Math.abs(distance(screenX, screenY, oldX, oldY)) > 5) {
            trails.add(new Trail(screenX + screenW / 2, screenY + screenH / 2, 2, Color.CYAN));
            oldX = screenX;
            oldY = screenY;
            if(fullOrbit) trails.remove(0);
        }

    }



    public void calcScreenPos(){
        screenX = ((posX-rad)/game.getScl()/SCALE_CONSTANT + game.getWidth()/2);
        screenY = ((posY-rad)/game.getScl()/SCALE_CONSTANT + game.getHeight()/2);

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
                double rad = distance(posX, posY, e.posX, e.posY);
                r = rad;
                w = (angle-oldAngle)/120;
                oldAngle = angle;
                if( rad > e.rad/2) {
                    double accel = (G * e.mass / Math.pow(rad,2) * 100.0) / 100;
                    //System.out.println(accel);
                    d2y += accel * Math.sin(angle);
                    dx2 += accel * Math.cos(angle);
                    //System.out.println(color + "  " + accel * Math.cos(angle) + "   " + dx + "   " + screenX);
                }
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



    public void paint(Entity other, Graphics g){
        g.setColor(color);
        g.fillOval((int)screenX,(int)screenY,(int)screenW,(int)screenH);
        for(Trail t : trails) {
            t.paint(g);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 16));
        double rad = distance(posX, posY, other.posX, other.posY);
        
        String accelEquation = "a(t) = -w^2(r cos(wt)i + r sin(wt)j)";
        String velEquation = "v(t) = -rw sin(wt)i + rw cos(wt)j";
        String posEquation = "r(t) = r cos(wt)i + r sin(wt)j";
        
        if(game.equations) {
            if(color == Color.BLUE) {
                g.drawString("Blue Mass:", 15, game.getHeight() - g.getFontMetrics().getHeight()*7);

                g.drawString("Acceleration: " + accelEquation + " = " + String.format("%6.3e\n", Math.sqrt(d2y * d2y + dx2 * dx2)), 15,
                    game.getHeight() - g.getFontMetrics().getHeight());

                g.drawString("Velocity: " + velEquation + " = " + String.format("%6.3e\n", Math.sqrt(dy * dy + dx * dx)), 15,
                    game.getHeight() - g.getFontMetrics().getHeight()*3);

                g.drawString("Position: " + posEquation + " = " + String.format("%6.3e\n", r), 15,
                    game.getHeight() - g.getFontMetrics().getHeight()*5);

            } else {
                g.drawString("Yellow Mass:", 15, g.getFontMetrics().getHeight());

                g.drawString("Acceleration: " + accelEquation + " = " + String.format("%6.3e\n", Math.sqrt(d2y * d2y + dx2 * dx2)), 15,
                    g.getFontMetrics().getHeight()*7);

                g.drawString("Velocity: " + velEquation + " = " + String.format("%6.3e\n", Math.sqrt(dy * dy + dx * dx)), 15,
                    g.getFontMetrics().getHeight()*5);

                g.drawString("Position: " + posEquation + " = " + String.format("%6.3e\n", r), 15,
                    g.getFontMetrics().getHeight()*3);
            }
        }
        
        
        
        
        
        

    }

    

}
