package com.adnerson.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture fundo;
	private Texture passaro1;
	private Texture passaro2;
	private Texture passaro3;
	private int movimento=0;




	
	@Override
	public void create () {
		batch = new SpriteBatch();
		fundo = new Texture("fundo.png");
		passaro1 = new Texture("passaro1.png");
		passaro2= new Texture("passaro2.png");
		passaro3 = new Texture("passaro3.png");


	}

	@Override
	public void render () {
		movimento++;
		batch.begin();
		batch.draw(fundo,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(passaro1,movimento,400);
		batch.end();


	}
	

}
