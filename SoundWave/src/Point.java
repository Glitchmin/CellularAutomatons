public class Point {

    public Point nNeighbour;
    public Point wNeighbour;
    public Point eNeighbour;
    public Point sNeighbour;
    public float nVel;
    public float eVel;
    public float wVel;
    public float sVel;
    public float pressure;
    public int type = 0;
    public static Integer[] types = {0,1,2};
    public static int sinInput = 45;

    public Point() {
        clear();
    }

    public void clicked() {
        pressure = 1;
    }

    public void clear() {
        nVel = 0;
        eVel = 0;
        wVel = 0;
        sVel = 0;
        pressure = 0.0F;
    }

    public void updateVelocity() {
        if (type == 1){
            clear();
            return;
        }
        nVel = nVel - nNeighbour.pressure + pressure;
        sVel = sVel - sNeighbour.pressure + pressure;
        eVel = eVel - eNeighbour.pressure + pressure;
        wVel = wVel - wNeighbour.pressure + pressure;
    }

    public void updatePressure() {
        if (type == 1){
            clear();
            return;
        }
        if (type == 2){
            double radians = Math.toRadians(sinInput);
            pressure = (float) (Math.sin(radians));
            sinInput+=25;
            return;
        }
        pressure = (float) (pressure - 0.5 * (nVel + sVel + eVel + wVel));
    }


    public float getPressure() {
        return pressure;
    }
}