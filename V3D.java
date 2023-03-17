package threeDprojection;

public class V3D {
	private double x;
	private double y;
	private double z;
	
	public V3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public void add(V3D otherVel) {
		setX(x + otherVel.getX());
		setY(y + otherVel.getY());
		setZ(z + otherVel.getZ());
	}
	
	public void multiplyScalar(double s) {
		setX(x * s);
		setY(y * s);
		setZ(z * s);
	}
	
	public double getDistance() {
		return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2) + Math.pow(getZ(), 2));
	}
	
	public String toString() {
		return "(" + x + ", " + y + ", "+ z + ")";
	}
	
	public V3D rotateXAxis(double angle) {
		//returns this point rotated by some angle relative to the x-axis
		//to do this multiply this point (as a column vector) by the
		//3d rotation matrix for z-axis
		// 1       0          0
		// 0 cos(angle)  -sin(angle)
		// 0 sin(angle)   cos(angle)
		//when multiplied by this point as a column vector we get
		// x -> x + 0 + 0
		// y -> 0 + ycos(angle) - zsin(angle)
		// z -> 0 + ysin(angle) + zcos(angle)
		//input angle is in degrees, because it is easier to manipulate that way
		//convert the angle into radians first
		//i dont know why the angle is reversed on x-axis but it is so we multilpy the angle by -1
		angle *= -1;
		angle = Math.toRadians(angle);
		V3D rotatedPoint = new V3D(0,0,0);
		rotatedPoint.setX(this.getX());
		double yPrime = this.getY() * Math.cos(angle) - this.getZ() * Math.sin(angle);
		rotatedPoint.setY(yPrime);
		double zPrime = this.getY() * Math.sin(angle) + this.getZ() * Math.cos(angle);
		rotatedPoint.setZ(zPrime);
		return rotatedPoint;
	}
	
	public V3D rotateYAxis(double angle) {
		//returns this point rotated by some angle relative to the y-axis
		//to do this multiply this point (as a column vector) by the 
		//3d rotation matrix for y-axis 
		// cos(angle)    0     -sin(angle)
		//     0         1           0
		// sin(angle)    0      cos(angle)
		//when multiplied by this point as a column vector we get
		// x -> xcos(angle) + 0 - zsin(angle)
		// y -> 0 + y + 0
		// z -> xsin(angle) + 0 + zcos(angle)
		//input angle is in degrees, because it is easier to manipulate that way
		//convert the angle into radians first
		angle = Math.toRadians(angle);
		V3D rotatedPoint = new V3D(0,0,0);
		double xPrime = this.getX() * Math.cos(angle) - this.getZ() * Math.sin(angle);
		rotatedPoint.setX(xPrime);
		rotatedPoint.setY(this.getY());
		double zPrime = this.getX() * Math.sin(angle) + this.getZ() * Math.cos(angle);
		rotatedPoint.setZ(zPrime);
		return rotatedPoint;
	}
	
	public V3D rotateZAxis(double angle) {
		//returns this point rotated by some angle relative to the z-axis
		//to do this multiply this point (as a column vector) by the
		//3d rotation matrix for z-axis
		// cos(angle)  -sin(angle)  0
		// sin(angle)   cos(angle)  0
		//     0            0       1
		//when multiplied by this point as a column vector we get
		// x -> xcos(angle) - ysin(angle) + 0
		// y -> xsin(angle) + ycos(angle) + 0
		// z -> 0 + 0 + z
		//input angle is in degrees, because it is easier to manipulate that way
		//convert the angle into radians first
		angle = Math.toRadians(angle);
		V3D rotatedPoint = new V3D(0,0,0);
		double xPrime = this.getX() * Math.cos(angle) - this.getY() * Math.sin(angle);
		rotatedPoint.setX(xPrime);
		double yPrime = this.getX() * Math.sin(angle) + this.getY() * Math.cos(angle);
		rotatedPoint.setY(yPrime);
		rotatedPoint.setZ(this.getZ());
		return rotatedPoint;
	}
}
