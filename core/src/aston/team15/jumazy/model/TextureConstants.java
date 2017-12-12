package aston.team15.jumazy.model;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class TextureConstants {

	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public static Texture getTexture(String name) {

		if(textures.get(name) == null) {
			textures.put(name, new Texture(name+".png"));
		}
		return textures.get(name);
		
	}
}
