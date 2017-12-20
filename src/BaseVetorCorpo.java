import java.awt.Shape;

/*
 * @author Marcelo
 * Base vector shape class for polygonal shape
 */

public class BaseVetorCorpo {
	private Shape corpo;
	private boolean vivo;
	private double x,y;
	private double velocidadeX, velocidadeY;
	private double mudarAngulo, frenteAngulo;
	
	public Shape getCorpo() {
		return corpo;
	}
	
	public boolean estaVivo() {
		return vivo;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getVelocidadeX() {
		return velocidadeX;
	}

	public double getVelocidadeY() {
		return velocidadeY;
	}

	public double getMudarAngulo() {
		return mudarAngulo;
	}

	public double getFrenteAngulo() {
		return frenteAngulo;
	}

	public void setCorpo(Shape corpo) {
		this.corpo = corpo;
	}

	public void setVivo(boolean estaVivo) {
		this.vivo = estaVivo;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void incX(double i) {
		this.x += i;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void incY(double i) {
		this.y += i;
	}

	public void setVelocidadeX(double velocidadeX) {
		this.velocidadeX = velocidadeX;
	}
	
	public void incVelocidadeX(double i) {
		this.velocidadeX += i;
	}

	public void setVelocidadeY(double velocidadeY) {
		this.velocidadeY = velocidadeY;
	}
	
	public void incVelocidadeY(double i) {
		this.velocidadeY += i;
	}

	public void setMudarAngulo(double mudarAngulo) {
		this.mudarAngulo = mudarAngulo;
	}
	
	public void incMudarAngulo(double i) {
		this.mudarAngulo += i;
	}

	public void setFrenteAngulo(double frenteAngulo) {
		this.frenteAngulo = frenteAngulo;
	}
	
	public void incFrenteAngulo(double i) {
		this.frenteAngulo += i;
	}

	public BaseVetorCorpo() {
		setCorpo(null);
		setVivo(false);
		setX(0.0);
		setY(0.0);
		setVelocidadeX(0.0);
		setVelocidadeY(0.0);
		setMudarAngulo(0.0);
		setFrenteAngulo(0.0);
	}
	
}
