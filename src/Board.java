public class Board {
  private State2[][] cells;
  private int size;

  public Board(int size) {
    this.size = size;
    this.cells = new State2[size][size];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        cells[i][j] = new State2("e");
      }
    }
  }

  public void setCell(int x, int y, String value, boolean isDefault) {
    this.cells[x][y].set(value, isDefault);
  }

  public State2 getCell(int x, int y) {
    return cells[x][y];
  }

  public int getSize() {
    return size;
  }

  public void print() {
    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[i].length; j++) {
        System.out.print(cells[i][j].getValue() + " ");
      }
      System.out.println();
    }
  }
}
