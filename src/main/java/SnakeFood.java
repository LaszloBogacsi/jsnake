import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SnakeFood {
    private static final int FOOD_WIDTH = 20;
    private static final int FOOD_HEIGHT = FOOD_WIDTH;
    private Queue<Rectangle> foodItems;
    private Map map;

    public SnakeFood (Map map) {
        this.map = map;
        this.foodItems = generateFoodItems(10);
    }

    private Queue<Rectangle> generateFoodItems(int num) {
        return IntStream.range(1, num)
                .mapToObj(i -> new Rectangle((int)(Math.random()*this.map.getWidth()/FOOD_WIDTH)*FOOD_WIDTH, (int)(Math.random()*this.map.getHeight()/FOOD_HEIGHT)*FOOD_HEIGHT, FOOD_WIDTH, FOOD_HEIGHT))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public Queue<Rectangle> getFoodItems() {
        return foodItems;
    }


}
