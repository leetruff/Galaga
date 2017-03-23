package com.ruffles.galaga;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ruffles.galaga.Enemy.State;

/*
 * Class, which models a swarm of enemys flying onto the playfield
 */
public class Swarm {

	ArrayList<Enemy> enemyList;
	ShapeRenderer shaperenderer;
	
	public Swarm(int size, Direction direction, GameScreen gameScreen){
		
		
		shaperenderer = new ShapeRenderer();
		shaperenderer.setAutoShapeType(true);
		shaperenderer.begin();
		shaperenderer.setColor(Color.RED);
		shaperenderer.end();	
		
		
		enemyList = new ArrayList<Enemy>();
		
		for(int i = 0; i < size; i++){
			if(direction == Direction.LEFT){
				enemyList.add(new BasicEnemy(0, 300));
				enemyList.get(i).setCurrentState(State.ARRIVING);
			}
			
			if(direction == Direction.RIGHT){
				enemyList.add(new BasicEnemy(400, 300));
				enemyList.get(i).setCurrentState(State.ARRIVING);
			}
		}
		
		for(int i = 0; i < enemyList.size(); i++){
			gameScreen.enemyList.add(enemyList.get(i));
		}
	}
	
	public void update(float delta){
		
	}
	
	public ShapeRenderer getShaperenderer() {
		return shaperenderer;
	}
}
