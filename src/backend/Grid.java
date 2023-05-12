package backend;

import java.util.*;

public class Grid {
	public ArrayList<ArrayList<Cell>> tableau = new ArrayList<ArrayList<Cell>>();
	private Simulator simu;
	public Grid(Simulator simu) {
		this.simu = simu;
	}
	/**
	 * Update the size of the grid by adding rows or cols of Cells
	 */
	public void updateGrid() {
		while (simu.getWidth()!=tableau.get(0).size()&&simu.getHeight()!=tableau.size()) {
			if (simu.getWidth()>tableau.get(0).size()) {
				appendCol();
			}
			if (simu.getWidth()<tableau.get(0).size()) {
				removeCol();
			}
			if (simu.getHeight()>tableau.size()) {
				appendCol();
			}
			if (simu.getHeight()<tableau.size()) {
				removeCol();
			}
		}
	}
	/**
	 * Add row
	 */
	public void appendRow() {
		if (tableau.isEmpty()) {
			tableau.add(new ArrayList<Cell>());
			tableau.get(0).add(new Cell(0,0,0));
		} else {
			tableau.add(new ArrayList<Cell>());
			for (int i=0; i<tableau.size();i++) {
				tableau.get(tableau.size()).add(new Cell(i,tableau.get(tableau.size()).size()));
			}
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
				tableau.get(i).add(new Cell(tableau.get(i).size(),i));
			}
		}
	}
	
	/**
	 * remove row
	 */
	public void removeRow() {
		tableau.remove(tableau.size());
	}
	
	/**
	 * remove col
	 */
	public void removeCol() {
		for (int i=0; i<tableau.size();i++) {
			tableau.get(i).get()
		}
	}
}
