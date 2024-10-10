package T6;

import javax.swing.SwingUtilities;

public class LinkStart {
	
	/**
	 * 建構子
	 * */
	public LinkStart() {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SwingUtilities.invokeLater(() -> {
			Dominator dominator = new Dominator();
			dominator.run();
		});
		
	}
}

/**
 * OK - LinkStart
 * 			OK - 啟動介面
 * 
 * OK - Dominator
 * 			OK - 初始化變數
 * 
 * OK - Checkerboard1
 * 			OK - 棋盤設計
 * */


/**
 * 陣列與繪製工具 xy 相反
 * 
 * 
 * */

/*
 * 按鈕控制未完全
 */
  