public class Point {

    public Integer type = 0;
    public Point next;
    public Boolean moved = false;

    public void move() {
        if (type == 1 && next.type == 0 && !moved && !next.moved) {
            type = 0;
            next.type = 1;
            moved = true;
            next.moved = true;
        }
    }

    public void clicked() {
        type = 1;
    }

    public void clear() {
        type = 0;
    }
}

