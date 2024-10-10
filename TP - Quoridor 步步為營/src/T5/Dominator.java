package T5;

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
	
//	基本變數
	private int CELL_SIZE;
	private int width;
	private int height;
	
	private int deviation; //壕溝寬度
	
	private int panelEAST_width;
	
	private boolean AnnotationSwitch; //註解開關
	
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
		Checkerboard checkerboard = new Checkerboard(CELL_SIZE, width, height, deviation, AnnotationSwitch);
		Console console = new Console(panelEAST_width);
		Quoridor quoridor = new Quoridor(CELL_SIZE, width, height, deviation, AnnotationSwitch);
		
		checkerboard.setInfCheckerboard(quoridor.getInfCheckerboard());
		checkerboard.setNumberOfPlayers(quoridor.getNumberOfPlayers());
		
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
									checkerboard.setInfCheckerboard(quoridor.getInfCheckerboard());
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
							if(quoridor.getNumberOfPlayers()[0] != console.getNumberOfPlayers()) { //判斷人數是否異動
								quoridor.setNumberOfPlayers(console.getNumberOfPlayers());
								checkerboard.setNumberOfPlayers(quoridor.getNumberOfPlayers());
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
//		setLocation(1600, 100);
        setLocationRelativeTo(null); // 将窗口置于屏幕中央
		setResizable(false); // 設置不可調整大小
//		pack();
        setVisible(true);
	}
}
