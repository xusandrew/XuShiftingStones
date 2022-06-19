import javax.swing.*;

public class PatternCard {
    private int points;
    private int index;
    private String pattern[][];
    private ImageIcon img;

    public PatternCard() {
        points = 0;
        index = 0;
    }

    public PatternCard(int i) {
        index = i;

        pattern = Patterns.data[i];
        points = Patterns.points[i];

        img = findImage();
    }

    public int getPoints() {
        return points;
    }

    // public int getIndex() {
    // return index;
    // }

    // public String[][] getPattern() {
    // return pattern;
    // }

    public ImageIcon getImage() {
        return img;
    }

    // public void setPoints(int p) {
    // points = p;
    // }

    // public void setIndex(int i) {
    // index = i;
    // }

    // public void setPattern(String[][] p) {
    // pattern = p;
    // }

    // public void setImage(ImageIcon i) {
    // img = i;
    // }

    // public String toString() {
    // return "The card index is " + index + " and is worth " + points + " points.";
    // }

    public ImageIcon findImage() {
        return createImageIcon("pics/pattern" + (index + 1) + ".png");
    }

    public boolean patternMatching(BlockCard[][] board) {
        int a = 4 - pattern.length;
        int b = 4 - pattern[0].length;

        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                if (matchBoardSlice(board, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean matchBoardSlice(BlockCard[][] board, int i, int j) {
        for (int x = 0; x < pattern.length; x++) {
            for (int y = 0; y < pattern[0].length; y++) {

                if (pattern[x][y].equals(""))
                    continue;

                if (!pattern[x][y].equals(board[i + x][j + y].getColor()))
                    return false;
            }
        }
        return true;
    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = XuShiftingStones.class.getResource(path);
        if (imgURL != null)
            return new ImageIcon(imgURL);
        else
            return null;
    }
}