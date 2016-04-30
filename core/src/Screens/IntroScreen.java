package Screens;

import java.io.FileNotFoundException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.badlogic.gdx.video.VideoPlayerDesktop;
import com.impetum.game.ImpetumGame;


public class IntroScreen implements Screen {

	VideoPlayer vplayer;
	FileHandle video;
	
	public Mesh mesh;
	
	PerspectiveCamera camera;
	ImpetumGame impetumGame;
	
	boolean videoLoaded = false;
	
	public IntroScreen(ImpetumGame impetumGame) {
		this.impetumGame = impetumGame;
		vplayer = VideoPlayerCreator.createVideoPlayer();
		vplayer.resize(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height);
		vplayer.setOnVideoSizeListener(new VideoPlayer.VideoSizeListener() {
            @Override
            public void onVideoSize(float v, float v2) {
                videoLoaded = true;
            }
        });
		
		try {
			vplayer.play(Gdx.files.internal("intro.ogv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		

		if ((videoLoaded && !vplayer.render()) || Gdx.input.isKeyPressed(Keys.ESCAPE)){
			vplayer.pause();
			impetumGame.setScreen(new GameScreen(impetumGame));
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	
	
}
