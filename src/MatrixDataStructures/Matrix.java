package MatrixDataStructures;
import java.lang.reflect.Array;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
//currently functionality is limited as this class was mainly created for Growing Matrix class
//but more functionality will be added
//check whether vairbales returned in java are refernces or copies
/**
 * 
 * @author Rajiv Sarvepalli
 *
 * @param <Item> the type of Objects that the Matrix will be filled with
 * @Description 
 * A Matrix class that uses a 2D array as its underlying data structure
 * -Provides more functionality than simple 2D arrays. 
 * Features include: <br> 
 * Python like list addressing to quicken indexing <br>
 * can get multiple items of the Matrix at once <br>
 * find() and findAll() methods <br>
 * an Iterator that returns each element of the matrix (could use numRows and numCols to recreate matrix) <br>
 * public variables numRows and numCols to be accessed like length of arrays <br>
 * clone() and equals() are properly implemented (clone returns a deep copy) <br>
 * a get2DArray() method that gets the the array, and a getCopyOf2DArray() that returns the a copy of the array that will work in outside classes <br>
 */
public class Matrix<Item> implements Iterable<Item>{
	private Item[][] matrix;
	private int[] size;
	public final int numRows;
	public final int numCols;
	public Matrix(Item[][] matrix){
		this.matrix = matrix;
		this.size = new int[]{this.matrix.length,this.matrix[0].length};
		this.numRows = matrix.length;
		this.numCols = matrix[0].length;
		for(int i=0;i<this.matrix.length;i++){
			if(this.matrix[i].length!=this.numCols)
				throw new IllegalArgumentException("matrix is not a matrix, 2D array does not have same number of columns for each row");
		}
	}
	/**
	 * Creates a Matrix with specified numRows and numCols
	 * @param numRows - the number of rows
	 * @param numCols - the number of columns
	 * @throws If numRows or numCols is 0 or less than 0
	 */
	public Matrix(int numRows, int numCols){
		this.matrix = (Item[][])new Object[numRows][numCols];
		this.size=new int[]{numRows,numCols};
		this.numRows = matrix.length;
		this.numCols = matrix[0].length;
	}
	/**
	 * 
	 * @param cls - the class of type Item
	 * @return a deep copy of the 2D array  that makes up the Matrix class
	 */
	public Item[][] getCopyOf2DArray(Class<? extends Item> cls){
		final Item[][] matrix = (Item[][]) Array.newInstance(cls,this.matrix.length,this.matrix[0].length);
        int a=0;
        int b=0;
		for(Item i: this){
			if(b<this.matrix[0].length){
				matrix[a][b++] = i;
			}
			if(b==this.matrix.length){
				b=0;
				a++;
			}
		}
		return matrix;
	}
	//not to be used outside of class
	public Item[][] get2DArray(){
		return this.matrix;
	}
	public String toString(){
		String str = "";
        int rows = this.matrix.length;
        int columns = this.matrix[0].length;
        str = "|\t";

        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                str += this.matrix[i][j] + "\t";
            }

