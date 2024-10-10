package T9;

import java.awt.BorderLayout;
import java.awt.Color;
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
	
	Checkerboard CB;
	Console CS;
	Quoridor Q;
	
	// 基本變數
	private int[] BaseVariable;
	
	private int CELL_SIZE;
	private int width;
	private int height;
	
	private int deviation; //壕溝寬度
	
	private int panelEAST_width;
	
	private boolean AnnotationSwitch; //註解開關
	
//	監聽器
	private checkerboardListener mlistener; //棋盤監聽器
	public boolean mouseclicked; 			//判斷是否點擊點擊鼠標
	
//	玩家	
	private int Attacker = 2;
	
//	(暫存) 滑鼠移動正確位置座標，作後移除使用
	private int[] tXY = new int[] {-1, -1};
	
//	判斷移動為玩家棋還是牆壁 - false: Player	- true: Wall
	private boolean PlyORWall;

//	水平還是垂直
	public boolean HV;
	
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
		
		CELL_SIZE = 80;			//單元格大小
		width = 9;				//棋盤格數(寬)
		height = 9;				//棋盤格數(長)
		deviation = 15; 		//壕溝寬度
		panelEAST_width = 75;	//功能視窗寬度
		
		BaseVariable = new int[]{
				CELL_SIZE,			//[0] - 單元格大小
				width,				//[1] - 棋盤格數(寬)
				height,				//[2] - 棋盤格數(長)
				deviation,			//[3] - 壕溝寬度
				panelEAST_width,	//[4] - 功能視窗寬度
		};
		
//		AnnotationSwitch = true;	//註解開關
		AnnotationSwitch = false;
		
		initAttacker();
	}

	/**
	 * 遊戲開始
	 * */
	public void run() {
		Q = new Quoridor(AnnotationSwitch);		// 步步為營(遊戲規則)
		CB = new Checkerboard(BaseVariable, AnnotationSwitch);	// 棋盤繪製
		CS = new Console(panelEAST_width, Q);					// 功能面板
		
		CB.loadInformation(Q.getCheckerboard(), Q.getWall());
		
//		滑鼠監聽器 --------------------------------------
		mlistener = new checkerboardListener();
		mouseclicked = false;
		
		CB.addMouseListener(mlistener);
		CB.addMouseMotionListener(mlistener);
		
//		面板設定 -------------------------------------------------
		getContentPane().add(CB, BorderLayout.CENTER);
		getContentPane().add(CS, BorderLayout.SOUTH);
		
		setFrame();
	}
	
	/**
	 * 框架設定
	 * */
	public void setFrame() {
		setTitle("TP - Quoridor 步步為營");						// 標題
		
		//setLayout(null); -------------------------------------
        //getContentPane().setBackground(Color.BLACK); ---------
		
		setSize(width*CELL_SIZE + deviation*2 ,	// 視窗(寬)
				height*CELL_SIZE + deviation*3+9 +panelEAST_width);				// 視窗(長)
		setResizable(false);									// 設置不可調整大小
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			// 設定預設關閉操作
        
//		setLocation(50, 50);									// 顯示位置
//		setLocation(2500, 100);
		setLocation(-1500, -50);
//        setLocationRelativeTo(null); 							// 将窗口置于屏幕中央
        
		//pack();-----------------------------------------------
		
        setVisible(true);										//可視化開關
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
			mouseclicked = true;	// 滑鼠點擊(開)
			
			// 執行判斷
			if(e.getX() < deviation || e.getY() < deviation || e.getX() > 720 || e.getY() > 720) {
				System.out.println("Out of bounds");	// 出界
				mouseclicked = false;					// 滑鼠點擊(關)
			}else{		
				callQuoridor(exchange(e.getX(), e.getY()), true);	// 進行對比與轉換([滑鼠座標 -> 棋盤座標], [F - 移除 / T - 修改])
			}
		}
//		當滑鼠釋放按鈕時觸發此事件
		public void mouseReleased(MouseEvent e) {
			if(mouseclicked) {
				if(PlyORWall) Q.setWall(getAttacker());	// 牆壁數量減一
				Attacker++;								// 進入下一回合

				Q.GameRules(getAttacker());
			}
			mouseclicked = false;
			
			tXY[0] = -1;
		}
