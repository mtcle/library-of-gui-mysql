package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import base.BaseJFrame;

import tool.SqlTool;

import model.BookStack;

/**
 * 该程序的主界面包括借阅图书的全部功能 针对不同用户权限进行了不同设置
 * 
 * @author mtcle
 * @version 1.0
 */
public class MainGui extends BaseJFrame {
  private static final long serialVersionUID = 1745017706827567279L;
  Color bg = new Color(224, 255, 255);
  Font font = new Font("Serif", Font.ITALIC, 20);
  Font font0 = new Font("Serif", Font.BOLD, 30);
  SqlTool tool = new SqlTool();
  /**
   * @param this is the main GUI menu
   */
  JButton viewBook = new JButton("浏览图书");
  JButton addBook = new JButton("添加图书");
  JButton checkOut = new JButton("借书");
  JButton checkIn = new JButton("还书");
  JButton record = new JButton("记录");// 下面有该书借书记录，本人借书记录，全部借书记录，按级别不同进行分配权限
  JButton delete = new JButton("删除图书");
  JButton adduser = new JButton("会员管理");
  JButton managebook = new JButton("图书管理");
  JButton changepass = new JButton("修改密码");
  JButton exit = new JButton("退出");
  JPanel menu = new JPanel();

  JTextArea jtext = new JTextArea(" 帮助信息", 30, 11);
  BookStack stack = new BookStack(10);// 初始化书架大小该版本无用

