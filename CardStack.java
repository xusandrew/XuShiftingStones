import javax.swing.*;

public class CardStack {
    private int count;
    private PatternCard[] data = new PatternCard[16];
    private ImageIcon img;

    public CardStack() {
        count = 0;
        img = createImageIcon("pics/back.png");
        generateCards();
        shuffle();

    }

    public ImageIcon getImage(){
        return img;
    }

    public void push(PatternCard addMe) {
        data[count] = addMe;
        count++;
    }

    public int size() {
        return count;
    }

    public boolean isFull() {
        return (count == 16);
    }

    public PatternCard pop() {
        count--;
        return data[count];
    }

    public PatternCard peek() {
        return data[count--];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void clear() {
        count = 0;
    }

    public void shuffle() {
        for (int i = 0; i < 100; i++) {
            int r1 = (int) (Math.random() * data.length);
            int r2 = (int) (Math.random() * data.length);
            PatternCard temp = data[r1];
            data[r1] = data[r2];
            data[r2] = temp;
        }
    }

    public void generateCards(){
        int[] card_nums = {0, 8, 4, 3, 0, 1};
        String[][][] pattern = Patterns.data;
        int[] points = Patterns.points;

        while(!this.isFull()){
            int i = (int) (Math.random() * pattern.length);

            if (card_nums[points[i]] > 0){
                push(new PatternCard(i));
                points[i]--;
            }
        }
    }

    public ImageIcon findImage(){
        return createImageIcon("pics/back.png");
    }

    protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = XuShiftingStones.class.getResource(path);
		if (imgURL != null)
			return new ImageIcon(imgURL);
		else
			return null;
	}
}