package main.view;
//140
import main.handle.ReflectHandle;
import main.object.Data;
import main.object.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class ReView extends JFrame {
    /*
        北部的按钮布局
     */
    JPanel northPanel = new JPanel();
    JButton back = new JButton("返回");
    JButton deleteBt = new JButton("删除");
    JButton specificBt = new JButton("具体");
    JButton reflectBt = new JButton("反思");

    /*
        南部的jScrollPanel
     */
    ArrayList<Task> reflectItems = new ArrayList<>(); // task的list
    Vector<String> column = new Vector<>(); // jTable的头部
    Vector<Vector<Object>> data = new Vector<>(); // jTable的内容
    DefaultTableModel tableModel;
    JTable jTable;
    JScrollPane southPanel;

    ReflectHandle reflectHandle; // 事件处理

    public ReView() throws SQLException {
        reflectHandle = new ReflectHandle(this);
        /*
            北部
         */

        back.addActionListener(reflectHandle);
        deleteBt.addActionListener(reflectHandle);
        specificBt.addActionListener(reflectHandle);
        reflectBt.addActionListener(reflectHandle);
        northPanel.add(back);
        northPanel.add(deleteBt);
        northPanel.add(specificBt);
        northPanel.add(reflectBt);
        /*
            南部;
         */
        setSouth();
        /*
            整体
         */
        Container contentPane = getContentPane();
        contentPane.add(northPanel, BorderLayout.NORTH);
        contentPane.add(southPanel, BorderLayout.CENTER);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

    }

    private void setSouth() throws SQLException {
        /* 设置table头部 */
        column.add("id");
        column.add("名称");
        column.add("起始时间");
        column.add("截止时间");
        column.add("重要");
        column.add("紧急");

        /*
            用从数据库读取的reflectList初始化表格
         */
        Data data1 = new Data();
        reflectItems = data1.getAllReflects();
        for(Task task : reflectItems)
        {
            Vector<Object> row = new Vector<>();
            row.add(task.getId());
            row.add(task.getName());
            row.add(task.getBegin());
            row.add(task.getEnd());
            row.add(task.getIm());
            row.add(task.getAl());
            row.add(task.getContent());
            data.add(row);
        }
        tableModel = new DefaultTableModel();
        tableModel.setDataVector(data, column); // 初步渲染table
        jTable = new JTable(tableModel);
        southPanel = new JScrollPane(jTable);
    }

    public static void main(String[] args) throws SQLException {
        new ReView();
    }

    public ArrayList<Task> getReflectItems() {
        return reflectItems;
    }

    public void setReflectItems(ArrayList<Task> reflectItems) {
        this.reflectItems = reflectItems;
    }

    public Vector<Vector<Object>> getData() {
        return data;
    }

    public void setData(Vector<Vector<Object>> data) {
        this.data = data;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JTable getJTable() {
        return jTable;
    }

    public Vector<String> getColumn() {
        return column;
    }
}

