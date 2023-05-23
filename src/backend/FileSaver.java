package backend;

import java.util.ArrayList;

public class FileSaver {
    private String[] outputString;

    public FileSaver(Grid grid) {
        int rowCount = grid.getTableau().size();
        outputString = new String[rowCount];

        int position = 0;
        for (ArrayList<Cell> row : grid.getTableau()) {
            outputString[position] = "";

            for (Cell cell : row) {
                int value = cell.getValue();
                outputString[position] += value + ";";
            }

            // Supprimer le dernier point-virgule de la ligne
            outputString[position] = outputString[position].substring(0, outputString[position].length() - 1);

            position++;
        }
    }

    public String[] getStringRepresentation() {
        return outputString;
    }
}
