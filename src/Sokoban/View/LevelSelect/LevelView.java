package Sokoban.View.LevelSelect;

import Sokoban.Model.Sokoban;
import Sokoban.Resources.Colors;
import Sokoban.View.BoardView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Views a version of the game for the selection of Levels
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0
 * @Date: 07/06/18
 */
public class LevelView extends JPanel {
    private Border defaultBorder = new EmptyBorder(10, 10, 10, 10);
    private Border hoverBorder = new CompoundBorder(
            new LineBorder(Colors.CANARINHO.getColor(), 5),
            new EmptyBorder(5, 5, 5, 5)
    );
    private Border clickedBorder = new CompoundBorder(
            new LineBorder(Colors.A_SWING_TRUMPET_V2.getColor(), 5),
            new EmptyBorder(5, 5, 5, 5)
    );
    private Sokoban sokoban;
    private Supplier<Consumer<Sokoban>> actionLevelSelected;

    /**
     * Constructor, creates a view for the Sokoban Level
     *
     * @param title   Level of this game version
     * @param sokoban Model of this Level
     */
    public LevelView(String title, Sokoban sokoban) {
        BoardView boardView = new BoardView(sokoban);
        JLabel lblTitle = new JLabel(title);
        Font oldFont = lblTitle.getFont();
        this.sokoban = sokoban;
        boardView.disableMouseListener();
        lblTitle.setFont(new Font(oldFont.getName(), Font.PLAIN, 20));

        setBackground(Colors.PIED_PIPER_BUTTERLAND.getColor());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());
        setFocusable(true);
        setMaximumSize(new Dimension(200, 350));
        registerMouseEvents();

        add(lblTitle, BorderLayout.WEST);
        add(boardView, BorderLayout.SOUTH);
    }

    /**
     * Adds MouseListener to the Selectable Level, clicking it selects it
     */
    private void registerMouseEvents() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(defaultBorder);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(hoverBorder);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setBorder(clickedBorder);
                if (actionLevelSelected.get() != null) {
                    actionLevelSelected.get().accept(sokoban);
                }
            }
        });
    }

    /**
     * Sets the selected Level
     *
     * @param actionLevelSelected The selected Level
     */
    public void setActionLevelSelected(Supplier<Consumer<Sokoban>> actionLevelSelected) {
        this.actionLevelSelected = actionLevelSelected;
    }
}
