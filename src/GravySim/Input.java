package GravySim;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Jared H on 5/12/2017.
 */
public class Input implements KeyListener {

    public boolean[] keys = new boolean[600];
    public boolean[] keyTyped = new boolean[600];

    public void keyTyped(KeyEvent e){

    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < keys.length) {
            keys[e.getKeyCode()] = true;
        }
    }


    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;

        //FOR TOGGLING
        keyTyped[e.getKeyCode()] = true;
    }
}
