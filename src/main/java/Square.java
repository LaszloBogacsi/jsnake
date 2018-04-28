import javax.swing.*;
import java.awt.*;

public class Square  extends JPanel{
    private final int height;
    private final int width;

    public Square(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(50, 50, 10, 10);
    }
}
