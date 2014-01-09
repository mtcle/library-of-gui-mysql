package Gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.Book;

/**
 * 用一个下拉列表框查看某个日期的记录
 */
public class Record extends JFrame {
  private static final long serialVersionUID = 2532471882810750062L;
  JButton a = new JButton("单本记录");
  JButton user = new JButton("我的借书记录");
  JButton b = new JButton("返回");
  String[] temprecordid;
  String choice = "aadsfasdfasdfasdfasd";
  ArrayList<Book> tempBookList;
  private String[][] record = null;
  Font font2 = new Font("隶书", Font.BOLD, 17);

  public Record() throws ClassNotFoundException {
    Class.forName("com.mysql.jdbc.Driver");
    String[] temp = null;

    int s = 0;
    try {
      Connection connection =
          DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
              "root", "121126");
      // System.out.println("连接成功！");
      Statement statement = connection.createStatement();
      ResultSet getpass;
      getpass = statement.executeQuery("select count(*) from book");
      while (getpass.next()) {
        s = getpass.getInt(1);
        // System.out.println("pass"+passtemp);
      }
      String[] temp1 = new String[s];
      connection.createStatement();
      ResultSet getstr;
      getstr = statement.executeQuery("select * from book");
      int j = 0;
      while (getstr.next()) {
        temp1[j] = "图书编号：" + getstr.getString(2) + " 图书名字：" + getstr.getString(3);//
        // System.out.println("pass"+passtemp);
        j++;
      }
      temp = temp1;
      connection.close();
      // System.out.println("连接关闭！");
    } catch (SQLException e1) {
      System.out.println("sql wrong!");
      // e1.printStackTrace();
    }
    temprecordid = temp;
    final JComboBox<String> bookname = new JComboBox<>(temp);
    setTitle("历史记录查询");
    bookname.addItemListener(new ItemListener() {// 匿名监听器
          public void itemStateChanged(ItemEvent e) {
            choice = (String) bookname.getSelectedItem();
          }
        });

    buttonlisenter l = new buttonlisenter();
    a.setBounds(100, 280, 130, 30);
    a.addActionListener(l);
    user.setBounds(260, 280, 130, 30);
    user.addActionListener(l);
    b.setBounds(420, 280, 130, 30);
    b.addActionListener(l);
    setLayout(null);
    JLabel jlabel = new JLabel("请选择您要查询的图书:");
    jlabel.setFont(font2);
    bookname.setBounds(150, 60, 330, 30);
    jlabel.setBounds(150, 10, 300, 30);
    add(jlabel);
    add(bookname);
    add(a);
    add(user);
    add(b);
    // add(c);
    setSize(600, 380);
    setLocationRelativeTo(null);
    setResizable(false);
    setTitle("历史记录查询");
  }

  private class buttonlisenter implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int aa = 0;
      if (e.getSource() == b) {
        setVisible(false);
        MainGui temp;
        try {
          temp = new MainGui();
          temp.setVisible(true);
          temp.exit.setVisible(false);
        } catch (ClassNotFoundException e1) {
          JOptionPane.showMessageDialog(null, "服务器错误！", "提示", 2);
        }// 调用系统主界面
      } else if (e.getSource() == user) {
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
          // System.out.println("连接成功！");
          Statement statement1 = connection.createStatement();
          ResultSet get;
          get =
              statement1.executeQuery("select count(*) from record where userid='"
                  + Login.username + "'");
          while (get.next()) {
            aa = get.getInt(1);
          }
          Statement statement = connection.createStatement();
          ResultSet getpass;
          getpass =
              statement.executeQuery("select * from record where userid='"
                  + Login.username + "'");
          String[][] temp3 = new String[aa][9];
          int j = 0;
          while (getpass.next()) {
            for (int i = 0; i < 9; i++) {
              temp3[j][i] = getpass.getString(i + 1);
              // System.out.println("pass"+temp3[j][i]);
            }
            j++;
          }
          record = temp3;
          connection.close();
          // System.out.println("连接关闭！");
        } catch (SQLException e1) {
          System.out.println("sql wrong!");
          // e1.printStackTrace();
        }
        if (aa > 0) {
          ViewOfRecord temp = new ViewOfRecord();
          setTitle("历史记录查询");
          temp.viewTable(record);
        } else {
          JOptionPane.showMessageDialog(null, "您暂无借阅记录！", "提示", 1);
        }
      } else if (e.getSource() == a) {
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
          // System.out.println("连接成功！");
          Statement statement1 = connection.createStatement();
          ResultSet get;
          get =
              statement1.executeQuery("select count(*) from record where bookid='"
                  + choice.substring(5, 17) + "'");
          while (get.next()) {
            aa = get.getInt(1);
          }
          Statement statement = connection.createStatement();
          ResultSet getpass;
          getpass =
              statement.executeQuery("select * from record where bookid='"
                  + choice.substring(5, 17) + "'");
          String[][] temp3 = new String[aa][9];
          int j = 0;
          while (getpass.next()) {
            for (int i = 0; i < 9; i++) {
              temp3[j][i] = getpass.getString(i + 1);
              // System.out.println("pass"+temp3[j][i]);
            }
            j++;
          }
          record = temp3;
          connection.close();
          // System.out.println("连接关闭！");
        } catch (SQLException e1) {
          System.out.println("sql wrong!");
          // e1.printStackTrace();
        }
        if (aa > 0) {
          ViewOfRecord temp = new ViewOfRecord();
          setTitle("历史记录查询");
          temp.viewTable(record);
        } else {
          JOptionPane.showMessageDialog(null, "该本图书无记录！", "提示", 1);
        }
      }
    }
  }
}
