package Gui;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CheckIn extends JFrame {

  /**
   * @param 主要是还书是选择图书的归还界面
   */
  private String[] name = {"序号", "图书编号", "图书名字", "借出日期", "应还日期"};
  // private JTable jtable=new JTable(null,name);
  JButton checkin = new JButton("归还");
  JButton renew = new JButton("续借");
  JButton backButton = new JButton("返回主菜单");
  private String[][] tempbook = null;
  private String getbookid = "aaa";

  public CheckIn() throws ClassNotFoundException {
    int s = 0;
    Class.forName("com.mysql.jdbc.Driver");
    try {
      Connection connection =
          DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
              "root", "121126");
      // System.out.println("连接成功！");
      Statement statement = connection.createStatement();
      ResultSet getpass;
      getpass =
          statement.executeQuery("select count(*) from record where userid='" + Login.username
              + "' and checkin is null");
      while (getpass.next()) {
        s = getpass.getInt(1);
        // System.out.println("pass"+passtemp);
      }
      Statement statement1 = connection.createStatement();
      ResultSet getbook =
          statement.executeQuery("select * from record where userid='" + Login.username
              + "' and checkin is null");
      String[][] book = new String[s][5];
      int j = 0;
      while (getbook.next()) {
        book[j][0] = ("" + j + 1);
        book[j][1] = getbook.getString(2);
        book[j][2] = getbook.getString(3);
        book[j][3] = getbook.getString(5);
        book[j][4] = getbook.getString(7);
        j++;
      }
      tempbook = book;
      connection.close();
      // System.out.println("连接关闭！");
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "sql异常！", "提示", 1);
      e1.printStackTrace();
    }

    setLayout(new GridLayout(2, 1));
    setSize(800, 600);
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    final JTable jtable = new JTable(tempbook, name);
    jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        System.out.println(jtable.getSelectedColumn() + tempbook[jtable.getSelectedRow()][1]);
        getbookid = tempbook[jtable.getSelectedRow()][1];
      }
    });
    add(new JScrollPane(jtable));
    backButton.addActionListener(new BackButtonListener());
    checkin.addActionListener(new BackButtonListener());
    renew.addActionListener(new BackButtonListener());
    JPanel temp = new JPanel();
    temp.add(renew);
    temp.add(checkin);
    temp.add(backButton);
    add(temp);
  }

  class BackButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == backButton) {
        setVisible(false);
      } else if (e.getSource() == checkin) {
        // String name = JOptionPane.showInputDialog(null, "输入图书名", "提示", 3);
        Date tempdate = new Date();
        String getbookid = "TP-10000-10";
        int getbooklevel = 0;
        Date getcheckout = null;
        Date getplan = null;
        SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd");// 格式化时间输出
        double jisuanzujin = 0;
        double jisuanfajin = 0;
        /**
         * 要通过查询当前用户借阅的图书和他要归还的图书名字是否匹配 若相同则计算费用罚金等 更新记录表
         */
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
          System.out.println("连接成功！");
          Statement statement1 = connection.createStatement();
          ResultSet str;
          str =
              statement1.executeQuery("select bookid,checkout,plan from record where userid='"
                  + Login.username + "' and checkin is null");
          int j = 0;
          while (str.next()) {
            // getbookid = str.getString(1);// bookid
            getcheckout = str.getDate(2);// 借出日期
            getplan = str.getDate(3);
            j++;
          }
          {
            Statement statement3 = connection.createStatement();
            ResultSet level;
            level =
                statement3.executeQuery("select booklevel from book where bookid='" + getbookid
                    + "'");
            while (level.next()) {
              getbooklevel = level.getInt(1);
              System.out.println("booklevel="+getbooklevel);
            }
            Statement statement4 = connection.createStatement();
            ResultSet dengji;
            dengji =
                statement4.executeQuery("select zujin,fajin from bookhead where head='"
                    + getbookid.substring(0, 2) + "'");
            float zujindanjia = 1;
            float fajindanjia = 2;
            while (dengji.next()) {
              zujindanjia = dengji.getFloat(1);
              fajindanjia = dengji.getFloat(2);
              System.out.println("=====================" + getbookid.substring(0, 2));
            }

            System.out.println("getbookid=" + getbookid);

            long day = (tempdate.getTime() - getcheckout.getTime()) / (24 * 60 * 60 * 1000);
            long planday = (getplan.getTime() - tempdate.getTime());
            if (getbooklevel == 0) {// normal book
              if (planday >= 0) {//
                jisuanzujin = (day + 1) * zujindanjia;// 此处的图书级别应该也是从数据库中读取的
                jisuanfajin = 0;
              } else {
                jisuanzujin = (day + 1) * zujindanjia;
                jisuanfajin = (-planday * fajindanjia);
              }
            } else {
              if (planday >= 0) {
                jisuanzujin = (day + 1) * zujindanjia * 2;
                jisuanfajin = 0;
              } else {
                jisuanzujin = (day + 1) * zujindanjia * 2;
                jisuanfajin = (-planday * fajindanjia * 2);
              }
            }
            Statement statement = connection.createStatement();
            statement.execute("update record set checkin='" + timeformat.format(tempdate.getTime())
                + "',rent='" + jisuanzujin + "',fajin='" + jisuanfajin + "'where bookid='"
                + getbookid + "'");
            Statement statement2 = connection.createStatement();
            statement2.execute("update book set status=0 where bookid='" + getbookid + "'");
            connection.close();
            JOptionPane.showMessageDialog(null, "租金：" + jisuanzujin + "元 罚金：" + jisuanfajin
                + "元 总计：" + (jisuanzujin + jisuanfajin) + "元", "提示", 1);
          }
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "服务器异常", "提示", 2);
          // System.out.println("sql wrong!");
           e1.printStackTrace();
        }
      } else {// 续借

      }
    }
  }
}
