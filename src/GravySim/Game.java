package GravySim;

import GravySim.entities.Entity;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by weavechr000 on 5/12/2017.
 */

public class Game extends JPanel implements ActionListener{
    public static Dimension windowSize = new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/10*9,
            (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/10*9);


    public boolean running = false, fullscreen = false, equations = false;

    int frameRate = 0, scl = 4;
    Input input = new Input();
    JFrame frame;
    Timer timer;

    CopyOnWriteArrayList<Entity> ents = new CopyOnWriteArrayList<Entity>();


    public Game() {

        frame = new JFrame("Gravy");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
        setPreferredSize(windowSize);
        frame.setLocationRelativeTo(this);
        frame.add(this);
        setVisible(true);
        frame.setResizable(false);
        frame.addKeyListener(input);
        frame.pack();
    }

    public void init(Entity a, Entity b){
        ents.clear();
        ents.add(a);
        ents.add(b);
        timer = new Timer(1000/120, this);
        timer.start();
    }
    public void init() {
        timer = new Timer(1000/120, this);
        timer.start();
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
            while (deltat >= .3){ //150 ps
                tick();
                deltat-=.3;
            }

            while (deltaf >= 2.2) { //60 ps
                repaint();
                frames++;
                deltaf-=2.2;
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
        for (int i = 0; i < ents.size(); i++){
            Entity e = ents.get(i);
            e.paint((i == 0) ? ents.get(1) : ents.get(0), g);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 32));
        if(ents.size() == 0) {
            printSimpleString("F1 - Fullscreen", 0, getWidth()/2, getHeight()/2 - g.getFontMetrics().getHeight()*2, g);
            printSimpleString("Space - Show Equations", 0, getWidth()/2, getHeight()/2, g);
            printSimpleString("1 & 2 - Toggle Presets", 0, getWidth()/2, getHeight()/2 + g.getFontMetrics().getHeight()*2, g);
        }

    }

    public void checkInput(){
        if (input.keyTyped[KeyEvent.VK_F1]){
            fullScreen();
            input.keyTyped[KeyEvent.VK_F1] = false;
        }
        if (!equations && input.keyTyped[KeyEvent.VK_SPACE]) {
            equations = true;
            input.keyTyped[KeyEvent.VK_SPACE] = false;
        }
        else if (equations && input.keyTyped[KeyEvent.VK_SPACE]) {
            equations = false;
            input.keyTyped[KeyEvent.VK_SPACE] = false;
        }
        
        if (input.keyTyped[KeyEvent.VK_1]) {
            init(new Entity(Color.YELLOW, 0, 0, 695.7E6/5, 1.989E30/6, 0,0, this), new Entity(Color.BLUE, 1.096E9/2/Math.sqrt(2),0,2.371E7, 3.97E24, 0,-2E5, this));
            input.keyTyped[KeyEvent.VK_1] = false;
        }
        else if (input.keyTyped[KeyEvent.VK_2]) {
            init(new Entity(Color.YELLOW, 0, 0, 695.7E6/5, 1.989E30/6, 0,0, this), new Entity(Color.BLUE, 1.496E9/2/Math.sqrt(2),0,2.371E7, 3.97E24, 0,-2.5E5, this));
            input.keyTyped[KeyEvent.VK_2] = false;
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
            frame.setResizable(false);
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

        game.init();
    }

    public int getScl() {
        return scl;
    }

    public CopyOnWriteArrayList<Entity> getEnts() {
        return ents;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        tick();
        repaint();

    }
    
    private void printSimpleString(String s, int width, int XPos, int YPos, Graphics g2d) {

        int stringLen = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int start = width/2 - stringLen/2;
        g2d.drawString(s,start + XPos, YPos);

    }
}
