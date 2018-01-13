import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * centre comptent
 * @date 2018/1/13.
 */
public class ShuduCanvers extends JPanel implements MouseListener {
    ShuduCell[][] cells;

    private SelectNumFrame selectNum;

    public ShuduCanvers() {
        ShuduMainFrame.usedTime = 0;
        // 加载数独区
        this.setLayout(null);
        cells = new ShuduCell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new ShuduCell();
                // positon
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

    @Override
    public void mousePressed(MouseEvent e) {
        int modes = e.getModifiers();
        if ((modes & InputEvent.BUTTON3_MASK) != 0) {// right click
            // clear cell
            ((ShuduCell) e.getSource()).setText("");
        } else if ((modes & InputEvent.BUTTON1_MASK) != 0) {// left click
            // select number
            if (selectNum != null) {
                selectNum.dispose();
            }
            selectNum = new SelectNumFrame();
            selectNum.setModal(true);
            selectNum.setLocation(e.getLocationOnScreen().x,
                    e.getLocationOnScreen().y);
            selectNum.setCell((ShuduCell) e.getSource());
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
