import java.util.ArrayList;

import static java.lang.System.out;

public class Point {

	public ArrayList<Point> neighbours;
	public static Integer []types ={0,1,2,3};
	public int type;
	public int staticField;
	public boolean isPedestrian;
	public boolean blocked = false;

	public Point() {
		type=0;
		staticField = 100000;
		neighbours= new ArrayList<Point>();
	}
	
	public void clear() {
		staticField = 100000;
		
	}

	public boolean calcStaticField() {
		if (type==1){
			return false;
		}
		boolean something_new = false;
		for (Point point: neighbours){
			if (point.staticField+1<staticField){
				staticField=point.staticField+1;
				something_new=true;
			}
		}
		return something_new;
	}
	
	public void move(){
		if (isPedestrian && !blocked){
			Point min=null;
			int mini = staticField;
			for (Point point:neighbours){
				if (!point.isPedestrian && point.staticField < mini){
					min = point;
					mini = point.staticField;
				}
			}
			if (min!=null) {
				if ( min.type!=2) {
					min.isPedestrian = true;
				}
				min.blocked = true;
				isPedestrian=false;
			}
		}
	}

	public void addneighbour(Point nei) {
		neighbours.add(nei);
	}
}