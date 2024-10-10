package T3;

import javax.swing.SwingUtilities;

public class Quoridor {
	
	private int[][] infCheckerboard;

	/**
	 * 建構子
	 * */
	public Quoridor() {}
	
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
	 * 轉換
	 * */
	public void exchange() {
		
	}
//	infCheckerboard[coordinateYX[i][0] *2][coordinateYX[i][1] *2] = 20 + i;
	
	/**
	 * 回傳棋盤值
	 * */
	public int[][] getInfCheckerboard() {
		return infCheckerboard;
	}
}
