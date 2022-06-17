import javax.swing.*;

public class BlockCard {
    private String color;
    private String other;
    private ImageIcon img;

    public BlockCard(){
        color = "";
        other = "";
        img = findImage();
    }


    public BlockCard(String c){
        color = c;
        other = findOther();
        img = findImage();
    }

    public String getColor(){
        return color;
    }

    public String getOther(){
        return other;
    }
    
    public ImageIcon getImage(){
        return img;
    }

    public void setColor(String c){
        color = c;
        other = findOther();
    }

    public void setOther(String o){
        other = o;
    }

    public void setImage(ImageIcon i){
        img = i;
    }

    public String toString(){
        return "The card color is "+ color;
    }

    public void switchColor(){
        String temp = color;
        color = other;
        other = temp;

        img = findImage();
    }

    public String findOther(){
        String[] list1 = {"yellow","orange","purple","grey"};
        String[] list2 = {"black","red","blue","green"};

        int index = 0;
        String ans = "";
        while (ans.equals("")){
            String color1 = list1[index];
            String color2 = list2[index];

            if (color1.equals(color))
                ans = color2;
            else if (color2.equals(color))
                ans = color1;

            index++;
        }

        return ans;
    }

    public ImageIcon findImage(){
        return createImageIcon("pics/" + color + ".jpg");
    }

    protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = XuShiftingStones.class.getResource(path);
		if (imgURL != null)
			return new ImageIcon(imgURL);
		else
			return null;
	}

    

}