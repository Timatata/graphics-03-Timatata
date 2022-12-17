import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.util.HashMap; // import the HashMap class
import java.util.LinkedList; // import the LinkedList class
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.time.*;
import java.io.File; // for reading images
import javax.imageio.ImageIO;
import java.io.IOException;

public class IPR {

    /**
     * Displays the image in a window and keeps it open till the user closes it.
     * 
     * @param title  the title of the image
     * @param pixels the image to be displayed as a 2D array of pixels
     */
    public static void imageShow(String title, int[][] pixels) {

        var h = pixels.length;
        var w = pixels[0].length;

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < w; ++x) {
            for (int y = 0; y < h; ++y) {
                img.setRGB(x, y, pixels[y][x]); // flip u,v here!
            }
        }

        JFrame frame = null;
        JLabel label = null;
        if (!openWindows.containsKey(title)) {
            openWindow(title, 512, 512 * h / w); // default size is 512x512
        }

        frame = openWindows.get(title);
        label = (JLabel) frame.getContentPane().getComponent(0);
        int w_ = (int) label.getSize().getWidth();
        int h_ = (int) label.getSize().getHeight();
        var icon = new ImageIcon(img.getScaledInstance(w_, h_, java.awt.Image.SCALE_FAST));
        label.setIcon(icon);

