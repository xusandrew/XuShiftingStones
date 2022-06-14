public class BlockCard {
    private String color;
    private String other;

    public void BlockCard(){
        color = "";
        other = "";
    }

    public void BlockCard(String c){
        color = c;

        String[] list1 = {"yellow","orange","purple","grey"};
        String[] list2 = {"black","red","blue","green"};

        int index = 0;
        while (other.equals("")){
            String color1 = list1[index];
            String color2 = list2[index];

            if (color1.equals(color))
                other = color2;
            else if (color2.equals(color))
                other = color1;
        }
    }

    public String getColor(){
        return color;
    }

    public void setColor(String c){
        color = c;
    }

    public String toString(){
        return "The card color is "+ color;
    }

    public void switch_color(){
        String temp = color;
        color = other;
        other = temp;
    }

}