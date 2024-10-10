package T2_;

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
public class Checkerboard extends JPanel { //

//	private JFrame frame;
	
	private int CELL_SIZE;
	private int width;
	private int height;
	
//	private int width_frame;
//	private int height_frame;
	
//	private int x_deviation; //誤差值
//	private int y_deviation; //誤差值
	private int deviation; //壕溝寬度
	
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
	
	private checkerboardListener mlistener; //棋盤監聽器
	
	private boolean AnnotationSwitch; //註解開關
	
	private int[] wall; //北南西東
	
	private int[] setWall_Coordinate; //放置牆壁之座標
	private boolean setWall_Switch; //放置牆壁之開關
	private boolean Wall_HorizontalOrVertical_Switch; //牆壁為水平還是垂直 。Horizontal false 。Vertical true
	
	private int[] NumberOfPlayers;
//	private int NumberOfPlayers;
	private int[] tempPlayersCoordinate;
	
	public boolean mouseclicked; //判斷是否點擊點擊鼠標
	
	private Color fullWall;
	private Color unfullWall;
	
	private int[][] infCheckerboard;
	private int[][] temp_InfCheckerboard;
	
	private boolean initGame; //遊戲初始化設定: (確定人數用) false 確定玩家人數 true 未確定玩家人數 
	
	private int[][] coordinateYX; //座標 YX[玩家][座標] 。[0] 沒有使用 。[1~4(北南西東)]。[0] Y 。[1] X
	
	/**
	 * 建構子
	 * */
	public Checkerboard() {
		
	}
	
	/**
	 * 建構子
	 * */
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
		drawRect_Color = new Color(0, 0, 0); //黑色
		background_Color = new Color(132, 0, 0); //深深咖啡色
		player_Color = new Color(255, 204, 153); //淡橘色
		wall_Color = new Color(153, 153, 255); //淡紫色
		
		mlistener = new checkerboardListener();
		
		setWall_Coordinate = new int[2];
		setWall_Switch = false;
		
		mouseclicked = false;
		
		NumberOfPlayers = new int[5]; //[0]: 遊戲人數 [1~4]: 玩家位置 (北南西東)
		tempPlayersCoordinate = new int[5]; //對應 NumberOfPlayers，[0] 沒有使用
//		tempPlayersCoordinate[0] = 100000000; //-------------------------------------------------- 測試用
//		Arrays.fill(tempPlayersCoordinate, 0); //tempPlayersCoordinate 初始化為 0
//		System.out.println(Arrays.toString(tempPlayersCoordinate)); //---------------------------- 測試用
		
		wall = new int[] {10,10,0,0};
//		wall = new int[] {6,6,6,0}; //------------------------------------------------------------ 測試用
		
		fullWall = new Color(0, 153, 51); //深綠色
		unfullWall = new Color(255, 204, 153); //淡橘色
		
		coordinateYX = new int[5][2]; //座標 YX[玩家][座標] 。[0] 沒有使用 。[1~4(北南西東)]。[0] Y 。[1] X

//		棋盤初始化
		initCheckerboard();
				
		initGame = true;
//		棋盤監聽器
		addMouseListener(mlistener);
		addMouseMotionListener(mlistener);
		setBackground(Color.black);
		
//		執行續設定 --------------------------------------------------
//		SwingUtilities.invokeLater(() -> {
//	        ColorOutputExample example = new ColorOutputExample();
//	        example.setVisible(true);
//	    });
//		-------------------------------------------------- 執行續設定
		
//		setSize(0, 0); //------------------------------------------------------------------------------------------ 測試用
//		setSize(new Dimension(width*CELL_SIZE, height*CELL_SIZE)); //---------------------------------------------- 測試用
//		setSize(width*CELL_SIZE + x_deviation*2, height*CELL_SIZE + y_deviation*3+5); //--------------------------- 測試用
		setSize(width*CELL_SIZE + deviation, height*CELL_SIZE + deviation);
//		setPreferredSize(new Dimension(width*CELL_SIZE + deviation, height*CELL_SIZE + deviation)); //------------- 測試用
		
		setBackground(background_Color);
		
//		createFrame(); //------------------------------------------------------------------------------------------ 測試用
//		frame.getContentPane().add(this); //----------------------------------------------------------------------- 測試用
	}
	
