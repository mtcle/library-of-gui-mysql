package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
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
import javax.swing.JTextArea;

import model.BookStack;

public class MainGui extends JFrame {
  private static final long serialVersionUID = 1745017706827567279L;
  Color bg = new Color(224, 255, 255);
  Font font = new Font("Serif", Font.ITALIC, 20);
  Font font0 = new Font("Serif", Font.BOLD, 30);

  /**
   * @param this is the main GUI menu
   */
  JButton viewBook = new JButton("浏览图书");
  JButton addBook = new JButton("添加图书");
  JButton checkOut = new JButton("借书");
  // JButton renew = new JButton("续借");
  JButton checkIn = new JButton("还书");
  JButton record = new JButton("记录");// 下面有该书借书记录，本人借书记录，全部借书记录，按级别不同进行分配权限
  JButton delete = new JButton("删除图书");
  JButton adduser = new JButton("会员管理");
  JButton managebook = new JButton("图书管理");
  JButton changepass = new JButton("修改密码");
  JButton exit = new JButton("退出");
  JPanel menu = new JPanel();
  JTextArea jtext = new JTextArea(" 帮助信息", 40, 20);
  BookStack stack = new BookStack(10);// 初始化书架大小，后期设计应该归管理员操作

  public MainGui() throws ClassNotFoundException {
    Class.forName("com.mysql.jdbc.Driver");
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
    JLabel label0 = new JLabel("<html><br><br>欢<br>迎<br>您<br>使<br>用<br>该<br>借<br>阅<br>系<br>统");
    label0.setForeground(Color.GRAY);
    label.setForeground(Color.CYAN);
    label0.setFont(font0);
    JPanel temp = new JPanel();
    JPanel aa = new JPanel();
    JPanel bb = new JPanel();

    aa.add(label0);
    bb.add(label);
    jtext.setFont(font);
    jtext.setForeground(Color.PINK);
    jtext.setLineWrap(true);
    jtext.setSize(80, 100);
    bb.add(jtext);
    // aa.setBackground(bg);

    temp.setLayout(new GridLayout(1, 2));
    temp.add(menu);
    temp.add(aa);
    temp.add(bb);
    // aa.setBackground(bg);
    // bb.setBackground(bg);
    setResizable(false);
    add(temp);
    setSize(800, 600);
    setLocationRelativeTo(null);
  }


