package lib;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class GameTest {
    //    Game game;
    @BeforeEach
    void setup(){
//        game = new Game(4);
    }

    @Test
    @DisplayName("generate players cards test")
    void testGetAvailableCards(){
        ArrayList<Card> test = new ArrayList<>();
        Deck deck = new Deck();
        Player tmp = new Player("test", deck);

        test.add(new Card(1,CType.Number, Color.Blue));
        test.add(new Card(5,CType.Number, Color.Red));
        test.add(new Card(6,CType.Number, Color.Red));
        test.add(new Card(7,CType.Number, Color.Red));
        test.add(new Card(5,CType.Number, Color.Yellow));
        Card top = new Card(5,CType.Number, Color.Yellow);
        tmp.setHand(test);
        assertEquals(tmp.getAvailableCards(top).get(0), test.get(1));
        assertEquals(tmp.getAvailableCards(top).get(1), test.get(4));

        test.clear();
        test.add(new Card(1,CType.WildCard, Color.Wild));
        test.add(new Card(5,CType.Number, Color.Red));
        test.add(new Card(6,CType.Number, Color.Red));
        test.add(new Card(7,CType.Number, Color.Red));
        test.add(new Card(5,CType.Number, Color.Yellow));
        top = new Card(5,CType.Number, Color.Yellow);
        tmp.setHand(test);
        assertEquals(tmp.getAvailableCards(top).get(0), test.get(0));
        assertEquals(tmp.getAvailableCards(top).get(1), test.get(1));
        assertEquals(tmp.getAvailableCards(top).get(2), test.get(4));



//        assertEquals(game.players_cards.size(), 4, "there should be 4 players");
//        assertEquals(game.deck.size(), 108 - 4*7, "The new deck should be smaller than original size of 108");

    }

    @Test
    @DisplayName("draw test")
    void testDraw(){
        Deck d = new Deck();
        int initial = d.size();
        Card c = d.draw();
        assertEquals(d.size(),initial-1);
        int curr = initial-1;
        for (int i = 0; i< 5; ++i){
            c = d.draw();
            assertEquals(d.size(),curr-1);
            curr -=1;
        }

        d = new Deck();
        ArrayList<Card> seen = new ArrayList<Card>();
        for (int i = 0; i< 10; ++i){
            seen.add(d.draw());
        }
        for(Card e : seen){
            assertEquals(d.contains(e), false);
        }
    }

    @Test
    @DisplayName("Deck tests")
    void testDeck(){
        Deck d = new Deck();
        int count_wild = 0;
        int count_red = 0;
        int count_draw_4 = 0;
        int count_yellow = 0;
        int count_blue = 0;
        int count_green = 0;
        while (!d.isEmpty()){
            Card c = d.draw();
            if(c.getColor() == Color.Red){
                count_red+=1;
            }else if(c.getColor() == Color.Yellow){
                count_yellow+=1;
            }else if(c.getColor() == Color.Green){
                count_green+=1;
            }else if(c.getColor() == Color.Blue){
                count_blue+=1;
            }else if(c.getColor() == Color.Wild && c.getCType() == CType.Draw4){
                count_draw_4+=1;
            }else if(c.getColor() == Color.Wild && c.getCType() == CType.WildCard){
                count_wild+=1;
            }
        }
        assertEquals(count_blue,13);
        assertEquals(count_red,13);
        assertEquals(count_green,13);
        assertEquals(count_yellow,13);
        assertEquals(count_draw_4,2);
        assertEquals(count_wild,2);
    }

}