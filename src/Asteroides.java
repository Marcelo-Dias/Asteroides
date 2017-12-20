
/**
 * 
 * @author Marcelo
 * Chapter 3 - ASTEROIDS GAME
 *
 */

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

/**
 * Primary class for the game
 */

public class Asteroides extends Applet implements Runnable, KeyListener {

	Thread gameloop;

	BufferedImage backbuffer;

	Graphics2D g2d;

	boolean exibirLimites = false;

	int ASTEROIDES = 20;
	Asteroide[] asteroide = new Asteroide[ASTEROIDES];

	int TIROS = 10;
	Tiros[] tiro = new Tiros[TIROS];
	int tiroCorrente = 0;

	Nave nave = new Nave();

	AffineTransform identidade = new AffineTransform();

	Random rand = new Random();

	/**
	 * applet init event
	 */

	public void init() {

		backbuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();

		nave.setX(320);
		nave.setY(240);

		for (int n = 0; n < TIROS; n++) {
			tiro[n] = new Tiros();
		}

		for (int n = 0; n < ASTEROIDES; n++) {
			asteroide[n] = new Asteroide();
			asteroide[n].setVelocidadeRotacao(rand.nextInt(3) + 1);
			asteroide[n].setX((double) rand.nextInt(600) + 20);
			asteroide[n].setY((double) rand.nextInt(440) + 20);
			asteroide[n].setMudarAngulo(rand.nextInt(360));
			double angulo = asteroide[n].getMudarAngulo() - 90;
			asteroide[n].setVelocidadeX(calculaMudarAnguloX(angulo));
			asteroide[n].setVelocidadeY(calculaMudarAnguloY(angulo));
		}

		addKeyListener(this);

	}

	/**
	 * applet update event to redraw the screen
	 */

	public void update(Graphics g) {

		g2d.setTransform(identidade);

		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);

		g2d.setColor(Color.WHITE);
		g2d.drawString("Nave: " + Math.round(nave.getX()) + "," + Math.round(nave.getY()), 5, 10);
		g2d.drawString("Mudar angluo: " + Math.round(nave.getMudarAngulo()) + 90, 5, 25);
		g2d.drawString("Frente angulo: " + Math.round(nave.getFrenteAngulo()), 5, 40);

		desenharNave();
		desenharTiro();
		desenharAsteroide();

