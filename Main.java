package threeDprojection;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main extends JFrame implements ActionListener, ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final private static int w = 1920;
	final private static int h = 1080;
	private JButton pauseButton;
	private boolean playing = false;
	private JPanel uiPanel;
	private JComboBox<String> dropDown;
	private ArrayList<JSlider> sliders;
	private ArrayList<JTextField> inputs;
	private JTextField[] headers;
	private JTextField[] subHeaders;
	private int currentBody;
	private double[][] values;
	private View v;

	public Main() {
		currentBody = 0;
		this.setSize(w, h);
		uiPanel = new JPanel();
		uiPanel.setLayout(null);
		uiPanel.setBounds(0, 0, 300, 950);
		uiPanel.setBackground(Color.BLACK);

		sliders = new ArrayList<JSlider>();
		headers = new JTextField[3];
		subHeaders = new JTextField[3 * 2 + 1];
		inputs = new ArrayList<JTextField>();
		int[][][] bounds = {
				{ { -w / 2, w / 2 }, { -h / 2, h / 2 }, { 0, 1000 }, { -100, 100 }, { -100, 100 }, { -100, 100 },
						{ 0, 25000 } },
				{ { -w / 2, w / 2 }, { -h / 2, h / 2 }, { 0, 1000 }, { -100, 100 }, { -100, 100 }, { -100, 100 },
						{ 0, 25000 } } };
		double[][] vals = { 
				{ 0, 0, Camera.distFromPlane(h), 0, 0, 0, 25000 },
				{ 200, 0, Camera.distFromPlane(h), 0, 9.13099118387, 0, 0.0000000001 }, 
				{ 0, 0, 0, 0, 0, 0}
				};
		values = vals;
		int count = 0;
		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 3; k++) {
				if (j == 2 && k == 1)
					break;
				JSlider s = new JSlider(bounds[currentBody][count][0], bounds[currentBody][count][1],
						(int) (values[currentBody][count]));
				s.addChangeListener(this);
				s.setPaintTicks(true);
				s.setMinorTickSpacing(bounds[currentBody][count][1] / 50);
				s.setPaintTrack(true);
				s.setMajorTickSpacing(bounds[currentBody][count][1]);
				s.setPaintLabels(true);
				s.setBounds(50, (j * 300) + (k * 75) + 175, 200, 75);
				sliders.add(s);
				uiPanel.add(sliders.get(count));

				JTextField t = new JTextField();
				String txt = "";
				switch (k) {
				case 0:
					txt = "x: ";
					break;
				case 1:
					txt = "y: ";
					break;
				case 2:
					txt = "z: ";
					break;
				}
				if (j == 2 && k == 0)
					txt = "m: ";
				t.setText(txt);
				t.setEditable(false);
				t.setBounds(0, (j * 300) + (k * 75) + 175, 50, 75);
				subHeaders[count] = t;
				uiPanel.add(subHeaders[count]);

				JTextField i = new JTextField();
				i.setBounds(250, (j * 300) + (k * 75) + 175, 50, 75);
				i.setText(String.valueOf(values[currentBody][count]));
				inputs.add(i);
				i.addActionListener(this);
				uiPanel.add(inputs.get(count));
				count++;
			}
		}
		count = 0;
		for (int i = 0; i < 3; i++) {
			JTextField t = new JTextField();
			String txt = "";
			switch (i) {
			case 0:
				txt = "Position: ";
				break;
			case 1:
				txt = "Velocity: ";
				break;
			case 2:
				txt = "Mass: ";
				break;
			}
			t.setText(txt);
			t.setBounds(0, 100 + (i * 300), 300, 75);
			t.setEditable(false);
			headers[count] = t;
			uiPanel.add(headers[count]);
			count++;
		}
		String[] options = { "Body 1", "Body 2", "Camera" };
		dropDown = new JComboBox<String>(options);
		dropDown.setBounds(0, 0, 300, 100);
		dropDown.addActionListener(this);
		uiPanel.add(dropDown);

		uiPanel.setVisible(true);
		this.add(uiPanel);

		pauseButton = new JButton();
		pauseButton.setBounds(0, 950, 100, 100);
		pauseButton.setText("Pause/Play");
		pauseButton.addActionListener(this);
		this.add(pauseButton);
		v = new View(w, h);
		sendInputs();
		v.startScene();
		this.add(v);

		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		Timer t = new Timer(10, this);
		t.start();
	}

	public static void main(String[] args) {
		new Main();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(pauseButton)) {
			// this is the pause button and we need to pause the simulation
			playing = !playing;
			uiPanel.setVisible(!playing);
			sendInputs();
			v.updateSystem();
		}
		if (e.getSource().equals(dropDown)) {
			currentBody = dropDown.getSelectedIndex();
			// reupdate the values in textfields and sliders
			if (dropDown.getSelectedIndex() < 2) {
				if(headers[1].getText().equals("Velocity: ") == false) {
					headers[1].setText("Velocity: ");
					headers[2].setVisible(true);
					subHeaders[6].setVisible(true);
					sliders.get(6).setVisible(true);
					inputs.get(6).setVisible(true);
				}
				for (int i = 0; i < sliders.size(); i++) {
					JTextField t = inputs.get(i);
					t.setText(String.valueOf(values[currentBody][i]));
					JSlider s = sliders.get(i);
					s.setValue((int) Math.floor(values[currentBody][i]));
				}
			} else {
				// this means camera
				headers[1].setText("Rotation: ");
				for (int i = 0; i < sliders.size()-1; i++) {
					JTextField t = inputs.get(i);
					t.setText(String.valueOf(values[currentBody][i]));
					JSlider s = sliders.get(i);
					s.setValue((int) Math.floor(values[currentBody][i]));
				}
				headers[2].setVisible(false);
				subHeaders[6].setVisible(false);
				sliders.get(6).setVisible(false);
				inputs.get(6).setVisible(false);
			}
		}
		if (inputs.contains(e.getSource())) {
			// the user entered a value in the textfield
			JTextField t = (JTextField) e.getSource();
			double val = Double.valueOf(t.getText());
			JSlider s = sliders.get(inputs.indexOf(t));
			s.setValue((int) val);
			values[currentBody][inputs.indexOf(t)] = val;
			sendInputs();
			v.updateSystem();
			repaint();
		}
		if (playing)
			repaint();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (sliders.contains(e.getSource())) {
			JSlider s = (JSlider) e.getSource();
			JTextField t = inputs.get(sliders.indexOf(s));
			double diff = Math.abs(Double.valueOf(t.getText()) - (double) (s.getValue()));

			if (diff > 1) {
				t.setText(String.valueOf(s.getValue()));
				inputs.set(sliders.indexOf(s), t);
			}
			values[currentBody][sliders.indexOf(s)] = Double.valueOf(t.getText());
			sendInputs();
			v.updateSystem();
			repaint();
		}
	}

	public void sendInputs() {
		v.acceptInputs(values);
	}
}
