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
	private Texture canoBaixo;
	private Texture canoAlto;

	//*Configuração*//
	private int larguraDispositivo;
	private int alturaDispositivo;
	private float variacao=0;
	private float velocidadeQueda=0;
	private float posicaoInicalVertical;
	private float posicaoMovimentoCanoHorizontal;
	private float espacoEntreCanos;
	private float deltaTime;






	
	@Override
	public void create () {
		batch = new SpriteBatch();
		fundo = new Texture("fundo.png");
		canoAlto = new Texture("cano_topo.png");
		canoBaixo = new Texture("cano_baixo.png");
		passaros = new Texture[3];
		passaros[0] = new Texture ("passaro1.png");
		passaros[1] = new Texture ("passaro2.png");
		passaros[2] = new Texture ("passaro3.png");
		larguraDispositivo =  Gdx.graphics.getWidth();
		alturaDispositivo =  Gdx.graphics.getHeight();
		posicaoInicalVertical = alturaDispositivo/2;
		posicaoMovimentoCanoHorizontal = larguraDispositivo -100;
		espacoEntreCanos = 300;



	}

	@Override
	public void render () {
		deltaTime = Gdx.graphics.getDeltaTime();
		variacao+=deltaTime * 10;
		posicaoMovimentoCanoHorizontal-=deltaTime * 200;
		velocidadeQueda++;
		/**Gdx.app.log("varicao","varicao" +Gdx.graphics.getDeltaTime());**/

		if(variacao>2){
			variacao =0;
		}

		if(posicaoInicalVertical >0 || velocidadeQueda < 0){
			posicaoInicalVertical-=velocidadeQueda;
		}

		if(posicaoMovimentoCanoHorizontal < 0) {
			posicaoMovimentoCanoHorizontal = larguraDispositivo - 100;
		};

		if(Gdx.input.justTouched()){
			velocidadeQueda=-15;
		};
		batch.begin();

		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);
		batch.draw(canoAlto,posicaoMovimentoCanoHorizontal,alturaDispositivo / 2 + espacoEntreCanos / 2);
		batch.draw(canoBaixo,posicaoMovimentoCanoHorizontal,alturaDispositivo / 2 - canoBaixo.getHeight() -espacoEntreCanos / 2 );
		batch.draw(passaros[(int)variacao],30, posicaoInicalVertical);

		batch.end();


	}
	

}
