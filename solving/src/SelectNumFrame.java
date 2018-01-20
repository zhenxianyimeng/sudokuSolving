import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *  Number Selector
 * @date 2018/1/13.
 */
public class SelectNumFrame extends JDialog implements MouseListener {
    private SoduCell cell;

    public void setCell(SoduCell cell) {
        this.cell = cell;
    }

    public SelectNumFrame(){
        this.setUndecorated(true);
        this.setSize(150, 150);
        this.setBackground(new Color(255,204,153, 123));
        this.setLayout(null);
        addNum();
    }

    /**
     * add number 1~9
     */
    private void addNum() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton();
                btn.setSize(50, 50);
                btn.setLocation(i*50,j*50);
                btn.setText(""+(j*3+i+1));
                btn.addMouseListener(this);
                this.add(btn);
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
        if ((modes & InputEvent.BUTTON1_MASK) != 0) {
            JButton btn = (JButton) e.getSource();
            cell.setText(btn.getText());
        }
        this.dispose();
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
