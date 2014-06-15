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
	 E�� ������������� � MainGame ���� ������� ��� ���������� ��� ��������� ����� ����� ��� �� ����� ��� ������.
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
	 ������������ ��� ������ getSkor ����� �� ��� ���������������� �������� ��� ����� highscore.
	 */
	public int getSkor() {
		return skor;
	}
	/*
	 ������������ ��� ������ loadImages ��� �� ������� �� ������ ������� ���� �� �� ���������������� ��� ���������.
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
	 ������������ ��� ������ initGame ���� ������� � ������ ��� ���������� ���� ��� ������� timer. ��� ������ ������������� �� ������� ���
	 ������ ��� ��� ��� ������� ��� ������������� ��� ��� ������ �� ���������. 
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
	 ������������ ��� ������ doDrawing ���� ����������� ��� ������� ���� ������ �� �������� �� ������. ��� ������ ����������� �� ������ ��� ��� ����� ��� �����.
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
	 ������������ ��� ������ score ���� ����������� ��� ������ ���� ��� ����� ��� �����.
	 */
	private void score(Graphics g) {

		String msg = "Score";
		Font small = new Font("Helvetica", Font.BOLD, 20);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (board_WIDTH + 100) , board_HEIGHT -350);
		g.drawString("�� ���� ���: " + getSkor() , (board_WIDTH + 100), board_HEIGHT -300);
	}
	/*
	 ������������ ��� ������ checkApple ���� ������ ������� �� �� ������ ������ ���� ����� ��� �� ������ ����������� �� ����, ������������
	 ��� �������� ������� ��� ������, ����������� �� ������� ��� ������ ��� ���������������� ��������� ����� ���� ��� locateApple.
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
	 ������������ ��� ������ move ���� ������� ��� ���� ���� ����� �� ������� �� ������.
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
	 ������������ ��� ������ checkCollision ���� ������� ������� �� �� ������ ���� ����������� ���� ���� ����� ��� ���� ��� ��������� ���
	 �� ���� ����������� ���� ������� ��� OverFrame ��� ������������ �� ���� .
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
	 ������������ ��� ������ locateApple ���� ������� ��� ������� ������������� ��� �� ���������� � �����.
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
	 ������������ ��� ����� TAdapter ��� ���� �������� �� ������� �� ������� ��� ������ � ������� ���� �� ������� �� ������ ����
	 ���������� ��� �������� � �������.
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







