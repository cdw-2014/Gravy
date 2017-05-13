import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * Created by weavechr000 on 5/12/2017.
 */
public abstract class Entity {

    Color color;
    Image img;
    int screenX, screenY, width, height;
    double mass, momentum, x, y, dx, dy;

    public Entity(Color color, double x, double y, int width, int height, double mass, double dx, double dy) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mass = mass;
        this.dx = dx;
        this.dy = dy;
    }

    public Entity(Image img, double x, double y, int width, int height, double mass, double dx, double dy) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mass = mass;
        this.dx = dx;
        this.dy = dy;
    }

    public void getMomentum(){
        
    }

    public abstract void paint(Graphics g);
}
