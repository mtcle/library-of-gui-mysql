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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.BookStack;

public class MainGui extends JFrame {
  private static final long serialVersionUID = 1745017706827567279L;
  Color bg = new Color(224, 255, 255);
  Font font = new Font("Serif", Font.ITALIC, 15);
  /**
   * @param this is the main GUI menu
   */
  JButton viewBook = new JButton("浏览图书");
  JButton addBook = new JButton("添加图书");
  JButton checkOut = new JButton("借书");
  JButton renew=new JButton("续借");
  JButton checkIn = new JButton("还书");
  JButton record = new JButton("记录");// 下面有该书借书记录，本人借书记录，全部借书记录，按级别不同进行分配权限
  JButton delete = new JButton("删除图书");
  JButton adduser = new JButton("会员管理");
  JButton managebook = new JButton("综合管理");
  JButton changePass = new JButton("修改密码");
  JButton exit = new JButton("退出");
  JPanel menu = new JPanel();
  JTextArea jtext = new JTextArea(" 帮助信息", 14, 10);
  BookStack stack = new BookStack(10);// 初始化书架大小，后期设计应该归管理员操作
  public MainGui() throws ClassNotFoundException {
    Class.forName("com.mysql.jdbc.Driver");
    setTitle("欢迎使用图书管理系统");
    ButtonListener l = new ButtonListener();
    viewBook.addActionListener(l);
    viewBook.addMouseListener(new MouseAdapter() {// mouse的监听器适配器，不用所以重写所以的抽象方法，方便
          @Override
          public void mouseExited(java.awt.event.MouseEvent e) {
            jtext.setText("");
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
            jtext.setText("");
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
            jtext.setText("");
          }

          @Override
          public void mouseEntered(java.awt.event.MouseEvent e) {
            jtext.setText("您可以通过图书名字查询借阅图书，若有重名，您还需要选择相应图书编号");
          }
        });
    renew.addActionListener(l);
    renew.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(java.awt.event.MouseEvent e) {
        jtext.setText("");
      }

      @Override
      public void mouseEntered(java.awt.event.MouseEvent e) {
        jtext.setText("您可以续借您借阅的图书，默认借阅日期为20天，续借后为40天。谢谢您的使用。");
      }
    });
   
    checkIn.addActionListener(l);
    checkIn.addMouseListener(new MouseAdapter() {// mouse的监听器适配器，不用所以重写所以的抽象方法，方便
          @Override
          public void mouseExited(java.awt.event.MouseEvent e) {
            jtext.setText("");
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
            jtext.setText("");
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
            jtext.setText("");
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
            jtext.setText("");
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
            jtext.setText("");
          }

          @Override
          public void mouseEntered(java.awt.event.MouseEvent e) {
            jtext.setText("您可以进行图书的租金金额罚金金额，图书类别等的设置修改。");
          }
        });
    changePass.addActionListener(l);
    exit.addActionListener(l);
    menu.add(viewBook);
    menu.add(addBook);
    menu.add(checkOut);
    menu.add(renew);
    menu.add(checkIn);
    menu.add(record);
    menu.add(delete);
    menu.add(adduser);
    menu.add(managebook);
    // menu.add(changePass);
    menu.add(exit);
    // menu.setBackground(bg);
    menu.setLayout(new GridLayout(10, 1, 5, 5));
    JPanel temp = new JPanel();
    JPanel aa = new JPanel();
    JPanel bb = new JPanel();

    jtext.setFont(font);
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
    add(temp);
    setSize(400, 300);
    setLocationRelativeTo(null);
  }


  private class ButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      Object[][] obj = null;// 定义书架列表
      if (e.getSource() == viewBook) {
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
          System.out.println("连接关闭！");
        } catch (SQLException e1) {
          System.out.println("sql wrong!");
          e1.printStackTrace();
        }
        TableOfMgr table = new TableOfMgr();
        table.viewTable(obj);
      } else if (e.getSource() == addBook) {// 添加图书
        String name = JOptionPane.showInputDialog(null, "输入图书名", "提示", 1);// 有异常需要处理，空指针异常
        if (!name.equals("")) {// there has a bug
          try {
            new AddBook(name);
          } catch (ClassNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "数据库错误！", "提示", 1);
            // e1.printStackTrace();
          }
        } else {
          JOptionPane.showMessageDialog(null, "图书名字不能为空！", "提示", 1);
        }
      } else if (e.getSource() == checkOut) {// 借出

        try {
          String name = JOptionPane.showInputDialog(null, "输入图书名", "提示", 1);
          String[][] temp;
          temp = stack.checkOut(name);
          ChoiceDialogue a = new ChoiceDialogue();
          a.viewTable(temp);
        } catch (Exception e1) {
          JOptionPane.showMessageDialog(null, "系统出错了！", "提示", 1);
        }
      } else if (e.getSource() == checkIn) {// 归还图书
        String name = JOptionPane.showInputDialog(null, "输入图书名", "提示", 1);
        // Date time = new Date();
        // Book temp = null;
        Date tempdate = new Date();
        String getbookid = null;
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
                  + Login.username + "' and bookname='" + name + "' and checkin is null");
          while (str.next()) {
            getbookid = str.getString(1);// bookid
            getcheckout = str.getDate(2);// 借出日期
            getplan = str.getDate(3);
          }
          Statement statement3 = connection.createStatement();
          ResultSet level;
          level =
              statement3
                  .executeQuery("select booklevel from book where bookid='" + getbookid + "'");
          while (level.next()) {
            getbooklevel = level.getInt(1);
            System.out.println(getbooklevel);
          }
          long day = (tempdate.getTime() - getcheckout.getTime()) / (24 * 60 * 60 * 1000);
          long planday = (tempdate.getTime() - getplan.getTime());
          if (getbooklevel == 0) {// normal book
            if (planday >= 0) {
              jisuanzujin = (day + 1) * 1;// 此处的图书级别应该也是从数据库中读取的
              jisuanfajin = 0;
            } else {
              jisuanzujin = (day + 1) * 1;
              jisuanfajin = (-planday * 2);
            }
          } else {
            if (planday >= 0) {
              jisuanzujin = (day + 1) * 2;
              jisuanfajin = 0;
            } else {
              jisuanzujin = (day + 1) * 2;
              jisuanfajin = (-planday * 4);
            }
          }
          Statement statement = connection.createStatement();
          statement.execute("update record set checkin='" + timeformat.format(tempdate.getTime())
              + "',rent='" + jisuanzujin + "',fajin='" + jisuanfajin + "'where bookid='"
              + getbookid + "'");
          Statement statement2 = connection.createStatement();
          statement2.execute("update book set status=0 where bookid='" + getbookid + "'");
          connection.close();
        } catch (SQLException e1) {
          System.out.println("sql wrong!");
          e1.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "租金：" + jisuanzujin + "元 罚金：" + jisuanfajin + "元 总计："
            + (jisuanzujin + jisuanfajin) + "元", "提示", 1);

      } else if (e.getSource() == record) {
        setVisible(false);
        Record a;
        try {
          a = new Record();
          a.setVisible(true);
        } catch (ClassNotFoundException e1) {
          JOptionPane.showMessageDialog(null, "数据库错误！", "提示", 1);
          // e1.printStackTrace();

        }
      } else if (e.getSource() == delete) {//删除图书的
        DeleteBook test;
        try {
          test = new DeleteBook();
          test.viewTable();
        } catch (ClassNotFoundException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(null, "数据库错误！", "提示", 1);
        }
        
      } else if (e.getSource() == adduser) {
        new VipManage();
      } else if (e.getSource() == managebook) {// 设置图书罚金，租金，图书类别
        try {
          new ManageOthers();
        } catch (ClassNotFoundException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
       else if (e.getSource() == renew) {
         try {
         Renew a= new Renew();
         a.viewTable();
        } catch (ClassNotFoundException | SQLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
       }
      else if (e.getSource() == exit) {
        JOptionPane.showMessageDialog(null, "谢谢使用！", "提示", 1);
        setVisible(false);
      }
    }
  }
}
