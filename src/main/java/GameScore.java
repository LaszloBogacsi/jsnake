import javax.swing.*;

public class GameScore extends JLabel implements Score {

    public GameScore() {
        this.setText("0");
    }

    @Override
    public void update(int score) {
        this.setText(String.valueOf(score));
    }

    void reset() {
        this.setText("0");
    }
}
