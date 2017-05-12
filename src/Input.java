import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Jared H on 5/12/2017.
 */
public class Input extends KeyListener{

    boolean[] keys = new boolean[160];

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
