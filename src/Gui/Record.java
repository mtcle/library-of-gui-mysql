package Gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import tool.SqlTool;

/**
 * 可以查看图书记录(管理员权限才可以)
 * 会员可以查看自己的借阅记录
 * @author mtcle
 */
public class Record extends JFrame {
  private static final long serialVersionUID = 2532471882810750062L;
  SqlTool tool = new SqlTool();
  JButton oneBookRecord = new JButton("单本记录");
  JButton userBookRecord = new JButton("我的借书记录");
  JButton back = new JButton("返回");
  String choice = "aadsfasdfasdfasdfasd";
  private String[][] record = null;
  Font font2 = new Font("隶书", Font.BOLD, 17);

  public Record() {
    int size = 0;// 标志数组长度
    size = tool.getRowOfStatement("select count(*) from book");
    String[] temp = new String[size];
    try {
      ResultSet getstr = tool.getQueryStatement("select * from book");
      int j = 0;
      while (getstr.next()) {
        temp[j] = "图书编号：" + getstr.getString(2) + " 图书名字：" + getstr.getString(3);//
        j++;
      }
      tool.closeConnection();
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "服务器错误！", "提示", 2);
    }
    final JComboBox<String> bookName = new JComboBox<>(temp);
    setTitle("历史记录查询");
    bookName.addItemListener(new ItemListener() {// 匿名监听器
          public void itemStateChanged(ItemEvent e) {
            choice = (String) bookName.getSelectedItem();//将获得的类型强转一下
          }
        });
    buttonlisenter l = new buttonlisenter();
    oneBookRecord.setBounds(100, 280, 130, 30);
    oneBookRecord.addActionListener(l);
    userBookRecord.setBounds(260, 280, 130, 30);
    userBookRecord.addActionListener(l);
    back.setBounds(420, 280, 130, 30);
    back.addActionListener(l);
    setLayout(null);
    JLabel jlabel = new JLabel("请选择您要查询的图书:");
    jlabel.setFont(font2);
    bookName.setBounds(150, 60, 330, 30);
    jlabel.setBounds(150, 10, 300, 30);
    add(jlabel);
    add(bookName);
    add(oneBookRecord);
    add(userBookRecord);
    add(back);
    if (!Login.username.equals("admin")) {
      bookName.setEnabled(false);
    }
    setSize(600, 380);
    setLocationRelativeTo(null);
    setResizable(false);
    setTitle("历史记录查询");
  }

  private class buttonlisenter implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int size = 0;// 标志数组长度
      if (e.getSource() == back) {
        setVisible(false);
        MainGui temp;
          temp = new MainGui();
          if (!Login.username.equals("admin")) {
            temp.setVisible(true);
            temp.addBook.setEnabled(false);
            temp.adduser.setEnabled(false);
            temp.delete.setEnabled(false);
            temp.managebook.setEnabled(false);
          }       
      } else if (e.getSource() == userBookRecord) {// 个人历史记录
        size =
            tool.getRowOfStatement("select count(*) from record where userid='" + Login.username
                + "'");
        String[][] temp = new String[size][9];
        try {
          ResultSet getRecord =
              tool.getQueryStatement("select * from record where userid='" + Login.username + "'");
          int j = 0;
          while (getRecord.next()) {
            for (int i = 0; i < 9; i++) {
              temp[j][i] = getRecord.getString(i + 1);
            }
            j++;
          }
          record = temp;
          tool.closeConnection();
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "服务器错误！", "提示", 2);
        }
        if (size > 0) {
          ViewOfRecord viewOfRecord = new ViewOfRecord();
          setTitle("历史记录查询");
          viewOfRecord.viewTable(record);
        } else {
          JOptionPane.showMessageDialog(null, "您暂无借阅记录！", "提示", 1);
        }
      } else if (e.getSource() == oneBookRecord) {// 一本书的历史记录
        try {
          size =
              tool.getRowOfStatement("select count(*) from record where bookid='"
                  + choice.substring(5, 17) + "'");
          String[][] temp = new String[size][9];
          ResultSet getRecord =
              tool.getQueryStatement("select * from record where bookid='"
                  + choice.substring(5, 17) + "'");
          int j = 0;
          while (getRecord.next()) {
            for (int i = 0; i < 9; i++) {
              temp[j][i] = getRecord.getString(i + 1);
            }
            j++;
          }
          record = temp;
          tool.closeConnection();
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "服务器错误！", "提示", 2);
        }
        if (size > 0) {
          ViewOfRecord temp = new ViewOfRecord();
          setTitle("历史记录查询");
          temp.viewTable(record);
        } else {
          JOptionPane.showMessageDialog(null, "该本图书无记录！", "提示", 1);
        }
      }
    }
  }
}
