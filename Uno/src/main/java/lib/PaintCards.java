package lib;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class PaintCards {
    int width;
    int height;
    int cardHeight;
    int getHeight;

    Map<Card, Rectangle> mapCards;
    ArrayList<Player> players;

    JPanel frame;
    Graphics2D g2d;

    /** Constructor for relevant variables
     *
     * @param frame Graphical frame to draw on
     * @param g2d Graphical frame to draw on
     * @param width width of graphical window
     * @param height height of graphical window
     * @param cardHeight height of card
     * @param getHeight height of card
     * @param players All players in the game
     * @param mapCards Map of graphical rectangles to their card
     */
    PaintCards(JPanel frame, Graphics2D g2d, int width, int height, int cardHeight, int getHeight, ArrayList<Player> players, Map<Card, Rectangle> mapCards){
        this.height = height;
        this.frame = frame;
        this.g2d = g2d;
        this.width = width;
        this.cardHeight = cardHeight;
        this.getHeight = getHeight;
        this.mapCards = mapCards;
        this.players = players;
    }

    /** Draws every player's hand
     *
     * @param g2d Graphical pane to draw on
     * @param pc Paint cards object to draw with
     */
    void drawHands(Graphics2D g2d, PaintCards pc){
        for(int i = 0; i < 4; i++) {
            for (Card card : players.get(i).getHand()) {
                Rectangle bounds = mapCards.get(card);
                System.out.println(bounds);
                if (bounds != null) {
                    Graphics2D copy = (Graphics2D) g2d.create();
                    try {
                        pc.paintCard(copy, card, bounds, i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    copy.dispose();
                }
            }
        }
    }

    /** Helper to draw each card in a hand
     *
     * @param g2d Graphical pane to draw on
     * @param card Card to draw
     * @param bounds Bounds of card in play
     * @param num Index of player who has the card
     * @throws IOException
     */
    protected void paintCard(Graphics2D g2d, Card card, Rectangle bounds, int num) throws IOException {
        g2d.translate(bounds.x, bounds.y + 10);
        g2d.setClip(0, 0, bounds.width - 5, bounds.height - 5);
        Image image;
        if(num == 0) {
            image = loadImage(card);
        }
        else {
            File url = new File("cards/large/back" + num + ".png");
            image = ImageIO.read(url);
        }
        AffineTransform at=new AffineTransform();
        at.setToScale((float)bounds.width/image.getWidth(null),(float)bounds.height/image.getHeight(null));
        g2d.drawImage(image, at, frame);
    }

    /**
     *
     * @param top Card in play
     */
    public void drawTop(Card[] top){
        if(top != null) {
            Image image = loadImage(top[0]);
            cardHeight = (int)((image.getHeight(null) - 20) / 2);
            int cardWidth = (int) (cardHeight * 0.6);
            int xPos = (int) (width/2)-cardWidth/2;
            int yPos = (getHeight/2 - cardHeight/2)-40;
            AffineTransform at = new AffineTransform();
            image = image.getScaledInstance(cardWidth, cardHeight, Image.SCALE_AREA_AVERAGING);
            at.setToTranslation(xPos, yPos);
            g2d.drawImage(image, at, frame);
        }
    }

    /**
     *
     * @param hasWon Whether the game has terminated, and who has won (-1 if no one)
     */
    public void paintWinningDialogue(int hasWon){
        if(hasWon != -1){
            if (hasWon == 0) {
                String text = "YOU WON!";
                JOptionPane.showMessageDialog(null, "<html><font size='900'>" + text + "</font></html>");
            }
            else {
                String text = "Bot " + hasWon + " Won";
                JOptionPane.showMessageDialog(null, "<html><font size='900'>" + text + "</font></html>");
            }
            frame.removeAll();
        }
    }

    /** Draws the indicator to let the player know who's turn it is
     *
     * @param turn The index of the current player's turn
     * @param g Graphical frame to draw on
     */
    public void drawTurnIndicator(int turn, Graphics g){
        Graphics2D dot = (Graphics2D) g.create();
        dot.setColor(Color.red);
        int dotw = 30;
        int doth = 30;

        switch (turn){
            case 0:
                int yPos = (getHeight- 20) - cardHeight;
                dot.fillOval(width/2 - dotw/2, yPos - (int)(doth/1.2), dotw, doth);
                break;
            case 1:
                dot.fillOval((int)(width/9.2), (int)(height/2), dotw, doth);
                break;
            case 2:
                dot.fillOval(width/2 - dotw/2, (int)(height*.23), dotw, doth);
                break;
            case 3:
                dot.fillOval((int)(width*.875), (int)(height/2), dotw, doth);
                break;
        }
    }

    /**
     *
     * @param card Card to display
     * @return Filename of card
     */
    private String getFileName(Card card){
        if(card.getCType() == CType.Number)
            return "cards/large/"+card.getColor().toString().toLowerCase()+'_'+card.getNum()+"_large.png";
        else if(card.getCType() == CType.WildCard)
            return "cards/large/wild_colora_changer_large.png";
        else if(card.getCType() == CType.Draw2)
            return "cards/large/"+card.getColor().toString().toLowerCase()+"_picker_large.png";
        else if(card.getCType() == CType.Reverse)
            return "cards/large/"+card.getColor().toString().toLowerCase()+"_reverse_large.png";
        else if(card.getCType() == CType.Skip)
            return "cards/large/"+card.getColor().toString().toLowerCase()+"_skip_large.png";
        else if(card.getCType() == CType.Draw4)
            return "cards/large/wild_pick_four_large.png";
        return "";
    }

    /**
     *
     * @param card Card to load
     * @return The png as an Image object
     */
    public Image loadImage(Card card) {
        Image img;
        File url = new File(getFileName(card));
        if (url == null) {
            System.err.println("Couldn't find file: " + getFileName(card));
        } else {
            try {
                img = ImageIO.read(url);
                return img;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
