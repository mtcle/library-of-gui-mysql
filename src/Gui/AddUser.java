package Gui;

import javax.swing.JOptionPane;

import tool.SqlTool;

/**
 * 管理员权限的增加,删除会员,会员名称不能相同 也不能非空,写入user表中
 * */
public class AddUser {
  SqlTool tool=new SqlTool();
  public void adduser(String name, String key){
    int sum = 0;// 标志查询到某名字的用户数量
    sum=tool.getRowOfStatement("select count(*) from user where id='" + name + "'");
    if (sum == 0) {//查询到数据库中无此用户名存在     
      tool.updateSql("insert user(id,pass) value ('" + name + "','" + key + "')");
      JOptionPane.showMessageDialog(null, "操作成功！", "提示", 1);
    } else {
      JOptionPane.showMessageDialog(null, "该用户名已经被使用", "提示", 1);
    }
  }
  /**
   * 定义从数据库中删除某会员的方法
   */
  public void deluser(String name){
    int sum = 0;//标志是否存在某人
    sum=tool.getRowOfStatement("select count(*) from user where id='" + name + "'");//查询是否有此人
    if (sum == 1) {// 查询到此人了
      if (name.equals("admin")) {//管理员不能被删除
        JOptionPane.showMessageDialog(null, "超级管理员账户无法删除！", "警告", 2);
      } else {
        tool.updateSql("delete from user where id='" + name + "'");
        JOptionPane.showMessageDialog(null, "操作成功！", "提示", 1);
      }
    } else {
      JOptionPane.showMessageDialog(null, "该用户不存在！", "提示", 2);
    }
  }
}
