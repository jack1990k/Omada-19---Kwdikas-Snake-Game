import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class ScoreFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6419581103872757210L;
	private JLabel label1 = new JLabel("       High Scores:                 ");
	private JLabel label3; 
	private JPanel masterpanel = new JPanel(new BorderLayout());
	private JList<Integer> ScoresList;

	/*
	Εδώ σχεδιάζουμε την οθόνη με τα υψηλότερα σκόρ όπου εκτυπώνονται στην οθόνη μας τα δέκα υψηλότερα σκόρ.
*/
	public ScoreFrame(List<Integer> Hscores, Integer skor) {
		ScoresList =  new JList<Integer>();

		DefaultListModel<Integer> listModel = new DefaultListModel<Integer>();

		for(int i=0; i< Hscores.size() && i<10 ; i++){
			listModel.addElement(Hscores.get(i));
		}

		ScoresList.setModel(listModel);
		JPanel listPanel;

		listPanel = new JPanel();

		ScoresList.setSize(80, 60);
		listPanel.add(ScoresList);

		label1.setForeground(Color.red);
		label3 = new JLabel("  Το τρέχων σκόρ σας είναι: " + skor + "     ");
		label3.setForeground(Color.BLUE);
		ScoresList.setBackground(Color.black);
		ScoresList.setForeground(Color.WHITE);
		masterpanel.setBackground(Color.BLACK);

		masterpanel.add(label1 , BorderLayout.WEST);
		masterpanel.add(ScoresList , BorderLayout.CENTER);
		masterpanel.add(label3 , BorderLayout.EAST);

		this.setContentPane(masterpanel);
		this.setVisible(true);
		this.setSize(380, 220);
		this.setLocation(600, 300);
		this.setTitle("GAME OVER");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
