package base;


import java.awt.EventQueue;

import javax.swing.JOptionPane;

public class Test {
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        PropertiesFrame frame = new PropertiesFrame();
        frame.setVisible(true);
      }
    });
  }
}


class PropertiesFrame extends BaseJFrame {

  /**
   * 
   */
  private static final long serialVersionUID = -9056314713602850771L;

  public PropertiesFrame() {
    super();
    int left = getIntPrefs("left");
    int top = getIntPrefs("top");
    int width = getIntPrefs("width");
    int height = getIntPrefs("height");
    setBounds(left, top, width, height);

    // if no title given, ask user
    String title = getPrefs("title");
    if (title.equals("")) title = JOptionPane.showInputDialog("Please supply a frame title:");
    if (title == null) title = "";
    setTitle(title);

    addWindowListener(this);
  }
}
