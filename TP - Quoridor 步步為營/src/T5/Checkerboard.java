package T5;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Checkerboard1 棋盤1
 * */
public class Checkerboard extends JPanel {
//	基本變數
	private int CELL_SIZE;
	
	private int width;
	private int height;
	
	private int deviation; 				//壕溝寬度
	
	private boolean AnnotationSwitch; 	//註解開關
	
//	顏色設定
	private Color drawRect_Color;
	private Color background_Color;
	private Color player_Color;
	private Color wall_Color;
	private Color fullWall;
	private Color unfullWall;
	
//	監聽器
	private checkerboardListener mlistener; //棋盤監聽器
	public boolean mouseclicked; 			//判斷是否點擊點擊鼠標
	
//	牆壁
	private int[] wall;
	private int[] setWall_Coordinate; //放置牆壁之座標
	private boolean setWall_Switch; //放置牆壁之開關
	private boolean Wall_HorizontalOrVertical_Switch; //牆壁為水平還是垂直 。Horizontal false 。Vertical true
	private boolean switchWall;
	
//	玩家
	private int[] NumberOfPlayers;
	private int[] coordinateYX; //座標 YX。[0] Y 。[1] X
	
//	棋盤
	private int[][] infCheckerboard;
	
//	遊戲正式開始開關
	private boolean initGame; //遊戲初始化設定: (確定人數用) false 確定玩家人數 true 未確定玩家人數 
	
	/**
	 * 建構子
	 * */
	public Checkerboard() {}
	
	public Checkerboard(int CELL_SIZE, int width, int height, int deviation, boolean AnnotationSwitch) {
		super(true);
		
		this.CELL_SIZE = CELL_SIZE;
		
		this.width = width;
		this.height = height;
		
		this.deviation = deviation;
		
		this.AnnotationSwitch = AnnotationSwitch;
		
		init();
	}
	
	private void init() {
		initColor(); //顏色初始化
		
//		遊戲
		initGame = true;
		
//		棋盤		
		infCheckerboard = new int[17][17]; //棋盤資訊
		
//		玩家
		NumberOfPlayers = new int[5]; //玩家人數 。[0] 玩家人數 。[1-4] 玩家座標
		
		coordinateYX = new int[2]; //座標 YX。[0] Y 。[1] X
		
//		牆壁
		wall = new int[4];
		setWall_Coordinate = new int[2];
		setWall_Switch = false;
		switchWall = true;
		
//		滑鼠監聽器
		mlistener = new checkerboardListener();
		
		mouseclicked = false;
		
		addMouseListener(mlistener);
		addMouseMotionListener(mlistener);
		
//		面板設定
		setBackground(Color.black);
		setSize(width*CELL_SIZE + deviation, height*CELL_SIZE + deviation);
		setBackground(background_Color);
	}
	
	/**
	 * 顏色初始化
	 * */
	public void initColor() {
		drawRect_Color = new Color(0, 0, 0);		//黑色
		background_Color = new Color(132, 0, 0); 	//深深咖啡色
		player_Color = new Color(255, 204, 153); 	//淡橘色
		wall_Color = new Color(153, 153, 255); 		//淡紫色
		fullWall = new Color(0, 153, 51); 			//深綠色
		unfullWall = new Color(255, 204, 153); 		//淡橘色
		
//		Color(0, 0, 0);		 	//黑色
//		Color(0, 153, 51);		//深綠色
//		Color(102, 255, 102); 	//淡綠色
//		Color(132, 0, 0); 		//深深咖啡色
//		Color(153, 153, 255); 	//淡紫色
//		Color(168, 82, 50); 	//咖啡色
//		Color(175, 29, 0); 		//深咖啡色
//		Color(204, 255, 204); 	//淡淡綠色
//		Color(255, 204, 153);	//淡橘色
//		Color(255, 255, 255); 	//白色
//		Color.green;			//綠色
	}
	
