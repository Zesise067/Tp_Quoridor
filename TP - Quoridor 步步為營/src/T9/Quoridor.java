package T9;

import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.SwingUtilities;

public class Quoridor {
//	基本變數
	private boolean AnnotationSwitch; //註解開關
	
//	玩家
	private int[] NumberOfPlayers; //玩家人數 。[0] 玩家人數 。[1-4] 玩家座標
	
	private int[] coordinateYX; //座標 YX	 。[0] Y 。[1] X
	
//	棋盤
	private int[][] Checkerboard; //棋盤資訊

//	四個方向 -> 東西南北 {y, x}
//	int[][] position = {{0,1},{0,-1},{1,0},{-1,0}};
////	  				  右		左	   下	  上
	int[][] position = {{1,0},{0,1},{0,-1},{-1,0}};
//	  					  下		右	   左	  上
//	int[][] position = {{-1,0},{0,1},{0,-1},{1,0}};
////	  						上		右	   左	 下 
	
//	探索路徑(紀錄路徑)
	private int[][] Map = new int[17][17];
	
//	使用於終點方置
	private int[] setEnd_value = new int[2];
	
//	使否找到終點
	private boolean findEnd = false;
	
//	牆壁
	public int[] wall;
	
	
	/**
	 * 建構子
	 * */
	public Quoridor() {}
	
	
	public Quoridor(boolean AnnotationSwitch) {
		
		this.AnnotationSwitch = AnnotationSwitch;	//註解開關
		
		init(); //初始化變數
	}
	
	/**
	 * 初始化變數
	 * */
	public void init() {
//		輸出棋盤資料
		if(AnnotationSwitch) System.out.println(" [+] init() -> Quoridor");
		
//		棋盤
		Checkerboard = new int[17][17]; //棋盤資訊
//		tempCheckerboard = new int[17][17]; //暫存棋盤資訊
		
		initInfCheckerboard(); //初始化棋盤
		
//		玩家
		NumberOfPlayers = new int[5]; //玩家人數 。[0] 玩家人數 。[1-4] 玩家座標
		coordinateYX = new int[2]; //座標 YX	 。[0] Y 。[1] X
		
		NumberOfPlayers[0] = 3;
//		NumberOfPlayers[1] = 4;  //北 - yx = 04
//		NumberOfPlayers[2] = 84; //南 - yx = 84
//		NumberOfPlayers[3] = 40; //西 - yx = 40 ------------------------- 測試用
//		NumberOfPlayers[4] = 48; //東 - yx = 48 ------------------------- 測試用
		
		initPlayerCoordinate(); //初始化玩家座標
		
//		終點放置
		setEnd_value[0] = 0;
		setEnd_value[1] = 16;
		
		initWall();
		
//		加載遊戲規則
		GameRules(1);
//		-------------------------------- 轉移

	}
	
