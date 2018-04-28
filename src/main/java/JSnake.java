import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;

public class JSnake {
    private static final String GAME_NAME = "Java Snake";
    private static final int MAP_WIDTH = 600;
    private static final int MAP_HEIGHT = MAP_WIDTH;

    public static void main(String[] args) {
        Map map = new Map(MAP_WIDTH, MAP_HEIGHT);
        Snake snake = new Snake(map);
        initGame(snake);
    }

    private static void initGame(Snake snake) {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setTitle(GAME_NAME);
        jFrame.setSize(MAP_WIDTH, MAP_HEIGHT);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.getContentPane().add(snake);
        jFrame.pack();

        jFrame.setVisible(true);
    }
}
