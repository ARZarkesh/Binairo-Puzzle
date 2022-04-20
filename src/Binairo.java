public class Binairo {
    private final Board board;
    private int backtrackingCount = 0;

    public Binairo(Board board) {
        this.board = board;
    }

    public void start() {
        long tStart = System.nanoTime();

        drawLine();
        System.out.println("Initial Board: \n");
        board.print();
        drawLine();

        if (backtrack(this.board)) {
            System.out.println("\nSolution found!");
        } else {
            System.out.println("\nNo solution found!");
        }
        long tEnd = System.nanoTime();
        board.print();
        System.out.println("Total time: " + (tEnd - tStart) / 1000000000.000000000);
    }

    private boolean backtrack(Board board) {
        return recursiveBacktrack(board);
    }

    private boolean recursiveBacktrack(Board board) {
        backtrackingCount++;
        if (isFinished(board)) {
            return true;
        }

        State unassignedCell = selectUnassignedCell(board);
        for (String value : unassignedCell.getRemainingValues()) {
            unassignedCell.set(value, false);
            // board.print();
            // drawLine();
            if (isConsistent(board)) {
                // forward check
                if (forwardCheck(board, unassignedCell)) {
                    if (recursiveBacktrack(board)) {
                        return true;
                    }
                }
            }
            unassignedCell.set("e", false);
            // board.print();
            // drawLine();
            // unassignedCell.resetRemainingValues();
        }

        return false;
    }

    private boolean forwardCheck(Board board, State cell) {
        if (backtrackingCount % 10 != 0) return true; // skip forward check every 10th backtracking
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                State targetCell = board.getCell(i, j);
                if (targetCell.getValue().equals("e")) {
                    int count = 0;
                    for (String value : cell.getRemainingValues()) {
                        targetCell.set(value, false);
                        if (checkSpecificRow(board, targetCell)
                                &&
                                checkSpecificColumn(board, targetCell)
                                &&
                                checkAdjacencyInRow(board, targetCell)
                                &&
                                checkAdjacencyInColumn(board, targetCell)) {
                            count++;
                            break;  // if there is at least one possible value, break
                        }
                    }

                    targetCell.set("e", false);
                    if (count == 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private State selectUnassignedCell(Board board) {
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getCell(i, j).getValue().equals("e")) {
                    return board.getCell(i, j);
                }
            }
        }

        return null;
        // return MRV(board);
    }

    private State MRV(Board board) {
        int minRemainingValues = Integer.MAX_VALUE;
        State minRemainingValuesCell = null;
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                State cell = board.getCell(i, j);
                if (cell.getValue().equals("e")) {
                    if (cell.getRemainingValues().size() < minRemainingValues) {
                        minRemainingValues = cell.getRemainingValues().size();
                        minRemainingValuesCell = cell;
                    }
                }
            }
        }
        return minRemainingValuesCell;
    }

    private boolean checkNumberOfCircles(Board board) {
        int size = board.getSize();
        // row
        for (int i = 0; i < size; i++) {
            int numberOfWhites = 0;
            int numberOfBlacks = 0;
            for (int j = 0; j < size; j++) {
                String value = board.getCell(i, j).getValue();
                if (value.equals("w")) {
                    numberOfWhites++;
                } else if (value.equals("b")) {
                    numberOfBlacks++;
                }
            }
            if (numberOfBlacks > size / 2 || numberOfWhites > size / 2) {
                return false;
            }
        }
        // column
        for (int i = 0; i < size; i++) {
            int numberOfWhites = 0;
            int numberOfBlacks = 0;
            for (int j = 0; j < size; j++) {
                String value = board.getCell(j, i).getValue();
                if (value.equals("w")) {
                    numberOfWhites++;
                } else if (value.equals("b")) {
                    numberOfBlacks++;
                }
            }
            if (numberOfBlacks > size / 2 || numberOfWhites > size / 2) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSpecificRow(Board board, State cell) {
        int size = board.getSize();
        int row = board.getPositionOfCell(cell).getX();
        int valueCount = 0;
        for (int j = 0; j < size; j++) {
            String value = board.getCell(row, j).getValue();
            if (value.equals(cell.getValue())) {
                valueCount++;
            }
        }
        return valueCount <= size / 2;
    }

    private boolean checkSpecificColumn(Board board, State cell) {
        int size = board.getSize();
        int column = board.getPositionOfCell(cell).getY();
        int valueCount = 0;
        for (int i = 0; i < size; i++) {
            String value = board.getCell(i, column).getValue();
            if (value.equals(cell.getValue())) {
                valueCount++;
            }
        }
        return valueCount <= size / 2;
    }

    private boolean checkAdjacencyInRow(Board board, State cell) {
        int size = board.getSize();
        int row = board.getPositionOfCell(cell).getX();
        int column = board.getPositionOfCell(cell).getY();
        for (int j = column - 2; j <= column + 2; j++) {
            if (j >= 0 && j < size - 2) {
                String c1 = board.getCell(row, j).getValue();
                String c2 = board.getCell(row, j + 1).getValue();
                String c3 = board.getCell(row, j + 2).getValue();
                if (c1.equals(c2) && c2.equals(c3) && !c1.equals("e")) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkAdjacencyInColumn(Board board, State cell) {
        int size = board.getSize();
        int row = board.getPositionOfCell(cell).getX();
        int column = board.getPositionOfCell(cell).getY();
        for (int i = row - 2; i <= row + 2; i++) {
            if (i >= 0 && i < size - 2) {
                String c1 = board.getCell(i, column).getValue();
                String c2 = board.getCell(i + 1, column).getValue();
                String c3 = board.getCell(i + 2, column).getValue();
                if (c1.equals(c2) && c2.equals(c3) && !c1.equals("e")) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkAdjacency(Board board) {
        int size = board.getSize();
        // Horizontal
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 2; j++) {
                String c1 = board.getCell(i, j).getValue();
                String c2 = board.getCell(i, j + 1).getValue();
                String c3 = board.getCell(i, j + 2).getValue();
                if (c1.equals(c2) && c2.equals(c3) && !c1.equals("e")) {
                    return false;
                }
            }
        }
        // column
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size - 2; i++) {
                String c1 = board.getCell(i, j).getValue();
                String c2 = board.getCell(i + 1, j).getValue();
                String c3 = board.getCell(i + 2, j).getValue();
                if (c1.equals(c2) && c2.equals(c3) && !c1.equals("e")) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkIfUnique(Board board) {
        int size = board.getSize();

        // check if two rows are duplicated
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                int count = 0;
                for (int k = 0; k < size; k++) {
                    String value = board.getCell(i, k).getValue();
                    if (value.equals(board.getCell(j, k).getValue()) && !value.equals("e")) {
                        count++;
                    }
                }
                if (count == size) {
                    return false;
                }
            }
        }

        // check if two columns are duplicated
        for (int j = 0; j < size - 1; j++) {
            for (int k = j + 1; k < size; k++) {
                int count = 0;
                for (int i = 0; i < size; i++) {
                    String value = board.getCell(i, j).getValue();
                    if (value.equals(board.getCell(i, k).getValue()) && !value.equals("e")) {
                        count++;
                    }
                }
                if (count == size) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean allAssigned(Board board) {
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                String cellValue = board.getCell(i, j).getValue();
                if (cellValue.equals("e"))
                    return false;
            }
        }
        return true;
    }

    private boolean isFinished(Board board) {
        return allAssigned(board) && isConsistent(board);
    }

    private boolean isConsistent(Board board) {
        return checkNumberOfCircles(board) && checkAdjacency(board) && checkIfUnique(board);
    }

    private void drawLine() {
        for (int i = 0; i < board.getSize() * 2; i++) {
            System.out.print("\u23E4\u23E4");
        }
        System.out.println();
    }
}
