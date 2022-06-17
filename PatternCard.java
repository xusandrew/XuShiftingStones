import javax.swing.*;

public class PatternCard {
    private int points;
    private int index;
    private String pattern[][];
    private ImageIcon img;

    public PatternCard(){
        points = 0;
        index = 0;
    }

    public PatternCard(int i){
        index = i;
    
        pattern = Patterns.data[i];
        points = Patterns.points[i];
        
    }

    public int getPoints(){
        return points;
    }

    public int getIndex(){
        return index;
    }

    public String[][] getPattern(){
        return pattern;
    }

    public ImageIcon getImage(){
        return img;
    }

    public void setPoints(int p){
        points = p;
    }

    public void setIndex(int i){
        index = i;
    }

    public void setPattern(String[][] p){
        pattern = p;
    }

    public void setImage(ImageIcon i){
        img = i;
    }

    public String toString(){
        return "The card index is " + index + " and is worth " + points +  " points.";
    }

    public ImageIcon findImage(){
        return createImageIcon("pics/pattern" + (index + 1)+ ".png");
    }

    protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = XuShiftingStones.class.getResource(path);
		if (imgURL != null)
			return new ImageIcon(imgURL);
		else
			return null;
	}

}