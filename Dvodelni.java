package tetris;

import java.awt.Color;
import java.awt.Graphics;

import tetris.Pozicija.Smer;

public class Dvodelni extends Figura {

	Dvodelni(Pozicija p, Color c) {
		super(p, c);
	}

	@Override
	public void paint(Tabla t) {
		Graphics g=t.getGraphics();
		g.setColor(this.c);
		g.fillRect(p.getCols()*t.sirina(), p.getRows()*t.visina(),
				2*t.sirina(), t.visina());
	}

	@Override
	public boolean prostirePreko(Pozicija p) {
		return (p.jednaka(this.p) || p.jednaka(this.p.getPozicijaPored(Smer.DESNO)));
	}

	@Override
	public boolean mozePomeritiDole(Tabla t) {
		try {
			return this.p.getPozicijaPored(Smer.DOLE).getRows()<t.getRows() &&
				!t.zauzeta( this.p.getPozicijaPored(Smer.DOLE)) &&
				!t.zauzeta(this.p.getPozicijaPored(Smer.DESNO).getPozicijaPored(Smer.DOLE));
		}
		catch (Greska e) {}
		return false;
	}

	@Override
	public boolean mozePomeritiUStranu(Tabla t, Smer s) {
		if(s==Smer.LEVO) {
			try {
				return this.p.getPozicijaPored(Smer.LEVO).getCols()>=0
						&& ! t.zauzeta(this.p.getPozicijaPored(Smer.LEVO));
			} catch (Greska e) {}
		}
		else if(s==Smer.DESNO) {
			try {
				return this.p.getPozicijaPored(Smer.DESNO).getPozicijaPored(Smer.DESNO).getCols()<t.getCols()
						&& ! t.zauzeta(this.p.getPozicijaPored(Smer.DESNO).getPozicijaPored(Smer.DESNO));
			} catch (Greska e) {}
		}
		return true;
	}

}
