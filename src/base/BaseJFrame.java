package base;


import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.swing.JFrame;

public abstract class BaseJFrame extends JFrame implements WindowListener {
  /**
   * BaseJFrame是由JFrame派生出来的类，实现了windowsListener接口
   * 是个抽象类，主要提供记忆frame用户拖动位置
   */
  private static final long serialVersionUID = 4672571102889540948L;
  private File propertiesFile;
  private Properties settings;

  private static final int DEFAULT_WIDTH = 300;
  private static final int DEFAULT_HEIGHT = 200;

  public BaseJFrame() {
    String userDir = System.getProperty("user.home");
    File propertiesDir = new File(userDir, ".corejava");
    if (!propertiesDir.exists()) propertiesDir.mkdir();
    propertiesFile = new File(propertiesDir, this.getClass().getCanonicalName() + ".properties");
    settings = new Properties();
    settings.put("left", "0");
    settings.put("top", "0");
    settings.put("width", "" + DEFAULT_WIDTH);
    settings.put("height", "" + DEFAULT_HEIGHT);
    settings.put("title", "");
    loadProperties();
  }

  public URL getImageURL(String fileName) {
    return BaseJFrame.class.getResource(fileName);
  }

  public int getIntPrefs(String key) {
    return Integer.parseInt(getPrefs(key));
  }

  public String getPrefs(String key) {
    return settings.getProperty(key);
  }

  public void putPrefs(String key, String value) {
    settings.put(key, value);
  }

  @Override
  public void windowClosing(WindowEvent e) {
    putPrefs("left", "" + getX());
    putPrefs("top", "" + getY());
    putPrefs("width", "" + getWidth());
    putPrefs("height", "" + getHeight());
    putPrefs("title", getTitle());

    restoreProperties();
    System.exit(0);
  }

  public void restoreProperties() {
    try {
      FileOutputStream out = new FileOutputStream(propertiesFile);
      settings.store(out, "Program Properties");
    } catch (IOException ex) {
      // ignore
    }
  }

  public void loadProperties() {
    try {
      FileInputStream in = new FileInputStream(propertiesFile);
      settings.load(in);
    } catch (IOException ex) {
      // ignore
    }
  }

  @Override
  public void windowOpened(WindowEvent e) {}

  @Override
  public void windowClosed(WindowEvent e) {}

  @Override
  public void windowIconified(WindowEvent e) {}

  @Override
  public void windowDeiconified(WindowEvent e) {}

  @Override
  public void windowActivated(WindowEvent e) {}

  @Override
  public void windowDeactivated(WindowEvent e) {}
}