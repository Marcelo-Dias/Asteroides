import java.awt.Rectangle;

/*
 * @author Marcelo
 * Bullet class - polygonal shape of a bullet
 */

public class Tiros extends BaseVetorCorpo {
	
	public Rectangle getBounds() {
		Rectangle r;
		r = new Rectangle((int)getX(), (int)getY(), 1, 1);
		return r;
	}
	
	Tiros(){
		setCorpo(new Rectangle(0, 0, 1, 1));
		setVivo(false);
	}
		
}