	/**
	 * 棋盤初始化
	 * */
	public void initInfCheckerboard() {		
		/**
		 * 棋盤資訊
		 * 	- 0 空	。0	以知不能動
		 *  - 1 牆	。10	沒有牆	 。11 有牆
		 *  - 2 人	。20	空位		 。21-4 Py1-4 。25 可移動位置
		 * */
//		初始化
		for(int i = 0;i<Checkerboard.length;i++) Arrays.fill(Checkerboard[i], 0);
		
//		註解
		if(AnnotationSwitch) {
			System.out.println(" [+] initCheckerboard() -> Quoridor");
		}
		
//		TEST -------------------------------------------------------------------------------------------------------
//		Checkerboard[1][0] = 11; 
//		Checkerboard[1][1] = 11; 
//		Checkerboard[1][2] = 11; // ---
//		
//		Checkerboard[6][3] = 11; 
//		Checkerboard[4][3] = 11; 
//		Checkerboard[5][3] = 11; // |
//		
//
//		Checkerboard[1][12] = 11; 
//		Checkerboard[1][13] = 11; 
//		Checkerboard[1][14] = 11; 
//		Checkerboard[1][15] = 11; 
////		Checkerboard[1][16] = 11; // ---
//		
//		Checkerboard[14][1] = 11; 
//		Checkerboard[15][1] = 11; 
//		Checkerboard[16][1] = 11; // |
//		
////		Checkerboard[14][15] = 11; 
////		Checkerboard[15][15] = 11; 
////		Checkerboard[16][15] = 11; // |
//		
//		Checkerboard[15][14] = 11; 
//		Checkerboard[15][15] = 11; 
//		Checkerboard[15][16] = 11; // ---
//		
//		Checkerboard[1][11] = 11; 
//		Checkerboard[2][11] = 11; // |
//		Checkerboard[3][11] = 11; 
//		
//		Checkerboard[1][13] = 11; 
//		Checkerboard[2][13] = 11; // |
//		Checkerboard[3][13] = 11; 
//		
//		Checkerboard[7][4] = 11; 
//		Checkerboard[7][5] = 11; // -
//		Checkerboard[7][6] = 11; 
//		
//		
//		Checkerboard[2][3] = 11; // |
//		
//		Checkerboard[7][10] = 11; 
//		Checkerboard[7][11] = 11; 
//		Checkerboard[7][12] = 11; // -
//		
//		Checkerboard[7][14] = 11; 
//		Checkerboard[7][15] = 11; // -
//		Checkerboard[7][16] = 11; 
//		
//		Checkerboard[9][14] = 11; 
//		Checkerboard[9][15] = 11; // -
//		Checkerboard[9][16] = 11; 
//		
//		Checkerboard[9][8] = 11; 
//		Checkerboard[9][7] = 11; // -
//		Checkerboard[9][6] = 11; 
//		Checkerboard[9][9] = 11; 
//		
////		Checkerboard[0][9] = 11; 
//		
//		Checkerboard[0][6] = 23; 
//		------------------------------------------------------------------------------------------------------- TEST
	}
	
	/**
	 * 初始化玩家座標
	 * */
	public void initPlayerCoordinate() {
//		輸出棋盤資料
		if(AnnotationSwitch) {
			System.out.println(" [+] initPlayerCoordinate() -> Quoridor\n"
								+ "	- NumberOfPlayers[All] = " + Arrays.toString(NumberOfPlayers));
		}
		
		if(NumberOfPlayers[0] == 2) {
			NumberOfPlayers[1] = 4;  //北 - yx = 04
			NumberOfPlayers[2] = 84; //南 - yx = 84
		}else {
			NumberOfPlayers[1] = 4;  //北 - yx = 04
			NumberOfPlayers[2] = 40; //西 - yx = 40
			NumberOfPlayers[3] = 84; //南 - yx = 84
			NumberOfPlayers[4] = 48; //東 - yx = 48
		}
		
//		加載玩家位置
//		修改 --------------------------------------------------------------------
//		int i = 1;
//		while(i<=NumberOfPlayers[0]) {
//			
//		}
//		------- 移除 ------------------------------------------------------------
				for(int ii = 1;ii<=NumberOfPlayers[0];ii++) {
					int[] XY = ExCoordinate(NumberOfPlayers[ii]);
					
					Checkerboard[XY[1]][XY[0]] = 20 + ii;
					
				}
//		------------------------------------------------------------------- 移除
	}
	
	/**
	 * 輸出棋盤資訊
	 * */
	public void printCheckerboard() {
//		輸出棋盤資料
//		if(AnnotationSwitch) {
			System.out.println(" [+] printCheckerboard() -> Quoridor");
			
//			輸出 tempInfCheckerboard
			int Row = 0; 
			System.out.println("      0 - 例外區域\n"
							 + "     10 - 牆壁可放置位置\n"
							 + "     20 - 玩家可移動方格\n"
							 + "     21、22、23、24 - 玩家 1、2、3、4\n"
							 + "     25 - 可移動位置"
							 + "\tinfCheckerboard\n"
							 + "\t0\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\n"
							 + "\t----------------------------------------------------------------------------------------------------------------------------------");
			for(int i = 0;i<Checkerboard.length;i++) {
				for(int j = 0;j<Checkerboard.length;j++) {
					System.out.print("\t" + Checkerboard[i][j]);
				}
				if(i%2 == 0) {
					System.out.println("\t-> " + Row++);
				}else {
					System.out.println();
				}
			}
			System.out.println();
//		}
	}
	
