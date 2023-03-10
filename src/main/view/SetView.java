package main.view;
//110
import main.object.Data;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Set;

public class SetView extends JFrame {
    JTextArea jta = new JTextArea();
    JButton ok = new JButton("ok");
    /*
        反思界面的显示具体落实，此时是不可编辑状态
     */
    public SetView(String specific){
        Container contentPane = getContentPane();
        contentPane.add(jta,BorderLayout.CENTER);
        jta.setText(specific);
        jta.setEditable(false);
        setSize(500, 500); // 得先设定大小 才set位置，否则会出错
        setLocationRelativeTo(null);
        setVisible(true);//可见
    }
    /*
        任务发布界面的第一次具体落实
     */
    public SetView(TaskView taskView) {
        Container contentPane = getContentPane();
        contentPane.add(jta, BorderLayout.CENTER);
        contentPane.add(ok, BorderLayout.SOUTH);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskView.content = getContent();
                setVisible(false);
            }
        });
        setSize(500, 500); // 得先设定大小 才set位置，否则会出错
        setLocationRelativeTo(null);
        setVisible(true);//可见
    }
    /*
        任务发布后的更改落实
     */
    public SetView(String txt, int row, TaskView taskView) {
        jta.setText(txt);
        Container contentPane = getContentPane();
        contentPane.add(jta, BorderLayout.CENTER);
        contentPane.add(ok, BorderLayout.SOUTH);
        /*
            匿名内部类处理
         */
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
         //       System.out.println("test");
       //         System.out.println(getContent());
                taskView.taskItems.get(row).setContent(getContent());
                Data data1= new Data();
                data1.updateAll(taskView.getTaskItems());
                setVisible(false);
            }
        });
        setSize(500, 500); // 得先设定大小 才set位置，否则会出错
        setLocationRelativeTo(null);
        setVisible(true);//可见

    }
    /*
        编辑反思
     */
    public SetView(String txt, int row, ReView reView) {
        jta.setText(txt);
        Container contentPane = getContentPane();
        contentPane.add(jta, BorderLayout.CENTER);
        contentPane.add(ok, BorderLayout.SOUTH);
        /*
            匿名内部类处理
         */
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //       System.out.println("test");
                //         System.out.println(getContent());
                reView.getReflectItems().get(row).setReflect(getContent());
                Data data1 = new Data();
                try {
                    data1.updateReflect(reView.getReflectItems().get(row));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
            }
        });
        setSize(500, 500); // 得先设定大小 才set位置，否则会出错
        setLocationRelativeTo(null);
        setVisible(true);//可见

    }
    public String getContent() {
        return this.jta.getText();
    }
}
