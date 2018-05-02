package nqueens;


public class N_Queen_helper {

//    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
//        ServerConfig serverConfig = (ServerConfig) getServletContext().getAttribute(ServerConfig.class.getName());

//    HTMLDocument doc = new HTMLDocument("N_Queens", false);
//
//        doc.setOwnerClass(getClass().getSimpleName());
//
//        doc.writeln("<div class=\"form-unit\">");
//
//        doc.writeln("<h2>N_Queen's Chess Problem</h2>");

    public int nQ;

    /***************************************************************************
     * Return true if queen placement q[n] does not conflict with
     * other queens q[0] through q[n-1]
     ***************************************************************************/
    public static boolean isConsistent(int[] q, int n) {
        for (int i = 0; i < n; i++) {
            if (q[i] == q[n])             return false;   // same column
            if ((q[i] - q[n]) == (n - i)) return false;   // same major diagonal
            if ((q[n] - q[i]) == (n - i)) return false;   // same minor diagonal
        }
        return true;
    }

    /***************************************************************************
     * Prints n-by-n placement of queens from permutation q in ASCII.
     ***************************************************************************/
    public void printQueens(int[] q) {
        int n = q.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (q[i] == j)
                    nQ+=1;
                else           System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println();
    }


    /***************************************************************************
     *  Try all permutations using backtracking
     ***************************************************************************/
    public  void enumerate(int n) {
        int[] a = new int[n];
        enumerate(a, 0);
    }

    public  void enumerate(int[] q, int k) {
        int n = q.length;
        if (k == n) printQueens(q);
        else {
            for (int i = 0; i < n; i++) {
                q[k] = i;
                if (isConsistent(q, k)) enumerate(q, k+1);
            }
        }
    }


    public int main(int n) {
        //int n = Integer.parseInt(args[0]);
        enumerate(n);
        return nQ;
    }

}