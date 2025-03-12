package tetris;

import java.awt.*;

import tetris.Pozicija.Smer;

public abstract class Figura {
	protected Pozicija p;
	protected Color c;
	Figura(Pozicija p, Color c){
		this.p=p;
		this.c=c;
	}
	public abstract void paint(Tabla t);
	
	public Pozicija getGornjiLevi() {
		return p;
	}
	public abstract boolean prostirePreko(Pozicija p);
	
	public abstract boolean mozePomeritiDole(Tabla t);
	
	public abstract boolean mozePomeritiUStranu(Tabla t, Smer s);
	
	public void pomeriFiguru(Tabla t, Smer s) throws Greska {
		if(!mozePomeritiDole(t))throw new Greska();
		if(mozePomeritiUStranu(t, s))this.p=this.p.getPozicijaPored(s);
	}
}
