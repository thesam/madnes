public class CPU {
	private final Memory memory;

	private int a = 0;  // Actually 8-bit
    private int x = 0;  // Actually 8-bit
    private int y = 0;  // Actually 8-bit
    private int pc = 0; // Actually 16-bit
    private int s = 0;  // Actually 8-bit
    private int p = 0;  // Actually 8-bit

	//Status flags
	private boolean n = false;
	private boolean z = false;

	public CPU(Memory memory) {
		this.memory =memory;
	}

	public void tick() {
		int next = memory.get(pc);
		switch(next) {
			case 0x4C:
				jmp_abs();
				break;
			case 0xA2:
				ldx_i();
				break;
			case 0xA9:
				lda_i();
				break;
		}
	}

	private void jmp_abs() {
		int addr_hi = memory.get(pc + 2);
		int addr_lo = memory.get(pc + 1);
		int addr = (addr_hi << 8) | addr_lo;
		pc = addr;
	}

	private void lda_i() {
		int value = memory.get(pc + 1);
		a = value;
		n(value);
		z(value);
		pc = pc + 2;
	}

	private void ldx_i() {
		int value = memory.get(pc + 1);
		x = value;
		n(value);
		z(value);
		pc = pc + 2;
	}

	private void z(int value) {
		z = value == 0;
	}

	private void n(int value) {
		n = (value & 0b10000000) > 0;
	}

	public int a() {
		return a;
	}

	public boolean n() {
		return n;
	}

	public boolean z() {
		return z;
	}

	public int pc() {
		return pc;
	}

	public int x() {
		return x;
	}
}