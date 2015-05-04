package myawt;

import java.awt.*;

/**
 * A class to help with the GridBagLayout manager.
 * The class is defined simply for its one static method.
 * It is not intended to be instantiated.
 * @author Rupert Parson
 * @version 1.0
 * @see java.awt.GridBagLayout
 */
public class GridBag {
  /**
   * A method to put a java.awt.Component in a java.awt.Container
   * being laid out by a GridBagLayout manager.
   *
   * @param container The parent Container.
   * @param component The Component to be placed.
   * @param grid_x x-coordinate in the grid.
   * @param grid_y y-coordinate in the grid.
   * @param grid_width How many grid spaces to take up horizontally.
   * @param grid_height How many grid spaces to take up vertically.
   * @param fill A GridBagLayout constant specifying in which 
   * direction the component can grow to fill the grid space allotted.
   * @param anchor A GridBagLayout constant specifying where in the grid 
   * space allotted the component should be placed.
   * @param weight_x How fast the component should grow in x-direction, if
   * the container is resized. 
   * @param weight_y How fast the component should grow in y-direction, if
   * the container is resized. 
   * @param top the space left blank at the top of the component.
   * @param left the space left blank at the left of the component.
   * @param bottom the space left blank at the bottom of the component.
   * @param right the space left blank at the right of the component.
   */
  public static void constrain(Container container, Component component, 
			int grid_x, int grid_y, int grid_width,  int
			grid_height, int fill, int anchor, double
			weight_x, double weight_y, int top, int left,
			int bottom, int right) {
    GridBagConstraints c = new GridBagConstraints();
    c.gridx=grid_x;
    c.gridy=grid_y;
    c.gridwidth=grid_width;
    c.gridheight=grid_height;
    c.fill=fill;
    c.anchor=anchor;
    c.weightx=weight_x;
    c.weighty=weight_y;

    if (top+bottom+left+right>0) 
      c.insets = new Insets(top,left,bottom,right);

    ((GridBagLayout)container.getLayout()).setConstraints(component,c);

    container.add(component);
  }
}
