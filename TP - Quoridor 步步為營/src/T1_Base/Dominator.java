package T1_Base;

import java.awt.Color;

import javax.swing.JFrame;

public class Dominator extends JFrame {
	
//	private JFrame frame;
	
	private int CELL_SIZE;
	private int width;
	private int height;
	
//	private int width_frame;
//	private int height_frame;
	
//	private int x_deviation; //誤差值
//	private int y_deviation; //誤差值
	private int deviation;
	
//	private int disparity; //差距
	
	/**
	 * 建構子
	 * */
	public Dominator() {
		init();
	}
	
	/**
	 * 初始化
	 * */
	private void init() {
		CELL_SIZE = 80;
		width = 9;
		height = 9;
		
		deviation = 15;
		
//		frame = createJFrame("TP - Quoridor 步步為營", width*CELL_SIZE + x_deviation*2, height*CELL_SIZE + y_deviation*3+5);
//		frame = createJFrame("TP - Quoridor 步步為營", 100, 200);    

	}

	/**
	 * 遊戲開始
	 * */
	public void run() {
		Checkerboard1 board = new Checkerboard1(CELL_SIZE, width, height, deviation);
		
//		frame.add(board);
		add(board);
		
//		frame.add(new JButton("123"));
		setTitle("TP - Quoridor 步步為營");
		setLayout(null);
		setSize(width*CELL_SIZE + deviation*2, height*CELL_SIZE + deviation*3+5);
//		setSize(width*CELL_SIZE + x_deviation*2, height*CELL_SIZE);
//		setSize(200, 200);
        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(50, 50);
//        frame.setLocationRelativeTo(null); // 将窗口置于屏幕中央
        setVisible(true);
	}
	
//	/**
//	 * 建立框架
//	 * */
//	public JFrame createJFrame(String title, int width, int height) {
//		JFrame frame = new JFrame(title);
////        frame.setLayout(new FlowLayout());
//        frame.setSize(width, height);
//        frame.getContentPane().setBackground(Color.BLACK);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLocation(50, 50);
////        frame.setLocationRelativeTo(null); // 将窗口置于屏幕中央
//        frame.setVisible(true);
//        
//        // 可根据需要添加其他设置
//
//        return frame;
//    }
}
