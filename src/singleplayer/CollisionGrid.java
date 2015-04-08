package singleplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import tools.Vector2D;

/**
 * this class handles all collisions and stores each object in the cell that it occupies it
 * 
 * @author Rodney
 * 
 */
public class CollisionGrid {

	private int gridWidth;
	private int gridHeight;
	private int cellDim;
	LinkedList<Object>[][] cell;

	/**
	 * CONSTRUCTOR
	 * 
	 * @param model
	 * @param gridWidth
	 * @param gridHeight
	 * @param cellDim
	 */
	@SuppressWarnings("unchecked")
	public CollisionGrid(SinglePlayerModel model, int gridWidth, int gridHeight, int cellDim) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		this.cellDim = cellDim;
		cell = (LinkedList<Object>[][]) new LinkedList<?>[16][16];
		for (int x = 0; x < 16; x++)
			for (int y = 0; y < 16; y++)
				cell[x][y] = new LinkedList<Object>();
	}

	/**
	 * This Method will check if a cell has more than 1 object in it for possible collisions
	 * 
	 * @param currentCell
	 */
	public void checkCell(Player p, int[][] currentCell) {
		if (currentCell == null) { // I don't think this will ever happen
			System.out.println("no collision"); // DEBUG
			return;
		}

		for (int x = 0; x < currentCell.length; x++) { // for each cell
			int cx = currentCell[x][0];
			int cy = currentCell[x][1];
			if (cell[cx][cy].size() >= 2) { // for each object in cell
				/* handle collisions in here */
				for (Object o : cell[cx][cy]) {
					if (o instanceof Ball)
						p.checkCollision((Ball) o);
				}
			}
		}
	}

	/**
	 * This Method will check if a cell has more than 1 object in it for possible collisions
	 * 
	 * @param ai
	 * @param currentCell
	 */
	public void checkCell(AI ai, int[][] currentCell) {
		if (currentCell == null) { // I don't think this will ever happen
			System.out.println("no collision"); // DEBUG
			return;
		}

		for (int x = 0; x < currentCell.length; x++) { // for each cell
			int cx = currentCell[x][0];
			int cy = currentCell[x][1];
			if (cell[cx][cy].size() >= 2) { // for each object in cell
				/* handle collisions in here */
				for (Object o : cell[cx][cy]) {
					if (o instanceof Ball)
						ai.checkCollision((Ball) o);
				}
			}
		}
	}

	/**
	 * This function allows to view all objects in a sub-grid passed in
	 * 
	 * @param fov
	 */
	public Ball look(int[][] fov, Vector2D pos, int anchor, int difficulty) {
		boolean binf = false; // is there a ball in fov
		float d = -1;
		Ball b = null;
		float temp;
		for (int x = 0; x < fov.length; x++) {
			int cx = fov[x][0];
			int cy = fov[x][1];

			if (cell[cx][cy].size() > 0) {
				for (Object o : cell[cx][cy]) {
					if (o instanceof Ball) {
						if (anchor == 0 && ((Ball) o).getVelocityX() > 0 || anchor == 1 && ((Ball) o).getVelocityY() < 0 || anchor == 2 && ((Ball) o).getVelocityX() < 0)
							; // continue
						else
							break; // this ball is harmless

						if (anchor == 0 || anchor == 2) {
							if (difficulty == 2)
								temp = Vector2D.distanceX(((Ball) o).getPos(), pos); // get the priority ball
							else
								temp = Vector2D.distance(((Ball) o).getPos(), pos); // get the priority ball
						} else {
							if (difficulty == 2)
								temp = Vector2D.distanceY(((Ball) o).getPos(), pos); // get the priority ball
							else
								temp = Vector2D.distance(((Ball) o).getPos(), pos); // get the priority ball
						}
						if (!binf) {
							d = temp; // set the first ball
							b = ((Ball) o);
							binf = true;
						} else if (temp < d) { // if a new ball is found to be closer, follow it
							d = temp;
							b = ((Ball) o);
						}
					} // end instance of
				} // end for
			} // end check cell size
		} // end for
		return b; // return the ball to chase
	} // end look

	/**
	 * This Method will check if a cell has more than 1 object in it for possible collisions
	 * 
	 * @param b
	 * @param currentCell
	 */
	public void checkCell(Ball b, int[][] currentCell) {
		if (currentCell == null) { // I don't think this will ever happen
			System.out.println("no collision"); // DEBUG
			return;
		}

		for (int x = 0; x < currentCell.length; x++) { // for each cell
			int cx = currentCell[x][0];
			int cy = currentCell[x][1];
			if (cell[cx][cy].size() >= 2) { // for each object in cell
				/* handle collisions in here */
				for (Object o : cell[cx][cy]) {
					if (o instanceof Ball && o != b) // if object is another ball
						b.checkCollision((Ball) o);
					if (b.hasBeenDeployed() && o instanceof BallInjector)
						((BallInjector) o).checkCollision(b);
				}
			}
		}
	}

	/**
	 * adds a player to the grid
	 * 
	 * @param p
	 * @param x
	 * @param y
	 */
	public void add(Player p, int x, int y) {
		cell[x][y].add(p);
	}

	/**
	 * adds an AI to the grid
	 * 
	 * @param ai
	 * @param x
	 * @param y
	 */
	public void add(AI ai, int x, int y) {
		cell[x][y].add(ai);
	}

	/**
	 * adds a ball to the grid
	 * 
	 * @param b
	 * @param x
	 * @param y
	 */
	public void add(Ball b, int x, int y) {
		cell[x][y].add(b);
	}

	/**
	 * adds a ball injector to the grid
	 * 
	 * @param bi
	 * @param x
	 * @param y
	 */
	public void add(BallInjector bi, int x, int y) {
		cell[x][y].add(bi);
	}

	/**
	 * This method will remove all objects from the grid
	 */
	public void clear() {
		for (int x = 0; x < 16; x++)
			for (int y = 0; y < 16; y++)
				cell[x][y].clear();
	}

	/**
	 * removes all of a ball from the grid
	 * 
	 * @param b
	 */
	public void removeAllOf(Ball b) {
		int[][] oldCells = null;
		if (b.getCells() != null) // get oldCells if not null
			oldCells = b.getCells();
		for (int i = 0; i < oldCells.length; i++) {
			int oldx = oldCells[i][0];
			int oldy = oldCells[i][1];
			remove(b, oldx, oldy);
		}
	}

	/**
	 * removes a player from the grid
	 * 
	 * @param p
	 * @param x
	 * @param y
	 */
	public void remove(Player p, int x, int y) {
		cell[x][y].remove(p);
	}

	/**
	 * removes an AI from the grid
	 * 
	 * @param ai
	 * @param x
	 * @param y
	 */
	public void remove(AI ai, int x, int y) {
		cell[x][y].remove(ai);
	}

	/**
	 * removes a ball from the grid
	 * 
	 * @param b
	 * @param x
	 * @param y
	 */
	public void remove(Ball b, int x, int y) {
		cell[x][y].remove(b);
	}

	/**
	 * removes a ball injector from the grid
	 * 
	 * @param bi
	 * @param x
	 * @param y
	 */
	public void remove(BallInjector bi, int x, int y) {
		cell[x][y].remove(bi);
	}

	/**
	 * update the AI in the collision grid
	 * 
	 * @param ai
	 * @param v
	 * @param w
	 * @param h
	 * @return
	 */
	public int[][] update(AI ai, Vector2D v, int w, int h) {
		// get the new squares
		Vector2D v1 = new Vector2D((int) Math.floor((v.x - w / 2) / cellDim), (int) Math.floor((v.y - h / 2) / cellDim));
		Vector2D v2 = new Vector2D((int) Math.floor((v.x + w / 2) / cellDim), (int) Math.floor((v.y + h / 2) / cellDim));

		int rx = (int) (Vector2D.distanceX(v1, v2) + 1);
		int ry = (int) (Vector2D.distanceY(v1, v2) + 1);

		int[][] oldCells = null;
		if (ai.getCells() != null) // get oldCells if not null
			oldCells = ai.getCells();

		int[][] newCells = new int[rx * ry][2]; // create a new sub-grid that the player occupies

		// fill the sub-grid
		for (int i = 0, x = 0; i < rx; i++) {
			for (int j = 0; j < ry; j++) {
				newCells[x][0] = Math.max(0, Math.min((i + (int) v1.x), 15)); // math.min makes sure that the cell stays within the main-grid
				newCells[x][1] = Math.max(0, Math.min((j + (int) v1.y), 15));
				x++;
			}
		}

		// we want: old U new / (old / new) -> new
		// make 2 new arrays
		// one of the new one to add
		// and one of the old ones to delete

		// insert sub-grid into main-grid if its not already in the cell
		if (ai.getCells() != null) {

			for (int x = 0; x < oldCells.length; x++) {
				int oldx = oldCells[x][0];
				int oldy = oldCells[x][1];
				remove(ai, oldx, oldy);
			}

			for (int x = 0; x < newCells.length; x++) {
				int i = newCells[x][0];
				int j = newCells[x][1];

				if (!cell[i][j].contains(ai)) {
					add(ai, i, j);
				}
			}
		} else {
			for (int x = 0; x < newCells.length; x++) {
				int i = newCells[x][0];
				int j = newCells[x][1];
				add(ai, i, j);
			}
		}

		return newCells;
	}

	/**
	 * updates the player in the grid
	 * 
	 * @param p
	 * @param v
	 * @param w
	 * @param h
	 * @return
	 */
	public int[][] update(Player p, Vector2D v, int w, int h) {
		// get the new squares
		Vector2D v1 = new Vector2D((int) Math.floor((v.x - w / 2) / cellDim), (int) Math.floor((v.y - h / 2) / cellDim));
		Vector2D v2 = new Vector2D((int) Math.floor((v.x + w / 2) / cellDim), (int) Math.floor((v.y + h / 2) / cellDim));

		int rx = (int) (Vector2D.distanceX(v1, v2) + 1);
		int ry = (int) (Vector2D.distanceY(v1, v2) + 1);

		int[][] oldCells = null;
		if (p.getCells() != null) // get oldCells if not null
			oldCells = p.getCells();

		int[][] newCells = new int[rx * ry][2]; // create a new sub-grid that the player occupies

		// fill the sub-grid
		for (int i = 0, x = 0; i < rx; i++) {
			for (int j = 0; j < ry; j++) {
				newCells[x][0] = Math.max(0, Math.min((i + (int) v1.x), 15)); // math.min makes sure that the cell stays within the main-grid
				newCells[x][1] = Math.max(0, Math.min((j + (int) v1.y), 15));
				x++;
			}
		}

		// we want: old U new / (old / new) -> new
		// make 2 new arrays
		// one of the new one to add
		// and one of the old ones to delete

		// insert sub-grid into main-grid if its not already in the cell
		if (p.getCells() != null) {

			for (int x = 0; x < oldCells.length; x++) {
				int oldx = oldCells[x][0];
				int oldy = oldCells[x][1];
				remove(p, oldx, oldy);
			}

			for (int x = 0; x < newCells.length; x++) {
				int i = newCells[x][0];
				int j = newCells[x][1];

				if (!cell[i][j].contains(p)) {
					add(p, i, j);
				}
			}
		} else {
			for (int x = 0; x < newCells.length; x++) {
				int i = newCells[x][0];
				int j = newCells[x][1];
				add(p, i, j);
			}
		}

		return newCells;
	}

	/**
	 * updates the ball in the grid
	 * 
	 * @param b
	 * @param v
	 * @param w
	 * @param h
	 * @return
	 */
	public int[][] update(Ball b, Vector2D v, int w, int h) {
		// get the new squares
		Vector2D v1 = new Vector2D(Math.max(0, Math.min((int) Math.floor((v.x - w / 2) / cellDim), 15)), Math.max(0, Math.min((int) Math.floor((v.y - h / 2) / cellDim), 15)));
		Vector2D v2 = new Vector2D(Math.max(0, Math.min((int) Math.floor((v.x + w / 2) / cellDim), 15)), Math.max(0, Math.min((int) Math.floor((v.y + h / 2) / cellDim), 15)));
		int rx = (int) (Vector2D.distanceX(v1, v2) + 1);
		int ry = (int) (Vector2D.distanceY(v1, v2) + 1);
		int[][] oldCells = null;
		if (b.getCells() != null) // get oldCells if not null
			oldCells = b.getCells();

		int[][] newCells = new int[rx * ry][2]; // create a new sub-grid that the player occupies

		// fill the sub-grid
		for (int i = 0, x = 0; i < rx; i++) {
			for (int j = 0; j < ry; j++) {
				newCells[x][0] = Math.max(0, Math.min((i + (int) v1.x), 15)); // math.min makes sure that the cell stays within the main-grid
				newCells[x][1] = Math.max(0, Math.min((j + (int) v1.y), 15));
				x++;
			}
		}

		// we want: old U new / (old / new) -> new
		// make 2 new arrays
		// one of the new one to add
		// and one of the old ones to delete

		// insert sub-grid into main-grid if its not already in the cell
		if (b.getCells() != null) {

			// remove the old cells
			for (int x = 0; x < oldCells.length; x++) {
				int oldx = oldCells[x][0];
				int oldy = oldCells[x][1];
				remove(b, oldx, oldy);
			}

			for (int x = 0; x < newCells.length; x++) {
				int i = newCells[x][0];
				int j = newCells[x][1];
				if (!cell[i][j].contains(b)) {
					add(b, i, j);
				}
			}
		} else {
			for (int x = 0; x < newCells.length; x++) {
				int i = newCells[x][0];
				int j = newCells[x][1];
				add(b, i, j);
			}
		}
		return newCells;
	}

	/**
	 * updates the ball injector in the grid
	 * 
	 * @param bi
	 * @param v
	 * @param w
	 * @param h
	 * @return
	 */
	public int[][] update(BallInjector bi, Vector2D v, int w, int h) {
		// get the new squares
		Vector2D v1 = new Vector2D((int) Math.floor((v.x - w / 2) / cellDim), (int) Math.floor((v.y - h / 2) / cellDim));
		Vector2D v2 = new Vector2D((int) Math.floor((v.x + w / 2) / cellDim), (int) Math.floor((v.y + h / 2) / cellDim));

		int rx = (int) (Vector2D.distanceX(v1, v2) + 1);
		int ry = (int) (Vector2D.distanceY(v1, v2) + 1);

		int[][] oldCells = null;
		if (bi.getCells() != null) // get oldCells if not null
			oldCells = bi.getCells();

		int[][] newCells = new int[rx * ry][2]; // create a new sub-grid that the player occupies

		// fill the sub-grid
		for (int i = 0, x = 0; i < rx; i++) {
			for (int j = 0; j < ry; j++) {
				newCells[x][0] = Math.max(0, Math.min((i + (int) v1.x), 15)); // math.min makes sure that the cell stays within the main-grid
				newCells[x][1] = Math.max(0, Math.min((j + (int) v1.y), 15));
				x++;
			}
		}

		// we want: old U new / (old / new) -> new
		// make 2 new arrays
		// one of the new one to add
		// and one of the old ones to delete

		// insert sub-grid into main-grid if its not already in the cell
		if (bi.getCells() != null) {

			for (int x = 0; x < oldCells.length; x++) {
				int oldx = oldCells[x][0];
				int oldy = oldCells[x][1];
				remove(bi, oldx, oldy);
			}

			for (int x = 0; x < newCells.length; x++) {
				int i = newCells[x][0];
				int j = newCells[x][1];

				if (!cell[i][j].contains(bi)) {
					add(bi, i, j);
				}
			}
		} else {
			for (int x = 0; x < newCells.length; x++) {
				int i = newCells[x][0];
				int j = newCells[x][1];
				add(bi, i, j);
			}
		}

		return newCells;
	}

	/**
	 * draws the collision grid
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {

		for (int x = 0; x < gridWidth; x += cellDim)
			for (int y = 0; y < gridHeight; y += cellDim) {

				// fill occupied cells
				g.setColor(Color.DARK_GRAY);
				if (!cell[x / cellDim][y / cellDim].isEmpty()) {
					if (cell[x / cellDim][y / cellDim].size() < 2)
						g.setColor(Color.DARK_GRAY);
					else
						g.setColor(Color.RED);
					g.fillRect(x, y, cellDim, cellDim);
				}

				// draw the grid
				g.setColor(Color.WHITE);
				g.drawLine(x, 0, x, gridHeight);
				g.drawLine(0, y, gridWidth, y);

			}
	}
}
