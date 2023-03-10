package main.handle;
//100
import main.object.Data;
import main.view.MainView;
import main.view.ScheduleView;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

public class ScheduleHandle implements ActionListener,TableModelListener {
    ScheduleView scheduleView;

    public ScheduleHandle(ScheduleView scheduleView) {
        this.scheduleView = scheduleView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jb = (JButton) e.getSource();
        int month = 1;
        int day = 1;
        /*
            要求：点击“查找”，获取从目标月日开始以后的所有数据
            法1：
                根据获取的月日数据，计算出begin的月跟日，然后利用初始化渲染的方法，重新读取数据进行渲染。
                如此做，对日程的修改是需要通过单元格修改事件，进而反映到dayList上。
            法2：
                直接在data中查找，遍历每一row的一周7天，如果匹配就将其和之后的row加进来，如此做的话，在查找mode中
                进行的修改时直接反映到data中的，但是dayList也必须经过单元格修改事件进行变化。
         */
        if ("查找".equals(jb.getText())) {  // 必须用SV的data数组来获取需要渲染的值 然后用新的newData来存储。
            month = Integer.parseInt(scheduleView.getSearchTxt1().getText());
            day = Integer.parseInt(scheduleView.getSearchTxt2().getText());
            Calendar goalDay = Calendar.getInstance();
            goalDay.set(Calendar.YEAR, 2021);
            goalDay.set(Calendar.MONTH, month-1); // 注意Calender对象的month是需要-1的
            goalDay.set(Calendar.DATE, day);
            int dayX = goalDay.get(Calendar.DAY_OF_WEEK)-1;
            int[] mon = {0,31, 28, 31, 30, 31,30,31,31,30,31,30,31};
            int beginDay = day - dayX + 1;
            int beginMonth=month;
            if(beginDay <= 0){
                beginDay = mon[month-1] + beginDay;
                beginMonth = month-1;
            }
            /*
            每次点击查收，都重新设立一个newData，进行数据传输和渲染
             */
            Vector<Vector<String>> newData = new Vector<>();
            scheduleView.scheduleFromNow(beginMonth,beginDay,newData);
            scheduleView.getTableModel().setDataVector(newData, scheduleView.getColumn());
        }
        else if("显示全部".equals(jb.getText())){
            /*
                之所以要重新渲染，是因为在查找mode中，对jTable的修改，并不直接反映到data中，而是反映到newData中。
             */
            scheduleView.getData().clear(); // 因为sv的data是成员，所以需要clear，下面再进行重新传输跟渲染
            scheduleView.scheduleFromNow(scheduleView.getBeginM(),scheduleView.getBeginD(),scheduleView.getData());
            scheduleView.getTableModel().setDataVector(scheduleView.getData(), scheduleView.getColumn());
        }
        else if("返回".equals(jb.getText())){
            new MainView();
            scheduleView.setVisible(false);
        }
    }
    /* 当单元格被修改时
        注意要按回车确定
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        String s = (String) scheduleView.getTableModel().getValueAt(row, column); // 获取该表格内的值
        // 得到该表格对应的month 跟 day， 然后通过更新dayList来更新渲染.
        int month = Integer.parseInt(s.substring(0,2));
        int day = Integer.parseInt(s.substring(3,5));
        scheduleView.getDayList()[month][day].setContent(s); // 将day对象的content更新!!!!
        // 在返回的时候重新根据dayList渲染
        Data data1 = new Data();
        try {
            data1.updateDay(month,day,s);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}



