// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
        //if (!this.sanity()) return null;
        A1List current=this;
        if (address==-1 && key==-1 && size==-1) return null;
        A1List node=new A1List(address, size, key);
        
        current.next.prev=node;
        node.next=current.next;
        current.next=node;
        node.prev=current;
        return node;
    }

    public boolean Delete(Dictionary d) 
    {
        //if (!this.sanity()) return false;
        A1List current = this.getFirst();
        if (current==null) return  false;
        
        while(current.next!=null){
            if (current.key==d.key && current==d){
                current.prev.next=current.next;
                current.next.prev=current.prev;
                return true;
            }
            current=current.next;
        }
        return false;
    }

    public A1List Find(int k, boolean exact)
    { 
        //if (!this.sanity()) return null;
        A1List node=this.getFirst();
        if (node==null) return null;
        if (exact){
            while(node.next!=null){
                if(node.key==k) return node;
                node=node.next;    
            }
        }
        else{
            while(node.next!=null){
                if(node.key>=k) return node;
                node=node.next;    
            }    
        }
        return null;
    }

    public A1List getFirst()
    {
        A1List first=this;
        while(first.prev!=null){
            first=first.prev;
        }
        
        if (first.next==null) return null;
        first=first.next;
        if (first.key==-1 && first.address==-1 && first.size==-1) return null;
        return first;
    }
    
    public A1List getNext() 
    {
        if (this.next!=null && (this.next.key==-1 && this.next.address==-1 && this.next.size==-1)) return null;       
        return this.next;
    }

    public boolean sanity()
    {
         
        A1List node=this.getFirst();
        if (node==null){
            if (!(this.key==-1 && this.address==-1 && this.size==-1)) return false;
            if (this.next!=null){
                if (!(this.next.key==-1 && this.next.address==-1 && this.next.size==-1)) return false;  
                else return true;  
            }
            else if (this.prev!=null){
                if (!(this.prev.key==-1 && this.prev.address==-1 && this.prev.size==-1)) return false; 
                else return true;   
            }
            else return false;
        }
        if (!(node.prev.key==-1 && node.prev.address==-1 && node.prev.size==-1)) return false;
        while(node.getNext()!=null){
            if (node.next.prev!=node) return false;//circular list
            node=node.getNext();
        }
        node=node.next;
        if (!(node.key==-1 && node.address==-1 && node.size==-1)) return false;

        return true;
    }

    // public static void main(String[] args) {
    //     A1List d = new A1List();
	// 	d.Insert(0,15,15);
	// 	d.Insert(15,25,25);
	// 	d.Insert(40,15,15);
	// 	A1List x1 = d.Find(10,false);
	// 	A1List x2 = d.Find(25,true);
	// 	System.out.println(d.Delete(x1));
	// 	System.out.println(d.Delete(x2));
	// 	A1List x5 = d.Insert(89,12,12);
	// 	A1List x6 = x5.Insert(178,21,21);
	// 	System.out.println(x6.Delete(x1));
	// 	A1List x7 = x5.getFirst();
	// 	A1List x8 = x6.getNext();
	// 	A1List x9 = x7.getNext();
	// 	System.out.println(x1.address+" "+x1.key);
	// 	System.out.println(x2.address+" "+x2.key);
	// 	System.out.println(x5.address+" "+x5.key);
	// 	System.out.println(x6.address+" "+x6.key);
	// 	System.out.println(x7.address+" "+x7.key);
	// 	System.out.println(x8.address+" "+x8.key);
	// 	System.out.println(x9.address+" "+x9.key);
	// 	System.out.println(x9.sanity());
	// 	System.out.println("Print :");
	// 	for(A1List l = d.getFirst();l != null;l = l.getNext()){
	// 	    System.out.println(l.address+" "+l.key);
    //     }
    // }
}


