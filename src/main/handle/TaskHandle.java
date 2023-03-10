package main.handle;
//160
import main.object.Data;
import main.view.MainView;
import main.view.SetView;
import main.object.Task;
import main.view.TaskView;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

public class TaskHandle implements ActionListener, ItemListener, TableModelListener {
    TaskView taskView;
    Data data1 = new Data();
    public TaskHandle(TaskView taskView){
        this.taskView = taskView;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bt = (JButton) e.getSource();
        if(bt.getText().equals("发布")){
            release();
        }
        else if(bt.getText().equals("重置")){
            taskView.getTaskTxt().setText("");
            taskView.getBeginTxt().setText("");
            taskView.getEndTxt().setText("");
            taskView.getImportance().setSelected(false);
            taskView.getAlert().setSelected(false);
        }
        /*
            to be better
            当处于查找模式时，删除操作无效，需要用id值来操作。
         */
        else if(bt.getText().equals("删除")){
            delete();
        }
        else if(bt.getText().equals("返回")){
            new MainView();
            taskView.setVisible(false);
        }
        else if(bt.getText().equals("具体落实")){
            SetView sv = new SetView(this.taskView);
        }
        else if(bt.getText().equals("具体")){
            int selectedRow = taskView.getJTable().getSelectedRow();
       //     System.out.println(taskView.getTaskItems().get(selectedRow).getContent());
            SetView sv = new SetView(taskView.getTaskItems().get(selectedRow).getContent(),selectedRow,taskView);
            
        }
        else if(bt.getText().equals("查找")){
            String goal = taskView.getSearchTxt().getText();
            int index= taskView.getIndex();
            Vector<Vector<Object>> newData=new Vector<>();
            /*
                key!!!
                因为此处newData添加的row，其实是data中的row的引用，所以在查找界面对其进行的修改，
                实际上是反馈到了data中的，所以最后显示全部依旧可行。
             */
            for(Vector<Object> row:taskView.getData()){
                if( String.valueOf(row.get(index)).equals(goal)){
                    newData.add(row);
                }
            }
            taskView.getTableModel().setDataVector(newData, taskView.getColumn());
        }
        else if(bt.getText().equals("显示全部")){
            taskView.getTableModel().setDataVector(taskView.getData(), taskView.getColumn());
        }
    }

    private void delete() {
        int selectedRow = taskView.getJTable().getSelectedRow();
        if(selectedRow!=-1){
            data1.addReflect(taskView.getTaskItems().get(selectedRow));
            taskView.getTaskItems().remove(selectedRow);
            taskView.getData().remove(selectedRow);
            /* 重新渲染id值 */
            for(int i=0;i<taskView.getData().size();i++){
                /* 此处的row其实是个引用，具体指向data的某个row*/
                Vector<Object> row = taskView.getData().get(i);
                row.set(0,i+1); // 更新id的值
                taskView.getTaskItems().get(i).setId(i+1);
            }
            data1.updateAll(taskView.getTaskItems());
            taskView.getTableModel().setDataVector(taskView.getData(), taskView.getColumn());
        }
    }

    private void release() {
        /* 获取具体数据 */
        String name = taskView.getTaskTxt().getText();
        String begin = taskView.getBeginTxt().getText();
        String end = taskView.getEndTxt().getText();
        boolean im = taskView.getImportance().isSelected();
        boolean al = taskView.getAlert().isSelected();
        String im1 = taskView.getImportance().getText();
        String al1 = taskView.getAlert().getText();
        String content = taskView.getContent();
        /* 创建新的task对象 , 并加入list中*/
        Task newTask = new Task(taskView.getTaskItems().size()+1,name,begin,end,im? im1:"",al? al1:"",content);
        taskView.setContent("");  // 每次都初始化
        taskView.getTaskItems().add(newTask);
        data1.updateAll(taskView.getTaskItems());
        /*创建新的row， 并加入data，最后对tablemodel进行新一次的渲染 */
        Vector<Object> row = new Vector<>();
        row.add(newTask.getId());
        row.add(name);
        row.add(begin);
        row.add(end);
        row.add(im? im1:"");
        row.add(al? al1:"");
        row.add(content);
        taskView.getData().add(row);
        taskView.getTableModel().setDataVector(taskView.getData(), taskView.getColumn());
    }

    /*
        返回选好的排序的key的索引
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        /* 将index设置为选中的下标值 */
        if(e.getStateChange() == ItemEvent.SELECTED) {
            taskView.setIndex(taskView.getComboBox().getSelectedIndex());
        }
    }

    /*
        单元格内容修改后，对数据和数据库进行更新。
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        if(row < 0 || column < 0)
            return;
        String s = (String) taskView.getTableModel().getValueAt(row, column); // 获取该表格内的值
        int idd = (int) taskView.getTableModel().getValueAt(row, 0);
        for(Task task:taskView.getTaskItems()){
            if(task.getId() == idd){
                switch(column){
                    case 1:  task.setName(s);break;
                    case 2:  task.setBegin(s);break;
                    case 3:  task.setEnd(s);break;
                    case 4:  task.setIm(s);break;
                    case 5:  task.setAl(s);break;
                    default:;
                }
            }
        }
        data1.updateAll(taskView.getTaskItems());
    }
}