	/**
	 * 遊戲規則
	 *  - infCheckerboard
	 *  		 0 - 例外區域\n
	 *  		10 - 牆壁可放置位置
	 *  		20 - 玩家可移動方格
	 *  	  21~4 - 玩家 1~4
	 *  - coordinateYX[玩家][座標]
	 *  		。[0] 進攻方		。[0] Y 
	 *  		。[1~4(北南西東)]	。[1] X
	 * */
	public void GameRules(int Player) {
//		輸出棋盤資料
		if(AnnotationSwitch) System.out.println(" [+] GameRules() -> Quoridor");
		
		for(int i = 0;i<Checkerboard.length;i++) {
			for(int j = 0;j<Checkerboard.length;j++) {
				if(Checkerboard[i][j] == 25) Checkerboard[i][j] = 0;
			}
		}
			
		exchangeCoordinate(NumberOfPlayers[Player]);
		int y = coordinateYX[0]; //座標 Y
		int x = coordinateYX[1]; //座標 X
		
		explore(y, x, true); //探索可放置位置
		
		Checkerboard[y*2][x*2] = 20 + Player;
		
//		TEST ------------------------------------------------------------------------------------------- 
//		for(int i = 1;i<=NumberOfPlayers[0];i++) { //人數
//			exchangeCoordinate(NumberOfPlayers[i]);
//			y = coordinateYX[0]; //座標 Y
//			x = coordinateYX[1]; //座標 X
//			
//			explore(y, x, true); //探索可放置位置
//			
//			infCheckerboard[y*2][x*2] = 20+i;
//			
//		}
//		------------------------------------------------------------------------------------------- TEST

//		printCheckerboard(); //輸出陣列資訊
			
	}
	
	/**
	 * 探索可放置位置
	 * */
	public void explore(int y, int x, boolean flag) {
//		輸出棋盤資料
		if(AnnotationSwitch) System.out.println(" [+] explore() -> Quoridor");
			
//			尋找可移動位置
			for(int j = 0;j<position.length;j++) { //四個方向
				if((y*2 + position[j][0]*2 > 16)	//下		避免超出索引值
				 ||(y*2 + position[j][0]*2 < 0)		//上
				 ||(x*2 + position[j][1]*2 > 16)	//右
				 ||(x*2 + position[j][1]*2 < 0)		//左
				) {
					continue;
				}else {
//					為牆壁 -----------------------------------------------------------------------------
					if(Checkerboard[y*2 + position[j][0]][x*2 + position[j][1]] == 11) {
//						               |-|   				 |-|					轉棋盤位置座標
//									  |--------------------||--------------------|	牆壁位置
						continue;
						
//					為玩家 -----------------------------------------------------------------------------
					}else if(Checkerboard[y*2 + position[j][0]*2][x*2 + position[j][1]*2]/10 == 2
							 && Checkerboard[y*2 + position[j][0]*2][x*2 + position[j][1]*2]%20 < 5) {
//						
						if(!flag) continue;
						
//						(再向前一步)避免超出索引值
						if((y*2 + position[j][0]*3 > 16)	//下
						 ||(y*2 + position[j][0]*3 < 0)		//上
						 ||(x*2 + position[j][1]*3 > 16)	//右
						 ||(x*2 + position[j][1]*3 < 0)) { 	//左
							continue;
						}
						
//						玩家後面還有玩家或牆壁
						if((Checkerboard[y*2 + position[j][0]*4][x*2 + position[j][1]*4]/10 == 2
							&& Checkerboard[y*2 + position[j][0]*4][x*2 + position[j][1]*4]%20 < 5
							|| Checkerboard[y*2 + position[j][0]*3][x*2 + position[j][1]*3] == 11)) {	
//							continue;
//							再探索
							if(flag) {
								explore(y + position[j][0], x + position[j][1], false);
							}
						}else {
							Checkerboard[y*2 + position[j][0]*4][x*2 + position[j][1]*4] = 25;
						}
					}
					
//					無障礙
					else {
						Checkerboard[y*2 + position[j][0]*2][x*2 + position[j][1]*2] = 25;
					}
				}
			}
	}
	
//	類型一 ----------------------------------------------------------------------------------------------------------------
	/**
	 * 數值轉換座標
	 * */
	public void exchangeCoordinate(int value) {
		coordinateYX[0] = value / 10; //座標 Y
		coordinateYX[1] = value % 10; //座標 X
		
//		輸出棋盤資料
		if(AnnotationSwitch) {
			System.out.println(" [+] exchangeCoordinate() -> Quoridor\n"
								+ "\tcoordinateYX - (y,x) = (" + coordinateYX[0] + "," + coordinateYX[1] + ")");
		}
	}
//	類型二 ----------------------------------------------------------------------------------------------------------------
	/**
	 * 數值轉換座標
	 * */
	public int[] ExCoordinate(int value) {
		int[] XY = new int[] {(value % 10 * 2), (value / 10 * 2)};
		
		return XY;
	}
	
