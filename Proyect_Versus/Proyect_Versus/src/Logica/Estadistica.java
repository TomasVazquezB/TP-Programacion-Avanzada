package Logica;

public class Estadistica {
	private int atk;
	private int hp;
    private int def;
    private int er;
    private int em;

    public Estadistica(int atk, int hp, int def, int er, int em) {
		super();
		this.atk = atk;
		this.hp = hp;
		this.def = def;
		this.er = er;
		this.em = em;
	}

	@Override
	public String toString() {
		return "Estadistica [atk=" + atk + ", hp=" + hp + ", def=" + def + ", er=" + er + ", em=" + em + "]";
	}
    	
	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}
	
	    public int getHp() {
			return hp;
		}

		public void setHp(int hp) {
			this.hp = hp;
		}

		public int getDef() {
			return def;
		}

		public void setDef(int def) {
			this.def = def;
		}

		public int getEr() {
			return er;
		}

		public void setEr(int er) {
			this.er = er;
		}

		public int getEm() {
			return em;
		}

		public void setEm(int em) {
			this.em = em;
		}
}