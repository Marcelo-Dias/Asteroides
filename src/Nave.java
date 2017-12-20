import java.awt.Polygon;
import java.awt.Rectangle;

/*
 * 
 * @author Marcelo
 * Ship class - polygonal shape of the player's ship
 *
 */

public class Nave extends BaseVetorCorpo {
	
	private int[] naveX = { -6, -3, 0 , 3, 6, 0 };
	private int[] naveY = { 6, 7, 7, 7, 6, -7 };
	
	public Rectangle getBounds() {
		Rectangle r;
		r = new Rectangle((int)getX() - 6, (int)getY() - 6, 12, 12);
		return r;
	}
	
	Nave(){
		setCorpo(new Polygon(naveX, naveY, naveX.length));
		setVivo(true);
	}
	
}
