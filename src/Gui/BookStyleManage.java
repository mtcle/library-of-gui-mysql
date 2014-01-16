package Gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class BookStyleManage extends JFrame {
  private static final long serialVersionUID = -7647381992063088986L;
  /**
   * @param 对图书类别的管理，设置等
   */
  SqlTool tool = new SqlTool();
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
  private int hang = -1;
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
    setTitle("图书类别管理");
    int size = 0;// 二维数组的长度
    size = tool.getRowOfStatement("select count(*) from bookhead");
    final String[][] getstr = new String[size][4];
    try {
      ResultSet gethead = tool.getQueryStatement("select * from bookhead");
      int p = 0;
      int q = 0;
      while (gethead.next()) {
        for (q = 0; q < 4; q++) {
          getstr[p][q] = gethead.getString(q + 2);
        }
        p++;
        temp = getstr;
      }
      tool.closeConnection();
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "数据库读取错误！", "提示", 1);     
    }

    final JTable table = new JTable(getstr, name);
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {// 对jtable添加一个选择监听

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
    jpanel0.setLayout(new GridLayout());// 默认是流布局
    jpanel0.add(new JScrollPane(table));
    int xCoordinate = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int yCoordinate = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    setLocation((int) (xCoordinate - 800) / 2, (int) (yCoordinate - 600) / 2);
    setSize(800, 600);
    setVisible(true);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == change) {//更改图书类型等信息
        try {
          if (!input.equals("")) {
            String[] header = {"head", "bookstyle", "zujin", "fajin"};
            tool.updateSql("update bookhead set " + header[lie] + " = '" + input + "' where head='"
                + temp[hang][0] + "'");
            JOptionPane.showMessageDialog(null, "操作成功！", "提示", 1);
            dispose();
            new BookStyleManage();
          } else {
            JOptionPane.showMessageDialog(null, "请输入正确的数据！", "提示", 1);
            new BookStyleManage();
          }
        } catch (NullPointerException e1) {}//用户选择取消后的操作
      } else if (e.getSource() == delete) {//
        if (hang > 0) {
          tool.updateSql("delete from bookhead where head= '" + temp[hang][0] + "'");
          JOptionPane.showMessageDialog(null, "操作成功！", "提示", 1);
          dispose();
          new BookStyleManage();
        }
      } else if (e.getSource() == add) {
        try {
          String input = "";
          String bookstyle = "";
          String bookhead = "";
          int sum = 0;// 标记是否有重复的图书类别(不能有重复的),0代表没有
          float zujin = 0;//租金单价
          float fajin = 0;//罚金单价
          input = JOptionPane.showInputDialog(null, "请输入图书类别及两位代号，例如：计算机-TP", "提示", 2);// 去除两头空格
          if (!input.equals("") && (input.length() < 8)) {// 限制输入长度
            bookstyle = input.split("-")[0];//对用户输入的信息进行分析取相应字段
            bookhead = input.split("-")[1];

            sum =
                tool.getRowOfStatement("select count(*) from bookhead where (head='" + bookhead
                    + "' or bookstyle='" + bookstyle + "')");

            if (sum == 0) {// 判断是否有重复的
              if ((bookhead.charAt(0) >= 'A' && bookhead.charAt(0) <= 'Z')//限制为大写字符代号
                  && (bookhead.charAt(1) >= 'A' && bookhead.charAt(1) <= 'Z')) {
                zujin = Float.parseFloat(JOptionPane.showInputDialog(null, "请输入图书租金单价：", "提示", 2));
                if (zujin != 0) {
                  fajin =
                      Float.parseFloat(JOptionPane.showInputDialog(null, "请输入图书罚金单价：", "提示", 2));
                  if (fajin != 0) {
                    tool.updateSql("insert bookhead (head,bookstyle,zujin,fajin)value('" + bookhead
                        + "','" + bookstyle + "'," + zujin + "," + fajin + ")");
                    JOptionPane.showMessageDialog(null, "操作成功！", "提示", 1);
                    dispose();
                    new BookStyleManage();
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
        } catch (NullPointerException e1) {} catch (Exception e1) {
          JOptionPane.showMessageDialog(null, "数据有误！", "提示", 1);
        }
      } else if (e.getSource() == back) {// 返回按钮
        dispose();
      }
    }
  }
}
