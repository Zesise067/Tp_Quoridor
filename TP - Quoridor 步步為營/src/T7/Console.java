package T7;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.Arrays;

//import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


/**
 * 遊戲功能控制
 *  - 棋盤大小: 寬 735, 高 735
 *  	- 東邊區塊寬度為 130
 * */
public class Console extends JPanel {
	
	private Checkerboard checkerboard;
	private Quoridor quoridor;
	
	private String[] objText; //字串用物件
	
	private int jbWidth;
	private int jbHeight;	
	private int jbFontSize;
	
	private int panelWidth;
	private int panel_inAboveHeight;
	
	private int panel_inBelowHeight;
	
	private JPanel inAbove; //面板中的上半部
	private JPanel inBelow; //面板中的下半部
	
//	private JLabel labelAttacker; //目前進攻方
//	private JLabel labelESWN;
//	private JLabel labelWall;
//	private JLabel labelEAST; //東方的牆壁數量
//	private JLabel labelWEST; //西方的牆壁數量
//	private JLabel labelNORTH; //西方的牆壁數量
//	private JLabel labelSOUTH; //西方的牆壁數量
	
//	按鈕監聽器
	private ActionListener[] jbGroup;
	private ActionListener jbRegretChess;	//悔棋
	private ActionListener jbHint;			//提示
	private ActionListener jbSurrender;		//投降
	private ActionListener jbSubstitute;	//代打
	private ActionListener jbNext;			//下一局
	private ActionListener jbCreator;		//開發者模式
	
	
	private JRadioButton NoP2; //Number of players 2
	private JRadioButton NoP3;
	private JRadioButton NoP4;
	private ButtonGroup buttonSelecter;
	
	private int NumberOfPlayers;

	/**
	 * 建構子
	 * */
	public Console() {}
	
	public Console(int panelWidth, Checkerboard checkerboard, Quoridor quoridor) {

		this.panelWidth = panelWidth;
		
		this.checkerboard = checkerboard;
		this.quoridor = quoridor;
		
		init();
	}
	
	/**
	 * 初始化
	 * */
	private void init() {
		jbWidth = 120;
		jbHeight = 60;
		jbFontSize = 17;
		
		panel_inAboveHeight = 520;
		
		panel_inBelowHeight = 60;
		
		NumberOfPlayers = 2;
		
//		clickJB = "";
		
		objText = new String[] {
				"悔棋",		//0
				"提示",		//1
				"投降",		//2
				"代打",		//3
				"下一局",		//4
				""			//5
		};
		
//		面板初始化
		setPanel();
		
//		單選按鈕初始化
		setRadioButtonGroupMethods();
		
//		標籤初始化
		setLabel();		
		
//		按鈕初始化
		setButton();
	}
	
	/**
	 * 面板設定
	 * */
	private void setPanel() {
		
//		上半部面板設定
		inAbove = new JPanel();
		inAbove.setBackground(Color.black);
		inAbove.setPreferredSize(new Dimension(panelWidth, panel_inAboveHeight));
//		inAbove.setLayout(new BoxLayout(inAbove, BoxLayout.Y_AXIS));
		
//		inAbove.add(labelAttacker);
//		inAbove.add(labelEAST);
//		inAbove.add(labelWEST);
//		inAbove.add(labelNORTH);
//		inAbove.add(labelSOUTH);
		
//		for(int i = 0;i<jbGroup.length;i++) {
//			
//		}
		
//		下半部面板設定
		inBelow = new JPanel();
		inBelow.setBackground(Color.black);
		inBelow.setPreferredSize(new Dimension(panelWidth, panel_inBelowHeight));
//		inAbove.setLayout(new BoxLayout(inAbove, BoxLayout.Y_AXIS));
		
		
		
		//底板設定
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		setPreferredSize(new Dimension(130, 80));
//		setLayout(new FlowLayout());
		setBackground(Color.black);
		
        add(inAbove);
        add(inBelow);
        
//		inAbove.add(Box.createVerticalStrut(60)); // 添加垂直间距
//		
//		inAbove.add(NoP2);
//		inAbove.add(NoP3);
//		inAbove.add(NoP4);
//		
//		inAbove.add(new JLabel("123                       "));
//        
//		inAbove.add(Box.createVerticalStrut(60)); // 添加垂直间距
//		
//		inAbove.add(new JLabel("123"));
	}
	
	/**
	 * 單選按鈕之成員設定
	 * */
	private void setRadioButtonGroupMethods() {
		radioSelecterListener radioListener = new radioSelecterListener();
		NoP2 = new JRadioButton("2", true);
		NoP3 = new JRadioButton("3", false);
		NoP4 = new JRadioButton("4", false);
		buttonSelecter = new ButtonGroup();
		
		NoP2.addActionListener(radioListener);
		NoP3.addActionListener(radioListener);
		NoP4.addActionListener(radioListener);
		
		NoP2.setForeground(Color.white);		
		NoP3.setForeground(Color.white);		
		NoP4.setForeground(Color.white);
		
		NoP2.setBackground(Color.black);
		NoP3.setBackground(Color.black);
		NoP4.setBackground(Color.black);
		
		buttonSelecter.add(NoP2);
		buttonSelecter.add(NoP3);
		buttonSelecter.add(NoP4);
		
		inBelow.add(NoP2);
		inBelow.add(NoP3);
		inBelow.add(NoP4);
	}
	
