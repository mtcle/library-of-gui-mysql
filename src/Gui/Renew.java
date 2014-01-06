package Gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
   * 通过查找借阅记录，找到该用户的借阅记录，更改plan日期
   * @throws SQLException
   * @throws ClassNotFoundException
   * @author mtcle
   * @since jdk1.7
   */
public class Renew extends JFrame {

  private static final long serialVersionUID = -978844632678286447L;
  
  JButton renew = new JButton("续借");
  JButton b = new JButton("返回");
  private String[] name = {"图书编号", "名字", "借出日期", "到期日期"};
  String id = null;
  String[][] temp = null;
  SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");

  public Renew() throws SQLException, ClassNotFoundException {
    Class.forName("com.mysql.jdbc.Driver");
    int size = 0;
    Connection connection =
        DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
            "root", "121126");
    Statement statement = connection.createStatement();
    ResultSet getpass;
    // System.out.println("name" + name);
    getpass =
        statement.executeQuery("select count(*) from record where userid = '" + Login.username
            + "'and checkin is null");
    while (getpass.next()) {
      size = getpass.getInt(1);
    }
    String[][] getbook = new String[size][4];
    Statement statement2 = connection.createStatement();
    ResultSet get;
    // System.out.println("name" + name);
    get =
        statement2.executeQuery("select * from record where userid = '" + Login.username
            + "'and checkin is null");
    int i = 0;
    while (get.next()) {
      getbook[i][0] = get.getString(2);
      getbook[i][1] = get.getString(3);
      getbook[i][2] = get.getString(5);
      getbook[i][3] = get.getString(7);
    }
    i++;
    connection.close();
    temp = getbook;
    setLayout(new GridLayout(3, 1));
    setSize(600, 400);
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("图书续借平台");
  }

  public void viewTable() {
    final JTable jtable = new JTable(temp, name);
    jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

      @Override
      public void valueChanged(ListSelectionEvent e) {
        int choice = jtable.getSelectedRow();
        if (choice != -1) {
          id = (String) temp[choice][0];
          // System.out.println(id);//得到了选择续借的id
        }
      }
    });
    add(new JScrollPane(jtable));
    renew.addActionListener(new BackButtonListener());
    b.addActionListener(new BackButtonListener());
    JPanel temp = new JPanel();
    temp.add(renew);// 为什么不用把表添加到布局中，只用调用方法就ok？
    temp.add(b);
    add(temp);
  }

  private class BackButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == renew) {
        long getdate = 0l;
        long getplan = 0l;
        String newtime = "";
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
          System.out.println("连接成功！");
          Statement statement0 = connection.createStatement();
          ResultSet getpass;
          getpass =
              statement0.executeQuery("select checkout,plan from record where bookid='" + id + "'");
          while (getpass.next()) {
            getdate = getpass.getDate(1).getTime();
            getplan = getpass.getDate(2).getTime();
          }
          newtime = a.format(getplan + (20 * 24 * 60 * 60 * 1000l));
          if (getplan - getdate > (21 * 24 * 60 * 60 * 1000l)) {
            JOptionPane.showMessageDialog(null, "一本图书只有一次续借机会！", "提示", 1);
          } else {
            Statement statement = connection.createStatement();
            statement.execute("update record set plan='" + newtime + "' where bookid='" + id + "'");// 计算后的日期赋值给他
            JOptionPane.showMessageDialog(null, "续借成功！", "提示", 1);
          }
          connection.close();
          // System.out.println("连接关闭！");
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "服务器异常！", "提示", 1);
          // e1.printStackTrace();
        }
      } else {
        setVisible(false);
      }
    }
  }
}
