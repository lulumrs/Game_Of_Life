package backend;

import java.util.*;

public class Grid {
	public ArrayList<ArrayList<Cell>> tableau = new ArrayList<ArrayList<Cell>>();
	private int width;
	private int height;
	private boolean loop;
	
	public Grid(int w, int h) {
		width = w;
		height = h;
		
		loop = true;
		
		appendRow();
		while (width!=tableau.get(0).size()||height!=tableau.size()) {
			if (height>tableau.size()) {
				appendRow();
			}
			if (width>tableau.get(0).size()) {
				appendCol();
			}
		}
	}
	/**
	 * Update the size of the grid by adding rows or cols of Cells
	 */
	public void updateGrid() {
		while (width!=tableau.get(0).size()||height!=tableau.size()) {
			if (width>tableau.get(0).size()) {
				appendCol();
			}
			if (width<tableau.get(0).size()) {
				removeCol();
			}
			if (height>tableau.size()) {
				appendRow();
			}
			if (height<tableau.size()) {
				removeRow();
			}
		}
		checkLineIntegrity();
	}
	
	public Cell getCell(int x, int y) {
		return tableau.get(y).get(x);
	}
	
	public ArrayList<ArrayList<Cell>> getTableau(){
		return tableau;
	}
	
	public void setNewGridSize(int w, int h) {
		width = w;
		height = h;
	}
	
	/**
	 * Return if the cell come to life; highest color being the strongest
	 * Priority is given to if the cell is living
	 * @param x
	 * @param y
	 * @return cell living type (0 if dead)
	 */
	public int checkLiveOrDeath(int x, int y, String type) {
		int returnedValue = 0;
		for (int z = 1; z < 6; z++) {
			// could had been done with 2 if but this way is readable
			if (type == "Game of life") {
				if (getNeighborhoodOfCell(x, y, z)==3) {
					returnedValue = z;
				}
				int CellValue = getCell(x, y).getValue();
				if (CellValue!=0) {
					if (getNeighborhoodOfCell(x, y, CellValue)==3||getNeighborhoodOfCell(x, y, CellValue)==2) {
						returnedValue = CellValue;
					}
				}
			}
			
			if (type == "High Life") {//B36/S23
				if (getNeighborhoodOfCell(x, y, z)==3||getNeighborhoodOfCell(x, y, z)==6) {
					returnedValue = z;
				}
				int CellValue = getCell(x, y).getValue();
				if (CellValue!=0) {
					if (getNeighborhoodOfCell(x, y, CellValue)==3||getNeighborhoodOfCell(x, y, CellValue)==2) {
						returnedValue = CellValue;
					}
				}
			}
			
			if (type == "Labyrinthes") {//B3/S2345
				if (getNeighborhoodOfCell(x, y, z)==3) {
					returnedValue = z;
				}
				int CellValue = getCell(x, y).getValue();
				if (CellValue!=0) {
					if (getNeighborhoodOfCell(x, y, CellValue)==3||getNeighborhoodOfCell(x, y, CellValue)==2||getNeighborhoodOfCell(x, y, CellValue)==4||getNeighborhoodOfCell(x, y, CellValue)==5) {
						returnedValue = CellValue;
					}
				}
			}
			
			if (type == "Exploding with chaos") {//B2/S
				if (getNeighborhoodOfCell(x, y, z)==2) {
					returnedValue = z;
				}
			}
			
			if (type == "replicating paterns") {//B1357/S1357
				if (getNeighborhoodOfCell(x, y, z)==1||getNeighborhoodOfCell(x, y, z)==3||getNeighborhoodOfCell(x, y, z)==5||getNeighborhoodOfCell(x, y, z)==7) {
					returnedValue = z;
				}
				int CellValue = getCell(x, y).getValue();
				if (CellValue!=0) {
					if (getNeighborhoodOfCell(x, y, CellValue)==3||getNeighborhoodOfCell(x, y, CellValue)==1||getNeighborhoodOfCell(x, y, CellValue)==5||getNeighborhoodOfCell(x, y, CellValue)==7) {
						returnedValue = CellValue;
					}
				}
			}
			
			if (type == "day and night") {//B36/S23
				if (getNeighborhoodOfCell(x, y, z)==3||getNeighborhoodOfCell(x, y, z)==6||getNeighborhoodOfCell(x, y, z)==7||getNeighborhoodOfCell(x, y, z)==8) {
					returnedValue = z;
				}
				int CellValue = getCell(x, y).getValue();
				if (CellValue!=0) {
					if (getNeighborhoodOfCell(x, y, CellValue)==3||getNeighborhoodOfCell(x, y, CellValue)==4||getNeighborhoodOfCell(x, y, CellValue)==6||getNeighborhoodOfCell(x, y, CellValue)==7||getNeighborhoodOfCell(x, y, CellValue)==8) {
						returnedValue = CellValue;
					}
				}
			}
			
		}
		return returnedValue;
	}
	/**
	 * check for the surrounding Cells in the grid, taking the borders into consideration
	 * @param x
	 * @param y
	 * @return nb of neighbors
	 */
	public int getNeighborhoodOfCell(int x, int y, int value) {
		int nbOfNeighbors = 0;
		for (int i = y-1;i<=y+1;i++) {
			for (int j = x-1;j<=x+1;j++) {
				if (!(i == y && j == x)) {
					//check for the borders if they are looping or not !
					int i_val = i;
					int j_val = j;
					if(j_val<0&&loop) {
						j_val=tableau.get(0).size()-1;
					}
					if(i_val<0&&loop) {
						i_val=tableau.size()-1;
					}
					if(j_val>tableau.get(0).size()-1&&loop) {
						j_val=0;
					}
					if(i_val>tableau.size()-1&&loop) {
						i_val=0;
					}
					//take into consideration only the part we are interested for to not look out of the grid
					if (i_val>=0&&i_val<tableau.size()&&j_val>=0&&j_val<tableau.get(i_val).size()) {
						if (value==tableau.get(i_val).get(j_val).getValue()) {
							nbOfNeighbors++;
						}
					}
				}
			}
		}
		return nbOfNeighbors;
	}
	/**
	 * in case the simulator behavior is "expending",
	 * the line is adjusted with this function
	 */
	public void checkLineIntegrity() {
		for (int i = 0; i<height;i++) {
			if (tableau.get(i).size()!=width) {
				for (int j = tableau.get(i).size()-1; j<width;j++) {
					tableau.get(i).add(new Cell(0,0,0));
				}
			}
		}
	}
	
	/**
	 * Add row
	 */
	public void appendRow() {
		tableau.add(new ArrayList<Cell>());
		for (int i=0; i<tableau.size();i++) {
			tableau.get(tableau.size()-1).add(new Cell(i,tableau.size()-1));
		}
	}
	
	/**
	 * add col
	 */
	public void appendCol() {
		if (tableau.isEmpty()) {
			tableau.add(new ArrayList<Cell>());
			tableau.get(0).add(new Cell(0,0,0));
		} else {
			for (int i=0; i<tableau.size();i++) {
				tableau.get(i).add(new Cell(tableau.get(i).size()-1,i));
			}
		}
	}
	
	/**
	 * remove row
	 */
	public void removeRow() {
		tableau.remove(tableau.size()-1);
	}
	
	/**
	 * remove col
	 */
	public void removeCol() {
		for (int i=0; i<tableau.size();i++) {
			tableau.get(i).remove(tableau.get(i).size() - 1);
		}
	}
	
	public void setLoop(boolean l) {
		loop = l;
	}
}
