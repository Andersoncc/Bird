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
	private Texture canoBaixoMaior;
	private Texture canoAltoMaior;
    private Texture canoAlto3;
    private Texture canoBaixo3;
    private Texture canoAltoMaior4;
    private Texture canoBaixoMaior4;
	private Texture gameOver;
	private BitmapFont texto;
	private BitmapFont mensagem;
	private Circle ponteiroPassaro;
	private Rectangle ponteiroCanoAlto;
	private Rectangle ponteiroCanoBaixo;
	private Rectangle ponteiroCanoAltoMaior;
	private Rectangle ponteiroCanoBaixoMaior;
    private Rectangle ponteiroCanoAlto3;
    private Rectangle ponteiroCanoBaixo3;
    private Rectangle ponteiroCanoAltoMaior4;
    private Rectangle ponteiroCanoBaixoMaior4;
	//private ShapeRenderer shape; //Rendereizar formas para teste

	//*Configuração*//
	private float larguraDispositivo;
	private float alturaDispositivo;
	private float variacao = 0;
	private float velocidadeQueda = 0;
	private float posicaoInicalVertical;
	private float posicaoMovimentoCanoHorizontal;
	private float espacoEntreCanos;
	private float espacoEntreCanosMaior;
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
	    //shape = new ShapeRenderer(); //Instancia do teste
	    aleatorio = new Random();
	    ponteiroPassaro = new Circle();
	    ponteiroCanoAlto = new Rectangle();
	    ponteiroCanoBaixo = new Rectangle();
		ponteiroCanoAltoMaior = new Rectangle();
		ponteiroCanoBaixoMaior = new Rectangle();
		fundo = new Texture("fundo.png");
		canoAlto = new Texture("cano_topo.png");
		canoBaixo = new Texture("cano_baixo.png");
		canoAltoMaior = new Texture("cano_topo_maior.png");
		canoBaixoMaior = new Texture("cano_baixo_maior.png");
        canoAlto3 = new Texture("cano_topo.png");
        canoBaixo3 = new Texture("cano_baixo.png");
        canoAltoMaior4 = new Texture("cano_topo_maior.png");
        canoBaixoMaior4 = new Texture("cano_baixo_maior.png");
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
		espacoEntreCanosMaior = 200;


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

			if (posicaoMovimentoCanoHorizontal < -canoAlto.getWidth() * 4) {
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
					/*Gdx.app.log("veio para reiniciar","reiniciou");*/
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



		batch.draw(canoAltoMaior, posicaoMovimentoCanoHorizontal + canoAlto.getWidth(), alturaDispositivo / 2 + 20  + espacoEntreCanosMaior / 2 + alturaAleatorio);
		batch.draw(canoBaixoMaior, posicaoMovimentoCanoHorizontal + canoBaixo.getWidth(), alturaDispositivo / 2 - 20 - canoBaixoMaior.getHeight() - espacoEntreCanosMaior / 2 + alturaAleatorio);

        batch.draw(canoAlto3, posicaoMovimentoCanoHorizontal + canoAltoMaior.getWidth()*2, alturaDispositivo / 2  + espacoEntreCanos / 2 + alturaAleatorio);
        batch.draw(canoBaixo3, posicaoMovimentoCanoHorizontal + canoBaixoMaior.getWidth()*2, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaAleatorio);

        batch.draw(canoAltoMaior4, posicaoMovimentoCanoHorizontal + canoAlto3.getWidth()*3, alturaDispositivo / 2 +10 + espacoEntreCanosMaior / 2 + alturaAleatorio);
        batch.draw(canoBaixoMaior4, posicaoMovimentoCanoHorizontal + canoBaixo3.getWidth()*3, alturaDispositivo / 2 -10 - canoBaixoMaior.getHeight() - espacoEntreCanosMaior / 2 + alturaAleatorio);


		batch.draw(passaros[(int) variacao], 120, posicaoInicalVertical);
		texto.draw(batch, String.valueOf(pontuacao),larguraDispositivo / 2, alturaDispositivo -50);
		if(estadoJogo ==2){
			batch.draw(gameOver,larguraDispositivo / 2 -gameOver.getWidth()/2, alturaDispositivo / 2);
			mensagem.draw(batch,"Toque para Reiniciar",larguraDispositivo / 2 - 200, alturaDispositivo / 2 - gameOver.getHeight() / 2);
		/*Gdx.app.log("Veio para ela game over","game over");*/
		}

		batch.end();

		ponteiroPassaro = new Circle(120 + passaros[0].getWidth() / 2,posicaoInicalVertical + passaros[0].getHeight() / 2, passaros[0].getWidth() / 2);

		ponteiroCanoAlto = new Rectangle(posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaAleatorio, canoAlto.getWidth(),canoAlto.getHeight());
		ponteiroCanoBaixo = new Rectangle(posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaAleatorio, canoBaixo.getWidth(), canoBaixo.getHeight());

		ponteiroCanoAltoMaior = new Rectangle(posicaoMovimentoCanoHorizontal + canoAlto.getWidth(), alturaDispositivo / 2 + 20 + espacoEntreCanosMaior / 2 + alturaAleatorio, canoAltoMaior.getWidth(), canoAltoMaior.getHeight());
		ponteiroCanoBaixoMaior = new Rectangle(posicaoMovimentoCanoHorizontal + canoBaixo.getWidth(), alturaDispositivo / 2 - 20 - canoBaixoMaior.getHeight() - espacoEntreCanosMaior / 2 + alturaAleatorio, canoBaixoMaior.getWidth(), canoBaixoMaior.getHeight());

		ponteiroCanoAlto3 = new Rectangle(posicaoMovimentoCanoHorizontal + canoAltoMaior.getWidth()*2, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaAleatorio, canoAlto.getWidth(),canoAlto.getHeight());
		ponteiroCanoBaixo3 = new Rectangle(posicaoMovimentoCanoHorizontal + canoBaixoMaior.getWidth()*2, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaAleatorio, canoBaixo.getWidth(), canoBaixo.getHeight());



		ponteiroCanoAltoMaior4 = new Rectangle(posicaoMovimentoCanoHorizontal + canoAlto.getWidth()*3, alturaDispositivo / 2 + 10 + espacoEntreCanosMaior / 2 + alturaAleatorio, canoAltoMaior.getWidth(), canoAltoMaior.getHeight());
		ponteiroCanoBaixoMaior4 = new Rectangle(posicaoMovimentoCanoHorizontal + canoBaixo.getWidth()*3, alturaDispositivo / 2 - 10 - canoBaixoMaior.getHeight() - espacoEntreCanosMaior / 2 + alturaAleatorio, canoBaixoMaior.getWidth(), canoBaixoMaior.getHeight());





		/**desenhando objetos de colisão teste colisao
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.circle(ponteiroPassaro.x, ponteiroPassaro.y,ponteiroPassaro.radius);

		shape.rect(ponteiroCanoAlto.x,ponteiroCanoAlto.y,ponteiroCanoAlto.width,ponteiroCanoAlto.height);
		shape.rect(ponteiroCanoBaixo.x,ponteiroCanoBaixo.y,ponteiroCanoBaixo.width,ponteiroCanoBaixo.height);




		shape.rect(ponteiroCanoAltoMaior.x,ponteiroCanoAltoMaior.y,ponteiroCanoAltoMaior.width,ponteiroCanoAltoMaior.height);
		shape.rect(ponteiroCanoBaixoMaior.x,ponteiroCanoBaixoMaior.y,ponteiroCanoBaixoMaior.width,ponteiroCanoBaixoMaior.height);

		shape.rect(ponteiroCanoAlto3.x,ponteiroCanoAlto3.y,ponteiroCanoAlto3.width,ponteiroCanoAlto3.height);
		shape.rect(ponteiroCanoBaixo3.x,ponteiroCanoBaixo3.y,ponteiroCanoBaixo3.width,ponteiroCanoBaixo3.height);


		shape.rect(ponteiroCanoAltoMaior4.x,ponteiroCanoAltoMaior4.y,ponteiroCanoAltoMaior4.width,ponteiroCanoAltoMaior4.height);
		shape.rect(ponteiroCanoBaixoMaior4.x,ponteiroCanoBaixoMaior4.y,ponteiroCanoBaixoMaior4.width,ponteiroCanoBaixoMaior4.height);

		shape.setColor(Color.RED);
		shape.end();**/

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
