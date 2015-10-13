import java.util.*;

public class MultiDS<T> implements PrimQ<T>, Reorder
{
	
	private T[] arr;
	private int numItems;
	
	public MultiDS(int size)
	{
		arr = (T[]) new Object[size];
		numItems = 0;
	}
	
	// From PrimQ
	// Add a new Object to the PrimQ in the next available location.  If
	// all goes well, return true.  If there is no room in the PrimQ for
	// the new item, return false (you do NOT have to resize it)
	public boolean addItem(T item)
	{
		boolean result = true;
		if ( full() ) 
		{
			result = false;
		}
		
		else
		{
			arr[numItems] = item;
			numItems++;
		}
		return result;
	}
	
	// Remove and return the "oldest" item in the PrimQ.  If the PrimQ
	// is empty, return null
	public T removeItem()
	{
		T item = null;
		if ( !empty() )
		{
			// Save leftmost (oldest) item into a variable, shift array to the 
			// left, and then delete the item from the array. (shiftLeft()
			// moves it to the right end.)
			item = arr[0];
			shiftLeft();
			arr[numItems - 1] = null;
			numItems -= 1;
		}
		return item;
	}
	
	// Return true if the PrimQ is full, and false otherwise
	public boolean full()
	{
		return numItems == arr.length;
	}
	
	// Return true if the PrimQ is empty, and false otherwise
	public boolean empty()
	{
		return numItems == 0;
	}
	
	// Return the number of items currently in the PrimQ
	public int size()
	{
		return numItems;
	}

	// Reset the PrimQ to empty status by reinitializing the variables
	// appropriately
	public void clear()
	{
		arr = (T[]) new Object[arr.length];
		numItems = 0;
	}
	
	// From Reorder
	// Logically reverse the data in the Reorder object so that the item
	// that was logically first will now be logically last and vice
	// versa.  The physical implementation of this can be done in 
	// many different ways, depending upon how you actually implemented
	// your physical MultiDS class
	public void reverse()
	{
		// Create a duplicate array, clear old array, and then
		// add everything to the old array in reverse order.
		T[] newArr = arr;
		// Necessary because clear() sets numItems to 0.		
		int count = size();
		clear();
		for ( int i=count-1; i>=0; i-- )
		{
			addItem(newArr[i]);
		}
	}

	// Remove the logical last item of the DS and put it at the 
	// front.  As with reverse(), this can be done physically in
	// different ways depending on the underlying implementation.  
	public void shiftRight()
	{
		// Save last item, shift everything else right, and then 
		// put last item in the front.
		T last = arr[numItems-1];
		for (int i=numItems-1; i>0; i--)
		{
			arr[i] = arr[i-1];
		}
		arr[0] = last;
	}

	// Remove the logical first item of the DS and put it at the
	// end.  As above, this can be done in different ways.
	public void shiftLeft()
	{
		// Same basic idea as shiftRight.
		T first = arr[0];
		for ( int i=0; i<numItems-1; i++ )
		{
			arr[i] = arr[i+1];
		}
		arr[numItems-1] = first;
	}
	
	// Reorganize the items in the object in a pseudo-random way.  The exact
	// way is up to you but it should utilize a Random object (see Random in 
	// the Java API)
	public void shuffle()
	{
		// Create random object and copy arr over to newArr. Clear out arr.
		Random r = new Random();
		T[] newArr = arr;
		// Necessary because clear() sets numItems to 0.		
		int count = size();
		clear();
		
		// Generate a random index. Add the item at this index in newArr
		// to arr, and then delete that item from newArr. Repeat the
		// process, except check to see that the index you generate has
		// not already been picked. (If it has, newArr[index] will be
		// null.)
		for ( int i=0; i<count; i++ )
		{
			int index = r.nextInt(count);
			while ( newArr[index] == null )
			{
				index = r.nextInt(count);
			}
			addItem(newArr[index]);
			// Clear out the item from newArr so that it doesn't get
			// picked again.
			newArr[index] = null;
		}
	}
	
	public String toString()
	{
		String line1 = "Contents:\n";
		String line2 = "";
		for ( int i=0; i<numItems; i++ )
		{
			line2 += arr[i].toString() + " ";
		}
		return line1 + line2;
	}
}