package tetris;


import java.awt.*;
import java.awt.event.*;

import tetris.Pozicija.Smer;

public class Tetris extends Frame implements ActionListener{
	class Dijalog extends Dialog implements ActionListener{
		Dijalog(Frame roditelj){
			
			super(roditelj, "Dijalog", false);
			setLayout(new FlowLayout());
			setSize(200, 140);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					setVisible(false);
				}
			});
			dodajKomponente();
		}
		private void dodajKomponente() {
			Label labela=new Label("Da li ste sigurni?");
			labela.setAlignment(Label.CENTER);
			this.add(labela);
			Button b1=new Button("Da");
			b1.addActionListener(this);
			Button b2=new Button("Ne");
			b2.addActionListener(this);
			this.add(b1);
			this.add(b2);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String kom=e.getActionCommand();
			if(kom.equals("Da")) {
				setVisible(false);
				((Tetris) this.getParent()).zavrsi();
			}
			if(kom.equals("Ne")) {
				setVisible(false);
			}
			
		}
	}
	private Dijalog dijalog;
	private Tabla t;
	TextField t1, t2;
	Label lab, lab1, lab2;
	Tetris(){
		super("Tetris");
		dijalog=new Dijalog(this);
		setLayout(new BorderLayout());
		t=new Tabla(10, 20);
		setBackground(Color.WHITE);
		add(t, BorderLayout.NORTH);
		setSize(300, 600);
		//setResizable(false);
		dodajMeni();
		dodajPanel();
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				t.zaustavi();
				dispose();
			}
			
		});
		t.requestFocus();
		t.setFocusable(true);
		t.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode()==KeyEvent.VK_LEFT) {
					t.pomeriPokrenutu(Smer.LEVO);
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
					t.pomeriPokrenutu(Smer.DESNO);
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN) {
					t.pomeriPokrenutu(Smer.DOLE);
				}
				if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_Z) {
					t.zaustavi();
					dispose();
				}
				if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_N) {
					startAgain();
				}
			}
			
		});
		pack();
		setVisible(true);
		repaint();
	}
	private void startAgain() {
		t.zaustavi();
		remove(t);
		t=new Tabla(10, 20);
		add(t);
		pack();
	}
	public void zavrsi() {
		t.zaustavi();
		dispose();
	}
	public void promeniLab() {
		lab.setText("Poeni: "+t.poeni());
	}
	public void promeniLab1() {
		lab1.setText("Figura: "+t.figuraCnt());
	}
	public void promeniT1() {
		t1.setText(""+t.xPokretne());
	}
	public void promeniT2() {
		t2.setText(""+t.yPokretne());
	}
	private void dodajPanel() {
		Panel p=new Panel(new GridLayout(2,2));
		 lab=new Label("Poeni: "+t.poeni());
		 lab1=new Label("Figura: "+t.figuraCnt());
		 lab2=new Label("x,y: ");
		lab.setText("Poeni: "+t.poeni());
		t1=new TextField();
		t2=new TextField();
		Button b=new Button("Pauziraj");
		b.addActionListener(this);
		p.add(lab);
		p.add(b);
		p.add(lab1);
		Panel p2=new Panel(new FlowLayout());
		p2.add(lab2);
		p2.add(t1);
		p2.add(t2);
		p.add(p2);
		this.add(p, BorderLayout.SOUTH);
		
	}
	public static void main(String []args) {
		new Tetris();
	}
	private void dodajMeni() {
		MenuBar traka=new MenuBar();
		Menu prvi=new Menu("Akcija");
		prvi.add("Nova igra  Ctrl+N");
		prvi.add("Zatvori      Ctrl+C");
		prvi.addActionListener(this);
		traka.add(prvi);
		setMenuBar(traka);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String komanda=e.getActionCommand();
		if(komanda.equals("Zatvori      Ctrl+C")) {
			dijalog.setVisible(true);
		}
		else if(komanda.equals("Nova igra  Ctrl+N")) {
			
			this.startAgain();
		}
		else if (komanda.equals("Pauziraj")) {
			if(t.aktivan())t.pauziraj();
			else {
				t.pokreni();
				t.requestFocus();
			}
		}
		repaint();
	}
}
