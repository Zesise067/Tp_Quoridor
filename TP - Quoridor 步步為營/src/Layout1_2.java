

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * Layout 佈局
 * */
public class Layout1_2 { //

	private JFrame frame;
	private JPanel panel;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JButton jButton5;
	private JButton jButton6;
	
	private int CELL_SIZE;
	private int width;
	private int height;
	
	private int deviation; //誤差值
	
	public Layout1_2() {
		init();
	}
	
	private void init() {
		CELL_SIZE = 70;
		width = 9;
		height = 9;
		
		deviation = 15;
		
		panel = new JPanel();
//		panel.setSize(new Dimension(100, 100));
		panel.setBackground(Color.black);
		panel.setLayout(new GridBagLayout());
		
		jButton1 = new JButton("jButton1");
		jButton2 = new JButton("jButton2");
		jButton3 = new JButton("jButton3");
		jButton4 = new JButton("jButton4");
		jButton5 = new JButton("jButton5");
		jButton6 = new JButton("jButton6");
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 12;
		c.gridheight = 6;
		c.weightx = 2;
		c.weighty = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		panel.add(jButton1, c);
		
		c.gridx = 12; //x 軸位置
		c.gridy = 0; //y 軸位置
		c.gridwidth = 1; //x 軸長度
		c.gridheight = 6; //y 軸長度
		c.weightx = 1;
		panel.add(jButton2, c);
		
		c.gridx = 13;
		c.gridy = 0;
		c.gridwidth = 12;
		c.gridheight = 6;
		c.weightx = 2;
		panel.add(jButton3, c);
		
		c.gridx = 24; //x 軸位置
		c.gridy = 0; //y 軸位置
		c.gridwidth = 1; //x 軸長度
		c.gridheight = 6; //y 軸長度
		c.weightx = 1;
		panel.add(jButton4, c);
		
		c.gridx = 25;
		c.gridy = 0;
		c.gridwidth = 12;
		c.gridheight = 6;
		c.weightx = 2;
		panel.add(jButton5, c);
		
		c.gridx = 36; //x 軸位置
		c.gridy = 0; //y 軸位置
		c.gridwidth = 1; //x 軸長度
		c.gridheight = 6; //y 軸長度
		c.weightx = 1;
		panel.add(jButton6, c);
		
		createFrame();
		frame.add(panel);
	}
	
	public void createFrame() {
		frame = new JFrame("TP - Quoridor 步步為營");
		frame.setLocation(50, 50);
		frame.setSize(new Dimension(500, 500));
//		frame.pack();
//		frame.getContentPane().setLayout(new GridBagLayout());
		frame.setBackground(Color.black);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setResizable(false); // 設置不可調整大小
		frame.setVisible(true);    
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Layout1_2();
	}

}
