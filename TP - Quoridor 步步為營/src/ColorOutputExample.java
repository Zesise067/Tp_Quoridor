

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorOutputExample extends JFrame {

    private JButton colorButton;
    private Color chosenColor = Color.BLACK;

    public ColorOutputExample() {
        setTitle("Color Output Example");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        colorButton = new JButton("Choose Color");
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseColor();
            }
        });

        JPanel panel = new JPanel();
        panel.add(colorButton);
        getContentPane().add(panel);

//        // 初始时设置按钮颜色为黑色
//        colorButton.setForeground(chosenColor);
    }

    private void chooseColor() {
        // 使用 JColorChooser 显示颜色选择器对话框
        Color selectedColor = JColorChooser.showDialog(this, "Choose Color", chosenColor);

        // 如果用户选择了颜色，更新按钮颜色
        if (selectedColor != null) {
            chosenColor = selectedColor;
//            colorButton.setForeground(chosenColor);

            // 输出 RGB 值
            int red = chosenColor.getRed();
            int green = chosenColor.getGreen();
            int blue = chosenColor.getBlue();

            System.out.println("RGB values: " + red + ", " + green + ", " + blue);
        }
    }
    
    private Color getColor() {
    	return chosenColor;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ColorOutputExample example = new ColorOutputExample();
            example.setVisible(true);
        });
    }
}
