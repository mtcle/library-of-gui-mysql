package Gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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

import tool.SqlTool;

public class CheckInRenew extends JFrame {
  private static final long serialVersionUID = 5788798799270995381L;
  /**
   * @param 主要是还书时选择图书的归还界面 因为一人可以借阅多本图书可以有选择的续借或者归还某一本图书
   */
  private String[] name = {"序号", "图书编号", "图书名字", "借出日期", "应还日期"};
  SqlTool tool = new SqlTool();// sql工具
  JButton checkin = new JButton("归还");
  JButton renew = new JButton("续借");
  JButton backButton = new JButton("返回主菜单");
  private String[][] tempbook = null;
  private String getbookid = "1234567";
  SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd");// 格式化时间输出

  public CheckInRenew() {
    int size = 0;// 数组大小显示当前用户借阅图书的数量
    try {
      size =
          tool.getRowOfStatement("select count(*) from record where userid='" + Login.username
              + "' and checkin is null");
      ResultSet getbook =
          tool.getQueryStatement("select * from record where userid='" + Login.username
              + "' and checkin is null");
      String[][] book = new String[size][5];
      int j = 0;
      while (getbook.next()) {
        book[j][0] = ("" + (j + 1));
        book[j][1] = getbook.getString(2);
        book[j][2] = getbook.getString(3);
        book[j][3] = getbook.getString(5);
        book[j][4] = getbook.getString(7);
        j++;
      }
      tempbook = book;
      tool.closeConnection();
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "sql异常！", "提示", 2);
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
      if (e.getSource() == backButton) {// 返回按钮
        dispose();
        new MainGui();
      } else if (e.getSource() == checkin) {
        Date tempdate = new Date();
        int getbooklevel = 0;
        Date getCheckout = null;
        Date getplan = null;
        double jisuanzujin = 0;
        double jisuanfajin = 0;
        /**
         * 要通过查询当前用户借阅的图书和他要归还的图书名字是否匹配 若相同则计算费用罚金等 更新记录表
         */
        try {
          ResultSet str =
              tool.getQueryStatement("select bookid,checkout,plan from record where userid='"
                  + Login.username + "' and checkin is null");
          while (str.next()) {
            getCheckout = str.getDate(2);// 借出日期
            getplan = str.getDate(3);// 应该归还日期
          }
          {
            ResultSet level =
                tool.getQueryStatement("select booklevel from book where bookid='" + getbookid
                    + "'");
            while (level.next()) {
              getbooklevel = level.getInt(1);
            }
            ResultSet dengji =
                tool.getQueryStatement("select zujin,fajin from bookhead where head='"
                    + getbookid.substring(0, 2) + "'");
            float zujindanjia = 0;//租金单价
            float fajindanjia = 0;//罚金单价
            while (dengji.next()) {
              zujindanjia = dengji.getFloat(1);
              fajindanjia = dengji.getFloat(2);
            }
            long day = (tempdate.getTime() - getCheckout.getTime()) / (24 * 60 * 60 * 1000);
            long planday = (getplan.getTime() - tempdate.getTime());
            DecimalFormat df = new DecimalFormat("#0.00");// 格式化数据，四舍五入
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
            tool.updateSql("update record set checkin='" + timeformat.format(tempdate.getTime())
                + "',rent='" + df.format(jisuanzujin) + "',fajin='" + df.format(jisuanfajin)
                + "'where bookid='" + getbookid + "'");// 更新记录表记录
            tool.updateSql("update book set status=0 where bookid='" + getbookid + "'");// 更改图书状态
            JOptionPane.showMessageDialog(
                null,
                "租金：" + df.format(jisuanzujin) + "元 罚金：" + df.format(jisuanfajin) + "元 总计："
                    + df.format(jisuanzujin + jisuanfajin) + "元", "提示", 1);
            dispose();
            new CheckInRenew();
          }
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "服务器异常", "提示", 2);
        } catch (Exception e1) {}//抓取空指针异常
      } else {// 续借
        long getDate = 0l;
        long getPlan = 0l;
        String newtime = "";
        int sum = 0;
        try {
          ResultSet getBook =
              tool.getQueryStatement("select checkout,plan from record where bookid='" + getbookid
                  + "'");
          while (getBook.next()) {
            getDate = getBook.getDate(1).getTime();
            getPlan = getBook.getDate(2).getTime();
            sum++;
          }
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "服务器异常！", "提示", 2);
        }
        tool.closeConnection();
        newtime = timeformat.format(getPlan + (20 * 24 * 60 * 60 * 1000l));
        if (sum > 0) {
          if (getPlan - getDate > (21 * 24 * 60 * 60 * 1000l)) {
            JOptionPane.showMessageDialog(null, "一本图书只有一次续借机会！", "提示", 1);
          } else {
            tool.updateSql("update record set plan='" + newtime + "' where bookid='" + getbookid
                + "'");
            JOptionPane.showMessageDialog(null, "续借成功！", "提示", 1);
          }
        }
      }
    }
  }
}
