package Gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DeleteBook extends JFrame {

  /**
   * 
   */
  private static final long serialVersionUID = 5902371180205079105L;
  private String[] name = {"图书编号", "图书级别", "借出次数"};
  // private JTable jtable=new JTable(null,name);
  JButton delete = new JButton("删除图书");
  JButton backButton = new JButton("返回主菜单");
  private String[][] booklist = null;
  private int size = 0;
  private String id = "";

  public DeleteBook() throws ClassNotFoundException, SQLException {
    setLayout(new GridLayout(2, 1));
    setSize(600, 400);
    setVisible(true);
    setResizable(false);
    setTitle("图书清理平台 只显示在馆图书");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Class.forName("com.mysql.jdbc.Driver");
    Connection connection =
        DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
            "root", "121126");
    Statement statement = connection.createStatement();
    ResultSet getpass;
    getpass = statement.executeQuery("select count(*) from book");
    while (getpass.next()) {
      size = getpass.getInt(1);
    }
    Statement statement1 = connection.createStatement();
    ResultSet getstr;
    getstr = statement1.executeQuery("select * from book where status=0");// 只能删除在馆的图书
    int i = 0;
    String[][] getbook = new String[size][4];
    while (getstr.next()) {
      getbook[i][0] = getstr.getString(2);
      getbook[i][1] = getstr.getString(3);
      getbook[i][2] = getstr.getString(5);
      getbook[i][3] = getstr.getString(6);
      i++;
    }
    booklist = getbook;
    connection.close();
  }

  public void viewTable() {
    final JTable jtable = new JTable(booklist, name);
    jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        int choice = jtable.getSelectedRow();
        if (choice != -1) {
          id = (String) booklist[choice][0];
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
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
          Statement statement = connection.createStatement();
          statement.execute("delete from book where bookid='" + id + "'");
          JOptionPane.showMessageDialog(null, "删除成功", "提示", 1);
          dispose();
          DeleteBook a = new DeleteBook();
          a.viewTable();
          connection.close();
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "服务器异常！", "提示", 1);
          e1.printStackTrace();
        } catch (ClassNotFoundException e1) {}
      }
    }
  }
}
