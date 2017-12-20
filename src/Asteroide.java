import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * @author Marcelo
 * Asteroid class - for polygonal asteroids shapes
 */

public class Asteroide extends BaseVetorCorpo {
	
	private int[] asteroideX = {-20, -13, 0, 20, 22, 20, 12, 2, -10, -22, -16};
	private int[] asteroideY = {20, 23, 17, 20, 16, -20, -22, -14, -17, -20, -5};
	
	protected double velocidadeRotacao;
	
	public double getRotationVelocity() {
		return velocidadeRotacao;
	}
	
	public void setVelocidadeRotacao(double v) {
		velocidadeRotacao = v;
	}
	
	public Rectangle getLimites() {
		Rectangle r;
		r = new Rectangle((int)getX() - 20, (int)getY() - 20, 40, 40);
		return r;
	}
	
	public Asteroide() {
		setCorpo(new Polygon(asteroideX, asteroideY, asteroideX.length));
		setVivo(true);
		setVelocidadeRotacao(0.0);
	}
	
}
