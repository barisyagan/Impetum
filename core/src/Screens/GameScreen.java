package Screens;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.impetum.game.AssetLoader;
import com.impetum.game.CurrentSituation;
import com.impetum.game.ImpetumGame;
import com.impetum.game.Entities.Character.Direction;
import com.impetum.game.Entities.Survivor;
import com.impetum.game.Entities.Zombie;
import com.impetum.game.Networking.Networking;

public class GameScreen implements Screen {
	ImpetumGame impetumGame;
	public static OrthographicCamera camera;
	private OrthographicCamera cameraUI;
	
	Survivor survivor;
	private static Survivor me;
	static LinkedList<Survivor> survivors;
	public static LinkedList<Zombie> zombies = new LinkedList<Zombie>();
	
	Texture backgroundTexture;
	Sprite backgroundSprite;
	public static float backgroundLimitWidth;
	public static float backgroundLimitHeight;
	
	Texture useless;
    
	//for testing purposes
	public GameScreen(ImpetumGame impetumGame) {
		this.impetumGame = impetumGame;
		survivor = new Survivor(0, 0, 0, ImpetumGame.username);
		me = survivor;
	} 

	public GameScreen(ImpetumGame impetumGame, Survivor me,
			LinkedList<Survivor> survivors) {
		
		this.impetumGame = impetumGame;
		GameScreen.me = me;
		GameScreen.survivors = survivors;
		survivor = me;
	}

	@Override
	public void show() { 
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		
		cameraUI = new OrthographicCamera();
		cameraUI.setToOrtho(false);
		
		backgroundTexture = new Texture("Background.png");
		backgroundSprite = new Sprite(backgroundTexture);
		backgroundLimitWidth = backgroundTexture.getWidth()/2;
		backgroundLimitHeight = backgroundTexture.getHeight()/2;
		
		useless = new Texture("impetum-useless.png");
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //while(Networking.executeSinglePacket()) {}
        
        updateCamera(delta);
        
        impetumGame.batch.setProjectionMatrix(camera.combined);
        
       

        getInput();
        
        impetumGame.batch.begin();
        
        impetumGame.batch.disableBlending();
        
        backgroundSprite.draw(impetumGame.batch);
        backgroundSprite.setPosition(0-backgroundSprite.getWidth()/2,
        		                     0-backgroundSprite.getHeight()/2);
        
        impetumGame.batch.draw(useless,
	               0-useless.getWidth()/2,
	               0-useless.getHeight()/2);
        
        impetumGame.batch.enableBlending();
        
        drawZombies();
        
        impetumGame.batch.end();
        
        drawSurvivors();
        
        impetumGame.batch.setProjectionMatrix(cameraUI.combined);
        cameraUI.update();
        
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        drawUI();
        
        Gdx.gl.glDisable(GL20.GL_BLEND);
        
	}
	
	

	float x;
	float y;
	
