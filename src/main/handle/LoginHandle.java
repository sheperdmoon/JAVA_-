package main.handle;
// 60
import main.object.Data;
import main.view.LoginView;
import main.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class    LoginHandle extends KeyAdapter implements ActionListener{
    LoginView loginView;
    public LoginHandle(LoginView loginView){
        this.loginView = loginView;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bt = (JButton) e.getSource();
        if(bt.getText().equals("登录")) {
            login();
        }
        else if(bt.getText().equals("重置")){
            loginView.getUserTxt().setText("");
            loginView.getCodeTxt().setText("");
        }
    }
    /*
        检测输入的用户密码是否与数据库中存储的一致
     */
    private void login(){
        String user = loginView.getUserTxt().getText();
        String code = new String(loginView.getCodeTxt().getPassword());
        System.out.println(user+":"+code);
        boolean flag= false;
        Data data = new Data();
        try {
            flag = data.login(user,code);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(flag){
            new MainView();
            loginView.setVisible(false);
        }
        else{
            JOptionPane.showMessageDialog(loginView, "用户名密码错误");
        }
    }
    /* 设置默认按钮的回车后的事件 */
    @Override
    public void keyPressed(KeyEvent e) {
        if(KeyEvent.VK_ENTER == e.getKeyCode())
            login();
    }
}
