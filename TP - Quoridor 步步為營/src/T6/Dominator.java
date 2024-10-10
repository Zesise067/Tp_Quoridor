package T6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 統治者
 * */
public class Dominator extends JFrame {
	
	Checkerboard checkerboard;
	Console console;
	Quoridor quoridor;
	
//	基本變數
	private int CELL_SIZE;
	private int width;
	private int height;
	
	private int deviation; //壕溝寬度
	
	private int panelEAST_width;
	
	private boolean AnnotationSwitch; //註解開關
	
//	監聽器
	private checkerboardListener mlistener; //棋盤監聽器
	public boolean mouseclicked; 			//判斷是否點擊點擊鼠標
	
//	建立物建
//	Checkerboard checkerboard = new Checkerboard(CELL_SIZE, width, height, deviation, AnnotationSwitch);
//	Console console = new Console(panelEAST_width);
//	Quoridor quoridor = new Quoridor(CELL_SIZE, width, height, deviation, AnnotationSwitch);
	
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
		checkerboard = new Checkerboard(CELL_SIZE, width, height, deviation, AnnotationSwitch);
		console = new Console(panelEAST_width);
		quoridor = new Quoridor(CELL_SIZE, width, height, deviation, AnnotationSwitch);

		checkerboard.setNumberOfPlayers(quoridor.getNumberOfPlayers());
		checkerboard.updataCheckerboard(quoridor.getInfCheckerboard(), true);
		
//		滑鼠監聽器
		mlistener = new checkerboardListener();
		mouseclicked = false;
		
		checkerboard.addMouseListener(mlistener);
		checkerboard.addMouseMotionListener(mlistener);
		
		Thread threadTouch = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					while(true) {
						Thread.sleep(100);
						
						if(console.getClickJB() != "") {
							switch(console.getClickJB()) {	
								case "jbRegretChess": 	//悔棋
									System.out.println("jbRegretChess");
								break;
								case "jbHint":			//提示
									System.out.println("jbHint");
								break;
								case "jbSurrender":		//投降
									System.out.println("jbSurrender");
								break;
								case "jbSubstitute":	//代打
									System.out.println("jbSubstitute");
								break;
								case "jbNext":			//下一局
									System.out.println("jbNext");
									quoridor.init();
									checkerboard.setInitGame();
									checkerboard.updataCheckerboard(quoridor.getInfCheckerboard(), true);
									checkerboard.setNumberOfPlayers(quoridor.getNumberOfPlayers());
								break;
								case "jbCreator":		//開發者模式
									System.out.println("jbCreator");
								break;
								default:
									System.out.println("???");
								break;
							}
							console.setClickJB();
						}
						
//						判斷遊戲人數是否確定
						if(checkerboard.getInitGame()) { //getInitGame(): 是否還在遊戲人數初始化階段
							if(quoridor.getNumberOfPlayers() != console.getNumberOfPlayers()) { //判斷人數是否異動
								quoridor.setNumberOfPlayers(console.getNumberOfPlayers());
								checkerboard.setNumberOfPlayers(quoridor.getNumberOfPlayers());
								checkerboard.updataCheckerboard(quoridor.getInfCheckerboard(), true);
							}
						}else {
							console.switchJRadioButton(false); //單選方塊關閉
						}
					}
				}catch(InterruptedException e) {
					
				}
			}
		});
		
		threadTouch.start();
		
		getContentPane().add(checkerboard, BorderLayout.EAST);
		getContentPane().add(console, BorderLayout.EAST);
		
		setFrame();
	}
	
	/**
	 * 框架設定
	 * */
	public void setFrame() {
		setTitle("TP - Quoridor 步步為營");
//		setLayout(null);
		setSize(width*CELL_SIZE + deviation*2 +panelEAST_width, height*CELL_SIZE + deviation*3+9);
//        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setLocation(50, 50);
//		setLocation(2500, 100);
//		setLocation(-1500, -50);
        setLocationRelativeTo(null); // 将窗口置于屏幕中央
		setResizable(false); // 設置不可調整大小
//		pack();
        setVisible(true);
	}
	
	/**
	 * 棋盤監聽器
//	 * 	* 游標誤差值
//	 *  	* e.getX()-7
//	 *  	* e.getY()-28
	 * */
	class checkerboardListener implements MouseListener, MouseMotionListener {
//		建構子
		public checkerboardListener() {}
//		當滑鼠按下按鈕時觸發此事件
		public void mousePressed(MouseEvent e) {
			mouseclicked = true;
//			if(e.getX()-7 < deviation || e.getY()-28 < deviation || e.getX()-7 > 720 || e.getY()-28 > 720) {
//				System.out.println("Out of bounds"); //出界
//			}else {
//				checkerboard.MPressed(e.getX()-7, e.getY()-28);
//			}
			checkerboard.MPressed(e.getX(), e.getY());
		}
//		當滑鼠釋放按鈕時觸發此事件
		public void mouseReleased(MouseEvent e) {
//			if(e.getX()-7 < deviation || e.getY()-28 < deviation || e.getX()-7 > 720 || e.getY()-28 > 720) {
//				System.out.println("Out of bounds"); //出界
//			}else {
				checkerboard.MReleased();
//			}
				mouseclicked = false;
		}
//		當滑鼠拖曳時觸發此事件
		public void mouseDragged(MouseEvent e) {
			if(mouseclicked) {
				checkerboard.MDragged(e.getX(), e.getY());
			}
			
//			if(e.getX()-7 < deviation || e.getY()-28 < deviation || e.getX()-7 > 720 || e.getY()-28 > 720) {
//				System.out.println("Out of bounds"); //出界
//			}else {
//				checkerboard.MDragged(e.getX()-7, e.getY()-28);
//			}
		}
//		當滑鼠移動時觸發此事件
		public void mouseMoved(MouseEvent e) {}
//		當滑鼠進入面板時觸發此事件
		public void mouseEntered(MouseEvent e) {}
//		當滑鼠離開面板時觸發此事件
		public void mouseExited(MouseEvent e) {
			mouseclicked = false;
			System.out.println("Out of bounds"); //出界
		}
//		當滑鼠點擊時觸發此事件
		public void mouseClicked(MouseEvent e) {}
	}
}
