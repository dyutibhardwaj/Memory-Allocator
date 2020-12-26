

// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    @Override
    public int Allocate(int blockSize) {
        if (blockSize<=0) return -1;
        if (this.freeBlk==null) return -1;
        Dictionary block=this.freeBlk.Find(blockSize,false);
        //System.out.println(block.key);
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
        // Dictionary t=this.freeBlk.getFirst();
        // while(t!=null){
        //     System.out.println(t.address);
        //     System.out.println(t.size);
        //     t=t.getNext();
        // }
        
        //System.out.println(block.key+"\n");
        return add;
    }  
    // return 0 if successful, -1 otherwise
    @Override
    public int Free(int startAddr) {
        if (startAddr<0) return -1;
        if (this.allocBlk==null) return -1;
        Dictionary block=this.allocBlk.Find(startAddr,true);
        if (block==null) return -1;
        //System.out.println(1);
        //System.out.println(block.address);
        int s=block.size;
        this.freeBlk.Insert(startAddr,s,s);
        //System.out.println(block.address +" " +block.size+"\n");
        
        this.allocBlk.Delete(block);

        
        return 0;
    }


    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    public void Defragment() {
        Dictionary dummy=new AVLTree();
        Dictionary temp=this.freeBlk.getFirst();
        if (temp==null) return ;
        while (temp!=null){
            dummy.Insert(temp.address, temp.size, temp.address);
            temp=temp.getNext();
        }
        
        temp=dummy.getFirst();
        
        Dictionary tn=temp.getNext();
        while(tn!=null){
            if (temp.address+temp.size==tn.address){
                
                this.freeBlk.Delete(new BSTree(temp.address, temp.size, temp.size));
                
                this.freeBlk.Delete(new BSTree(tn.address, tn.size, tn.size));
                
                temp.size+=tn.size;
                this.freeBlk.Insert(temp.address, temp.size, temp.size);
                
                
                dummy.Delete(tn);
                dummy.Delete(temp);
                temp=dummy.Insert(temp.address, temp.size, temp.address);
                tn=temp.getNext();
            }
            else{
                temp=tn;
                tn=tn.getNext();
            }
            
        }
        // Dictionary t=this.freeBlk.getFirst();
        // while(t!=null){
        //     System.out.println(t.address + " " + t.size);
        //     t=t.getNext();
        // }
        
    }

    public static void main(String[] args) {
        A2DynamicMem a= new A2DynamicMem(100,3);
        System.out.println(a.Allocate(10));
        
        System.out.println(a.Allocate(10));
        
        System.out.println(a.Allocate(10));
        
        System.out.println(a.Free(30));
        
        System.out.println(a.Free(10));
     
        System.out.println(a.Free(0));

        System.out.println(a.Free(20));
   
        a.Defragment();
       
        System.out.println(a.Allocate(100));
    }
}