package lib;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.awt.Color;
import javax.swing.*;
import java.util.List;
import javax.swing.JFrame;

public class Renderer {
    public static void main(String[] args){
        Deck deck = new Deck();
        Player player = new Player("Mo", deck);

        ArrayList<Player> cur = new ArrayList<>();
        cur.add(player);
        for(int i = 1; i < 4; i++){
            cur.add(new Player("bot" + i, deck));
        }
        new Renderer(cur, deck);
    }

    /** This function sets into motion the entire front end
     *
     * @param players The players in the game
     * @param deck The main deck that will be drawn from
     */
    public Renderer(ArrayList<Player> players, Deck deck) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setBackground(Color.darkGray);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.add(new GamePane(players, deck, frame));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    /**
     * GamePane creates all Visuals
     */
    public class GamePane extends JPanel {
        private int height = 800;
        private int width = 1600;
        private int[] player_turn = {0};
        private int hasWon = -1;
        private int[] order = {1};

        private Map<Card, Rectangle> mapCards;
        private JFrame frame;

        private Card selected;
        private Card[] top = {null};
        private List<Card> hand;
        private Deck deck;
        private ArrayList<Player> players;

        /** This function essentially maps all graphical changes to clicking different cards
         *
         * @param player Players in the game
         * @param deck Deck to be drawn from
         * @param fr Graphics frame that will be drawn to
         * @throws InterruptedException
         */
        public GamePane(ArrayList<Player> player, Deck deck, JFrame fr) throws InterruptedException {
            frame = fr;
            this.deck = deck;
            this.hand = player.get(0).getHand();
            players = player;
            mapCards = new HashMap<>(hand.size());
            top[0] = deck.draw();
            while(top[0].getNum()==-1){
                top[0] = deck.draw();
            }

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selected != null) {
                        Rectangle bounds = mapCards.get(selected);
                        repaint();
                    }
                    if (top[0] == null) {
                        top[0] = deck.draw();
                        while(top[0].getNum()==-1){
                            top[0] = deck.draw();
                        }
                    }
                    selected = null;

                    for (Card card : hand) {
                        Rectangle bounds = mapCards.get(card);
                        if (bounds.contains(e.getPoint())) {
                            selected = card;
                            boolean valid = false;
                            if (player.get(0).getAvailableCards(top[0]).contains(selected) && player_turn[0] == 0) {
                                top[0] = selected;
                                topAndRemove(player.get(0), selected, bounds);
                                valid = true;
                            } else if (player.get(0).getAvailableCards(top[0]).size() == 0 && player_turn[0] == 0) {
                                hand.add(deck.draw());
                            }
                            else if(player_turn[0] != 0){
                                while(player.get(player_turn[0]).getAvailableCards(top[0]).size() == 0) {
                                    player.get(player_turn[0]).draw(deck);
                                }
                                Card cur = player.get(player_turn[0]).getAvailableCards(top[0]).get(0);
                                topAndRemove(player.get(player_turn[0]), cur, bounds);
                                valid = true;
                            }
                            SpecialCardActions.getSpecial(player_turn, order, players, deck, top);
                            if(valid) {
                                player_turn[0] = (player_turn[0] + order[0]) % players.size();
                                if (player_turn[0] < 0)
                                    player_turn[0] += players.size();
                            }
                            invalidate();
                            repaint();
                            break;
                        }
                    }
                }
            });
        }

        /**
         * @return Desired dimensions
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(width, height);
        }

        /**
         * Invalidate() resets the image mapping and is essential for updating the graphics
         */
        @Override
        public void invalidate() {
            super.invalidate();
            mapCards.clear();
            MapCards mc = new MapCards(getHeight(), (ArrayList<Card>) players.get(0).getHand(), getWidth(), width, mapCards, players);
            mc.mapHumanCards();
            //Player 1 on left side of screen
            mc.mapBot1Cards();
            //Player 2 on top of screen
            mc.mapBot2Cards();
            //Player 3 on left side of screen
            mc.mapBot3Cards();

        }

        /** paintComponent() paints every element of the game including the hands, turn indicator, and current card in play
         *
         * @param g Graphics element to draw with
         */
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            int cardHeight = (getHeight() - 20) / 3;

            PaintCards paintCards = new PaintCards(this, g2d, width, height, cardHeight, getHeight(), players, mapCards);

            //Indicates who's turn it is
            paintCards.drawTurnIndicator(player_turn[0], g);
            //Print dialogue box if a winner has been found
            paintCards.paintWinningDialogue(hasWon);
            //Draws the current card in play
            paintCards.drawTop(top);
            //Draws every player's hand
            paintCards.drawHands(g2d, paintCards);
            g2d.dispose();
        }

        /** topAndRemove is a basic helper function that removes a selected card and puts it as the card in play
         *
         * @param player Player to remove card from
         * @param selected Card player selected to  play
         * @param bounds The graphical coordinates of the card
         */
        void topAndRemove(Player player, Card selected, Rectangle bounds){
            top[0] = selected;
            player.getHand().remove(selected);
            mapCards.remove(bounds);
        }
    }
}