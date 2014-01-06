package Gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewOfRecord extends JFrame {
  /**
   * 现在实现一条一条的浏览的功能
   * 还需要增加功能是一本书的浏览
   */
  private static final long serialVersionUID = 4878192299484824382L;
  private String[] record = {"序号","图书编号", "图书名字","借阅者id", "借出日期", "归还日期","计划日期", "当次租金","当次罚金"};
  // private JTable jtable=new JTable(null,name);
  JButton backButton = new JButton("返回主菜单");

  public ViewOfRecord() {
    setLayout(new GridLayout(2, 1));
    setSize(700, 400);
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  public void viewTable(Object[][] a) {
    JTable jtable = new JTable(a, record);
    add(new JScrollPane(jtable));
//    TableColumn temp1 = null;
//    TableColumn temp2 = null;
//    TableColumn temp4 = null;
//    temp1 = jtable.getColumnModel().getColumn(0);
//    temp2 = jtable.getColumnModel().getColumn(1);
//    temp4 = jtable.getColumnModel().getColumn(4);
//    temp1.setPreferredWidth(10);
//    temp2.setPreferredWidth(10);
//    temp4.setPreferredWidth(10);
    // temp1.setWidth(10);
    backButton.addActionListener(new BackButtonListener());
    JPanel temp = new JPanel();
    temp.add(backButton);
    add(temp);

  }

  class BackButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == backButton) {
        setVisible(false);
      }
    }
  }
}
