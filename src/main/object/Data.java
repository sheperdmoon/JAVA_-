package main.object;
//250
import java.sql.*;
import java.util.ArrayList;

public class Data {
    Connection con = null;
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/db";
    String name = "root";
    String passwd = "123456";

    /* 加载数据库驱动并连接*/
    public Data(){
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(url, name, passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* 根据传入的用户名和密码与数据库中已有的匹配，判断是否是用户*/
    public boolean login(String username, String password) throws SQLException {
        String sql = "select username,password from user where username=? and password=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        else
            return false;
    }
    /*
        得到数据库中的tasks
     */
    public ArrayList<Task> getAllTasks() throws SQLException {
        String sql = "Select * From task";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rst=ps.executeQuery();
        ArrayList<Task> taskList = new ArrayList<>();
        while (rst.next()) {
            Task task = new Task(rst.getInt("id"), rst.getString("name"),
                    rst.getString("begin"), rst.getString("end"),rst.getString("im"),
                    rst.getString("al"),rst.getString("content"));
            taskList.add(task);
        }
        return taskList;
    }
    /*
        删除数据库中的task
     */
    public void deleteAll(){
        String sql="delete from task";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
        用ArrayList<Task> taskItems来重新载入数据库
     */
    public void updateAll(ArrayList<Task> taskItems){
        deleteAll();
        String sql="insert into task(id,name,begin,end,im,al,content) values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            for(Task task:taskItems){
                ps.setInt(1,task.getId());
                ps.setString(2,task.getName());
                ps.setString(3,task.getBegin());
                ps.setString(4,task.getEnd());
                ps.setString(5,task.getIm());
                ps.setString(6,task.getAl());
                ps.setString(7,task.getContent());
                ps.executeUpdate(); // 注意，每次设置完，都需要直接更新，否则会覆盖。
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //创建一个Statement对象 27;//为sql语句中第一个问号赋值 28 ps.setString(2,"123456");
    }

    /*
        从数据库中初始化dayList
     */
    public void initialDay(Day[][] dayList) throws SQLException {
        String sql = "Select * From day";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rst=ps.executeQuery();
        while(rst.next()){
            int month = rst.getInt(1);
            int day = rst.getInt(2);
            String content = rst.getString(3);
            dayList[month][day]= new Day(month,day,content);
        }
    }
    /*
        更新某天的内容
     */
    public void updateDay(int month, int day,String content) throws SQLException {
        String sql = "select month,day from day where month=? and day=?";
        PreparedStatement ps = null;
        ps = con.prepareStatement(sql);
        ps.setInt(1, month);
        ps.setInt(2, day);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
         //   System.out.println("wwww");
            /*
                sql语句中的？代表参数，也就是parameter，顺序代表index ！！！
             */
            sql = "update day set content=? where month=? and day=?";
            ps = con.prepareStatement(sql);
            ps.setString(1,content);
            ps.setInt(2,month);
            ps.setInt(3,day);
            ps.executeUpdate();
        }
        else {
            sql = "insert into day(month,day,content) values(?,?,?)";
            try {
                ps = con.prepareStatement(sql);
                ps.setInt(1,month);
                ps.setInt(2,day);
                ps.setString(3,content);
                ps.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //创建一个Statement对象 27;//为sql语句中第一个问号赋值 28 ps.setString(2,"123456");
    }

    /*
          添加反思
       */
    public void addReflect(Task task){
        String sql="insert into reflect(id,name,begin,end,im,al,content,reflect) values(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,task.getId());
            ps.setString(2,task.getName());
            ps.setString(3,task.getBegin());
            ps.setString(4,task.getEnd());
            ps.setString(5,task.getIm());
            ps.setString(6,task.getAl());
            ps.setString(7,task.getContent());
            ps.setString(8,"");
            ps.executeUpdate(); // 注意，每次设置完，都需要直接更新，否则会覆盖。
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
       得到所有反思
    */
    public ArrayList<Task> getAllReflects() throws SQLException {
        String sql = "Select * From reflect";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rst=ps.executeQuery();
        ArrayList<Task> reflectList = new ArrayList<>();
        int i = 0;
        while (rst.next()) {
            i++;
            Task task = new Task(i, rst.getString("name"),
                    rst.getString("begin"), rst.getString("end"),rst.getString("im"),
                    rst.getString("al"),rst.getString("content"));
            task.setReflect(rst.getString("reflect"));
            reflectList.add(task);
        }
        return reflectList;
    }

    public void deleteReflect(Task task) throws SQLException {
        String sql = "delete from reflect where name=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1,task.getName());
        ps.executeUpdate();
    }

    public void updateReflect(Task task) throws SQLException {
        String sql = "update reflect set reflect=? where name=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps = con.prepareStatement(sql);
        ps.setString(1,task.getReflect());
        ps.setString(2,task.getName());
        ps.executeUpdate();
    }


    public ArrayList<Note> getAllNotes() throws SQLException {
        String sql = "Select * From note";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rst=ps.executeQuery();
        ArrayList<Note> noteList = new ArrayList<>();
        while (rst.next()) {
            Note note = new Note(rst.getString("title"), rst.getString("content"));
            noteList.add(note);
        }
        return noteList;
    }

    public void deleteNote(String title) throws SQLException {
        String sql = "delete from note where title=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1,title);
        ps.executeUpdate();
    }

    public void addNote(Note note){
        String sql="insert into note(title,content) values(?,?)";
        try {
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,note.getTitle());
            ps.setString(2,note.getContent());
            ps.executeUpdate(); // 注意，每次设置完，都需要直接更新，否则会覆盖。
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
        对标题进行更改时，同步数据库
     */
    public void updateNote(String oldTitle, String newTitle) throws SQLException {
        String sql = "update note set title=? where title=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps = con.prepareStatement(sql);
        ps.setString(1,newTitle);
        ps.setString(2,oldTitle);
        ps.executeUpdate();
    }
    /*
        对内容进行更改时，同步数据库
     */
    public void updateNote_content(String title, String newContent) throws SQLException {
        String sql = "update note set content=? where title=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps = con.prepareStatement(sql);
        ps.setString(1,newContent);
        ps.setString(2,title);
        ps.executeUpdate();
    }

}
