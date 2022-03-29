//Marco Dall'O' Polveni - 4AI

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Es1 extends JFrame{

	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

	int x;
	int y;
	CustomP custP;
	MainPanel mainP;
	MenuRiconoscimenti menuR;
	SimulationOptionPanel menuSimOp;
	JPanel p0;
	Font f = new Font("Impact", Font.PLAIN, 20);

	ImageIcon pos1;
	ImageIcon pos2;
	ImageIcon pos3;

	JLabel lblVincitore;

	ArrayList<Thread> arrT;
	ArrayList<MyThread> arrMyT;
	int n = 15;
	Color tema = new Color(0,234,56);

	String modifica = "HELLO IO SONO UNA MODIFICA";
	
	File f1 = new File("frame1.png");
	File f2 = new File("frame2.png");
	File f3 = new File("frame3.png");

	public Es1() {
		super("Simulazione Atletica");




		pos1 = new ImageIcon(f1.getPath());
		pos2 = new ImageIcon(f2.getPath());
		pos3 = new ImageIcon(f3.getPath());

		pos1 = new ImageIcon( pos1.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		pos2 = new ImageIcon( pos2.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		pos3 = new ImageIcon( pos3.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

		this.setIconImage(pos1.getImage());
		
		x = 10;
		y = 200;
		lblVincitore = new JLabel("Vincitore: ");

		custP = new CustomP();
		mainP = new MainPanel();
		menuR = new MenuRiconoscimenti();
		menuSimOp = new SimulationOptionPanel();
		p0 = new JPanel(new GridLayout(1,1));

		lblVincitore.addComponentListener(new fontAdj(f,2));

		p0.add(mainP);

		this.getContentPane().add(p0);
		this.setSize(1000,700);
		this.setMinimumSize(new Dimension(300,300));
		this.setLocation((screen.width-1000)/2, (screen.height-700)/2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Es1();
	}

	public class MainPanel extends JPanel {

		public MainPanel() {

			this.setLayout(new GridLayout(2,1));

			JPanel p1 = new JPanel(new GridLayout(1,1));
			JPanel p2 = new JPanel(new GridLayout(3,1,10,10));

			CustomButton btnSimula = new CustomButton("Simula");
			CustomButton btnCrediti = new CustomButton("Crediti");
			CustomButton btnEsci = new CustomButton("Esci");


			p2.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));

			JLabel lblTitolo = new JLabel("Simulazione Atleti");
			lblTitolo.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitolo.addComponentListener(new fontAdj(f,4));

			btnSimula.addActionListener(new GestioneSimula());
			btnEsci.addActionListener(new GestioneEsci());
			btnCrediti.addActionListener(new GestioneCrediti());

			this.setBackground(tema);
			p1.setBackground(tema);
			p2.setBackground(tema);
			
			p1.add(lblTitolo);

			p2.add(btnSimula);
			p2.add(btnCrediti);
			p2.add(btnEsci);


			this.add(p1);
			this.add(p2);
		}

		public class GestioneCrediti implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				p0.removeAll();
				p0.add(menuR);
				p0.revalidate();
				p0.repaint();
			}

		}

		public class GestioneEsci implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int x = JOptionPane.showConfirmDialog(null, "Vuoi veramente uscire da questa simulazione bellissima?", "OH NO", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if(x==0)
					System.exit(0);
			}

		}

		public class GestioneSimula implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				p0.removeAll();
				p0.add(menuSimOp);
				p0.revalidate();
				p0.repaint();
				//custP.startThread();
			}

		}

	}

	public class SimulationOptionPanel extends JPanel {

		JTextField txtNAtleti = new JTextField("10");

		int array[] = {5,6,7,8,9,10,11,12,13,14,15};
		int index = 5;

		public SimulationOptionPanel() {

			this.setLayout(new GridLayout(3,1));

			JPanel p1 = new JPanel(new GridLayout(1,1));
			JPanel p2 = new JPanel(new GridLayout(1,3,10,10));
			JPanel p3 = new JPanel(new GridLayout(1,2,10,10));


			JLabel lblAtleti = new JLabel("Numero di atleti");

			CustomButton btnIncrease = new CustomButton(">");
			CustomButton btnDecrease = new CustomButton("<");

			CustomButton btnIndietro = new CustomButton("Indietro");
			CustomButton btnAvanti = new CustomButton("Avanti");

			btnIncrease.setName(">");
			btnDecrease.setName("<");

			btnDecrease.addActionListener(new GestioneIncDec());
			btnIncrease.addActionListener(new GestioneIncDec());

			btnAvanti.addActionListener(new GestioneAvanti());
			btnIndietro.addActionListener(new GestioneIndietro());

			lblAtleti.setHorizontalAlignment(SwingConstants.CENTER);
			lblAtleti.addComponentListener(new fontAdj(f,4));

			txtNAtleti.setHorizontalAlignment(SwingConstants.CENTER);
			txtNAtleti.addComponentListener(new fontAdj(f,3));
			txtNAtleti.setFocusable(false);

			p2.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
			p3.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

			this.setBackground(tema);
			p1.setBackground(tema);
			p2.setBackground(tema);
			p3.setBackground(tema);
			
			p1.add(lblAtleti);

			p2.add(btnDecrease);
			p2.add(txtNAtleti);
			p2.add(btnIncrease);

			p3.add(btnIndietro);
			p3.add(btnAvanti);

			this.add(p1);
			this.add(p2);
			this.add(p3);

		}

		public class GestioneIndietro implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				p0.removeAll();
				p0.add(mainP);
				p0.revalidate();
				p0.repaint();
			}

		}

		public class GestioneAvanti implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				custP.creaThread(Integer.parseInt(txtNAtleti.getText()));
				p0.removeAll();
				p0.add(custP);
				p0.revalidate();
				p0.repaint();


			}

		}


		public class GestioneIncDec implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JButton btn = (JButton) e.getSource();

				if(btn.getName().equals("<")) {
					index--;
					if(index<0)
						index=0;

				}
				else {
					index++;
					if(index>array.length-1)
						index = array.length-1;
				}

				txtNAtleti.setText(""+array[index]);


			}

		}



	}


	public class CustomP extends JPanel {



		GameThread gt;

		Thread t;

		boolean isRunning;

		public CustomP() {

			isRunning = false;

			creaThread(n);


			this.setLayout(new BorderLayout());

			JPanel p1 = new JPanel(new GridLayout(1,3,10,10));

			CustomButton btnSimula = new CustomButton("Simula");
			CustomButton btnIndietro = new CustomButton("Indietro");

			btnSimula.addActionListener(new GestioneSimula());
			btnIndietro.addActionListener(new GestioneIndietro());

			p1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			
			this.setBackground(tema);
			p1.setBackground(tema);
			
			p1.add(btnSimula);
			p1.add(btnIndietro);
			p1.add(lblVincitore);

			this.add(p1, BorderLayout.SOUTH);



			//repaint();
		}

		public void startThread() {
			t.start();
		}

		public void stopThread() {
			t.interrupt();
		}

		public class GestioneIndietro implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				t.interrupt();
				for(int i=0;i<n;i++) {

					arrT.get(i).interrupt();
				}
				creaThread(n);
				//resetBallPosition();

				isRunning=false;
				lblVincitore.setText("Vincitore: ");


				p0.removeAll();
				p0.add(mainP);
				p0.revalidate();
				p0.repaint();
			}

		}


		public void creaThread(int nThread) {

			n = nThread;
			gt = new GameThread();

			t = new Thread(gt);

			arrT = new ArrayList<Thread>();
			arrMyT = new ArrayList<MyThread>();

			for(int i=0;i<n;i++) {
				if(f1.exists() && f2.exists() && f3.exists())
					arrMyT.add(new MyThread(new Ball(30, 10+i*30, "G"+(i+1),true)));
				else
					arrMyT.add(new MyThread(new Ball(30, 10+i*30, "G"+(i+1),false)));

				arrT.add(new Thread(arrMyT.get(i)));
			}

		}


		public class GestioneSimula implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if(isRunning==true) {
					t.interrupt();
					for(int i=0;i<n;i++) {

						arrT.get(i).interrupt();
					}
					creaThread(n);
					//resetBallPosition();
					try {
						Thread.sleep(20);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}


				}
				lblVincitore.setText("Vincitore: ");

				t.start();
				isRunning=true;
			}

		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.drawLine(0, arrMyT.get(0).getBall().y-5, this.getWidth(), arrMyT.get(0).getBall().y-5);
			int goal = (int) (custP.getWidth()*0.9);
			Font f = new Font("Impact", Font.PLAIN, 15);
			g.drawLine(goal, 0, goal, arrMyT.get(arrMyT.size()-1).getBall().y+25);

			for(int i=0;i<n;i++) {
				g.setColor(Color.BLACK);
				int x = arrMyT.get(i).getBall().x;
				int y = arrMyT.get(i).getBall().y;
				g.drawLine(0, y+25, this.getWidth(), y+25);

				//g.fillOval(x, y, 20, 20);

				if(arrMyT.get(i).getBall().getPos()==1) {
					g.drawImage(pos1.getImage(), x, y, 25, 25, this);
				}
				else if(arrMyT.get(i).getBall().getPos()==2) {
					g.drawImage(pos2.getImage(), x, y, 25, 25, this);
				}
				else if(arrMyT.get(i).getBall().getPos()==3){
					g.drawImage(pos3.getImage(), x, y, 25, 25, this);
				}
				else {
					g.fillOval(x, y, 20, 20);
				}

				g.setFont(f);
				g.setColor(Color.RED);
				g.drawString(arrMyT.get(i).getBall().getPlayer(), x-25, y+12);
			}


		}




	}

	public class MenuRiconoscimenti extends JPanel{


		JPanel p1 = new JPanel(new GridLayout(1,2,10,10));

		public MenuRiconoscimenti() {

			this.setLayout(new GridLayout(4,1,10,10));


			JButton btn = new CustomButton("Menù principale");

			Font f1 = new Font("Calibri",Font.BOLD,20);
			Font f2 = new Font("Calibri",Font.ITALIC,20);

			JLabel lblAutore = new JLabel("<html>Autore: <br/><i>Marco Dall'O' Polveni</i></html>");
			JLabel lblImmagini = new JLabel("<html>Immagini: <br/><i>Sempre io</i></html>");
			JLabel lblCopyright = new JLabel("© Marco Dall'O' Polveni - 2022",SwingConstants.RIGHT);

			lblCopyright.setVerticalAlignment(SwingConstants.BOTTOM);

			lblAutore.addComponentListener(new fontAdj(f1,4));
			lblImmagini.addComponentListener(new fontAdj(f1,4));
			lblCopyright.addComponentListener(new fontAdj(f2,6));

			lblAutore.setForeground(Color.WHITE);
			lblCopyright.setForeground(Color.WHITE);
			lblImmagini.setForeground(Color.WHITE);

			this.setBorder(BorderFactory.createEmptyBorder(50, 20, 10, 10));
			p1.setBorder(BorderFactory.createEmptyBorder(10,10,10,50));

			btn.addActionListener(new GestioneMenuPr());
			btn.addComponentListener(new fontAdj(f1,5));

			this.setBackground(tema);
			p1.setBackground(tema);

			p1.add(btn);
			p1.add(lblCopyright);

			this.add(lblAutore);
			this.add(lblImmagini);

			this.add(Box.createGlue());

			this.add(p1);
		}


	}

	public class GestioneMenuPr implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			p0.removeAll();
			p0.add(mainP);
			p0.revalidate();
			p0.repaint();
		}

	}

	public class Ball {

		int x;
		int y;
		String player;

		int pos;

		public Ball(int x, int y, String p, boolean images) {
			this.x = x;
			this.y = y;
			player = p;
			if(images==true)
				pos = 1;
			else
				pos=0;
		}

		public int getPos() {
			return pos;
		}

		public void setPos(int n) {
			pos = n;
		}

		public void setCoords(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public String getPlayer() {
			return player;
		}

	}

	public class MyThread implements Runnable {

		Ball ball;
		int goal;

		public MyThread(Ball b) {
			ball = b;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
			}

			goal = (int) (custP.getWidth()*0.9);

			while(ball.getX()<goal) {
				goal = (int) (custP.getWidth()*0.9)+1;
				int x = ball.getX()+((int) (Math.random()*(10-1+1)+1));

				if(ball.getPos()!=0) {
					if(ball.getPos()==2)
						ball.setPos(3);
					else
						ball.setPos(2);
				}

				if(x>goal)
					x=goal;
				ball.setCoords(x, ball.getY());
				custP.repaint();

				if(ball.player.equals("G1")) {
					System.out.println("Goal 0 "+x + " "+ball.player);

				}

				if(x==goal) {

					for(int i=0;i<n;i++) {
						arrT.get(i).interrupt();
					}

					lblVincitore.setText("Vincitore: "+ball.getPlayer());

				}

				try {
					Thread.sleep((int) (Math.random()*(200-1+1)+1));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block


					//Ok, forse ho capito:
					//Il .interrupt NON ferma il thread, attiva solo
					//una "bandiera" (true/false) che dice che il thread è
					//interrotto, ma poi sta nel resto del programma interrompere effettivamente il thread

					Thread.currentThread().interrupt();		//Questo non interrompe un tubo

					if(ball.player.equals("G1")) {
						System.out.println("INTERROTTO");

					}

					break;		

				}
			}

		}

		public Ball getBall() {
			return ball;
		}

	}

	//	public void resetBallPosition() {
	//
	//		for(int i=0;i<n;i++) {
	//			arrT.get(i).interrupt();
	//		}
	//
	//		for(int i=0;i<n;i++) {
	//			arrMyT.get(i).getBall().setCoords(30, 10+i*30);
	//		}
	//	}

	public class GameThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub


			int goal = 0;

			boolean b = false;

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
				b= true;

			}


			if(b==false) {
				do {

					goal = (int) (custP.getWidth()*0.9);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} while(goal==0);

				for(int i=0;i<n;i++)
					arrT.get(i).start();

			}



		}

	}


	public class CustomButton extends JButton implements ChangeListener, ComponentListener, ActionListener{

		Color colore = new Color(233,233,233);
		Color rollover = new Color(211,211,211);
		Color pressed = new Color(189,190,189);
		Color disabled = new Color(169,169,169);
		double divisore;

		Clip clip;
		AudioInputStream audioInput;

		public CustomButton(String testo) {
			//super(testo); stessa cosa
			this.setText(testo);
			Font f = new Font("Calibri",Font.BOLD, (int) (this.getHeight()/1.5));

			divisore = 1.5;

			this.setBackground(colore);
			this.setFont(f);
			this.setFocusable(false);
			this.setBorder(BorderFactory.createLineBorder(pressed, 1));
			this.setContentAreaFilled(false);
			this.setOpaque(true);
			this.setFocusPainted(false);
			this.setEnabled(true);
			this.addChangeListener(this);
			this.addComponentListener(this);
			this.addActionListener(this);
			this.setMinimumSize(new Dimension(80,50));
			this.setPreferredSize(new Dimension(80,70));
		}


		public CustomButton(String testo, double divisore) {
			//super(testo); stessa cosa
			this.setText(testo);
			Font f = new Font("Calibri",Font.BOLD, (int) (this.getHeight()/1.5));

			this.divisore = divisore;

			this.setBackground(colore);
			this.setFont(f);
			this.setFocusable(false);
			this.setBorder(BorderFactory.createLineBorder(pressed, 1));
			this.setContentAreaFilled(false);
			this.setOpaque(true);
			this.setFocusPainted(false);
			this.setEnabled(true);
			this.addChangeListener(this);
			this.addComponentListener(this);
			this.addActionListener(this);
			this.setMinimumSize(new Dimension(80,50));
			this.setPreferredSize(new Dimension(80,70));
		}


		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub

			if(this.getModel().isPressed()) 
				this.setBackground(rollover);
			else if(this.getModel().isRollover())
				this.setBackground(pressed);
			else if( !this.isEnabled()) 
				this.setBackground(disabled);
			else 
				this.setBackground(colore);

		}

		@Override
		public void componentResized(ComponentEvent e) {
			// TODO Auto-generated method stub

			int size = ((int) (this.getHeight()/divisore));
			if(size>65) {
				size=65;
			}
			this.setFont(new Font("Calibri",Font.BOLD, size ) );
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void componentShown(ComponentEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void componentHidden(ComponentEvent e) {
			// TODO Auto-generated method stub

		}


		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			try {
				clip = AudioSystem.getClip();
				audioInput = AudioSystem.getAudioInputStream(new File("Button.wav"));
				clip.open(audioInput);
				clip.start();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
			} catch (UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			}



		}



	}


	public class fontAdj implements ComponentListener{

		Font f;
		double div;

		public fontAdj(Font f, double div) {
			this.f = f;
			this.div = div;
		}

		@Override
		public void componentResized(ComponentEvent e) {
			// TODO Auto-generated method stub

			e.getComponent().setFont(new Font(f.getFamily(), f.getStyle(), (int) (e.getComponent().getHeight()/div)));


		}

		@Override
		public void componentMoved(ComponentEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void componentShown(ComponentEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void componentHidden(ComponentEvent e) {
			// TODO Auto-generated method stub

		}

	}


}
