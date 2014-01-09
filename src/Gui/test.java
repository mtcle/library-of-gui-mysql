package Gui;

public class test {

  /**
   * @param args
   * @throws ClassNotFoundException 
   */
  public static void main(String[] args) throws ClassNotFoundException {
//    new BookStyleManage();
    String a="编号：HG-10-121200 图书名字：test";
    System.out.println(a.subSequence(3, 14));
    new CheckIn();
  }

}
