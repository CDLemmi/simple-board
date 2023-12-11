import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("simple board");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(701,700);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        Board board= new Board();
        frame.add(board, BorderLayout.CENTER);

        frame.setSize(700,700);
    }
}