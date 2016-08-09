/* author: vags */
/* mail_to: viyangusaini@gmail.com */

/**
 * Program is to implement Dijkstra's Algorithm to find 
 * the shortest path between two vertices.
 * It has two files, 'Algorithm.java' and 'Frame.java'.
 * Frame.java is the main class file so you have to run that.
 * Also, the graph here is Directed graph.
 */

package Dijkstra;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;

public class Frame extends JFrame{
	
	// An object of Algorithm
	Algorithm m = new Algorithm();
	
	// Some useless stuff created by eclipse
	private static final long serialVersionUID = 1L;
	
	// Message to display when user click HELP button
	String helpMessage = "<html><br><b>Dijkstra Algorithm to find Shortest Path : </b><br>" + 
							"<br>First, create all the required Nodes (vertices)." +
							"<br>A Node will be created at the point where you click on gray screen." +
							"<br>Node will be named in increasing number starting from 0, automatically." + 
							"<br>After creating all the Nodes, click on the Start Button." +
							"<br>Then join these Nodes by Edges." +
							"<br>Enter the name of the Node in \"From\" and \"To\" textfield to join the two Nodes." +
							"<br>After creating all the Edges, enter \"Initial\" Node and \"Final\" Node to" +
							"<br>get the minimum distance between them." + 
							"<br><br><i><b>author :<font color=\"blue\"> vags</font>" + 
							"<br>mailto : <font color=\"blue\">viyangusaini@gmail.com</font></i><b></html>";
	
	// Adjacency matrix (used to get minimum distance and path)
	int adjacency[][];
	
	// Some GUI components
	JPanel upperPanel, lowerPanel, quotePanel, bottomPanel,  resultPanel;
	JButton setDistance, calculate, start, help, reset;
	JTextField fromTextfield, toTextfield, initialTextfield, finalTextfield, valueTextfield;
	JLabel fromLabel, toLabel, initialLabel, finalLabel, valueLabel, resultLabel, quote, helpLabel;
	
	// Variable that will be used to name Nodes
	int count = 0;
	
	// ArrayList to store x-y co-ordinates of Nodes
	ArrayList<Integer> x_pos = new ArrayList<Integer>();
	ArrayList<Integer> y_pos = new ArrayList<Integer>();
	
	// Constructor
	public Frame(){
		
		setLayout(new BorderLayout());
		Font textFont = new Font("Book Antiqua", Font.PLAIN, 18);
		
		// Frame Icon's Image
		try{
			Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("abc.png"));
			ImageIcon icon = new ImageIcon(image);
			setIconImage(icon.getImage());
		}catch(Exception e){}
		
		// Quote Panel ( green one )
			quote = new JLabel("     \"Simplicity is prerequisite for reliability.\"         ... Edsger W. Dijkstra", SwingConstants.CENTER);
			quote.setFont(new Font("Bell MT", Font.ITALIC, 24));
			quote.setForeground(Color.WHITE);
			
			quotePanel = new JPanel();
			quotePanel.setBackground(new Color(41, 212, 164));
			
			quotePanel.add(quote);
			
		// Working Panel ( gray one )
			upperPanel = new JPanel();
			upperPanel.setBackground(new Color(213, 219, 219)); // Alternative (191, 201, 202)

		// Bottom Panel ( pink and blue one )
		
			// Pink Panel
				resultLabel = new JLabel("Result will be shown here ...   ( click HELP for more info )", SwingConstants.CENTER);
				resultLabel.setFont(new Font("Book Antiqua", Font.PLAIN, 20));
				resultLabel.setForeground(Color.WHITE);
				
				resultPanel = new JPanel();
				resultPanel.setLayout(new BorderLayout());
				
				resultPanel.setBackground(new Color(251, 119, 138));
				resultPanel.add(resultLabel, BorderLayout.CENTER);
							
