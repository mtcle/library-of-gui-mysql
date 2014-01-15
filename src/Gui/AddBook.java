package Gui;

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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Book;

/**
 * 添加图书的类,主要是添加图书包括图书的图书类型和藏书级别,针对图书区号是和图书类别进行绑定,某一类图书存在某一个区域内,在该区域内有内部编号
 * 读取到区号进行处理,对新增的图书进行区号,编号的设置
 * 系统会通过读取sql数据库读到当前的编号位置,自动追加编号;
 * 系统用到了Book类,依赖bookmgr工程里面的Book类来产生一个新图书
 * */
public class AddBook extends JFrame {

  private static final long serialVersionUID = -4973375427174316669L;
  /**
   * @param addbook from gui
   */
  JRadioButton normal = new JRadioButton("普通", true);
  JRadioButton vip = new JRadioButton("珍藏");
  ButtonGroup group = new ButtonGroup();
  JButton select = new JButton("添加");
  JButton back = new JButton("返回");
  String gethead = new String();
  private String name;
  private String hd = "";
  private String quhao = "";
  String[] head = null;
  String[] headlist = null;
  Font font = new Font("Serif", Font.BOLD, 25);
  Font font2 = new Font("Serif", Font.BOLD, 15);

  public AddBook(String name) throws ClassNotFoundException {
    this.name = name;

    int s = 0;
    Class.forName("com.mysql.jdbc.Driver");
    try {
      Connection connection =
          DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
              "root", "121126");
      // System.out.println("连接成功！");
      Statement statement1 = connection.createStatement();
      ResultSet getint;
      getint = statement1.executeQuery("select count(*) from bookhead");
      while (getint.next()) {
        s = getint.getInt(1);
      }
      Statement statement = connection.createStatement();
      ResultSet getpass;
      getpass = statement.executeQuery("select head,bookstyle,number from bookhead");
      int i = 0;
      String[] temp = new String[s];
      String[] temp2 = new String[s];
      while (getpass.next()) {
        temp[i] = getpass.getString(1);
        temp2[i] = getpass.getString(2);
        i++;
      }
      head = temp;
      headlist = temp2;
      connection.close();
      // System.out.println("连接关闭！");
    } catch (SQLException e1) {
      // System.out.println("sql wrong!");
      // e1.printStackTrace();
    }
    final JList<String> list = new JList<String>(headlist);
    group.add(normal);
    group.add(vip);

    JPanel jpanel = new JPanel();
    JPanel jpanel1 = new JPanel();


    list.addListSelectionListener(new ListSelectionListener() {

      @Override
      public void valueChanged(ListSelectionEvent e) {
        hd = head[list.getSelectedIndex()];//
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
          Statement statement = connection.createStatement();
          ResultSet getquhao;
          getquhao = statement.executeQuery("select number from bookhead where head = '" + hd + "'");
          while (getquhao.next()) {
            if (getquhao.getString(1).length() == 1)
              quhao = "0" + getquhao.getString(1);//为了美观对于区号为个位的前端补一个零
            else
              quhao = getquhao.getString(1);
          }          
          connection.close();
        } catch (SQLException e1) {
          System.out.println("sql wrong!");
          // e1.printStackTrace();
        }
      }
    });
    ButtonListener l = new ButtonListener();
    select.addActionListener(l);
    back.addActionListener(l);
    list.setVisibleRowCount(5);
    JLabel biaoti = new JLabel("图书详细类型选择");
    biaoti.setFont(font);
    JLabel label = new JLabel("图书类别:");
    JLabel label1 = new JLabel("区号");
    JLabel label2 = new JLabel("  藏书级别:");
    label2.setFont(font2);
    label1.setFont(font2);
    label.setFont(font2);
    jpanel.setLayout(new FlowLayout(10, 30, 10));
    jpanel.add(label);
    jpanel.add(new JScrollPane(list));
    jpanel.add(label2);
    // jpanel.add(list);//不用添加了
    jpanel.add(normal);
    jpanel.add(vip);
    jpanel1.add(select);
    jpanel1.add(back);
    jpanel1.setLayout(new FlowLayout(150, 140, 30));
    setLayout(new GridLayout(3, 1, 30, 30));
    JPanel jpanel0 = new JPanel();
    jpanel0.add(biaoti);
    add(jpanel0);
    add(jpanel);
    add(jpanel1);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    int xCoordinate = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();// 获取当前屏幕尺寸,进行设置居中
    int yCoordinate = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    setLocation((int) (xCoordinate - 550) / 2, (int) (yCoordinate - 400) / 2);
    setResizable(false);
    // setLocationRelativeTo(null);
    setSize(550, 400);
    setTitle("添加图书");
  }

  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == back) {
        setVisible(false);
      } else {
        try {
          if (!hd.equals("")) {
            if (normal.isSelected()) {
              new Book(name, hd,quhao, 0);// 针对不同藏书级别设置的图书类别0代表普通,1代表珍藏
              setVisible(false);
            } else {
              new Book(name, hd,quhao, 1);
              setVisible(false);
            }
          } else {
            JOptionPane.showMessageDialog(null, "请选择图书类别！", "提示", 1);
          }
        } catch (ClassNotFoundException | SQLException e1) {
          System.out.println(e1);
        }// 异常处理
      }
    }
  }
}
