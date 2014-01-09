package Gui;

import java.awt.GridLayout;
import java.awt.Toolkit;
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

/**
 * 该类的数据都是静态的，主要供其他类使用 涉及图书类别，租金单价，罚金单价设置
 * */
public class ManageOthers extends JFrame {

  private static final long serialVersionUID = -2322361950167799258L;
  public static double rent = 1;
  public static double fajin = 2;
  public static String bookStyle = "TP";
  public static String password = "123456";
  JButton rentbutton = new JButton("租金单价");
  JButton fajinbutton = new JButton("罚金单价");
  JButton bookStylebutton = new JButton("图书类别");//再增加一个图书容量设置
  JButton changepass = new JButton("修改密码");
  JButton back = new JButton("返回上级");
  ButtonListener l = new ButtonListener();
  JPanel jpanel = new JPanel();

  public ManageOthers() throws ClassNotFoundException {
    rentbutton.addActionListener(l);
    fajinbutton.addActionListener(l);
    bookStylebutton.addActionListener(l);
    changepass.addActionListener(l);
    back.addActionListener(l);
    jpanel.setLayout(new GridLayout(5, 1, 20, 40));
    JPanel a = new JPanel();
    JPanel b = new JPanel();
    JPanel c = new JPanel();
    jpanel.add(rentbutton);
    jpanel.add(fajinbutton);
    jpanel.add(bookStylebutton);
    jpanel.add(changepass);
    jpanel.add(back);
    c.setLayout(new GridLayout(1, 3, 5, 5));
    c.add(jpanel);
    c.add(a);
    c.add(b);
    add(c);
    setTitle("综合管理平台");
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    int xCoordinate = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int yCoordinate = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    setLocation((int) (xCoordinate - 550) / 2, (int) (yCoordinate - 400) / 2);    
    setSize(400, 300);
    Class.forName("com.mysql.jdbc.Driver");
  }

  private class ButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      try {
        if (e.getSource() == rentbutton) {
          String str = JOptionPane.showInputDialog("当前普通图书单价为：" + rent + "元，请输入新的租金单价：");
          rent = Double.parseDouble(str);
        } else if (e.getSource() == fajinbutton) {
          String str = JOptionPane.showInputDialog("当前罚金为:" + fajin + "元，请输入新的罚金单价：");
          fajin = Double.parseDouble(str);
        } else if (e.getSource() == changepass) {
          String str = JOptionPane.showInputDialog("请输入旧密码：");
          String passtemp = "";
          try {
            Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost/book_mgr", "root", "121126");
//            System.out.println("连接成功！");
            Statement statement = connection.createStatement();
            ResultSet getpass;
            getpass = statement.executeQuery("select pass from user where id='admin'");
            while (getpass.next()) {
              passtemp = getpass.getString(1);
              // System.out.println("pass"+passtemp);
            }
            connection.close();
//            System.out.println("连接关闭！");
          } catch (SQLException e1) {
            System.out.println("sql wrong!");
            e1.printStackTrace();
          }
          if (str.equals(passtemp)) {
            String str1 = JOptionPane.showInputDialog("请输入新密码：");
            String str2 = JOptionPane.showInputDialog("请再次输入新密码：");
            if (str1.equals(str2)) {
              Connection connection =
                  DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8", "root", "121126");
              Statement statement = connection.createStatement();
              statement.execute("update user set pass= '" + str1 + "' where id='admin'");//更新数据库里的admin密码
              // password = str1;//以前的做法
              connection.close();
//              System.out.println("连接关闭！");
              JOptionPane.showMessageDialog(null, "修改成功！", "提示", 1);
            } else {
              JOptionPane.showMessageDialog(null, "两次密码不相同！", "错误", 1);
            }
          } else {
            JOptionPane.showMessageDialog(null, "密码错误！", "错误", 1);
          }
        } else if (e.getSource() == back) {          
          setVisible(false);
          new MainGui();
          
        } else {
//          new BookStyleManage();
//          String str = JOptionPane.showInputDialog("输入图书类别");
//          if (!str.equals("")) bookStyle = str;
        }
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "请输入正确的数据！", "错误", 1);
      }
    }
  }
}
