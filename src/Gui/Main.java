package Gui;

import java.sql.SQLException;

/**
 * main函数,程序入口
 * @version 1.0
 * @author mtcle
 */
public class Main {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    Login frame = new Login();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
