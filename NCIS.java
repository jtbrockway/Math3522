import java.util.ArrayList;
import java.util.Scanner;

public class NCIS {
	
	public static void main(String[] args) {
		ArrayList<double[]> input = new ArrayList<double[]>();
		
		// handle input parsing
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input either \"x y\" or \"done\"");
        
        // while (!done)
        while (true) {
            String line = scanner.nextLine();
            // if input == "done"
            if (line.equals("done")) {
                // done with inputs
                break;
            }
            else {
                // input -> point
                String[] splitLine = line.split(" ");
                if (splitLine.length == 2) {
                    double x = Double.parseDouble(splitLine[0]);
                    double y = Double.parseDouble(splitLine[1]);  // can fail here
                    double[] inPoint = new double[2];
                    inPoint[0] = x;
                    inPoint[1] = y;
                    input.add(inPoint);
                }
                else {
                    // wrong format
                    System.out.println("Discarding last line of input, please input \"x y\"");
                }
            }
        }
        
        // make sure no dup x's
        for (int i = 0; i < input.size(); i++) {
            for (int j = i + 1; j < input.size(); j++) {
                if ((input.get(i)[0] == input.get(j)[0])){
                    System.out.println("Inputted points contain duplicate values. Does not compute.");
                    return;
                }
            }
        }
		// handle input parsing
        
        // while (!done)
        
            // if input == "done"
        
                // done with inputs
        
                // do stuff -- check if tridiagonal!
        
                    // check for wrong format
        
        // make sure that computation will work

        // perform & print NCIS
		
        // close scanner
		
	}

}
