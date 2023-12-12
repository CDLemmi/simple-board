import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Board extends JPanel{

    enum Type {EMPTY, BLACK, RED, AVAILABLE}

    Type[] types = new Type[49];

    HashMap<Type, BufferedImage> textures = new HashMap<>();

    private int selected = 0;

    Board() {
        for(int i = 0; i < 49; i++) {
            types[i] = Type.EMPTY;
        }
        types[0] = Type.RED;
        types[6] = Type.BLACK;
        types[42] = Type.BLACK;
        types[48] = Type.RED;
        loadTextures();
        setupMouseListener();
    }

    private void handleClick(int i) {
        switch(types[i]) {
            case EMPTY -> resetAvailable();
            case AVAILABLE -> {
                types[i] = types[selected];
                types[selected] = Type.EMPTY;
                resetAvailable();
            }
            case BLACK, RED -> {
                resetAvailable();
                selected = i;
                int[] a = new int[] {-7,-1,1,7};
                for(int j : a) {
                    int k = j + i;
                    boolean isInBoard = k <= 48 && k >= 0;
                    boolean doesNotWrapRight = !(j==1 && k%7 == 0);
                    boolean doesNotWrapLeft = !(j==-1 && k%7 == 6);
                    if(isInBoard && doesNotWrapLeft && doesNotWrapRight
                            && types[k] == Type.EMPTY )
                        types[k] = Type.AVAILABLE;
                }
            }
        }
        paintComponent(getGraphics());
    }

    private void resetAvailable() {
        for(int i = 0; i < 49; i++) {
            if(types[i] == Type.AVAILABLE) {
                types[i] = Type.EMPTY;
            }
        }
    }

    private void setupMouseListener() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int w = getWidth() / 7;
                int h = getHeight() / 7;
                int i = (x/w) + ((y/h)*7);
                handleClick(i);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void loadTextures() {
        String path = "res";
        System.out.println(new File("").getAbsolutePath());
        for(Type type : Type.values()) {
            try {
                BufferedImage image = ImageIO.read(new File(path + "\\" + type.toString() + ".png"));
                textures.put(type, image);
            } catch(IOException e ) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        int w = getWidth() / 7;
        int h = getHeight() / 7;
        for(int i = 0; i < 49; i++) {
            g.drawImage(textures.get(types[i]), (i%7)*w, (i/7)*h, w, h, null);
        }
    }
}
