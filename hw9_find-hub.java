/*
* test03
*/
public class FindHub {
    public static void main(String[] args) {

        In in = new In(args[0]);
        int n = in.readInt();
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++)
            points[i] = new Point2D(in.readDouble() , in.readDouble());

        EdgeWeightedGraph n_map = new EdgeWeightedGraph(n);

        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                n_map.addEdge(new Edge(i,j,points[i].distanceTo(points[j])));
            }
        }
        KruskalMST mst = new KruskalMST(n_map);

        EdgeWeightedDigraph S_tree = new EdgeWeightedDigraph(n);
        for(Edge e:mst.edges()){
            int y = e.either();
            int w = e.other(y);
            double weight = e.weight();
            S_tree.addEdge(new DirectedEdge(y, w, weight));
            S_tree.addEdge(new DirectedEdge(w, y, weight));
        }


        double m_path = Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            DijkstraSP hub = new DijkstraSP(S_tree,i);
            double sum = 0.0;
            for (int j = 0; j < n; j++)
                sum += hub.distTo(j);
            if (m_path > sum) m_path = sum;
        }

        System.out.println(String.format("%5.5f",m_path));


    }


}