			// Blue Panel
				fromTextfield = new JTextField(2);
				toTextfield = new JTextField(2);
				initialTextfield = new JTextField(2);
				finalTextfield = new JTextField(2);
				valueTextfield = new JTextField(3);
				
				fromLabel = new JLabel("From:", SwingConstants.CENTER);
					fromLabel.setFont(textFont);
				toLabel = new JLabel("To:", SwingConstants.CENTER);
					toLabel.setFont(textFont);
				initialLabel = new JLabel("Inital:", SwingConstants.CENTER);
					initialLabel.setFont(textFont);
				finalLabel = new JLabel("Final:", SwingConstants.CENTER);
					finalLabel.setFont(textFont);
				valueLabel = new JLabel("Value:", SwingConstants.CENTER);
					valueLabel.setFont(textFont);
					
				setDistance = new JButton("Set Distance");
					setDistance.setFont(new Font("Bell MT", Font.BOLD, 14));
				calculate = new JButton("Calculate");
					calculate.setFont(new Font("Bell MT", Font.BOLD, 14));
				start = new JButton("Start");
					start.setFont(new Font("Bell MT", Font.BOLD, 14));
				reset = new JButton("Reset");
					reset.setFont(new Font("Bell MT", Font.BOLD, 14));
				help = new JButton("HELP");
					help.setFont(new Font("Bell MT", Font.BOLD, 12));
					
				helpLabel = new JLabel(helpMessage);
				helpLabel.setFont(new Font("Utsaah", Font.PLAIN, 20));
				
				fromTextfield.setEnabled(false);
				toTextfield.setEnabled(false);
				valueTextfield.setEnabled(false);
				setDistance.setEnabled(false);
				initialTextfield.setEnabled(false);
				finalTextfield.setEnabled(false);
				calculate.setEnabled(false);
					
				lowerPanel = new JPanel();
				lowerPanel.setBackground(new Color(111, 160, 255));
				lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
					
				lowerPanel.add(start);
				lowerPanel.add(fromLabel);
				lowerPanel.add(fromTextfield);
				lowerPanel.add(toLabel);
				lowerPanel.add(toTextfield);
				lowerPanel.add(valueLabel);
				lowerPanel.add(valueTextfield);
				lowerPanel.add(setDistance);
				lowerPanel.add(new JSeparator(SwingConstants.VERTICAL));
				lowerPanel.add(initialLabel);
				lowerPanel.add(initialTextfield);
				lowerPanel.add(finalLabel);
				lowerPanel.add(finalTextfield);
				lowerPanel.add(calculate);
				lowerPanel.add(reset);
				lowerPanel.add(help);
		
			bottomPanel = new JPanel();
			bottomPanel.setLayout(new BorderLayout());
						
			bottomPanel.add(lowerPanel, BorderLayout.CENTER);
			bottomPanel.add(resultPanel, BorderLayout.NORTH);
				
