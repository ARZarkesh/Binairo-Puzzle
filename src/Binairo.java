public class Binairo {
    private final Board board;

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
        if (isFinished(board)) {
            return true;
        }

        State2 unassingedCell = selectUnassignedCell(board);
        for (String value : unassingedCell.getDomain()) {
            if (isConsistent(board)) {
                unassingedCell.set(value, false);
                if (recursiveBacktrack(board)) {
                    return true;
                }
            }
            unassingedCell.set("e", false);
        }

        return false;
    }

    private State2 selectUnassignedCell(Board board) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                State2 cell = board.getCell(i, j);
                if (cell.getValue().equals("e")) {
                    return cell;
                }
            }
        }
        return null;
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
                    if (board.getCell(i, j).getValue().equals(board.getCell(i, k).getValue())) {
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
