package T1_Base;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Checkerboard1 棋盤1
 * */
public class Checkerboard1 extends JPanel { //

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
	
//	private Color drawRect_Color = Color.green;
//	private Color drawRect_Color = new Color(255, 204, 153); //淡橘色
//	private Color drawRect_Color = new Color(153, 153, 255); //淡紫色
//	private Color drawRect_Color = new Color(255, 255, 255); //白色
//	private Color drawRect_Color = new Color(204, 255, 204); //淡淡綠色
//	private Color drawRect_Color = new Color(102, 255, 102); //淡綠色
//	private Color drawRect_Color = new Color(0, 153, 51); //深綠色
//	private Color drawRect_Color = new Color(0, 0, 0); //黑色
	private Color drawRect_Color;
	
	
//	private Color background_Color = new Color(153, 153, 255); //淡紫色
//	private Color background_Color = new Color(255, 204, 153); //淡橘色
//	private Color background_Color = new Color(0, 0, 0); //黑色
//	private Color background_Color = new Color(0, 153, 51); //深綠色
//	private Color background_Color = new Color(204, 255, 204); //淡淡綠色
//	private Color background_Color = new Color(102, 255, 102); //淡綠色
//	private Color background_Color = new Color(168, 82, 50); //咖啡色
//	private Color background_Color = new Color(175, 29, 0); //深咖啡色
//	private Color background_Color = new Color(132, 0, 0); //深深咖啡色
	private Color background_Color;
	
//	private Color player_Color = new Color(255, 204, 153); //淡橘色
	private Color player_Color;
//	private Color wall_Color = new Color(153, 153, 255); //淡紫色
	private Color wall_Color;
	
	/**
	 * 建構子
	 * */
	public Checkerboard1() {
		
	}
	
	/**
	 * 建構子
	 * */
	public Checkerboard1(int CELL_SIZE, int width, int height, int deviation) {
		super(true);
		
		this.CELL_SIZE = CELL_SIZE;
		this.width = width;
		this.height = height;
		
		this.deviation = deviation;
		
		init();
	}
	
	private void init() {
		drawRect_Color = new Color(0, 0, 0); //黑色
		background_Color = new Color(132, 0, 0); //深深咖啡色
		player_Color = new Color(255, 204, 153); //淡橘色
		wall_Color = new Color(153, 153, 255); //淡紫色
//		SwingUtilities.invokeLater(() -> {
//	        ColorOutputExample example = new ColorOutputExample();
//	        example.setVisible(true);
//	    });
		
//		CELL_SIZE = 80;
//		width = 9;
//		height = 9;
//		
//		x_deviation = 15;
//		y_deviation = 15;
		
//		setSize(new Dimension(width*CELL_SIZE, height*CELL_SIZE));
//		setSize(width*CELL_SIZE + x_deviation*2, height*CELL_SIZE + y_deviation*3+5);
		setSize(width*CELL_SIZE + deviation, height*CELL_SIZE + deviation);
//		setSize(0, 0);
		setBackground(background_Color);
//		System.out.printf("panel -> width: %d    height: %d\n", this.getWidth(), this.getHeight());
		
//		createFrame();
//		frame.getContentPane().add(this);
	}
	
//	public void createFrame() {
//		frame = new JFrame("TP - Quoridor 步步為營");
//		frame.setLocation(50, 50);
////		frame.setSize(new Dimension(width*CELL_SIZE, height*CELL_SIZE));
//		frame.setSize(width*CELL_SIZE + x_deviation*2, height*CELL_SIZE + y_deviation*3+5); //完整棋盤
////		System.out.printf("frame -> width: %d    height: %d\n", frame.getWidth(), frame.getHeight());
////		frame.pack();
////		frame.getContentPane().setLayout(new FlowLayout());
//		frame.getContentPane().setBackground(Color.black);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////		frame.setResizable(false); // 設置不可調整大小
//		frame.setVisible(true);
//        
//	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
//		g2d.setStroke(new BasicStroke(2));

		for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
//        		方格
            	g2d.setColor(drawRect_Color);
//            	g2d.drawRect(x*CELL_SIZE + x_deviation, y*CELL_SIZE + y_deviation, CELL_SIZE, CELL_SIZE);
//            	g2d.drawRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
            	g2d.fillRect(x*CELL_SIZE + deviation, y*CELL_SIZE + deviation, CELL_SIZE - deviation, CELL_SIZE - deviation);
//            	x_deviation = x_deviation + 15;
//        		y_deviation = y_deviation + 15;
            	
//            	玩家
            	Ellipse2D circle = new Ellipse2D.Double(0*CELL_SIZE + deviation +20, 0*CELL_SIZE  + deviation +20, 25, 25); // (x, y, width, height)
		    	g2d.setColor(player_Color);
		        g2d.fill(circle);
		        g2d.draw(circle);
		        

//		        牆壁
	            g2d.setColor(wall_Color);
//	            由上往下
	            g2d.fillRect(0*CELL_SIZE + CELL_SIZE, 0*CELL_SIZE + deviation, deviation, 2*CELL_SIZE - deviation);
//	            由左往右
	            g2d.fillRect(0*CELL_SIZE + deviation, 0*CELL_SIZE + CELL_SIZE, 2*CELL_SIZE - deviation, deviation);
		        
            }
		}
//		for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
////        		格線
//            	g2d.setColor(drawRect_Color);
//            	g2d.drawRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
//            }
//		}
	}

}
