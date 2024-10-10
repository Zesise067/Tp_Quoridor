package T8;

//import java.awt.BasicStroke;
//import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;

//import javax.swing.JFrame;
import javax.swing.JPanel;
//import javax.swing.SwingUtilities;

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
	private Color t_player_Color;
	private Color wall_Color;
	private Color fullWall;
	private Color unfullWall;
	
//	繪製座標(暫時繪製)
	private int[] drawCoordinates;
	
//	牆壁
	private int[] wall;
	private boolean setWall_Switch; //放置牆壁之開關
	private boolean Wall_HorizontalOrVertical_Switch; //牆壁為水平還是垂直 。Horizontal false 。Vertical true
	private boolean Wall_Switch;
	
//	玩家
	private int NumberOfPlayers;
	private int[] coordinateYX; //座標 YX。[0] Y 。[1] X
	
	private boolean setPlayer_Switch; //放置玩家棋之開關
	
//	棋盤
	private int[][] Checkerboard;
	
//	遊戲正式開始開關
	private boolean initGame; //遊戲初始化設定: (確定人數用) false 確定玩家人數 true 未確定玩家人數 
	
//	點擊後結果
//	 - 1: 垂直牆壁
//	 - 2: 水平牆壁
//	 - 3: 玩家棋
//		使用於更新棋盤資訊
//	public int choice;
	
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
		Checkerboard = new int[17][17]; //棋盤資訊
		
//		玩家		
		coordinateYX = new int[2]; //座標 YX。[0] Y 。[1] X
		
		setPlayer_Switch = false;
		
//		牆壁
		wall = new int[4];
		drawCoordinates = new int[2];
		setWall_Switch = false;
		Wall_Switch = true;
		
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
		t_player_Color = new Color(102, 255, 102); 	//淡橘色
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
		if(NumberOfPlayers == 2) {
			wall = new int[] {10,10,0,0};
		}else if(NumberOfPlayers == 3) {
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
//		if(AnnotationSwitch) {
//			System.out.println(" [+] paintComponent() -> Checkerboard");
//		}
		
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;

//    	方格
		for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            	g2d.setColor(drawRect_Color);
            	g2d.fillRect(x*CELL_SIZE + deviation, y*CELL_SIZE + deviation, CELL_SIZE - deviation, CELL_SIZE - deviation);
            }
		}
		
//		預設
		for(int i = 0;i<Checkerboard.length;i++) {
			for(int j = 0;j<Checkerboard.length;j++) {
				if(Checkerboard[i][j]/10 == 2				//玩家 -----------------------------------------------------------
						&& Checkerboard[i][j]%10 < 5
						&& Checkerboard[i][j]%10 > 0) {
					Ellipse2D circle = new Ellipse2D.Double(j/2*CELL_SIZE + deviation +20, i/2*CELL_SIZE  + deviation +20, 25, 25); // (x, y, width, height)
			    	g2d.setColor(player_Color);
			        g2d.fill(circle);
			        g2d.draw(circle);
				}				
				
				if(Checkerboard[i][j] == 11) {				//牆壁 -----------------------------------------------------------
					if(i % 2 == 0) {
//						由上往下
			            g2d.fillRect((j/2)*CELL_SIZE+65 + deviation, (i/2)*CELL_SIZE + deviation, deviation, CELL_SIZE - deviation);
					}else {
//						由左往右
						if(j%2 == 0) { //大
							g2d.fillRect((j/2)*CELL_SIZE + deviation, (i/2)*CELL_SIZE+65 + deviation, CELL_SIZE - deviation, deviation);
						}else {			//小
							g2d.fillRect((j/2)*CELL_SIZE+65 + deviation, (i/2)*CELL_SIZE+65 + deviation, deviation, deviation);
						}
					}
				}
			}
		}
		
//	    玩家棋
		if(setPlayer_Switch) {
			Ellipse2D circle = new Ellipse2D.Double(drawCoordinates[0]*CELL_SIZE + deviation +20, drawCoordinates[1]*CELL_SIZE  + deviation +20, 25, 25); // (x, y, width, height)
	    	g2d.setColor(t_player_Color);
	        g2d.fill(circle);
	        g2d.draw(circle);
		}
		
//		牆壁
		if(Wall_Switch) {
			if(setWall_Switch) {
				g2d.setColor(wall_Color);
				if(Wall_HorizontalOrVertical_Switch) {
//					由上往下
		            g2d.fillRect(drawCoordinates[0]*CELL_SIZE + CELL_SIZE, drawCoordinates[1]*CELL_SIZE + deviation, deviation, 2*CELL_SIZE - deviation);
				}else {
//					由左往右
		            g2d.fillRect(drawCoordinates[0]*CELL_SIZE + deviation, drawCoordinates[1]*CELL_SIZE + CELL_SIZE, 2*CELL_SIZE - deviation, deviation);
				}
//				setWall_Switch = false;   
			}
//			牆壁剩餘數量
			for(int i = 0;i<NumberOfPlayers;i++) {
				if((NumberOfPlayers == 2 && wall[i] == 10) 
				|| (NumberOfPlayers == 3 && wall[i] == 6) 
				|| (NumberOfPlayers == 4 && wall[i] == 5)) {
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
		
		super.repaint();
	}

	/**
	 * 滑鼠按下
	 * */
	public void MPressed(int x, int y) {
//		System.out.println(x + ":" + y);
		
		initGame = false;
		
		if(wall[0] != 0) {
			wall[0]--;
			wall[1]--;
			wall[2]--;
			wall[3]--;
		}else {
			Wall_Switch = false;
		}

//		getLocation(x - deviation, y - deviation); //補上誤差值(邊界外的牆壁表示)
	}
	
	/**
	 * 滑鼠釋放
	 * */
	public void MReleased() {
		/***/
		setWall_Switch = false;
		setPlayer_Switch = false;
	}
	
//	/**
//	 * 滑鼠拖曳
//	 * */
//	public void MDragged(int x, int y) {
////		getLocation(x - deviation, y - deviation); //補上誤差值
//	}
	
	/**
	 * 數值轉換座標
	 * */
	public void exchangeCoordinate(int value) {
		coordinateYX[0] = value / 10; //座標 Y
		coordinateYX[1] = value % 10; //座標 X
	}
	
	/**
	 * 設定遊戲初始化旗標
	 * */
	public void setInitGame() {
		initGame = true;
		Wall_Switch = true;
	}
	
	/**
	 * 取得遊戲初始化旗標
	 * */
	public boolean getInitGame() {return initGame;}
	
	/**
	 * 設定玩家人數
	 * */
	public void setNumberOfPlayers(int total) {this.NumberOfPlayers =  total;}
	
	/**
	 * 設定棋盤值
	 * */
	public void updataCheckerboard(int[][] Checkerboard, boolean flag) {
		this.Checkerboard = Checkerboard;	//更新棋盤資訊
		
		if(flag) initWall();				//遊戲開始前的牆壁分配
	}
	
	/***/
//	public void setChoice(int value){choice = value;}
	
	/***/
	public void setWall_HorizontalOrVertical_Switch(boolean value) {Wall_HorizontalOrVertical_Switch = value;}
	
	/***/
	public void setsetWall_Switch(boolean value) {setWall_Switch = value;}
	
	/***/
	public void setDrawCoordinates(int index, int value) {drawCoordinates[index] = value;}
	
	/***/
	public void setsetPlayer_Switch(boolean value) {setPlayer_Switch = value;}
}
