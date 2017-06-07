public class Memory {
    private int[] bytes = new int[65536];

	public Memory(int... bytes) {
		for (int i = 0; i < bytes.length; i++) {
			this.bytes[i] = bytes[i];
		}
	}

	public int get(int index) {
		return bytes[index];
	}

	public void set(int addr, int value) {
		this.bytes[addr] = value;
	}
}