package Gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BookStyleManage extends JFrame {

  /**
   * 
   */
  private static final long serialVersionUID = -7647381992063088986L;
  /**
   * @param 对图书类别的管理，设置等
   */
  Font font = new Font("宋体", Font.BOLD, 18);
  JPanel jpanel = new JPanel();
  JPanel jpanel0=new JPanel();
  JLabel label = new JLabel("                         欢迎使用图书类别管理系统");
  JButton add = new JButton("添加");
  JButton back = new JButton("返回");
  JButton change = new JButton("改变");
  JButton delete = new JButton("删除");
  String[][] temp=null;
  Object tempstr="";
  Object input="";
//  String input=null;
 private int hang=-1;
 private int lie;
  private String[] name = {"图书代号", "图书类别", "租金单价", "罚金单价"};
 
  public BookStyleManage() {
    ButtonListener l = new ButtonListener();
    add.addActionListener(l);
    change.addActionListener(l);
    delete.addActionListener(l);
    back.addActionListener(l);
    jpanel.setLayout(new FlowLayout());
    jpanel.setLayout(new FlowLayout());
    jpanel.add(add);
    jpanel.add(change);
    jpanel.add(delete);
    jpanel.add(back);
    setLayout(new GridLayout(3, 1, 10, 10));
    label.setFont(font);
    label.setForeground(Color.PINK);
    
    add(label);
    add(jpanel0);
    add(jpanel);
    setVisible(true);
    setTitle("图书类别管理");
    int s=0;
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e2) {
      // TODO Auto-generated catch block
      e2.printStackTrace();
    }
    try {
      Connection connection =
          DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
              "root", "121126");
      // System.out.println("连接成功！");
      Statement statement = connection.createStatement();
      ResultSet getpass;
      getpass = statement.executeQuery("select count(*) from bookhead");
      while (getpass.next()) {
        s = getpass.getInt(1);          
      }
      connection.close();
      // System.out.println("连接关闭！");
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "您以游客身份登陆，权限不足", "提示", 1);
      e1.printStackTrace();
    }
    final String[][] getstr=new String[s][4];
    try {
      Connection connection =
          DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
              "root", "121126");
      // System.out.println("连接成功！");
      Statement statement = connection.createStatement();
      ResultSet gethead;
      gethead = statement.executeQuery("select * from bookhead");
      int p=0;
      int q=0;
      while (gethead.next()) {
        for(q=0;q<4;q++){
        getstr[p][q]=gethead.getString(q+2);  
        }
        p++;        
      }
      connection.close();
      // System.out.println("连接关闭！");
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "您以游客身份登陆，权限不足", "提示", 1);
      e1.printStackTrace();
    }
    
    final  JTable table = new JTable(getstr,name);  
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      
      @Override
      public void valueChanged(ListSelectionEvent e) {
        hang=table.getSelectedRow();
        lie=table.getSelectedColumn();
        System.out.println("选择的行是:"+table.getSelectedRow());
        System.out.println("lie:"+table.getSelectedColumn());
        table.editCellAt(hang, lie);
        System.out.println(table.getValueAt(hang,lie));//输出null
        tempstr=table.getValueAt(hang, lie);
        if(tempstr!=null){
          System.out.println("+++++"+tempstr);
          input=JOptionPane.showInputDialog(null, "请输入 "+getstr[hang][1]+" 的 "+name[lie]+" 的值：\n若要删除该项可以忽略此项", "提示", 2);
        }
      }
    });
   
    jpanel0.removeAll();//先移除掉上面的控件
    jpanel0.setLayout(new GridLayout());//默认是流布局
    jpanel0.add(new JScrollPane(table));
//    temp=getstr;  
    // setLocationRelativeTo(null);
    int xCoordinate = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int yCoordinate = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    setLocation((int) (xCoordinate - 550) / 2, (int) (yCoordinate - 400) / 2);
    setSize(550, 480);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void changeSytle() {}
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {      
      if (e.getSource() == change) {        
//        System.out.println("tset"+tempstr);
       
        if (!input.equals("")){
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
          // System.out.println("连接成功！");
          Statement statement = connection.createStatement();
          String[] header={"head","bookstyle","zujin","fajin"};
          statement.execute("update bookhead set "+header[lie]+" = '"+input+"' where "+header[lie]+" = '"+tempstr+"' and number='"+(hang+1)+"'");          
          connection.close();
          JOptionPane.showMessageDialog(null, "修改成功", "提示", 1);
//          validate();
          new BookStyleManage();
          // System.out.println("连接关闭！");
        } catch (SQLException e1) {
//          JOptionPane.showMessageDialog(null, "您以游客身份登陆，权限不足", "提示", 1);
          JOptionPane.showMessageDialog(null, "您输入的字段和数据库类型不匹配！", "提示", 2);
        }
        }
        else {
          JOptionPane.showMessageDialog(null, "请输入正确的数据！", "提示", 1);
          new BookStyleManage();
        }                      
      } else if (e.getSource() == delete) {
        
       if  (hang>0){
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
          // System.out.println("连接成功！");
          Statement statement = connection.createStatement();  
          System.out.println(hang+1);
          statement.execute("delete from bookhead where number= "+(hang+1)+"");          
          connection.close();
          JOptionPane.showMessageDialog(null, "删除成功！", "提示", 1);
//          validate();
          new BookStyleManage();
          // System.out.println("连接关闭！");
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
       }else if(e.getSource()==add){
         //判断输入
       }
       else{
         JOptionPane.showMessageDialog(null, "请选择要删除的图书类别！", "提示", 1);
       }
      
      
      } else {
        setVisible(false);
        try {
          new ManageOthers();
        } catch (ClassNotFoundException e2) {
          // TODO Auto-generated catch block
          e2.printStackTrace();
        }
        try {
          new ManageOthers();
        } catch (ClassNotFoundException e1) {}
      }
    }
  }
}
