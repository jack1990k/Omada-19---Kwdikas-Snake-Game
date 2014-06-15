import javax.swing.JFrame;

public class Snake extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8018797853319667332L;

	public Snake() {
/*
 Eδώ δημιουργείται το κεντρικό πάνελ και καλούμε την MainGame.
 */
		add(new MainGame());        

		setTitle("Snake Game");
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}