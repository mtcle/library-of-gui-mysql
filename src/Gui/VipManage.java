package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import tool.SqlTool;

public class VipManage extends JFrame {

  private static final long serialVersionUID = 51102264562388271L;
  JButton adduser = new JButton("增加会员");
  JButton deluser = new JButton("删除会员");
  JButton viewuser = new JButton("浏览会员");
  JButton backmenu = new JButton("返回菜单");
  JTextArea showplace = new JTextArea();
  SqlTool tool = new SqlTool();

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
    setResizable(false);
    setSize(600, 400);
    setVisible(true);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setLocationRelativeTo(null);
  }

  private class ButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      AddUser user = new AddUser();
      StringBuilder str = new StringBuilder();
      if (e.getSource() == viewuser) {
        tool.getQueryStatement("select * from user");
        try {
          ResultSet getUser = tool.getQueryStatement("select * from user");
          while (getUser.next()) {
            str.append("序号：" + getUser.getString(1) + "\t\tid:" + getUser.getString(2) + "\t"
                + "密码：" + getUser.getString(3));
            str.append("\n");
          }
          tool.closeConnection();
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "sql错误", "提示", 2);
        }
        showplace.setText(str.toString());
      } else if (e.getSource() == adduser) {

        String username = "";
        username = JOptionPane.showInputDialog("输入用户名");
        int sum = 0;// 标记当前会员数量
        sum = tool.getRowOfStatement("select count(*) from user where id='" + username + "'");
        if (sum == 0) {
          try {
            if (!username.equals("")) {
              String pass = JOptionPane.showInputDialog("输入密码");
              if (!pass.equals("")) {
                user.adduser(username, pass);
                viewuser.doClick();
              } else
                JOptionPane.showMessageDialog(null, "密码不能为空", "提示", 1);
            } else {
              JOptionPane.showMessageDialog(null, "用户名不能为空", "提示", 1);
            }
          } catch (NullPointerException e1) {}
        } else {
          JOptionPane.showMessageDialog(null, "该用户名已经存在", "提示", 1);
        }
      } else if (e.getSource() == deluser) {// 删除会员
        viewuser.doClick();// 触发一次view按钮的button事件
        String username = JOptionPane.showInputDialog("输入用户名");
        user.deluser(username);
        viewuser.doClick();
      } else {// 返回
        dispose();
      }
    }
  }
}
