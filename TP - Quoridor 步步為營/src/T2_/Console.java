package T2_;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


/**
 * 遊戲功能控制
 *  - 棋盤大小: 寬 735, 高 735
 *  	- 東邊區塊寬度為 130
 * */
public class Console extends JPanel {
	
	private int buttonWidth;
	private int buttonHeight;	
	private int buttonFontSize;
	
	private int panelWidth;
	private int panel_inAboveHeight;
	
	private int panel_inBelowHeight;
	
	private JPanel inAbove; //面板中的上半部
	private JPanel inBelow; //面板中的下半部
//	private JLabel labelAttacker; //目前進攻方
	private JLabel labelESWN;
	private JLabel labelWall;
	private JLabel labelEAST; //東方的牆壁數量
	private JLabel labelWEST; //西方的牆壁數量
	private JLabel labelNORTH; //西方的牆壁數量
	private JLabel labelSOUTH; //西方的牆壁數量
	private JButton buttonRegretChess; //悔棋
	private JButton buttonHint; //提示
	private JButton buttonSurrender; //投降
	private JButton buttonSubstitute; //代打
	private JButton buttonNext; //下一局
	private JButton creator; //開發者模式
	
	private JRadioButton NoP2; //Number of players 2
	private JRadioButton NoP3;
	private JRadioButton NoP4;
	private ButtonGroup buttonSelecter;
	
	private int NumberOfPlayers;

	/**
	 * 建構子
	 * */
	public Console() {
	}
	
	public Console(int panelWidth) {
		this.panelWidth = panelWidth;
		
		init();
	}
	
	/**
	 * 初始化
	 * */
	private void init() {
		buttonWidth = 120;
		buttonHeight = 60;
		buttonFontSize = 17;
		
		panel_inAboveHeight = 520;
		
		panel_inBelowHeight = 60;
		
		NumberOfPlayers = 2;
//		NumberOfPlayers = 3;
		
//		單選按鈕初始化
		setRadioButtonGroupMethods();
		
//		標籤初始化
		setLabel();		
		
//		按鈕初始化
		setButton();
		
//		面板初始化
		setPanel();
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
		
		inAbove.add(buttonRegretChess);
		inAbove.add(buttonHint);
		inAbove.add(buttonSurrender);
		inAbove.add(buttonSubstitute);
		inAbove.add(buttonNext);
		inAbove.add(creator);
		
//		下半部面板設定
		inBelow = new JPanel();
		inBelow.setBackground(Color.black);
		inBelow.setPreferredSize(new Dimension(panelWidth, panel_inBelowHeight));
//		inAbove.setLayout(new BoxLayout(inAbove, BoxLayout.Y_AXIS));
		
		inBelow.add(NoP2);
		inBelow.add(NoP3);
		inBelow.add(NoP4);
		
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
		buttonRegretChess = new JButton("悔棋"); //悔棋 Regret Chess 
		buttonHint = new JButton("提示"); //提示 Hint
		buttonSurrender = new JButton("投降"); //投降 Surrender
		buttonSubstitute = new JButton("代打"); //代打 Substitute
		buttonNext = new JButton("下一局"); //下一局 Next Round
		creator = new JButton(); //開發者模式
		
		buttonRegretChess.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		buttonHint.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		buttonSurrender.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		buttonSubstitute.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		buttonNext.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		creator.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		
		buttonRegretChess.setFont(new Font("Label.font", Font.PLAIN, buttonFontSize));
		buttonHint.setFont(new Font("Label.font", Font.PLAIN, buttonFontSize));
		buttonSurrender.setFont(new Font("Label.font", Font.PLAIN, buttonFontSize));
		buttonSubstitute.setFont(new Font("Label.font", Font.PLAIN, buttonFontSize));
		buttonNext.setFont(new Font("Label.font", Font.PLAIN, buttonFontSize));
		creator.setFont(new Font("Label.font", Font.PLAIN, buttonFontSize));
		
		buttonRegretChess.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO Auto-generated method stub
				
			}
		});
		
		buttonHint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO Auto-generated method stub
				
			}
		});
		
		buttonSurrender.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO Auto-generated method stub
				
			}
		});
		
		buttonSubstitute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO Auto-generated method stub
				
			}
		});
		
		buttonNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO Auto-generated method stub
				
			}
		});
		
		creator.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO Auto-generated method stub
				
			}
		});
	}
	/**
	 * 取的玩家人數
	 * */
	public int getNumberOfPlayers() {
		
		return NumberOfPlayers;
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
}
