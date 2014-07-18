import java.util.*;

/*
** Insertion sort will take a single array, and partition it into [sorted][unsorted]
** While sorting the array, keep track of its index. (Where the partition is.)
*/  

class InsertionSort <T extends Comparable<T>>{
  public void sortIter(T data[], int x){ // Sort the array, where n is the sorted partition.
    int i, j;
    T tmp;
    for(i=1;i<x;i++){
      j=i;
      tmp = data[i];
      while(j>0 && data[i].compareTo(data[j-1]) < 1) data[j] = data[--j];
      data[j] = tmp;
    }
  }
  public void sortRec(T data[], int x){ // Sort the array recursivly, where n is the sorted partition.
    T tmp = null;
    if(x+1 < data.length){ // This will also solve the problem of a 1 element array.
      for(int r=0;r<x;r++){
        if(data[x].compareTo(data[r]) < 0){ // Make sure we hold the sorting correctly.
          tmp = data[r];
          for(int p=r;p>=0;p--) data[p] = data[p-1]; // Shift it all down.
          data[r] = data[x]; // Move the element into place.
          r=x;
        }
      }
      sortRec(data, x++);
    }
  }
}
