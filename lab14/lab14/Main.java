package lab14;

import lab14lib.*;
import org.junit.Test;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		/** Your code here. */
//		Generator generator = new SineWaveGenerator(200);
//		GeneratorPlayer gp = new GeneratorPlayer(generator);
//		gp.play(1000000);
//		Generator generator = new SineWaveGenerator(200);
//		GeneratorDrawer gd = new GeneratorDrawer(generator);
//		gd.draw(4096);
//		Generator generator = new SineWaveGenerator(200);
//		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
//		gav.drawAndPlay(4096, 1000000);
//		Generator g1 = new SineWaveGenerator(200);
//		Generator g2 = new SineWaveGenerator(201);
//
//		ArrayList<Generator> generators = new ArrayList<Generator>();
//		generators.add(g1);
//		generators.add(g2);
//		MultiGenerator mg = new MultiGenerator(generators);
//
//		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(mg);
//		gav.drawAndPlay(500000, 1000000);

	}

	@Test
	public void testSaw() {
		Generator generator = new SawToothGenerator(512);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
		gav.drawAndPlay(4096, 1000000);
	}

	@Test
	public void testAcc() {
		Generator generator = new AcceleratingSawToothGenerator(200, 1.1);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
		gav.drawAndPlay(4096, 1000000);
	}

	@Test
	public void testStr() {
		StrangeBitwiseGenerator strangeBitwiseGenerator = new StrangeBitwiseGenerator(512);
		GeneratorAudioVisualizer gv = new GeneratorAudioVisualizer(strangeBitwiseGenerator);
		gv.drawAndPlay(4096, 1000000);
	}
} 