import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * centre comptent
 * @date 2018/1/13.
 */
public class SudoCanvers extends JPanel implements MouseListener {
    SoduCell[][] cells;

    private SelectNumFrame selectNum;

    public SudoCanvers() {
        SudoMainFrame.usedTime = 0;
        // load cells
        this.setLayout(null);
        cells = new SoduCell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new SoduCell();
                // cells positon
                cells[i][j].setLocation(20 + i * 50 + (i / 3) * 5, 20 + j * 50
                        + (j / 3) * 5);
                cells[i][j].setEnabled(false);
                cells[i][j].setForeground(Color.gray);
                cells[i][j].addMouseListener(this);

                this.add(cells[i][j]);
            }
        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * cell selector
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int modes = e.getModifiers();
        if ((modes & InputEvent.BUTTON3_MASK) != 0) {// right click
            // clear cell
            ((SoduCell) e.getSource()).setText("");
        } else if ((modes & InputEvent.BUTTON1_MASK) != 0) {// left click
            // select number
            if (selectNum != null) {
                selectNum.dispose();
            }
            selectNum = new SelectNumFrame();
            selectNum.setModal(true);
            selectNum.setLocation(e.getLocationOnScreen().x,
                    e.getLocationOnScreen().y);
            selectNum.setCell((SoduCell) e.getSource());
            selectNum.setVisible(true);
        }
    }

    /**
     * clear all cells listener
     */
    private void clearAllListener() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].removeMouseListener(this);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
