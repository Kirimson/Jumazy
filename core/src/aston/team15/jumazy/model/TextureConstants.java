package aston.team15.jumazy.model;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class TextureConstants {

	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public static Texture getTexture(String name) {

		
		
		switch(name) {
		case "path":return path;
		case "trap":return trap;
		case "Both":return both;
		case "BothTop":return bothTop;
		case "Left":return left;
		case "LeftTop":return leftTop;
		case "Middle":return middle;
		case "MiddleTop":return middleTop;
		case "Right":return right;
		case "RightTop":return rightTop;
		case "TopBlank":return topBlank;
		case "TopBoth":return topBoth;
		case "TopEnd":return topEnd;
		case "TopLeft":return topLeft;
		case "TopRight":return topRight;
		case "TopStraight":return topStraight;
		case "TopStraightLeft":return topStraightL;
		case "TopStraightRight":return topStraightR;
		case "outline":return outline;
		case "player":return player;
		case "border":return border;
		default: return path;

		if(textures.get(name) == null) {
			textures.put(name, new Texture(name+".png"));

		}
		return textures.get(name);
		
	}
	}
}
