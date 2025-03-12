package tetris;

public class Pozicija {
	public enum Smer{LEVO, DESNO, DOLE};
	private int rows, cols;
	Pozicija(int rows, int cols){
		this.rows=rows;
		this.cols=cols;
	}
	public int getRows() {
		return rows;
	}
	public int getCols() {
		return cols;
	}
	public void pomeriPoziciju(Smer s) {
		switch(s) {
		case DESNO:
			cols+=1;
			break;
		case DOLE:
			rows+=1;
			break;
		case LEVO:
			cols-=1;
			break;
		default:
			break;
		
		}
	}
	public Pozicija getPozicijaPored(Smer s) {
		Pozicija p=new Pozicija(this.rows, this.cols);
		p.pomeriPoziciju(s);
		return p;
	}
	public boolean jednaka(Pozicija p) {
		return p.rows==this.rows && p.cols==this.cols;
	}
}