//		當滑鼠拖曳時觸發此事件
		public void mouseDragged(MouseEvent e) {
			if(mouseclicked) {
				if(e.getX() < deviation || e.getY() < deviation || e.getX() > 720 || e.getY() > 720) {	// 出界
					System.out.println("Out of bounds");
					mouseclicked = false;
					
					Attacker++;	// 進入下一回合
					
					Q.GameRules(getAttacker());
					
				}else{
					callQuoridor(exchange(e.getX(), e.getY()), true);	// 進行對比與轉換([滑鼠座標 -> 棋盤座標], [T - 修改])
				}
			}
		}
//		當滑鼠移動時觸發此事件
		public void mouseMoved(MouseEvent e) {}
//		當滑鼠進入面板時觸發此事件
		public void mouseEntered(MouseEvent e) {}
//		當滑鼠離開面板時觸發此事件
		public void mouseExited(MouseEvent e) {}
//		當滑鼠點擊時觸發此事件
		public void mouseClicked(MouseEvent e) {}
	}
	
	/**
	 * - 轉換
	 * 	[+] 滑鼠座標 -> 棋盤座標
	 *			-+----+-+-	[+] 玩家棋	- mX < dividerX && mY < dividerY
	 *			 |    | |	[+] 垂直牆壁	- mX > dividerX && mY < dividerY
	 *			-+----+ +-	[+] 水平牆壁	- mX < dividerX && mY > dividerY
	 *			-+----+-+-	[+] 不動作	- mX > dividerX && mY > dividerY
	 * */
	public int[] exchange(int mX, int mY) {
		// 滑鼠座標減去誤差(外圍表示牆壁數量空間)
		mX -= deviation;
		mY -= deviation;
		
		// 滑鼠座標轉換單元格(為 9*9 空間)(以 0 為首)
		int csX = mX / CELL_SIZE;
		int csY = mY / CELL_SIZE;
		
		// 分隔線(區分玩家棋與牆壁方置空間)
		int dividerX = csX*CELL_SIZE + 65;	//	-+----+-+-	[+] 玩家棋	- mX < dividerX && mY < dividerY
		int dividerY = csY*CELL_SIZE + 65;	//	 |    | |	[+] 垂直牆壁	- mX > dividerX && mY < dividerY
											//	-+----+-+-	[+] 水平牆壁	- mX < dividerX && mY > dividerY
											//	-+----+-+-	[+] 不動作	- mX > dividerX && mY > dividerY
		
		// (回傳值) 轉換棋盤座標(棋盤格式為 17*17 空間)(以 0 為首)
		int[] XY = new int[2];
		
		// 滑鼠座標轉換期盤座標
		if(mX < dividerX && mY < dividerY) {		// 玩家棋
			XY[0] = csX*2;
			XY[1] = csY*2;
		}else if(mX > dividerX && mY < dividerY) {	// 垂直牆壁
			XY[0] = csX*2 + 1;
			XY[1] = csY*2;
			
			HV = true;
		}else if(mX < dividerX && mY > dividerY) {	//水平牆壁
			XY[0] = csX*2;
			XY[1] = csY*2 + 1;
			
			HV = false;
		}//else {}									// 不動作
		
		return XY;
	}

