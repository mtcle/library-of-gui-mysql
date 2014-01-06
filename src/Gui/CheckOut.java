package Gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Book;

public class CheckOut {

  public CheckOut(ArrayList<Book> list, String str) {// 通过id借出图书
    int index = 0;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getId().equals(str))// 找到编号
      {
        index = i;
      }
    }
    list.get(index).setRentCounts();// 设置编号对应图书的借出次数
    list.get(index).setStatus(1);// 改写状态
    JOptionPane.showMessageDialog(null, "图书信息：\n" + "名字： " + list.get(index).bookName + "\n"
        + "编号： " + list.get(index).getId(), "  借出成功", 1);
    Book.Record newRecord = list.get(index).newRecord();// 增加一条借阅记录？？？？这里还需要完善
    list.get(index).getRecord().add(newRecord);
    System.out.println("jilu" + list.get(index).getRecord().size());
    for (int j = 0; j < list.get(index).getRecord().size(); j++) {
//      System.out.println("jilu" + list.get(index).getRecord().get(j).toString());// 写入记录中
    }
  }
}
