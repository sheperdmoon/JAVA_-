package main.view;
//250
import main.handle.TaskHandle;
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

public class TaskView extends JFrame {

    /* 南部之南 jTable的具体内容 */
    ArrayList<Task> taskItems = new ArrayList<>(); // task的list
    String content="";  //具体落实的初始化内容
    Vector<String> column = new Vector<>(); // jTable的头部
    Vector<Vector<Object>> data = new Vector<>(); // jTable的内容
    DefaultTableModel tableModel; //
    JTable jTable;

    /* 4个布局 */
    JPanel northPanel = new JPanel(); //  任务frame北部的任务设置panel
    JPanel southPanel = new JPanel(new BorderLayout());  // 任务frame南部的显示当前任务panel
    JPanel southNorthPanel = new JPanel(); // 南部的北部控制栏panel
    JScrollPane southSouthPanel; // 南部南部的滚动布局

    /* 南部之北 */
    JLabel southLabel = new JLabel("任务列表");
    String[] listData = {"id", "名称","起始时间","截止时间"}; //下拉栏的参数
    int index; // 选择参数的索引
    JComboBox<String> comboBox = new JComboBox<>(listData); // 下拉栏
    JLabel searchLabel = new JLabel("查找"); // 后期可以设置关键词 比如 名称 时间 重要 紧急
    JLabel keyWord = new JLabel("关键词");
    JButton searchBt = new JButton("查找");
    JTextField searchTxt = new JTextField();
    JButton showAll = new JButton("显示全部");
    JButton delete = new JButton("删除");
    JButton show = new JButton("具体");

    /* 北部 */
    JButton back = new JButton("返回");
    JLabel northLabel = new JLabel("发布任务");
    JLabel taskName = new JLabel("任务名称");
    JTextField taskTxt = new JTextField();
    JLabel beginTime = new JLabel("开始");
    JTextField beginTxt = new JTextField();
    JLabel endTime = new JLabel("截止");
    JTextField endTxt = new JTextField();
    JCheckBox importance =new JCheckBox("重要");
    JCheckBox alert = new JCheckBox("紧急");
    JButton set = new JButton("具体落实");  // 根据begin跟end，创建一个日期table，以弹窗的形式
    JButton assign = new JButton("发布"); // 发布，就是在southPanel方添加一个新的
    JButton reset = new JButton("重置");

    TaskHandle taskHandle; // 事件处理

    /* 渲染页面 */
    public TaskView() throws SQLException {
        super("任务发布和查看界面");
        taskHandle = new TaskHandle(this); // 初始化对象


        /* 设置4个输入框的大小 */
        searchTxt.setPreferredSize(new Dimension(100,30));
        taskTxt.setPreferredSize(new Dimension(200,50));
        beginTxt.setPreferredSize(new Dimension(100,50));
        endTxt.setPreferredSize(new Dimension(100,50));

        northPanelLayout();  // 北部添加组件
        southNorthPanelLayout(); // 南之北添加组件
        southSouthPanel = getJScrollPane(); // 设置 南之南布局

        /* 南部添加南北两个panel*/
        southPanel.add(southNorthPanel, BorderLayout.NORTH);
        southPanel.add(southSouthPanel, BorderLayout.CENTER);

        addListener(); // 为按钮跟下拉栏添加事件

        /* 为总的frame添加南北布局 和设置格式*/
        setView();
    }

    private void setView() {
        Container contentPane = getContentPane();
        contentPane.add(northPanel, BorderLayout.NORTH);
        contentPane.add(southPanel, BorderLayout.CENTER);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private JScrollPane getJScrollPane() throws SQLException {
        /* 设置table头部 */
        column.add("id");
        column.add("名称");
        column.add("起始时间");
        column.add("截止时间");
        column.add("重要");
        column.add("紧急");

        /*
            用taskList初始化表格
         */
        Data  data1 = new Data();
        taskItems = data1.getAllTasks();
    //    data1.deleteAll();
        for(Task task : taskItems)
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
        JScrollPane southSouthPanel = new JScrollPane(jTable);

        /* 给 表格 设置 行排序器 */
        RowSorter<TableModel> rowSorter = new TableRowSorter<>(tableModel);
        jTable.setRowSorter(rowSorter);
        return southSouthPanel;
    }

    private void addListener() {
        set.addActionListener(taskHandle);
        back.addActionListener(taskHandle);
        assign.addActionListener(taskHandle);
        reset.addActionListener(taskHandle);
        delete.addActionListener(taskHandle);
        show.addActionListener(taskHandle);
        searchBt.addActionListener(taskHandle);
        showAll.addActionListener(taskHandle);
        comboBox.addItemListener(taskHandle);
        tableModel.addTableModelListener(taskHandle);

    }

    private void southNorthPanelLayout() {
        southNorthPanel.add(southLabel);
        southNorthPanel.add(searchLabel);
        southNorthPanel.add(keyWord);
        southNorthPanel.add(comboBox);
        southNorthPanel.add(searchTxt);
        southNorthPanel.add(searchBt);
        southNorthPanel.add(showAll);
        southNorthPanel.add(delete);
        southNorthPanel.add(show);
    }

    private void northPanelLayout() {
        northPanel.add(back);
        northPanel.add(northLabel);
        northPanel.add(taskName);
        northPanel.add(taskTxt);
        northPanel.add(beginTime);
        northPanel.add(beginTxt);
        northPanel.add(endTime);
        northPanel.add(endTxt);
        northPanel.add(importance);
        northPanel.add(alert);
        northPanel.add(set);
        northPanel.add(assign);
        northPanel.add(reset);
    }

    public ArrayList<Task> getTaskItems() {
        return taskItems;
    }

    public String getContent() {
        return content;
    }

    public Vector<String> getColumn() {
        return column;
    }

    public Vector<Vector<Object>> getData() {
        return data;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getJTable() {
        return jTable;
    }

    public int getIndex() {
        return index;
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }


    public JTextField getTaskTxt() {
        return taskTxt;
    }

    public JTextField getBeginTxt() {
        return beginTxt;
    }

    public JTextField getEndTxt() {
        return endTxt;
    }

    public JCheckBox getImportance() {
        return importance;
    }

    public JCheckBox getAlert() {
        return alert;
    }

    public JTextField getSearchTxt() {
        return searchTxt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static void main(String[] args) {
        try {
            new TaskView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
