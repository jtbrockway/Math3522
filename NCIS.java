//Inspiration drawn from: http://facstaff.cbu.edu/wschrein/media/M329%20Notes/M329L67.pdf

import java.util.ArrayList;
import java.util.Scanner;

/*
Test Cases:

#1)
Input:
	(1, 2), (2, 3), (3, 5)
Result:
	S(x) =
	    S0(x) = 2.0 + 0.75(x - 1.0) + 0.0(x - 1.0)^2 + 0.25(x - 1.0)^3
	    S1(x) = 3.0 + 1.5(x - 2.0) + 0.75(x - 2.0)^2 + -0.25(x - 2.0)^3

#2) (Textbook Problem 9.2.32)
Input:
	(1, 0), (2, 1), (3, 0), (4, 1), (5, 0)
Result:
	S(x) =
	    S0(x) = 0.0 + 1.7142857142857144(x - 1.0) + 0.0(x - 1.0)^2 + -0.7142857142857143(x - 1.0)^3
	    S1(x) = 1.0 + -0.4285714285714285(x - 2.0) + -2.142857142857143(x - 2.0)^2 + 1.5714285714285712(x - 2.0)^3
	    S2(x) = 0.0 + 1.1102230246251565E-16(x - 3.0) + 2.571428571428571(x - 3.0)^2 + -1.5714285714285712(x - 3.0)^3
	    S3(x) = 1.0 + 0.4285714285714286(x - 4.0) + -2.142857142857143(x - 4.0)^2 + 0.7142857142857143(x - 4.0)^3

#3) (Textbook Problem 9.2.41)
Input:
	(0, 1), (1, 2), (2, 3), (3, 4), (4, 5)
Result:
	S(x) =
			S0(x) = 1.0 + 1.0(x - 0.0) + 0.0(x - 0.0)^2 + 0.0(x - 0.0)^3
    	S1(x) = 2.0 + 1.0(x - 1.0) + 0.0(x - 1.0)^2 + 0.0(x - 1.0)^3
    	S2(x) = 3.0 + 1.0(x - 2.0) + 0.0(x - 2.0)^2 + 0.0(x - 2.0)^3
    	S3(x) = 4.0 + 1.0(x - 3.0) + 0.0(x - 3.0)^2 + 0.0(x - 3.0)^3

*/


public class NCIS {

	// tridiagonal linear system solver
	public static void tridAlgo(ArrayList<double[]> xy, ArrayList<Double> hi, ArrayList<Double> ss, ArrayList<Double> ls, ArrayList<Double> us, ArrayList<Double> zs, ArrayList<Double> bs, ArrayList<Double> cs, ArrayList<Double> ds){
		// set up
		ls.add(1.0);
		us.add(0.0);
		zs.add(0.0);

		for(int i = 1; i < xy.size() - 1; i++){
			ls.add(2 * (xy.get(i+1)[0] - xy.get(i-1)[0]) - hi.get(i-1) * us.get(i-1));
			us.add(hi.get(i) / ls.get(i));
			zs.add((ss.get(i-1) - hi.get(i-1) * zs.get(i-1)) / ls.get(i));
		}

		ls.add(1.0);
		zs.add(0.0);
		cs.add(0.0);

		for(int j = xy.size() - 2; j >= 0; j--){
			cs.set(j, zs.get(j) - us.get(j) * cs.get(j+1));
			bs.set(j,(xy.get(j+1)[1] - xy.get(j)[1]) / hi.get(j) - hi.get(j)*(cs.get(j+1) + 2 * cs.get(j)) / 3);
			ds.set(j,(cs.get(j+1) - cs.get(j)) / (3 * hi.get(j)));
		}

	}

	// creates and prints the S(x) function and its parts
	public static void ncisAlgo(ArrayList<double[]> xy, ArrayList<Double> bs, ArrayList<Double> cs, ArrayList<Double> ds){
		System.out.println("S(x) = ");
		for(int j = 0; j < xy.size() - 1; j++){
			String si = "S" + Integer.toString(j) + "(x) = ";
			String aj = Double.toString(xy.get(j)[1]);
			String bj = bs.get(j).toString();
			String cj = cs.get(j).toString();
			String dj = ds.get(j).toString();
			String xj = Double.toString(xy.get(j)[0]);
			System.out.println("    " + si + aj + " + " + bj + "(x - " + xj + ") + " + cj + "(x - " + xj + ")^2 + " + dj + "(x - " + xj +")^3");
		}
	}

	public static void main(String[] args) {
		ArrayList<double[]> xy = new ArrayList<double[]>();
		ArrayList<Double> hi = new ArrayList<>();
		ArrayList<Double> ss = new ArrayList<>();

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
                xy.add(inPoint);
            }
            else {
                // wrong format
                System.out.println("Discarding last line of input, please input \"x y\"");
            }
        }
    }

    // make sure no dup x's
    for (int i = 0; i < xy.size(); i++) {
        for (int j = i + 1; j < xy.size(); j++) {
            if ((xy.get(i)[0] == xy.get(j)[0])){
                System.out.println("Inputted points contain duplicate values. Does not compute.");
                return;
            }
        }
    }

		// create the h list
		for(int i = 0; i < xy.size() - 1; i++){
			hi.add(xy.get(i+1)[0] - xy.get(i)[0]);
		}

		// set up the right hand side of the tridiangonal system
		for(int i = 1; i < xy.size() - 1; i++){
			ss.add(((3/hi.get(i))*(xy.get(i+1)[1] - xy.get(i)[1])) - ((3/hi.get(i-1))*(xy.get(i)[1] - xy.get(i-1)[1])));
		}

		// setup lists for tridiagonal subroutine to use
		ArrayList<Double> ls = new ArrayList<>();
		ArrayList<Double> us = new ArrayList<>();
		ArrayList<Double> zs = new ArrayList<>();
		ArrayList<Double> bs = new ArrayList<>();
		ArrayList<Double> cs = new ArrayList<>();
		ArrayList<Double> ds = new ArrayList<>();

		// Set up ArrayLists
		for(int i = 0; i < xy.size(); i++){
			bs.add(0.0);
			cs.add(0.0);
			ds.add(0.0);
		}

		// do tridiagonal subroutine
		tridAlgo(xy, hi, ss, ls, us, zs, bs, cs, ds);

		// print the natural cubic interpolating splitLine
		ncisAlgo(xy, bs, cs, ds);

		// cleanup
		scanner.close();
	}
}
