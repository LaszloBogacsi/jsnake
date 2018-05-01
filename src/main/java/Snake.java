import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class Snake extends JPanel implements KeyListener {
    private static final int SNAKE_WIDTH = 14;
    private static final int SNAKE_HEIGHT = SNAKE_WIDTH;
    private final Map map;
    private SnakeFood foods;
    private Deque<Rectangle> snakeBits = new ArrayDeque<>();
    private int direction;
    private boolean gameOver;
    private Timer timer;
    private int score;
    private List<Score> scoreObservers = new ArrayList<>();

    public Snake(Map map, SnakeFood food) {
        this.map = map;
        this.foods = food;
        this.gameOver = false;
        this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        this.setBackground(Color.GRAY);
        this.addKeyListener(this);
        this.setFocusable(true);
        int offsetX = (map.getGridWidth() - SNAKE_WIDTH) / 2;
        int offsetY = (map.getGridHeight() - SNAKE_HEIGHT) / 2;

        snakeBits.add(new Rectangle(map.getWidth() / 2 + map.getGridWidth() * 2 + offsetX, map.getHeight() / 2 + offsetY, SNAKE_WIDTH, SNAKE_HEIGHT));
        snakeBits.add(new Rectangle(map.getWidth() / 2 + map.getGridWidth() + offsetX, map.getHeight() / 2 + offsetY, SNAKE_WIDTH, SNAKE_HEIGHT));
        snakeBits.add(new Rectangle(map.getWidth() / 2  + offsetX, map.getHeight() / 2 + offsetY, SNAKE_WIDTH, SNAKE_HEIGHT));

        direction = (int)(Math.random()*4) + KeyEvent.VK_LEFT; // choose a random starting direction
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        };
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        start(task); // start the game loop
    }

    public void addObserver(Score score) {
        this.scoreObservers.add(score);
    }

    private void updateScore() {
        for (Score scoreLabel : scoreObservers) {
            scoreLabel.update(this.score);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isGameOver()) {
            g.drawString("Game Over", 300, 300);
        }
        g.drawRect(0,0, map.getWidth(), map.getHeight());

        foods.getFoodItems().forEach(food -> {
//            g.setColor(Color.BLUE);
//            g.fillOval(food.x, food.y, food.width, food.height);
            g.drawImage(foods.image, food.x, food.y, null);
        });
        // snake head
        snakeBits.stream().limit(1).forEach(snake -> {
            g.setColor(Color.RED);
            g.drawRect(snake.x-1, snake.y-1, snake.width+1, snake.height+1);
            g.setColor(Color.RED);
            g.fillRect(snake.x, snake.y, snake.width, snake.height);
        });

        snakeBits.stream().skip(1).forEach(snake -> {
            // snake bit outline
            g.setColor(Color.BLACK);
            g.drawRect(snake.x-1, snake.y-1, snake.width+1, snake.height+1);
            // snake bit body
            g.setColor(Color.GREEN);
            g.fillRect(snake.x, snake.y, snake.width, snake.height);
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.direction = e.getKeyCode();
    }

    private void start(TimerTask task) {
        timer.schedule(task, 0, 200);
    }

    private void stop() {
        timer.cancel();
        System.out.println("Game over, stopped");
    }

    private void tick(){
        if(!isGameOver()) {
            moveSnake(this.direction);
            foodCollisionDetection();
            foods.maybeGenerateNewFood();
            repaint();
        } else {
            stop();
        }
    }

    private void moveSnake(int direction) {
        Rectangle first = snakeBits.peek();
        Rectangle last;
        switch(direction) {
            case KeyEvent.VK_LEFT:
                if (first.getMinX() >= 0 + first.width) {
                    last = snakeBits.pollLast();
                    last.setBounds(first.x - map.getGridWidth(), first.y, last.width, last.height);
                    snakeBits.offerFirst(last);
                } else {
                    hitWall();
                }
                break;
            case KeyEvent.VK_UP:
                if (first.getMinY() >= 0 + first.height) {
                    last = snakeBits.pollLast();
                    last.setBounds(first.x, first.y - map.getGridHeight(), last.width, last.height);
                    snakeBits.offerFirst(last);
                }
                else {
                    hitWall();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (first.getMaxX() <= map.getWidth() - first.width) {
                    last = snakeBits.pollLast();
                    last.setBounds(first.x + map.getGridWidth(), first.y, last.width, last.height);
                    snakeBits.offerFirst(last);
                }
                else {
                    hitWall();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (first.getMaxY() <= map.getHeight() - first.height) {
                    last = snakeBits.pollLast();
                    last.setBounds(first.x, first.y + map.getGridHeight(), last.width, last.height);
                    snakeBits.offerFirst(last);
                }
                else {
                    hitWall();
                }
                break;
        }
    }

    private void hitWall() {
        this.gameOver = true;
    }

    private void foodCollisionDetection() {
        Rectangle first = snakeBits.peek();
        Rectangle foodEaten = null;

        for(Rectangle foodItem : foods.getFoodItems()) {
            if (foodItem.getCenterX() == first.getCenterX() && foodItem.getCenterY() == first.getCenterY()) {
                foodEaten = foodItem;
                Rectangle last = snakeBits.removeLast();
                Rectangle secondToLast = snakeBits.peekLast();
                snakeBits.add(last);

                addFoodToSnakeTail(last, secondToLast, foodItem);
            }
        }
        if (foodEaten != null) {
            foods.getFoodItems().remove(foodEaten);
            this.score++;
            updateScore();
        }
    }

    private void addFoodToSnakeTail(Rectangle last, Rectangle secondToLast, Rectangle foodEaten) {
        int dX = secondToLast.x - last.x;
        int dY = secondToLast.y - last.y;
        // tail moves right, add food left
        if (dX > 0 && dY == 0) {
            foodEaten.setBounds(last.x - map.getGridWidth(), last.y, last.width, last.height);
            snakeBits.add(foodEaten);
        }
        // tail moves left, add food right
        if (dX < 0 && dY == 0) {
            foodEaten.setBounds(last.x + map.getGridWidth(), last.y, last.width, last.height);
            snakeBits.add(foodEaten);
        }
        // tail moves down, add food up
        if (dY > 0 && dX == 0) {
            foodEaten.setBounds(last.x, last.y - map.getGridHeight(), last.width, last.height);
            snakeBits.add(foodEaten);
        }
        // tail moves up, add food down
        if (dY < 0 && dX == 0) {
            foodEaten.setBounds(last.x, last.y + map.getGridHeight(), last.width, last.height);
            snakeBits.add(foodEaten);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public boolean isGameOver() {
        return gameOver;
    }
}
