package backend;

import java.util.*;

public class Grid {
	public ArrayList<ArrayList<Cell>> tableau = new ArrayList<ArrayList<Cell>>();
	private Simulator simu;
	public Grid(Simulator simu) {
		this.simu = simu;
		appendRow();
		while (simu.getWidth()!=tableau.get(0).size()||simu.getHeight()!=tableau.size()) {
			if (simu.getHeight()>tableau.size()) {
				appendRow();
			}
			if (simu.getWidth()>tableau.get(0).size()) {
				appendCol();
			}
		}
	}
	/**
	 * Update the size of the grid by adding rows or cols of Cells
	 */
	public void updateGrid() {
		while (simu.getWidth()!=tableau.get(0).size()||simu.getHeight()!=tableau.size()) {
			if (simu.getWidth()>tableau.get(0).size()) {
				appendCol();
			}
			if (simu.getWidth()<tableau.get(0).size()) {
				removeCol();
			}
			if (simu.getHeight()>tableau.size()) {
				appendRow();
			}
			if (simu.getHeight()<tableau.size()) {
				removeRow();
			}
		}
		checkLineIntegrity();
	}
	
	public Cell getCell(int x, int y) {
		return tableau.get(y).get(x);
	}
	
	/**
	 * Return if the cell come to life; highest color being the strongest
	 * Priority is given to if the cell is living
	 * @param x
	 * @param y
	 * @return cell living type (0 if dead)
	 */
	public int checkLiveOrDeath(int x, int y) {
		int returnedValue = 0;
		for (int z = 1; z < 6; z++) {
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
					if(j<0&&simu.isLoopingBorder()) {
						j=tableau.get(0).size()-1;
					}
					if(i<0&&simu.isLoopingBorder()) {
						i=tableau.size()-1;
					}
					if(j>tableau.get(0).size()-1&&simu.isLoopingBorder()) {
						j=0;
					}
					if(i>tableau.size()-1&&simu.isLoopingBorder()) {
						i=0;
					}
					//take into consideration only the part we are interested for to not look out of the grid
					if (i>=0&&i<tableau.size()&&j>=0&&j<tableau.get(i).size()) {
						if (value==tableau.get(i).get(j).getValue()) {
							nbOfNeighbors++;
						}
					}
				}
			}
		}
		return nbOfNeighbors;
	}
	
	public void checkLineIntegrity() {
		for (int i = 0; i<simu.getHeight();i++) {
			if (tableau.get(i).size()!=simu.getWidth()) {
				for (int j = tableau.get(i).size()-1; j<simu.getHeight();j++) {
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
}
