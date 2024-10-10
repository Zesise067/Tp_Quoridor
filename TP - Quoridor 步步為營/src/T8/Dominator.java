package T8;

import java.awt.BorderLayout;
//import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

//import javax.swing.JButton;
import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;

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
	
	private int Attacker = 1;
	
//	牆壁為水平還是垂直(暫定)
//	 - false 水平
//	 - true 垂直
	private boolean wallHorizontalOrVertical;
	
//	牆壁放置位置(暫定)
	private int[] wallXY;
	
//	玩家棋: 判斷最後移動位置是否為玩家可移動位置
//	 - false 不在
//	 - true 在
	private boolean PChess = false;
	
//	是否可以新增新物件
	private boolean NEW = false;
	
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
		
//		AnnotationSwitch = true;
		AnnotationSwitch = false;
		
		initAttacker();
		
	}

	/**
	 * 遊戲開始
	 * */
	public void run() {
		quoridor = new Quoridor(CELL_SIZE, width, height, deviation, AnnotationSwitch);
		
		checkerboard = new Checkerboard(CELL_SIZE, width, height, deviation, AnnotationSwitch);
		checkerboard.setNumberOfPlayers(quoridor.getNumberOfPlayers()[0]);
		checkerboard.updataCheckerboard(quoridor.getInfCheckerboard(), true);
		
		console = new Console(panelEAST_width, checkerboard, quoridor);

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
						
//						判斷遊戲人數是否確定 -------------------------------------------------------------------------------------
						if(checkerboard.getInitGame()) { //getInitGame(): 是否還在遊戲人數初始化階段
							if(quoridor.getNumberOfPlayers()[0] != console.getNumberOfPlayers()) { //判斷人數是否異動
								quoridor.setNumberOfPlayers(console.getNumberOfPlayers());
								checkerboard.setNumberOfPlayers(quoridor.getNumberOfPlayers()[0]);
								checkerboard.updataCheckerboard(quoridor.getInfCheckerboard(), true);
								initAttacker();
							}
						}else {
							console.switchJRadioButton(false); //單選方塊關閉
						}
						
					}
				}catch(InterruptedException e) {
					System.out.println(" - Error!!!!!!!! - run");
				}
			}
		});
		
		threadTouch.start();
		
		getContentPane().add(checkerboard, BorderLayout.CENTER);
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
			
			
//			加載遊戲規則
			quoridor.GameRules(getAttacker());
			
			mouseclicked = true;
			if(e.getX() < deviation || e.getY() < deviation || e.getX() > 720 || e.getY() > 720) {
				System.out.println("Out of bounds"); //出界
				
				mouseclicked = false;
			}else{
				
				checkerboard.MPressed(e.getX(), e.getY());
				getLocation(e.getX() - deviation, e.getY() - deviation);
				
				
			}
		}
//		當滑鼠釋放按鈕時觸發此事件
		public void mouseReleased(MouseEvent e) {
			checkerboard.MReleased();
			
			// 判斷是否取消點擊
			if(mouseclicked) {
				
				Attacker++;
				
				// 判斷最後的位置是否為玩家棋可放置位置
				if(PChess) {
					// 
					//--------------------------------------------
				}else {
					// 新增牆壁
					updata(wallXY, wallHorizontalOrVertical, true);
				}
			}
				
			
			
			mouseclicked = false;
		}
//		當滑鼠拖曳時觸發此事件
		public void mouseDragged(MouseEvent e) {
			if(mouseclicked) {
				if(e.getX() < deviation || e.getY() < deviation || e.getX() > 720 || e.getY() > 720) {
					System.out.println("Out of bounds"); //出界
					mouseclicked = false;
					
					Attacker++;
					
					// 判斷最後的位置是否為玩家棋可放置位置
					if(PChess) {
						// 
						//--------------------------------------------
					}else {
						// 新增牆壁
						updata(wallXY, wallHorizontalOrVertical, true);
					}
					
				}else{
											
					getLocation(e.getX() - deviation, e.getY() - deviation);
				}
			}
		}
//		當滑鼠移動時觸發此事件
		public void mouseMoved(MouseEvent e) {}
//		當滑鼠進入面板時觸發此事件
		public void mouseEntered(MouseEvent e) {}
