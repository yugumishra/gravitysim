package threeDprojection;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Shape {
	private Color col;
	private ArrayList<V3D> points;
	private V3D velocity;
	
	public Shape() {
		points = new ArrayList<>();
		velocity = new V3D(0,0,0);
	}
	
	public void addPoint(V3D v) {
		points.add(v);
	}
	
	public V3D getPoint(int i) {
		return points.get(i);
	}
	
	public Color getColor() {
		return col;
	}
	
	public void setColor(Color col) {
		this.col = col;
	}
	
	public V3D getVelocity() {
		return velocity;
	}
	
	public void addVelocity(V3D otherVel) {
		//do vector sum of velocities
		velocity.add(otherVel);
	}
	
	public void setVelocity(V3D otherVel) {
		velocity = otherVel;
	}
	
	public void updatePosition() {
		for(V3D v: points) {
			v.add(velocity);
		}
	}
	
	public void setPoint(int i, V3D p) {
		points.set(i, p);
	}
	
	public abstract void drawShape(Graphics2D g, Camera camera);
}
