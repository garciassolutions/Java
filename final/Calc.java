// Calculator final.
// Written by Anthony Garcia

import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.regex.*;
import java.util.Formatter;
import javax.swing.*;

public class Calc{
	static Integer i;
	static String mem_set = null;
	static JPanel pan[] = new JPanel[4];
	static JButton btn[] = new JButton[24];
	static TextField memory = new TextField(1);
	static TextField query = new TextField(25);
	
	public static void main(String args[]){
		JFrame win = new JFrame("nueb calculator");
		Toolkit Tk = Toolkit.getDefaultToolkit();
		Dimension screen = Tk.getScreenSize();
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu infoMenu = new JMenu("Info");
		JMenuItem aboutAction = new JMenuItem("* About");
		aboutAction.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					menu_clicked(e.getActionCommand());
				}
			}
		);
		JMenuItem quitAction = new JMenuItem(" * Quit");
		quitAction.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					System.exit(0);
				}
			}
		);	
		infoMenu.add(aboutAction);
		fileMenu.add(quitAction);
		menuBar.add(fileMenu);
		menuBar.add(infoMenu);
		win.setJMenuBar(menuBar);
		memory.setEditable(false);
		pan[0] = new JPanel(new GridLayout(4, 3));
		pan[1] = new JPanel(new GridLayout(4, 2));
		pan[2] = new JPanel();
		pan[3] = new JPanel(new GridLayout(4, 1));
		win.setSize(398, 264);
		win.setLocation(((screen.height-264)/2), ((screen.width-398)/2));
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Lets set our buttons up!
		for(i=0;i<10;i++){
			btn[i] = new JButton(i.toString());
			btn[i].setForeground(Color.BLUE);
			btn[i].addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						calculate(e.getActionCommand());
					}
				}
			);
		}
		btn[10] = new JButton(".");
		btn[11] = new JButton("-/+");
		btn[10].setForeground(Color.BLUE);
		btn[10].addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					calculate(e.getActionCommand());
				}
			}
		);
		btn[11].setForeground(Color.BLUE);
		btn[11].addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					calculate(e.getActionCommand());
				}
			}
		);
		pan[0].add(btn[7]);
		pan[0].add(btn[8]);
		pan[0].add(btn[9]);
		pan[0].add(btn[4]);
		pan[0].add(btn[5]);
		pan[0].add(btn[6]);
		pan[0].add(btn[1]);
		pan[0].add(btn[2]);
		pan[0].add(btn[3]);
		pan[0].add(btn[10]);
		pan[0].add(btn[0]);
		pan[0].add(btn[11]);
		btn[12] = new JButton("*");
		btn[13] = new JButton("1/x");
		btn[14] = new JButton("/");
		btn[15] = new JButton("sqrt");
		btn[16] = new JButton("+");
		btn[17] = new JButton("clr");
		btn[18] = new JButton("-");
		btn[19] = new JButton("=");
		for(i=12;i<20;i++){
			if((i%2)==0)
				btn[i].setForeground(Color.RED);
			else
				btn[i].setForeground(Color.BLUE);
			pan[1].add(btn[i]);
			btn[i].addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						calculate(e.getActionCommand());
					}
				}
			);
		}
		btn[20] = new JButton("M+");
		btn[21] = new JButton("MR");
		btn[22] = new JButton("MS");
		btn[23] = new JButton("MC");
		for(i=20;i<24;i++){
			btn[i].setForeground(Color.RED);
			pan[3].add(btn[i]);
			btn[i].addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						calculate(e.getActionCommand());
					}
				}
			);
		}
		pan[2].add(memory);
		pan[2].add(query);
		win.add(pan[3], BorderLayout.WEST);
		win.add(pan[2], BorderLayout.NORTH);
		win.add(pan[1], BorderLayout.EAST);
		win.add(pan[0], BorderLayout.CENTER);
		win.setVisible(true);
	}
	public static void calculate(String op){
		if(op.matches("\\d") || op.matches("\\.")){
			query.setText(query.getText() + op);
		}
		else if(op.matches("[^=]")){
			query.setText(query.getText() + " " + op + " ");
		}
		else if(op.matches("=")){
			double sum = 0;
			String equation[] = (query.getText().replaceAll("(\\d*\\.*\\d*)(\\+|\\/|\\-|\\*)(\\d*\\.*\\d*)", "$1 $2 $3")).split("\\s+");
			if(equation.length == 0){
				query.setText("0");
			}
			else if(equation.length >= 3){
				for(int i=0;i<(equation.length-2);i+=2){
					sum = do_math(equation[i+1], equation[i], equation[i+2]);
					equation[i+2] = String.format("%f", sum); // Set up for the next do_math()
				}
				query.setText(String.format("%f", sum));
			}
			else{
				query.setText(equation[0]);
			}
		}
		else if(op.matches("M\\+")){
			if(mem_set != null){
				calculate("=");
				mem_set = Double.toString(do_math("+", mem_set, query.getText()));
			}
		}
		else if(op.matches("MC")){
			mem_set = null;
			memory.setText("");
		}
		else if(op.matches("MR")){
			if(mem_set != null)
				query.setText(mem_set);
		}
		else if(op.matches("MS")){
			calculate("=");
			mem_set = query.getText();
			memory.setText("M");
		}
		else if(op.matches("1/x")){
			calculate("=");
			query.setText(String.format("%f", do_math("/", "1", query.getText())));
		}
		else if(op.matches("sqrt")){
			calculate("=");
			query.setText(Double.toString(Math.sqrt(Double.valueOf(query.getText()))));
		}
		else if(op.matches("clr")){
			query.setText("");
		}
		else if(op.matches("-/\\+")){
			query.setText(Double.toString(do_math("*", "-1", query.getText())));
		}
	}
	public static double do_math(String op, String num1, String num2){
		double x = 0;
		if(op.equals("+")){
			x = (Double.valueOf(num1) + Double.valueOf(num2));
		}
		else if(op.equals("-")){
			x = (Double.valueOf(num1) - Double.valueOf(num2));
		}
		else if(op.equals("/")){
			x = (Double.valueOf(num1) / Double.valueOf(num2));
		}
		else if(op.equals("*")){
			x = (Double.valueOf(num1) * Double.valueOf(num2));
		}
		return x;
	}	
	public static void menu_clicked(String cl_ck){
		if(cl_ck.matches("\\* About")){
			JFrame about_win = new JFrame("About!");
			about_win.setSize(200, 150);
			about_win.add(new JLabel("Calculator final."), BorderLayout.NORTH);
			about_win.add(new JLabel("Written by Anthony Garcia."), BorderLayout.CENTER);
			about_win.setVisible(true);
		}
	}
}
