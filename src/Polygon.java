import java.awt.Point;
import java.util.ArrayList;



public class Polygon {

	public double area;
	public double naXDist;
	public double naYDist;
	private ArrayList<Point> points;
	
	
	public Polygon(){}
	
	public Polygon(ArrayList<Point> points) {
		this.points = points;

		this.area = findArea(points);
	}
	
	
	private double findArea(ArrayList<Point> points){
		
		double area=0;

		for( int i = 0; i < points.size(); i += 2 )
		   area += points.get(i+1).x*(points.get(i+2).y-points.get(i).y) + points.get(i+1).y*(points.get(i).x-points.get(i+2).x);
		area /= 2;
		
		return area;
		
	}
	
	

}
