package T5;

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
//		輸出棋盤資料
		if(AnnotationSwitch) System.out.println(" [+] init() -> Quoridor");
		
//		玩家
		NumberOfPlayers = new int[5]; //玩家人數 。[0] 玩家人數 。[1-4] 玩家座標
		coordinateYX = new int[2]; //座標 YX	 。[0] Y 。[1] X
		
		initPlayerCoordinate(); //初始化玩家座標
		
//		棋盤
		infCheckerboard = new int[17][17]; //棋盤資訊
		tempInfCheckerboard = new int[17][17]; //暫存棋盤資訊
		
		for(int i = 0;i<infCheckerboard.length;i++) Arrays.fill(infCheckerboard[i], 0);
		for(int i = 0;i<tempInfCheckerboard.length;i++) Arrays.fill(tempInfCheckerboard[i], 0);
		
		initInfCheckerboard(); //初始化棋盤
		
//		---------------------------------------------------------------------------------------
		
//		加載玩家位置
		for(int i = 1;i<=NumberOfPlayers[0];i++) {
			exchangeCoordinate(NumberOfPlayers[i]);
			
			infCheckerboard[coordinateYX[0]*2][coordinateYX[1]*2] = 20 + i;
			
			saveInfCheckerboard();
			printCheckerboard(); //輸出陣列資訊
		}
		
//		加載遊戲規則
		GameRules();
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
		
//		註解
		if(AnnotationSwitch) {
			System.out.println(" [+] initCheckerboard() -> Quoridor");
		}
		
		saveInfCheckerboard();//第一次暫存
		printCheckerboard(); //輸出陣列資訊
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
	public void GameRules() {
//		輸出棋盤資料
		if(AnnotationSwitch) System.out.println(" [+] GameRules() -> Quoridor");
			
		explore(); //探索可放置位置
	}
	
	/**
	 * 探索可放置位置
	 * */
	public void explore() {
//		輸出棋盤資料
		if(AnnotationSwitch) System.out.println(" [+] explore() -> Quoridor");
		
		int x, y;
		int[][] position = {{0,1},{0,-1},{1,0},{-1,0}}; //四個方向 -> 東西南北 {y, x}
//							  右		左	   下	  上
		
//		TEST ----------------------------------------------
		infCheckerboard[0][7] = 11;
		infCheckerboard[2][8] = 22;
//		---------------------------------------------- TEST
		
		for(int i = 1;i<=NumberOfPlayers[0];i++) { //人數
			exchangeCoordinate(NumberOfPlayers[i]);
			y = coordinateYX[0]; //座標 Y
			x = coordinateYX[1]; //座標 X
			
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
					if(infCheckerboard[y*2 + position[j][0]][x*2 + position[j][1]] == 11) {
//						               |-|   				 |-|					轉棋盤位置座標
//									  |--------------------||--------------------|	牆壁位置
						continue;
//怪怪的 --------------------------------------------------------------------------------------------------------------
//					}else
//						
////					為玩家 -----------------------------------------------------------------------------
//					if(infCheckerboard[y*2 + position[j][0]*2][x*2 + position[j][1]*2]/10 == 2) {
//						if((y*2 + position[j][0]*3 > 16)	//下		避免超出索引值
//						 ||(y*2 + position[j][0]*3 < 0)		//上
//						 ||(x*2 + position[j][1]*3 > 16)	//右
//						 ||(x*2 + position[j][1]*3 < 0)) {	//左
//							continue;
//						}else if(infCheckerboard[y*2 + position[j][0]*4][x*2 + position[j][1]*4]/10 == 2) {	//玩家後面還有玩家
//							continue;
//						}
//						
////						無障礙
//						else {
//							infCheckerboard[y*2 + position[j][0]*2][x*2 + position[j][1]*2] = 25;
//						}
//怪怪的 --------------------------------------------------------------------------------------------------------------
					}
					
//					無障礙
					else {
						infCheckerboard[y*2 + position[j][0]*2][x*2 + position[j][1]*2] = 25;
					}
				}
			}
		}		
		saveInfCheckerboard();//第一次暫存
		printCheckerboard(); //輸出陣列資訊
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
								+ "\tcoordinateYX[0] : [1] = " + coordinateYX[0] + " : " + coordinateYX[1]);
		}
	}
	
//	------------------------------------------------------------------------------
	
	/**
	 * 設定玩家人數
	 * */
	public void setNumberOfPlayers(int NumberOfPlayers) {
		this.NumberOfPlayers[0] =  NumberOfPlayers;
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
	}
	
	/**
	 * 回傳棋盤值
	 * */
	public int[][] getInfCheckerboard() {
		return infCheckerboard.clone();
	}
}
