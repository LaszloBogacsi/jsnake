import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JSnake {
    private static final String GAME_NAME = "Java Snake";
    private static final int MAP_WIDTH = 600;
    private static final int MAP_HEIGHT = MAP_WIDTH;
    private static final int MAP_GRID_WIDTH = 20;
    private static final int MAP_GRID_HEIGHT = MAP_GRID_WIDTH;
    private static final int MAX_NUM_SNAKE_FOOD = 6;
    JFrame jFrame;
    JSplitPane gamePane;
    JPanel scorePanel;
    JButton reset;
    JLabel scoreLabel;
    GameScore score;
    Map map;
    SnakeFood food;
    Snake snake;


    public static void main(String[] args) {
        JSnake jsnake = new JSnake();
        jsnake.newGame();



    }

    void newGame() {
        map = new Map(MAP_WIDTH, MAP_HEIGHT, MAP_GRID_WIDTH, MAP_GRID_HEIGHT);
        food = new SnakeFood(map, MAX_NUM_SNAKE_FOOD);
        snake = new Snake(map, food);
        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setTitle(GAME_NAME);
        jFrame.setSize(MAP_WIDTH, MAP_HEIGHT);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setPreferredSize(new Dimension(MAP_WIDTH + 200, MAP_HEIGHT + 50));
        jFrame.getContentPane().setLayout(new GridLayout());

        gamePane = new JSplitPane();

        gamePane.setLeftComponent(snake);

        scorePanel = new JPanel();
        reset = new JButton("New Game");
        scoreLabel = new JLabel("Score:");
        score = new GameScore();
        snake.addObserver(score);
        jFrame.getContentPane().add(gamePane);

        gamePane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        gamePane.setDividerLocation(MAP_WIDTH);

        gamePane.setRightComponent(scorePanel);
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.add(scoreLabel);
        scorePanel.add(score);
        scorePanel.add(reset);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
                newGame();
            }
        });

        jFrame.pack();
//        gamePane.setEnabled(false);

        jFrame.setVisible(true);
    }

    private void resetGame() {
        map = new Map(MAP_WIDTH, MAP_HEIGHT, MAP_GRID_WIDTH, MAP_GRID_HEIGHT);
        food = new SnakeFood(map, MAX_NUM_SNAKE_FOOD);
        snake = new Snake(map, food);
        snake.addObserver(score);
        gamePane.setLeftComponent(snake);
    }


}
