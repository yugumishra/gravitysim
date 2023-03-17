package threeDprojection;

import java.util.ArrayList;

public class CSystem {
	private ArrayList<CBody> bodies;
	
	public CSystem() {
		bodies = new ArrayList<>();
	}
	
	public void addBody(CBody b) {
		bodies.add(b);
	}
	
	public CBody getBody(int i) {
		return bodies.get(i);
	}
	
	public int getLength() {
		return bodies.size();
	}
	
	public void setBody(int i, CBody b) {
		bodies.set(i, b);
	}
}