	private void drawUI() {
		impetumGame.renderer.setProjectionMatrix(cameraUI.combined);
		
        impetumGame.renderer.begin(ShapeRenderer.ShapeType.Filled);
        
        
        // health corner
        impetumGame.renderer.setColor(Color.GRAY);
        impetumGame.renderer.rect(0, Gdx.graphics.getHeight()-55,
                135, 25);
        impetumGame.renderer.setColor(Color.BLACK);
        impetumGame.renderer.rect(0, Gdx.graphics.getHeight()-50,
                130, 25);
        
        // wave
        impetumGame.renderer.setColor(Color.GRAY);
        impetumGame.renderer.circle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()+5,
                50);
        impetumGame.renderer.setColor(Color.BLACK);
        impetumGame.renderer.circle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()+5,
                45);
        
        // zombie count corner
        impetumGame.renderer.setColor(Color.GRAY);
        impetumGame.renderer.circle(Gdx.graphics.getWidth()-70, Gdx.graphics.getHeight()-50,
                50);
        impetumGame.renderer.setColor(Color.WHITE);
        impetumGame.renderer.circle(Gdx.graphics.getWidth()-70, Gdx.graphics.getHeight()-50,
                45);
        impetumGame.renderer.setColor(Color.BLACK);
        impetumGame.renderer.circle(Gdx.graphics.getWidth()-70, Gdx.graphics.getHeight()-50,
                40);
        
        
        
        // players corner
        impetumGame.renderer.setColor(150/255f,150/255f,150/255f,0.5f);
        impetumGame.renderer.rect(0, 0,
                170, 200);
        impetumGame.renderer.setColor(50/255f,50/255f,50/255f,0.5f);
        impetumGame.renderer.rect(0, 10,
                160, 200);
        
        
        impetumGame.renderer.end();
		
		impetumGame.batch.begin();
		
        impetumGame.font.draw(impetumGame.batch, "Health: " + me.getHealth(), 
        		30, 
        		Gdx.graphics.getHeight()-30);
        
        x = impetumGame.font.draw(impetumGame.batch, "Wave " + CurrentSituation.wave, 
        		Gdx.graphics.getWidth()/2 - x/2 + 5, 
        		Gdx.graphics.getHeight()-10).width;
        
        x = impetumGame.font.draw(impetumGame.batch, "Zombies", 
        		Gdx.graphics.getWidth() - x - 50, 
        		Gdx.graphics.getHeight()-35).width;
        impetumGame.font.draw(impetumGame.batch, "" + CurrentSituation.getZombieNumber(), 
        		Gdx.graphics.getWidth() - x/2 - 45, 
        		Gdx.graphics.getHeight()- 55);
        
        
        impetumGame.font.draw(impetumGame.batch, "Players", 
        		50, 
        		200);
        
        impetumGame.font.draw(impetumGame.batch, "" + me.getUserName() + " : " + me.getScore(), 
        		20, 
        		180);
        
        for(int i=0; i<survivors.size();i++){
        	
        	y = impetumGame.font.draw(impetumGame.batch, "" + survivors.get(i).getUserName() 
        			                                   + " : " + survivors.get(i).getScore(), 
            		20, 
            		180 - y - 10).height;
        }
        
        impetumGame.batch.end();
	}

	private void drawSurvivors() {
		survivor.draw(impetumGame);
		
		
		me.draw(impetumGame);
		for (Survivor surv: survivors)
			surv.draw(impetumGame);
		
	}
	
	private void drawZombies() {
		for (Zombie zombie: zombies)
			zombie.draw(impetumGame);
		
	}

	private void updateCamera(float delta) {
		float smooth = 1.5f;
		Vector3 position = camera.position;
		position.x += (survivor.getX() - position.x) * smooth * delta;
		position.y += (survivor.getY() - position.y) * smooth * delta;
		camera.update();
	}

	private void getInput() {
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			if(Gdx.input.isKeyPressed(Keys.UP)){
				//survivor.setCurrentDirection(Direction.NorthWest);
				survivor.move(Direction.NorthWest);
			}else if(Gdx.input.isKeyPressed(Keys.DOWN)){
				//survivor.setCurrentDirection(Direction.SouthWest);
				survivor.move(Direction.SouthWest);
			}else{
				//survivor.setCurrentDirection(Direction.West);
				survivor.move(Direction.West);
			}
			
		}
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
        	if(Gdx.input.isKeyPressed(Keys.UP)){
        		//survivor.setCurrentDirection(Direction.NorthEast);
        		survivor.move(Direction.NorthEast);
    		}else if(Gdx.input.isKeyPressed(Keys.DOWN)){
            	//survivor.setCurrentDirection(Direction.SouthEast);
            	survivor.move(Direction.SouthEast);
    		}else{
    			//survivor.setCurrentDirection(Direction.East);
    			survivor.move(Direction.East);
    		}
            
		}
        if(Gdx.input.isKeyPressed(Keys.UP)){
        	if(Gdx.input.isKeyPressed(Keys.LEFT)){
        		//survivor.setCurrentDirection(Direction.NorthWest);
        		survivor.move(Direction.NorthWest);
        	}else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
        		//survivor.setCurrentDirection(Direction.NorthEast);
        		survivor.move(Direction.NorthEast);
        	}else{
        		//survivor.setCurrentDirection(Direction.North);
        		survivor.move(Direction.North);
        	}
        	
		}
        if(Gdx.input.isKeyPressed(Keys.DOWN)){
        	if(Gdx.input.isKeyPressed(Keys.LEFT)){
        		survivor.move(Direction.SouthWest);
        		//survivor.setCurrentDirection(Direction.SouthWest);
        	}else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
        		//survivor.setCurrentDirection(Direction.SouthEast);
        		survivor.move(Direction.SouthEast);
        	}else{
        		//survivor.setCurrentDirection(Direction.South);
        		survivor.move(Direction.South);
        	}
        	
		}
        
        if(Gdx.input.isKeyPressed(Keys.SPACE)){
        	survivor.attack();
        }
	}
	
	public static Survivor getSurvivor(int id){
		if (me.getID() == id)
			return me;
		for (Survivor survivor : survivors)
			if (survivor.getID() == id)
				return survivor;
		return null;
	}
	

	@Override
	public void resize(int width, int height) { }

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void hide() { }

	@Override
	public void dispose() {
		backgroundTexture.dispose();
		useless.dispose();
	}

	public static Zombie getZombie(byte playerID) {
		for (Zombie zombie : zombies)
			if (zombie.getID() == playerID)
				return zombie;
		return null;
	}

}
