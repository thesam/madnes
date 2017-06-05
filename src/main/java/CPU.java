public class CPU {
	private final Memory memory;

	private int a;  // Actually 8-bit
    private int x;  // Actually 8-bit
    private int y;  // Actually 8-bit
    private int pc; // Actually 16-bit
    private int s;  // Actually 8-bit
    private int p;  // Actually 8-bit

	//Status flags
	private boolean n = false;
	private boolean z = false;

	public CPU(Memory memory) {
		this.memory =memory;
	}

	public void tick() {
		int next = memory.get(pc);
		switch(next) {
			case 0xA9:
				int value = memory.get(pc + 1);
				a = value;
				n(value);
				z(value);
				pc = pc + 2;
		}
	}

	private void z(int value) {
		z = value == 0;
	}

	private void n(int value) {
		z = (value & 0b10000000) > 0;
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
}