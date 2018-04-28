import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;

public class JSnake extends JPanel implements KeyListener {
    private static final String GAME_NAME = "Java Snake";
    private static final int SNAKE_WIDTH = 20;
    private static final int SNAKE_HEIGHT = SNAKE_WIDTH;
    private final Map map;
    private Queue<Rectangle> snakeBits = new LinkedList<>();

    public JSnake(Map map) {
        this.map = map;
        this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        this.setBackground(Color.GRAY);
        this.addKeyListener(this);
        this.setFocusable(true);
        snakeBits.add(new Rectangle(map.getWidth() / 2 + 40, map.getHeight() / 2, SNAKE_WIDTH, SNAKE_HEIGHT));
        snakeBits.add(new Rectangle(map.getWidth() / 2 + 20, map.getHeight() / 2, SNAKE_WIDTH, SNAKE_HEIGHT));
        snakeBits.add(new Rectangle(map.getWidth() / 2, map.getHeight() / 2, SNAKE_WIDTH, SNAKE_HEIGHT));
    }

    public static void main(String[] args) {
        Map map = new Map(600, 600);
        JSnake jSnake = new JSnake(map);
        jSnake.initMap(jSnake);
    }

    private void initMap(JSnake jSnake) {
        JFrame jFrame = new JFrame();

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setTitle(GAME_NAME);
        jFrame.setSize(map.getWidth(), map.getHeight());
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);

        jFrame.getContentPane().add(jSnake);
        jFrame.pack();

        jFrame.setVisible(true);
        System.out.println("Jframe created");

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        snakeBits.stream().limit(1).forEach(snake -> {
            g.setColor(Color.RED);
            g.fillRect(snake.x, snake.y, snake.width, snake.height);
        });
        snakeBits.stream().skip(1).forEach(snake -> {
            g.setColor(Color.GREEN);
            g.fillRect(snake.x, snake.y, snake.width, snake.height);
        });

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // ignored
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("e = " + e.getKeyCode());
        int keyCode = e.getKeyCode();
        Rectangle first = snakeBits.peek();
        Rectangle last = ((LinkedList<Rectangle>) snakeBits).pollLast();
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                last.setBounds(first.x - last.width, first.y, last.width, last.height);
                ((LinkedList<Rectangle>) snakeBits).offerFirst(last);
                break;
            case KeyEvent.VK_UP:
                last.setBounds(first.x, first.y - last.height, last.width, last.height);
                ((LinkedList<Rectangle>) snakeBits).offerFirst(last);
                break;
            case KeyEvent.VK_RIGHT:
                last.setBounds(first.x + last.width, first.y, last.width, last.height);
                ((LinkedList<Rectangle>) snakeBits).offerFirst(last);
                break;
            case KeyEvent.VK_DOWN:
                last.setBounds(first.x, first.y + last.height, last.width, last.height);
                ((LinkedList<Rectangle>) snakeBits).offerFirst(last);
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // ignored
    }
}
