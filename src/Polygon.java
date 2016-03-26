import java.awt.Point;
import java.util.ArrayList;

//So this class needs to be constructed with an ArrayList of Point objects. Upon construction, it automatically finds its own area and MOI

public class Polygon {

	public double area;
	public double moiX;
	public double moiY;
	public double naXDist;
	public double naYDist;
	private ArrayList<Point> points;
	
	
	public Polygon(){}
	
	public Polygon(ArrayList<Point> points) {
		this.points = points;

		this.area = findArea(points);
		this.moiX = findMOIX(points);
		this.moiY = findMOIY(points);
		
	}
	
	
	private double findArea(ArrayList<Point> points){
		
		double area=0;

		for( int i = 0; i < points.size(); i += 2 )
		   area += points.get(i+1).x*(points.get(i+2).y-points.get(i).y) + points.get(i+1).y*(points.get(i).x-points.get(i+2).x);
		area /= 2;
		
		return area;
		
	}
	
	private double findMOIX(ArrayList<Point> points){
		double moi = 0;
		
		for(int i=0; i<points.size(); i++){
			moi=((points.get(i).y)^2+(points.get(i).y)*(points.get(i+1).y)+(points.get(i+1).y)^2)+((points.get(i).x)*(points.get(i+1).y)-(points.get(i+1).x)*(points.get(i).y));
					
		}
		return moi;
	}
	
	private double findMOIY(ArrayList<Point> points){
		double moi = 0;
		
		for(int i=0; i<points.size(); i++){
			moi=((points.get(i).x)^2+(points.get(i).x)*(points.get(i+1).x)+(points.get(i+1).x)^2)+((points.get(i).x)*(points.get(i+1).y)-(points.get(i+1).x)*(points.get(i).y));		
		}
		
		return moi;
	}

}
