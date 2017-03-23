package com.ruffles.galaga;

import java.util.ArrayList;

import com.sun.javafx.geom.Point2D;

/*
 * Class, which interpolates enemy paths, so they fly smoother
 */
public class Interpolation {

	public static ArrayList<Point2D> interpolateArray(ArrayList<Point2D> data, int steps){
		

		for(int j = 0; j < steps; j++){
			int i = 0;
			while(i < data.size()-1){
				
				int x1 = (int) data.get(i).x;
				int x2 = (int) data.get(i+1).x;
				
				int y1 = (int) data.get(i).y;
				int y2 = (int) data.get(i+1).y;
				
				int rx = (x1 + x2) / 2;
				int ry = (y1 + y2) / 2;
				data.add(i+1, new Point2D(rx, ry));
				i += 2;
			}
		}
		
		return data;
	}
}
