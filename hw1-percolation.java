import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Integer;
import static java.lang.System.err;
/**
 *
 * @author Daniel C
 */
public class Percolation {
    


/**
 *
 * @author Daniel
 */

     //0closed, 1open
  private int [] states;
  private int side;
  private WeightedQuickUnionUF cellStorage;
// create N-by-N grid, with all sites blocked
     public  void Percolate(int N){
       side=N;
        //+2 are for additional top and bottom cells
       cellStorage=new WeightedQuickUnionUF(N*N+2);      
       states=new int[N*N+2];
       for(int index=0;index<N*N;index++){
           states[index]=0;
       }
       states[N*N]=1;
       states[N*N+1]=1;            
     }  
   // open site (row i, column j)  
     public void open(int i, int j){ 
       checkRange(i,j);
       if(isOpen(i,j))return;       
       int cell=getCellIndex(i,j);
       states[cell]=1;
       //if not top row
       if(i!=1 && isOpen(i-1,j)){
          union(getCellIndex(i-1,j),cell);
       }else if(i==1 && side >1){
          //connect to virtual top cell
         union(cell,side*side);
       }
       //if not bottom row
       if(i!=side && isOpen(i+1,j)){       
         union(getCellIndex(i+1,j),cell);
       }else if (i==side){
          //connect to virtual bottom cell
          union(cell,side*side+1);
       }
       //if not left border
       if(j!=1 && isOpen(i,j-1)){
         union(getCellIndex(i,j-1),cell);
       }
       //if not right border
        if(j!=side && isOpen(i,j+1)){
         union(getCellIndex(i,j+1),cell);
       }
     }
     
     private void checkRange(int i, int j){
       if (i<=0||j<=0||i>side||j>side)throw new IndexOutOfBoundsException();
     }
     
     private void union(int a, int b){
         if (!cellStorage.connected(a,b)){
           cellStorage.union(a,b);
         }
     }
     
     // is site (row i, column j) open?
     public boolean isOpen(int i, int j){
       checkRange(i,j);
       return states[getCellIndex(i,j)]==1;
     }   
     
      // is site (row i, column j) full?
     public boolean isFull(int i, int j){
       checkRange(i,j);
       return cellStorage.connected(side*side,getCellIndex(i,j));
     }   
     
      // does the system percolate?
     public boolean percolates(){
       return cellStorage.connected(side*side,side*side+1);
     }           
     
     private int getCellIndex(int row, int column){
       return (side*(row-1))+column-1;
     }
     public static void main(String[] args) throws IOException {
        
        File inputfile = null; 
        if(0 < args.length){
            inputfile = new File(args[0]);
        } else{
            System.out.println("file does not exist");
        }
        Percolation Perco1 = new Percolation();
        BufferedReader br = new BufferedReader(new FileReader(inputfile));
        int inputnumber1 = 0 , inputnumber2 = 0;
        int newside = Integer.parseInt(br.readLine());
        Perco1.Percolate(newside);
        String data = null;
        String[] inputarray;
        data = br.readLine();
        while((data = br.readLine())!=null){
//            try{
             inputarray = data.split(",");
             inputnumber1 = Integer.parseInt(inputarray[0]);
             inputnumber2 = Integer.parseInt(inputarray[1]); 
             Perco1.open(inputnumber1, inputnumber2);
             if(Perco1.percolates()){
               System.out.println(inputnumber1+","+inputnumber2);
               break;
           }
//             data = br.readLine();
//             }catch(IOException ex){
//            err.println(ex.getMessage());
//        } 
        }
        
           if(!Perco1.percolates()){
               System.out.printf("-1");
           }
        
     
    }
    
}