        frame.repaint();
    }

    // ----- definitions for Lines exercises -----

    /**
     * Create a new image of the given width and height. The image is filled with a
     * uniform color.
     * 
     * @param w   width of the image
     * @param h   height of the image
     * @param rgb red, green, blue components of the uniform color
     * @return a new image with the given width and height and filled color
     */
    public static int[][] makeImage(int w, int h, int rgb) {
        int[][] pixels = new int[h][w];
        for (int x = 0; x < w; ++x) {
            for (int y = 0; y < h; ++y) {
                setPixel(pixels, x, y, rgb);
            }
        }
        return pixels;
    }

    /**
     * Create a new image of the given width and height. The image is filled with a
     * uniform color.
     * 
     * @param w width of the image
     * @param h height of the image
     * @return a new image with the given width and height and filled color
     */
    public static int[][] makeImage(int w, int h) {
        return makeImage(w, h, 0);
    }

    /**
     * Sets the color of a pixel at coordinate u,v in an image
     * 
     * @param image the image
     * @param u     pixel u/x coordinate
     * @param v     pixel v/y coordinate
     * @param rgb   the color component
     * 
     */
    public static void setPixel(int[][] image, int u, int v, int rgb) {
        // boundary check
        if (u < 0 || u >= image[0].length || v < 0 || v >= image.length) {
            return; // do nothing
        }
        image[v][u] = rgb & 0xFFFFFF;
    }

    // definitions for Fill exercises ------------

    /**
     * Gets the color of a pixel at coordinate u,v in an image
     * 
     * @param image the image as 2D array of ints
     * @param u     pixel u/x coordinate
     * @param v     pixel v/y coordinate
     * 
     * @return the color packed as 24 bit RGB
     */
    public static int getPixel(int[][] image, int u, int v) {
        // boundary check
        if (u < 0 || u >= image[0].length || v < 0 || v >= image.length) {
            return -1; // do nothing
        }
        return image[v][u] & 0xFFFFFF;
    }

    /**
     * Create an array of points for a circle
     * 
     * @param r  the radius of the circle
     * @param n  the number of points/segments
     * @param cx the x coordinate of the center of the circle
     * @param cy the y coordinate of the center of the circle
     * @return an array of points of the form float[n][2]
     */
    public static float[][] createCirclePoints(float r, int n, int cx, int cy) {
        float[][] points = new float[n][2];

        float alpha = 2.0f * (float) Math.PI / n;

        for (int i = 0; i < n; i++) {
            points[i][0] = cx + r * (float) Math.cos(alpha * i);
            points[i][1] = cy + r * (float) Math.sin(alpha * i);
            // System.out.print("[" + points[i][0] + ", " + points[i][1] + "], ");
        }

        return points;
    }

    /**
     * Returns an array of colors for the rainbow.
     * 
     * @param n number of colors
     * @return an int array with RGB values packed as integer for n colors
     */
    public static int[] getRainbowColors(int n) {
        int[] colors = new int[n];
        for (int i = 0; i < n; i++) {
            float hue = (float) i / n;
            colors[i] = Color.HSBtoRGB(hue, 1, 1);
        }
        return colors;
    }

    /**
     * Draw a line with the DDA algorithm into an image
     * 
     * @param img   the image to draw into
     * @param x1    the x coordinate of the start point
     * @param y1    the y coordinate of the start point
     * @param x2    the x coordinate of the end point
     * @param y2    the y coordinate of the end point
     * @param color the color of the line
     */
    public static void drawLine(int[][] img, int x1, int y1, int x2, int y2, int color) {
        int steps = Math.max(Math.abs(y2 - y1), Math.abs(x2 - x1));

        float incX = (x2 - x1) / (float) steps;
        float incY = (y2 - y1) / (float) steps;
        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            IPR.setPixel(img, Math.round(x), Math.round(y), color);
            x += incX;
            y += incY;
        }

    }

    // ----- definitions for Game exercise

    /**
     * Sets the color of all pixels in an image
     * 
     * @param image the image
     * @param rgb   the color component
     * 
     */
    public static void clearImage(int[][] image, int rgb) {
        for (int x = 0; x < image[0].length; ++x) {
            for (int y = 0; y < image.length; ++y) {
                setPixel(image, x, y, rgb);
            }
        }
    }

    /**
     * Returns any keyboard presses that happened since the last call to this
     * function.
     * 
     * Function returns null if no key was pressed.
     * Special keys (left, right, up, down, escape) are mapped to "LEFT", "RIGHT",
     * "UP", "DOWN", "ESCAPE".
     * 
     * @return keyboard key as string or null if no key was pressed
     */
    public static String getKey() {
        return keys.poll();
    }

    /**
     * Opens a window with the given title and size.
     * 
     * @param title the title of the window
     * @param w     width of the window
     * @param h     height of the window
     */
    public static void openWindow(String title, int w, int h) {

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        var emptyIcon = new ImageIcon(img.getScaledInstance(w, h, java.awt.Image.SCALE_FAST));

        var frame = new JFrame();
        openWindows.put(title, frame);
        frame.setMinimumSize(new Dimension(w, h));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(title);
        var label = new JLabel();
        label.setIcon(emptyIcon);
        label.setMinimumSize(new Dimension(w, h));
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.addKeyListener(new IPR.SimpleKeyAdapter());

    }

    /**
     * close the window with the given title
     * 
     * @param title window title
     */
    public static void closeWindow(String title) {
        if (openWindows.containsKey(title)) {
            openWindows.get(title).dispose();
            openWindows.remove(title);
        }
    }

    private static HashMap<String, JFrame> openWindows = new HashMap<>(); // store open windows in a map by the title!
    private static LinkedList<String> keys = new LinkedList<>(); // stores the pressed keys as String.

    // ------ Key Listener for KeyBoard Inputs -----
    private static class SimpleKeyAdapter extends KeyAdapter {

        public SimpleKeyAdapter() {
        }

        public void keyPressed(KeyEvent e) {
            char keyChar = e.getKeyChar();
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_RIGHT:
                    keys.offer("RIGHT");
                    break;
                case KeyEvent.VK_LEFT:
                    keys.offer("LEFT");
                    break;
                case KeyEvent.VK_UP:
                    keys.offer("UP");
                    break;
                case KeyEvent.VK_DOWN:
                    keys.offer("DOWN");
                    break;
                case KeyEvent.VK_ESCAPE:
                    keys.offer("ESC");
                    // System.exit(0);
                    break;
                default:
                    if (keyChar > 0 && keyChar <= 'z') {
                        keys.offer(new String("" + keyChar));
                    }
            }

        }
    }

    // ~~~~~~----- Time for animation -----~~~~~~

    private static long startTime = System.currentTimeMillis();

    /**
     * Function returns the time since startup in seconds.
     * 
     * @return time since startup in seconds
     */
    public static float getTime() {
        return (float) (System.currentTimeMillis() - startTime) / 1000.0f;
    }

    /**
     * Returns the hours of the current time.
     * 
     * @return hours of the current time
     */
    public static int getCurrentTimeHours() {
        var date = LocalDateTime.now();
        int hours = date.getHour();
        return hours;
    }

    /**
     * Returns the minutes of the current time.
     * 
     * @return minutes of the current time
     */
    public static int getCurrentTimeMinutes() {
        var date = LocalDateTime.now();
        return date.getMinute();
    }

    /**
     * Returns the seconds of the current time.
     * 
     * @return seconds of the current time
     */
    public static int getCurrentTimeSeconds() {
        var date = LocalDateTime.now();
        int seconds = date.getSecond();
        return seconds;
    }

    // Additional functions for reading/writing images ------------

    /**
     * Reads an image from a file.
     * 
     * @param fileName the name of the file
     * @return the image
     */
    public static int[][] imageRead(String fileName) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] image = makeImage(img.getWidth(), img.getHeight());
        for (int x = 0; x < img.getWidth(); ++x) {
            for (int y = 0; y < img.getHeight(); ++y) {
                setPixel(image, x, y, img.getRGB(x, y));
            }
        }
        return image;
    }

    /**
     * Writes an image to a file.
     * 
     * @param fileName the name of the file. Needs to be PNG!
     * @param image    the image
     */
    public static void imageWrite(String fileName, int[][] image) {
        BufferedImage img = new BufferedImage(image[0].length, image.length, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < image[0].length; ++x) {
            for (int y = 0; y < image.length; ++y) {
                img.setRGB(x, y, getPixel(image, x, y));
            }
        }
        try {
            ImageIO.write(img, "png", new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}