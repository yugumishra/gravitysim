package threeDprojection;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class View extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color bgColor = Color.BLACK;
	private Scene scene;
	private Rectangle2D.Double bg;
	private int w;
	private int h;
	private V3D[] positions = new V3D[3];
	private V3D[] velocities = new V3D[3];
	private double[] masses = new double[2];
	private CSystem system;

	public View(int w, int h) {
		this.w = w;
		this.h = h;
		// create background
		bg = new Rectangle2D.Double(0, 0, w, h);
	}

	public void startScene() {
		// initialize scene
		scene = new Scene(w, h, positions[2], velocities[2]);
		// create celestial system
		system = new CSystem();
		CBody body1 = new CBody(10.0, masses[0] * Math.pow(10, 10), system);
		body1.addPoint(positions[0]);
		body1.setVelocity(velocities[0]);
		CBody body2 = new CBody(10.0, masses[1] * Math.pow(10, 10), system);
		body2.addPoint(positions[1]);
		body2.setVelocity(velocities[1]);
		system.addBody(body1);
		system.addBody(body2);
		// add to scene
		scene.addShape(body1);
		scene.addShape(body2);
	}
	
	public void updateSystem() {
		scene.updateCameraPosRot(positions[2], velocities[2]);
		for(int i = 0; i< positions.length-1; i++) {
			CBody b = system.getBody(i);
			b.setPoint(0, positions[i]);
			b.setVelocity(velocities[i]);
			b.setMass(masses[i] * Math.pow(10, 10));
			scene.setShape(i, b);
			system.setBody(i, b);
		}
		
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(bgColor);
		g2.fill(bg);
		g2.translate(w / 2, h / 2);
		scene.drawScene(g2);
	}

	public void acceptInputs(double[][] inputs) {
		for (int i = 0; i < inputs.length; i++) {
			V3D position = new V3D(inputs[i][0], inputs[i][1], inputs[i][2]);
			V3D velocity = new V3D(inputs[i][3], inputs[i][4], inputs[i][5]);
			positions[i] = position;
			velocities[i] = velocity;
			if(i < 2) {
				double mass = inputs[i][6];
				masses[i] = mass;
			}
		}
	}
	
	public Scene getScene() {
		return scene;
	}
}
