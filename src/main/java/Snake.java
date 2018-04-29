import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Deque;
import java.util.LinkedList;

public class Snake extends JPanel implements KeyListener {
    private static final int SNAKE_WIDTH = 20;
    private static final int SNAKE_HEIGHT = SNAKE_WIDTH;
    private final Map map;
    private SnakeFood food;
    private Deque<Rectangle> snakeBits = new LinkedList<>();

    public Snake(Map map, SnakeFood food) {
        this.map = map;
        this.food = food;
        this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        this.setBackground(Color.GRAY);
        this.addKeyListener(this);
        this.setFocusable(true);
        snakeBits.add(new Rectangle(map.getWidth() / 2 + 40, map.getHeight() / 2, SNAKE_WIDTH, SNAKE_HEIGHT));
        snakeBits.add(new Rectangle(map.getWidth() / 2 + 20, map.getHeight() / 2, SNAKE_WIDTH, SNAKE_HEIGHT));
        snakeBits.add(new Rectangle(map.getWidth() / 2, map.getHeight() / 2, SNAKE_WIDTH, SNAKE_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        food.getFoodItems().forEach(food -> {
            g.setColor(Color.BLUE);
            g.fillOval(food.x, food.y, food.width, food.height);
        });

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

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        Rectangle first = snakeBits.peek();
        Rectangle last;
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                if (first.getMinX() >= 0 + first.width) {
                    last = snakeBits.pollLast();
                    last.setBounds(first.x - last.width, first.y, last.width, last.height);
                    snakeBits.offerFirst(last);
                }
                break;
            case KeyEvent.VK_UP:
                if (first.getMinY() >= 0 + first.height) {
                    last = snakeBits.pollLast();
                    last.setBounds(first.x, first.y - last.height, last.width, last.height);
                    snakeBits.offerFirst(last);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (first.getMaxX() <= map.getWidth() - first.width) {
                    last = snakeBits.pollLast();
                    last.setBounds(first.x + last.width, first.y, last.width, last.height);
                    snakeBits.offerFirst(last);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (first.getMaxY() <= map.getHeight() - first.height) {
                    last = snakeBits.pollLast();
                    last.setBounds(first.x, first.y + last.height, last.width, last.height);
                    snakeBits.offerFirst(last);
                }
                break;
        }
        foodCollisionDetection();
        repaint();
    }

    private void foodCollisionDetection() {
        Rectangle first = snakeBits.peek();
        Rectangle foodEaten = null;

        for(Rectangle foodItem : food.getFoodItems()) {
            if (foodItem.getCenterX() == first.getCenterX() && foodItem.getCenterY() == first.getCenterY()) {
                foodEaten = foodItem;
                Rectangle last = snakeBits.peekLast();

                foodEaten.setBounds(last.x - foodEaten.width, last.y, last.width, last.height);
                snakeBits.add(foodItem);
            }
        }
        if (foodEaten != null) {
            food.getFoodItems().remove(foodEaten);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
