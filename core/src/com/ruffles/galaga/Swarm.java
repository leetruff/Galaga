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
	Direction direction;
	
	public Swarm(int size, Direction direction, GameScreen gameScreen){
		
		this.direction = direction;
		
		shaperenderer = new ShapeRenderer();
		shaperenderer.setAutoShapeType(true);
		shaperenderer.begin();
		shaperenderer.setColor(Color.RED);
		shaperenderer.end();	
		
		
		enemyList = new ArrayList<Enemy>();
		
		for(int i = 0; i < size; i++){
			if(direction == Direction.LEFT){
				enemyList.add(new BasicEnemy(-100, 300, gameScreen));
			}
			
			if(direction == Direction.RIGHT){
				enemyList.add(new BasicEnemy(400, 300, gameScreen));
			}
		}
		
		for(int i = 0; i < enemyList.size(); i++){
			gameScreen.enemyList.add(enemyList.get(i));
		}
	}

	float startPathTimer = 0;
	
	public void update(float delta){
		startPathTimer += delta;
		
		if(startPathTimer > 0.25){
			startNextEnemy();
			startPathTimer = 0;
		}
	}
	
	int currentEnemy = 0;
	private void startNextEnemy() {
		if(currentEnemy < enemyList.size()){
			
			if(direction == Direction.LEFT){
			enemyList.get(currentEnemy).startFlyInLeft(currentEnemy);
			System.out.println("started enemy #" + currentEnemy);
			}
			
			if(direction == Direction.RIGHT)
			enemyList.get(currentEnemy).startFlyInRight();
			
			currentEnemy++;
		}
	}

	public ShapeRenderer getShaperenderer() {
		return shaperenderer;
	}
}
