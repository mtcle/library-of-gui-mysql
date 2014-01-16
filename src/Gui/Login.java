package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import tool.SqlTool;

/**
 * Login类是登录界面设置 目前有管理员登录和匿名登录及会员登录功能 不同的登录级别有不同的权限,管理员和会员都可以修改密码
 * 
 * @author mtcle
 * @version 1.0
 */
public class Login extends JFrame {
  SqlTool tool = new SqlTool();
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

  /**
   * Login()方法主要是登录界面的设置以及用户的验证
   * 
   * @param 登录名:username
   * @author mtcle
   */
  public Login() {
    login.addActionListener(new ButtonListener());// 监听绑定
    jpassword.addActionListener(new ButtonListener());
    JLabel welcome = new JLabel("请登录");
    welcome.setFont(font2);
    // jtext.setSize(5,10);
    login.setBackground(bg);
    others.setBackground(bg);
    vip.setBackground(bg);
    admin.setBackground(bg);
    JPanel jpanel = new JPanel();
    JPanel jpanel1 = new JPanel();
    JPanel jpanel2 = new JPanel();
    JLabel username = new JLabel("     账户名：");
    JLabel password = new JLabel("     密码：");
    jpanel1.setLayout(new BorderLayout());
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
  }

  /**
   * 实现监听
   * 
   * @category 内部类
   * @author mtcle
   */
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if ((e.getSource() == login || e.getSource() == jpassword) && admin.isSelected()) {// 管理员登陆
        String passtemp = "";
        try {
          String sql = "select pass from user where id='admin'";
          ResultSet result = tool.getQueryStatement(sql);
          while (result.next()) {
            passtemp = result.getString(1);
          }
          tool.closeConnection();
        } catch (SQLException e2) {
          System.out.println(e2);
        }

        if (jtext.getText().equals("admin")
            && tool.getStringOfPassword(jpassword.getPassword()).equals(passtemp)) {// 调用了工具类的密码转换方法
          setVisible(false);
          // dispose();
          MainGui frame;
          frame = new MainGui();
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
          username = jtext.getText();
        } else {
          JOptionPane.showMessageDialog(null, "login failed！", "wraning", 1);
        }
      } else if ((e.getSource() == login || e.getSource() == jpassword) && others.isSelected()) {// 游客登录
        setVisible(false);
        JOptionPane.showMessageDialog(null, "您以游客身份登陆，权限不足", "提示", 1);
        MainGui frame = null;
        frame = new MainGui();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.adduser.setEnabled(false);// 本阶段是为了测试方便
        frame.addBook.setEnabled(false);
        frame.managebook.setEnabled(false);
        frame.changepass.setEnabled(false);
        frame.checkOut.setEnabled(false);
        frame.checkIn.setEnabled(false);
        frame.record.setEnabled(false);
        frame.delete.setEnabled(false);// 运行时注释回来
      } else {// vip login
        String vippasstemp = null;// 用户密码
        String name = jtext.getText();
        String pass = tool.getStringOfPassword(jpassword.getPassword());
        String sql = "select pass from user where id = '" + name + "'";
        try {
          ResultSet result = tool.getQueryStatement(sql);
          while (result.next()) {
            vippasstemp = result.getString(1);
          }
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "sql出错！", "警告", 2);
        }
        tool.closeConnection();
        if (pass.equals(vippasstemp)) {
          setVisible(false);
          JOptionPane.showMessageDialog(null, "您是尊贵的vip！", "提示", 1);
          username = jtext.getText();
          MainGui frame;
          frame = new MainGui();
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
          frame.adduser.setEnabled(false);
          frame.addBook.setEnabled(false);
          frame.delete.setEnabled(false);
          frame.managebook.setEnabled(false);
        } else {
          JOptionPane.showMessageDialog(null, "login failed！", "wraning", 2);
        }
      }
    }
  }
}
