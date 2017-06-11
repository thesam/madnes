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
		this.memory = memory;
	}

	public void tick() {
		int next = memory.get(pc);
		switch (next) {
			case 0x4C:
				jmp_abs();
				break;
			case 0x86:
				stx_zeropage();
				break;
			case 0xA2:
				ldx_immediate();
				break;
			case 0xA5:
				ldaZeroPage();
				break;
			case 0xA9:
				lda_immediate();
				break;
			case 0xAD:
				ldaAbsolute();
				break;
			case 0xB5:
				ldaZeroPageX();
				break;
			case 0xB9:
				ldaAbsoluteY();
				break;
			case 0xBD:
				ldaAbsoluteX();
				break;
			case 0xCA:
				dex();
				break;
			case 0xEA:
				nop();
				break;
			case 0xE8:
				inx();
				break;
			default:
				throw new RuntimeException("Unknown instruction: " + next);

		}
	}

	private void nop() {
		pc++;
	}

	private void inx() {
		setX(x + 1);
		n(x);
		z(x);
		pc++;
	}

	private void dex() {
		setX(x - 1);
		n(x);
		z(x);
		pc++;
	}

	private void stx_zeropage() {
		int addr = memory.get(pc + 1);
		memory.set(addr, x);
		pc = pc + 2;
	}

	private void jmp_abs() {
		int addr_hi = memory.get(pc + 2);
		int addr_lo = memory.get(pc + 1);
		int addr = (addr_hi << 8) | addr_lo;
		pc = addr;
	}

	private void ldaAbsolute() {
		int lsb = memory.get(pc+1);
		int msb = memory.get(pc+2);
		int addr = (msb<<8) | lsb;
		int value = memory.get(addr);
		a = value;
		n(value);
		z(value);
		pc = pc + 3;
	}

	private void ldaAbsoluteX() {
		int lsb = memory.get(pc+1);
		int msb = memory.get(pc+2);
		int addr = (msb<<8) | lsb;
		addr = addr + x;
		int value = memory.get(addr);
		a = value;
		n(value);
		z(value);
		pc = pc + 3;
	}

	private void ldaAbsoluteY() {
		int lsb = memory.get(pc+1);
		int msb = memory.get(pc+2);
		int addr = (msb<<8) | lsb;
		addr = addr + y;
		int value = memory.get(addr);
		a = value;
		n(value);
		z(value);
		pc = pc + 3;
	}

	private void lda_immediate() {
		int value = memory.get(pc + 1);
		a = value;
		n(value);
		z(value);
		pc = pc + 2;
	}

	private void ldaZeroPage() {
		int addr = memory.get(pc+1);
		int value = memory.get(addr);
		a = value;
		n(value);
		z(value);
		pc = pc + 2;
	}

	private void ldaZeroPageX() {
		int addr = memory.get(pc+1) + x;
//		TODO: addr = addr & 0xFF;
		int value = memory.get(addr);
		a = value;
		n(value);
		z(value);
		pc = pc + 2;
	}

	private void ldx_immediate() {
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

	public void setX(int x) {
		this.x = x & 0xff;
	}

	public void setY(int y) {
		this.y = y & 0xff;
	}

	public Memory memory() {
		return memory;
	}
}