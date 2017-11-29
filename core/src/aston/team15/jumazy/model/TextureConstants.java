package aston.team15.jumazy.model;

import com.badlogic.gdx.graphics.Texture;

public class TextureConstants {

	private static Texture path = new Texture("path.png");
	private static Texture trap = new Texture("trap.png");
	private static Texture both = new Texture("wallBoth.png");
	private static Texture bothTop = new Texture("wallBothTop.png");
	private static Texture left = new Texture("wallLeft.png");
	private static Texture leftTop = new Texture("wallLeftTop.png");
	private static Texture middle = new Texture("wallMiddle.png");
	private static Texture middleTop = new Texture("wallMiddleTop.png");
	private static Texture right = new Texture("wallRight.png");
	private static Texture rightTop = new Texture("wallRightTop.png");
	private static Texture topBlank = new Texture("wallTopBlank.png");
	private static Texture topBoth = new Texture("wallTopBoth.png");
	private static Texture topEnd = new Texture("wallTopEnd.png");
	private static Texture topLeft = new Texture("wallTopLeft.png");
	private static Texture topRight = new Texture("wallTopRight.png");
	private static Texture topStraight = new Texture("wallTopStraight.png");
	private static Texture topStraightL = new Texture("wallTopStraightLeft.png");
	private static Texture topStraightR = new Texture("wallTopStraightRight.png");
	
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
		case "TopStraightL":return topStraightL;
		case "TopStraightR":return topStraightR;
		default: return path;
		}
		
	}
	
}