//	
	public boolean Switch = false;
	
	/**
	 * 呼叫遊戲管理員
	 * 	- XY: 棋盤座標
	 * 	- flag
	 * 		False:	移除
	 * 		True:	新增
	 * */
	public void callQuoridor(int[] XY, boolean flag) {
		
		if(flag) {	// 新增
			if(XY[0] % 2 == 0 && XY[1] % 2 == 0) {	// 
				if(Q.getCheckerboard()[XY[1]][XY[0]] == 25) {	// 玩家可移動位置
					
					callQuoridor(tXY, false);	// 進行對比與轉換([(前) 滑鼠座標], [F - 移除])	
					
					Q.getCheckerboard()[XY[1]][XY[0]] = 20;
	
					Q.printCheckerboard();
					
					
					tXY[0] = XY[0];	//暫存
					tXY[1] = XY[1];
					
					PlyORWall = false;	//移動玩家棋
				}
			}else {
				if(Q.getWall(getAttacker()) > 0) {	// 牆壁數量
					if(Q.avoidWall(XY, HV)) {		// 牆壁是否重疊
						
						Q.getCheckerboard()[XY[1]][XY[0]] = 11;
						
						if(HV) {		// 垂直牆壁
							if(XY[0] == 16) {			// 最下面
								Q.getCheckerboard()[XY[1]-1][XY[0]] = 11;
								Q.getCheckerboard()[XY[1]-2][XY[0]] = 11;
							}else {						// 其他
								Q.getCheckerboard()[XY[1]+1][XY[0]] = 11;
								Q.getCheckerboard()[XY[1]+2][XY[0]] = 11;
							}							
						}else {						// 水平牆壁
							if(XY[0] == 16) {			// 最右面
								Q.getCheckerboard()[XY[1]][XY[0]-1] = 11;
								Q.getCheckerboard()[XY[1]][XY[0]-2] = 11;
							}else {						// 其他
								Q.getCheckerboard()[XY[1]][XY[0]+1] = 11;
								Q.getCheckerboard()[XY[1]][XY[0]+2] = 11;
							}	
						}
						
						if(Q.Adventurer()) {		// 死路判斷
							
							callQuoridor(tXY, false);	// 進行對比與轉換([(前) 滑鼠座標], [F - 移除])	
							
							tXY[0] = XY[0];	//暫存
							tXY[1] = XY[1];
							
							PlyORWall = true;	//移動牆壁
						}else {
							callQuoridor(XY, false);	// 進行對比與轉換([滑鼠座標], [F - 移除])
						}
					}
				}
			}
		}else {		// 移除
			if(tXY[0] != -1) {
				if(Q.getCheckerboard()[XY[1]][XY[0]] == 20) Q.getCheckerboard()[XY[1]][XY[0]] = 25;	// 玩家棋
				else {										Q.getCheckerboard()[XY[1]][XY[0]] = 0;	// 牆壁
				
					if(XY[0] % 2 == 1 && XY[1] % 2 == 0) {			// 垂直牆壁
						if(XY[1] == 16) {								// 最下面
							Q.getCheckerboard()[XY[1]-1][XY[0]] = 0;
							Q.getCheckerboard()[XY[1]-2][XY[0]] = 0;
						}else {											// 其他
							Q.getCheckerboard()[XY[1]+1][XY[0]] = 0;
							Q.getCheckerboard()[XY[1]+2][XY[0]] = 0;
						}
					}else if(XY[0] % 2 == 0 && XY[1] % 2 == 1) {	// 水平牆壁
						if(XY[0] == 16) {								// 最右面
							Q.getCheckerboard()[XY[1]][XY[0]-1] = 0;
							Q.getCheckerboard()[XY[1]][XY[0]-2] = 0;
						}else {											// 其他
							Q.getCheckerboard()[XY[1]][XY[0]+1] = 0;
							Q.getCheckerboard()[XY[1]][XY[0]+2] = 0;
						}	
					}
				}
			}
		}
	}
	
	/**
	 * 初始化Attacker
	 * */
	public void initAttacker() {Attacker = 1;}

	/***/
	public int getAttacker() {
		if(Attacker % Q.getNumberOfPlayers()[0] == 0)	return Q.getNumberOfPlayers()[0];				// EndPlayer
		else 											return Attacker % Q.getNumberOfPlayers()[0];	// 1 - (EndPlayer-1)
	}

	/**
	 * 轉換玩家棋座標
	 * */
	public int[] exchangePlayerCoordinate(int player) {
		int[] pXY = new int[2];
		pXY[0] = Q.getNumberOfPlayers()[player] % 10 * 2;
		pXY[1] = Q.getNumberOfPlayers()[player] / 10 * 2;
		return pXY;
	}
}
