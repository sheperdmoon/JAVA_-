package main.view;
//105
import main.handle.NoteHandle;
import main.object.Data;
import main.object.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class NoteView extends JFrame {

    JPanel northPanel = new JPanel();
    JButton back = new JButton("返回");
    JLabel noteLabel = new JLabel("笔记名");
    JTextField noteName = new JTextField();
    JButton release = new JButton("发布笔记");
    JButton find = new JButton("查找笔记");
    JButton delete = new JButton("删除笔记");
    JButton change = new JButton("修改标题");
    JButton save = new JButton("保存");


    JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
    ArrayList<Note> noteList = new ArrayList<>();


    NoteHandle noteHandle;

    public NoteView() throws SQLException {
        /*
            注意，初始化得在之前，不然noteHandle里面的noteArraylist就是null
         */
        Data data = new Data();
        noteList=data.getAllNotes();
        noteHandle = new NoteHandle(this);
        /*
            从数据库中读取数据进行初始化
         */
        for(Note note : noteList)
            jTabbedPane.addTab(note.getTitle(), noteHandle.createTab(note.getContent()));

        back.addActionListener(noteHandle);
        release.addActionListener(noteHandle);
        change.addActionListener(noteHandle);
        delete.addActionListener(noteHandle);
        save.addActionListener(noteHandle);
        find.addActionListener(noteHandle);

        northPanel.add(back);
        northPanel.add(noteLabel);
        noteName.setPreferredSize(new Dimension(150,35));
        northPanel.add(noteName);
        northPanel.add(release);
        northPanel.add(find);
        northPanel.add(delete);
        northPanel.add(change);
        northPanel.add(save);

        Container contentPane = getContentPane();
        contentPane.add(northPanel, BorderLayout.NORTH);
        contentPane.add(jTabbedPane, BorderLayout.CENTER);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
    public static void main(String[] args) throws SQLException {
        new NoteView();
    }
    public JTabbedPane getjTabbedPane() {
        return jTabbedPane;
    }

    public void setjTabbedPane(JTabbedPane jTabbedPane) {
        this.jTabbedPane = jTabbedPane;
    }

    public JTextField getNoteName() {
        return noteName;
    }

    public void setNoteName(JTextField noteName) {
        this.noteName = noteName;
    }
    public ArrayList<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(ArrayList<Note> noteList) {
        this.noteList = noteList;
    }

}
