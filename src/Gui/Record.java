package Gui;

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
import javax.swing.JOptionPane;

import model.Book;

/**
 * 用一个下拉列表框查看某个日期的记录
 */
public class Record extends JFrame {
  private static final long serialVersionUID = 2532471882810750062L;
  JButton a = new JButton("查询");
  JButton b = new JButton("返回");
  String[] temprecordid;
  String choice = null;
  ArrayList<Book> tempBookList;
  private String[][] record = null;

  public Record() throws ClassNotFoundException {
    Class.forName("com.mysql.jdbc.Driver");
    String[] temp = null;

    int s = 0;
    try {
      Connection connection =
          DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
              "root", "121126");
      System.out.println("连接成功！");
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
        temp1[j] = getstr.getString(3);//
        // System.out.println("pass"+passtemp);
        j++;
      }
      temp = temp1;
      connection.close();
      System.out.println("连接关闭！");
    } catch (SQLException e1) {
      System.out.println("sql wrong!");
      e1.printStackTrace();
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
    a.setBounds(200, 320, 80, 30);
    a.addActionListener(l);
    b.setBounds(320, 320, 80, 30);
    b.addActionListener(l);
    setLayout(null);
    bookname.setBounds(30, 20, 130, 22);
    add(bookname);
    add(a);
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
      } else if (e.getSource() == a) {
        // System.out.println("choice"+choice);
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
//          System.out.println("连接成功！");
          Statement statement1 = connection.createStatement();
          int aa = 0;
          ResultSet get;
          get =
              statement1
                  .executeQuery("select count(*) from record where bookname='" + choice + "'");
          while (get.next()) {
            aa = get.getInt(1);
            System.out.println("pass" + aa);
          }
          Statement statement = connection.createStatement();
          ResultSet getpass;
          getpass = statement.executeQuery("select * from record where bookname='" + choice + "'");
          String[][] temp3 = new String[aa][9];
          int j = 0;
          while (getpass.next()) {
            for (int i = 0; i < 9; i++) {
              temp3[j][i] = getpass.getString(i + 1);
            }// System.out.println("pass"+passtemp);
            j++;
          }
          record = temp3;
          connection.close();
//          System.out.println("连接关闭！");
        } catch (SQLException e1) {
          System.out.println("sql wrong!");
          e1.printStackTrace();
        }
        ViewOfRecord temp = new ViewOfRecord();
        setTitle("历史记录查询");
        temp.viewTable(record);
      }
    }
  }
}
