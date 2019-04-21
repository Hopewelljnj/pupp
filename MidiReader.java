package edu.mccc.cos210.fp.pupp;

import java.io.File;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import edu.mccc.cos210.demo.midi19.MidiControl19;

public class MidiReader implements MetaEventListener, ControllerEventListener {
	private static final String SONG = "data/yup.mid";
	private static final int META_EndofTrack = 47;
	private Synthesizer synth;
	private Sequencer sequencer;
	private Sequence sequence;
	public MidiReader() {
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			Soundbank defsb = synth.getDefaultSoundbank();
			synth.unloadAllInstruments(defsb);
			Soundbank sb = MidiSystem.getSoundbank(new File("data/FluidR3_GM.sf2"));
			synth.loadAllInstruments(sb);
			sequencer = MidiSystem.getSequencer(true);
			sequencer.open();
			sequence = MidiSystem.getSequence(new File(SONG));
			sequencer.setSequence(sequence);
			sequencer.addMetaEventListener(this);
			sequencer.addControllerEventListener(this, new int[] { 7, 16, 17, 18, 19 });
			//sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}
	public static void main(String... args) {
		new MidiControl19();
	}
	public void meta(MetaMessage message) {
		byte[] ba = message.getMessage();
		String s = new String(ba);
		decode(s);
		for (int i = 0; i < ba.length; i++) {
			//printHex(code[i]);
		}
		//if (message.getType() == 127) {
		//}
		if (message.getType() == META_EndofTrack) {
			try {
				Thread.sleep(1000);
				sequencer.close();
			} catch (Exception ex) {
				// ignore
			} finally {
				System.exit(0);
			}
		}
	}
	@Override
	public void controlChange(ShortMessage event) {
		System.out.print("CC:");
		byte[] ba = event.getMessage();
		for (int i = 0; i < ba.length; i++) {
			//printHex(ba[i]);
		}
		System.out.println();
	}
	private void printHex(byte b) {
		String s = Integer.toHexString(b & 0x000000ff);
		switch (s.length()) {
			case 1:
				System.out.print("0" + s);
				break;
			case 2:
				System.out.print(s);
				break;
		}
	}
	private void decode(String s) {
		System.out.println(s);
	}
}