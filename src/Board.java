public class Board {
  private State[][] cells;
  private int size;

  public Board(int size) {
    this.size = size;
    this.cells = new State[size][size];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        cells[i][j] = new State("e");
      }
    }
  }

  public void setCell(int x, int y, String value, boolean isDefault) {
    this.cells[x][y].set(value, isDefault);
  }

  public State getCell(int x, int y) {
    return cells[x][y];
  }

  public int getSize() {
    return size;
  }

  public void print() {
    char blackCircle = '\u26AB';
    char whiteCircle = '\u26AA';
    char blackSquare = '\u2B1B';
    char whiteSquare = '\u2B1C';

    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[i].length; j++) {
        State cell = cells[i][j];
        if (cell.getValue().equals("e")) {
          System.out.print("--");
        } else if (cell.getValue().equals("w") && cell.isDefault()) {
          System.out.print(whiteSquare);
        } else if (cell.getValue().equals("w") && !cell.isDefault()) {
          System.out.print(whiteCircle);
        } else if (cell.getValue().equals("b") && cell.isDefault()) {
          System.out.print(blackSquare);
        } else if (cell.getValue().equals("b") && !cell.isDefault()) {
          System.out.print(blackCircle);
        }
        System.out.print("  ");
      }
      System.out.println();
      System.out.println();
    }
  }

  public State[][] getCells() {
    return cells;
  }

  public Position getPositionOfCell(State state) {
    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[i].length; j++) {
        if (cells[i][j].equals(state)) {
          return new Position(i, j);
        }
      }
    }
    return null;
  }
}
