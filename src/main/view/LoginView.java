package main.view;
//100
import main.handle.LoginHandle;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    /* 头部 */
    JLabel nameLabel = new JLabel("任务规划软件", JLabel.CENTER);
    SpringLayout springLayout = new SpringLayout();
    JPanel jPanel = new JPanel(springLayout);

    // 用户名和密码
    JLabel userLabel = new JLabel("用户名");
    JTextField userTxt = new JTextField();

    JLabel codeLabel = new JLabel("密码");
    JPasswordField codeTxt = new JPasswordField();

    /* 登录和重置 bt */
    JButton bt1 = new JButton("登录");
    JButton bt2 = new JButton("重置");

    LoginHandle loginHandle;
    public LoginView(){
        super("登录页面");
        loginHandle = new LoginHandle(this);
        Container container = getContentPane();
        setPanel();
        lay();
        setView(container);
    }

    private void setPanel() {
        Font font1 = new Font("华文行楷", Font.PLAIN, 40);
        nameLabel.setFont(font1);
        userTxt.setPreferredSize(new Dimension(200,30));
        codeTxt.setPreferredSize(new Dimension(200,30));
        jPanel.add(userLabel);
        jPanel.add(userTxt);
        jPanel.add(codeLabel);
        jPanel.add(codeTxt);
        jPanel.add(bt1);
        bt1.addActionListener(loginHandle);
        jPanel.add(bt2);
        bt2.addActionListener(loginHandle);
    }

    private void setView(Container container) {
        getRootPane().setDefaultButton(bt1);
        container.add(nameLabel, BorderLayout.NORTH);
        container.add(jPanel, BorderLayout.CENTER);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void lay() {
        Spring width = Spring.sum(Spring.sum(Spring.width(userLabel),Spring.width(userTxt)),Spring.constant(20));
        int offsetX = width.getValue()/2;
        springLayout.putConstraint(SpringLayout.WEST, userLabel, -offsetX,SpringLayout.HORIZONTAL_CENTER,jPanel);
        springLayout.putConstraint(SpringLayout.NORTH, userLabel, 20, SpringLayout.NORTH, jPanel);
// 用户名txt
        springLayout.putConstraint(SpringLayout.WEST, userTxt, 20, SpringLayout.EAST, userLabel);
        springLayout.putConstraint(SpringLayout.NORTH, userTxt, 0, SpringLayout.NORTH, userLabel);
// 密码label
        springLayout.putConstraint(SpringLayout.EAST, codeLabel, 0, SpringLayout.EAST, userLabel);
        springLayout.putConstraint(SpringLayout.NORTH, codeLabel, 20, SpringLayout.SOUTH, userLabel);
// 密码txt
        springLayout.putConstraint(SpringLayout.WEST, codeTxt, 20, SpringLayout.EAST, codeLabel);
        springLayout.putConstraint(SpringLayout.NORTH, codeTxt, 0, SpringLayout.NORTH, codeLabel);
// 登录按钮
        springLayout.putConstraint(SpringLayout.WEST, bt1, 50, SpringLayout.WEST, codeLabel);
        springLayout.putConstraint(SpringLayout.NORTH, bt1, 20, SpringLayout.SOUTH, codeLabel);
// 重置按钮
        springLayout.putConstraint(SpringLayout.WEST, bt2, 50, SpringLayout.EAST, bt1);
        springLayout.putConstraint(SpringLayout.NORTH, bt2, 0, SpringLayout.NORTH, bt1);
    }

    public JTextField getUserTxt() {
        return userTxt;
    }

    public JPasswordField getCodeTxt() {
        return codeTxt;
    }

    public static void main(String[] args) {
        new LoginView();
    }

}
