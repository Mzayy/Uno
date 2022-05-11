package lib;
import java.util.*;

public class Deck {
    private List<Card> deck; //deck should only be interacted with through methods
    private List<Card> used;
    /**
     * Inititializes the deck and populates it with all cards
     */
    public Deck() {
        deck = new ArrayList<Card>();
        populateDeck();
    }

    /**
     * Adds all possible cards to the deck
     */
    private void populateDeck() {

        for(Color col: Color.values()) {
            if(col != Color.Wild) {
                for (int i = 0; i < 10; i++) {
                    deck.add(new Card(i, CType.Number, col));
                }
                deck.add(new Card(11, CType.Draw2, col));
                deck.add(new Card(12, CType.Reverse, col));
                deck.add(new Card(13, CType.Skip, col));
            }
        }
        deck.add(new Card(-1, CType.WildCard, Color.Wild));
        deck.add(new Card(-1, CType.WildCard, Color.Wild));
        deck.add(new Card(-1, CType.Draw4, Color.Wild));
        deck.add(new Card(-1, CType.Draw4, Color.Wild));
        Collections.shuffle(deck);
    }


    /**
     * Simulates drawing
     * @return The top card of the deck
     */
    public Card draw(){
//        if(deck.size() == 0)
//            deck = used;
        Card ret = deck.get(0);
        deck.remove(0);
//        used.add(ret);
        return ret;
    }

    /**
     * Simple check to see if deck is empty
     * @return Whether the deck is empty
     */
    public boolean isEmpty(){
        return deck.isEmpty();
    }

    /**
     * Return string with each card on its own line in sequential order
     * @return The deck in a string
     */
    @Override
    public String toString(){
        String str = "";
        for(Card cur: deck)
            str += cur.toString() + "\n";
        return str;
    }

    public int size(){
        return deck.size();
    }

    public boolean contains(Card c){
        return deck.contains(c);
    }
}