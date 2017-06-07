import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CPUTest {

	private CPU cpu;

	@Test
	public void lda_immediate() {
		init(0xA9, 0xFF);
		cpu.tick();
		a(0xFF);
		n(true);
		z(false);
		pc(2);
	}

	@Test
	public void ldx_immediate() {
		init(0xA2, 0xFF);
		cpu.tick();
		x(0xFF);
		n(true);
		z(false);
		pc(2);
	}

	private void pc(int i) {
		assertEquals(i, cpu.pc());
	}


	private void a(int i) {
		assertEquals(i, cpu.a());
	}

	private void n(boolean b) {
		assertEquals(b, cpu.n());

	}

	private void z(boolean b) {
		assertEquals(b, cpu.z());
	}

	private void x(int i) {
		assertEquals(i, cpu.x());
	}

	private void init(int... bytes) {
		Memory memory = new Memory(bytes);
		cpu = new CPU(memory);
	}
}
