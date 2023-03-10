package main.handle;
//50
import main.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainHandle implements ActionListener {
    MainView mainView;
    public MainHandle(MainView mainView){
        this.mainView=mainView;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jb = (JButton) e.getSource();
        switch(jb.getText()) {
            case "发布任务": {
                try {
                    new TaskView();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case "发布日程": {
                new ScheduleView();
                break;
            }
            case "发布反思":
                try {
                    new ReView();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                break;
            case "发布笔记":
                try {
                    new NoteView();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                break;
        }
        mainView.setVisible(false);
    }
}
