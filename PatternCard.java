public class PatternCard {
    private int points;
    private int index;
    private char pattern[][] = new char[3][3];

    public void PatternCard(){
        points = 0;
        index = 0;
    }

    public void PatternCard(int p, int i, char[][] pt){
        points = p;
        index = i;
        pattern = pt;
    }

    public int getPoints(){
        return points;
    }

    public int getIndex(){
        return index;
    }

    public char[][] getPattern(){
        return pattern;
    }

    public void setPoints(int p){
        points = p;
    }

    public void setIndex(int i){
        index = i;
    }

    public void setPattern(char[][] p){
        pattern = p;
    }

    public String toString(){
        return "The card index is " + index + " and is worth " + points +  " points.";
    }

}
