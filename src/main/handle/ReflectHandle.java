package main.handle;
//70
import main.object.Data;
import main.view.MainView;
import main.view.ReView;
import main.view.SetView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class ReflectHandle implements ActionListener {
    ReView reView;
    Data data = new Data();
    public ReflectHandle(ReView reView) {
        this.reView = reView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jb = (JButton) e.getSource();
        switch(jb.getText()){
            case "删除":
                try {
                    delete();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                break;
            case "具体": specific();break;
            case "反思": reflect();break;
            case "返回":
                        new MainView();
                        reView.setVisible(false);
                        break;
            default:;
        }
    }

    private void reflect() {
        int selectedRow = reView.getJTable().getSelectedRow();
        String before = reView.getReflectItems().get(selectedRow).getReflect();
        SetView setView = new SetView(before, selectedRow, reView);
    }

    private void specific() {
        int selectedRow = reView.getJTable().getSelectedRow();
        SetView setView = new SetView(reView.getReflectItems().get(selectedRow).getContent());
    }

    public void delete() throws SQLException {
        int selectedRow = reView.getJTable().getSelectedRow();
        System.out.println(selectedRow);
        if(selectedRow!=-1){
            data.deleteReflect(reView.getReflectItems().get(selectedRow));
            reView.getReflectItems().remove(selectedRow);
            reView.getData().remove(selectedRow);
            /* 重新渲染id值 */
            for(int i=0;i<reView.getData().size();i++){
                /* 此处的row其实是个引用，具体指向data的某个row*/
                Vector<Object> row = reView.getData().get(i);
                row.set(0,i+1); // 更新id的值
                reView.getReflectItems().get(i).setId(i+1);
            }
            reView.getTableModel().setDataVector(reView.getData(), reView.getColumn());
        }
    }
}