	/**
	 * 初始化牆壁數量
	 * */
	public void initWall() {				
		if(NumberOfPlayers[0] == 2) {
			wall = new int[] {10,10,0,0};
		}else if(NumberOfPlayers[0] == 3) {
			wall = new int[] {6,6,6,0};
		}else {
			wall = new int[] {5,5,5,5};
		}
		
//		註解
		if(AnnotationSwitch) System.out.println(" [+] initWall() -> Checkerboard\n" + "\twall = " + Arrays.toString(wall));
	}
	
	/**
	 * 繪製面板
	 * */
	@Override
	public void paintComponent(Graphics g) {
//		註解輸出
		if(AnnotationSwitch) {
			System.out.println(" [+] paintComponent() -> Checkerboard");
		}
		
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;

//    	方格
		for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            	g2d.setColor(drawRect_Color);
            	g2d.fillRect(x*CELL_SIZE + deviation, y*CELL_SIZE + deviation, CELL_SIZE - deviation, CELL_SIZE - deviation);
            }
		}
		
//		牆壁
		if(switchWall) {
			if(setWall_Switch) {
				g2d.setColor(wall_Color);
				if(Wall_HorizontalOrVertical_Switch) {
//					由上往下
		            g2d.fillRect(setWall_Coordinate[0]*CELL_SIZE + CELL_SIZE, setWall_Coordinate[1]*CELL_SIZE + deviation, deviation, 2*CELL_SIZE - deviation);
				}else {
//					由左往右
		            g2d.fillRect(setWall_Coordinate[0]*CELL_SIZE + deviation, setWall_Coordinate[1]*CELL_SIZE + CELL_SIZE, 2*CELL_SIZE - deviation, deviation);
				}
				setWall_Switch = false;   
			}
//			牆壁剩餘數量
			for(int i = 0;i<NumberOfPlayers[0];i++) {
				if((NumberOfPlayers[0] == 2 && wall[i] == 10) 
				|| (NumberOfPlayers[0] == 3 && wall[i] == 6) 
				|| (NumberOfPlayers[0] == 4 && wall[i] == 5)) {
					g2d.setColor(fullWall);
					switch(i) {
						case 0: //北
							g2d.fillRect(deviation, 0, 705, deviation);
							break;
						case 1: //南
							g2d.fillRect(deviation, 720, 705, deviation);
							break;
						case 2: //西
							g2d.fillRect(0, deviation, deviation, 705);
							break;
						case 3: //東
							g2d.fillRect(720, deviation, deviation, 705);
							break;
						default:
							System.out.println("Error!!!");
			                break;
					}
				}else {
					for(int j = 0;j<wall[i];j++) {
						g2d.setColor(unfullWall);
						switch(i) {
							case 0: //北
								g2d.fillRect(deviation + j*CELL_SIZE, 0, 65, deviation);
								break;
							case 1: //南
								g2d.fillRect(735 - (j+1)*80, 720, 65, deviation);
								break;
							case 2: //西
								g2d.fillRect(0, deviation + j*CELL_SIZE, deviation, 65);
								break;
							case 3: //東
								g2d.fillRect(720, 735 - (j+1)*80, deviation, 65);
								break;
							default:
								System.out.println("Error!!!");
				                break;
						}
					}
				}
			}
		}
		
		