	/**
	 * 標籤設定
	 * */
	private void setLabel() {
//		labelAttacker = new JLabel("     " + "東");
//		labelAttacker = new JLabel("   " + "北");
		
//		labelESWN = new JLabel();
//		labelWall = new JLabel("  ");
//		labelEAST = new JLabel("          - EAST:  " + 9);
//		labelSOUTH = new JLabel();
//		labelWEST = new JLabel("          - WEST:  " + 10);
//		labelNORTH = new JLabel();
//		labelSOUTH = new JLabel("          - SOUTH: " + 10);
//		labelNORTH = new JLabel("          - NORTH: " + 10);
		
//		labelAttacker.setForeground(Color.white);
//		labelEAST.setForeground(Color.white);
//		labelSOUTH.setForeground(Color.white);
//		labelWEST.setForeground(Color.white);
//		labelNORTH.setForeground(Color.white);

//		labelAttacker.setFont(new Font("Label.font", Font.PLAIN, 25));
//		labelEAST.setFont(new Font("Label.font", Font.PLAIN, 16));
//		labelSOUTH.setFont(new Font("Label.font", Font.PLAIN, 15));
//		labelWEST.setFont(new Font("Label.font", Font.PLAIN, 15));
//		labelNORTH.setFont(new Font("Label.font", Font.PLAIN, 15));
		
		
//		labelAttacker.setPreferredSize(new Dimension(panelWidth-100, 50));
//		labelEAST.setPreferredSize(new Dimension(panelWidth-100, 20));
//		labelSOUTH.setPreferredSize(new Dimension(panelWidth, 20));
//		labelWEST.setPreferredSize(new Dimension(panelWidth, 20));
//		labelNORTH.setPreferredSize(new Dimension(panelWidth, 20));
	}
	
	/**
	 * 按鈕設定
	 * */
	private void setButton() {
		setJButtonActionLinstener();
		
		jbGroup = new ActionListener[] {
				jbRegretChess,	//悔棋 Regret Chess 
				jbHint,			//提示 Hint
				jbSurrender,	//投降 Surrender
				jbSubstitute,	//代打 Substitute
				jbNext,			//下一局 Next Round
				jbCreator		//開發者模式
		};
		
		for(int i = 0;i<jbGroup.length;i++) {
			JButton jButton = new JButton(objText[i]);
			jButton.setPreferredSize(new Dimension(jbWidth, jbHeight));
			jButton.setFont(new Font("Label.font", Font.PLAIN, jbFontSize));
			jButton.setActionCommand(String.valueOf(i));
			jButton.addActionListener(jbGroup[i]);
			inAbove.add(jButton);
		}
	}
	
	/**
	 * 設定按鈕監聽器
	 * */
	public void setJButtonActionLinstener() {
//		悔棋 Regret Chess
		jbRegretChess = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("jbRegretChess");
			}
		};
		
//		提示 Hint
		jbHint = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("jbHint");
			}
		};
		
//		投降 Surrender
		jbSurrender = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("jbSurrender");
			}
		};
		
//		代打 Substitute
		jbSubstitute = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("jbSubstitute");
			}
		};
		
//		下一局 Next Round
		jbNext = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("jbNext");
				
				quoridor.init();
				checkerboard.setInitGame();
				checkerboard.updataCheckerboard(quoridor.getInfCheckerboard(), true);
				checkerboard.setNumberOfPlayers(quoridor.getNumberOfPlayers());
				
				switchJRadioButton(true);
			}
		};
		
//		開發者模式
		jbCreator = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("jbCreator");
			}
		};		
	}
	
	/**
	 * 單選按鈕監聽器
	 * */
	class radioSelecterListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == NoP2) {
				NumberOfPlayers = 2;
			}else if(e.getSource() == NoP3) {
				NumberOfPlayers = 3;
			}else if(e.getSource() == NoP4) {
				NumberOfPlayers = 4;
			}
		}
	}
	
	/**
	 * 單選按鈕開關
	 * */
	public void switchJRadioButton(boolean Power) {
		if(Power) {
			NoP2.setEnabled(true);
			NoP3.setEnabled(true);
			NoP4.setEnabled(true);
		}else {
			NoP2.setEnabled(false);
			NoP3.setEnabled(false);
			NoP4.setEnabled(false);
		}
	}
	
	/**
	 * 取的玩家人數
	 * */
	public int getNumberOfPlayers() {
		return NumberOfPlayers;
	}
}
