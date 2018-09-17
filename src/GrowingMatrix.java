
import java.util.Arrays;
/**
Growing Matrix - a matrix that supports growing in negative and positive directions <br> 
Features - a pointer to point to value that updates to continue to point to the same value <br>
-a factor to multiply how much the matrix is growing by   
*/
public class GrowingMatrix<Item>{
	//grows in any way you want (negative too) 
	private Item[][] matrix;
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
		this.matrix = matrix;
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
		this.matrix = (Item[][])new Object[1][1];
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
		this.matrix = (Item[][])new Object[1][1];
	}
	/**
	Adds an item at the specified indices given to the matrix.
	The Matrix fills all empty slots with null.
	Int i and Int j can be negative and bigger than matrix
	@param The Item i to add, the indices to which it goes
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
			if(i>=this.matrix.length)
				addx = i-this.matrix.length+1;
		}
		if(j<=0){
			addy =-j;
		}
		else{
			if(j>=this.matrix[0].length)
				addy = j-this.matrix[0].length+1;
		}
		if(addx>0||addy>0){
			Item[][] matrix = (Item[][]) new Object[this.matrix.length+(int)(addx*this.growthFactor)][this.matrix[0].length+(int)(addy*this.growthFactor)];
			for(int a =0;a<this.matrix.length;a++){
				for(int b =0;b<this.matrix[0].length;b++){
					if(negativei&&negativej){
						matrix[a+(int)(addx*this.growthFactor)][b+(int)(addy*this.growthFactor)] = this.matrix[a][b]; //siwtched from just addx and addy
					}
					else if(negativei){
						matrix[a+(int)(addx*this.growthFactor)][b] = this.matrix[a][b];
					}
					else if(negativej){
						matrix[a][b+(int)(addy*this.growthFactor)] = this.matrix[a][b];
					}
					else{
						matrix[a][b] = this.matrix[a][b];
					}
				}
			}
			this.matrix = matrix;
		}
		if(negativei&&negativej){
			this.matrix[-addx+(int)(addx*this.growthFactor)][-addy+(int)(addy*this.growthFactor)] = item;
			if(pointer!=null){
				this.pointer[0] = this.pointer[0]+(int)(addx*this.growthFactor);
				this.pointer[1] = this.pointer[1]+(int)(addy*this.growthFactor);
			}
		}
		else if(negativei){
			this.matrix[-addx+(int)(addx*this.growthFactor)][0] = item;
			if(pointer!=null){
				this.pointer[0] = this.pointer[0]+(int)(addx*this.growthFactor);
			}
		}
		else if(negativej){
			this.matrix[0][-addy+(int)(addy*this.growthFactor)] = item;
			if(pointer!=null){
				this.pointer[1] = this.pointer[1]+(int)(addy*this.growthFactor);
			}
		}
		else{
			this.matrix[i][j] = item;
		}
		this.size[0] = this.matrix.length;
		this.size[1] = this.matrix[0].length;
	}
	/**
	Adds an items at the specified indices given to the matrix.
	The Matrix fills all empty slots with null.
	indices inside ind can be negative and bigger than matrix
	@param array items - 1D array of items to add
	@param matrix ind - 2D array
	@return void
	*/
	public void addItems(Item[] items,int[][] ind){
		if(ind[0].length!=2){
			throw new IllegalArgumentException("ind must have 2 elements per row");
		}
		if(ind.length!=items.length){
			throw new IllegalArgumentException("number of rows in ind must equal length of items");
		}
		for(int i=0;i<items.length;i++){
			this.addItem(items[i],ind[i][0],ind[i][1]);
		}
	}
	/**
	Compares two GrowingMatrix and tells if matrix are equals
	Note: does not check pointers, growthFactor or anything else
	@param Object o to compare to
	@return true if equal, false if not
	@throws If object o is not a GrowingMatrix
	*/
	public boolean equals(Object o){
		GrowingMatrix<Item> other = (GrowingMatrix<Item>)o;
		return Arrays.deepEquals(other.getMatrix(), this.matrix);
	}
	/**
	Creates a deep copy of GrowingMatrix
	@param void
	@return a deep copy of this (GrowingMatrix)
	*/
	public GrowingMatrix<Item> clone(){
		Item[][] matrix = (Item[][])new Object[this.matrix.length][this.matrix[0].length];
		for(int i=0;i<this.matrix.length;i++){
			matrix[i] = Arrays.copyOf(this.matrix[i], this.matrix[i].length);
		}
		return new GrowingMatrix<Item>(matrix,this.growthFactor);
	}
	protected Item[][] getMatrix(){
		//Don't use because the matrix was soft casted and being accessed outside of class will mess it up
		return this.matrix;
	}
	/**
	Get size of matrix in GrowingMatrix
	@param void
	@return the matrix's size in 1D array with format {matrix.length,matrix[0].length}
	*/
	public int[] getSize(){
		return this.size;
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
	as it intially pointed to.
	@param None
	@return pointer, a 1D array of two values (indexes of matrix)
	@throws if pointer was null
	*/
	public int[] getPointer(){
		return Arrays.copyOf(this.pointer, 2);
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
	Trims the matrix in the growingMatrix so that extra columns on the right or left
	or extra rows on the top or bottom that are completely null values are cut off. 
	Is not automatically involved by GrowingMatrix
	*/
	public void trim(){
		//trims the curretn matrix to make it reasonable 
		//find how for null goes down from top
		int numberOfRowsFromTop = 0;
		OuterLoop:
		for(int i =0;i<this.matrix.length;i++){
			for(int j =0;j<this.matrix[0].length;j++){
				if(this.matrix[i][j]!=null)
					break OuterLoop;
			}
			numberOfRowsFromTop++;
		}
		/*
		  Find how many rows from the bottom should be dropped
		*/
		int numberOfRowsFromBottom = 0;
		OuterLoop:
		for(int i =this.matrix.length-1;i>0;i--){
			for(int j =0;j<this.matrix[0].length;j++){
				if(this.matrix[i][j]!=null)
					break OuterLoop;
			}
			numberOfRowsFromBottom++;
		}
		int numberofColsfromLeft = 0;
		OuterLoop:
		for(int j =0;j<this.matrix[0].length;j++){ 
			for(int i =0;i<this.matrix.length;i++){
				if(this.matrix[i][j]!=null)
					break OuterLoop;
			}
			numberofColsfromLeft++;
		}
		int numberofColsfromRight = 0;
		OuterLoop:
		for(int j =this.matrix[0].length-1;j>0;j--){ 
			for(int i =0;i<this.matrix.length;i++){
				if(this.matrix[i][j]!=null)
					break OuterLoop;
			}
			numberofColsfromRight++;
		}
		Item[][] matrix = (Item[][]) new Object[this.matrix.length-(numberOfRowsFromTop+numberOfRowsFromBottom)]
				[this.matrix[0].length-(numberofColsfromLeft+numberofColsfromRight)];
		/*
		 Simpler to create two new incrementing indices to start at 0 than subtract the already created ones
		 */
		int indi = 0;
		int indj = 0;
		for(int i =numberOfRowsFromTop;i<this.matrix.length-numberOfRowsFromBottom;i++){
			for(int j =numberofColsfromLeft;j<this.matrix[0].length-numberofColsfromRight;j++){
				matrix[indi][indj] = this.matrix[i][j];
				indj++;
			}
			indj=0;
			indi++;
		}
		this.matrix = matrix;
		this.size[0] = this.matrix.length;
		this.size[1] = this.matrix[0].length;
	}
	public static void main(String[] args) {
		//size and pointer still broke
		Integer[][] x= {{1,2,3},{4,5,6}};
		GrowingMatrix<Integer> gm = new GrowingMatrix<Integer>(x,1);
		gm.setPointer(new int[]{0,0});
		gm.addItem(0,-1, 0);
		System.out.println(gm);
		GrowingMatrix<Integer> y = gm.clone();
		y.addItem(0, 1, 1);
		System.out.println(gm);
		System.out.println(y);
		System.out.println(gm.equals(y));
		//System.out.print(Arrays.deepToString(gm.getMatrix()));
	}
}
