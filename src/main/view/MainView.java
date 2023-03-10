package main.view;
//45
import main.handle.MainHandle;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    MainHandle mainHandle;
    JLabel jLabel = new JLabel("欢迎使用本软件", JLabel.CENTER);
    JPanel jPanel = new JPanel();
    JButton task = new JButton("发布任务");
    JButton routine = new JButton("发布日程");
    JButton reflect = new JButton("发布反思");
    JButton note = new JButton("发布笔记");
    public MainView(){
        mainHandle = new MainHandle(this);
        Container contentPane = getContentPane();
        Font font1 = new Font("normal",Font.PLAIN, 30);
        jLabel.setFont(font1);
        jPanel.add(task);
        jPanel.add(routine);
        jPanel.add(reflect);
        jPanel.add(note);
        task.setPreferredSize(new Dimension(200,50));
        routine.setPreferredSize(new Dimension(200,50));
        reflect.setPreferredSize(new Dimension(200,50));
        note.setPreferredSize(new Dimension(200,50));
        task.addActionListener(mainHandle);
        routine.addActionListener(mainHandle);
        reflect.addActionListener(mainHandle);
        note.addActionListener(mainHandle);
        contentPane.add(jLabel,BorderLayout.NORTH);
        contentPane.add(jPanel,BorderLayout.CENTER);
        setSize(300, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
    public static void main(String[] args) {
        new MainView();
    }
}
