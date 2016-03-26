package src;

import java.awt.Point;
import java.util.ArrayList;

//So this class needs to be constructed with an ArrayList of Point objects. Upon construction, it automatically finds its own area and MOI

public class Polygon {

	public double area;
	public double moiX;
	public double moiY;
	public double naXDist;
	public double naYDist;
	public ArrayList<Point> points = new ArrayList<Point>();
	
	
	public Polygon(){}
	
	
	
	public Polygon(ArrayList<Point> points) {
		this.points = points;

		
	}
	
	public void add(Point p){
		this.points.add(p);
	}
	
	public void compute(){
		this.area = findArea(points);
		this.moiX = findMOIX(points);
		this.moiY = findMOIY(points);
	}
	
	private double findArea(ArrayList<Point> points){
		
		double area=0;

		for( int i = 0; i < points.size()-1; i ++ ){
			area += points.get(i).x*points.get(i+1).y-points.get(i).y*points.get(i+1).x;
		}
		area +=points.get(points.size()-1).x*points.get(0).y-points.get(points.size()-1).y*points.get(0).x;
		area /= -2;

		return area;

	}
	
	private double findMOIX(ArrayList<Point> points){
		double moi = 0;
		
		for(int i=0; i<points.size()-2; i++){
			moi +=((points.get(i).y)^2+(points.get(i).y)*(points.get(i+1).y)+(points.get(i+1).y)^2)+((points.get(i).x)*(points.get(i+1).y)-(points.get(i+1).x)*(points.get(i).y));
					
		}
		return moi/12;
	}
	
	private double findMOIY(ArrayList<Point> points){
		double moi = 0;
		
		for(int i=0; i<points.size()-2; i++){
			moi +=((points.get(i).x)^2+(points.get(i).x)*(points.get(i+1).x)+(points.get(i+1).x)^2)+((points.get(i).x)*(points.get(i+1).y)-(points.get(i+1).x)*(points.get(i).y));		
		}
		
		return moi/12;
	}

}
