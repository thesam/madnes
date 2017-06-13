import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CPUTest {

    // Reference: http://www.6502.org/tutorials/6502opcodes.html
    // https://en.wikibooks.org/wiki/6502_Assembly
    // http://www.obelisk.me.uk/index.html
	/* TODO:
	ADC
	AND
	ASL
	BCC
    BCS
    BEQ
    BIT
    BMI
    BNE
    BPL
    BRK
    BVC
    BVS
    CMP
    CPX
    CPY
    DEC
    DEX
    DEY
    EOR
    INC
    INX
    INY
    JMP
    JSR
    LDA
    LDY
    LSR
    NOP
    ORA
    PHA
    PHP
    PLA
    PLP
    ROL
    ROR
    RTI
    RTS
    SBC
    SEI
    STA
    STX
    STY
    TAX
    TAY
    TSX
    TXA
    TXS
    TYA
	 */

	private CPU cpu;

	@Test
    public void clc() {
	    init(0x18);
	    cpu.setC(true);
	    cpu.tick();
	    c(false);
   }

    @Test
    public void cli() {
        init(0x58);
        cpu.setI(true);
        cpu.tick();
        i(false);
    }

    @Test
    public void cld() {
        init(0xD8);
        cpu.setD(true);
        cpu.tick();
        d(false);
    }

    @Test
    public void clv() {
        init(0xB8);
        cpu.setV(true);
        cpu.tick();
        v(false);
    }

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
	public void ldaZeroPage() {
		init(0xA5, 0x02, 0XFF);
		cpu.tick();
		a(0xFF);
		n(true);
		z(false);
		pc(2);
	}

	@Test
	public void ldaZeroPageX() {
		init(0xB5, 0x01, 0xFF);
		cpu.setX(0x01);
		cpu.tick();
		a(0xFF);
		n(true);
		z(false);
		pc(2);
	}

	@Test
	public void ldaAbsolute() {
		init(0xAD, 0x03, 0x00, 0xFF);
		cpu.tick();
		a(0xFF);
		n(true);
		z(false);
		pc(3);
	}

	@Test
	public void ldaAbsoluteX() {
		init(0xBD, 0x01, 0x00, 0xFF);
		cpu.setX(0x02);
		cpu.tick();
		a(0xFF);
		n(true);
		z(false);
		pc(3);
	}

	@Test
	public void ldaAbsoluteY() {
		init(0xB9, 0x01, 0x00, 0xFF);
		cpu.setY(0x02);
		cpu.tick();
		a(0xFF);
		n(true);
		z(false);
		pc(3);
	}

    @Test
    public void ldaIndirectX() {
	    // X is added to the first address (in instruction)
        init(0xA1, 0x01, 0x04,0x00, 0xFF);
        cpu.setX(0x01);
        cpu.tick();
        a(0xFF);
        n(true);
        z(false);
        pc(2);
    }

    @Test
    public void ldaIndirectY() {
	    // Y is added to the second address (from memory)
        init(0xB1, 0x02, 0x01, 0x00, 0xFF);
        cpu.setY(0x03);
        cpu.tick();
        a(0xFF);
        n(true);
        z(false);
        pc(2);
    }

    @Test
    public void ldxImmediate() {
        init(0xA2, 0xFF);
        cpu.tick();
        x(0xFF);
        n(true);
        z(false);
        pc(2);
    }

    @Test
    public void ldxZeroPage() {
        init(0xA6, 0x02, 0XFF);
        cpu.tick();
        x(0xFF);
        n(true);
        z(false);
        pc(2);
    }

    @Test
    public void ldxZeroPageY() {
        init(0xB6, 0x01, 0xFF);
        cpu.setX(0x01);
        cpu.tick();
        x(0xFF);
        n(true);
        z(false);
        pc(2);
    }

    @Test
    public void ldxAbsolute() {
        init(0xAE, 0x03, 0x00, 0xFF);
        cpu.tick();
        x(0xFF);
        n(true);
        z(false);
        pc(3);
    }

    @Test
    public void ldxAbsoluteY() {
        init(0xBE, 0x01, 0x00, 0xFF);
        cpu.setY(0x02);
        cpu.tick();
        x(0xFF);
        n(true);
        z(false);
        pc(3);
    }

    @Test
	public void jmp_absolute() {
		init(0x4C, 0x34, 0x12);
		cpu.tick();
		pc(0x1234);
	}

	@Test
	public void stx_zeropage() {
		init(0x86, 0x11);
		cpu.setX(0x22);
		cpu.tick();
		mem(0x11, 0x22);
		pc(2);
	}

	@Test
	public void inx() {
		init(0xe8);
		cpu.setX(-1);
		cpu.tick();
		x(0);
		n(false);
		z(true);
		pc(1);
	}

	@Test
	public void dex() {
		init(0xca);
		cpu.setX(0);
		cpu.tick();
		x(0xFF);
		n(true);
		z(false);
		pc(1);
	}

	@Test
	public void nop() {
		init(0xea);
		cpu.tick();
		pc(1);
	}

    @Test
    public void sec() {
        init(0x38);
        cpu.setC(false);
        cpu.tick();
        c(true);
    }

    @Test
    public void sed() {
        init(0xF8);
        cpu.setD(false);
        cpu.tick();
        d(true);
    }

    @Test
    public void sei() {
        init(0x78);
        cpu.setI(false);
        cpu.tick();
        i(true);
    }

	private void mem(int addr, int value) {
		assertEquals(value, cpu.memory().get(addr));
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

    private void c(boolean b) {
        assertEquals(b, cpu.c());
    }

    private void i(boolean b) {
        assertEquals(b, cpu.i());
    }

    private void d(boolean b) {
        assertEquals(b,cpu.d());
    }

    private void v(boolean b) {
        assertEquals(b,cpu.v());
    }

    private void init(int... bytes) {
		Memory memory = new Memory(bytes);
		cpu = new CPU(memory);
	}
}