//	建立框架 -----------------------------------------------------------------------------------------------
//	public void creatdeFrame() {
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
//	----------------------------------------------------------------------------------------------- 建立框架
	
	@Override
	public void paintComponent(Graphics g) {
//		註解輸出
		if(AnnotationSwitch) {
			System.out.println(" [+] paintComponent() -> Checkerboard1");
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
		if(setWall_Switch) {
			g2d.setColor(wall_Color);
			if(Wall_HorizontalOrVertical_Switch) {
//				由上往下ㄋ
	            g2d.fillRect(setWall_Coordinate[0]*CELL_SIZE + CELL_SIZE, setWall_Coordinate[1]*CELL_SIZE + deviation, deviation, 2*CELL_SIZE - deviation);
			}else {
//				由左往右
	            g2d.fillRect(setWall_Coordinate[0]*CELL_SIZE + deviation, setWall_Coordinate[1]*CELL_SIZE + CELL_SIZE, 2*CELL_SIZE - deviation, deviation);
			}
			setWall_Switch = false;   
		}
//		牆壁剩餘數量
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
		
//		註解輸出
		if(AnnotationSwitch) System.out.println("\t  - Player Chess\n"
												+ "\t\t - NumberOfPlayers[0] = " + NumberOfPlayers[0]);
		
//	    玩家棋		
		for(int i = 1;i<=NumberOfPlayers[0];i++) { //玩家順序： 北南西東
//			註解輸出
			if(AnnotationSwitch) {
				System.out.println("\t\t    - Players " + i
								+ "\n\t\t      - coordinateYX[i][1]:coordinateYX[i][0] = " + coordinateYX[i][1] + ":" + coordinateYX[i][0] + "\n");
			}
			
			Ellipse2D circle = new Ellipse2D.Double(coordinateYX[i][1]*CELL_SIZE + deviation +20, coordinateYX[i][0]*CELL_SIZE  + deviation +20, 25, 25); // (x, y, width, height)
	    	g2d.setColor(player_Color);
	        g2d.fill(circle);
	        g2d.draw(circle);
		}
		
//        if(NumberOfPlayers[0] == 2) {
////        	北
//			Ellipse2D circle = new Ellipse2D.Double(4*CELL_SIZE + deviation +20, 0*CELL_SIZE  + deviation +20, 25, 25); // (x, y, width, height)
//	    	g2d.setColor(player_Color);
//	        g2d.fill(circle);
//	        g2d.draw(circle);
////	        南
//	        circle = new Ellipse2D.Double(4*CELL_SIZE + deviation +20, 8*CELL_SIZE  + deviation +20, 25, 25);
//	        g2d.fill(circle);
//	        g2d.draw(circle);
//        }else if(NumberOfPlayers[0] == 3) {
////			北
//			Ellipse2D circle = new Ellipse2D.Double(4*CELL_SIZE + deviation +20, 0*CELL_SIZE  + deviation +20, 25, 25); // (x, y, width, height)
//	    	g2d.setColor(player_Color);
//	        g2d.fill(circle);
//	        g2d.draw(circle);
////	        南
//	        circle = new Ellipse2D.Double(4*CELL_SIZE + deviation +20, 8*CELL_SIZE  + deviation +20, 25, 25);
//	        g2d.fill(circle);
//	        g2d.draw(circle);
////	        西
//			circle = new Ellipse2D.Double(0*CELL_SIZE + deviation +20, 4*CELL_SIZE  + deviation +20, 25, 25); // (x, y, width, height)
//	    	g2d.setColor(player_Color);
//	        g2d.fill(circle);
//	        g2d.draw(circle);
//		}else {
////			北
//			Ellipse2D circle = new Ellipse2D.Double(4*CELL_SIZE + deviation +20, 0*CELL_SIZE  + deviation +20, 25, 25); // (x, y, width, height)
//	    	g2d.setColor(player_Color);
//	        g2d.fill(circle);
//	        g2d.draw(circle);
////	        南
//	        circle = new Ellipse2D.Double(4*CELL_SIZE + deviation +20, 8*CELL_SIZE  + deviation +20, 25, 25);
//	        g2d.fill(circle);
//	        g2d.draw(circle);
////			西
//			circle = new Ellipse2D.Double(0*CELL_SIZE + deviation +20, 4*CELL_SIZE  + deviation +20, 25, 25); // (x, y, width, height)
//	    	g2d.setColor(player_Color);
//	        g2d.fill(circle);
//	        g2d.draw(circle);
////	        東
//	        circle = new Ellipse2D.Double(8*CELL_SIZE + deviation +20, 4*CELL_SIZE  + deviation +20, 25, 25); // (x, y, width, height)
//	    	g2d.setColor(player_Color);
//	        g2d.fill(circle);
//	        g2d.draw(circle);
//		}
        
//		備用方法 ----------------------------------------------------------------------
//			+ [格線]
//				for (int y = 0; y < height; y++) {
//		            for (int x = 0; x < width; x++) {
//		
//		            	g2d.setColor(drawRect_Color);
//		            	g2d.drawRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
//		            }
//				}
//        
//        	+ [物件間距]
//        		g2d.setStroke(new BasicStroke(2));
//		----------------------------------------------------------------------- 備用方法
	}
	
	/**
	 * 取的點擊位置
	 * */
	private void getLocation(int mX, int mY) { //mX, mY: 游標位置。 (已補上誤差值)
		int x = mX / CELL_SIZE; //棋盤格座標
		int y = mY / CELL_SIZE;
		
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
			wall[0]--;
			wall[1]--;
			wall[2]--;
			wall[3]--;
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
	 * 設定玩家人數
	 * */
	public void setNumberOfPlayers(int NOPlayers) {
		NumberOfPlayers[0] = NOPlayers;
				
		if(NumberOfPlayers[0] == 2) {
			wall = new int[] {10,10,0,0};
		}else if(NumberOfPlayers[0] == 3) {
			wall = new int[] {6,6,6,0};
//			NumberOfPlayers[3] = 40; //西 - yx = 40
		}else {
			wall = new int[] {5,5,5,5};
//			NumberOfPlayers[4] = 47; //東 - yx = 47
		}
		
		exchangeCoordinate(NumberOfPlayers);
		
//		輸出棋盤資料
		if(AnnotationSwitch) {
			System.out.println(" [+] setNumberOfPlayers() -> Checkerboard1\n"
								+ "\t - NumberOfPlayers[0] = " + NumberOfPlayers[0]);
			for(int i = 1;i<=NumberOfPlayers[0];i++) {
				System.out.println("\t - coordinateYX[" + i + "][] = " + Arrays.toString(coordinateYX[i]));
			}
			System.out.println();
		}
		
		saveInfCheckerboard();
		
		repaint();
	}
	
	/**
	 * 取得玩家人數
	 * */
	public int getNumberOfPlayers() {
		return NumberOfPlayers[0];
	}
	
	/**
	 * 棋盤初始化
	 * */
	public void initCheckerboard() {
//		輸出棋盤資料
		if(AnnotationSwitch) {
			System.out.println(" [+] initCheckerboard() -> Checkerboard1");
		}
		
		/**
		 * 棋盤資訊
		 * 	- 0 空	。0	以知不能動
		 *  - 1 牆	。10	沒有牆	 。11 有牆
		 *  - 2 人	。20	空位		 。21 Py1	。22 Py2		。23	Py3		。24 Py4
		 * */
		infCheckerboard = new int[17][17];
		temp_InfCheckerboard = new int[17][17];
		
		initNumberOfPlayersCoordinate(); //玩家人數及初始座標初始化
		
//		初始化
		for(int i = 0;i<infCheckerboard.length;i++) {
			Arrays.fill(infCheckerboard[i], 20); //預設玩家都可以走
		}
		
//		空
		for(int i = 1;i<infCheckerboard.length;i=i+2) {
			for(int j = 1;j<infCheckerboard.length;j=j+2) {
				infCheckerboard[i][j] = 0;
			}
		}
		
//		牆
		for(int i = 0;i<infCheckerboard.length;i++) {
			for(int j = 1;j<infCheckerboard.length;j=j+2) {
				if(i % 2 == 0) {
					infCheckerboard[i][j] = 10;
				}else {
					infCheckerboard[i][j-1] = 10;
				}
			}
			
			if(i % 2 == 1) { //補最右邊
				infCheckerboard[i][16] = 10;
			}
		}
		
//		temp_InfCheckerboard = infCheckerboard.clone(); //---------------------------------------------- 複製失敗
//		temp_InfCheckerboard = Arrays.copyOf(infCheckerboard, infCheckerboard.length); //--------------- 複製失敗
		
//		測試用 ------------------------------------------------------------------------
//		infCheckerboard[0][0] = 100000000;
//		
//		if(AnnotationSwitch) {
////			輸出 temp_InfCheckerboard
//			int Row = 0; 
//			System.out.println("\ttemp_InfCheckerboard\n"
//									+ "\t0\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\n"
//									+ "\t----------------------------------------------------------------------------------------------------------------------------------");
//			for(int i = 0;i<temp_InfCheckerboard.length;i++) {
//				for(int j = 0;j<temp_InfCheckerboard.length;j++) {
//					System.out.print("\t" + temp_InfCheckerboard[i][j]);
//				}
//				if(i%2 == 0) {
//					System.out.println("\t-> " + Row++);
//				}else {
//					System.out.println();
//				}
//			}
//		}
//		------------------------------------------------------------------------ 測試用
		
		exchangeCoordinate(NumberOfPlayers);
		
//		第一次暫存
		saveInfCheckerboard();
	}
	
	/**
	 * 取得遊戲初始化旗標
	 * */
	public boolean getInitGame() {
		return initGame;
	}
	
	/**
	 * 轉換座標
	 * 	- 將 NumberOfPlayers[1~4] 數值轉換座標
	 *  - coordinateYX[0~3][0] 。座標 Y	[1] 。座標 X
	 * */
	public void exchangeCoordinate(int[] value) { //NumberOfPlayers[0] 為人數 。[1~4]為玩家
		for(int i = 1;i<=NumberOfPlayers[0];i++) {
			if(NumberOfPlayers[i] != tempPlayersCoordinate[i]) {
//				System.out.println("\t - exchange " + i);
//				第一次儲存玩家座標
				tempPlayersCoordinate[i] = NumberOfPlayers[i];
//				數值轉換座標
				coordinateYX[i][0] = value[i] / 10; //座標 Y
				coordinateYX[i][1] = value[i] % 10; //座標 X
//				加載入棋盤中
				infCheckerboard[coordinateYX[i][0] *2][coordinateYX[i][1] *2] = 20 + i;
			}else {
//				System.out.println("\t - exchange None");
				continue;
			}
		}
		
//		輸出棋盤資料
		if(AnnotationSwitch) {
			System.out.println(" [+] exchangeCoordinate() -> Checkerboard1");
			for(int i = 1;i<=NumberOfPlayers[0];i++) {
				System.out.println("	Player" + i + " -> coordinateY:coordinateX = " + coordinateYX[i][0] + ":" + coordinateYX[i][1]);
			}
			System.out.println();
		}
	}
	
	/**
	 * 玩家人數及初始座標初始化
	 * */
	public void initNumberOfPlayersCoordinate() {
		NumberOfPlayers[0] = 2;
		NumberOfPlayers[1] = 4;  //北 - yx = 04
		NumberOfPlayers[2] = 84; //南 - yx = 84
		NumberOfPlayers[3] = 40; //西 - yx = 40 ------------------------- 測試用
		NumberOfPlayers[4] = 48; //東 - yx = 48 ------------------------- 測試用
		
//		tempPlayersCoordinate[0] = 100000000; //-------------------------------------------------- 測試用
		Arrays.fill(tempPlayersCoordinate, 0); //tempPlayersCoordinate 初始化為 0
//		System.out.println(Arrays.toString(tempPlayersCoordinate)); //---------------------------- 測試用
		
//		輸出棋盤資料
		if(AnnotationSwitch) {
			System.out.println(" [+] initNumberOfPlayersCoordinate() -> Checkerboard1\n"
								+ "	- NumberOfPlayers[All] = " + Arrays.toString(NumberOfPlayers) + "\n");
		}
	}
	
	/**
	 * 輸出棋盤資訊
	 * */
	public void printCheckerboard() {
//		輸出棋盤資料
		if(AnnotationSwitch) {
			System.out.println(" [+] printCheckerboard() -> Checkerboard1");
		}
		
		int Row = 0; 
		System.out.println("      0 - 例外區域\n"
						 + "     10 - 牆壁可放置位置\n"
						 + "     20 - 玩家可移動方格\n"
						 + "     21、22、23、24 - 玩家 1、2、3、4\n"
						 + "\tinfCheckerboard\n"
						 + "\t0\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\n"
						 + "\t----------------------------------------------------------------------------------------------------------------------------------");
		for(int i = 0;i<infCheckerboard.length;i++) {
			for(int j = 0;j<infCheckerboard.length;j++) {
				System.out.print("\t" + infCheckerboard[i][j]);
			}
			if(i%2 == 0) {
				System.out.println("\t-> " + Row++);
			}else {
				System.out.println();
			}
		}
		System.out.println("\n");
		
//		輸出 temp_InfCheckerboard
		Row = 0; 
		System.out.println("\ttemp_InfCheckerboard\n"
								+ "\t0\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\n"
								+ "\t----------------------------------------------------------------------------------------------------------------------------------");
//		System.out.println(temp_InfCheckerboard[8][4]);
		for(int i = 0;i<temp_InfCheckerboard.length;i++) {
			for(int j = 0;j<temp_InfCheckerboard.length;j++) {
				System.out.print("\t" + temp_InfCheckerboard[i][j]);
			}
			if(i%2 == 0) {
				System.out.println("\t-> " + Row++);
			}else {
				System.out.println();
			}
		}
		System.out.println();
	}
	
	/**
	 * 儲存棋盤資料
	 * */
	public void saveInfCheckerboard() {
//		輸出棋盤資料
		if(AnnotationSwitch) {
			System.out.println(" [+] saveInfCheckerboard() -> Checkerboard1");
		}
		
		for(int i = 0;i<temp_InfCheckerboard.length;i++) {
			for(int j = 0;j<temp_InfCheckerboard.length;j++) {
				temp_InfCheckerboard[i][j] = infCheckerboard[i][j];
			}
		}
		
		printCheckerboard();
	}
}
