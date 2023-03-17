package threeDprojection;

public class Camera {
	private V3D position;
	private V3D rotation;
	//w and h represent the size of the camera plane
	private int w;
	private int h;
	//angle represents the angle (in radians) the camera makes with the top half of the projection plane
	//aka tan(angle) = (h/2)/(distance from camera to plane)
	private static final double angle = Math.PI * 0.25;
	
	public Camera(V3D pos, V3D rot, int w, int h) {
		position = pos;
		rotation = rot;
		this.w = w;
		this.h = h;
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
	
	public V3D getPos() {
		return position;
	}
	
	public V3D getRot() {
		return rotation;
	}
	
	public void setPos(V3D pos) {
		position = pos;
	}
	
	public void setRot(V3D rot) {
		rotation = rot;
	}
	
	public static double distFromPlane(int h) {
		return ((double)h/2)/(Math.tan(angle));
	}
	
	public double distFromPlane() {
		return ((double)h/2)/(Math.tan(angle));
	}
	
	public V3D getPointOnPlane(V3D otherPoint) {
		V3D res = new V3D(0,0,0);
		//get difference in otherPoint and camera position
		V3D otherPointCopy = new V3D(otherPoint.getX(), otherPoint.getY(), otherPoint.getZ());
		V3D diff = new V3D(position.getX(), position.getY(), position.getZ());
		otherPointCopy.multiplyScalar(-1);
		diff.add(otherPointCopy);
		//now define the resultant x and y
		double dFP = distFromPlane();
		double x = (diff.getX()/diff.getZ()) * dFP;
		double planeHyp = Math.sqrt(Math.pow(x, 2) + Math.pow(dFP, 2));
		double lookHyp = Math.sqrt(Math.pow(diff.getZ(), 2) + Math.pow(diff.getX(), 2));
		double y = (diff.getY()/lookHyp) * planeHyp;
		res.setX(x);
		res.setY(y);
		return res;
	}
}
