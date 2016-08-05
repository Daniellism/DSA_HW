import java.util.Arrays;
import java.util.ArrayList;
/**
 *
 * @author Daniel
 */
public class CriticalDis {

    /**
     * @param args the command line arguments
     */
    static class Discounter implements Comparable<Discounter>{
        double d;
        int i;
        int j;
        public Discounter(double dis, int i , int j){
            this.d = dis;
            this.i = i;
            this.j = j;
        }

        @Override
        public int compareTo(Discounter o) {
            if (this.d > o.d) return 1;
            else if (this.d < o.d) return -1;
            else return 0;
        }
    }
    //counter for digraph
    
    
    public static void main(String[] args) {
        In in = new In(args[0]);
		int n_pts = in.readInt();
		Point2D[] pts = new Point2D[n_pts];
		String[] inputs = new String[2];
//		for (int i = 0 ; i < n_pts ; i++) {
//		inputs = in.readLine().split(" ");
//                pts[i] = new Point2D(Double.parseDouble(inputs[0]) , Double.parseDouble(inputs[1]));
//                }
                for (int i = 0 ; i < n_pts ; i++) {
            pts[i] = new Point2D(in.readDouble() , in.readDouble());
            }
        int source = 0;
        int goal = 0;     
        for (int i = 0; i < pts.length; i++) {
            Point2D p = pts[i];
            Point2D s = pts[source];
            Point2D t = pts[goal];
            if (p.x() + p.y() < s.x() + s.y()){ source = i;
            
            }
            if (p.x() + p.y() > t.x() + t.y()) {goal = i;
            
            }
        }
//        MinPQ temp_edges = null;
        ArrayList<Discounter> temp_edges = new ArrayList<Discounter>();
        for (int i = 0; i < pts.length; i++) {
            Point2D a = pts[i];
            for (int j = 0; j < pts.length; j++) {
                Point2D b = pts[j];
                if (a.x() < b.x() && a.y() < b.y()) {
                    temp_edges.add(new Discounter(a.distanceTo(b), i, j));
                }
            }
        }
        Discounter[] sort_edges = temp_edges.toArray(new Discounter[temp_edges.size()]);
        Arrays.sort(sort_edges);
        Digraph con = new Digraph(n_pts);
        for (Discounter new_edges:sort_edges){
            con.addEdge(new_edges.i,new_edges.j);
            DirectedDFS dfs = new DirectedDFS(con , source);
            if (dfs.marked(goal)) {
                System.out.println(String.format("%1.3f",new_edges.d));
                break;
            }
        }
    }
        
                
    }
