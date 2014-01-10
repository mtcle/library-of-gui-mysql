package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class VipManage extends JFrame {

  private static final long serialVersionUID = 51102264562388271L;
  JButton adduser = new JButton("增加会员");
  JButton deluser = new JButton("删除会员");
  JButton viewuser = new JButton("浏览会员");
  JButton backmenu = new JButton("返回菜单");
  JTextArea showplace = new JTextArea();

  public VipManage() {
    JPanel jpanel = new JPanel();
    jpanel.setLayout(null);
    ButtonListener l = new ButtonListener();
    adduser.setBounds(50, 320, 90, 30);
    deluser.setBounds(180, 320, 90, 30);
    viewuser.setBounds(310, 320, 90, 30);
    backmenu.setBounds(450, 320, 90, 30);
    showplace.setBounds(50, 30, 500, 280);
    adduser.addActionListener(l);
    deluser.addActionListener(l);
    viewuser.addActionListener(l);
    backmenu.addActionListener(l);
    jpanel.add(showplace);
    jpanel.add(viewuser);
    jpanel.add(adduser);
    jpanel.add(deluser);
    jpanel.add(backmenu);
    add(jpanel);
    setTitle("会员管理平台");
    setVisible(true);
    setResizable(false);
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setLocationRelativeTo(null);  
  }
  
  private class ButtonListener implements ActionListener {
    
    public void actionPerformed(ActionEvent e) {
      try {
        Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e2) {
        // TODO Auto-generated catch block
        // e2.printStackTrace();
      }

      AddUser user = new AddUser();
      StringBuilder str = new StringBuilder();
      if (e.getSource() == viewuser) {
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
          // System.out.println("连接成功！");
          Statement statement = connection.createStatement();
          ResultSet getpass;
          getpass = statement.executeQuery("select * from user");
          while (getpass.next()) {

            // System.out.println("pass"+passtemp);
            str.append("序号：" + getpass.getString(1) + "\t\tid:" + getpass.getString(2) + "\t"
                + "密码：" + getpass.getString(3));
            str.append("\n");
          }
          connection.close();
          // System.out.println("连接关闭！");
        } catch (SQLException e1) {}
        showplace.setText(str.toString());
      } else if (e.getSource() == adduser) {

        String username = "";
        username = JOptionPane.showInputDialog("输入用户名");
        int sum = 0;
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
          // System.out.println("连接成功！");
          Statement statement = connection.createStatement();
          ResultSet getpass;
          getpass = statement.executeQuery("select count(*) from user where id='" + username + "'");
          while (getpass.next()) {
            sum = getpass.getInt(1);
          }
          connection.close();
          // System.out.println("连接关闭！");
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "sql错误", "提示", 2);
          e1.printStackTrace();
        }
        if (sum == 0) {
          try {
            if (!username.equals("")) {
              String pass = JOptionPane.showInputDialog("输入密码");
              if (!pass.equals(""))
              {
                user.adduser(username, pass);                
                viewuser.doClick();
              }
              else
                JOptionPane.showMessageDialog(null, "密码不能为空", "提示", 1);
            } else {
              JOptionPane.showMessageDialog(null, "用户名不能为空", "提示", 1);
            }
          } catch (Exception e1) {}
        } else {
          JOptionPane.showMessageDialog(null, "该用户名已经存在", "提示", 1);
        }
      } else if (e.getSource() == deluser) {// 删除会员
        viewuser.doClick();// 触发一次view按钮的button事件
        String username = JOptionPane.showInputDialog("输入用户名");
        try {
          user.deluser(username);
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "服务器异常，添加失败！", "提示", 1);        }
        viewuser.doClick();
      } else {// 返回
//        setVisible(false);
        dispose();
      }
    }
  }
}
