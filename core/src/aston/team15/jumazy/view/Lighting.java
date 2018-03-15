package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

public class Lighting extends Image{
	
	
	private FrameBuffer lightBuffer;
	private TextureRegion lightBufferRegion;
	private SpriteBatch batch;
	private Texture LightImage;
    private float width, height;
    
    public Lighting(float mazeWidth, float mazeHeight) {
        this.width = mazeWidth;
        this.height = mazeHeight;
        
        if (lightBuffer!=null) lightBuffer.dispose();
        lightBuffer = new FrameBuffer(Format.RGBA8888, (int) width,(int) height, false);

        lightBuffer.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(),0,lightBuffer.getHeight() - height,width,height);

        lightBufferRegion.flip(false, false);
        
        lightBuffer.begin();

        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        Gdx.gl.glEnable(GL20.GL_BLEND);     		        
        Gdx.gl.glClearColor(0.3f,0.38f,0.4f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
     			             
        batch.begin();
        
        batch.setColor(0.9f, 0.4f, 0f, 1f);
     
        float tx= (width/2);
        float ty= (height/2);     
        float tw=(128/100f)*96;     
        tx-=(tw/2);
        ty-=(tw/2);     
    
        batch.draw(LightImage, tx,ty,tw,tw,0,0,128,128,false,true);
        batch.end();
        lightBuffer.end();

        Gdx.gl.glBlendFunc(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        batch.begin();
        batch.draw(lightBufferRegion, 0, 0,width,height);               
        batch.end();

    }



        public Lighting getAnimation() {
            return this;
        }

    

}