	/**
	 * 設定玩家人數
	 * */
	public void setNumberOfPlayers(int NumberOfPlayers) {
		this.NumberOfPlayers[0] =  NumberOfPlayers;
		
		initInfCheckerboard();
		initPlayerCoordinate();
//		saveCheckerboard();
		GameRules(1);
	}
	
	/**
	 * 回傳玩家人數
	 * */
	public int[] getNumberOfPlayers() {
//		return NumberOfPlayers.clone();
		return NumberOfPlayers;
	}
	
	/**
	 * 回傳棋盤值
	 * */
	public int[][] getCheckerboard() {
//		return infCheckerboard.clone();
		return Checkerboard;
	}
	

//	修改 -------------------------------------------------------------------
	/**
	 * 探險家(走訪路徑) -> 待改良 <-------------------------------------
	 * */
    public boolean Adventurer() {
    	
    	// 針對每一名玩家做死路判斷
    	for(int i = 1;i<=NumberOfPlayers[0];i++) {
    																// 初始化
        	findEnd = false;											// 是否找到終點
        	for(int ii = 0;ii<Map.length;ii++) Arrays.fill(Map[ii], 0);	// 探險路徑

    		int[] XY = ExCoordinate(NumberOfPlayers[i]);	// 座標轉換
        	
        	setEnd(i, true); 			// 設定終點
        	
        	Adventure(XY[0], XY[1]);	//走訪終點
        	
        	setEnd(i, false);			//去除終點
        	
//    		列印 ----------------------------------------------
	    	System.out.print("  ");
			for(int j = 0;j<Map.length;j++) {
				if(j%2 == 0) System.out.print(j/2 + "   ");
	    	}
	    	System.out.println();
	    	
	    	for(int j = 0;j<Map.length;j++) {
	    		if(j%2 == 0) System.out.print(j/2 + " ");
	    		for(int k = 0;k<Map.length;k++) {
	    			if(Map[j][k] == 0) System.out.print("  ");
	    			else System.out.print(Map[j][k] + " ");
	    		}
	    		System.out.println();
	    	}
//    		---------------------------------------------- 列印

        	if(!findEnd) {
        		System.out.println(" - Error! - Adventurer");
        		break;
        	}
        }
    	
    	return findEnd;
    }
    
