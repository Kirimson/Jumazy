package aston.team15.jumazy.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class UIComponent extends Actor {

	private float actorX;
	private float actorY;
	Texture texture;

	public UIComponent(float x, float y, Texture texture){
    	
    	actorX = x;
    	actorY = y;
    	this.texture = texture;
    	
        setBounds(actorX,actorY,texture.getWidth(),texture.getHeight());
    }
	
	 @Override
	    public void draw(Batch batch, float alpha){
	        batch.draw(texture,actorX, actorY);
	    }

}
