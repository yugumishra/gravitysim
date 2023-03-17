package threeDprojection;

public class CBody extends Sphere {

	private double mass;
	// we need a reference to the current celestial system in order to update
	// velocities
	private CSystem system;

	public CBody(double r, double m, CSystem cs) {
		super(r);
		mass = m;
		system = cs;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	@Override
	public void updatePosition() {
		super.updatePosition();
		updateVelocities();
	}

	public void updateVelocities() {
		for (int i = 0; i < system.getLength(); i++) {
			CBody b = system.getBody(i);
			if (b.equals(this) == false) {
				V3D thisPos = this.getPoint(0);
				V3D diff = new V3D(thisPos.getX(), thisPos.getY(), thisPos.getZ());
				V3D otherPosCopy = new V3D(b.getPoint(0).getX(), b.getPoint(0).getY(), b.getPoint(0).getZ());
				otherPosCopy.multiplyScalar(-1);
				diff.add(otherPosCopy);
				double distance = diff.getDistance();
				// universal law of gravitation is
				// G * m1 * m2
				// ------------- = Fg
				//    r^2
				// where r is distance and Fg is the gravitational force
				// gravitational acceleration can be found easily by just removing m1
				double g = 6.67 * Math.pow(10, -11);
				double gravAccel = (g * b.getMass()) / (distance * distance);
				// we use gravAccel to change the velocities
				// however we must split the gravitational acceleration
				// into its x, y, and z components and then change the velocity accordingly

				// below formulas found using trig
				//no clue why -1 one scalar multiple is needed for all 3 components
				double yComp = -1 * (diff.getY() / distance) * gravAccel;
				double zComp = -1 * (diff.getZ() / distance) * gravAccel;
				double xComp = -1 * (diff.getX() / distance) * gravAccel;
				// now we update the velocities in each direction using the components
				V3D change = new V3D(xComp, yComp, zComp);
				this.addVelocity(change);
			}
		}
	}

	@Override
	public boolean equals(Object other) {
		CBody o = (CBody) other;
		if (o.getMass() == getMass() && o.getRadius() == getRadius() && o.getVelocity().equals(getVelocity()))
			return true;
		return false;
	}
}