    /**
     * 終點設置
     * 	- flag
     * 		- true: 放置終點
     * 		- False: 移除終點
     * */
    public void setEnd(int Player, boolean flag) {
    	if(flag) {	//放置終點
    		int End = Player%2;
    		
    		if(Player < 3) {			//北南
        		for(int i = 0;i<Checkerboard.length;i=i+2) {
        			if(Checkerboard[setEnd_value[End]][i] > 20 && Checkerboard[setEnd_value[End]][i] < 25) {	//玩家棋
        				continue;
        			}else {
        				Map[setEnd_value[End]][i] = 2;
        			}
        		}
        	}else {						//東西
        		for(int i = 0;i<Checkerboard.length;i=i+2) {
        			if(Checkerboard[i][setEnd_value[End]] > 20 && Checkerboard[i][setEnd_value[End]] < 25) {	//玩家棋
        				continue;
        			}else {
        				Map[i][setEnd_value[End]] = 2;
        			}
        		}
        	}
    	}else {		//移除終點
    		for(int i = 0;i<Checkerboard.length;i++) {
    			for(int j = 0;j<Checkerboard.length;j++) {
    				if(Checkerboard[i][j] == 2) Map[i][j] = 0;
    			}
    		}
    	}
    }
    
    /**
     * 走訪路徑
     * */
    public void Adventure(int x, int y) {
//    	輸出棋盤資料
    	if(AnnotationSwitch) System.out.println(" [+] Adventure() -> Quoridor");

//    	尋找可移動位置
    	for(int j = 0;j<position.length;j++) { //四個方向
    		
//    		//訪問過位置
	    	Map[y][x] = 4;
	    	
    		if(findEnd) {
    			break;
    		}
    		
    		//(略過)出界、牆壁、玩家
			if((y + position[j][0]*2 > 16)	//下
			 ||(y + position[j][0]*2 < 0)	//上
			 ||(x + position[j][1]*2 > 16)	//右
			 ||(x + position[j][1]*2 < 0)) {//左
				continue;
			}else if(Checkerboard[y + position[j][0]][x + position[j][1]] == 11 		//為牆壁
				  ||(Checkerboard[y + position[j][0]*2][x + position[j][1]*2]/10 == 2	//為玩家
			 	  && Checkerboard[y + position[j][0]*2][x + position[j][1]*2]%10 < 5)) {
				continue;
			}else if(Map[y + position[j][0]*2][x + position[j][1]*2] == 4) {
				continue;
			}else if(Map[y + position[j][0]*2][x + position[j][1]*2] == 2) {
				findEnd = true;
				Map[y + position[j][0]*2][x + position[j][1]*2] = 4;
				break;
			}else{
				Adventure(x + position[j][1]*2, y + position[j][0]*2);
			}
		}
    }
    
    /**
	 * 初始化牆壁數量
	 * */
	public void initWall() {				
		if(NumberOfPlayers[0] == 2) {
			wall = new int[] {0,10,10};
		}else if(NumberOfPlayers[0] == 3) {
			wall = new int[] {0,6,6,6};
		}else {
			wall = new int[] {0,5,5,5,5};
		}
		
//		註解
		if(AnnotationSwitch) System.out.println(" [+] initWall() -> Checkerboard\n" + "\twall = " + Arrays.toString(wall));
	}
	
	/**
	 * 取得(單一)牆壁資訊
	 * */
	public int getWall(int Ply) {
		return wall[Ply];
	}
	
	/**
	 * 取得(單一)牆壁資訊
	 * */
	public void setWall(int Ply) {
		wall[Ply]--;
	}
	
	/**
	 * 取得(完整)牆壁資訊
	 * */
	public int[] getWall() {
		return wall;
	}
	
	/**
	 * 牆壁迴避
	 * */
	public boolean avoidWall(int[] XY, boolean HV) {
		
		int x = XY[0];
		int y = XY[1];
		
		if(Checkerboard[y][x] == 11) return false;
	
		if(HV) {	// 垂直牆壁
			if(y == 16) {
				if(Checkerboard[y-1][x] == 11 || Checkerboard[y-2][x] == 11) {
					return false;
				}
			}else {
				if(Checkerboard[y+1][x] == 11 || Checkerboard[y+2][x] == 11) {
					return false;
				}
			}
		}else {		//水平牆壁
			if(x == 16) {
				if(Checkerboard[y][x-1] == 11 || Checkerboard[y][x-2] == 11) {
					return false;
				}
			}else {
				if(Checkerboard[y][x+1] == 11 || Checkerboard[y][x+2] == 11) {
					return false;
				}
			}
		}
		
		return true;
	}
}