//		當滑鼠離開面板時觸發此事件
		public void mouseExited(MouseEvent e) {
//			mouseclicked = false;
//			System.out.println("Out of bounds"); //出界
		}
//		當滑鼠點擊時觸發此事件
		public void mouseClicked(MouseEvent e) {}
	}
	
	/**
	 * 取的點擊位置
	 * */
	private void getLocation(int mX, int mY) { //mX, mY: 游標位置。 (已補上誤差值)
		int x = mX / CELL_SIZE; //棋盤格座標
		int y = mY / CELL_SIZE;
		
		int cX = x*CELL_SIZE + 65; //(內)棋盤格座標，包含壕溝右、下。(外)計算方格加壕溝最右、最下x、y軸座標
		int cY = y*CELL_SIZE + 65;		//計算計算方格與壕溝界線
		
		// 目前進攻方
		int p = getAttacker();
		int[] pXY = exchangePlayerCoordinate(p);
		
		// 判斷是垂直牆壁還是水平牆壁
		if(mX > cX && mY < cY) { //垂直牆壁
			
			// 滑鼠位於玩家棋不可放置位置
			PChess = false;
						
			// 牆壁為垂直
			wallHorizontalOrVertical = true;
			
			// 取的滑鼠所指示的牆壁位置
			wallXY = exchangeCoordinate(x, y, wallHorizontalOrVertical);
			
			// 避免牆壁衝突
			if(avoidWall(wallXY, wallHorizontalOrVertical)) {
				
				// 新增牆壁
				updata(wallXY, wallHorizontalOrVertical, true);
				
				// 死路判斷
				if(quoridor.Adventurer(pXY[0], pXY[1], p)) {
					
					// 可放置新物件
					NEW = true;
					
//					System.out.println("   | Wall");
				
					checkerboard.setWall_HorizontalOrVertical_Switch(wallHorizontalOrVertical);
					
					checkerboard.setsetWall_Switch(true);
					checkerboard.setDrawCoordinates(0, x);
					
					if(y == 8) {
						checkerboard.setDrawCoordinates(1, y-1);
					}else {
						checkerboard.setDrawCoordinates(1, y);
					}
					
					checkerboard.setsetPlayer_Switch(false);
				}
				
				// 移除牆壁
				updata(wallXY, wallHorizontalOrVertical, false);
				
			}
			
		}else if(mX < cX && mY > cY) { //水平牆壁
			
			// 滑鼠位於玩家棋不可放置位置
			PChess = false;
			
//			牆壁為水平
			wallHorizontalOrVertical = false;
			
			// 取的滑鼠所指示的牆壁位置
			wallXY = exchangeCoordinate(x, y, wallHorizontalOrVertical);
			
			// 避免牆壁衝突
			if(avoidWall(wallXY, wallHorizontalOrVertical)) {

				// 新增牆壁
				updata(wallXY, wallHorizontalOrVertical, true);
				
				// 死路判斷
				if(quoridor.Adventurer(pXY[0], pXY[1], p)) {
					
					// 可放置新物件
					NEW = true;
					
//					System.out.println("   - Wall");
					
					checkerboard.setWall_HorizontalOrVertical_Switch(wallHorizontalOrVertical);
					checkerboard.setsetWall_Switch(true);
					checkerboard.setDrawCoordinates(1, y);
					
					if(x == 8) {
						checkerboard.setDrawCoordinates(0, x-1);
					}else {
						checkerboard.setDrawCoordinates(0, x);
					}
	
					checkerboard.setsetPlayer_Switch(false);
				}
				
				// 移除牆壁
				updata(wallXY, wallHorizontalOrVertical, false);
				
			}
			
		}else if(quoridor.getInfCheckerboard()[y*2][x*2] == 25) {
			
			// 可放置新物件
			NEW = true;
			
			// 滑鼠位於玩家棋可放置位置
			PChess = true;
			
			checkerboard.setsetWall_Switch(false);
			
			checkerboard.setsetPlayer_Switch(true);
			
			checkerboard.setDrawCoordinates(0, x);
			checkerboard.setDrawCoordinates(1, y);
			
		}
	}
	
	/**
	 * 數值轉換座標
	 * 	- 垂直牆壁 true
	 * 	- 水平牆壁 false
	 * */
	public int[] exchangeCoordinate(int x, int y, boolean flag) {
		x*=2;
		y*=2;
		
		int[] wallXY = new int[2];
		
		if(flag) {
			wallXY[0] = x+1;
			wallXY[1] = y;
		}else {
			wallXY[0] = x;
			wallXY[1] = y+1;
		}
		
		return wallXY;
	}
	
	/**
	 * 牆壁迴避
	 * 	- direction
	 * 		- 垂直牆壁 true
	 * 		- 水平牆壁 false
	 * */
	public boolean avoidWall(int[] XY, boolean direction) {
		boolean flag = true;
		
		int x = XY[0];
		int y = XY[1];
		
		//本身
		if(quoridor.getInfCheckerboard()[y][x] == 11) return false;
		
		//另一邊
		if(direction) {
			if(XY[1] == 16) {
				if(quoridor.getInfCheckerboard()[y-1][x] == 11
					|| quoridor.getInfCheckerboard()[y-2][x] == 11) flag = false;
			}else {
				if(quoridor.getInfCheckerboard()[y+1][x] == 11
					|| quoridor.getInfCheckerboard()[y+2][x] == 11) flag = false;
			}
		}else {
			if(XY[0] == 16) {
				if(quoridor.getInfCheckerboard()[y][x-1] == 11
					|| quoridor.getInfCheckerboard()[y][x-2] == 11) flag = false;
			}else {
				if(quoridor.getInfCheckerboard()[y][x+1] == 11
					|| quoridor.getInfCheckerboard()[y][x+2] == 11) flag = false;
			}
		}
		
		return flag;
	}
	
	/**
	 * 初始化Attacker
	 * */
	public void initAttacker() {Attacker = 1;}
	
	/***/
	public int getAttacker() {
		if(Attacker%console.getNumberOfPlayers() == 0) {
			System.out.println(console.getNumberOfPlayers());
			return console.getNumberOfPlayers();
		}else{
			System.out.println(Attacker%console.getNumberOfPlayers());
			return Attacker%console.getNumberOfPlayers();
		}
	}
	
	/**
	 * 轉換玩家棋座標
	 * */
	public int[] exchangePlayerCoordinate(int player) {
		int[] pXY = new int[2];
		pXY[0] = quoridor.getNumberOfPlayers()[player] % 10;
		pXY[1] = quoridor.getNumberOfPlayers()[player] / 10;
		return pXY;
	}
	
	/**
	 * 變更棋盤
	 * 	- direction
	 * 		- 垂直牆壁 true
	 * 		- 水平牆壁 false
	 * 	- flag
	 * 		- true 新增牆壁
	 * 		- false 移除牆壁
	 * */
	public void updata(int[] wallXY, boolean direction, boolean flag){
		int x = wallXY[0];
		int y = wallXY[1];
		
		//本身
		if(flag) quoridor.getInfCheckerboard()[y][x] = 11;
		else quoridor.getInfCheckerboard()[y][x] = 0;
		
		//另一邊
		if(direction) {
			if(wallXY[1] == 16) 
				if(flag) {
					quoridor.getInfCheckerboard()[y-1][x] = 11;
					quoridor.getInfCheckerboard()[y-2][x] = 11;
				}
				else {
					quoridor.getInfCheckerboard()[y-1][x] = 0;
					quoridor.getInfCheckerboard()[y-2][x] = 0;
				}
			else 
				if(flag) {
					quoridor.getInfCheckerboard()[y+1][x] = 11;
					quoridor.getInfCheckerboard()[y+2][x] = 11;
				}
				else {
					quoridor.getInfCheckerboard()[y+1][x] = 0;
					quoridor.getInfCheckerboard()[y+2][x] = 0;
				}
		}else {
			if(wallXY[0] == 16) 
				if(flag) {
					quoridor.getInfCheckerboard()[y][x-1] = 11;
					quoridor.getInfCheckerboard()[y][x-2] = 11;
				}
				else {
					quoridor.getInfCheckerboard()[y][x-1] = 0;
					quoridor.getInfCheckerboard()[y][x-2] = 0;
				}
			else 
				if(flag) {
					quoridor.getInfCheckerboard()[y][x+1] = 11;
					quoridor.getInfCheckerboard()[y][x+2] = 11;
				}
				else {
					quoridor.getInfCheckerboard()[y][x+1] = 0;
					quoridor.getInfCheckerboard()[y][x+2] = 0;
				}
		}
	}
}
