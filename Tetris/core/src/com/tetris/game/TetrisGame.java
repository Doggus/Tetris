package com.tetris.game;

//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;


public class TetrisGame extends Game{

	@Override
	public void create ()
	{
		this.setScreen(new GameScreen(this));//switch to the gamescreen
	}

	@Override
	public void render ()
	{
		super.render(); //use the render method used by the Game class
	}
}
