package Gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class AddUser {


  final static ArrayList<String> username = new ArrayList<String>();
  final static ArrayList<String> userpassword = new ArrayList<String>();

  public void adduser(String name, String key) throws ClassNotFoundException, SQLException {
    if (!username.contains(name)) {
      username.add(name);
      userpassword.add(key);
      JOptionPane.showMessageDialog(null, "添加成功！", "提示", 1);
    } else {
      JOptionPane.showMessageDialog(null, "该用户名已经被使用", "提示", 1);
      Login aa = new Login();
      aa.setVisible(true);
    }
  }

  public ArrayList<String> getUsername() {
    return username;
  }

  public ArrayList<String> getUserpassword() {
    return userpassword;
  }


  public void deluser(String name) {
    if (username.contains(name)) {
      userpassword.remove(username.indexOf(name));
      username.remove(name);
      JOptionPane.showMessageDialog(null, "删除成功！", "提示", 1);
    }
    else{
      JOptionPane.showMessageDialog(null, "该用户不存在！", "提示", 2);
    }
  }
}
