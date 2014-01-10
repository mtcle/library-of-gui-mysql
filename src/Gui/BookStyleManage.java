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
  private static final long serialVersionUID = -7647381992063088986L;
  /**
   * @param 对图书类别的管理，设置等
   */
  Font font = new Font("宋体", Font.BOLD, 32);
  JPanel jpanel = new JPanel();
  JPanel jpanel0 = new JPanel();
  JLabel label = new JLabel("                     欢迎使用图书类别管理系统");
  JButton add = new JButton("添加");
  JButton back = new JButton("退出");
  JButton change = new JButton("改变");
  JButton delete = new JButton("删除");
  String[][] temp = null;
  Object tempstr = "";
  Object input = "";
  // String input=null;
  private int hang = -1;
  private int lie;
  private String[] name = {"图书代号", "图书类别", "租金单价", "罚金单价"};

  // private String gethead;
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
    setResizable(false);
    setTitle("图书类别管理");
    int s = 0;
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
      JOptionPane.showMessageDialog(null, "数据库读取错误！", "提示", 2);
      e1.printStackTrace();
    }
    final String[][] getstr = new String[s][4];
    try {
      Connection connection =
          DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
              "root", "121126");
      // System.out.println("连接成功！");
      Statement statement = connection.createStatement();
      ResultSet gethead;
      gethead = statement.executeQuery("select * from bookhead");
      int p = 0;
      int q = 0;
      while (gethead.next()) {
        for (q = 0; q < 4; q++) {
          getstr[p][q] = gethead.getString(q + 2);
          // System.out.println(getstr[p][q]);
        }
        p++;
        temp = getstr;
      }
      connection.close();
      // System.out.println("连接关闭！");
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "数据库读取错误！", "提示", 1);
      e1.printStackTrace();
    }

    final JTable table = new JTable(getstr, name);
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

      @Override
      public void valueChanged(ListSelectionEvent e) {

        hang = table.getSelectedRow();
        lie = table.getSelectedColumn();
        tempstr = table.getValueAt(hang, lie);
        if (tempstr != null) {
          input =
              JOptionPane.showInputDialog(null, "请输入 " + getstr[hang][1] + " 的 " + name[lie]
                  + " 的值：\n若要删除该项可以忽略此项", "提示", 1);
        }
      }
    });

    // jpanel0.removeAll();// 先移除掉上面的控件
    jpanel0.setLayout(new GridLayout());// 默认是流布局
    jpanel0.add(new JScrollPane(table));
    // setLocationRelativeTo(null);
    int xCoordinate = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int yCoordinate = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    setLocation((int) (xCoordinate - 800) / 2, (int) (yCoordinate - 600) / 2);
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void changeSytle() {}

  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == change) {
        if (!input.equals("")) {
          try {
            Connection connection =
                DriverManager.getConnection(
                    "jdbc:mysql://localhost/book_mgr?characterEncoding=utf8", "root", "121126");
            // System.out.println("连接成功！");
            Statement statement = connection.createStatement();
            String[] header = {"head", "bookstyle", "zujin", "fajin"};
            statement.execute("update bookhead set " + header[lie] + " = '" + input
                + "' where head='" + temp[hang][0] + "'");// ///////+++++++++++++++
            connection.close();
            JOptionPane.showMessageDialog(null, "修改成功", "提示", 1);
            dispose();
            new BookStyleManage();
            // System.out.println("连接关闭！");
          } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, "您输入的字段和数据库类型不匹配！", "提示", 2);
          }
        } else {
          JOptionPane.showMessageDialog(null, "请输入正确的数据！", "提示", 1);
          new BookStyleManage();
        }
      } else if (e.getSource() == delete) {//
        System.out.println(hang);
        if (hang > 0) {
          Connection connection = null;
          try {
            connection =
                DriverManager.getConnection(
                    "jdbc:mysql://localhost/book_mgr?characterEncoding=utf8", "root", "121126");
          } catch (SQLException e2) {}
          try {

            Statement statement = connection.createStatement();
            // System.out.println(temp[hang][0]);
            connection.setAutoCommit(false);
            statement.execute("delete from bookhead where head= '" + temp[hang][0] + "'");
            JOptionPane.showMessageDialog(null, "删除成功！", "提示", 1);
            // validate();
            connection.commit();
            dispose();
            new BookStyleManage();
            // jpanel.updateUI();
            // System.out.println("连接关闭！");
          } catch (SQLException e1) {
            e1.printStackTrace();
            try {
              connection.rollback();
            } catch (SQLException e2) {}
          } finally {
            try {
              connection.close();
            } catch (SQLException e1) {}
          }
        }
      } else if (e.getSource() == add) {
        String input = "";
        String bookstyle = "";
        String bookhead = "";
        int sum = 0;
        float zujin = 0;
        float fajin = 0;
        input = JOptionPane.showInputDialog(null, "请输入图书类别及两位代号，例如：计算机-TP", "提示", 2);// 去除两头空格
        if (!input.equals("") && (input.length() < 8)) {// 限制输入长度
          bookstyle = input.split("-")[0];
          bookhead = input.split("-")[1];
          try {
            Connection connection =
                DriverManager.getConnection(
                    "jdbc:mysql://localhost/book_mgr?characterEncoding=utf8", "root", "121126");
            // System.out.println("连接成功！");
            Statement statement = connection.createStatement();
            ResultSet getpass;
            getpass =
                statement.executeQuery("select count(*) from bookhead where (head='" + bookhead
                    + "' or bookstyle='" + bookstyle + "')");
            while (getpass.next()) {
              sum = getpass.getInt(1);
            }
            connection.close();
            // System.out.println("连接关闭！");
          } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, "您以游客身份登陆，权限不足", "提示", 1);
            e1.printStackTrace();
          }
          if (sum == 0) {// 判断是否有重复的
            if ((bookhead.charAt(0) >= 'A' && bookhead.charAt(0) <= 'Z')
                && (bookhead.charAt(1) >= 'A' && bookhead.charAt(1) <= 'Z')) {
              zujin = Float.parseFloat(JOptionPane.showInputDialog(null, "请输入图书租金单价：", "提示", 2));
              if (zujin != 0) {
                fajin = Float.parseFloat(JOptionPane.showInputDialog(null, "请输入图书罚金单价：", "提示", 2));
                if (fajin != 0) {
                  Connection connection = null;
                  try {
                    connection =
                        DriverManager.getConnection(
                            "jdbc:mysql://localhost/book_mgr?characterEncoding=utf8", "root",
                            "121126");
                  } catch (SQLException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                  }
                  try {
                    // System.out.println("连接成功！");
                    Statement statement = connection.createStatement();
                    connection.setAutoCommit(false);// 设置自动提交为false
                    statement.execute("insert bookhead (head,bookstyle,zujin,fajin)value('"
                        + bookhead + "','" + bookstyle + "'," + zujin + "," + fajin + ")");
                    connection.commit();
                    JOptionPane.showMessageDialog(null, "添加成功！", "提示", 1);
                    // jpanel.updateUI();
                    // setVisible(false);
                    dispose();
                    new BookStyleManage();
                    // System.out.println("连接关闭！");
                  } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "数据库有问题了！", "提示", 1);
                    // e1.printStackTrace();
                    try {
                      connection.rollback();
                    } catch (SQLException e2) {}
                  } finally {
                    try {
                      connection.close();// 关闭连接
                    } catch (SQLException e1) {}
                  }
                }
              } else {
                JOptionPane.showMessageDialog(null, "您输入正确的数据：", "提示", 1);
              }
            } else {
              JOptionPane.showMessageDialog(null, "您输入的图书代码有误，请核对后重新输入！", "提示", 1);
            }
          } else {
            JOptionPane.showMessageDialog(null, "有关键字段重复了！", "提示", 1);
          }
        } else {
          JOptionPane.showMessageDialog(null, "请输入正确的数据！", "提示", 1);
        }
      } else if (e.getSource() == back) {// 退出按钮
        System.exit(0);
      }
    }
  }
}