		add(upperPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		add(quotePanel, BorderLayout.NORTH);
		
		// when you click Start button, this will happen ...
		start.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
				adjacency = new int[count][count];
				fromTextfield.setText("");
				toTextfield.setText("");
				valueTextfield.setText("");
				fromTextfield.requestFocus();
				start.setEnabled(false);
				fromTextfield.setEnabled(true);
				toTextfield.setEnabled(true);
				valueTextfield.setEnabled(true);
				setDistance.setEnabled(true);
				initialTextfield.setEnabled(true);
				finalTextfield.setEnabled(true);
				calculate.setEnabled(true);
			}
		});
		
		// when you hit enter in fromTextfield, control will move to toTextfield
		fromTextfield.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent text){
				toTextfield.requestFocus();
			}
		});
		
		// when you hit enter in toTextfield, control will move to valueTextfield
		toTextfield.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent text){
				valueTextfield.requestFocus();
			}
		});
		
		// when you hit enter in valueTextfield, control will click setDistance button
		valueTextfield.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent text){
				setDistance.doClick();
				fromTextfield.requestFocus();
				fromTextfield.setText("");
				toTextfield.setText("");
				valueTextfield.setText("");
			}
		});
		
		// It will draw a line b/w two nodes with the given value 
		setDistance.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ee){
				try{
					Integer fromInt = Integer.parseInt(fromTextfield.getText());
					Integer toInt = Integer.parseInt(toTextfield.getText());
					Integer value = Integer.parseInt(valueTextfield.getText());
					
					// To check if user input a negative number or 
					// a number which is not yet have been assigned to any node 
					if(fromInt > count-1 || toInt > count-1 || value < 0 || fromInt < 0 || toInt < 0){
						JOptionPane.showMessageDialog(null, "Invalid Input", " Error", JOptionPane.ERROR_MESSAGE);
						return;
					// if user enters same number in both fields
					}else if(fromInt == toInt){
						JOptionPane.showMessageDialog(null, "Loops are not Allowed", " Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					drawLine(fromInt, toInt, value);
					fromTextfield.requestFocus();
					fromTextfield.setText("");
					toTextfield.setText("");
					valueTextfield.setText("");
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Invalid Input", " Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		// when you hit enter in initialTextfield, control will move to finalTextfield
		initialTextfield.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent text){
				finalTextfield.requestFocus();
			}
		});
		
		// when you hit enter in finalTextfield, control will click calculate button
		finalTextfield.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent text){
				calculate.doClick();
			}
		});
		
		// It will calculate the required distance, Dijkstra algorithm works here
		calculate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ee){
				
				try{
					Integer source = Integer.parseInt(initialTextfield.getText());
					Integer destination = Integer.parseInt(finalTextfield.getText());
					
					// To check if user input a negative number or 
					// a number which is higher than the number of nodes 
					if (source > count-1 || destination > count-1 || source < 0 || destination < 0){
						JOptionPane.showMessageDialog(null, "Invalid Input", " Error", JOptionPane.ERROR_MESSAGE);
						initialTextfield.setText("");
						finalTextfield.setText("");
						initialTextfield.requestFocus();
						return;
					}
					
					initialTextfield.setText("");
					finalTextfield.setText("");
					
					// main algorithm, m is the object of class Algorithm
					m.Dijkstra(adjacency, source, destination);
					
					// if there is no path b/w source and destination
					if (m.distance == Integer.MAX_VALUE)
						resultLabel.setText("There is no way to go from " + source +" to " + destination);
					else{
						resultLabel.setText("Minimum Distance between " + source + " and " + destination + " is " + m.distance + " ( via" + m.via + " )");
						showColor(m.path, source);
					}
	
					fromTextfield.setEnabled(false);
					toTextfield.setEnabled(false);
					valueTextfield.setEnabled(false);
					setDistance.setEnabled(false);
					initialTextfield.setEnabled(false);
					finalTextfield.setEnabled(false);
					calculate.setEnabled(false);
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Invalid Input", " Error", JOptionPane.ERROR_MESSAGE);
					initialTextfield.setText("");
					finalTextfield.setText("");
					initialTextfield.requestFocus();
				}
				
			}
		});
			
		// when you click on the g panel, this will happen ...
		upperPanel.addMouseListener(new MouseListener() {
		    public void mouseClicked(MouseEvent e) {
		    	if(start.isEnabled()){
		    		int flag = 0;
		    		
		    		// to get the coordinate of the point where we clicked
			    	int x = e.getX() - 6;
				    int y = e.getY() + 15;
				    x_pos.add(x + 2);
				    y_pos.add(y + 40);
				    
				    // if you click on the same position more than once
				    for (int w=0; w<x_pos.size()-1; w++){
				    	if(x+2 == x_pos.get(w) && y+40 == y_pos.get(w)){
				    		JOptionPane.showMessageDialog(null, "Node Already present here", "Error", JOptionPane.ERROR_MESSAGE);
				    		x_pos.remove(x_pos.size()-1);
				    		y_pos.remove(y_pos.size()-1);
				    		flag = 1;
				    		return;
				    	}
				    }
				    //System.out.println(x+","+y+","+count);
				    if(flag == 0)
				    	drawNode(count++, x, y);
			    }
		    }

			@Override
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			
		});
		
		// reset button listener
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				count = 0;
				adjacency = new int[0][0];
				x_pos.clear();
				y_pos.clear();
				start.setEnabled(true);
				fromTextfield.setEnabled(false);
				toTextfield.setEnabled(false);
				valueTextfield.setEnabled(false);
				setDistance.setEnabled(false);
				initialTextfield.setEnabled(false);
				finalTextfield.setEnabled(false);
				calculate.setEnabled(false);
				resultLabel.setText("Result will be shown here ...   ( click HELP for more info )");
				m.reset();
				repaint();
			}
		});
		
		// help button listener
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, helpLabel, " How to Use ?", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	// Method to change color of the required path (green color)
	private void showColor(ArrayList<Integer> path, int source){
		drawLineChangeColor(source, path.get(0));
		drawNode(source, x_pos.get(source)-2, y_pos.get(source)-41);
		int i;
		for (i=0; i<path.size()-1; i++){
			drawLineChangeColor(path.get(i), path.get(i+1));
			drawNode(path.get(i), x_pos.get(path.get(i))-2, y_pos.get(path.get(i))-41);
		}
		drawNode(path.get(i), x_pos.get(path.get(i))-2, y_pos.get(path.get(i))-41);
			
			
	}
	
	// Method to draw the node at the given coordinate
	private void drawNode(int count, int x, int y){
		Graphics g = this.getGraphics();
		Graphics2D graphics2d = (Graphics2D) g;
		g.setColor(new Color(111, 150, 255));
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.fillOval(x-13, y+10, 45, 45);
		Font font = new Font("Verdana", Font.PLAIN, 25);
		g.setFont(font);
		g.setColor(Color.WHITE);
		String text = count + "";
		if (count > 9)
			g.drawString(text, x-6, y+41);
		else
			g.drawString(text, x+2, y+41);
	}
	
	// Method to draw a line b/w two nodes with the given value as its weight
	private void drawLine(int from, int to, int value){
		if(adjacency[from][to] != 0){
			JOptionPane.showMessageDialog(null, "Can't Overwrite", " Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		adjacency[from][to] = value;
		Graphics g = this.getGraphics();
		Graphics2D graphics2d = (Graphics2D) g;
		g.setColor(new Color(111, 150, 255));
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.drawLine(x_pos.get(from)+4, y_pos.get(from)-4, x_pos.get(to)+4, y_pos.get(to)-4);
		String st = value + "";
		int x = (((x_pos.get(from) + x_pos.get(to))/2) + (x_pos.get(to)))/2;
		int y = (((y_pos.get(from) + y_pos.get(to))/2) + (y_pos.get(to)))/2;
		Font font = new Font("Verdana", Font.BOLD, 20);
		g.setColor(Color.RED);
		g.setFont(font);
		g.drawString(st, x, y);
		drawNode(from, x_pos.get(from)-2, y_pos.get(from)-41);
		drawNode(to, x_pos.get(to)-2, y_pos.get(to)-41);
	}
	
	// Re-draw the lines which has now green color
	private void drawLineChangeColor(int from, int to){
		Graphics g = this.getGraphics();
		Graphics2D graphics2d = (Graphics2D) g;
		g.setColor(new Color(0, 130, 0));
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.drawLine(x_pos.get(from)+4, y_pos.get(from)-4, x_pos.get(to)+4, y_pos.get(to)-4);
	}
	
	// Main Method
	public static void main(String args[]){

		// To select the Nimbus's Look and Feel
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		Frame cs = new Frame();
		cs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cs.setVisible(true);
		cs.setLocation(160,16);
		cs.setSize(1100,700);
		cs.setResizable(false);
		cs.setTitle("Dijkstra Algorithm");		
	}
}
