package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

//import aston.team15.jumazy.model.TextureConstants;

public class Button extends Actor {
    Texture texture = new Texture("ButtonNormal.png");
    float actorX = 0, actorY = 0;
    public boolean started = false;
    private String text;
    
    private BitmapFont font12;
    
    public Button(float x, float y, String text, boolean minusSelf){
    	
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("wood_sticks.ttf"));
    	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    	parameter.size = 36;
    	parameter.color = Color.BLACK;
    	font12 = generator.generateFont(parameter); // font size 12 pixels
    	generator.dispose();
    	
    	this.text = text;
    	
    	actorX = x;
    	if(minusSelf)
    		x -= texture.getWidth();
    	
    	actorY = y;
    	
        setBounds(actorX,actorY,texture.getWidth(),texture.getHeight());
        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                started = true;
                SystemManager.setNewSystem(new MenuSystem());
                return true;
            }
        });
    }
        
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,actorX, actorY);

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font12,text);
        float w = glyphLayout.width;
        float h = glyphLayout.height;
        
        font12.draw(batch, text, actorX+((texture.getWidth()-w)/2), actorY+texture.getHeight()-((texture.getHeight()-h)/2));
        
    }
    
    @Override
    public void act(float delta){
        //empty
    }
}