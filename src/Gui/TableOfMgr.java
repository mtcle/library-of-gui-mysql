package Gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
/**
 * 主要是显示用户搜索到的可能要借阅的图书的功能
 * 把同名的或相关的图书列出来
 * 和用户进行交互,供用户选择某本图书
 * @author mtcle
 * 
 */
public class TableOfMgr extends JFrame {
  private static final long serialVersionUID = -5150007535581084817L;
  private String[] tableTitle = {"图书编号", "名字", "状态", "图书级别", "借出次数"};
  JButton backButton = new JButton("返回主菜单");
  public TableOfMgr() {
    setLayout(new GridLayout(2, 1));
    setSize(800, 600);
    setResizable(false);
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  /**
   * @param  二维对象类型的数组
   */
  public void viewTable(Object[][] a) {
    JTable jtable = new JTable(a, tableTitle);
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
      }
    }
  }
}
