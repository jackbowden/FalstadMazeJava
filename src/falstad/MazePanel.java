package falstad;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.RenderingHints;

/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 * 
 * @author pk
 *
 */
public class MazePanel extends Panel  {
	/* Eclipse told me to add this lol */
	private static final long serialVersionUID = 1L;
	
	/* Panel operates a double buffer see
	 * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
	 * for details
	 */
	// bufferImage can only be initialized if the container is displayable,
	// uses a delayed initialization and relies on client class to call initBufferImage()
	// before first use
	private Image bufferImage;  
	private Graphics2D graphics; // obtained from bufferImage, 
	// graphics is stored to allow clients to draw on same graphics object repeatedly
	// has benefits if color settings should be remembered for subsequent drawing operations
	
	/**
	 * Constructor. Object is not focusable.
	 */
	public MazePanel() {
		setFocusable(false);
		bufferImage = null; // bufferImage initialized separately and later
		graphics = null;
		
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	/**
	 * Method to draw the buffer image on a graphics object that is
	 * obtained from the superclass. The method is used in the MazeController.
	 * Warning: do not override getGraphics() or drawing might fail. 
	 */
	public void update() {
		paint(getGraphics());
	}
	

	/**
	 * Draws the buffer image to the given graphics object.
	 * This method is called when this panel should redraw itself.
	 */
	@Override
	public void paint(Graphics g) {
		if (null == g) {
			System.out.println("MazePanel.paint: no graphics object, skipping drawImage operation");
		}
		else {
			g.drawImage(bufferImage,0,0,null);	
		}
	}

	public void initBufferImage() {
		bufferImage = createImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		if (null == bufferImage)
		{
			System.out.println("Error: creation of buffered image failed, presumedly container not displayable");
		}
	}
	/**
	 * Obtains a graphics object that can be used for drawing.
	 * The object internally stores the graphics object and will return the
	 * same graphics object over multiple method calls. 
	 * To make the drawing visible on screen, one needs to trigger 
	 * a call of the paint method, which happens 
	 * when calling the update method. 
	 * @return graphics object to draw on, null if impossible to obtain image
	 */
	public Graphics getBufferGraphics() {
		if (null == graphics) {
			// instantiate and store a graphics object for later use
			if (null == bufferImage)
				initBufferImage();
			if (null == bufferImage)
				return null;
			graphics = (Graphics2D) bufferImage.getGraphics();
			if (null == graphics) {
				System.out.println("Error: creation of graphics for buffered image failed, presumedly container not displayable");
			}
			// success case
			
			//System.out.println("MazePanel: Using Rendering Hint");
			// For drawing in FirstPersonDrawer, setting rendering hint
			// became necessary when lines of polygons 
			// that were not horizontal or vertical looked ragged
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		}
		
		return graphics;
	}
	
	public void drawLine(int x1, int y1, int x2, int y2) {
		graphics.drawLine(x1, y1, x2, y2);
	}
	
	public void drawString(String string, int x, int y) {
		graphics.drawString(string, x, y);
	}
	
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		graphics.drawPolygon(xPoints, yPoints, nPoints);
	}
	
	public void fillOval(int x, int y, int width, int height) {
		graphics.fillOval(x, y, width, height);
	}
	
	public void fillRect(int x, int y, int width, int height) {
		graphics.fillRect(x, y, width, height);
	}
	
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		graphics.fillPolygon(xPoints, yPoints, nPoints);
	}
	
	public FontMetrics getFontMetrics() {
		return graphics.getFontMetrics();
	}
	
	public enum MazePanelColors {
		BLACK, BLUE, DARKGRAY, GRAY, ORANGE, RED, WHITE, YELLOW
	}
	
	public void setColor(MazePanelColors color) {
		switch (color) {
		case BLACK:
			graphics.setColor(Color.black);
			break;
		case BLUE:
			graphics.setColor(Color.blue);
			break;
		case DARKGRAY:
			graphics.setColor(Color.darkGray);
			break;
		case GRAY:
			graphics.setColor(Color.gray);
			break;
		case ORANGE:
			graphics.setColor(Color.orange);
			break;
		case RED:
			graphics.setColor(Color.red);
			break;
		case WHITE:
			graphics.setColor(Color.white);
			break;
		case YELLOW:
			graphics.setColor(Color.yellow);
			break;
		}
	}
	
	public void setColor(int r, int g, int b) {
		graphics.setColor(new Color(r, g, b));
	}
	
	public void setColor(int colorInt) {
		graphics.setColor(new Color(colorInt));
	}
	
	public static int getRGB(int r, int g, int b) {
		return new Color(r, g, b).getRGB();
	}
	
	public static int[] getRGBArray(int colorInt) {
		Color color = new Color(colorInt);
		return new int[] {color.getRed(), color.getGreen(), color.getBlue()};
	}
	
	public void setFont(Font font) {
		graphics.setFont(font);
	}
}
