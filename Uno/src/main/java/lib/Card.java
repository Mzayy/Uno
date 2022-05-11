package lib;

public class Card {
    private int num; //All global variables only accessible through methods
    private CType type;
    private Color color;

    /**
     * Creates a card with given specifications:
     * @param num The number of the card (0 for wild, 11-12-13 for Draw2-Reverse-Skip respectively)
     * @param type The type of the card
     * @param color The color of the card
     */
    public Card(int num, CType type, Color color){
        this.num = num;
        this.type = type;
        this.color = color;
    }

    /**
     * @return The number of the card
     */
    public int getNum(){
        return num;
    }

    /**
     * @return The type of the card
     */
    public CType getCType() {
        return type;
    }

    /**
     * @return The color of the card
     */
    public Color getColor() {
        return color;
    }

    /**
     * Summarizes card details in string
     * @return The Card in a string format
     */
    @Override
    public String toString(){
        if((num == 0) || (num > 10))
            return type.name() + " " + color.name();
        return type.name() + " " + num + " " + color.name();
    }
}
