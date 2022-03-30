import javax.swing.*;
import java.lang.reflect.Array;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.System.out;

public class Point {
    public Integer max_speed = 0;
    public static Integer[] speed_from_type = {0, 3, 5, 7, 0, 0};
    static public Integer p = 20;

    public Integer type = 0;
    public Point[] next;
    public Boolean moved = false;
    public Integer speed = 0;
    public Point[][] board;
    private final int x;
    private final int y;
    public static Integer[] types = {0, 1, 2, 3, 5};

    public Point(Integer type, int i, int j, Board board) {
        this.type = type;
        this.board = board.getPoints();
        x = i;
        y = j;
        max_speed = speed_from_type[type];
        next = new Point[7 + 1];
        speed = max_speed;
    }

    public void slowingDown() {
        int free_spaces = 0;
        for (int i = 1; i <= max_speed; i++) {
            if (next[i].type == 0) {
                free_spaces += 1;
            } else {
                break;
            }
        }
        speed = min(free_spaces, speed);
    }


    public void accelerate() {
        speed += 1;
        speed = min(max_speed, speed);
    }

    public void updateSpeed() {
        if (type != 0 && type != 5) {
            accelerate();
            slowingDown();
        }
    }

    public int getClosestBehind(int lane) {
        for (int i = 1; i <= max_speed; i++) {
            if (board[(x - i + board.length) % board.length][lane+2].type != 0) {
                return i-1;
            }
        }
        return max_speed;
    }

    public int getClosestInFront(int lane) {
        for (int i = 1; i <= max_speed; i++) {
            if (board[(x + i + board.length) % board.length][lane+2].type != 0) {
                return i-1;
            }
        }
        return max_speed;
    }

    public void move() {
        if (type != 0 && type != 5 && !moved && speed != 0) {
            if (y == 1 && speed < max_speed && getClosestBehind(1) >= max_speed
                    && getClosestBehind(0) >= max_speed && getClosestInFront(0) > speed) {
                board[(x + (speed - 1))%board.length][y + 1].speed = speed + 1;
                board[(x + (speed - 1))%board.length][y + 1].type = type;
                board[(x + (speed - 1))%board.length][y + 1].max_speed = Point.speed_from_type[type];
                board[(x + (speed - 1))%board.length][y + 1].moved = true;
                type = 0;
                moved = true;
                return;
            }
            if (y==0 && getClosestBehind(1)>=max_speed && getClosestBehind(0)>=max_speed
                    && getClosestInFront(1)>=speed){
                board[(x + (speed - 1))%board.length][y + 3].speed = speed;
                board[(x + (speed - 1))%board.length][y + 3].type = type;
                board[(x + (speed - 1))%board.length][y + 3].max_speed = Point.speed_from_type[type];
                board[(x + (speed - 1))%board.length][y + 3].moved = true;
                type = 0;
                moved = true;
                return;
            }
            moved = true;
            int last_free_space = 0;
            for (int i = 0; i <= speed; i++) {
                if (next[i].type == 0) {
                    last_free_space = i;
                }
            }
            next[last_free_space].type = type;
            next[last_free_space].speed = speed;
            next[last_free_space].max_speed = Point.speed_from_type[type];
            type = 0;
            next[last_free_space].moved = true;
        }

    }

    public void clicked() {
        type = 0;
    }

    public void clear() {
        type = 0;
    }
}

