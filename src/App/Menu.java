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

    /**
     * Constructor
     * @param title title of Menu
     */
    public Menu(String title) {
        super(title);
    }

    /**
     * Alt Constructor to add Tool Tips
     * @param title title of Menu
     * @param toolTip text of tooltip
     */
    public Menu(String title,String toolTip) {
        super(title);
        this.setToolTipText(toolTip);
    }

    /**
     * Adds an item to the Menu
     * @param title Name of MenuItem
     * @param callback  action of the
     * @return returns the menu to enable chaining
     */
    public Menu addItem(String title, Consumer<ActionEvent> callback) {
        JMenuItem item = new JMenuItem(title);
        ActionListener listener = callback::accept;
        item.addActionListener(listener);
        add(item);
        return this;
    }

    /**
     * A sub Menu
     * @param menu name of the menu
     * @return the main menu for chaining
     */
    public Menu addItem(Menu menu) {
        add(menu);
        return this;
    }

    /**
     * Alternate additem methode
     * @param toolTip text of the tool Tip
     * @return
     */
    public Menu addItem(String title,String toolTip, Consumer<ActionEvent> callback ) {
        JMenuItem item = new JMenuItem(title);
        item.setToolTipText(toolTip);
        ActionListener listener = callback::accept;
        item.addActionListener(listener);
        add(item);
        return this;
    }
}