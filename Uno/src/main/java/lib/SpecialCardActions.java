package lib;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class SpecialCardActions {
    /** This function enables the special abilities of certain cards
     *
     * @param player_turn Index of player that is currently playing card
     * @param order 1 if clockwise, -1 if counter-clockwise
     * @param players List of all players
     * @param deck Game's deck
     * @param top Card currently in play
     */
    public static void getSpecial(int[] player_turn, int[] order, ArrayList<Player> players, Deck deck, Card[] top){
        int before = player_turn[0];
        int after = updateOrder(player_turn[0], order, players.size());
        String[] possibilities = {"Blue", "Yellow", "Red","Green"};

        if(top[0].getNum()==11){
            if(after != before) {
                players.get(after).draw(deck);
                players.get(after).draw(deck);
            }
        }
        else if(top[0].getNum()==13){
            player_turn[0] = after;
        }
        else if(top[0].getNum()==12){
            order[0]*=-1;
        }
        else if((top[0].getNum()== -1 || top[0].getCType()==CType.Draw4)&& before ==0) {
            JFrame tmp = new JFrame();
            int opt = JOptionPane.QUESTION_MESSAGE;
            String question = "Which color would you  to select?";
            String title = "Choose a color";
            int n = JOptionPane.showOptionDialog(null,question, title, 0, opt, null, possibilities,null);
            wildCardHelper(top, n);
            if(top[0].getCType()==CType.Draw4)
                for(int i = 0; i < 4; i++)
                    players.get(after).draw(deck);
        }
        else if((top[0].getNum()== -1 || top[0].getCType()==CType.Draw4) && before!=0) {
            Random rand = new Random();
            int n = rand.nextInt()%4;
            String color = possibilities[n];
            wildCardHelper(top, n);
            JOptionPane.showMessageDialog(null, "The Color " + color+" was selected");
            if(top[0].getCType()==CType.Draw4)
                for(int i = 0; i < 4; i++)
                    players.get(after).draw(deck);
        }
    }

    /**
     *
     * @param cur Current player index
     * @param order 1 if clockwise, -1 if counter-clockwise
     * @param size number of players
     * @return The index of next player
     */
    private static int updateOrder(int cur, int[] order, int size){
        int after = (cur + order[0])%size;
        if(after<0)
            after+=size;
        return after;
    }

    /**
     *
     * @param top top card in play
     * @param n A number 0-3 detailing the color the wild card player wishes to set
     */
    private static void wildCardHelper(Card[] top, int n){
        switch (n){
            case 0:
                top[0] = new Card(top[0].getNum(), top[0].getCType(), lib.Color.Blue);
                break;
            case 1:
                top[0] = new Card(top[0].getNum(), top[0].getCType(), lib.Color.Yellow);
                break;
            case 2:
                top[0] = new Card(top[0].getNum(), top[0].getCType(), lib.Color.Red);
                break;
            case 3:
                top[0] = new Card(top[0].getNum(), top[0].getCType(), lib.Color.Green);
                break;
        }
    }
}