            str += "|";
            str += "\n|\t";
        }

		return str.substring(0, str.length()-2);
	}
	/**
	 * compares two Matrix and returns true if arrays equal other
	 * @param o - the Object to compare (must be a type of Matrix)
	 * @throws o must be able to be casted to Matrix
	*/
	public boolean equals(Object o){
		Matrix<Item> other = (Matrix<Item>)o;
		return Arrays.deepEquals(other.get2DArray(), this.matrix);
	}
	/**
	 * 
	 * @author Rajiv Sarvepalli
	 *private class to iterate over matrixes items
	 */
	private class MatrixIterator implements Iterator<Item> {
        int i = 0;
        int j=0;
        public boolean hasNext() {
            if (i < matrix.length&j<matrix[0].length) {
                return true;
            } else {
                return false;
            }
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if(j==matrix[0].length-1){
            	i++;
            	j=0;
            	return matrix[i-1][matrix[0].length-1];
            }
            return matrix[i][j++];
        }
    }
	/**
	 * returns all the items in matrix in iterator
	 */
	public Iterator<Item> iterator() {
        return new MatrixIterator();
    }
	/**
	 * Returns a deep clone of a instance of Matrix
	 */
	public Matrix<Item> clone(){
		Item[][] matrix = (Item[][])new Object[this.matrix.length][this.matrix[0].length];
		for(int i=0;i<this.matrix.length;i++){
			matrix[i] = Arrays.copyOf(this.matrix[i], this.matrix[i].length);
		}
		return new Matrix<Item>(matrix);
	}
	/**
	 * Sets the index to Item i in the 2D array
	 * @param i - the item to put into array
	 * @param index - a 1D array in the format {row, col}
	 * @throws Index.length must equal 2 and must be appropriate indexes for the array
	 */
	public void set(Item i, int[] index){
		if(index.length!=2)
			throw new IllegalArgumentException("index length must equal 2");
		if(index[0]>this.numRows||index[1]>this.numCols||index[0]<0||index[1]<0)
			throw new IllegalArgumentException("index out of bounds");
		this.matrix[index[0]][index[1]] = i;	
	}
	/**
	 * Sets each item of items to given index
	 * @param items - the items to add to array
	 * @param indexes - the indexes that correspond to each item <br>
	 * EX: {{rowOfItem1, colOfItem1},{rowOfItem2,colOfItem2}}
	 * @throws Indexes[0].length must equal 2 and must be appropriate indexes for the array
	 */
	public void set(Item[] items, int[][] indexes){
		if(indexes[0].length!=2){
			throw new IllegalArgumentException("length of ind must be 2");
		}
		int itemCounter=0;
		for(int[] row: indexes){
			this.matrix[row[0]][row[1]] = items[itemCounter++]; 
		}
	}
	/**
	 * Gets a item at specified index
	 * @param index - a 1D array n the format {row, col}
	 * @return the item at the specified index
	 */
	public Item get(int[] index){
		if(index.length!=2)
			throw new IllegalArgumentException("index length must equal 2");
		if(index[0]>this.numRows||index[1]>this.numCols||index[0]<0||index[1]<0)
			throw new IllegalArgumentException("index out of bounds");
		return this.matrix[index[0]][index[1]];
	}
	/**
	Get all Items of at the specified indexes
	@param indexes - a 2D array like {{1,3}{2,3} to get row 1 and column 3 and row 2 and column 3 (start at 0)
	@return gets the Item at specified indexes
	@throws indexes must be less than matrix's respective lengths
	*/
	public Item[] get(int[][] indexes){
		if(indexes[0].length!=2){
			throw new IllegalArgumentException("length of ind must be 2");
		}
		final Item[] items = (Item[]) Array.newInstance(this.matrix[0][0].getClass(),indexes.length);
		for(int r=0;r<indexes.length;r++){
			items[r] = this.matrix[indexes[r][0]][indexes[r][1]];
		}
		return items;
	}
	/**
	Get all Items of at the specified indexes in a Python like style <br>
	EX: .get(0,2, true) will get the first two rows similar to python ListOfLists[0:2,:]
	EX2: .get(0,2, false) will get the first two columns similar to python ListOfLists[:,0:2]
	@param start - the start index
	@param end  - the end index (non-inclusive)
	@param rowIndexing - whether the rows will be indexed or columns
	@throws start must be less than end and both must fit in array bounds
	*/
	public Item[][] get(int start, int end, boolean rowIndexing){
		//like python list indexing (very easy to get certain portion of array, very slow though)
		//start inclusive end noninclusive
		//start and end start at 0
		if(start<0||start>end){
			throw new IllegalArgumentException("start out of bounds");
		}
		if(rowIndexing){
			if(end>this.matrix.length){
				throw new IllegalArgumentException("end out of bounds");
			}
		}
		else{
			if(end>this.matrix[0].length){
				throw new IllegalArgumentException("end out of bounds");
			}
		}

		
		final Item[][] items;
		if(rowIndexing){
			int itemIndexer = 0;
			items = (Item[][]) Array.newInstance(this.matrix[0][0].getClass(),end-start,this.numCols);
			for(int e=start;e<end;e++){
				items[itemIndexer++] = this.matrix[e];
			}
		}
		else{
			items = (Item[][]) Array.newInstance(this.matrix[0][0].getClass(),this.numRows,end-start);
			int itemIndexer = 0;
			for(int a=0;a<this.numRows;a++){
				for(int e=start;e<end;e++){
					items[a][itemIndexer++] = this.matrix[a][e];
				}
				itemIndexer = 0;
			}
		}
		return items;
	}
	/**
	Finds the indexes of the first instance of Item i 
	@param i - the item to find
	@return 1D array of the location of Item i (first instance of it) indexes of format {row,col}
	*/
	public int[] find(Item i){
		for(int r= 0;r<this.matrix.length;r++){
			for(int j=0;j<this.matrix[0].length;j++){
				if(this.matrix[r][j]==i){
					return new int[]{r,j};
				}
			}
		} 
		return new int[]{};
	}
	/**
	Finds the indexes of the all instances of Item i <br>
	Note: Very memory inefficient on very large matrixes
	@param i - the item to find
	@return 2D array of the location of Item i (every instance of it) indexes of format {row,col}
	2D array has lengths of number of instances of i and 2 respectively
	*/
	public int[][] findAll(Item i){
		//very large memory usage if array is large
		//switch to arraylist(vector) to better optimize memory usage
		int[][] allInstances= new int[this.size[0]*this.size[1]][2];
		for(int[] row:allInstances){
			Arrays.fill(row, -1);

		}
		int indexOfAllInstances = 0;
		for(int r= 0;r<this.matrix.length;r++){
			for(int c=0;c<this.matrix[0].length;c++){
				if(this.matrix[r][c]==i){
					allInstances[indexOfAllInstances][0] = r;
					allInstances[indexOfAllInstances][1] = c;
					indexOfAllInstances++;
				}
			}
		}
		int endIndexOfNullItems = 0;
		for(int index =0;index<allInstances.length;index++){
			if(allInstances[index][0]==-1){
				endIndexOfNullItems = index;
				break;
			}
		}
		return Arrays.copyOfRange(allInstances, 0, endIndexOfNullItems);
	}
	//item must be a multiplable type
	public Matrix<Double> multiply(Matrix<Double> other){
		//use strassen algirhtm to make faster
		//check make sure this is of type double
		boolean b =this.getClass() == other.getClass();
		return other;
	}
	//add solve and guassian only for double 
	public void transpose(){
		Item[][] matrix = (Item[][])new Object[this.matrix.length][this.matrix[0].length];
        for (int i = 0; i < this.numRows; i++)
            for (int j = 0; j < this.numCols; j++)
                matrix[j][i] = this.get(new int[]{i,j});
        this.matrix = matrix;
	}
	public static void main(String[] args) {
		Integer[][] m= new Integer[][]{{1,2,3},{3,4,5},{6,5,4}};
		Matrix<Integer> matrix = new Matrix<Integer>(m);
		//works like python's myListOfLists[0:2,:] row slice or myListOfLists[:,0:2] column slice
		System.out.println(Arrays.deepToString(matrix.get(0,2,false)));
	}
}
