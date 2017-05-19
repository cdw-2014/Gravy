package GravySim;

import GravySim.entities.Entity;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by weavechr000 on 5/12/2017.
 */
public class Game extends JPanel {
    public static Dimension windowSize = new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/10*9,
            (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/10*9);


    boolean running = false, fullscreen = false;

    int frameRate = 0, scl = 5;
    Input input = new Input();
    JFrame frame;

    ArrayList<Entity> ents = new ArrayList<Entity>();


    public Game() {

        frame = new JFrame("Gravy");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
        setPreferredSize(windowSize);
        frame.setLocationRelativeTo(this);
        frame.add(this);
        setVisible(true);
        frame.addKeyListener(input);
        frame.pack();
    }

    public void init(){
        ents.add(new Entity(Color.YELLOW, 0, 0, 4E3, 4E3, 2E16, 0,0, this));
        ents.add(new Entity(Color.BLUE, 2E4,0,2E3,2E3, 2E9, -30,-190, this));
    }

    public void run(){
        init();

        long lastTime = System.nanoTime();
        long time = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double deltau = 0;
        double deltaf = 0;
        double deltat = 0;

        int frames = 0;
        running = true;
        while(running){
            long now = System.nanoTime();
            deltat += (now-lastTime) / ns;
            deltaf += (now-lastTime) / ns;
            lastTime = now;
            while (deltat >= 1){ //120 ps
                tick();
                deltat-=1;
            }

            while (deltaf >= 1) { //120 ps
                repaint();
                frames++;
                deltaf-=1;
            }

            if (System.currentTimeMillis() - time > 1000){
                frameRate = frames;
                time += 1000;
                frames = 0;
            }


        }
    }

    public void tick() {
        checkInput();
        for(Entity e: ents){
            e.tick();
        }



    }


    public void paint(Graphics g){
        super.paint(g);
        for (Entity e: ents){
            e.paint(g);
        }
    }

    public void checkInput(){
        if (input.keyTyped[KeyEvent.VK_F1]){
            fullScreen();
            input.keyTyped[KeyEvent.VK_F1] = false;
        }
    }

    public void fullScreen(){
        if(!fullscreen){
            frame.dispose();
            frame.setResizable(false);
            frame.setUndecorated(true);
            frame.setLocation(0,0);
            frame.setSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                    (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
            frame.setVisible(true);
            fullscreen = true;
            return;
        }
        else{
            frame.dispose();
            frame.setResizable(true);
            frame.setUndecorated(false);
            frame.setLocationRelativeTo(this);
            frame.setSize(windowSize);
            frame.setVisible(true);
            fullscreen = false;
            return;


        }
    }

    public static  void main(String[] args) {

        Game game = new Game();

        game.run();
    }

    public int getScl() {
        return scl;
    }

    public ArrayList<Entity> getEnts() {
        return ents;
    }
}
