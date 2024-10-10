package T7;

import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.SwingUtilities;

public class Quoridor {
//	基本變數
	private int CELL_SIZE;
	
	private int width;
	private int height;
	
	private int deviation; //壕溝寬度
	
	private boolean AnnotationSwitch; //註解開關
	
//	玩家
	private int[] NumberOfPlayers; //玩家人數 。[0] 玩家人數 。[1-4] 玩家座標
	
	private int[] coordinateYX; //座標 YX	 。[0] Y 。[1] X
	
//	棋盤
	private int[][] Checkerboard; //棋盤資訊
	private int[][] tempCheckerboard; //暫存棋盤資訊

//	四個方向 -> 東西南北 {y, x}
	int[][] position = {{0,1},{0,-1},{1,0},{-1,0}};
//	  					  右		左	   下	  上
	
//	探索路徑(紀錄路徑)
	private int[][] Map = new int[17][17];
	
//	使用於終點方置
	private int[] setEnd_value = new int[2];
	
//	使否找到終點
	private boolean findEnd = false;
	
	/**
	 * 建構子
	 * */
	public Quoridor() {}
	
	public Quoridor(int CELL_SIZE, int width, int height, int deviation, boolean AnnotationSwitch) {
		this.CELL_SIZE = CELL_SIZE;
		
		this.width = width;
		this.height = height;
		
		this.deviation = deviation; //壕溝寬度
		
		this.AnnotationSwitch = AnnotationSwitch;
		
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
		tempCheckerboard = new int[17][17]; //暫存棋盤資訊
		
		initInfCheckerboard(); //初始化棋盤
		
//		for(int i = 0;i<infCheckerboard.length;i++) Arrays.fill(infCheckerboard[i], 0);
//		for(int i = 0;i<tempInfCheckerboard.length;i++) Arrays.fill(tempInfCheckerboard[i], 0);
		
//		玩家
		NumberOfPlayers = new int[5]; //玩家人數 。[0] 玩家人數 。[1-4] 玩家座標
		coordinateYX = new int[2]; //座標 YX	 。[0] Y 。[1] X
		
		NumberOfPlayers[0] = 2;
//		NumberOfPlayers[1] = 4;  //北 - yx = 04
//		NumberOfPlayers[2] = 84; //南 - yx = 84
//		NumberOfPlayers[3] = 40; //西 - yx = 40 ------------------------- 測試用
//		NumberOfPlayers[4] = 48; //東 - yx = 48 ------------------------- 測試用
		
		initPlayerCoordinate(); //初始化玩家座標
		
//		終點放置
		setEnd_value[0] = 0;
		setEnd_value[1] = 16;
		
		Adventurer(8, 0, 1);
		
//		加載遊戲規則
		GameRules(1);
		
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
		for(int i = 0;i<Checkerboard.length;i++) {
			Arrays.fill(Checkerboard[i], 0);
		}
		
//		空
//		for(int i = 1;i<infCheckerboard.length;i=i+2) {
//			for(int j = 1;j<infCheckerboard.length;j=j+2) {
//				infCheckerboard[i][j] = 0;
//			}
//		}
//		
//		牆
//		for(int i = 0;i<infCheckerboard.length;i++) {
//			for(int j = 1;j<infCheckerboard.length;j=j+2) {
//				if(i % 2 == 0) {
//					infCheckerboard[i][j] = 0;
//				}else {
//					infCheckerboard[i][j-1] = 0;
//				}
//			}
//			
//			if(i % 2 == 1) { //補最右邊
//				infCheckerboard[i][16] = 0;
//			}
//		}
		
//		註解
		if(AnnotationSwitch) {
			System.out.println(" [+] initCheckerboard() -> Quoridor");
		}
		
//		saveInfCheckerboard();//第一次暫存
//		printCheckerboard(); //輸出陣列資訊
		
//		TEST -------------------------------------------------------------------------------------------------------
		Checkerboard[1][0] = 11; 
		Checkerboard[1][1] = 11; 
		Checkerboard[1][2] = 11; // ---
		
		Checkerboard[6][3] = 11; 
		Checkerboard[4][3] = 11; 
		Checkerboard[5][3] = 11; // |
		
		Checkerboard[1][14] = 11; 
		Checkerboard[1][15] = 11; 
		Checkerboard[1][16] = 11; // ---
		
		Checkerboard[14][1] = 11; 
		Checkerboard[15][1] = 11; 
		Checkerboard[16][1] = 11; // |
		
//		Checkerboard[14][15] = 11; 
//		Checkerboard[15][15] = 11; 
//		Checkerboard[16][15] = 11; // |
		
		Checkerboard[15][14] = 11; 
		Checkerboard[15][15] = 11; 
		Checkerboard[15][16] = 11; // ---
		
		Checkerboard[1][11] = 11; 
		Checkerboard[2][11] = 11; // |
		Checkerboard[3][11] = 11; 
		
		Checkerboard[1][13] = 11; 
		Checkerboard[2][13] = 11; // |
		Checkerboard[3][13] = 11; 
		
		Checkerboard[7][4] = 11; 
		Checkerboard[7][5] = 11; // -
		Checkerboard[7][6] = 11; 
		
		
		Checkerboard[2][3] = 11; // |
		
		Checkerboard[7][10] = 11; 
		Checkerboard[7][11] = 11; 
		Checkerboard[7][12] = 11; // -
		
		Checkerboard[7][14] = 11; 
		Checkerboard[7][15] = 11; // -
		Checkerboard[7][16] = 11; 
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
		
		NumberOfPlayers[1] = 4;  //北 - yx = 04
//		NumberOfPlayers[1] = 34;  //北 - yx = 04
		NumberOfPlayers[2] = 84; //南 - yx = 84
		NumberOfPlayers[3] = 40; //西 - yx = 40 ------------------------- 測試用
		NumberOfPlayers[4] = 48; //東 - yx = 48 ------------------------- 測試用
		
//		加載玩家位置
		for(int i = 1;i<=NumberOfPlayers[0];i++) {
			exchangeCoordinate(NumberOfPlayers[i]);
			
			Checkerboard[coordinateYX[0]*2][coordinateYX[1]*2] = 20 + i;
			
		}
		
//		saveInfCheckerboard();
//		printCheckerboard(); //輸出陣列資訊
		
	}

