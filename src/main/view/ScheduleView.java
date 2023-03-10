package main.view;
//200
import main.handle.ScheduleHandle;
import main.object.Data;
import main.object.Day;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

public class ScheduleView extends JFrame {
    /* 北部 */
    JPanel northPanel = new JPanel();
    JButton back = new JButton("返回");
    JLabel searchLabel = new JLabel("具体某天");
    JTextField searchTxt1 = new JTextField();
    JTextField searchTxt2 = new JTextField();
    JLabel monthLabel = new JLabel("月");
    JLabel dayLabel = new JLabel("日");
    JButton searchBt = new JButton("查找");
    JButton showAll = new JButton("显示全部");

    /* 南部 */
    Vector<String> column=new Vector<>();
    Vector<Vector<String>> data = new Vector<>();
    DefaultTableModel tableModel;
    JTable jTable;
    JScrollPane jScrollPane;

    /* 日期集合 */
    Day[][] dayList = new Day[13][32];
    int[] mon ;
    int beginM;
    int beginD;

    /* 事件处理 */
    ScheduleHandle scheduleHandle;

    public ScheduleView(){
        scheduleHandle = new ScheduleHandle(this);
        /* 日期和原有数据的渲染*/
        column.add("一");
        column.add("二");
        column.add("三");
        column.add("四");
        column.add("五");
        column.add("六");
        column.add("日");

        /*
            读取数据库，初始化dayList
         */
        Data data2 = new Data();
        try {
            data2.initialDay(dayList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /* 计算开始的日期 */
        int day2 = 28;
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int nowMonth = c.get(Calendar.MONTH)+1;
        int nowDate = c.get(Calendar.DATE);
        int dayX = c.get(Calendar.DAY_OF_WEEK)-1;
        if((year%4==0 && year%100!=0) || (year%400==0))
            day2 = 29;
        mon = new int[]{0,31, day2, 31, 30, 31,30,31,31,30,31,30,31};
        int beginDay = nowDate - dayX + 1;
        int beginMonth=nowMonth;
        if(beginDay <= 0){
            beginDay = mon[nowMonth-1] + beginDay;
            beginMonth = nowMonth-1;
        }
        beginM = beginMonth;
        beginD = beginDay;
        /* 第一次渲染 (初始化)*/
        scheduleFromNow(beginM,beginD,data);


   //     System.out.println(data);
        /*  北部布局 */
        searchTxt1.setPreferredSize(new Dimension(100,30));
        searchTxt2.setPreferredSize(new Dimension(100,30));
        northPanel.add(back);
        northPanel.add(searchLabel);
        northPanel.add(searchTxt1);
        northPanel.add(monthLabel);
        northPanel.add(searchTxt2);
        northPanel.add(dayLabel);
        northPanel.add(searchBt);
        northPanel.add(showAll);
        back.addActionListener(scheduleHandle);
        showAll.addActionListener(scheduleHandle);
        searchBt.addActionListener(scheduleHandle);

        /* 南部布局 */
        tableModel = new DefaultTableModel();
        tableModel.setDataVector(data, column);
        tableModel.addTableModelListener(scheduleHandle);
        jTable = new JTable(tableModel);
        jTable.setRowHeight(130);
        jScrollPane = new JScrollPane(jTable);

         /*
            设置单元格起始地址
         */
        MyTableCellRenderer renderer = new MyTableCellRenderer();
      //  MyRenderer myRenderer = new MyRenderer();
      //  MyCellEditor cellEditor = new MyCellEditor(new JTextArea());
        // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < column.size(); i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = jTable.getColumn(column.get(i));
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
      //      tableColumn.setCellRenderer(myRenderer);
     //       tableColumn.setCellEditor(cellEditor);
        }


        /* 总体布局 */
        Container contentPane = getContentPane();
        contentPane.add(northPanel, BorderLayout.NORTH);
        contentPane.add(jScrollPane, BorderLayout.CENTER);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

        /*
                设置日期默认显示在单元格的头部
             */
    public static class MyTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setVerticalAlignment(SwingConstants.NORTH);
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
    /*
        根据表格开头的月和日进行计算，直至当年的12.31，将该期间的day对象的content传到data中
     */
    public void scheduleFromNow(int beginMonth, int beginDay, Vector<Vector<String>>data) {
     /*  data = new Vector<>();
        这是在函数内新创建的对象，所以在函数结束后就自动销毁，所以没有用处，
        更确切的说，这个data不是成员的data了，因为他的地址改变了，所以在这进行的data的赋值，都不会影响成员data的值
      */
        Vector<String> row = new Vector<>();
        int countColumn=0;
        for(int i=beginMonth;i<=12;i++)
        {
            for(int j=(i==beginMonth)? beginDay:1;j<=mon[i];j++)
            {
                /*
                    注意，因为我们使用dayList渲染，所以分为两种情况
                 */
                if(dayList[i][j]==null) {   // 当day对象没有建立时
                    dayList[i][j] = new Day(i, j, "");
                    String a = (i < 10) ? '0' + String.valueOf(i) : String.valueOf(i);
                    String b = (j < 10) ? '0' + String.valueOf(j) : String.valueOf(j);
                    dayList[i][j].setContent( a + "." + b);
                }
                row.add(countColumn,dayList[i][j].getContent());
                countColumn ++;
                if(countColumn == 7){
                    data.add(row);
                    row = new Vector<>(); // 注意 ， 若不重新定义一个， 这个row是一直没变的，而且会持续更新data以前添加的row
                    countColumn = 0;
                }
                /*
                    当最后的时候，如果不是表格行的末尾，就手动添加
                 */
                else if(i == 12 && j == 31){
                    for(int k=countColumn;k<=6;k++)
                        row.add(k,"");
                    data.add(row);
                }
            }
        }
    }

    public JTextField getSearchTxt1() {
        return searchTxt1;
    }

    public JTextField getSearchTxt2() {
        return searchTxt2;
    }

    public int[] getMon() {
        return mon;
    }

    public int getBeginM() {
        return beginM;
    }

    public int getBeginD() {
        return beginD;
    }

    public Vector<Vector<String>> getData() {
        return data;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getjTable() {
        return jTable;
    }

    public Vector<String> getColumn() {
        return column;
    }

    public Day[][] getDayList() {
        return dayList;
    }

    public static void main(String[] args) {
        new ScheduleView();
    }

}
