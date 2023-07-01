import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static java.lang.Double.parseDouble;

public class Calculator {
    private JFrame frame;
    private JTextField textField;

    private double firstNumber;
    private String operator;
    private boolean isOperatorClicked;

    public Calculator() {
        frame = new JFrame("XXX的垃圾计算器（充满了bug）");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //关闭窗口时释放
        frame.setSize(400, 500);                    //默认窗口大小
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(238, 238, 238)); //默认背景色

        textField = new JTextField();       //设计了一个文本框,可输入和可复制输出
        textField.setFont(new Font("黑体", Font.PLAIN, 52));
        frame.add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));    //按钮参数
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //按钮池参数

        String[] buttons = {
                "Esc", "C", "00", "+",
                "7", "8", "9", "-",
                "4", "5", "6", "*",
                "1", "2", "3", "/",
                "0", ".", "=", "%"
        };

        for (String button : buttons) {
            JButton btn = new JButton(button);
            btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setFont(new Font("黑体", Font.PLAIN, 38));
            btn.addActionListener(new ButtonListener());
            buttonPanel.add(btn);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String buttonText = ((JButton) e.getSource()).getText();

            //如果输入的是数字
            if (buttonText.matches("[0-9]")) {
                textField.setText(textField.getText() + buttonText);
                isOperatorClicked = false;
            }
            //如果输入的是小数点
            else if (buttonText.equals(".")) {
                if (!textField.getText().contains(".")) {
                    textField.setText(textField.getText() + buttonText);
                    isOperatorClicked = false;
                }
            }
            //如果按的是C键(也就是Clear)
            else if (buttonText.equals("C")) {
                textField.setText("");
                isOperatorClicked = false;
            }
            //如果按的是等于号
            else if (buttonText.equals("=")) {
                if (!isOperatorClicked) {
                    double secondNumber = parseDouble(textField.getText());
                    double result = calculate(firstNumber, secondNumber, operator);
                    textField.setText(String.valueOf(result));
                    isOperatorClicked = true;
                }
            }
            //如果按的是运算符
            else {
                if (!isOperatorClicked) {
                    operator = buttonText;
                    firstNumber = Double.parseDouble(textField.getText());
                    textField.setText("");
                    isOperatorClicked = true;
                }
            }
        }
    }

    private double calculate(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return num1 / num2;
            case "%":
                return num1 % num2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculator();
            }
        });
    }
}
