import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        File input = new File("inputs/input3.txt");
        try {
            Scanner reader = new Scanner(input);
            int boardSize = reader.nextInt();
            Board board = new Board(boardSize);

            int m = reader.nextInt();
            for (int i = 0; i < m; i++) {
                int x = reader.nextInt();
                int y = reader.nextInt();
                String value = switch (reader.nextInt()) {
                    case 0 -> "w"; // white
                    case 1 -> "b"; // black
                    default -> null;
                };

                board.getCell(x, y).set(value, true);

            }
            Binairo binairo = new Binairo(board);
            binairo.start();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
