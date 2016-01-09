/* Author: 	Sklug (aka The GenomeWhisperer)
 * 
 * Profile:	github.com/TheGenomeWhisperer/
 * Repository:  ArrayBuilderAndQuestParser
 * 
 * Purpose: 	In Conjunction with the "Rebot" Warcraft program (Rebot.to)
 * 		the questing profiles can be quite tedious to keep track of all
 * 		of the added quest IDs, so I basically created a way to take 
 * 		a quest profile exported as a .txt of any size, parse through it
 * 		and collect all the IDs. For quality of life, it exports
 * 		the data into an easily copyable format to paste straight into
 * 		the official Rebot editor in C# format for quest progress checking 			
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class QuestParserGUI {

	public static void main(String[] args) throws IOException {
		
		// Creating GUI
		final JFrame frame = new JFrame("QuestID Finder");
		
		final JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(false);
		fc.setCurrentDirectory(new File("c:\\"));
		
		// First Button of the Window
		JButton btn1 = new JButton("Please Choose a File to Parse");
		btn1.addActionListener(new ActionListener() {
			
        		public void actionPerformed(ActionEvent e) {
        			fc.showDialog(frame, "Choose txt File to Parse");
        		}
		});
        
        	// Second Button of the Window
        	JButton btn2 = new JButton("Parse the File");
		btn2.addActionListener(new ActionListener() {
 
        	public void actionPerformed(ActionEvent e) {
            		File selectedFile = fc.getSelectedFile();
            		QuestParser QP;
			try {
				QP = new QuestParser(selectedFile);
				QP.toFile();
			} catch (IOException e2) {
				System.out.println("Unable to Find File");
			}
			JOptionPane.showMessageDialog(frame, "File Successfully Parsed!");
        	}
        });
        
        // Running the GUI
        Container pane = frame.getContentPane();
        pane.setLayout(new GridLayout(2, 1, 15, 15));
        pane.add(btn1);
        pane.add(btn2);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null); // Middle of the screen
        frame.setVisible(true);
	}
}
