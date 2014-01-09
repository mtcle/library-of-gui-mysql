package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


/**
 * 登录界面设置 目前有管理员登录和匿名登录及会员登录功能
 * 
 * @author mtcle
 */
public class Login extends JFrame {

  Color bg = new Color(224, 255, 255);
  private static final long serialVersionUID = -5032612745381818733L;
  JButton login = new JButton("登录");
  JRadioButton others = new JRadioButton("游客", true);
  JRadioButton vip = new JRadioButton("会员");
  JRadioButton admin = new JRadioButton("管理员");
  JTextField jtext = new JTextField(10);
  JPasswordField jpassword = new JPasswordField(10);
  static String username = "";
  Font font = new Font("Serif", Font.BOLD, 25);
  Font font2 = new Font("隶书", Font.BOLD, 15);

  public Login() throws ClassNotFoundException, SQLException {



    login.addActionListener(new ButtonListener());// 监听绑定
    jpassword.addActionListener(new ButtonListener());
    JLabel welcome = new JLabel("欢迎使用图书借阅系统");
    welcome.setFont(font2);
    // jtext.setSize(5,10);
    login.setBackground(bg);
    others.setBackground(bg);
    vip.setBackground(bg);
    admin.setBackground(bg);
    // jpassword.setSize(5,10);
    JPanel jpanel = new JPanel();
    JPanel jpanel1 = new JPanel();
    JPanel jpanel2 = new JPanel();
    JLabel username = new JLabel("     账户名：");
    JLabel password = new JLabel("     密码：");
    // jpanel1.setLayout(new BorderLayout());
    jpassword.setEchoChar('*');

    ButtonGroup group = new ButtonGroup();
    group.add(others);
    group.add(admin);
    group.add(vip);
    jpanel1.add(others, BorderLayout.WEST);
    jpanel1.add(vip, BorderLayout.CENTER);
    jpanel1.add(admin, BorderLayout.EAST);
    jpanel1.setBackground(bg);
    jpanel2.setLayout(new GridLayout(2, 2, 5, 5));
    jpanel2.add(username);
    jpanel2.add(jtext);
    jpanel2.add(password);
    jpanel2.add(jpassword);
    jpanel2.setBackground(bg);
    // jpanel.setLayout(new GridLayout(3,1));

    jpanel.add(welcome);
    jpanel.add(jpanel2);
    jpanel.add(jpanel1);
    jpanel.add(login);
    jpanel.setBackground(bg);
    add(jpanel, BorderLayout.CENTER);
    setTitle("登录界面");
    setSize(240, 180);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);
    Class.forName("com.mysql.jdbc.Driver");
  }

  private class ButtonListener implements ActionListener {
    @SuppressWarnings("deprecation")
    public void actionPerformed(ActionEvent e) {
      if ((e.getSource() == login || e.getSource() == jpassword) && admin.isSelected()) {// 管理员登陆
        String passtemp = "";
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
          // System.out.println("连接成功！");
          Statement statement = connection.createStatement();
          ResultSet getpass;
          getpass = statement.executeQuery("select pass from user where id='admin'");
          while (getpass.next()) {
            passtemp = getpass.getString(1);
            // System.out.println("pass"+passtemp);
          }
          connection.close();
          // System.out.println("连接关闭！");
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "您以游客身份登陆，权限不足", "提示", 1);
          e1.printStackTrace();
        }

        if (jtext.getText().equals("admin") && jpassword.getText().equals(passtemp)) {
          setVisible(false);
          MainGui frame;
          try {
            frame = new MainGui();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
          } catch (ClassNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "服务器错误！", "提示", 2);
            // e1.printStackTrace();
          }
          username = jtext.getText();
        } else {
          JOptionPane.showMessageDialog(null, "login failed！", "wraning", 1);
        }
      } else if ((e.getSource() == login || e.getSource() == jpassword) && others.isSelected()) {
        setVisible(false);
        JOptionPane.showMessageDialog(null, "您以游客身份登陆，权限不足", "提示", 1);
        MainGui frame;
        try {
          frame = new MainGui();

          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
        } catch (ClassNotFoundException e1) {
          JOptionPane.showMessageDialog(null, "服务器错误！", "提示", 2);
          // e1.printStackTrace();
        }
        // frame.adduser.setEnabled(false);//本阶段是为了测试方便
        // frame.addBook.setEnabled(false);
        // frame.changePass.setEnabled(false);
        // frame.checkOut.setEnabled(false);
        // frame.checkIn.setEnabled(false);
        // frame.record.setEnabled(false);
        // frame.delete.setEnabled(false);//运行时注释回来
      } else {// vip login
        String vippasstemp = null;// 用户密码
        String name = jtext.getText();
        String pass = jpassword.getText();
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
          Statement statement = connection.createStatement();
          ResultSet getpass;
          getpass = statement.executeQuery("select pass from user where id = '" + name + "'");

          while (getpass.next()) {
            vippasstemp = getpass.getString(1);
          }
          connection.close();
        } catch (SQLException e1) {
          System.out.println("sql wrong!");
          // e1.printStackTrace();
        }
        if (pass.equals(vippasstemp)) {
          setVisible(false);
          JOptionPane.showMessageDialog(null, "您是尊贵的vip！", "提示", 1);
          username = jtext.getText();
          MainGui frame;
          try {
            frame = new MainGui();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.adduser.setEnabled(false);
            frame.addBook.setEnabled(false);
            frame.delete.setEnabled(false);
            frame.managebook.setEnabled(false);
          } catch (ClassNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "服务器连接失败！", "wraning", 2);
          }
        } else {
          JOptionPane.showMessageDialog(null, "login failed！", "wraning", 2);
        }
      }
    }
  }
}
