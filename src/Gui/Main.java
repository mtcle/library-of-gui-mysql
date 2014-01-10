package Gui;

import java.sql.SQLException;
/**
 * main函数
 * */
public class Main {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    Login frame = new Login();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
