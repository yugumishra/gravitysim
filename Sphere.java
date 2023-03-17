package threeDprojection;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Sphere extends Shape {
	private double radius;

	public Sphere(double r) {
		radius = r;
		setColor(Color.WHITE);
	}

	public double getRadius() {
		return radius;
	}
	
	@Override
	public void drawShape(Graphics2D g, Camera camera) {
		// TODO Auto-generated method stub
		// rotate the point to account for the camera's rotation
		g.setColor(getColor());
		V3D pointToDraw = getPoint(0);
		V3D point = pointToDraw;
		V3D cRot = camera.getRot();
		V3D unPointX = pointToDraw.rotateXAxis(cRot.getX() * -1);
		V3D unPointXY = unPointX.rotateYAxis(cRot.getY() * -1);
		point = unPointXY.rotateZAxis(cRot.getZ() * -1);
		// use camera method to get the render point
		V3D planePoint = camera.getPointOnPlane(point);
		
		// use similar triangles to find the adjusted radius of the sphere
		V3D diff = new V3D(camera.getPos().getX(), camera.getPos().getY(), camera.getPos().getZ());
		V3D copy = new V3D(pointToDraw.getX(), pointToDraw.getY(), pointToDraw.getZ());
		copy.multiplyScalar(-1);
		diff.add(copy);
		 
		
		double ratio = (radius / diff.getDistance());
		double leg = Math.sqrt(Math.pow(planePoint.getX(), 2) + Math.pow(camera.distFromPlane(), 2));
		double planeHyp = Math.sqrt(Math.pow(leg, 2) + Math.pow(planePoint.getY(), 2));
		double translatedRadius = ratio * planeHyp;
		Ellipse2D.Double circ = new Ellipse2D.Double(planePoint.getX() - translatedRadius / 2,
				planePoint.getY() - translatedRadius / 2, translatedRadius,
				translatedRadius);
		g.fill(circ);
		updatePosition();
	}

}
