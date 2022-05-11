package lib;
import java.util.*;

public class Player {
    private static int START_HAND = 7; //All global variables only accessible through methods
    private List<Card> hand;
    private String name;


    /**
     * Initializes Player in game
     * @param name The name inputted (or set by bot)
     * @param deck The deck of the game
     */
    public Player(String name, Deck deck){
        this.name = name;
        this.hand = new ArrayList<Card>();
        for(int i = 0; i < START_HAND; i++)
            hand.add(deck.draw());
    }

    /**
     * Simulates the Player drawing a card
     * @param deck The deck to draw from
     * @return The card that was drawn
     */
    public Card draw(Deck deck){
        Card tmp = deck.draw();
        hand.add(tmp);
        return tmp;
    }

    /**
     * Simple getter for player name
     * @return Player name
     */
    public String getName(){
        return name;
    }

    /**
     * get available cards to play from the hand
     * @param top the top card of the pile
     * @return the list of all available options
     */
    public List<Card> getAvailableCards(Card top){
        List<Card> to_return = new ArrayList<Card>();
        for(Card c : hand){
            if(c.getColor() == top.getColor() || c.getNum() == top.getNum() || c.getColor() == Color.Wild){
                to_return.add(c);
            }
        }
        return to_return;
    }

    /**
     * Gets player's hand
     * @return The player's hand
     */
    public List<Card> getHand(){
        return hand;
    }

    public void setHand(ArrayList<Card> replace){
        hand = replace;
    }



    /**
     * Checks if the player has won
     * @return Whether the player's hand is empty
     */
    public boolean hasWon(){
        return hand.isEmpty();
    }

    /**
     * Summarizes player in string
     * @return A string containing name and Hand
     */
    @Override
    public String toString(){
        String str = "Name: " + name + "\n Hand: ";
        for(Card cur: hand)
            str += cur.toString();
        return str;
    }

    public void addCard(Card c){

    }
}