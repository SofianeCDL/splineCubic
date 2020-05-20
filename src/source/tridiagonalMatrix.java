package source;

import java.util.ArrayList;

public class tridiagonalMatrix {
	private int lenght;
	
	private ArrayList<Double> matrix;
	
	public tridiagonalMatrix(int lenght, ArrayList<Double> matrix) {
		
		this.lenght = lenght;
		this.matrix = matrix;
		
	}


	/** @role :  Solves a system of linear equations Ax=b, given a vector b with a TridiagonalMatrix "this".
	 * This function uses LU system to solves matrix.
	 * After the first procedure, vector x in SolveTriangularSystemUP contains the solution to Ax=b.
	 *
	 *  @param b : know parameter of equation.
	 *  @return : vector
	 */
	public vector solveSystemLU(vector b) {
		vector x = null,y = null;
		tridiagonalMatrix A = copy();
		
		decompositionLU(A);
		solveTriangularSystemDown(x,A,b);
		solveTriangularSystemUP(x,A,y);
		
		return x;
	}

	/** @role : Solves a system of linear equations Ax=y for a double-precision matrix A.
	 * Uses iterative ascension algorithm. After the procedure, x contains the solution of Ax=y.
	 *
	 * @precondition :  We assume that x and result have been allocated outside the function.
	 * @param x : contains the solution.
	 * @param A : this parameter represent a copy of matrix "this".
	 * @param y : this parameter represent "b" in solve down.
	 */
	private void solveTriangularSystemUP(vector x, tridiagonalMatrix A, vector y) {
		double value;
	    // we start with the last pivot (A[(n-1) * n + (n-1)]) and go up...
	    for(int i = A.lenght - 1 ; i >= 0 ; i--) {
	        y.tabElement.set(i, x.tabElement.get(i) / A.matrix.get(i * A.lenght + i)); // Look for the result of the only variable in the last row of A, such that t = ...

	        for (int j = i - 1; j >= 0; j--) {
	            value = x.tabElement.get(i) * A.matrix.get(j * A.lenght + i); // ... then we replace (here t) by its value found previously, on the column ...
	            x.tabElement.set(j, x.tabElement.get(j) - value); // ... and we move this value on the side of b to leave only the variables in the A matrix.
	        }
	    }
		
	}


	/**
	 * @role : Solves a system of linear equations Ax=b for a double-precision matrix.
	 * Uses iterative descent algorithm. After the procedure, x contains the solution of Ax=b.
	 *
	 * @precondition : We assume that x has been allocated outside the function.
	 *
	 * @param x : contains the solution.
	 * @param A : this parameter represent a copy of matrix "this".
	 * @param b : know parameter of equation.
	 */
	private void solveTriangularSystemDown(vector x, tridiagonalMatrix A, vector b) {
		double value;
	    x.tabElement.set(0, b.tabElement.get(0));

	    for(int i = 1 ; i <= A.lenght ; i++) {
	        value = b.tabElement.get(i);
	        for (int j = 0; j <= i; j++) {
	            value -= A.matrix.get(i*A.lenght+j)*x.tabElement.get(j);
	        }
	        x.tabElement.set(i, value);
	   }
	
	}

	/**
	 * @role : This function enables to carry out the LU decomposition.
	 * Uses iterative ascension algorithm.
	 *
	 * @param A : this parameter represent a copy of matrix "this".
	 */
	private void decompositionLU(tridiagonalMatrix A) {
		double coef;
		
		for (int cl = 0; cl < A.lenght - 1; cl++) {
	        for (int lgn = cl + 1; lgn < A.lenght; lgn++) {
	            // if the coef is not null
	            if (A.matrix.get(cl * A.lenght + cl) != 0) {
	                // determine the coef x (exp L2 = L2 - xL1)
	                 coef = A.matrix.get(lgn * A.lenght + cl) / A.matrix.get(cl * A.lenght + cl);
	                // performs the calculation (L2 = L2-xL1) by applying x to each cell of the line

	                for (int w = cl; w < A.lenght; w++) {
	                    A.matrix.set(lgn * A.lenght + w, A.matrix.get(lgn * A.lenght + w) - coef * A.matrix.get(cl * A.lenght + w));
	                }

	                A.matrix.set(lgn * A.lenght + cl, coef);
	            }
	        }
	    }
		
	}

	/**
	 * @role : Copy and return a tridiagonal Matrix.
	 * @return : tridiagonalMatrix
	 */
	public tridiagonalMatrix copy() {
		return new tridiagonalMatrix(this.lenght, this.matrix);
	}

}
