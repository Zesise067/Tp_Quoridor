package T4;

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
	
//	牆壁
	private int[] wall;
//	棋盤
	private int[][] infCheckerboard; //棋盤資訊
	private int[][] tempInfCheckerboard; //暫存棋盤資訊

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
//		玩家
		NumberOfPlayers = new int[5]; //玩家人數 。[0] 玩家人數 。[1-4] 玩家座標
		coordinateYX = new int[2]; //座標 YX	 。[0] Y 。[1] X
		
		initPlayerCoordinate(); //初始化玩家座標
		
//		牆壁
		wall = new int[4];
		
		initWall();
		
//		棋盤
		infCheckerboard = new int[17][17]; //棋盤資訊
		tempInfCheckerboard = new int[17][17]; //暫存棋盤資訊
		
		for(int i = 0;i<infCheckerboard.length;i++) Arrays.fill(infCheckerboard[i], 0);
		for(int i = 0;i<tempInfCheckerboard.length;i++) Arrays.fill(tempInfCheckerboard[i], 0);
		
		initInfCheckerboard(); //初始化棋盤
	}
	
	/**
	 * 初始化玩家座標
	 * */
	public void initPlayerCoordinate() {
		NumberOfPlayers[0] = 2;
		NumberOfPlayers[1] = 4;  //北 - yx = 04
		NumberOfPlayers[2] = 84; //南 - yx = 84
		NumberOfPlayers[3] = 40; //西 - yx = 40 ------------------------- 測試用
		NumberOfPlayers[4] = 48; //東 - yx = 48 ------------------------- 測試用
		
//		輸出棋盤資料
		if(AnnotationSwitch) {
			System.out.println(" [+] initPlayerCoordinate() -> Quoridor\n"
								+ "	- NumberOfPlayers[All] = " + Arrays.toString(NumberOfPlayers) + "\n");
		}
	}

	/**
	 * 初始化牆壁數量
	 * */
	public void initWall() {				
		if(NumberOfPlayers[0] == 2) {
			wall = new int[] {10,10};
		}else if(NumberOfPlayers[0] == 3) {
			wall = new int[] {6,6,6};
		}else {
			wall = new int[] {5,5,5,5};
		}
		
//		註解
		if(AnnotationSwitch) {
			System.out.println(" [+] initWall() -> Quoridor\n"
								+ "\twall = " + Arrays.toString(wall));
		}
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
		
		saveInfCheckerboard();//第一次暫存
		printCheckerboard(); //輸出陣列資訊

//		註解
		if(AnnotationSwitch) {
			System.out.println(" [+] initCheckerboard() -> Quoridor");
		}
	}

	/**
	 * 儲存棋盤資料
	 * */
	public void saveInfCheckerboard() {
		for(int i = 0;i<tempInfCheckerboard.length;i++) {
			for(int j = 0;j<tempInfCheckerboard.length;j++) {
				tempInfCheckerboard[i][j] = infCheckerboard[i][j];
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
		
//			輸出 temp_InfCheckerboard
			Row = 0; 
			System.out.println("\ttemp_InfCheckerboard\n"
									+ "\t0\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\n"
									+ "\t----------------------------------------------------------------------------------------------------------------------------------");
			for(int i = 0;i<tempInfCheckerboard.length;i++) {
				for(int j = 0;j<tempInfCheckerboard.length;j++) {
					System.out.print("\t" + tempInfCheckerboard[i][j]);
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
	public void GameRules(int[][] infCheckerboard, int[][] coordinateYX) {
		this.infCheckerboard = infCheckerboard;
		
		int[][] position = {{0,1},{0,-1},{1,0},{-1,0}}; //四個方向 -> 東西南北 {y, x}
		
//		尋找可行走到路
//		for(int i = 0;i<position.length;i++) { //四個方向
//			if((coordinateYX[coordinateYX[0][0]][0] + position[i][0]) > 9 || (coordinateYX[coordinateYX[0][0]][0] + position[i][0] < 0)		//避免超出索引值
//			|| (coordinateYX[coordinateYX[0][0]][1] + position[i][1]) > 9 || (coordinateYX[coordinateYX[0][0]][1] + position[i][1] < 0)) {
//				continue;
//			}else {
//				if(this.infCheckerboard[(coordinateYX[i][0] *2) + position[i][0]][(coordinateYX[i][1] *2) + position[i][1]] == 11) { //為牆壁
////					               |---------------------|					 |---------------------|					轉棋盤位置座標
////								   |--------------------------------------|  |--------------------------------------|	牆壁位置
//					
//				}else if(this.infCheckerboard[(coordinateYX[i][0] *2) + (position[i][0]*2)][(coordinateYX[i][1] *2) + (position[i][1]*2)] > 20) { //為玩家
//					
//				}else { //無障礙
//					this.infCheckerboard[(coordinateYX[i][0] *2) + (position[i][0]*2)][(coordinateYX[i][1] *2) + (position[i][1]*2)] = 25;
//				}
//			}
////			for(int j = 0;j<position[0].length;j++) { //{y, x}
////				
////			}
//		}
	}
	
	/**
	 * 功能開關
	 * */
//	public void switchFuction(int value) {
//		switch(value) {
////		case 0:
////			break;
//		case 1:
//			System.out.println("1");
//			break;
//		case 2:
//			System.out.println("2");
//			break;
//		case 3:
//			System.out.println("3");
//			break;
//		case 4:
//			System.out.println("4");
//			break;
//		case 5:
//			System.out.println("5");
//			initCheckerboard();
//			initGame = true;
//			repaint();
//			break;
//		default:
//			System.out.println("???");
//			break;
//		}
//	}

	/**
	 * 數值轉換座標
	 * */
	public void exchangeCoordinate(int value) {
		coordinateYX[0] = value / 10; //座標 Y
		coordinateYX[1] = value % 10; //座標 X
	}
	
	/**
	 * 取得玩家人數
	 * */
	public int[] getNumberOfPlayers() {
		return NumberOfPlayers;
	}
	
	/**
	 * 回傳棋盤值
	 * */
	public int[][] getInfCheckerboard() {
		return infCheckerboard;
	}
}