		paint(g);

	}

	/**
	 * drawShip called by applet update event
	 */

	public void desenharNave() {

		g2d.setTransform(identidade);
		g2d.translate(nave.getX(), nave.getY());
		g2d.rotate(Math.toRadians(nave.getFrenteAngulo()));
		g2d.setColor(Color.ORANGE);
		g2d.fill(nave.getCorpo());

	}

	/**
	 * drawBullets called by applet update event
	 */

	public void desenharTiro() {

		for (int n = 0; n < TIROS; n++) {
			if (tiro[n].estaVivo()) {
				g2d.setTransform(identidade);
				g2d.translate(tiro[n].getX(), tiro[n].getY());
				g2d.setColor(Color.MAGENTA);
				g2d.draw(tiro[n].getCorpo());
			}
		}

	}

	/**
	 * drawAsteroids called by applet update event
	 */

	public void desenharAsteroide() {

		for (int n = 0; n < ASTEROIDES; n++) {
			if (asteroide[n].estaVivo()) {
				g2d.setTransform(identidade);
				g2d.translate(asteroide[n].getX(), asteroide[n].getY());
				g2d.rotate(Math.toRadians(asteroide[n].getMudarAngulo()));
				g2d.setColor(Color.DARK_GRAY);
				g2d.fill(asteroide[n].getCorpo());
			}
		}

	}

	/**
	 * applet window repaint event - - draw the back buffer
	 */

	public void paint(Graphics g) {
		g.drawImage(backbuffer, 0, 0, this);
	}

	/**
	 * thread start event - start the game loop running
	 */

	public void start() {

		gameloop = new Thread(this);
		gameloop.start();

	}

	/**
	 * thread run event (game loop)
	 */

	public void run() {
		Thread t = Thread.currentThread();

		while (t == gameloop) {
			try {
				atualizarJogo();

				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			repaint();

		}
	}

	/**
	 * thread stop event
	 */

	public void stop() {

		gameloop = null;

	}

	/**
	 * move and animate the objects in the game
	 */

	private void atualizarJogo() {

		atualizarNave();
		atualizarTiro();
		atualizarAsteroides();
		checarColisao();

	}

	/**
	 * Update the ship position based on velocity
	 */

	public void atualizarNave() {

		nave.incX(nave.getVelocidadeX());

		if (nave.getX() < -10)
			nave.setX(getWidth() + 10);
		else if (nave.getX() > getWidth() + 10)
			nave.setX(-10);

		nave.incY(nave.getVelocidadeY());

		if (nave.getY() < -10)
			nave.setY(getHeight() + 10);
		else if (nave.getY() > getHeight() + 10)
			nave.setY(-10);

	}

	/**
	 * Update the bullets based on velocity
	 */

	public void atualizarTiro() {

		for (int n = 0; n < TIROS; n++) {

			if (tiro[n].estaVivo()) {
				tiro[n].incX(tiro[n].getVelocidadeX());

				if (tiro[n].getX() < 0 || tiro[n].getX() > getWidth()) {
					tiro[n].setVivo(false);
				}

				tiro[n].incY(tiro[n].getVelocidadeY());

				if (tiro[n].getY() < 0 || tiro[n].getY() > getHeight()) {
					tiro[n].setVivo(false);
				}

			}

		}

	}

	/**
	 * Update the asteroids based on velocity
	 */

	public void atualizarAsteroides() {

		for (int n = 0; n < ASTEROIDES; n++) {
			if (asteroide[n].estaVivo()) {
				asteroide[n].incX(asteroide[n].getVelocidadeX());

				if (asteroide[n].getX() < -20)
					asteroide[n].setX(getWidth() + 20);
				else if (asteroide[n].getX() > getWidth() + 20)
					asteroide[n].setX(-20);

				asteroide[n].incY(asteroide[n].getVelocidadeY());

				if (asteroide[n].getY() < -20)
					asteroide[n].setY(getHeight() + 20);
				else if (asteroide[n].getY() > getHeight() + 20)
					asteroide[n].setY(-20);

				asteroide[n].incMudarAngulo(asteroide[n].getRotationVelocity());

				if (asteroide[n].getMudarAngulo() < 0)
					asteroide[n].setMudarAngulo(360 - asteroide[n].getRotationVelocity());
				else if (asteroide[n].getMudarAngulo() > 360)
					asteroide[n].setMudarAngulo(asteroide[n].getRotationVelocity());
			}
		}

	}

	/**
	 * Test asteroids for collisions with ship or bullets
	 */

	public void checarColisao() {

		for (int m = 0; m < ASTEROIDES; m++) {
			if (asteroide[m].estaVivo()) {
				for (int n = 0; n < TIROS; n++) {
					if (tiro[n].estaVivo()) {
						if (asteroide[m].getLimites().contains(tiro[n].getX(), tiro[n].getY())) {
							tiro[n].setVivo(false);
							asteroide[m].setVivo(false);
							continue;
						}
					}
				}

				// check for collision with ship
				if (asteroide[m].getLimites().intersects(nave.getBounds())) {
					asteroide[m].setVivo(false);
					nave.setX(320);
					nave.setY(240);
					nave.setFrenteAngulo(0);
					nave.setVelocidadeX(0);
					nave.setVelocidadeY(0);
					continue;
				}
			}
		}

	}

	/**
	 * Key listener events
	 */

	public void keyReleased(KeyEvent k) {

	}

	public void keyTyped(KeyEvent k) {

	}

	public void keyPressed(KeyEvent k) {
		int keyCode = k.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			nave.incFrenteAngulo(-5);
			
			if (nave.getFrenteAngulo() < 0) nave.setFrenteAngulo(360 - 5);
			break;
			
		case KeyEvent.VK_RIGHT:
			nave.incFrenteAngulo(5);
			
			if (nave.getFrenteAngulo() > 360) nave.setFrenteAngulo(5);
			break;
			
		case KeyEvent.VK_UP:
			nave.setMudarAngulo(nave.getFrenteAngulo() - 90);
			nave.incVelocidadeX(calculaMudarAnguloX(nave.getMudarAngulo()) * 0.1);
			nave.incVelocidadeY(calculaMudarAnguloY(nave.getMudarAngulo()) * 0.1);
			break;
		
		case KeyEvent.VK_CONTROL:
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_SPACE:
			tiroCorrente++;
			if (tiroCorrente > TIROS - 1) tiroCorrente = 0;
			tiro[tiroCorrente].setVivo(true);
			
			tiro[tiroCorrente].setX(nave.getX());
			tiro[tiroCorrente].setY(nave.getY());
			tiro[tiroCorrente].setMudarAngulo(nave.getFrenteAngulo() - 90);
			
			double angle = tiro[tiroCorrente].getMudarAngulo();
			double svx = nave.getVelocidadeX();
			double svy = nave.getVelocidadeY();
			tiro[tiroCorrente].setVelocidadeX(svx + calculaMudarAnguloX(angle) * 2);
			tiro[tiroCorrente].setVelocidadeY(svy + calculaMudarAnguloY(angle) * 2);
			break;
		}
	}
	
	/**
	 * calculate X movement value based on direction angle
	 */
	
	public double calculaMudarAnguloX(double angle) {
		return (double) (Math.cos(angle * Math.PI / 180));
	}
	
	/**
	 * calculate Y movement value based on direction angle
	 */
	
	public double calculaMudarAnguloY(double angle) {
		return (double) (Math.sin(angle * Math.PI / 180));
	}

}