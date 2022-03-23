import java.lang.reflect.Array;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.System.out;

public class Point {
    static public Integer max_speed = 5;
    static public Integer p = 20;
    static public Random random = new Random();

    public Integer type = 0;
    public Point next[];
    public Boolean moved = false;
    public Integer speed = 0;

    public Point(){
        next = new Point[max_speed+1];
    }
    public void slowingDown(){
        int free_spaces = 0;
        for(int i=0; i<=max_speed;i++){
            if (next[i].type == 0){
                free_spaces+=1;
            }
        }
        speed = max(free_spaces, speed);
    }
    public void Rng(){
        if (speed >=1 && random.nextInt(100) < p){
            speed-=1;
        }
    }


    public void accelerate(){
        speed+=1;
        speed = min(max_speed,speed);
    }

    public void updateSpeed(){
        if (type == 1) {
            accelerate();
            slowingDown();
            Rng();
        }
    }

    public void move() {
        if (type == 1 && !moved) {
            type = 0;
            moved = true;
            int last_free_space = 0;
            for (int i=0;i<=speed;i++){
                if (next[i].type == 0){
                    last_free_space = i;
                }
            }
            next[last_free_space].type = 1;
            next[last_free_space].moved = true;
        }

    }

    public void clicked() {
        type = 1;
    }

    public void clear() {
        type = 0;
    }
}

