/**
 * IPR | Graphics - Exercise : Fill
 * 
 * This is the exercise file for the IPR Graphics Exercise.
 * You have to solve tasks, summing to 8 points.
 * 
 * 
 * @Author [Todo: Student name]
 * @ID [Todo: Student id]
 * 
 * 
 * 
 * @Contact: If you have any questions, please contact the tutors or the
 *           instructor (David Schedl, email: david.schedl@fh-hagenberg.at)
 * 
 * @Notes:
 * 
 *         <ul>
 *         <li>Hand in the solution via Github Classroom on time.
 *         <li>You can delete code that is not related to the tasks.
 *         <li>Ensure that you added your name and matriculation number to the
 *         top of the files and in any additional source files.
 *         </ul>
 */
public class Fill {

	// We define some color values for easier usage in our code.
	final static int WHITE = 0xffffff; // white
	final static int BLACK = 0x000000; // black
	final static int RED = 0xff0000; // red
	final static int GREEN = 0x00ff00; // green
	final static int BLUE = 0x0000ff; // blue
	final static int YELLOW = 0xffff00; // yellow
	final static int GRAY = 0x808080; // gray

	// Todo: Task 01 - Recursive Fill

	public static void main(String[] args) {

		/*
		 * _Task 01_: Color the Smiley
		 * 
		 * Implement the 4-neighbors flood-fill algorithm to color the smiley face from
		 * the images exercise. <br>
		 * Color the inside of the smiley yellow and the outside in a different color
		 * (the outside color is up to you).
		 * 
		 * Note:* In comparison to the images lecture, I added one more column and row
		 * on the borders of the image.
		 */
		{
			// # define the pixels of an image with a smiley face
			int F = BLACK; // # background
			int T = WHITE; // # border of face
			int E = BLUE; // # eye
			int M = RED; // # mouth
			int[][] pixels = {
					{ F, F, F, F, F, F, F, F, F, F },
					{ F, F, F, T, T, T, T, F, F, F },
					{ F, F, T, F, F, F, F, T, F, F },
					{ F, T, F, E, F, F, E, F, T, F },
					{ F, T, F, F, F, F, F, F, T, F },
					{ F, T, F, M, F, F, M, F, T, F },
					{ F, T, F, F, M, M, F, F, T, F },
					{ F, F, T, F, F, F, F, T, F, F },
					{ F, F, F, T, T, T, T, F, F, F },
					{ F, F, F, F, F, F, F, F, F, F },
			};

			// Todo: Task 1 - recursive 4 neighbors flood fill
			// you can use the IPR.getPixel() and IPR.setPixel() functions
			// to implement a recursive flood fill algorithm

			IPR.imageShow("Smiley", pixels);
		}

		/**
		 * 
		 * _Task 02_: Rainbow-color our wheel
		 * 
		 * The code first draws a wheel (circle plus connections to its center) with the
		 * DDA algorithm. The implementation is similar to the one in the line-drawing
		 * exercise. Segments on the cirlce are drawn in color.
		 * 
		 * Reuse the image with the (colored) wheel and floodfill the segments of the
		 * wheel.
		 * The colors for the segments are available in the `colors` array.
		 * Make sure that your algorithm is correct if the number of segments
		 * (`numSegments`) changes. Don't make numSegments larger than 40 or smaller
		 * than 3!
		 * 
		 */

		{
			int[][] img = IPR.makeImage(64, 64, WHITE);

			// define circle parameters
			int cx = 30;
			int cy = 30;
			int r = 29;
			int numSegments = 10;
			var points = IPR.createCirclePoints(r, numSegments, cx, cy);
			var colors = IPR.getRainbowColors(numSegments);

			for (int i = 0; i < points.length; i++) {
				int x1 = Math.round(points[i][0]);
				int y1 = Math.round(points[i][1]);
				int x2 = Math.round(points[(i + 1) % points.length][0]);
				int y2 = Math.round(points[(i + 1) % points.length][1]);

				// draw with DDA (wheel spokes and circle)
				IPR.drawLine(img, x1, y1, cx, cy, GRAY);
				IPR.drawLine(img, x1, y1, x2, y2, colors[i]);
			}

			// Todo: Task 2 - color the wheel
			// you can use the IPR.getPixel() and IPR.setPixel() functions
			// to implement a recursive flood fill algorithm (see Task 1).
			// Then color each segment of the wheel with a different color from the 'colors'
			// array.

			IPR.imageShow("Wheel", img);

		}

	}

}
