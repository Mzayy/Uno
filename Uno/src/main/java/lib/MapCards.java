package lib;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class MapCards {
    int height;
    int width;
    int getWidth;

    ArrayList<Card> hand;
    ArrayList<Player> players;
    Map<Card, Rectangle> mapCards;

    /** Constructor defining all needed variables
     *
     * @param height height of cards
     * @param hand human hand of cards
     * @param getWidth width of graphical window
     * @param width width of card
     * @param mapCards Map of rectangles to cards
     * @param players List of players in game
     */
    MapCards(int height, ArrayList<Card> hand, int getWidth, int width, Map<Card, Rectangle> mapCards, ArrayList<Player> players){
        this.height = height;
        this.width = width;
        this.hand = hand;
        this.getWidth = getWidth;
        this.mapCards = mapCards;
        this.players = players;
    }

    /**
     * Maps all humans cards to their graphical rectangles
     */
    void mapHumanCards(){
        int cardHeight = (height - 20) / 3;
        int cardWidth = (int) (cardHeight * 0.6);
        int xDelta = (int)(cardWidth / 1.2);
        int xPos = (int) ((getWidth / 2) - (cardWidth * (hand.size() / 2.5)));
        int yPos = (height - 20) - cardHeight;
        if(hand.size()> 10) {
            cardWidth = (int) (width / hand.size());
            cardHeight = (int)(cardWidth/.6);
            xDelta = (int)(cardWidth/1.2);
            if (xPos < 0)
                xPos =0;
        }
        for (Card card : hand) {
            Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
            mapCards.put(card, bounds);
            xPos += xDelta;
        }
    }

    /**
     * Maps all the first bot's cards to their graphical rectangles
     */
    void mapBot1Cards(){
        int cardWidth = (getWidth - 20) / 10;
        int cardHeight = (int) (cardWidth * 0.6);;
        int yDelta = (int)(cardHeight / 1.2);
        int xPos = (int) ((10));
        int yPos = (int) ((height / 2) - (cardHeight * (players.get(1).getHand().size() / 2.5)));

        if(players.get(3).getHand().size()> 10) {
            if (xPos < 0)
                xPos =0;
        }
        for (Card card: players.get(1).getHand()){
            Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
            mapCards.put(card, bounds);
            yPos += yDelta;
        }
    }

    /**
     * Maps all the second bot's cards to their graphical rectangles
     */
    void mapBot2Cards(){
        int yPos =  20;
        int cardHeight = (height - 20) / 5;
        int cardWidth = (int) (cardHeight * 0.6);
        int xDelta = (int)(cardWidth / 1.2);
        int xPos = (int) ((getWidth / 2) - (cardWidth * (players.get(2).getHand().size() / 2.5)));

        if(players.get(2).getHand().size()> 10) {
            xDelta = (int)(cardWidth/1.2);
            if (xPos < 0)
                xPos =0;
        }
        for (Card card: players.get(2).getHand()){
            Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
            mapCards.put(card, bounds);
            xPos += xDelta;
        }
    }

    /**
     * Maps all the third bot's cards to their graphical rectangles
     */
    void mapBot3Cards(){
        int cardWidth = (height- 20) / 5;
        int cardHeight = (int) (cardWidth * 0.6);
        int yDelta = (int)(cardHeight / 1.2);
        int yPos = (int) ((height / 2) - (cardHeight * (players.get(3).getHand().size() / 2.5)));

        int xPos = (int) ((width -10 - cardWidth));
        if(players.get(3).getHand().size()> 10) {
            if (xPos < 0)
                xPos =0;
        }
        for (Card card: players.get(3).getHand()){
            Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
            mapCards.put(card, bounds);
            yPos += yDelta;
        }
    }
}
