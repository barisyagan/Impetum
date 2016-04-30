package Screens;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.impetum.game.ImpetumGame;
import com.impetum.game.Entities.Survivor;
import com.impetum.game.Networking.Networking;

public class MainMenuScreen implements Screen {
	
	ImpetumGame impetumGame;
	OrthographicCamera camera;
	
	Stage stage;
	
	TextField textField;
	TextFieldStyle textFieldStyle;
	
	InputAdapter inputAdapter;
	InputMultiplexer inputMultiplexer;
	
	private static Survivor me;
	private static LinkedList<Survivor> survivors = new LinkedList<Survivor>();
	private boolean initListener = false;
	
	public MainMenuScreen(ImpetumGame impetumGame){
		this.impetumGame = impetumGame;
		//camera = new OrthographicCamera();
		//camera.setToOrtho(false);
		
		stage = new Stage();
		//Gdx.input.setInputProcessor(stage);
		
		inputAdapter = new InputAdapter(){
			@Override
			public boolean keyDown(int keyCode){
				if(keyCode == Input.Keys.ENTER){
					setUsername(textField.getText());
					connectToServer();
					return true;
				}
				return false;
			}
		};
		
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(inputAdapter);
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		BitmapFont font = new BitmapFont();
		
		textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = font;
		textFieldStyle.fontColor = Color.WHITE;
		
		textField = new TextField("", textFieldStyle);
		textField.setWidth(100);
		textField.setHeight(50);
		textField.pack();
		textField.setX(Gdx.graphics.getWidth()/2-textField.getWidth()/2);
		textField.setY(Gdx.graphics.getHeight()/2);
		
		stage.addActor(textField);
		textField.pack();
		stage.setKeyboardFocus(textField);
		
	}

	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //camera.update();
        //impetumGame.batch.setProjectionMatrix(camera.combined);
        if (!initListener && Networking.isConnected()){
        	Networking.initializePacketListener();
        	initListener = true;
        	Networking.initializeUsername(ImpetumGame.username);
        }
        
        if (Networking.gameStarted())
        	impetumGame.setScreen(new GameScreen(impetumGame, me, survivors));
        Networking.executeSinglePacket();
        
        impetumGame.batch.begin();
        impetumGame.font.draw(impetumGame.batch, "IMPETUM", 
        		Gdx.graphics.getWidth()/2-40, 
        		Gdx.graphics.getHeight()-50);
        impetumGame.batch.end();
        
        impetumGame.batch.begin();
        impetumGame.font.draw(impetumGame.batch, "ENTER NAME", 
        		Gdx.graphics.getWidth()/2-50, 
        		Gdx.graphics.getHeight()-250);
        impetumGame.batch.end();
        
        impetumGame.renderer.setColor(Color.WHITE);
        impetumGame.renderer.begin(ShapeRenderer.ShapeType.Filled);
        impetumGame.renderer.rect(textField.getX() - 2, textField.getY() - 2,
                textField.getWidth() + 4, 2);
        impetumGame.renderer.end();
        
        stage.act(delta);
        stage.draw();
	}
	
	private void connectToServer() {
		if(!Networking.isConnected()){
			Networking.connectServer();		
		}
	}
	
	private void setUsername(String username) {
		ImpetumGame.username = username;
	}
	
	public static void setMe(Survivor survivor){
		me = survivor;
	}
	
	public static Survivor getMe(){
		return me;
	}
	
	public static void addSurvivor(Survivor survivor){
		survivors.add(survivor);
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
	
}