  /**
   * 主操作界面 通过增加重写的buttonlisteneraction和mouselistener行为进行操作处理
   * 
   * @throws ClassNotFoundException
   */
  public MainGui() {
    super();
    setTitle("欢迎使用图书管理系统");
    ButtonListener l = new ButtonListener();
    viewBook.addActionListener(l);
    viewBook.addMouseListener(new MouseAdapter() {// mouse的监听器适配器，不用所以重写所以的抽象方法，方便
          @Override
          public void mouseExited(java.awt.event.MouseEvent e) {
            jtext.setText("提示信息");
          }

          @Override
          public void mouseEntered(java.awt.event.MouseEvent e) {
            jtext.setText("您可以浏览图书信息:包括编号，图书名称，图书级别，借阅状态等信息");
          }
        });
    addBook.addActionListener(l);
    addBook.addMouseListener(new MouseAdapter() {// mouse的监听器适配器，不用所以重写所以的抽象方法，方便
          @Override
          public void mouseExited(java.awt.event.MouseEvent e) {
            jtext.setText("提示信息");
          }

          @Override
          public void mouseEntered(java.awt.event.MouseEvent e) {
            jtext.setText("您只有通过管理员身份才能操作，通过图书名字添加图书，图书级别为默认值");
          }
        });
    checkOut.addActionListener(l);
    checkOut.addMouseListener(new MouseAdapter() {// mouse的监听器适配器，不用所以重写所以的抽象方法，方便
          @Override
          public void mouseExited(java.awt.event.MouseEvent e) {
            jtext.setText("提示信息");
          }

          @Override
          public void mouseEntered(java.awt.event.MouseEvent e) {
            jtext.setText("您可以通过图书名字查询借阅图书,以及相关图书，若有重名，您还需要选择相应图书编号");
          }
        });
    checkIn.addActionListener(l);
    checkIn.addMouseListener(new MouseAdapter() {// mouse的监听器适配器，不用所以重写所以的抽象方法，方便
          @Override
          public void mouseExited(java.awt.event.MouseEvent e) {
            jtext.setText("提示信息");
          }

          @Override
          public void mouseEntered(java.awt.event.MouseEvent e) {
            jtext.setText("您通过图书名字和编号还书，缴纳费用（包括租金和罚金）");
          }
        });
    record.addActionListener(l);
    record.addMouseListener(new MouseAdapter() {// mouse的监听器适配器，不用所以重写所以的抽象方法，方便
          @Override
          public void mouseExited(java.awt.event.MouseEvent e) {
            jtext.setText("提示信息");
          }

          @Override
          public void mouseEntered(java.awt.event.MouseEvent e) {
            jtext.setText("您可以查询图书的借阅历史：包括借阅者id，借出日期，归还日期，租金等。");
          }
        });
    delete.addActionListener(l);
    delete.addMouseListener(new MouseAdapter() {// mouse的监听器适配器，不用所以重写所以的抽象方法，方便
          @Override
          public void mouseExited(java.awt.event.MouseEvent e) {
            jtext.setText("提示信息");
          }

          @Override
          public void mouseEntered(java.awt.event.MouseEvent e) {
            jtext.setText("您可以通过图书名字删除图书");
          }
        });
    adduser.addActionListener(l);
    adduser.addMouseListener(new MouseAdapter() {// mouse的监听器适配器，不用所以重写所以的抽象方法，方便
          @Override
          public void mouseExited(java.awt.event.MouseEvent e) {
            jtext.setText("提示信息");
          }

          @Override
          public void mouseEntered(java.awt.event.MouseEvent e) {
            jtext.setText("您可以对会员进行增加删除等操作。");
          }
        });
    managebook.addActionListener(l);
    managebook.addMouseListener(new MouseAdapter() {// mouse的监听器适配器，不用所以重写所以的抽象方法，方便
          @Override
          public void mouseExited(java.awt.event.MouseEvent e) {
            jtext.setText("提示信息");
          }

          @Override
          public void mouseEntered(java.awt.event.MouseEvent e) {
            jtext.setText("您可以进行图书的租金金额罚金金额，图书类别等的设置修改。");
          }
        });
    viewBook.setFont(font);
    addBook.setFont(font);
    checkOut.setFont(font);
    checkIn.setFont(font);
    record.setFont(font);
    adduser.setFont(font);
    delete.setFont(font);
    managebook.setFont(font);
    exit.setFont(font);
    changepass.setFont(font);
    changepass.addActionListener(l);
    exit.addActionListener(l);
    menu.add(viewBook);
    menu.add(addBook);
    menu.add(checkOut);
    // menu.add(renew);
    menu.add(checkIn);
    menu.add(record);
    menu.add(delete);
    menu.add(adduser);
    menu.add(managebook);
    menu.add(changepass);
    menu.add(exit);
    // menu.setBackground(bg);
    menu.setLayout(new GridLayout(10, 1, 5, 5));

    JLabel label = new JLabel("<html>提<br>示<br>信<br>息");
    label.setFont(font);
    JLabel label0 = new JLabel("<html><br><br>欢<br>迎<br>您<br>使<br>用<br>图<br>书<br>借<br>阅<br>系<br>统");// 添加html标签实现竖着排列
    label0.setForeground(Color.GRAY);
    label.setForeground(Color.CYAN);
    label0.setFont(font0);

    JPanel aa = new JPanel();
    JPanel bb = new JPanel();
    aa.add(label0);
    bb.add(label);
    jtext.setFont(font);
    jtext.setForeground(Color.PINK);
    jtext.setLineWrap(true);
    jtext.setSize(80, 100);
    bb.add(jtext);
    JPanel temp = new JPanel();
    temp.setLayout(new GridLayout(1, 2));
    temp.add(menu);
    temp.add(aa);
    temp.add(bb);
    add(temp);
   
    
    int left = getIntPrefs("left");
    int top = getIntPrefs("top");
    int width = getIntPrefs("width");
    int height = getIntPrefs("height");
    setBounds(left, top, width, height);
    setSize(800, 600);
    addWindowListener(this);

  }

