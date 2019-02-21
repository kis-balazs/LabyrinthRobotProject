package lrp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class drawPanel extends JComponent {
	private static final long serialVersionUID = 1L;
	
	String cwd = System.getProperty("user.dir");
	
	public final int width = 517;
	public final int height = 590;
	public final int rectDim = 25;
	int myX = 0;
	int myY = 0;
	int nrMoves = 0;
	public boolean won;

	Envir data;

	final Image image;
	final Image finish;

	/*
	 * drawPanel constructor with the data, and the images input
	 */
	public drawPanel(int level) {
		data = new Envir();
		data.read(level);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.image = toolkit.getImage(cwd + "/ExtData/robot.png");
		this.finish = toolkit.getImage(cwd + "/ExtData/finish.png");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.won == false) {

			g.clearRect(0, 0, width, height);
			/* set the moves counter */
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g.drawString("Moves : " + nrMoves, 10, height - 70);

			/* print the labyrinth */
			for (int i = 0; i < data.size; i++) {
				for (int j = 0; j < data.size; j++) {
					/* draw the whole */
					g.setColor(Color.gray);
					g.drawRect(j * rectDim, i * rectDim, rectDim, rectDim);
					/* set up the path based on the inputs */
					if (data.matrix[i][j] == 0)
						g.setColor(Color.black);
					else
						g.setColor(Color.white);

					/* fill with color assoc -> 1 white, 0 black + visible borders */
					g.fillRect(j * rectDim + 1, i * rectDim + 1, rectDim - 1, rectDim - 1);
				}
			}

			/* use the characters */
			g.drawImage(this.finish, 475, 475, rectDim - 1, rectDim - 1, Color.white, this);
			g.drawImage(this.image, myX, myY, rectDim - 1, rectDim - 1, Color.white, this);
			/* checkWin to see if robot reached finish spot */
			checkWin(g);
		}
	}
	public void moveUp() {
		this.nrMoves++;
		if (won == false)
			if (myY - 25 >= 0 && data.matrix[(myY - 25) / 25][myX / 25] != 0)
				myY -= 25;
		repaint();
	}
	public void moveDown() {
		this.nrMoves++;
		if (won == false)
			if (myY + 25 <= 475 && data.matrix[(myY + 25) / 25][myX / 25] != 0)
				myY += 25;
		repaint();
	}
	public void moveLeft() {
		this.nrMoves++;
		if (won == false)
			if (myX - 25 >= 0 && data.matrix[myY / 25][(myX - 25) / 25] != 0)
				myX -= 25;
		repaint();
	}
	public void moveRight() {
		this.nrMoves++;
		if (won == false)
			if (myX + 25 <= 475 && data.matrix[myY / 25][(myX + 25) / 25] != 0)
				myX += 25;
		repaint();
	}
	public void checkWin(Graphics g) {
		if (this.won == false && myX == 475 && myY == 475) {
			this.won = true;
			this.myX = 0;
			this.myY = 0;
			g.clearRect(0, 0, width, height);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
			g.setColor(Color.black);
			g.drawString("You won!", 50, 250);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
			g.drawString(nrMoves + " moves", 150, 350);
			g.drawString("Press space to continue", 20, 400);
		}
	}
}

class BodyClass extends JFrame {
	private static final long serialVersionUID = 1L;

	JFrame myFrame = new JFrame("Game");
	drawPanel draw;

	public BodyClass() {
		draw = new drawPanel(0);
		myFrame.setFocusable(true);
		myFrame.setFocusTraversalKeysEnabled(false);

		myFrame.setResizable(false);
		myFrame.setSize(draw.width, draw.height);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

public class Body extends UI implements KeyListener {

	BodyClass bc = new BodyClass();
	static JMenu menu = new JMenu("Menu");  
    static JMenuItem game = new JMenuItem("Game");
    static JMenuItem scores = new JMenuItem("Scores");  
    static JMenuBar mb = new JMenuBar(); 
    /*
     * create clickers, and for the game start the game, scores, go to scoretable
     */
	public Body(int level) {
		/*set up the menu*/
		menu.add(game);
		menu.add(scores);
		mb.add(menu);
		bc.myFrame.setJMenuBar(mb);
		/*create the the game*/
		bc.myFrame.setIconImage(bc.draw.image);
		bc.draw = new drawPanel(level);
		bc.myFrame.addKeyListener(this);
		bc.myFrame.getContentPane().add(bc.draw);
		bc.myFrame.setVisible(true);
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		// System.out.println("keyPressed");
	}
	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	public void keyReleased(KeyEvent e) {
		if (bc.draw.won == false && e.getKeyCode() == KeyEvent.VK_RIGHT)
			bc.draw.moveRight();
		else if (bc.draw.won == false && e.getKeyCode() == KeyEvent.VK_LEFT)
			bc.draw.moveLeft();
		else if (bc.draw.won == false && e.getKeyCode() == KeyEvent.VK_DOWN)
			bc.draw.moveDown();
		else if (bc.draw.won == false && e.getKeyCode() == KeyEvent.VK_UP)
			bc.draw.moveUp();
		else if (bc.draw.won == true && e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (super.level < super.totalLevels - 1) {
				bc.draw.won = false;
				super.level++;
				super.totalScore += bc.draw.nrMoves;
				//stop the previous page
				this.bc.myFrame.hide();
				
				@SuppressWarnings("unused")
				Body b = new Body(super.level);
				System.out.println(super.totalScore);
			}
			else
			{
				super.totalScore += bc.draw.nrMoves;
				System.out.println(super.totalScore);
				super.stop();
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// System.out.println("keyTyped");
	}
}
