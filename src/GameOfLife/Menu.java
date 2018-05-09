package GameOfLife;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

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