  private class ButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      Object[][] obj = null;// 定义书架列表
      if (e.getSource() == viewBook) {// 浏览图书
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr?characterEncoding=utf8",
                  "root", "121126");
          // jdbc:mysql://地址:3306/数据库名?characterEncoding=utf8
          System.out.println("连接成功！");
          Statement statement = connection.createStatement();
          ResultSet count = statement.executeQuery("select count(*) from book");
          int size = 0;
          while (count.next()) {
            size = count.getInt(1);
            // System.out.println(size);
          }
          Object[][] temp = new Object[size][5];
          int j = 0;
          ResultSet getpass = statement.executeQuery("select * from book");
          while (getpass.next()) {
            temp[j][0] = getpass.getString(2);
            temp[j][1] = getpass.getString(3);
            temp[j][4] = getpass.getString(6);
            if (getpass.getInt(4) == 0) {
              temp[j][2] = "在馆";
            } else {
              temp[j][2] = "借出";
            }
            if (getpass.getInt(5) == 0) {
              temp[j][3] = "普通";
            } else {
              temp[j][3] = "珍藏";
            }
            j++;
          }
          obj = temp;
          connection.close();
          // System.out.println("连接关闭！");
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "数据库错误！", "提示", 2);
          // e1.printStackTrace();
        }
        TableOfMgr table = new TableOfMgr();
        table.viewTable(obj);
      } else if (e.getSource() == addBook) {// 添加图书
        String name = JOptionPane.showInputDialog(null, "请输入图书名:", "提示", 3);
        try {
          if (!name.equals("") && !name.equals(null)) {
            new AddBook(name);
          } else {
            JOptionPane.showMessageDialog(null, "图书名字不能为空！", "提示", 2);
          }
        } catch (ClassNotFoundException e1) {
          JOptionPane.showMessageDialog(null, "数据库错误！", "提示", 1);
          // e1.printStackTrace();
        } catch (Exception e1) {
          // JOptionPane.showMessageDialog(null, "", "提示", 1);
          // e1.printStackTrace();
        }
      } else if (e.getSource() == checkOut) {// 借出

        try {
          String name = JOptionPane.showInputDialog(null, "输入借阅的图书名,支持模糊搜索：", "提示", 3);
          String[][] temp = null;
          if (!name.equals("") && !name.equals(null)) {
            int s = 0;
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection =
                DriverManager.getConnection(
                    "jdbc:mysql://localhost/book_mgr?characterEncoding=utf8", "root", "121126");
            // System.out.println("连接成功！");
            Statement statement1 = connection.createStatement();
            ResultSet getpass;
            getpass =
                statement1.executeQuery("select count(*) from book where name like '%" + name
                    + "%' and status=0");
            while (getpass.next()) {
              s = getpass.getInt(1);
            }
            if (s == 0) {
              JOptionPane.showMessageDialog(null, "该图书不存在，请检查拼写是否错误！", "提示", 1);
            } else {
              temp = stack.checkOut(name);
              ChoiceDialogue a = new ChoiceDialogue();
              a.viewTable(temp);
            }
            connection.close();
            // System.out.println("连接关闭！");
          } else {
            JOptionPane.showMessageDialog(null, "请输入图书名字！", "提示", 1);
          }
        } catch (Exception e1) {
          // JOptionPane.showMessageDialog(null, "系统出错了！", "提示", 2);
        }
      } else if (e.getSource() == checkIn) {// 归还图书
        try {
          new CheckInRenew();
        } catch (ClassNotFoundException e1) {
          JOptionPane.showMessageDialog(null, "数据库错误！", "提示", 2);
          // e1.printStackTrace();
        }
      } else if (e.getSource() == record) {// 查看记录
        setVisible(false);
        Record a;
        try {
          a = new Record();
          a.setVisible(true);
          if(!Login.username.equals("admin")){
            a.a.setEnabled(false);
          }
        } catch (ClassNotFoundException e1) {
          JOptionPane.showMessageDialog(null, "数据库错误！", "提示", 2);
          // e1.printStackTrace();
        }
      } else if (e.getSource() == delete) {// 删除图书
        DeleteBook test;
        try {
          test = new DeleteBook();
          test.viewTable();
        } catch (ClassNotFoundException e1) {
          JOptionPane.showMessageDialog(null, "数据库错误！", "提示", 2);
          // e1.printStackTrace();
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "数据库错误！", "提示", 2);
        }

      } else if (e.getSource() == changepass) {
        String str = "";
        str = JOptionPane.showInputDialog("请输入旧密码：");
        String passtemp = "";
        try {
          Connection connection =
              DriverManager.getConnection("jdbc:mysql://localhost/book_mgr", "root", "121126");
          // System.out.println("连接成功！");
          Statement statement = connection.createStatement();
          ResultSet getpass;
          getpass =
              statement.executeQuery("select pass from user where id='" + Login.username + "'");
          while (getpass.next()) {
            passtemp = getpass.getString(1);
          }

          if (!passtemp.equals("")) {
            if (str.equals(passtemp)) {
              String str2 = null;
              String str1 = JOptionPane.showInputDialog("请输入新密码：");
              if (str1 != null) {
                str2 = JOptionPane.showInputDialog("请再次输入新密码：");
              } else {
                JOptionPane.showMessageDialog(null, "请输入密码！", "错误", 1);
              }
              if (str1.equals(str2)) {
                Statement statement1 = connection.createStatement();
                statement1.execute("update user set pass= '" + str1 + "' where id='"
                    + Login.username + "'");// 更新数据库里的该用户的密码
                connection.close();
                JOptionPane.showMessageDialog(null, "修改成功！", "提示", 1);
              } else {
                JOptionPane.showMessageDialog(null, "两次密码不相同！", "错误", 1);
              }
            } else {
              JOptionPane.showMessageDialog(null, "密码错误！", "错误", 1);
            }
          }
        } catch (Exception e1) {}//抓取空指针异常的用户放弃输入时的空指针异常
      } else if (e.getSource() == adduser) {// 管理会员的
        new VipManage();
      } else if (e.getSource() == exit) {
        System.exit(0);
      } else if (e.getSource() == managebook) {// 设置图书罚金，租金，图书类别
         dispose();       
        new BookStyleManage();        
      }
    }
  }
}
