import java.awt.*;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SnakeFood {
    private static final int FOOD_WIDTH = 20;
    private static final int FOOD_HEIGHT = FOOD_WIDTH;
    private Queue<Rectangle> foodItems;
    private Map map;
    private LocalTime timeLastGenerated;
    private int maxNumSnakeFood;
    private final long GENERATE_FOOD_TIMEOUT_SEC = 10;

    public SnakeFood(Map map, int maxNumSnakeFood) {
        this.map = map;
        this.maxNumSnakeFood = maxNumSnakeFood;
        this.timeLastGenerated = LocalTime.now();
        this.foodItems = generateFoodItems(maxNumSnakeFood);
    }

    private Queue<Rectangle> generateFoodItems(int maxNum) {
        return IntStream.range(1, (int)(Math.random()*maxNum))
                .mapToObj(i -> new Rectangle((int)(Math.random()*this.map.getWidth()/FOOD_WIDTH)*FOOD_WIDTH, (int)(Math.random()*this.map.getHeight()/FOOD_HEIGHT)*FOOD_HEIGHT, FOOD_WIDTH, FOOD_HEIGHT))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public Queue<Rectangle> getFoodItems() {
        return foodItems;
    }

    public void maybeGenerateNewFood() {
        if (foodItems.size() < 1 || isTimeToGenerate()) {
            this.foodItems = generateFoodItems(maxNumSnakeFood);
            this.timeLastGenerated = LocalTime.now();
        }
    }

    private boolean isTimeToGenerate() {
        return timeLastGenerated.plusSeconds(GENERATE_FOOD_TIMEOUT_SEC).isBefore(LocalTime.now());
    }


}
