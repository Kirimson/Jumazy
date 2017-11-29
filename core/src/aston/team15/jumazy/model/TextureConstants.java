package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class TextureConstants {

	private static final Texture path = new Texture("path.png");
	private static final Texture trap = new Texture("trap.png");
	private static final Texture both = new Texture("wallBoth.png");
	private static final Texture bothTop = new Texture("wallBothTop.png");
	private static final Texture left = new Texture("wallLeft.png");
	private static final Texture leftTop = new Texture("wallLeftTop.png");
	private static final Texture middle = new Texture("wallMiddle.png");
	private static final Texture middleTop = new Texture("wallMiddleTop.png");
	private static final Texture right = new Texture("wallRight.png");
	private static final Texture rightTop = new Texture("wallRightTop.png");
	private static final Texture topBlank = new Texture("wallTopBlank.png");
	private static final Texture topBoth = new Texture("wallTopBoth.png");
	private static final Texture topEnd = new Texture("wallTopEnd.png");
	private static final Texture topLeft = new Texture("wallTopLeft.png");
	private static final Texture topRight = new Texture("wallTopRight.png");
	private static final Texture topStraight = new Texture("wallTopStraight.png");
	private static final Texture topStraightL = new Texture("wallTopStraightLeft.png");
	private static final Texture topStraightR = new Texture("wallTopStraightRight.png");
	private static final Texture outline = new Texture("playeroutline.png");
	private static final Texture player = new Texture("player.png");
	private static final Texture border = new Texture("border.png");
	
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
		}
		
	}
	
}
