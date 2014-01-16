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
   * 既可以一本一本的查看历史记录，也可以查看本人记录，针对admin和普通会员权限不同
   */
  private static final long serialVersionUID = 4878192299484824382L;
  private String[] recordTitle = {"序号", "图书编号", "图书名字", "借阅者id", "借出日期", "归还日期", "计划日期", "当次租金",
      "当次罚金"};
  JButton backButton = new JButton("返回主菜单");

  public ViewOfRecord() {
    setLayout(new GridLayout(2, 1));
    setSize(700, 400);
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("图书记录管理查询平台");
  }

  public void viewTable(Object[][] a) {
    JTable jtable = new JTable(a, recordTitle);
    add(new JScrollPane(jtable));

    backButton.addActionListener(new BackButtonListener());
    JPanel temp = new JPanel();
    temp.add(backButton);
    add(temp);
  }

  class BackButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == backButton) {
        dispose();
        new MainGui();
      }
    }
  }
}
