import java.util.ArrayList;
import java.util.Scanner;

public class NCIS {

	public static void tridAlgo(ArrayList<double[]> xy, ArrayList<double[]> hi, ArrayList<double[]> as, ArrayList<double[]> ls, ArrayList<double[]> us, ArrayList<double[]> zs, ArrayList<double[]> as, ArrayList<double[]> bs, ArrayList<double[]> cs, ArrayList<double[]> ds){
		ls.add(1);
		us.add(0);
		zs.add(0);

		for(int i = 1; i < hi.size() + 1; i++){
			ls.add(2*(xy.get(i+1)[0] - xy.get(i-1)[0]) - hi.get(i-1)*us.get(i-1));
			us.add(hi.get(i) / ls.get(i));
			zs.add((as.get(i) - hi.get(i-1)*zs.get(i-1)) / ls.get(i));
		}

		ls.add(1);
		zs.add(0);
		cs.add(xy.size(), 0);

		for(int j = xy.size(); j > 0; j--){
			
		}

	}

	public static void ncisAlgo(){
		System.out.println("STUF AND THAINSNGS");
	}

	public static void main(String[] args) {
		ArrayList<double[]> xy = new ArrayList<double[]>();
		ArrayList<double[]> hi = new ArrayList<double[]>();
		ArrayList<double[]> ss = new ArrayList<double[]>();

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
		for(int i = 0; i < xy.size(); i++){
			hi.add(xy.get(i+1)[0] - xy.get(i)[0]);
		}

		// create the z list
		for(int i = 1; i < xy.size(); i++){
			ss.add(((3/hi.get(i))*(xy.get(i+1)[1] - xy.get(i)[1])) - ((3/hi.get(i-1))*(xy.get(i)[1] - xy.get(i-1)[1])));
		}

		// setup lists for tridiagonal subroutine to use
		ArrayList<double[]>() ls = new ArrayList<double[]>();
		ArrayList<double[]>() us = new ArrayList<double[]>();
		ArrayList<double[]>() zs = new ArrayList<double[]>();
		ArrayList<double[]>() as = new ArrayList<double[]>();
		ArrayList<double[]>() bs = new ArrayList<double[]>();
		ArrayList<double[]>() cs = new ArrayList<double[]>();
		ArrayList<double[]>() ds = new ArrayList<double[]>();

		// do tridiagonal subroutine
		tridAlgo(xy, hi, ss, ls, us, zs, as, bs, cs, ds);


	}
}
