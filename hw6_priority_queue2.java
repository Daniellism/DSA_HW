import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;


public class Hand implements Comparable<Hand> {

    // sorted by Card value are recommended but not necessary
    private Card[] cards; 
    enum CardType {
        full_house , flush , straight , two_pair , one_pair , high
    }
    private CardType cardtype;
    public cardReq cardreq = new cardReq();
    private static CardTypeOrder CTO = new CardTypeOrder();
    

    // TODO, Judge System will call this constructor once for each hand
    public Hand(Card[] cards){
        this.cards = cards;
        this.setCardType();
//        return;
    }
    public CardType getCardType(){
        return cardtype;
    }

    // TODO
    public int compareTo(Hand that) {
        int result = CTO.compare(this.getCardType(), that.getCardType());
        if (result != 0){
            return result;
        } else {
            // broke ties
            switch (this.getCardType()) {
                case full_house:
                    Card c1 = Collections.max(this.cardreq.Triples());
                    Card c2 = Collections.max(that.cardreq.Triples());
                    return c1.compareTo(c2);

                case two_pair:
                case one_pair:
                    c1 = Collections.max(this.cardreq.Doubles());
                    c2 = Collections.max(that.cardreq.Doubles());
                    return c1.compareTo(c2);

                case high:
                    c1 = Collections.max(this.cardreq.Singles());
                    c2 = Collections.max(that.cardreq.Singles());
                    return c1.compareTo(c2);
                case straight:
                case flush:
                    c1 = Collections.max(this.cardreq.Singles());
                    c2 = Collections.max(that.cardreq.Singles());
                    return c1.compareTo(c2);
            }
        }
        return 0;
       
    }

      // Do not modified this function
    public Card[] getCards() { return this.cards; }
    
    private static class CardTypeOrder implements Comparator<CardType> {
        private static List<CardType> order;
        CardTypeOrder() {
            CardType[] cardTypes = {
                    CardType.full_house,
                    CardType.flush,
                    CardType.straight,
                    CardType.two_pair,
                    CardType.one_pair,
                    CardType.high
            };
            order = new ArrayList<CardType>();
            for (CardType cardType : cardTypes) {
                order.add(cardType);
            }
        }

        @Override
        public int compare(CardType cardType1, CardType cardType2) {
            int cardType_1 = order.indexOf(cardType1);
            int cardType_2 = order.indexOf(cardType2);
            if (cardType_1 < cardType_2) return 1;
            else if (cardType_1 > cardType_2) return -1;
            else return 0;
        }
    }
    private void setCardType(){
        Map<String,Integer> faces = cardreq.getFaceCount();
        if (faces.size() == 2) {
            // (4,1) or (3,2)
            if (cardreq.Triples().size() != 0) {
                cardtype = CardType.full_house;
                return;
            }
            cardtype = CardType.high;
        } else if (faces.size() == 3){
            // (3,1,1) or (2,2,1)
            if (cardreq.Doubles().size() != 0) {
                cardtype = CardType.two_pair;
                return;
            }
            cardtype = CardType.high;
        } else if (faces.size() == 4){
            // (2,1,1,1)
            cardtype = CardType.one_pair;
        } else if (faces.size() == 5){
            // check for flush
            if (cardreq.getSuitSet().size() == 1) {
               cardtype = CardType.flush;
                return;
            }
            // check for straight
            String[] straights = {"A","K","Q","J","10","9","8","7","6","5","4","3","2","A"};
            Set<String> faceSet = faces.keySet();
            for(int i = 0; i < straights.length - 5 ; i++){
                Set<String> straightOne = new HashSet<String>();
                for(int j = i ; j < i + 5 ; j++){
                    straightOne.add(straights[j]);
                }
                if (faceSet.containsAll(straightOne)) {
                    cardtype = CardType.straight;
                    return;
                }
            }
            // high card otherwise
            cardtype = CardType.high;
        }
    }
    private class cardReq{
        public Map<String , Integer> getFaceCount(){
            Map<String,Integer> faces = new HashMap<String,Integer>();
            for(Card c:cards){
                if (faces.containsKey(c.getFace())){
                    faces.put(c.getFace() , faces.get(c.getFace())+1);
                }
                else
                    faces.put(c.getFace() , 1);
            }
            return faces;
        }
        public Set<String> getSuitSet(){
            Set<String> suitSet = new HashSet<String>();
            for (Card c:cards){
                suitSet.add(c.getSuit());
            }
            return suitSet;
        }
        public Set<String> getSuitSet(String fsample){
            Set<String> suitSet = new HashSet<String>();
            for (Card c:cards){
                if (fsample.equals(c.getFace()))
                    suitSet.add(c.getSuit());
            }
            return suitSet;
        }
        public Set<Card> Singles(){
            Set<Card> single = new HashSet<Card>();
            Map<String,Integer> faces = getFaceCount();
            for (Card c:cards){
                if (faces.get(c.getFace()) == 1)
                    single.add(c);
            }
            return single;
        }
         public Set<Card> Doubles(){
            Set<Card> pairs = new HashSet<Card>();
            Map<String,Integer> faces = getFaceCount();
            for (Card c:cards){
                if (faces.get(c.getFace()) == 2)
                    pairs.add(c);
            }
            return pairs;
        }
         public Set<Card> Triples(){
            Set<Card> threes = new HashSet<Card>();
            Map<String,Integer> faces = getFaceCount();
            for (Card c:cards){
                if (faces.get(c.getFace()) == 3){
                    threes.add(c);
                }
            }
            return threes;
         }
        
        
    }
    
}
