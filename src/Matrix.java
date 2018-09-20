import java.lang.reflect.Array;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
//currently functionality is limited as this class was mainly created for Growing Matrix class
//but more functionality will be added
/**
 * 
 * @author Rajiv Sarvepalli
 *
 * @param <Item> genreic 
 */
public class Matrix<Item> implements Iterable<Item>{
	private Item[][] matrix;
	private int[] size;
	public final Integer numRows;
	public final Integer numCols;
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
	public Matrix(int numRows, int numCols){
		this.matrix = (Item[][])new Object[numRows][numCols];
		this.size=new int[]{numRows,numCols};
		this.numRows = matrix.length;
		this.numCols = matrix[0].length;
	}
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
	public boolean equals(Object o){
		Matrix<Item> other = (Matrix<Item>)o;
		return Arrays.deepEquals(other.get2DArray(), this.matrix);
	}
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
	public Iterator<Item> iterator() {
        return new MatrixIterator();
    }
	public Matrix<Item> clone(){
		Item[][] matrix = (Item[][])new Object[this.matrix.length][this.matrix[0].length];
		for(int i=0;i<this.matrix.length;i++){
			matrix[i] = Arrays.copyOf(this.matrix[i], this.matrix[i].length);
		}
		return new Matrix<Item>(matrix);
	}
	public void set(Item i, int[] index){
		if(index.length!=2)
			throw new IllegalArgumentException("index length must equal 2");
		if(index[0]>this.numRows||index[1]>this.numCols||index[0]<0||index[1]<0)
			throw new IllegalArgumentException("index out of bounds");
		this.matrix[index[0]][index[1]] = i;	
	}
	public void set(Item[] items, int[][] indexes){
		if(indexes[0].length!=2){
			throw new IllegalArgumentException("length of ind must be 2");
		}
		int itemCounter=0;
		for(int[] row: indexes){
			this.matrix[row[0]][row[1]] = items[itemCounter++]; 
		}
	}
	public Item get(int[] index){
		if(index.length!=2)
			throw new IllegalArgumentException("index length must equal 2");
		if(index[0]>this.numRows||index[1]>this.numCols||index[0]<0||index[1]<0)
			throw new IllegalArgumentException("index out of bounds");
		return this.matrix[index[0]][index[1]];
	}
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
	//like python list indexing (very easy to get certain portion of array, very slow though)
	//start inclusive end noninclusive
	//start and end start at 0
	public Item[][] get(int start, int end, boolean rowIndexing){
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
	public static void main(String[] args) {
		Integer[][] m= new Integer[][]{{1,2,3},{3,4,5},{6,5,4}};
		Matrix<Integer> matrix = new Matrix<Integer>(m);
		//works like python's myListOfLists[0:2,:] row slice or myListOfLists[:,0:2] column slice
		System.out.println(Arrays.deepToString(matrix.get(0,2,false)));
	}
}
