import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Daniel
 */
public class Clustering implements Comparable<Clustering>{
    private double x;
    private double y;
    private List<Point> pts = new ArrayList<Point>();

    private int size() {
        return pts.size(); //To change body of generated methods
    }
    public double get_X() { return this.x; }
    public double get_Y() { return this.y; }
    public List<Point> get_pts() { return pts; }
    
    Clustering(){}
    //an empty constructor
    
    Clustering(Point s){
        pts.add(s);
        x = s.getX();
        y = s.getY();
    }
    public static Clustering merge(Clustering a , Clustering b){
        Clustering c = new Clustering();
        double total_x = 0.0;
        double total_y = 0.0;
        for(Point d:a.pts){
            c.pts.add(d);
            total_x += d.getX();
            total_y += d.getY();
        }
        for(Point d:b.pts){
            c.pts.add(d);
            total_x += d.getX();
            total_y += d.getY();
        }
        c.x = total_x/c.pts.size();
        c.y = total_y/c.pts.size();
        return c;
    }
    
    public double distanceTo(Clustering that){
        return Math.sqrt( (this.x-that.x)*(this.x-that.x) + (this.y-that.y)*(this.y-that.y) );
    }
    
    public static class Point {
        private double x;
        private double y;
        Point(double x, double y){
            this.x = x;
            this.y = y;
        }
        public double getX(){ return x; }
        public double getY(){ return y; }
        public double distanceTo(Point that){
            return Math.sqrt( (this.y-that.y)*(this.y-that.y)+(this.x-that.x)*(this.x-that.x) );
        }
    }
    @Override
    public int compareTo(Clustering that){
        if(this.size()<that.size()) {
            return 1;
        } else if(this.size()>that.size()){
            return -1;
        } else if(this.get_X() > that.get_X()){
            return 1;
        } else if(this.get_X() < that.get_X()){
            return -1;
        } else if(this.get_Y() > that.get_Y()){
            return 1;
        } else if(this.get_Y() < that.get_Y()){
            return -1;
        } else{
            return 0;
        }
    }

   
    public static void main(String[] args) {
        try {

            List<Clustering> clu_1 = new ArrayList<Clustering>();
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            int total = Integer.parseInt(br.readLine());
            for (int i = 0; i < total; i++) {
                String[] arr = br.readLine().split(" ");
                Point in = new Point(Double.parseDouble(arr[0]) , Double.parseDouble(arr[1]));
                clu_1.add( new Clustering(in));
            }
            br.close();
            
            if(clu_1.size()==0){
                System.out.println("0.00");
            } else if(clu_1.size()==1){
                Clustering x =clu_1.get(0);
                System.out.printf(String.format("%d %.2f %.2f", x.size(), x.get_X(), x.get_Y()));
                System.out.println("0.00");
            } else if(clu_1.size()==2){
                Clustering.Point pt1 = clu_1.get(0).get_pts().get(0);
                Clustering.Point pt2 = clu_1.get(1).get_pts().get(0);
                double out = pt1.distanceTo(pt2);
                Clustering[] clu_out = clu_1.toArray(new Clustering[2]);
                Arrays.sort(clu_out);
                for(Clustering o:clu_out){
                    System.out.println(String.format("%d %.2f %.2f", o.size(), o.get_X(), o.get_Y()));
                }
                System.out.println(String.format("%.2f", out));
            } else {
            while(clu_1.size()>3){
                double least =clu_1.get(0).distanceTo(clu_1.get(1));
                int min01 = 0;
                int min02 = 1;
                // double for loop to find the minimun distance
                for(int i=0; i<clu_1.size(); i++){
                    for(int i2 =i+1; i2<clu_1.size();i2++){
                        double least01 = clu_1.get(i).distanceTo(clu_1.get(i2));
                        if(least>least01){
                            least = least01;
                            min01 = i;
                            min02 = i2;
                        }
                    }
                    
                }
                clu_1.add(Clustering.merge(clu_1.remove(min02), clu_1.remove(min01)));   
            }
            Clustering.Point pt1 = clu_1.get(0).get_pts().get(0);
            Clustering.Point pt2 = clu_1.get(1).get_pts().get(0);
            double output = pt1.distanceTo(pt2);
            for(int i=0; i<clu_1.size();i++){
                List<Clustering.Point> pts1 = clu_1.get(i).get_pts();
                for(int i2=i+1;i2<clu_1.size();i2++){
                    List<Clustering.Point> pts2 = clu_1.get(i2).get_pts();
                    for(Point p1:pts1){
                        for(Point p2:pts2){
                            if(p1.distanceTo(p2)<output){
                                output = p1.distanceTo(p2);
                                pt1 = p1;
                                pt2 = p2;
                            }
                        }
                    }
                }
            }
            Clustering[] clu_out = clu_1.toArray(new Clustering[clu_1.size()]);
            Arrays.sort(clu_out);
            for(Clustering o:clu_out){
                System.out.println(String.format("%d %.2f %.2f", o.size(), o.get_X(), o.get_Y()));
            }
            System.out.println(String.format("%.2f", output));
            }
        }  catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
}
