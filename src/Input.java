import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Jared H on 5/12/2017.
 */
public class Input implements KeyListener {

    boolean[] keys = new boolean[160];

    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }


    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
