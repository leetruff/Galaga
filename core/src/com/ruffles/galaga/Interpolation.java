package com.ruffles.galaga;

import java.util.ArrayList;

/*
 * Class, which interpolates enemy paths, so they fly smoother
 */
public class Interpolation {

	public static ArrayList<Integer> interpolateArray(ArrayList<Integer> data, int steps){
		

		for(int j = 0; j < steps; j++){
			int i = 0;
			while(i < data.size()-1){
				
				int v1 = data.get(i);
				int v2 = data.get(i+1);
				
				int r = (v1 + v2) / 2;
				data.add(i+1, r);
				i += 2;
			}
		}
		
		return data;
	}
}
