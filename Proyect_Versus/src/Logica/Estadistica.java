package Logica;

import BD.Conexion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Estadistica {
    private int hp;
    private int def;
    private int er;
    private int em;

    public Estadistica(int hp, int def, int er, int em) {
        this.hp = hp;
        this.def = def;
        this.er = er;
        this.em = em;
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

		@Override
	    public String toString() {
	        return "Estadistica{" +
	                "hp=" + hp +
	                ", def=" + def +
	                ", er=" + er +
	                ", em=" + em +
	                '}';
	    }
	}