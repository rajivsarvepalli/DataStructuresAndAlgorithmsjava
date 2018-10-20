package MatrixDataStructures;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
@author Rajiv Sarvepalli<br>
@Description
Growing Matrix - a matrix that supports growing in negative and positive directions <br> 
-Uses the Matrix class as its underlying data structure for more functionality <br>
-However slower and less memory efficient than Growing2DArray <br>
Features - a pointer to point to value that updates to continue to point to the same value <br>
-a factor to multiply how much the matrix is growing by <br>
-implements Iterable to return each element of its matrix <br>
-find() and findAll() methods to find Item i (either first or all instances) <br>
-insert(), addItem(), and addItems() methods <br>
-trim() method to trim all extra added length <br>
-get copy of matrix method to return copy of matrix used in GrowingMatrix <br>
-get method to get Item at indexes i, j and method to get multiple Items given multiple indexes <br> 
-even has a get method that uses python like list indexing to return portions of array <br> 
-getSize() method to get size of matrix <br>
-equals() and clone() are implemented, equals just checks if matrixes, pointers, and growthFactor are equal <br>
-optional equals methods are implemented, one ignores pointer variable and one ignores growthFactor and pointer variables
*/
//could change to extend matrix then cut out methods like get, iterate
//or leave as is
//indexing is a little memory inefficient due to using arrays instead of ints, but it provides cleaner code and easier usage
//especially in getting multiple items
public class GrowingMatrix<Item> implements Iterable<Item>{
	//grows in any way you want (negative too) 
	private Matrix<Item> matrix;
	private double growthFactor;
	private int[] size;
	private int pointer[];
	/**
	Creates a GrowingMatrix with base matrix and a growth factor
	@param matrix - a matrix to start with
	@param growthFactor - a multiplier that determines
	how much the matrix grows in addition to match to indices. 
	Note that growthFactor is multiplied by how much is added,
	not the matrix's size
	@throws growthFactor must be >= 1
	*/
	public GrowingMatrix(Item[][] matrix, double growthFactor){
		//make growth factor increase size quicker so many copies cna be avoided
		//growth = amount added*growthfactor
		if(growthFactor<1){
			throw new IllegalArgumentException("growthFactor must be >=1");
		}
		this.matrix = new Matrix<Item>(matrix);
		this.growthFactor =growthFactor;
		this.size = new int[2];
		this.size[0] = matrix.length;
		this.size[1] = matrix[0].length;
	}
	/**
	Creates a GrowingMatrix with base matrix and a growth factor
	Matrix is set to 1 by 1 null matrix. 
	growthFactor is 2.0
	@param None
	*/
	public GrowingMatrix(){
		this.growthFactor = 2.0;
		this.size = new int[]{1,1};
		this.matrix = new Matrix<Item>(1,1);
	}
	/**
	Creates a GrowingMatrix with base matrix and a growth factor
	@param matrix - a matrix to start with
	@param growthFactor - a multiplier that determines
	how much the matrix grows in addition to match to indices. 
	Note that growthFactor is multiplied by how much is added,
	not the matrix's size
	@throws growthFactor must be >= 1
	*/
	public GrowingMatrix(Matrix<Item> matrix, double growthFactor){
		if(growthFactor<1){
			throw new IllegalArgumentException("growthFactor must be >=1");
		}
		this.matrix = matrix;
		this.growthFactor =growthFactor;
		this.size = new int[2];
		this.size[0] = matrix.numRows;
		this.size[1] = matrix.numCols;
	}
	/**
	Creates a GrowingMatrix with base matrix and a growth factor
	Matrix is set to 1 by 1 null matrix. 
	@param growthFactor - a multiplier that determines
	how much the matrix grows in addition to match to indices. 
	Note that growthFactor is multiplied by how much is added,
	not the matrix's size
	@throws growthFactor must be >= 1 
	*/
	public GrowingMatrix(double growthFactor){
		if(growthFactor<1){
			throw new IllegalArgumentException("growthFactor must be >=1");
		}
		this.growthFactor = growthFactor;
		this.size = new int[]{1,1};
		this.matrix = new Matrix<Item>(1,1);
	}
	/**
	Adds an item at the specified indices given to the matrix.
	The Matrix fills all empty slots with null.
	Int i and Int j can be negative and bigger than matrix <br>
	Note: This does not insert elements if indexes are within matrix's bounds, it sets them <br>
	Null values will always exist if you go above or below the normal bounds, that is the nature of 2D arrays
	@param item - item to add
	@param i - the row index (starts at 0)
	@param j - the column index (starts at 0)
	@return void
	*/
	public void addItem(Item item, int i, int j){
		//fix the bad casts later
		//negatives grows in the postive direction still (needs change)
		int addx = 0;
		int addy = 0;
		boolean negativei = i<0;
		boolean negativej = j<0;

		if(i<=0){
			addx=-i;
		}
		else{
			if(i>=this.matrix.numRows)
				addx = i-this.matrix.numRows+1;
		}
		if(j<=0){
			addy =-j;
		}
		else{
			if(j>=this.matrix.numCols)
				addy = j-this.matrix.numCols+1;
		}
		if(addx>0||addy>0){
			Matrix<Item> matrix = new Matrix<Item>(this.matrix.numRows+(int)(addx*this.growthFactor),this.matrix.numCols+(int)(addy*this.growthFactor));
			for(int a =0;a<this.matrix.numRows;a++){
				for(int b =0;b<this.matrix.numCols;b++){
					if(negativei&&negativej){
						matrix.set(this.matrix.get(new int[]{a,b}),new int[]{a+(int)(addx*this.growthFactor),b+(int)(addy*this.growthFactor)}); //siwtched from just addx and addy
					}
					else if(negativei){
						matrix.set(this.matrix.get(new int[]{a,b}), new int[]{a+(int)(addx*this.growthFactor),b});
					}
					else if(negativej){
						matrix.set(this.matrix.get(new int[]{a,b}), new int[]{a,b+(int)(addy*this.growthFactor)});
					}
					else{
						matrix.set(this.matrix.get(new int[]{a,b}), new int[]{a,b});
					}
				}
			}
			this.matrix = matrix;
		}
		if(negativei&&negativej){
			this.matrix.set(item, new int[]{-addx+(int)(addx*this.growthFactor),-addy+(int)(addy*this.growthFactor)});
			if(pointer!=null){
				this.pointer[0] = this.pointer[0]+(int)(addx*this.growthFactor);
				this.pointer[1] = this.pointer[1]+(int)(addy*this.growthFactor);
			}
		}
		else if(negativei){
			this.matrix.set(item, new int[]{-addx+(int)(addx*this.growthFactor),0});
			if(pointer!=null){
				this.pointer[0] = this.pointer[0]+(int)(addx*this.growthFactor);
			}
		}
		else if(negativej){
			this.matrix.set(item, new int[]{0,-addy+(int)(addy*this.growthFactor)});
			if(pointer!=null){
				this.pointer[1] = this.pointer[1]+(int)(addy*this.growthFactor);
			}
		}
		else{
			this.matrix.set(item, new int[]{i,j});
		}
		this.size[0] = this.matrix.numRows;
		this.size[1] = this.matrix.numCols;
	}
	/**
	Adds an items at the specified indices given to the matrix.
	The Matrix fills all empty slots with null.
	Indices inside ind can be negative and bigger than matrix <br>
	Note: This does not insert elements if indexes are within matrix's bounds, it sets them
	@param items - 1D array of items to add
	@param indexes - 2D array of indexes in format {{1, 2}, {0, 0}}
	which indexes row 1 column 2 and row 0 column 0
	@return void
	*/
	public void addItems(Item[] items,int[][] indexes){
		if(indexes[0].length!=2){
			throw new IllegalArgumentException("indexes must have 2 elements per row");
		}
		if(indexes.length!=items.length){
			throw new IllegalArgumentException("number of rows in ind must equal length of items");
		}
		for(int i=0;i<items.length;i++){
			this.addItem(items[i],indexes[i][0],indexes[i][1]);
		}
	}
	//note last item if falls off will be added back on 
	//all nulls except the last are kept
	/**
	Inserts an item at the specified indices given to the matrix.
	Nulls elements are shifted over <br>
	If the last item falls off and is not null it is re-added using the addItem method to the next row<br>
	This will add null spaces to the new bottom row so beware
	@param i - an Item to add to array
	@param index - 1D array index point to insert at in format {row, col}
	@return void
	@throws if index is not of length 2 or contains value out of matrixes bounds
	*/
	public void insert(Item i, int[] index){
		if(index.length!=2)
			throw new IllegalArgumentException("index length must equal 2");
		if(index[0]>this.size[0]||index[1]>this.size[1]||index[0]<0||index[1]<0)
			throw new IllegalArgumentException("index out of bounds");
		Matrix<Item> matrix = new Matrix<Item>(this.size[0],this.size[1]);
		Item last = this.matrix.get(new int[]{this.matrix.numRows-1,this.matrix.numCols-1});
		for(int a =0;a<this.matrix.numRows;a++){
			for(int b =0;b<this.matrix.numCols;b++){
				if(a==index[0]&&b==index[1])
					matrix.set(i, new int[]{a,b});
				else if(a>index[0]){
					if(b<this.size[1]-1)
						matrix.set(this.matrix.get(new int[]{a,b}), new int[]{a,b+1});
					else{
						if(a!=this.size[0]-1)
							matrix.set(this.matrix.get(new int[]{a,b}), new int[]{a+1,0});
					}
				}
				else
					matrix.set(this.matrix.get(new int[]{a,b}), new int[]{a,b});
			}
		}
		this.matrix = matrix;
		if(last!=null)
			this.addItem(last, this.size[0], 0);
	}
	/**
	 Iterator method to return each item of the matrix that composes the GrowingMatrix 
	 */
	public Iterator<Item> iterator() {
        return this.matrix.iterator();
    }
	/**
	Compares two GrowingMatrix and tells if they are equals
	Note: does check pointers and growthFactor too (matrix obviously)
	@param Object o to compare to
	@return true if equal, false if not
	@throws If object o is not a GrowingMatrix
	*/
	public boolean equals(Object o){
		GrowingMatrix<Item> other = (GrowingMatrix<Item>)o;
		return this.matrix.equals(other.getMatrix())&&Arrays.equals(this.pointer, other.getPointer())
				&&this.growthFactor==other.getGrowthFactor();
	}
	/**
	Compares two GrowingMatrix and tells if they are equals (compares growthFactor and matrix)
	Note: does not check pointers 
	@param Object o to compare to
	@return true if equal, false if not
	@throws If object o is not a GrowingMatrix
	*/
	public boolean equalsWithoutPointer(Object o){
		GrowingMatrix<Item> other = (GrowingMatrix<Item>)o;
		return this.matrix.equals(other.getMatrix())&&this.growthFactor==other.getGrowthFactor();
	}
	/**
	Compares two GrowingMatrix and tells if they are equals (compares just matrix)
	Note: does not check pointer or growthFactor
	@param Object o to compare to
	@return true if equal, false if not
	@throws If object o is not a GrowingMatrix
	*/
	public boolean equalsWithoutPointerAndGrowthFactor(Object o){
		GrowingMatrix<Item> other = (GrowingMatrix<Item>)o;
		return this.matrix.equals(other.getMatrix());
	}
	/**
	Creates a deep copy of GrowingMatrix
	@param void
	@return a deep copy of this (GrowingMatrix)
	*/
	public GrowingMatrix<Item> clone(){
		return new GrowingMatrix<Item>(matrix.clone(),this.growthFactor);
	}
	/**
	Gets the matrix probably won't outside class
	@param void
	@return the matrix inside GrowingMatrix
	*/
	protected Matrix<Item> getMatrix(){
		//Don't use because the matrix was soft casted and being accessed outside of class will mess it up
		return this.matrix;
	}
	//gets growthFactor just used for equals method
	protected double getGrowthFactor(){
		return this.growthFactor;
	}
	/**
	Gets a copy of the matrix inside GrowingMatrix <br>
	Works outside of this class
	@param the class of Item you have used when instantiating this GrowingMatrix
	@return a copy of the matrix inside GrowingMatrix
	*/
	public Matrix<Item> getCopyOfMatrix(Class<? extends Item> cls){
		return this.matrix.clone();
	}
	/**
	Get size of matrix in GrowingMatrix <br>
	Includes all null entities <br>
	Use trim to get rid of extra null items that have been added
	@param void
	@return the matrix's size in 1D array with format {matrix.length,matrix[0].length}
	*/
	public int[] getSize(){
		return this.size;
	}
	/**
	Get Item i of at index index
	@param index - a 1D array like {1,3} to get row 1 and column 3 (start at 0)
	@return gets the Item at specified indexes
	@throws indexes must be less than matrix's respective lengths
	*/
	public Item get(int[] index){
		return this.matrix.get(index);
	}
	/**
	Get all Items of at the specified indexes
	@param indexes - a 2D array like {{1,3}{2,3} to get row 1 and column 3 and row 2 and column 3 (start at 0)
	@return gets the Item at specified indexes
	@throws indexes must be less than matrix's respective lengths
	*/
	public Item[] get(int[][] indexes){
		return this.matrix.get(indexes);
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
		return this.matrix.get(start, end, rowIndexing);
	}
	/**
	Sets all null items to some Item i
	@param void
	@return void
	@throws Item i must be of type Item
	*/
	public void setAllNullTo(Item i){
		for(int r= 0;r<this.matrix.numRows;r++){
			for(int j=0;j<this.matrix.numCols;j++){
				if(this.matrix.get(new int[]{r,j})==null){
					this.matrix.set(i, new int[]{r,j});
				}
			}
		}
	}
	/**
	Sets a pointer (indexes of matrix in map)
	to the specified value in matrix. Pointer is updated automatically
	to continue to point at the same value. Can be grabbed with get Pointer
	@param pointer - the indexes that will be pointed to,
	will automatically be updated to point the same value
	@return void
	@throws pointer must be 1D array with 2 values
	*/
	public void setPointer(int[] pointer){
		if(pointer.length!=2){
			throw new IllegalArgumentException("Pointer must have a length of 2");
		}
		this.pointer = pointer;
	}
	/**
	Returns a pointer if it was initialized earlier to point to the same value
	as it initially pointed to.
	@param None
	@return pointer, a 1D array of two values (indexes of matrix)
	@throws if pointer was null
	*/
	public int[] getPointer(){
		return Arrays.copyOf(this.pointer, 2);
	}
	/**
	Finds the indexes of the first instance of Item i 
	@param i - the item to find
	@return 1D array of the location of Item i (first instance of it) indexes of format {row,col}
	*/
	public int[] find(Item i){
		return this.matrix.find(i);
	}
	/**
	Finds the indexes of the all instances of Item i <br>
	Note: Very memory inefficient on very large matrixes
	@param i - the item to find
	@return 2D array of the location of Item i (every instance of it) indexes of format {row,col}
	2D array has lengths of number of instances of i and 2 respectively
	*/
	public int[][] findAll(Item i){
		return this.matrix.findAll(i);
	}
	public String toString(){
		return this.matrix.toString();
	}
	/**
	Trims the matrix in the growingMatrix so that extra columns on the right or left
	or extra rows on the top or bottom that are completely null values are cut off. 
	Is not automatically involved by GrowingMatrix
	*/
	public void trim(){
		//trims the curretn matrix to make it reasonable 
		//find how for null goes down from top
		int numberOfRowsFromTop = 0;
		OuterLoop:
		for(int i =0;i<this.matrix.numRows;i++){
			for(int j =0;j<this.matrix.numCols;j++){
				if(this.matrix.get(new int[]{i,j})!=null)
					break OuterLoop;
			}
			numberOfRowsFromTop++;
		}
		/*
		  Find how many rows from the bottom should be dropped
		*/
		int numberOfRowsFromBottom = 0;
		OuterLoop:
		for(int i =this.matrix.numRows-1;i>0;i--){
			for(int j =0;j<this.matrix.numCols;j++){
				if(this.matrix.get(new int[]{i,j})!=null)
					break OuterLoop;
			}
			numberOfRowsFromBottom++;
		}
		int numberofColsfromLeft = 0;
		OuterLoop:
		for(int j =0;j<this.matrix.numCols;j++){ 
			for(int i =0;i<this.matrix.numRows;i++){
				if(this.matrix.get(new int[]{i,j})!=null)
					break OuterLoop;
			}
			numberofColsfromLeft++;
		}
		int numberofColsfromRight = 0;
		OuterLoop:
		for(int j =this.matrix.numCols-1;j>0;j--){ 
			for(int i =0;i<this.matrix.numRows;i++){
				if(this.matrix.get(new int[]{i,j})!=null)
					break OuterLoop;
			}
			numberofColsfromRight++;
		}
		Matrix<Item> matrix =  new Matrix<Item>(this.matrix.numRows-(numberOfRowsFromTop+numberOfRowsFromBottom),
				this.matrix.numCols-(numberofColsfromLeft+numberofColsfromRight));
		/*
		 Simpler to create two new incrementing indices to start at 0 than subtract the already created ones
		 */
		int indi = 0;
		int indj = 0;
		for(int i =numberOfRowsFromTop;i<this.matrix.numRows-numberOfRowsFromBottom;i++){
			for(int j =numberofColsfromLeft;j<this.matrix.numCols-numberofColsfromRight;j++){
				matrix.set(this.matrix.get(new int[]{i,j}), new int[]{indi,indj});
				indj++;
			}
			indj=0;
			indi++;
		}
		this.matrix = matrix;
		this.size[0] = this.matrix.numRows;
		this.size[1] = this.matrix.numCols;
	}
	public static void main(String[] args) {
		//testing
		Integer[][] x= {{1,2,3},{4,5,6}};
		GrowingMatrix<Integer> gm = new GrowingMatrix<Integer>(x,1);
		gm.setPointer(new int[]{0,0});
		gm.addItem(0,-1, 0);
		System.out.println(gm);
		GrowingMatrix<Integer> y = gm.clone(); //clone works
		y.addItem(0, 1, 1);
		System.out.println(gm);
		gm.insert(1, new int[]{0,1});
		System.out.println(gm);
		//as does copy
		Matrix<Integer> ma = y.getCopyOfMatrix(Integer.class);
		y.addItem(3, 4, 4);
		System.out.print(ma + "\n");
		System.out.println(y);
	}
}
