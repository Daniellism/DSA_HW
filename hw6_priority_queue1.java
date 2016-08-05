import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class HandPQ {
    
        int p;
        Hand[] pq;
    HandPQ (){
        p = 0;
        pq = new Hand[10];
    }
    
    
    public static String Printcard(Card[] c1){
        String total="";
        String comma="";
        for(Card card:c1){
            total+= comma+card.getSuit()+"_"+card.getFace();
            comma =",";
        }
        return total;
    }
    

    public static void main(String[] args) throws Exception {

        try(BufferedReader br = new BufferedReader(new FileReader(args[0]))){

            String[] header = br.readLine().split(",");
            int count = Integer.parseInt(header[0]);
            int target = Integer.parseInt(header[1]);
            MinPQ<Hand> pq = new MinPQ<Hand>();
            for(int in=0;in<count;in++){
                Card[] Cardinput = new Card[5];
                String[] Cardeach = br.readLine().split(",");
                for(int i=0; i<5;i++){
                    String[] temp = Cardeach[i].split("_");
                    Card c = new Card(temp[1],temp[0]);
                    Cardinput[i] = c;
                }
                Hand h = new Hand(Cardinput);
                pq.insert(h);
                if(pq.size()>target){
                    pq.delMin();
                }
            }
                br.close();
                Card[] clast = pq.delMin().getCards();
                Arrays.sort(clast);
                System.out.println(Printcard(clast));
        }
    }
}
