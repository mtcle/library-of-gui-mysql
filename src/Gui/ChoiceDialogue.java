package Gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ChoiceDialogue extends JFrame {
  /**
   * 为了解决重名图书可以被用户选择的问题
   */
  private static final long serialVersionUID = 470074714744646645L;
  JButton checkout = new JButton("借阅");
  JButton b = new JButton("返回");
  private String[] name = {"图书编号", "名字", "图书级别", "借出次数"};
  String id = "";
  
  JLabel jlabel=new JLabel("<html><br><br>为您检索的相关图书，请选择:");
  Font font=new Font("宋体",Font.BOLD,20);
  
  public ChoiceDialogue() {
    setTitle("图书检索系统");
    setLayout(new GridLayout(3, 1));
    setSize(800, 600);
    setResizable(false);
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void viewTable(final Object[][] a) {
    final JTable jtable = new JTable(a, name);
    jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {//添加匿名监听类，负责监鼠标选择

      @Override
      public void valueChanged(ListSelectionEvent e) {
        int choice = jtable.getSelectedRow();
        if (choice != -1) {
          id = (String) a[choice][0];
          // System.out.println(id);
        }
      }
    });
    JPanel temp = new JPanel();
    jlabel.setFont(font);
    temp.add(jlabel);
    add(temp);
    setLayout(new GridLayout(3,1));
    add(new JScrollPane(jtable));
    checkout.addActionListener(new BackButtonListener());
    b.addActionListener(new BackButtonListener());
      JPanel temp2=new JPanel();  
    temp2.add(checkout);// 为什么不用把表添加到布局中，只用调用方法就ok？
    temp2.add(b);
    add(temp2);
  }

  class BackButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
        Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e2) {
        JOptionPane.showMessageDialog(null, "数据库出错！", "提示", 1);
//        e2.printStackTrace();
      }
      if (e.getSource() == b) {
        setVisible(false);
      } else {// 借书操作
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
//          System.out.println("连接成功！");
          Statement statement0 = connection.createStatement();
          ResultSet getpass;
          getpass =
              statement0.executeQuery("select checkoutsum from book where bookid='" + id + "'");
          int i = 0;
          while (getpass.next()) {
            i = getpass.getInt(1);
          }
          i++;
          Statement statement3 = connection.createStatement();
          ResultSet getname;
          getname = statement3.executeQuery("select name from book where bookid='" + id + "'");
          String bookname = null;
          while (getname.next()) {
            bookname = getname.getString(1);
          }
          Statement statement = connection.createStatement();
          statement.execute("update book set status=1,checkoutsum='" + i + "'  where bookid='" + id
              + "'");
          Date tempdate = new Date();
          SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd");// 格式化时间输出
          statement
              .execute("insert record (bookid,bookname,userid,checkout,checkin,plan,rent,fajin)value('"
                  + id
                  + "','"
                  + bookname
                  + "','"
                  + Login.username
                  + "','"
                  + timeformat.format(tempdate.getTime())
                  + "',null,'"
                  + timeformat.format(tempdate.getTime()+20*24*60*60*1000l) + "','0','0')");

          JOptionPane.showMessageDialog(null, "借阅成功", "提示", 1);
          connection.close();
//          System.out.println("连接关闭！");
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "sql错误", "提示", 2);
//          e1.printStackTrace();
        }
      }
    }
  }
}
