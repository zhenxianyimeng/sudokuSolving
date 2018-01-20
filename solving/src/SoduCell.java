import javax.swing.*;
import java.awt.*;

/**
 * cell
 * @date 2018/1/13.
 */
public class SoduCell extends JTextField {
    public SoduCell(){
        this.setSize(50,50);
        Font font = new Font("",2,24);
        this.setFont(font);
        this.setBackground(Color.WHITE);
        this.setOpaque(true);
        this.setHorizontalAlignment(JTextField.CENTER);
    }
}
