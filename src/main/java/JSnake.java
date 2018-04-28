import javax.swing.*;

public class JSnake {
    private static final String GAME_NAME = "Java Snake";

    public static void main(String[] args) {
        initMap();
    }

    private static void initMap() {
        Map map = new Map(600, 600);
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setTitle(GAME_NAME);
        jFrame.setSize(map.getWidth(), map.getHeight());
        jFrame.setLocationRelativeTo(null);
        Square square = new Square(10, 10);
        jFrame.getContentPane().add(square);
        jFrame.setVisible(true);

    }
}