  /**
   * 按钮的事件监听
   */
  private class ButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == viewBook) {// 浏览图书
        int size = 0;
        String sql1="select count(*) from book";
        String sql2 = "select * from book";
        size = tool.getRowOfStatement(sql1);// 查询图书数量建立图书数组
        Object[][] book = new Object[size][5];
        try {
          int j = 0;
          ResultSet getbook = tool.getQueryStatement(sql2);
          while (getbook.next()) {
            book[j][0] = getbook.getString(2);
            book[j][1] = getbook.getString(3);
            book[j][4] = getbook.getString(6);
            if (getbook.getInt(4) == 0) {
              book[j][2] = "在馆";
            } else {
              book[j][2] = "借出";
            }
            if (getbook.getInt(5) == 0) {
              book[j][3] = "普通";
            } else {
              book[j][3] = "珍藏";
            }
            j++;
          }
          tool.closeConnection();
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "数据库错误！", "提示", 2);
        }
        TableOfMgr table = new TableOfMgr();
        table.viewTable(book);
      } else if (e.getSource() == addBook) {// 添加图书
        String name = JOptionPane.showInputDialog(null, "请输入图书名:", "提示", 3);
        try {
          if (!name.equals("") && !name.equals(null)) {
            new AddBook(name);
          } else {
            JOptionPane.showMessageDialog(null, "图书名字不能为空！", "提示", 2);
          }
        } catch (NullPointerException e1) {}
      } else if (e.getSource() == checkOut) {// 借出
        String name = JOptionPane.showInputDialog(null, "输入借阅的图书名,支持模糊搜索", "提示", 3);
        try {
          if (!name.equals("") && !name.equals(null)) {
            int bookStatus = 0;
            String sql = "select count(*) from book where name like '%" + name + "%' and status=0";
            bookStatus = tool.getRowOfStatement(sql);
            if (bookStatus == 0) {
              JOptionPane.showMessageDialog(null, "该图书不存在，请检查拼写是否错误！", "提示", 1);
            } else {
              String[][] temp = stack.checkOut(name);// 定义temp接收checkout的返回值
              ChoiceDialogue a = new ChoiceDialogue();
              a.viewTable(temp);
            }
          } else {
            JOptionPane.showMessageDialog(null, "请输入图书名字！", "提示", 1);
          }
        } catch (NullPointerException e1) {}// 抓取空指针异常,用户取消操作
      } else if (e.getSource() == checkIn) {// 归还图书
        new CheckInRenew();
      } else if (e.getSource() == record) {// 查看记录
        setVisible(false);
        Record record;
        record = new Record();
        record.setVisible(true);
        if (!Login.username.equals("admin")) {
          record.oneBookRecord.setEnabled(false);
        }
      } else if (e.getSource() == delete) {// 删除图书
        DeleteBook temp = new DeleteBook();
        temp.viewTable();
      } else if (e.getSource() == changepass) {//该密码
        String str = "";
        str = JOptionPane.showInputDialog("请输入旧密码：");
        String oldPassword = "";
        try {                 
            oldPassword = (String)tool.queryOne("select pass from user where id='" + Login.username + "'");          
          if (!oldPassword.equals("")) {// 用户会进行取消操作
            if (str.equals(oldPassword)) {
              String newPasswordAgain = null;
              String newPassword = JOptionPane.showInputDialog("请输入新密码：");
              if (newPassword != null) {
                newPasswordAgain = JOptionPane.showInputDialog("请再次输入新密码：");
              } else {
                JOptionPane.showMessageDialog(null, "请输入密码！", "错误", 1);
              }
              if (newPassword.equals(newPasswordAgain)) {
                tool.updateSql("update user set pass= '" + newPassword + "' where id='"
                    + Login.username + "'");
              } else {
                JOptionPane.showMessageDialog(null, "两次密码不相同！", "错误", 1);
              }
            } else {
              JOptionPane.showMessageDialog(null, "密码错误！", "错误", 1);
            }
          }
        } catch (Exception e1) {}// 抓取空指针异常,用户放弃输入时的空指针异常
      } else if (e.getSource() == adduser) {// 管理会员的
        new VipManage();
      } else if (e.getSource() == exit) {
        System.exit(0);
      } else if (e.getSource() == managebook) {// 设置图书罚金，租金，图书类别
        new BookStyleManage();
      }
    }
  }
}
