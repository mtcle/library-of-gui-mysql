package Gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * 管理员权限的增加,删除会员,会员名称不能相同 也不能非空,写入user表中
 * */
public class AddUser {
  // final static ArrayList<String> username = new ArrayList<String>();//运用数组的是
  // final static ArrayList<String> userpassword = new ArrayList<String>();
  public void adduser(String name, String key) throws ClassNotFoundException, SQLException {
    int sum = 0;// 标志查询到某名字的用户数量
    try {
      Connection connection =
          DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
              "root", "121126");
      // System.out.println("连接成功！");
      Statement statement = connection.createStatement();
      ResultSet getpass;
      getpass = statement.executeQuery("select count(*) from user where id='" + name + "'");
      while (getpass.next()) {
        sum = getpass.getInt(1);
      }
      connection.close();
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "sql错误", "提示", 2);
      // e1.printStackTrace();
    }
    if (sum == 0) {//查询到数据库中无此用户名存在
      Connection connection =
          DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
              "root", "121126");
      Statement statement = connection.createStatement();
      try {
        connection.setAutoCommit(false);
        statement.execute("insert user(id,pass) value ('" + name + "','" + key + "')");
        connection.commit();
      } catch (SQLException e1) {
        JOptionPane.showMessageDialog(null, "服务器异常，添加失败！", "提示", 2);
        connection.rollback();
      } finally {
        connection.close();
      }
      JOptionPane.showMessageDialog(null, "添加成功！", "提示", 1);
    } else {
      JOptionPane.showMessageDialog(null, "该用户名已经被使用", "提示", 1);
      // Login aa = new Login();
      // aa.setVisible(true);
    }
  }
  /**
   * 定义从数据库中删除某会员的方法*/
  public void deluser(String name) throws SQLException {
    int sum = 0;
    try {
      Connection connection =
          DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
              "root", "121126");
      // System.out.println("连接成功！");
      Statement statement = connection.createStatement();
      ResultSet getstr;
      getstr = statement.executeQuery("select count(*) from user where id='" + name + "'");
      while (getstr.next()) {
        sum = getstr.getInt(1);
      }
      connection.close();
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "服务器异常！", "提示", 2);
      // e1.printStackTrace();
    }
    if (sum == 1) {// 查询到此人了
      if (name.equals("admin")) {
        JOptionPane.showMessageDialog(null, "超级管理员账户无法删除！", "警告", 2);
      } else {
        Connection connection =
            DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                "root", "121126");
        try {

          // System.out.println("连接成功！");
          Statement statement = connection.createStatement();
          connection.setAutoCommit(false);
          statement.execute("delete from user where id='" + name + "'");
          connection.commit();
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "服务器异常！", "提示", 2);
          // e1.printStackTrace();
          connection.rollback();
        } finally {
          connection.close();
        }
        JOptionPane.showMessageDialog(null, "删除成功！", "提示", 1);
      }
    } else {
      JOptionPane.showMessageDialog(null, "该用户不存在！", "提示", 2);
    }
  }
}
