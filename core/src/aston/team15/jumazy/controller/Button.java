package aston.team15.jumazy.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import aston.team15.jumazy.model.TextureConstants;

public class Button extends Actor {
    Texture texture = TextureConstants.getTexture("path");
    float actorX = 0, actorY = 0;
    public boolean started = false;

    public Button(){
        setBounds(actorX,actorY,texture.getWidth(),texture.getHeight());
        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                started = true;
                System.out.println("CLICKED");
                return true;
            }
        });
    }
        
        @Override
        public void draw(Batch batch, float alpha){
            batch.draw(texture,actorX,actorY);
        }
        
        @Override
        public void act(float delta){
            if(started){
                actorX+=5;
            }
        }
    }