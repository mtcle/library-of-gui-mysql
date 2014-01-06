package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
  JTextArea showplace=new JTextArea();

  public VipManage() {
    JPanel jpanel = new JPanel();
    jpanel.setLayout(null);
    ButtonListener l = new ButtonListener();
    adduser.setBounds(50, 320, 90, 30);
    deluser.setBounds(180, 320, 90, 30);
    viewuser.setBounds(310, 320,90, 30);
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
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }

  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
     AddUser user = new AddUser(); 
     StringBuilder str=new StringBuilder();
     if (e.getSource() == viewuser) {
       for(int i=0;i<user.getUsername().size();i++){                
         str.append("序号："+(i+1)+"\tid:"+ user.getUsername().get(i)+"\t"+"\t密码："+user.getUserpassword().get(i));
         str.append("\n");
       }
       showplace.setText(str.toString());
      } else if (e.getSource() == adduser) {
        
        String username = JOptionPane.showInputDialog("输入用户名");
        if (!(username.equals(""))) {
          String pass = JOptionPane.showInputDialog("输入密码");
          if (!(pass.equals("")))
            try {
              user.adduser(username, pass);
            } catch (ClassNotFoundException | SQLException e1) {
              // 待处理
              e1.printStackTrace();
            }
          else
            JOptionPane.showMessageDialog(null, "密码不能为空", "提示", 1);
        } else {
          JOptionPane.showMessageDialog(null, "用户名不能为空", "提示", 1);
        }
      } else if(e.getSource()==deluser) {//删除会员
        String username = JOptionPane.showInputDialog("输入用户名");        
        user.deluser(username);
      }
      else{//返回
       setVisible(false);
      }
    }
  }
}
