package T1_Base;

import javax.swing.SwingUtilities;

public class LinkStart {

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