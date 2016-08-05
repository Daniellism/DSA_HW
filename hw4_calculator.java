
/**
 *
 * @author Daniel C
 */
public class Calculator {

    public Double ans (String e) {
        String [] inputnumb = e.split(" ");
      // please replace the following null to the answer you calculated.
    Stack<String> ops = new Stack<String>();
    Stack<Double> vals = new Stack<Double>();
    for(int i=0; i<inputnumb.length;i++) {
    String s = inputnumb[i];
//    System.out.println(s);
    if (s.contains("("))   ;
    else if (s.equals(" "))     ;
    else if (s.equals("+")) ops.push(s);
    else if (s.equals("-")) ops.push(s);
    else if (s.equals("*")) ops.push(s);
    else if (s.equals("/")) ops.push(s);
    else if (s.equals(")"))
    {
     String op = ops.pop();
    if (op.equals("+")) vals.push(vals.pop() + vals.pop());
    else if (op.equals("-")) {
         double a = vals.pop();
        double b = vals.pop();
        vals.push( b - a );
    }
    else if (op.equals("*")) vals.push(vals.pop() * vals.pop());
    else if (op.equals("/")){
        double a = vals.pop();
        double b = vals.pop();
        vals.push( b / a );
    }
    }
    else vals.push(Double.parseDouble(s));
    }
//    System.out.println(vals.pop());
    while(!ops.isEmpty()){
        String op = ops.pop();
    if (op.equals("+")) vals.push(vals.pop() + vals.pop());
    else if (op.equals("-")) {
        double a = vals.pop();
        double b = vals.pop();
        vals.push( b - a );
    }
    else if (op.equals("*")) vals.push(vals.pop() * vals.pop());
    else if (op.equals("/")) {
        double a = vals.pop();
        double b = vals.pop();
        vals.push( b / a );
    }
    }
        
        
      return vals.pop();
}
    public static void main(String[] args) {
      
        
    }
    
}
