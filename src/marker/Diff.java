package marker;

/**
 * Compares two files and finds the differences between them
 * 
 * Credits to Princeton for original version, minor modifications by Kai and Adam
 * http://introcs.cs.princeton.edu/java/96optimization/Diff.java.html
 */
public class Diff {
	static int prevAff = 3; //tracks changes in addition/deletion state

	public static String diff(String[] args) {
		// String builder
		StringBuilder stringBuilder = new StringBuilder();

		prevAff = 3; 

		// read in lines of each file
		In in0 = new In(args[0]);
		In in1 = new In(args[1]);
		String[] x = in0.readAllLines();
		String[] y = in1.readAllLines();

		// number of lines of each file
		int M = x.length;
		int N = y.length;

		// opt[i][j] = length of LCS of x[i..M] and y[j..N]
		int[][] opt = new int[M + 1][N + 1];

		// compute length of LCS and all subproblems via dynamic programming
		for (int i = M - 1; i >= 0; i--) {
			for (int j = N - 1; j >= 0; j--) {
				if (x[i].equals(y[j]))
					opt[i][j] = opt[i + 1][j + 1] + 1;
				else
					opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]);
			}
		}

		// recover LCS itself and print out non-matching lines to standard
		// output
		int i = 0, j = 0;
		while (i < M && j < N) {
			if (x[i].equals(y[j]) || equalityWithoutBlanks(x[i],y[j])) {
				i++;
				j++;
			} else {
				if (opt[i + 1][j] >= opt[i][j + 1]) {
					if (prevAff != 0) {
						stringBuilder.append("DELETIONS: \n");
						prevAff = 0;
					}
                    stringBuilder.append("< " + x[i++] + "\n");

                } else {
					if (prevAff != 1) {
						stringBuilder.append("ADDITIONS: \n");
						prevAff = 1;
					}
					stringBuilder.append("> " + y[j++] + "\n");
				}
			}
		}

		// dump out one remainder of one string if the other is exhausted
		while (i < M || j < N) {
			if (i == M) {
				if (prevAff != 0) {
					stringBuilder.append("DELETIONS: \n");
					prevAff = 0;
				}
                stringBuilder.append("> " + y[j++] + "\n");

            } else if (j == N){
				if (prevAff != 1) {
					stringBuilder.append("ADDITIONS: \n");

					prevAff = 1;
				}
				stringBuilder.append("< " + x[i++] + "\n");
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * Checks two strings to see if they are the same after removing tabs,newlines and returns
	 * @param string first string
	 * @param string2 second string
	 * @return whether or not they are equal
	 */
	private static boolean equalityWithoutBlanks(String string, String string2) {
		String input = string.replaceAll("\t|\n|\r", "");
		String input2 = string2.replaceAll("\t|\n|\r", "");
		if(input.equals(input2))return true;
		else return false;
	}

}
