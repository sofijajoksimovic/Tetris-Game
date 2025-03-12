package tetris;

import java.awt.*;
import java.util.ArrayList;

import tetris.Pozicija.Smer;

public class Tabla extends Canvas implements Runnable {
	private int rows, cols;
	private Figura pokrenuta=null;
	private Figura pripravna=null;
	private ArrayList<Figura>lista;
	private Thread nit;
	private boolean active=false;
	private int poeni, cnt;
	public Tabla(int cols, int rows) {
		this.rows=rows;
		this.cols=cols;
		poeni=0;
		cnt=0;
		lista=new ArrayList<Figura>();
		nit=new Thread(this);
		nit.start();
		active=true;
		setSize(300, 600);
	}
	public int getRows() {
		return rows;
	}
	public int getCols() {
		return cols;
	}
	public void setRows(int r)
	{
		this.rows=r;
	}
	public void setCols(int c) {
		this.cols=c;
	}
	public void dodajFiguru(Figura f) {
		if(!lista.contains(f))lista.add(f);
	}
	public void paint(Graphics g) {
		setBackground(new Color(204, 204, 204));
		g.setColor(Color.BLACK);
		int x=0;
		for(int i=0;i<cols;i++) {
			g.drawLine(x, 0, x, this.getHeight());
			x+=this.getWidth()/cols;
		}
		int y=0;
		for(int j=0;j<rows;j++) {
			g.drawLine(0, y, this.getWidth(), y);
			y+=this.getHeight()/rows;
		}
		for(Figura f:lista) {
			f.paint(this);
		}
	}
	public int sirina() {
		return this.getWidth()/cols;
	}
	public int visina() {
		return this.getHeight()/rows;
	}
	public boolean zauzeta( Pozicija p) throws Greska {
		if(p.getRows()>this.rows || p.getRows()<0
				|| p.getCols()>this.cols || p.getCols()<0) throw new Greska();
		
		for(Figura fig:lista) {
			if(fig.prostirePreko(p)) {
				return true;
			}
		}return false;
	}
	private Figura generisiFiguru() {
		Figura nova=null;
		double col=Math.random();
		double tip=Math.random();
		if(tip<0.5) {
			if(col<0.25) {
				nova=new Jednodelni(new Pozicija(0, 4), Color.RED);
			}
			else if(col<0.5) {
				nova=new Jednodelni(new Pozicija(0, 4), Color.BLUE);
			}
			else if(col<0.75) {
				nova=new Jednodelni(new Pozicija(0, 4), Color.GREEN);
			}
			else {
				nova=new Jednodelni(new Pozicija(0, 4), Color.YELLOW);
			}
		}
		else {
			if(col<0.25) {
				nova=new Dvodelni(new Pozicija(0, 4), Color.RED);
			}
			else if(col<0.5) {
				nova=new Dvodelni(new Pozicija(0, 4), Color.BLUE);
			}
			else if(col<0.75) {
				nova=new Dvodelni(new Pozicija(0, 4), Color.GREEN);
			}
			else {
				nova=new Dvodelni(new Pozicija(0, 4), Color.YELLOW);
			}
			
		}
		return nova;
	}
	public void pomeriPokrenutu(Smer s) {
		try {
			if(pokrenuta==null) {
				pokrenuta=generisiFiguru();
				pripravna=generisiFiguru();
				lista.add(pokrenuta);
				lista.add(pripravna);
				cnt+=2;
				((Tetris) this.getParent()).promeniLab1();
			}
			((Tetris) this.getParent()).promeniT1();
			((Tetris) this.getParent()).promeniT2();
			pokrenuta.pomeriFiguru(this, s);
			repaint();
		} catch (Greska e) {
			pokrenuta=pripravna;
			pripravna=generisiFiguru();
			lista.add(pripravna);
			cnt++;
			((Tetris) this.getParent()).promeniLab1();
		}
	}
	public void pauziraj() {
		active=false;
	}
	public void pokreni() {
		active=true;
		synchronized (this) {
			notifyAll();
		}
		
	}
	public int poeni() {
		return poeni;
	}
	public int xPokretne() {
		return pokrenuta.p.getRows();
	}
	public int yPokretne() {
		return pokrenuta.p.getCols();
	}
	public int figuraCnt() {
		return cnt;
	}
	public boolean aktivan() {
		return active;
	}
	@Override
	public void run() {
		try {
			while(!nit.isInterrupted()) {
				synchronized (this) {
					while(!active) {
						wait();
					}
				}
				Thread.sleep(200);
				ceoRedPopunjen();
				pomeriPokrenutu(Smer.DOLE);
			}
		}
		catch(InterruptedException i) {}
		
	}
	public void zaustavi() {
		nit.interrupt();
	}
	public void ceoRedPopunjen() {
		int popRed=-1;
		for(int i=0;i<this.getRows();i++) {
			popRed=i;
			for(int j=0;j<this.getCols();j++) {
				try {
					if(!zauzeta(new Pozicija(i, j)))popRed=-1;
				} catch (Greska e) {}
			}
			if(popRed!=-1)break;
		}
		if(popRed>-1) {
			ArrayList<Figura>zaBrisanje=new ArrayList<Figura>();
			for(Figura f:lista) {
				if(f.p.getRows()==popRed) {
					zaBrisanje.add(f);
				}
			}
			for(Figura f:zaBrisanje) {
				lista.remove(f);
			}
			poeni+=1;
			((Tetris) this.getParent()).promeniLab();
			
			for(int i=this.getRows()-1;i>=0;i--) {
				for(int j=0;j<this.getCols();j++) {
					for(Figura f: lista) {
						if(f!=pripravna && f.prostirePreko(new Pozicija(i,j)))
						{
							while(f.mozePomeritiDole(this))
								try {
									f.pomeriFiguru(this, Smer.DOLE);
								} catch (Greska e) {
								}
						}
					}
				}
			}
		}
	}
}
