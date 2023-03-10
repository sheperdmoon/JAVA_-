package main.handle;
//180
import main.object.Data;
import main.object.Note;
import main.view.MainView;
import main.view.NoteView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class NoteHandle implements ActionListener {
    NoteView noteView;
    JTabbedPane jTabbedPane;
    ArrayList<Note> noteArrayList;
    Data data = new Data();
    int preIndex;
    int preTabCount;
    public NoteHandle(NoteView noteView) {
        this.noteView = noteView;
        jTabbedPane = noteView.getjTabbedPane();
        noteArrayList = noteView.getNoteList();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jb = (JButton) e.getSource();
        String text = jb.getText();
        if ("发布笔记".equals(text)) {
            String name = noteView.getNoteName().getText();
            release(name,"");
        } else if ("删除笔记".equals(text)) {
            try {
                delete();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if ("修改标题".equals(text)) {
            try {
                change();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if ("返回".equals(text)) {
            back();
        } else if ("保存".equals(text)){
            try {
                save();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if("查找笔记".equals(text)){
            String name = noteView.getNoteName().getText();
            search(name);
        }
    }

    private void search(String name) {
        int flag = 0;
        for(Note note:noteArrayList) {
            if(note.getTitle().equals(name)) {
                int index = noteArrayList.indexOf(note);
                jTabbedPane.setSelectedIndex(index);
                flag = 1;
            }
        }
        if(flag == 0) {
            JOptionPane.showMessageDialog(
                    noteView,
                    "笔记不存在",
                    "warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public void save() throws SQLException {
        int index =  jTabbedPane.getSelectedIndex();
        JPanel nowPanel = (JPanel) jTabbedPane.getComponentAt(index);
        JTextArea nowComponent = (JTextArea) nowPanel.getComponent(0);
        String nowContent = nowComponent.getText();
   //     System.out.println(nowContent);
        data.updateNote_content(noteArrayList.get(index).getTitle(),nowContent);
        noteArrayList.get(index).setContent(nowContent);
    }

    private void back() {
        new MainView();
        noteView.setVisible(false);
    }

    private void change() throws SQLException {
        int index =  jTabbedPane.getSelectedIndex();
        String newTitle = noteView.getNoteName().getText();
        for(Note note:noteArrayList){
            if(note.getTitle().equals(newTitle)){
                JOptionPane.showMessageDialog(
                        noteView,
                        "笔记已存在，请重新发布",
                        "warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;

            }
        }
        jTabbedPane.setTitleAt(index, newTitle);
        data.updateNote(noteArrayList.get(index).getTitle(),newTitle);
        noteArrayList.get(index).setTitle(newTitle);
    }

    private void delete() throws SQLException {
        int index =  noteView.getjTabbedPane().getSelectedIndex();
        jTabbedPane.removeTabAt(index);
        data.deleteNote(noteArrayList.get(index).getTitle());
        noteArrayList.remove(index);
        preTabCount--;
    }

    public void release(String name, String content) {
        for(Note note:noteArrayList){
            System.out.println(note.getTitle());
            if(note.getTitle().equals(name)){
                JOptionPane.showMessageDialog(
                        noteView,
                        "笔记已存在，请重新发布",
                        "warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }
        noteView.getjTabbedPane().addTab(name, createTab(content));
        noteView.getjTabbedPane().setSelectedIndex(noteView.getjTabbedPane().getTabCount()-1);
        Note newNote = new Note(name, content);
        noteArrayList.add(newNote);
        data.addNote(newNote);
        preTabCount++;
    }

    /*
        创建新的选项卡界面
     */
    public JTextArea createTab(String content) {
      //  JPanel jPanel = new JPanel(new BorderLayout());
        JTextArea jTextArea = new JTextArea();
        jTextArea.setText(content);
        jTextArea.setPreferredSize(new Dimension(1000,400));
        /*
            为当前输入 增加 ctrl+s 的快捷键。
         */
        jTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown()) {
                    if(e.getKeyCode() == KeyEvent.VK_S){
                        try {
                            System.out.println("保存成功");
                            save();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
      //  jPanel.add(jTextArea,BorderLayout.CENTER); // 需要用center，才能随标题长度动态改变textarea起始位置。
        return jTextArea;
    }

}
