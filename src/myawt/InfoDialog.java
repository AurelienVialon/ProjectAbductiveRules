package myawt;


import java.awt.*;
import java.awt.event.*;

/** 
 * A program information dialog.
 * The InfoDialog displays a couple of short messages, and 
 * a public okay button - listened to by the parent as well
 * as the dialog.
 * @author Rupert Parson
 * @version 1.0
 */
public class InfoDialog extends Dialog 
implements ActionListener {
  /**
   * A button that holds the text "okay".
   */
  public Button button;

  /**
   * Construct the InfoDialog box.
   * @param parent The parent Container.
   * @param title The title displayed on the InfoDialog.
     * @param message1
     * @param message2
   */
  public InfoDialog(Frame parent, String title, 
		    String message1, String message2) {
    super(parent, title, false);
    
    this.setLocation(new Point(parent.getLocation().x+150,
			       parent.getLocation().y+200));
    this.setLayout(new GridBagLayout());
    
    Label m1 = new Label(message1);
    Label m2 = new Label(message2);

    button = new Button("Okay");
    button.addActionListener(this);
    button.addActionListener((ActionListener) parent);

    GridBag.constrain(this, m1, 0, 0, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.CENTER,
		      1.0, 0.0, 15, 15, 5, 15);
    GridBag.constrain(this, m2, 0, 1, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.CENTER,
		      1.0, 0.0, 5, 15, 15, 15);
    GridBag.constrain(this, button, 0, 2, 1, 1,
		      GridBagConstraints.NONE, 
		      GridBagConstraints.CENTER,
		      1.0, 0.0, 20, 20, 20, 20);
    this.pack();
  }

  public final void actionPerformed(ActionEvent event) {
    if (event.getSource() == button) {
      this.setVisible(false);
      this.dispose();
    }
  }
}
