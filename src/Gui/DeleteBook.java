package Gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tool.SqlTool;

/**
 * 通过将所有图书列出来供管理员进行选择删除
 * 
 * @author mtcle
 */
public class DeleteBook extends JFrame {
  SqlTool tool = new SqlTool();
  private static final long serialVersionUID = 5902371180205079105L;
  private String[] name = {"图书编号", "图书级别", "借出次数"};
  JButton delete = new JButton("删除图书");
  JButton backButton = new JButton("返回主菜单");
  private String[][] bookList = null;//图书组成的二维数组
  private int size = 0;// 当前在馆图书的数目
  private String id = "";

  public DeleteBook() {// 删除图书的方法
    /* 只能删除在管的图书 */
    setLayout(new GridLayout(2, 1));
    setSize(600, 400);
    setVisible(true);
    setResizable(false);
    setTitle("图书清理平台 只显示在馆图书");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    size = tool.getRowOfStatement("select count(*) from book");
    try {
      ResultSet getstr = tool.getQueryStatement("select * from book where status=0");// 只能删除在馆的图书
      int i = 0;
      String[][] getbook = new String[size][4];
      while (getstr.next()) {
        getbook[i][0] = getstr.getString(2);
        getbook[i][1] = getstr.getString(3);
        getbook[i][2] = getstr.getString(5);
        getbook[i][3] = getstr.getString(6);
        i++;
      }
      bookList = getbook;
      tool.closeConnection();
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "服务器异常!", "提示", 2);
    }
  }

  public void viewTable() {
    final JTable jtable = new JTable(bookList, name);
    jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        int choice = jtable.getSelectedRow();
        if (choice != -1) {
          id = (String) bookList[choice][0];
        }
      }
    });
    add(new JScrollPane(jtable));
    backButton.addActionListener(new BackButtonListener());
    delete.addActionListener(new BackButtonListener());
    JPanel temp = new JPanel();
    temp.add(delete);
    temp.add(backButton);
    add(temp);
  }

  class BackButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == backButton) {
        setVisible(false);
      } else {// 删除操作
        tool.updateSql("delete from book where bookid='" + id + "'");
        JOptionPane.showMessageDialog(null, "操作成功！", "提示", 1);
        dispose();
        DeleteBook temp = new DeleteBook();// 为了立即显示更新情况
        temp.viewTable();
      }
    }
  }
}
