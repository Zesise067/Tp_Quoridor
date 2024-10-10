package T4;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 統治者
 * */
public class Dominator extends JFrame {
	
	private int CELL_SIZE;
	private int width;
	private int height;
	
	private int deviation; //壕溝寬度
	
	private int panelEAST_width;
	
	private boolean AnnotationSwitch; //註解開關
	
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
		
//		panelEAST_width = 130;
		panelEAST_width = 150;
		
		AnnotationSwitch = true;
//		AnnotationSwitch = false;
		
//		frame = createJFrame("TP - Quoridor 步步為營", width*CELL_SIZE + x_deviation*2, height*CELL_SIZE + y_deviation*3+5);
//		frame = createJFrame("TP - Quoridor 步步為營", 100, 200);    

	}

	/**
	 * 遊戲開始
	 * */
	public void run() {
		Checkerboard board = new Checkerboard(CELL_SIZE, width, height, deviation, AnnotationSwitch);
		Console console = new Console(panelEAST_width);
		
		Thread threadTouch = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					while(true) {
						Thread.sleep(300);
						
//						判斷遊戲人數是否確定
//						if(board.getInitGame()) { //getInitGame(): 是否還在遊戲人數初始化階段
//							if(board.getNumberOfPlayers() != console.getNumberOfPlayers()) { //判斷人數是否異動
//								board.setNumberOfPlayers(console.getNumberOfPlayers());
//							}
//						}else {
//							console.switchJRadioButton(false); //單選方塊關閉
//						}
						
//						if(console.getSwitchJB() > 0) {
//							System.out.println("On");
//							board.switchFuction(console.getSwitchJB());
//							console.setSwitchJB();
//						}
					}
				}catch(InterruptedException e) {
					
				}
			}
		});
		
		threadTouch.start();
		
		getContentPane().add(board, BorderLayout.EAST);
		getContentPane().add(console, BorderLayout.EAST);
		
		setFrame();
	}
	
	/**
	 * 框架設定
	 * */
	public void setFrame() {
//		JPanel panel = new JPanel();
//		panel.add(new JButton("123"));
//		add(panel);
		
//		frame.add(board);
//		add(board);
//		add(console);
		
		
//		frame.add(new JButton("123"));
		setTitle("TP - Quoridor 步步為營");
//		setLayout(null);
//		setSize(width*CELL_SIZE + deviation*2, height*CELL_SIZE + deviation*3+5);
		setSize(width*CELL_SIZE + deviation*2 +panelEAST_width, height*CELL_SIZE + deviation*3+9);
//		setSize(width*CELL_SIZE + x_deviation*2, height*CELL_SIZE);
//		setSize(200, 200);
//        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setLocation(50, 50);
		setLocation(1600, 100);
//        frame.setLocationRelativeTo(null); // 将窗口置于屏幕中央

		setResizable(false); // 設置不可調整大小
//		pack();
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
