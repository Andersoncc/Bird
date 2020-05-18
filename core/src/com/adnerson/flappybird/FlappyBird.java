package com.adnerson.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture fundo;
	private Texture[] passaros;
	private Texture canoBaixo;
	private Texture canoAlto;
	private Texture gameOver;
	private BitmapFont texto;
	private BitmapFont mensagem;
	private Circle ponteiroPassaro;
	private Rectangle ponteiroCanoAlto;
	private Rectangle ponteiroCanoBaixo;
	//private ShapeRenderer shape; Rendereizar formas para teste

	//*Configuração*//
	private float larguraDispositivo;
	private float alturaDispositivo;
	private float variacao = 0;
	private float velocidadeQueda = 0;
	private float posicaoInicalVertical;
	private float posicaoMovimentoCanoHorizontal;
	private float espacoEntreCanos;
	private float deltaTime;
	private Random aleatorio;
	private float alturaAleatorio;
	private int estadoJogo = 0;//Descrição dos Estados -> 0 Não Iniciado, -> 1 Iniciado, -> 2 Game Over.
	private int pontuacao=0;
	private boolean marcouPonto;

	//Camêra
	private OrthographicCamera camera;
	private Viewport viewPort;
	private final float VIRTUAL_WIDHT = 768 ;
	private final float VIRTUAL_HEIGHT = 1080 ;


	@Override
	public void create() {
	    batch = new SpriteBatch();
	    texto = new BitmapFont();
	    texto.setColor(Color.WHITE);
	    texto.getData().setScale(6);
	    mensagem = new BitmapFont();
	    mensagem.setColor(Color.WHITE);
	    mensagem.getData().setScale(3);
	    //shape = new ShapeRenderer(); Instancia do teste
	    aleatorio = new Random();
	    /*ponteiroPassaro = new Circle();
	    ponteiroCanoAlto = new Rectangle();
	    ponteiroCanoBaixo = new Rectangle();*/
		fundo = new Texture("fundo.png");
		canoAlto = new Texture("cano_topo_maior.png");
		canoBaixo = new Texture("cano_baixo_maior.png");
		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");
		gameOver = new Texture("game_over.png");

		/**Configuração* da camêra*/
		camera = new OrthographicCamera();
		camera.position.set(VIRTUAL_WIDHT / 2, VIRTUAL_HEIGHT / 2, 0);
		viewPort = new StretchViewport(VIRTUAL_WIDHT, VIRTUAL_HEIGHT, camera);


		larguraDispositivo = VIRTUAL_WIDHT;
		alturaDispositivo = VIRTUAL_HEIGHT;
		posicaoInicalVertical = alturaDispositivo / 2;
		posicaoMovimentoCanoHorizontal = larguraDispositivo;
		espacoEntreCanos = 300;


	}

	@Override
	public void render() {
		camera.update();
		//limpar frames anteriores
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		deltaTime = Gdx.graphics.getDeltaTime();
		variacao += deltaTime * 10;
		if (variacao > 2) {
			variacao = 0;
		}
		if(estadoJogo == 0){
			if(Gdx.input.justTouched()){
				estadoJogo = 1;
			}

		}else {//iniciado
			velocidadeQueda++;
			if (posicaoInicalVertical > 0 || velocidadeQueda < 0) {
				posicaoInicalVertical -= velocidadeQueda;
			}


			if(estadoJogo==1){
         	posicaoMovimentoCanoHorizontal -= deltaTime * 200;

         	//Gdx.app.log("varicao","varicao" +Gdx.graphics.getDeltaTime());
			// verifica se o cano saiu da tela

			if (posicaoMovimentoCanoHorizontal < -canoAlto.getWidth()) {
				posicaoMovimentoCanoHorizontal = larguraDispositivo;
				alturaAleatorio = aleatorio.nextInt(400) - 200;
				marcouPonto=false;
			}
				if (Gdx.input.justTouched()) {
					velocidadeQueda = -15;
				}

			//verifica pontuacao
			if (posicaoMovimentoCanoHorizontal < 120){
				if(!marcouPonto){
					pontuacao++;
					marcouPonto = true;
				}
			}
		}
			else{// Reinicar após Game Over
				if(Gdx.input.justTouched()){
					Gdx.app.log("veio para reiniciar","reiniciou");
					estadoJogo = 0;
					pontuacao = 0;
					velocidadeQueda = 0;
					posicaoInicalVertical = alturaDispositivo / 2;
					posicaoMovimentoCanoHorizontal = larguraDispositivo;

				}

			}
		}
	// configurar dados de projeção da camêra
	    batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
		batch.draw(canoAlto, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2  + espacoEntreCanos / 2 + alturaAleatorio);
		batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaAleatorio);
		batch.draw(passaros[(int) variacao], 120, posicaoInicalVertical);
		texto.draw(batch, String.valueOf(pontuacao),larguraDispositivo / 2, alturaDispositivo -50);
		if(estadoJogo ==2){
			batch.draw(gameOver,larguraDispositivo / 2 -gameOver.getWidth()/2, alturaDispositivo / 2);
			mensagem.draw(batch,"Toque para Reiniciar",larguraDispositivo / 2 - 200, alturaDispositivo / 2 - gameOver.getHeight() / 2);
		Gdx.app.log("Veio para ela game over","game over");
		}

		batch.end();

		ponteiroPassaro = new Circle(120 + passaros[0].getWidth() / 2,posicaoInicalVertical + passaros[0].getHeight() / 2, passaros[0].getWidth() / 2);
		ponteiroCanoBaixo = new Rectangle(posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaAleatorio, canoBaixo.getWidth(), canoBaixo.getHeight());
		ponteiroCanoAlto = new Rectangle(posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaAleatorio, canoAlto.getWidth(),canoAlto.getHeight());

		/*desenhando objetos de colisão teste colisao
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.circle(ponteiroPassaro.x, ponteiroPassaro.y,ponteiroPassaro.radius);
		shape.rect(ponteiroCanoBaixo.x,ponteiroCanoBaixo.y,ponteiroCanoBaixo.width,ponteiroCanoBaixo.height);
		shape.rect(ponteiroCanoAlto.x,ponteiroCanoAlto.y,ponteiroCanoAlto.width,ponteiroCanoAlto.height);
		shape.setColor(Color.RED);
		shape.end();*/

		//teste de colisão
		if(Intersector.overlaps(ponteiroPassaro, ponteiroCanoBaixo) ||
				Intersector.overlaps(ponteiroPassaro, ponteiroCanoAlto)
				|| posicaoInicalVertical <=0 || posicaoInicalVertical > alturaDispositivo ){
          //Gdx.app.log("Teste colisão","Colidiu");
			estadoJogo = 2;
		}
	}

	@Override
	public void resize(int width, int height) {
		viewPort.update(width, height);
	}
}
