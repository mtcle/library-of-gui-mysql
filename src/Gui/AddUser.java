package Gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class AddUser {
  final static ArrayList<String> username = new ArrayList<String>();
  final static ArrayList<String> userpassword = new ArrayList<String>();

  public void adduser(String name, String key) throws ClassNotFoundException, SQLException {
    int sum=0;
    try {
      Connection connection =
          DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
              "root", "121126");
      // System.out.println("连接成功！");
      Statement statement = connection.createStatement();
      ResultSet getpass;
      getpass = statement.executeQuery("select count(*) from user where id='"+name+"'");
      while (getpass.next()) {
        sum = getpass.getInt(1);        
      }
      connection.close();
      // System.out.println("连接关闭！");
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "sql错误", "提示", 2);
      e1.printStackTrace();
    }
    if (sum==0) {
//      username.add(name);
//      userpassword.add(key);
      try {
        Connection connection =
            DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                "root", "121126");
        // System.out.println("连接成功！");
        Statement statement = connection.createStatement();
       
        statement.execute("insert user(id,pass) value ('"+name+"','"+key+"')");        
        connection.close();        
      } catch (SQLException e1) {
        JOptionPane.showMessageDialog(null, "服务器异常，添加失败！", "提示", 2);
        e1.printStackTrace();
      }
      JOptionPane.showMessageDialog(null, "添加成功！", "提示", 1);
    } else {
      JOptionPane.showMessageDialog(null, "该用户名已经被使用", "提示", 1);
//      Login aa = new Login();
//      aa.setVisible(true);
    }
  }

//  public ArrayList<String> getUsername() {
//    return username;
//  }
//
//  public ArrayList<String> getUserpassword() {
//    return userpassword;
//  }


  public void deluser(String name) {
    int sum=0;
    try {
      Connection connection =
          DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
              "root", "121126");
      // System.out.println("连接成功！");
      Statement statement = connection.createStatement();     
      ResultSet getstr;
      getstr=statement.executeQuery("select count(*) from user where id='"+name+"'");   
      while(getstr.next()){
        sum=getstr.getInt(1);
      } 
      connection.close();        
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "服务器异常！", "提示", 2);
      e1.printStackTrace();
    }
    if (sum==1) {
      try {
        Connection connection =
            DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                "root", "121126");
        // System.out.println("连接成功！");
        Statement statement = connection.createStatement();             
        statement.execute("delete from user where id='"+name+"'");           
        connection.close();        
      } catch (SQLException e1) {
        JOptionPane.showMessageDialog(null, "服务器异常！", "提示", 2);
        e1.printStackTrace();
      }
      JOptionPane.showMessageDialog(null, "删除成功！", "提示", 1);
    }
    else{
      JOptionPane.showMessageDialog(null, "该用户不存在！", "提示", 2);
    }
  }
}
