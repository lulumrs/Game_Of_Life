package backend;

import windowInterface.MyInterface;
//TODO : add imports you will need here
/*
 *  Note : if you use an import in another class you will need to add
 *  the import lines on top of the file of the other class.
 */
// Examples of useful imports :
// import java.util.LinkedList;
// import java.util.ArrayList;
// import java.util.Random;

public class Simulator extends Thread {

	private MyInterface mjf;
	private boolean stopFlag;
	private boolean pauseFlag;
	private boolean loopedBorders;
	private boolean behavior;
	public boolean highLife;
	private int loopDelay;
	private int minimalCellSize;
	private String gameType;
	public Grid grid;
	//TODO : add declaration of additional attributes here

	public Simulator(MyInterface mjfParam) {
		mjf = mjfParam;
		stopFlag=false;
		pauseFlag=false;
		loopDelay = 150;
		minimalCellSize = 10;// in pixel
		loopedBorders = mjf.getLooping();
		behavior = mjf.getBehavior();
		gameType = mjf.getGameType();
		//TODO : add other attribute initialization here
		//highLife = mjf.checkBoxHigh.isSelected();
		grid = new Grid(getWidth(),getHeight());

	}
	
	/**
	 * getter of the width of the simulated world
	 * @return the number of columns in the grid composing the simulated world
	 */
	public int getWidth() {
		int width;
		if (behavior) {
			width =(int) (mjf.getPanelDessin().getWidth()/minimalCellSize);
		} else {
			if (grid == null) {
				width =(int) (mjf.getPanelDessin().getWidth()/minimalCellSize);
			} else {
				width = grid.getTableau().get(0).size();
			}
		}
		
		return width;
	}

	/**
	 * getter of the height of the simulated world
	 * @return the number of rows in the grid composing the simulated world
	 */
	public int getHeight() {
		int height;
		
		
		if (behavior) {
			height =(int) (mjf.getPanelDessin().getHeight()/minimalCellSize);
		} else {
			if (grid == null) {
				height =(int) (mjf.getPanelDessin().getHeight()/minimalCellSize);
			} else {
				height = grid.getTableau().size();
			}
		}
		return height;
	}
	
	public void updateSize() {
		if (behavior) {
			grid.setNewGridSize(getWidth(), getHeight());
			grid.updateGrid();
		}
	}
	
