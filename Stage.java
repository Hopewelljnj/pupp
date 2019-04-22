package edu.mccc.cos210.fp.pupp;

import java.awt.FileDialog;
import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import edu.mccc.cos210.ds.Array;
import edu.mccc.cos210.ds.IArray;
import edu.mccc.cos210.ds.IMap;

public class Stage {
	private static final String SONG = "data/yup.mid";
	private static final int META_EndofTrack = 47;
	private static final int META_Data = 127;
	private Synthesizer synth;
	private Sequencer sequencer;
	private Sequence sequence;
	private JFrame jf;
	private ImageIcon ico = new ImageIcon("images/icon.png");
	private FileDialog fd = new FileDialog(jf, "Load File", FileDialog.LOAD);
	public Array<Puppet> puppets = new Array<>(4);
	
	public Stage() {
		createPuppets();
		initswing();
	}
	//Insert real numbers in here once we have an idea of sizes
	private void initswing() {
		jf = new JFrame("Dance Dance Baby!");
		fd.setVisible(true);
		if (fd.getFile() != null) {
			String file = fd.getFile();
			
			if(file.contains(".mid") || file.contains(".midi")) {
				System.out.println("yerp!");
				new MidiReader(this, new File(fd.getDirectory(),fd.getFile()));
			}
		}
	}
	private void createPuppets() {
		for(int i = 0;i < 4; i ++) {
			Limb head = new Limb(0);
			Joint neck = new Joint(100, 100, head);
			Limb torso = new Limb(0, neck);
			Joint lShoulder = new Joint(50,110,torso);
			Joint rShoulder = new Joint(150,110,torso);
			Limb rUpperArm = new Limb(0, rShoulder);
			Limb lUpperArm = new Limb(0, lShoulder);
			Joint rElbow = new Joint(25, 150, rUpperArm);
			Joint lElbow = new Joint(175 , 150, lUpperArm);
			Limb lLowerArm = new Limb(0 , lElbow);
			Limb rLowerArm = new Limb(0, rElbow);
			//hands?
			Hip hip = new Hip(100, 400, torso);
			Limb lUpperLeg = new Limb(0, hip);
			Limb rUpperLeg = new Limb(0, hip);
			hip.setLeftLowerLimb(lUpperLeg);
			hip.setrLowerLimb(rUpperLeg);
			Joint lKnee = new Joint(50, 450, lUpperLeg);
			Joint rKnee = new Joint(150, 450, rUpperLeg);
			Limb lLowerLeg = new Limb(0, lKnee);
			Limb rLowerLeg = new Limb(0, rKnee);
			//feet?
			
			IMap<Datatypes.Joint, Joint> joints = new edu.mccc.cos210.ds.Map<>();
			IMap<Datatypes.Part, ILimb> limbs = new edu.mccc.cos210.ds.Map<>();
			
			joints.put(Datatypes.Joint.NECK, neck);
			joints.put(Datatypes.Joint.LEFT_SHOULDER, lShoulder);
			joints.put(Datatypes.Joint.RIGHT_SHOULDER, rShoulder);
			joints.put(Datatypes.Joint.LEFT_ELBOW, lElbow);
			joints.put(Datatypes.Joint.RIGHT_ELBOW, rElbow);
			joints.put(Datatypes.Joint.HIP, hip);
			joints.put(Datatypes.Joint.LEFT_KNEE, lKnee);
			joints.put(Datatypes.Joint.RIGHT_KNEE, rKnee);
			
			limbs.put(Datatypes.Part.HEAD, head);
			limbs.put(Datatypes.Part.TORSO, torso);
			limbs.put(Datatypes.Part.LEFT_UPPER_ARM, lUpperArm);
			limbs.put(Datatypes.Part.RIGHT_UPPER_ARM, rUpperArm);
			limbs.put(Datatypes.Part.LEFT_LOWER_ARM, lLowerArm);
			limbs.put(Datatypes.Part.RIGHT_LOWER_ARM, rLowerArm);
			limbs.put(Datatypes.Part.LEFT_UPPER_LEG, rUpperLeg);
			limbs.put(Datatypes.Part.RIGHT_UPPER_LEG, rUpperLeg);
			limbs.put(Datatypes.Part.LEFT_LOWER_LEG, lLowerLeg);
			limbs.put(Datatypes.Part.RIGHT_LOWER_LEG, rLowerLeg);
			
			puppets.set(i, new Puppet(("Puppet" + i), limbs, joints));
			
			
		}
	}
	/*public static void main(String... args) {
		new MidiReader();
		new Stage();
	} */
}