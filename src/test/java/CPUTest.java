import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CPUTest {

	private CPU cpu;

	@Test
	public void lda() {
		init(0xA9,0xFF);
		cpu.tick();
		a(0xFF);
		n(true);
		z(false);
	}



	private void a(int i) {
		assertEquals(i,cpu.a());
	}

	private void n(boolean b) {
		assertEquals(b,cpu.n());

	}

	private void z(boolean b) {
		assertEquals(b,cpu.z());

	}

	private void init(int ... bytes) {
		Memory memory = new Memory(bytes);
		cpu = new CPU(memory);
	}
}
