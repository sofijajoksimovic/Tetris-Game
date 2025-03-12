package tetris;

import java.awt.Color;
import java.awt.Graphics;

import tetris.Pozicija.Smer;

public class Jednodelni extends Figura{

	Jednodelni(Pozicija p, Color c) {
		super(p, c);
	}

	@Override
	public void paint(Tabla t) {
		Graphics g=t.getGraphics();
		g.setColor(Color.RED);
		g.fillOval(p.getCols()*t.sirina(), p.getRows()*t.visina(),
				t.sirina(), t.visina());
	}

	@Override
	public boolean prostirePreko(Pozicija p) {
		return this.p.jednaka(p);
	}

	@Override
	public boolean mozePomeritiDole(Tabla t) {
		try {
			return 
					this.p.getPozicijaPored(Smer.DOLE).getRows()<t.getRows() &&
					!t.zauzeta(this.p.getPozicijaPored(Smer.DOLE));
		} catch (Greska e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean mozePomeritiUStranu(Tabla t, Smer s) {
		try {
			return this.p.getPozicijaPored(s).getCols()<t.getCols() && 
					this.p.getPozicijaPored(s).getCols()>=0
					&& !t.zauzeta(this.p.getPozicijaPored(s));
		} catch (Greska e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