	public void run() {
		//WARNING : Do not modify this.
		/*Exception : 
		 *if everything you have to do works and you have a backup... 
		 *have fun editing this if you want to enhance it!
		 *But be sure to take into account that this class inherits from Thread
		 *You might want to check documentation online about the Thread class
		 *But do not hesitate to email me any questions 
		*/
		int stepCount=0;
		while(!stopFlag) {
			stepCount++;
			if (behavior) {
				grid.setNewGridSize(getWidth(), getHeight());
				grid.updateGrid();
			}
			makeStep();
			mjf.update(stepCount);
			try {
				Thread.sleep(loopDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while(pauseFlag && !stopFlag) {
				try {
					Thread.sleep(loopDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Individual step of the Simulation, modifying the world from
	 * its state at time t to its state at time t+1
	 */
	public void makeStep() {
		//change the pre state to what it should be depending on the game
		for (int i = 0; i < grid.getWidth();i++) {
			for (int j = 0; j < grid.getHeight();j++) {
				setCell(i, j, grid.checkLiveOrDeath(i, j, gameType));
			}
		}
		for (int i = 0; i < grid.getWidth();i++) {
			for (int j = 0; j < grid.getHeight();j++) {
				grid.getCell(i, j).setValue();
			}
		}
		
	}

	/**
	 * Stops simulation by raising the stop flag used in the run method
	 */
	public void stopSimu() {
		stopFlag = true;
		
	}

	/**
	 * Toggles Pause of simulation 
	 * by raising or lowering the pause flag used in the run method
	 */
	public void togglePause() {
		pauseFlag = !pauseFlag;

	}
	/**
	 * Changes content value of the Cell at the coordinates specified in arguments
	 * 
	 * 
	 * @param x coordinate on the x-axis (horizontal)
	 * @param y coordinate on the y-axis (vertical)
	 */
	public void toggleCell(int x, int y) {
		grid.getCell(x, y).toggle();
	}
	/**
	 * get the value of a cell at coordinates
	 * @param x coordinate
	 * @param y coordinate
	 * @return the value of the cell
	 */
	public int getCell(int x, int y) {
		//TODO implement proper return
		return grid.getCell(x, y).getValue();
	}


	/**
	 * set the future value of a cell at coordinates
	 * @param x coordinate
	 * @param y coordinate
	 * @param val the value to set inside the cell
	 */
	public void setCell(int x, int y, int val) {
		grid.getCell(x, y).setFutureValue(val);
	}
	
	public void setTypeOfGame(String type) {
		gameType = type;
	}

	/**
	 * Each String in the returned array represents a [row/column]
	 * 
	 * @return an array of Strings representing the simulated world's state
	 */
	public String[] getFileRepresentation() {
		FileSaver file = new FileSaver(grid);
		return file.getStringRepresentation();
	}
	/**
	 * Populates a [row/column] indicated by the given coordinate
	 * using its String representation
	 * 
	 * @param y the y coordinate of the row/column to populate
	 * @param fileLine the String line representing the row
	 */
	public void populateLine(int coord, String fileLine) {
		String[] values = fileLine.split(";");
		if (coord<getHeight()&&behavior) {
			for (int x = 0; x < values.length&&x<getWidth(); x++) {
			    int value = Integer.parseInt(values[x]);
			    grid.getCell(x, coord).adressNewValue(value);
			}
		} else if (!behavior) {
			//pas du tout optimisé, permet de réduire l'espace s'il a été créé trop grand au début
			while (coord==getHeight()&&values.length==getWidth()) {
				if (values.length<getWidth()) {
					grid.removeCol();
				}
				if (values.length>getWidth()) {
					grid.appendCol();
				}
				if (coord<getHeight()) {
					grid.removeRow();
				}
				if (coord>getHeight()) {
					grid.appendRow();
				}
			}
			for (int x = 0; x < values.length; x++) {
			    int value = Integer.parseInt(values[x]);
			    grid.getCell(x, coord).adressNewValue(value);
			}
		}
	}
	
	/**
	 * populates world with randomly living cells
	 * 
	 * @param chanceOfLife the probability, expressed between 0 and 1, 
	 * that any given cell will be living
	 */
	public void generateRandom(float chanceOfLife) {
		for (int row = 0; row < getHeight(); row++) {
	        for (int col = 0; col < getWidth(); col++) {
	            if (Math.random() <= chanceOfLife) {
	                // Set the cell at (row, col) as alive
	                grid.getCell(col, row).toggle();
	            } else {
	                continue;
	            }
	        }
	    }
	}
	
	/**
	 * Change the behavior of the simulator, is it gets bigger or if the cells gets bigger
	 */
	public void toggleBehavior() {
		behavior = !behavior;
		System.out.println(behavior);
	}
	
	/**
	 * check the behavior of the world
	 * @return bool
	 */
	public boolean isExpanding() {
		return behavior;
	}
	
	/**
	 * Checks if the borders are looping
	 * 
	 * @return true if the borders are looping, false otherwise
	 */
	public boolean isLoopingBorder() {
		return loopedBorders;
	}
	
	/**
	 * Toggle the looping of borders, activating or deactivating it
	 * depending on the present state
	 */
	public void toggleLoopingBorder() {
		loopedBorders = !loopedBorders;
		grid.setLoop(loopedBorders);
	}
	
	/**
	 * Setter for the delay between steps of the simulation
	 * @param delay in milliseconds
	 */
	public void setLoopDelay(int delay) {
		loopDelay = delay;
		//TODO : implement
	}
}