//	    玩家棋		
		for(int i = 1;i<=NumberOfPlayers[0];i++) { //玩家順序： 北南西東
			exchangeCoordinate(NumberOfPlayers[i]);
			
			Ellipse2D circle = new Ellipse2D.Double(coordinateYX[1]*CELL_SIZE + deviation +20, coordinateYX[0]*CELL_SIZE  + deviation +20, 25, 25); // (x, y, width, height)
	    	g2d.setColor(player_Color);
	        g2d.fill(circle);
	        g2d.draw(circle);
		}
	}
	
	/**
	 * 取的點擊位置
	 * */
	private void getLocation(int mX, int mY) { //mX, mY: 游標位置。 (已補上誤差值)
		int x = mX / CELL_SIZE; //棋盤格座標
		int y = mY / CELL_SIZE;
		
//		???????????????????????????????????????????????????????????????????????????????????????????????????
		
		int cX = x*CELL_SIZE + 65; //(內)棋盤格座標，包含壕溝右、下。(外)計算方格加壕溝最右、最下x、y軸座標
		int cY = y*CELL_SIZE + 65;		//計算計算方格與壕溝界線
		
//		判斷是垂直牆壁還是水平牆壁
		if(mX > cX && mY < cY) { //垂直牆壁
			Wall_HorizontalOrVertical_Switch = true;
			setWall_Switch = true;
			setWall_Coordinate[0] = x;
			if(y == 8) {
				setWall_Coordinate[1] = y-1;
			}else {
				setWall_Coordinate[1] = y;
			}
			repaint();
		}else if(mX < cX && mY > cY) { //水平牆壁
			Wall_HorizontalOrVertical_Switch = false;
			setWall_Switch = true;
			if(x == 8) {
				setWall_Coordinate[0] = x-1;
			}else {
				setWall_Coordinate[0] = x;
			}
			setWall_Coordinate[1] = y;
			repaint();
		}
		
//		註解輸出
		if(AnnotationSwitch) {
			System.out.println(" [+] getLocation() -> Checkerboard1");
			System.out.printf("\tx : y = %d : %d\n", x, y);
			System.out.printf("\tmX : mY = %d : %d\n", mX, mY);
			System.out.printf("\tcX : cY = %d : %d\n", cX, cY);
			if(mX > cX || mY > cY) { //牆壁
				System.out.println("   Wall");
			}else {
				System.out.println("   Square");
			}
			System.out.println();
		}
	}
		
	/**
	 * 棋盤監聽器
	 * */
	class checkerboardListener implements MouseListener, MouseMotionListener {
//		建構子
		public checkerboardListener() {
			mouseclicked = false;
		}
//		當滑鼠按下按鈕時觸發此事件
		public void mousePressed(MouseEvent e) { 
			mouseclicked = true;
			initGame = false;
			
//			---------------------------------------------------------------------------------------------------------------
			if(wall[0] != 0) {
				wall[0]--;
				wall[1]--;
				wall[2]--;
				wall[3]--;
			}else {
				switchWall = false;
			}
//			---------------------------------------------------------------------------------------------------------------
			
//			GameStart = true;
			if(e.getX() < deviation || e.getY() < deviation || e.getX() > 720 || e.getY() > 720) {
				if(AnnotationSwitch) {
					System.out.println("Out of bounds"); //出界
				}
			}else {
				getLocation(e.getX() -deviation, e.getY() -deviation); //補上誤差值
			}
		}
//		當滑鼠釋放按鈕時觸發此事件
		public void mouseReleased(MouseEvent e) {
			mouseclicked = false;
		}
//		當滑鼠拖曳時觸發此事件
		public void mouseDragged(MouseEvent e) {
			if(mouseclicked) {
				if(e.getX() < deviation || e.getY() < deviation || e.getX() > 720 || e.getY() > 720) {
					if(AnnotationSwitch) {
						System.out.println("Out of bounds"); //出界
						mouseclicked = false;
					}
				}else {
					getLocation(e.getX() -deviation, e.getY() -deviation); //補上誤差值
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
	 * 數值轉換座標
	 * */
	public void exchangeCoordinate(int value) {
		coordinateYX[0] = value / 10; //座標 Y
		coordinateYX[1] = value % 10; //座標 X
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 設定遊戲初始化旗標
	 * */
	public void setInitGame() {
		initGame = true;
		switchWall = true;
	}
	
	/**
	 * 取得遊戲初始化旗標
	 * */
	public boolean getInitGame() {
		return initGame;
	}
	
	/**
	 * 設定玩家人數
	 * */
	public void setNumberOfPlayers(int[] NumberOfPlayers) {
		this.NumberOfPlayers =  NumberOfPlayers;

		initWall();
		
		repaint();
	}
	
	/**
	 * 回傳玩家人數
	 * */
	public int[] getNumberOfPlayers() {
		return NumberOfPlayers.clone();
	}
	
	/**
	 * 設定棋盤值
	 * */
	public void setInfCheckerboard(int[][] infCheckerboard) {
		this.infCheckerboard = infCheckerboard;
		repaint();
	}
	
	/**
	 * 回傳棋盤值
	 * */
	public int[][] getInfCheckerboard() {
		return infCheckerboard.clone();
	}
}
