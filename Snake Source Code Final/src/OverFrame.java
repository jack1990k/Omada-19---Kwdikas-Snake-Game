import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OverFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2435163545825850867L;
	private Integer skor;
	private JLabel label = new JLabel("                              ����� ����������");
	private JLabel label1;
	private JButton restartButton = new JButton("Restart Game");
	private JButton hscoreButton = new JButton("�������� High Scores");
	private JPanel masterpanel = new JPanel(new BorderLayout());
	private JPanel panel1 = new JPanel(new BorderLayout());

	/*
	 ������������ ��� ������ �verFrame ���� ����������� ��� ����� ������ ��� ����������� �� ������� restartButton ��� hscorebutton.
	 */
	public OverFrame(Integer skor) {
		this.skor = skor;
		label1 = new JLabel("                               �� ���� ���: " + skor);

		restartButton.addActionListener(new restartButtonListener());
		hscoreButton.addActionListener(new hscoreButtonListener());

		masterpanel.add(label , BorderLayout.NORTH);
		masterpanel.add(label1 , BorderLayout.CENTER);
		masterpanel.add(panel1 , BorderLayout.SOUTH);

		panel1.add(restartButton,BorderLayout.WEST);
		panel1.add(hscoreButton,BorderLayout.CENTER);

		label.setForeground(Color.WHITE);
		label1.setForeground(Color.WHITE);
		masterpanel.setBackground(Color.BLACK);

		this.setContentPane(masterpanel);
		this.setVisible(true);
		this.setSize(300, 200);
		this.setLocation(600, 300);
		this.setTitle("GAME OVER");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 E�� ������������ �� ������ txt ��� ����� �� ������������� �� ����.
		 */
		try
		{
			File filename = new File("C:\\Snake\\SnakeDataBase.txt");
			filename.getParentFile().mkdirs();
			FileWriter fw = new FileWriter(filename,true);
			fw.write("" + skor);
			fw.write("\n" );//appends the string to the file
			fw.close();
		}
		catch(IOException ioe)
		{
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	/*
	 ��� ������� ��� ������ �� ������������ ��������� �������� �������� ��� Snake.
	 	 */
	class restartButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			JFrame ex = new Snake();
			ex.setVisible(true); 
		}
	}
	/*
	 ��� ���� ������� �� ������ hscore ������� ��� ������ �� ��������� �� �����o txt ���� ������������� �� ����,
	 ��� �� ������� �� ��� ����� ��� ��������� �� ������� sort ���� ���� ����������� ��� ����� �� ��� ����� �� 
	 ��������� ����. ������ ������� ��� ScoreFrame.
	 */
	class hscoreButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			List<Integer> Hscores = new ArrayList<Integer>();    

			try {
				@SuppressWarnings("resource")
				Scanner	fileScanner = new Scanner(new File("C:\\Snake\\SnakeDataBase.txt"));
				while (fileScanner.hasNextInt()){
					Hscores.add(fileScanner.nextInt());
				}
			} catch(IOException ioe)
			{
				System.err.println("IOException: " + ioe.getMessage());
			}

			Collections.sort(Hscores);   	
			Collections.reverse(Hscores);    
			new ScoreFrame(Hscores, skor);
		}
	}
}