	/**
	 * 儲存棋盤資料
	 * */
	public void saveCheckerboard() {
		for(int i = 0;i<tempCheckerboard.length;i++) {
			for(int j = 0;j<tempCheckerboard.length;j++) {
				tempCheckerboard[i][j] = Checkerboard[i][j];
			}
		}
		
//		註解
		if(AnnotationSwitch) {
			System.out.println(" [+] saveInfCheckerboard() -> Quoridor");
		}
	}
	
	/**
	 * 輸出棋盤資訊
	 * */
	public void printCheckerboard() {
//		輸出棋盤資料
		if(AnnotationSwitch) {
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
			System.out.println("\n");
		
//			輸出 temp_InfCheckerboard
			Row = 0; 
			System.out.println("\ttemp_InfCheckerboard\n"
									+ "\t0\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\n"
									+ "\t----------------------------------------------------------------------------------------------------------------------------------");
			for(int i = 0;i<tempCheckerboard.length;i++) {
				for(int j = 0;j<tempCheckerboard.length;j++) {
					System.out.print("\t" + tempCheckerboard[i][j]);
				}
				if(i%2 == 0) {
					System.out.println("\t-> " + Row++);
				}else {
					System.out.println();
				}
			}
			System.out.println();
		}
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
			
		int x, y;
		

		
		exchangeCoordinate(NumberOfPlayers[Player]);
		y = coordinateYX[0]; //座標 Y
		x = coordinateYX[1]; //座標 X
		
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

		printCheckerboard(); //輸出陣列資訊
			
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
	
	/**
	 * 設定玩家人數
	 * */
	public void setNumberOfPlayers(int NumberOfPlayers) {
		this.NumberOfPlayers[0] =  NumberOfPlayers;
		
		initInfCheckerboard();
		initPlayerCoordinate();
		saveCheckerboard();
		GameRules(1);
	}
	
	/**
	 * 回傳玩家人數
	 * */
	public int getNumberOfPlayers() {
//		return NumberOfPlayers.clone();
		return NumberOfPlayers[0];
	}
	
	/**
	 * 回傳棋盤值
	 * */
	public int[][] getInfCheckerboard() {
//		return infCheckerboard.clone();
		return Checkerboard;
	}
	

	
	/**
	 * 探險家(走訪路徑)
	 * */
    private boolean Adventurer(int x, int y, int Player) {
    	boolean flag = false;	//終點設定使用
    	
//    	探險路徑(初始化)
    	for(int i = 0;i<Map.length;i++) Arrays.fill(Map[i], 0);
    	
//    	是否找到終點(初始化)
    	findEnd = false;
    	
    	setEnd(Player, true);	//設定終點
    	
    	Adventure(x, y);		//走訪終點
    	
    	if(!findEnd) System.out.println(" - Error! -");
    	
    	setEnd(Player, false);	//去除終點
    	
    	printCheckerboard();
    	
//		-------------------------------------------------
    	System.out.print("  ");
		for(int i = 0;i<Map.length;i++) {
			if(i%2 == 0) System.out.print(i/2 + "   ");
    	}
    	System.out.println();
    	
    	for(int i = 0;i<Map.length;i++) {
    		if(i%2 == 0) System.out.print(i/2 + " ");
    		for(int k = 0;k<Map.length;k++) {
    			if(Map[i][k] == 0) System.out.print("  ");
    			else System.out.print(Map[i][k] + " ");
    		}
    		System.out.println();
    	}
//		--------------------------------------------------
    	
    	return flag;
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
    
//	四個方向 -> 東西南北 {y, x}
//	int[][] position = {{0,1},{0,-1},{1,0},{-1,0}};
//	  					  右		左	   下	  上
    
    /**
     * 走訪路徑
     * */
    public void Adventure(int x, int y) {
//    	輸出棋盤資料
    	if(AnnotationSwitch) System.out.println(" [+] Adventure() -> Quoridor");

    	System.out.println(" - " + x + ":" + y + " -");
    	    	
//    	尋找可移動位置
    	for(int j = 0;j<position.length;j++) { //四個方向
    		System.out.println(" - " + x + ":" + y + " - " + j + "方向");
    		//找到終點了
    		
    		//訪問過位置
	    	Map[y][x] = 4;
	    	
    		if(findEnd) {
    			System.out.println("1 - " + x + " " + y);
    			break;
    		}
    		
    		//(略過)出界、牆壁、玩家
			if((y + position[j][0]*2 > 16)	//下
			 ||(y + position[j][0]*2 < 0)	//上
			 ||(x + position[j][1]*2 > 16)	//右
			 ||(x + position[j][1]*2 < 0)) {	//左
				System.out.println("2-1");
				continue;
			}else if(Checkerboard[y + position[j][0]][x + position[j][1]] == 11 			//為牆壁
			 || Checkerboard[y + position[j][0]*2][x + position[j][1]*2]/10 == 2) {	//為玩家
				System.out.println("2-2");
				continue;
			}else if(Map[y + position[j][0]*2][x + position[j][1]*2] == 4) {

    			System.out.println("2-3");
				continue;
			}else if(Map[y + position[j][0]*2][x + position[j][1]*2] == 2) {

    			System.out.println("3 - " + x + " " + y);
    			
				findEnd = true;
//				Map[y + position[j][0]*2][x + position[j][1]*2] = 2;
				


				break;
			}else{

    			System.out.println("4");
		    	
				Adventure(x + position[j][1]*2, y + position[j][0]*2);
			}
		}
    }
}
