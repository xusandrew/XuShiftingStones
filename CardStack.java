// Name: Andrew Xu
// Date: 6/10/2022
// Purpose: Stack class for game

public class CardStack {
    private int count;
    private PatternCard[] data = new PatternCard[16];

    public CardStack() {
        count = 0;
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

    public Object pop() {
        count--;
        return data[count];
    }

    public Object peek() {
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
}