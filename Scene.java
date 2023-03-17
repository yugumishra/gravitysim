package threeDprojection;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Scene {
	private ArrayList<Shape> shapes;
	private Camera camera;
	
	public Scene(int camW, int camH, V3D camPos, V3D camRot) {
		shapes = new ArrayList<>();
		camera = new Camera(camPos,camRot,camW,camH);
	}
	
	public void addShape(Shape s) {
		shapes.add(s);
	}
	
	public void drawScene(Graphics2D g) {
		for(Shape s: shapes) {
			s.drawShape(g, camera);
		}
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setShape(int i, Shape s) {
		shapes.set(i, s);
	}
	
	public void updateCameraPosRot(V3D pos, V3D rot) {
		camera.setPos(pos);
		camera.setRot(rot);
	}
}
