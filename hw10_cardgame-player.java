import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Player implements Comparable<Player>{ 
    
    public static final SuitOrder SUIT_ORDER = new SuitOrder();

    private static final FaceOrder FACE_ORDER = new FaceOrder();
    
    private static final Type_Order TYPE_ORDER = new Type_Order();
    
    enum Types{
        fullhouse, flush, straight, tpair, opair, high
    }
    private Types cardtype;
    public CardReq req = new CardReq();
    
    
    public void set_types(){
        Map<String, Integer> faces = req.FaceMaps();
        if(faces.size()==2){
            if(req.Triples().size()!=0){
                cardtype = Types.fullhouse;
                return;
            } cardtype = Types.tpair;              
           
            
        }
        else if(faces.size()==3){
            if(req.Pairs().size()!=0){
                cardtype = Types.tpair;
                return;
            }
            cardtype = Types.high;
        }
        else if(faces.size()==4){
            cardtype = Types.opair;
        }
        else if(faces.size()==5){
//  flushes
            if(req.Get_SuitSet().size()==1){
                cardtype = Types.flush;
                return;
            }
            // if 5 continuous cards are in a row then its a straight
            String[] straights = {"A","K","Q","J","10","9","8","7","6","5","4","3","2","A"};
            Set<String> faceSet = faces.keySet();
            for(int i = 0; i < straights.length - 5 ; i++){
                Set<String> straightOne = new HashSet<String>();
                for(int j = i ; j < i + 5 ; j++){
                    straightOne.add(straights[j]);
                }
                if (faceSet.containsAll(straightOne)) {
                   cardtype = Types.straight;
                   return;
                }
            }
            cardtype = Types.high;
        }
        
    }

    private Card[] cards = new Card[5];
    private String name;
     
    // DO NOT MODIFY THIS
    public Player(String name) {
        this.name = name;
    }
     
    // DO NOT MODIFY THIS
    public String getName() {
        return this.name;
     }
     
    // DO NOT MODIFY THIS
    public void setCards(Card[] cards) {
        this.cards = cards;
    }
    public Types get_types(){
        return this.cardtype;
    }
     
    // TODO 
    public int compareTo(Player that) {
        // complete this function so the Player can be sorted according to the cards he/she has.
        this.set_types();
        that.set_types();
        int cons = TYPE_ORDER.compare(this.get_types(),that.get_types());
        if(cons!=0){
            return cons;
        }else{
            switch(this.get_types()){
                case fullhouse:
//                    String p1 = Collections.max(this.req.Triples(), FACE_ORDER);
//                    String p2 = Collections.max(that.req.Triples(), FACE_ORDER);
//                    cons = FACE_ORDER.compare(p1, p2);
                    return cons;
                case flush:
                    
//                case high:
//                    String hi1 = Collections.max(this.req.Singles(), FACE_ORDER);
//                    String hi2 = Collections.max(that.req.Singles(), FACE_ORDER);
//                    cons = FACE_ORDER.compare(hi1, hi2);
//                    if(cons!=0){
//                        return cons;
//                    } else{
//                        Set<String> his1 = this.req.Get_SuitSet(hi1);
//                        Set<String> his2 = that.req.Get_SuitSet(hi2);
//                        cons = SUIT_ORDER.compare(Collections.max(his1, SUIT_ORDER), Collections.max(his2, SUIT_ORDER));
//                        return cons;
//                    }
//                case tpair:
//                    return-1;
                    
            }
        }
        
        return 1;
    }
    private static class SuitOrder implements Comparator<Card> {

        private static List<String> orders;
        SuitOrder() {
            String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
            orders = new ArrayList<String>();
            for (String suit : suits) {
                orders.add(suit);
            }
        }

        @Override
        public int compare(Card c1, Card c2) {
            int suitc1 = orders.indexOf(c1.getSuit());
            int suitc2 = orders.indexOf(c2.getSuit());
            if (suitc1 < suitc2) return 1;
            else if (suitc1 > suitc2) return -1;
            else return 0;
        }

    }

    private static class FaceOrder implements Comparator<Card> {
        private static List<String> orders;
        FaceOrder() {
            String[] faces = {"A","K","Q","J","10","9","8","7","6","5","4","3","2"};
            orders = new ArrayList<String>();
            for (String face : faces) {
                orders.add(face);
            }
        }

        @Override
        public int compare(Card c1, Card c2) {
            int facec1 = orders.indexOf(c1.getFace());
            int facec2 = orders.indexOf(c2.getFace());
            if (facec1 < facec2) return 1;
            else if (facec1 > facec2) return -1;
            else return 0;
        }
    }
    private static class Type_Order implements Comparator<Types> {
        public static List<Types> orders;
       private Type_Order(){
        Types[] CardTypes ={
            Types.fullhouse,
            Types.flush,
            Types.straight,
            Types.tpair,
            Types.opair,
            Types.high
        };
        orders = new ArrayList<Types>();
        for(Types cardType : CardTypes){
            orders.add(cardType);
        }
                
    }
        @Override
        public int compare(Types t, Types t1) {
            int cardt1 = orders.indexOf(t);
            int cardt2 = orders.indexOf(t1);
            if (cardt1 < cardt2) return 1;
            else if (cardt1 > cardt2) return -1;
            else return 0;
            
        }
        
    }
    public class CardReq{
        public Map<String, Integer> FaceMaps(){
            Map<String,Integer> FMaps = new HashMap<String,Integer>();
            for(Card c:cards){
                if (FMaps.containsKey(c.getFace()))
                    FMaps.put(c.getFace() , FMaps.get(c.getFace())+1);
                else
                    FMaps.put(c.getFace() , 1);
            }
            return FMaps;
        }
        public Set<String> Get_SuitSet(){
            Set<String> suitSet = new HashSet<String>();
            for (Card c:cards){
                suitSet.add(c.getSuit());
            }
            return suitSet;
        }
        public Set<String> Get_SuitSet(String face){
            Set<String> suitSet = new HashSet<String>();
            for (Card c:cards){
                if (face.equals(c.getFace()))
                    suitSet.add(c.getSuit());
            }
            return suitSet;
        }
        public Set<String> Singles(){
            Set<String> single = new HashSet<String>();
            Map<String,Integer> faces = FaceMaps();
            for (Map.Entry<String,Integer> entry:faces.entrySet()) {
                if (entry.getValue() == 1)
                    single.add(entry.getKey());
            }
            return single;
        }
        public Set<String> Pairs(){
            Set<String> pair = new HashSet<String>();
            Map<String,Integer> faces = FaceMaps();
            for (Map.Entry<String,Integer> entry:faces.entrySet()) {
                if (entry.getValue() == 2)
                    pair.add(entry.getKey());
            }
            return pair;
        }
        public Set<String> Triples(){
            Set<String> triple = new HashSet<String>();
            Map<String,Integer> faces = FaceMaps();
            for (Map.Entry<String,Integer> entry:faces.entrySet()) {
                if (entry.getValue() == 3)
                    triple.add(entry.getKey());
            }
            return triple;
        }
        
    }
}
