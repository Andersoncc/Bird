package com.adnerson.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture fundo;
	private Texture[] passaros;

	//*Configuração*//
	private int larguraDispositivo;
	private int alturaDispositivo;
	private int variacao=0;




	
	@Override
	public void create () {
		batch = new SpriteBatch();
		fundo = new Texture("fundo.png");
		passaros = new Texture[3];
		passaros[0] = new Texture ("passaro1.png");
		passaros[1] = new Texture ("passaro2.png");
		passaros[2] = new Texture ("passaro3.png");


		larguraDispositivo =  Gdx.graphics.getWidth();
		alturaDispositivo =  Gdx.graphics.getHeight();

	}

	@Override
	public void render () {
		variacao++;
		if(variacao>2) variacao =0;
		batch.begin();
		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
		batch.draw(passaros[variacao],30,alturaDispositivo/2);
		batch.end();


	}
	

}
