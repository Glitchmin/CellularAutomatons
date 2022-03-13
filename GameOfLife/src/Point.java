import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Point {
    private ArrayList<Point> neighbours;
    private int currentState;
    private int nextState;
    private final static List<Integer> transitionRule1 = Arrays.asList(2, 3);
    private final static List<Integer> transitionRule2 = List.of(3);
    private static Boolean isRain = false;
    private int numStates = 6;
    private final static Random random = new Random();
    private final static int dropChance = 5;

    public Point() {
        currentState = 0;
        nextState = 0;
        neighbours = new ArrayList<>();
    }

    public void clicked() {
        currentState = (++currentState) % numStates;
    }

    public int getState() {
        return currentState;
    }

    public void setState(int s) {
        currentState = s;
    }

    public void calculateNewState() {
        if (isRain) {
            if (currentState > 0) {
                nextState = currentState - 1;
                return;
            }
            if (!neighbours.isEmpty() && neighbours.get(0).currentState > 0){
                nextState = 6;
            }
            return;
        }
        if (getState() == 0 && transitionRule2.contains(getActiveNeighboursCount())) {
            nextState = 1;
            return;
        }
        if (getState() == 0) {
            nextState = 0;
            return;
        }
        if (transitionRule1.contains(getActiveNeighboursCount())) {
            nextState = 1;
        } else {
            nextState = 0;
        }
    }


    public void changeState() {
        currentState = nextState;
    }

    public void addNeighbour(Point nei) {
        neighbours.add(nei);
    }

    public void drop() {
        if (random.nextInt(100) < dropChance) {
            nextState = 6;
        }
    }

    public static void changeIsRain() {
        Point.isRain = !isRain;
    }

    public static Boolean getIsRain() {
        return isRain;
    }

    public void clearNeighbours(){
        neighbours.clear();
    }

    public int getActiveNeighboursCount() {
        int answer = 0;
        if (!isRain) {
            for (Point neighbor : neighbours) {
                answer += neighbor.getState();
            }
        }
        if (isRain){
            answer += neighbours.size();
        }
        return answer;
    }
}
