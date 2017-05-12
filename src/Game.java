import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;

/**
 * Created by weavechr000 on 5/12/2017.
 */
public class Game extends JPanel {


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

    public static  void main(String[] args) {
        Game game = new Game();
    }
}
