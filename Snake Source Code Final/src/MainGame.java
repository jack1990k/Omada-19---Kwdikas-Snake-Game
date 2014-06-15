import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainGame extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4065757095800695953L;
	private final int board_WIDTH = 400;
	private final int board_HEIGHT = 400;
	private final int dot_SIZE = 10;
	private final int all_DOTS = 900;
	private final int random_POS = 29;
	private int delay = 100;

	private final int x[] = new int[all_DOTS];
	private final int y[] = new int[all_DOTS];

	private int skor=0;

	private int dots;
	private int apple_x;
	private int apple_y;

	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;

	private Timer timer;
	private Image ball;
	private Image apple;
	private Image head;

	private ArrayList<Integer> highScore = new ArrayList<Integer>();

	/*
	 Eδώ δημιουργείται η MainGame όπου δίνουμε τις διαστάσεις του κεντρικού πανελ καθώς και το χρώμα του φόντου.
	 */ 
	public MainGame() {

		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);

		setPreferredSize(new Dimension(board_WIDTH + 300, board_HEIGHT));
		loadImages();
		initGame();
	}
	/*
	 Δημιουργούμε την μέδοθο getSkor γιατί θα την χρησιμοποιήσουμε αργότερα στο πάνελ highscore.
	 */
	public int getSkor() {
		return skor;
	}
	/*
	 Δημιουργούμε την μέδοθο loadImages για να δώσουμε τα αρχεία εικόνων ώστε να τα χρησιμοποιήσουμε στο πρόγραμμα.
	 */
	private void loadImages() {

		ImageIcon iid = new ImageIcon("ball.png");
		ball = iid.getImage();

		ImageIcon iia = new ImageIcon("apple.png");
		apple = iia.getImage();

		ImageIcon iih = new ImageIcon("head.png");
		head = iih.getImage();
	}
	/*
	 Δημιουργούμε την μέδοθο initGame όπου γίνεται η έναρξη του παιχνιδιού μέσω της μεθόδου timer. Εδώ επίσης αρχικοποιούμε το μέγεθος του
	 φιδιού μας και του δίνουμε τις συντεταγμένες απο τις οποίες θα ξεκινήσει. 
	 */
	private void initGame() {

		dots = 5;

		for (int z = 0; z < dots; z++) {
			x[z] = 50 - z * 10;
			y[z] = 50;
		}

		locateApple();

		timer = new Timer(delay, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	/*
	 Δημιουργούμε την μέδοθο doDrawing όπου σχεδιάζουμε τις γραμμές στις οποίες θα κινείται το φιδάκι. Εδώ επίσης σχεδιάζουμε το φιδάκι και την τροφή στο πάνελ.
	 */
	private void doDrawing(Graphics g) {

		if (inGame) {
			g.drawLine(board_WIDTH+9, board_HEIGHT+20, board_WIDTH+9, board_HEIGHT -400);
			score(g);
			g.drawImage(apple, apple_x, apple_y, this);

			for (int z = 0; z < dots; z++) {
				if (z == 0) 
					g.drawImage(head, x[z], y[z], this);
				else 
					g.drawImage(ball, x[z], y[z], this);	
			}
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		} 
		else 
			score(g);    
	}
	/*
	 Δημιουργούμε την μέδοθο score όπου σχεδιάζουμε τον πίνακα σκόρ στα δεξιά του πάνελ.
	 */
	private void score(Graphics g) {

		String msg = "Score";
		Font small = new Font("Helvetica", Font.BOLD, 20);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (board_WIDTH + 100) , board_HEIGHT -350);
		g.drawString("Το σκόρ σας: " + getSkor() , (board_WIDTH + 100), board_HEIGHT -300);
	}
	/*
	 Δημιουργούμε την μέδοθο checkApple όπου γίνετε έλεγχος αν το φιδάκι έφτασε στην τροφή και αν έφτασε προσθέτουμε το σκόρ, επιταχύνουμε
	 την ταχύτητα κίνησης του φιδιού, μεγαλώνουμε το μέγεθος του φιδιού και επανασχεδιάζουμε καινούρια τροφή μέσω της locateApple.
	 */
	private void checkApple() {

		if ((x[0] == apple_x) && (y[0] == apple_y)) {

			dots++;
			skor= skor + 3;

			timer.stop();
			delay= delay - 2;
			timer = new Timer(delay , this);
			timer.start();

			locateApple();
		}
	}
	/*
	 Δημιουργούμε την μέδοθο move όπου δίνουμε την φορά στην οποία θα κινηθεί το φιδάκι.
	 */
	private void move() {

		for (int z = dots; z > 0; z--) {
			x[z] = x[(z - 1)];
			y[z] = y[(z - 1)];
		}

		if (leftDirection) {
			x[0] -= dot_SIZE;
		}

		if (rightDirection) {
			x[0] += dot_SIZE;
		}

		if (upDirection) {
			y[0] -= dot_SIZE;
		}

		if (downDirection) {
			y[0] += dot_SIZE;
		}
	}
	/*
	 Δημιουργούμε την μέδοθο checkCollision όπου γίνεται έλεγχος άν το φιδάκι έχει συγκρουστεί είτε στον εαυτό του είτε στα τοιχώματα και
	 αν έχει συγκρουστεί τότε καλούμε την OverFrame και αποθηκέυουμε το σκόρ .
	 */
	private void checkCollision() {

		for (int z = dots; z > 0; z--) {

			if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) { 
				inGame = false;
			}
		}

		if (y[0] > board_HEIGHT) {
			inGame = false;
		}

		if (y[0] < 0) { 
			inGame = false;
		}

		if (x[0] > board_WIDTH) {
			inGame = false;
		}

		if (x[0] < 0) {
			inGame = false;
		}

		if (inGame==false){
			new OverFrame(skor);
			highScore.add(skor);
		}
	}
	/*
	 Δημιουργούμε την μέδοθο locateApple όπου δίνουμε τις τυχαίες συντεταγμένες για να σχεδιαστεί η τροφή.
	 */
	private void locateApple() {

		int r = (int) (Math.random() * random_POS);
		apple_x = ((r * dot_SIZE));

		r = (int) (Math.random() * random_POS);
		apple_y = ((r * dot_SIZE));
	}

	public void actionPerformed(ActionEvent e) {

		if (inGame) {

			checkApple();
			checkCollision();
			move();
		}
		repaint();
	}

	/*
	 Δημιουργούμε την κλάση TAdapter απο όπου λαμβάνει το σύστημα τα κουμπιά που πατάει ο χρήστης ώστε να κινηθεί το φιδάκι στην
	 κατεύθυνση που επιθυμεί ο χρήστης.
	 */
	private class TAdapter extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_UP) && (!downDirection)) {
				upDirection = true;
				rightDirection = false;
				leftDirection = false;
			}

			if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
				downDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
		}
	}
}







