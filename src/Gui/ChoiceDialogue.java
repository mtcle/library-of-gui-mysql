package Gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import tool.SqlTool;

public class ChoiceDialogue extends JFrame {
  /**
   * 为了解决重名图书可以被用户选择的问题 将某一本图书,重名的都列出来让用户选择
   */
  private static final long serialVersionUID = 470074714744646645L;
  SqlTool tool = new SqlTool();
  JButton checkout = new JButton("借阅");
  JButton back = new JButton("返回");
  private String[] name = {"图书编号", "名字", "图书级别", "借出次数"};
  String id = "";//图书编号

  JLabel jlabel = new JLabel("<html><br><br>为您检索的相关图书，请选择:");
  Font font = new Font("宋体", Font.BOLD, 20);

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
    /**
     * 对表格增加鼠标选择监听,监听用户选择的是哪一本图书
     */
    final JTable jtable = new JTable(a, name);
    jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {// 添加匿名监听类，负责监鼠标选择

          @Override
          public void valueChanged(ListSelectionEvent e) {
            int choice = jtable.getSelectedRow();
            if (choice != -1) {
              id = (String) a[choice][0];//用户选择的那本数
            }
          }
        });
    JPanel temp = new JPanel();
    jlabel.setFont(font);
    temp.add(jlabel);
    add(temp);
    setLayout(new GridLayout(3, 1));
    add(new JScrollPane(jtable));
    checkout.addActionListener(new BackButtonListener());
    back.addActionListener(new BackButtonListener());
    JPanel temp2 = new JPanel();
    temp2.add(checkout);
    temp2.add(back);
    add(temp2);
  }

  class BackButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == back) {// 返回按钮
        setVisible(false);
      } else {// 借书操作
        String bookName = null;//图书名字
        int CheckOutSum = 0;
        try {
          ResultSet getCheckOutSum =
              tool.getQueryStatement("select checkoutsum from book where bookid='" + id + "'");
          while (getCheckOutSum.next()) {
            CheckOutSum = getCheckOutSum.getInt(1);
          }
          CheckOutSum++;// 图书记录加一
          ResultSet getBookName =
              tool.getQueryStatement("select name from book where bookid='" + id + "'");
          while (getBookName.next()) {
            bookName = getBookName.getString(1);
          }
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "sql错误", "提示", 2);
        }
        tool.updateSql("update book set status=1,checkoutsum='" + CheckOutSum + "'  where bookid='"
            + id + "'");//更改图书状态
        Date tempdate = new Date();
        SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd");// 格式化时间输出
        tool.updateSql("insert record (bookid,bookname,userid,checkout,checkin,plan,rent,fajin)value('"
            + id
            + "','"
            + bookName
            + "','"
            + Login.username
            + "','"
            + timeformat.format(tempdate.getTime())
            + "',null,'"
            + timeformat.format(tempdate.getTime() + 20 * 24 * 60 * 60 * 1000l) + "','0','0')");//插入一条借阅记录
        JOptionPane.showMessageDialog(null, "操作成功！", "提示", 1);
        tool.closeConnection();
      }
    }
  }
}
