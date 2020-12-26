// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).
    // While inserting into the list, only call insert at the head of the list
    // Please note that ALL insertions in the DLL (used either in A1DynamicMem or used independently as the “dictionary” class implementation) are to be made at the HEAD (from the front).
    // Also, the find-first should start searching from the head (irrespective of the use for A1DynamicMem). Similar arguments will follow with regards to the ROOT in the case of trees (specifying this in case it was not so trivial to anyone of you earlier)
    public int Allocate(int blockSize) {
        if (blockSize<=0) return -1;
        A1List block=(A1List)this.freeBlk.Find(blockSize,false);
        if (block==null) return -1;
        int add=block.address;
        
        
        if (block.size==blockSize) {
            this.freeBlk.Delete(block);
            this.allocBlk.Insert(add, block.size, add);
        }
    	else{
    	    this.allocBlk.Insert(add,blockSize,add);
            block.size-=blockSize;
            block.key-=blockSize;
            block.address+=blockSize;
            this.freeBlk.Delete(block);
            this.freeBlk.Insert(block.address, block.size, block.key);
        }
        
        return add;
    }  
    // return 0 if successful, -1 otherwise
    public int Free(int startAddr) {
        if (startAddr<0) return -1;
        A1List block=(A1List)this.allocBlk.Find(startAddr,true);
        if (block==null) return -1;
        //System.out.println(block.address);
        int s=block.size;
        this.freeBlk.Insert(startAddr,s,s);
        this.allocBlk.Delete(block);
        return 0;
    }

    // public static void main(String[] args) {
    //     // A1DynamicMem a = new A1DynamicMem(100);
	// 	// System.out.println(a.Allocate(20));
	// 	// System.out.println(a.Allocate(50));
	// 	// System.out.println(a.Allocate(20));
	// 	// System.out.println(a.Allocate(5));
	// 	// System.out.println(a.Free(20));
	// 	// System.out.println(a.Free(0));
    //     // System.out.println(a.Free(70));
        
        

    //     A1DynamicMem a = new A1DynamicMem(20);
	//     System.out.println(a.Allocate(10));
    //     System.out.println(a.Allocate(20));
    //     System.out.println(a.Free(50));
	// 	System.out.println(a.Free(0));
	// 	System.out.println(a.Free(10));
    // }
}