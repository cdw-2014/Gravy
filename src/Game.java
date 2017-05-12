import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;

/**
 * Created by weavechr000 on 5/12/2017.
 */
public class Game extends JPanel {
    boolean running = false;
    int frameRate = 0;
    public Game() {

        JFrame frame = new JFrame("Gravy");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
        frame.setSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/10*9,
                (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/10*9));
        frame.setLocationRelativeTo(this);
        frame.add(this);
        setVisible(true);
    }

    public void init(){}

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

        System.exit(0);
    }

    public void tick() {

    }



    public static  void main(String[] args) {

        Game game = new Game();

        game.run();
    }
}
