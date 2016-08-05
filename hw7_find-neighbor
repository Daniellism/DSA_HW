import java.util.Comparator;

public class FindNeighbors {
	Node root = null;

    // DO NOT MODIFY THE CONSTRUCTOR! 
    public FindNeighbors(){}

    // TODO
    // please use the Point2D from algs4.jar 
    public void init(Point2D[] points){
         for (Point2D s:points){
            this.insert(new Node(s, null, null));
         }
        return;
    }
    private void insert(Node node){
        if (root == null) {
            root = node;
            node.setDependsOnX(true);
        } else {
            Node thisRoot = root;
            while (thisRoot.getNext(node) != null)
                thisRoot = thisRoot.getNext(node);
            if (thisRoot.Depend_x()) {
                node.setDependsOnX(false);
                if (thisRoot.getX() < node.getX()) thisRoot.setRight(node);
                else thisRoot.setLeft(node);
            } else {
                node.setDependsOnX(true);
                if (thisRoot.getY() < node.getY()) thisRoot.setRight(node);
                else thisRoot.setLeft(node);
            }
        }
    }
    public Point2D[] query(Point2D point , int r){
        MaxPQ<Node> pq = new MaxPQ<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.getDistance() > o2.getDistance()) return 1;
                else if (o1.getDistance() < o2.getDistance()) return -1;
                else return 0;
            }
        });
        query_2(pq , root , point , r , Double.MAX_VALUE);
        Point2D[] result = new Point2D[r];
        for (int i = r-1; i >= 0 ; i--) {
            result[i] = pq.delMax().getPoint2D();
        }
        return result;
    }

    private void query_2(MaxPQ<Node> pq , Node root , Point2D target , int k , double minDistance) {
        if (root == null) return;
        root.setDistance(root.distanceTo(target));
        pq.insert(root);

        if (pq.size() > k) {
            pq.delMax();
            Node peek = pq.delMax();
            minDistance = peek.distanceTo(target);
            pq.insert(peek);
        }

        Node targetNode = new Node(target, null, null);
        query_2(pq , root.getNext(targetNode) , target, k , minDistance);

        if (pq.size() < k) {
            query_2(pq, root.getOppositeNext(targetNode), target, k , minDistance);
        } else if (root.Depend_x()) {
            if (Math.abs(root.getX() - target.x()) < minDistance)
                query_2(pq , root.getOppositeNext(targetNode) , target , k , minDistance);
        } else {
            if (Math.abs(root.getY() - target.y()) < minDistance)
                query_2(pq , root.getOppositeNext(targetNode) , target , k , minDistance);
        }
    }

    @Deprecated
    Node nearest = null;
    @Deprecated
    double nearestDistance = 0.0;
    @Deprecated
    public Point2D Nearest_node(Point2D target) {
        nearest = root;
        nearestDistance = nearest.distanceTo(target);
        Nearest_node(root , target);
        return nearest.getPoint2D();
    }

    @Deprecated
    private void Nearest_node(Node root , Point2D target) {
        if (root == null) return;
        if (root.distanceTo(target) < nearestDistance) {
            nearest = root;
            nearestDistance = nearest.distanceTo(target);
        }
        Node targetNode = new Node(target , null , null);
        Node next = root.getNext(targetNode);
        Nearest_node(next, target);
        if (root.Depend_x()) {
            if (Math.abs(root.getX() - target.x()) < nearestDistance)
                Nearest_node(root.getOppositeNext(targetNode) , target);
        } else {
            if (Math.abs(root.getY() - target.y()) < nearestDistance)
                Nearest_node(root.getOppositeNext(targetNode) , target);
        }
    }

    // TODO
    // the result should be sorted accordingly to their distances to the query, from small to large
    // please use the Point2D from algs4.jar 
  //  public Point2D[] query(Point2D point, int k){
  //      Point2D[] result = new Point2D[k];
  //      return result;
  //  }
    static class Node{
        private Point2D point2D;
        private Node left;
        private Node right;
        private boolean Depend_x;
        private double dis;

        Node(Point2D point2D , Node left , Node right){
            this.point2D = point2D;
            this.left = left;
            this.right = right;
        }

        public Node getNext(Node get) {
            if (this.isLeaf()) return null;
            if (this.Depend_x()){
                if (this.getX() < get.getX()) return this.get_Right();
                else return this.getLeft();
            } else {
                if (this.getY() < get.getY()) return this.get_Right();
                else return this.getLeft();
            }
        }

        public Node getOppositeNext(Node op) {
            if (this.isLeaf()) return null;
            if (this.Depend_x()){
                if (this.getX() < op.getX()) return this.getLeft();
                else return this.get_Right();
            } else {
                if (this.getY() < op.getY()) return this.getLeft();
                else return this.get_Right();
            }
        }

        public boolean isLeaf() { return left == null && right == null; }

        public double getX() { return point2D.x(); }

        public double getY() { return point2D.y(); }

        public double distanceTo(Point2D p) { return point2D.distanceTo(p); }

        public Point2D getPoint2D() { return point2D; }

        public boolean Depend_x() { return Depend_x; }

        public void setDependsOnX(boolean dependsOnX) { Depend_x = dependsOnX; }

        public Node getLeft() { return left; }

        public void setLeft(Node left) { this.left = left; }

        public Node get_Right() { return right; }

        public void setRight(Node right) { this.right = right; }

        public double getDistance() { return dis; }

        public void setDistance(double dis) { this.dis = dis; }
    }
    

}
