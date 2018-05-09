package App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

/**
 * Makes adding items to Menubar easier
 * @Author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @Version: 1.0
 * @Date: 09/05/18
 */
public class Menu extends JMenu {
    public Menu(String title) {
        super(title);
    }
    public Menu addItem(String title, Consumer<ActionEvent> callback) {
        JMenuItem item = new JMenuItem(title);
        ActionListener listener = callback::accept;
        item.addActionListener(listener);
        add(item);
        return this;
    }
}