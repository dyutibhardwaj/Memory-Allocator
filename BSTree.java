// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    private BSTree goToRoot(){
        BSTree current=this;
        if (this.isSent()) return this.right;
        while(!current.parent.isSent()){
            current=current.parent;
        }
        return current;
    }

    private boolean isSent(){
        if (this.parent==null && this.left==null && this.key==-1 && this.address==-1 && this.size==-1) return true;
        return false;
    }

    public BSTree Insert(int address, int size, int key) 
    { 
        //if (!this.sanity()) return null;
        BSTree node=new BSTree(address, size, key);
        BSTree x=this.goToRoot();
        BSTree y=this;
        
        while(x!=null){
            y=x;
            if (key==x.key){
                if (address<=x.address) x=x.left;
                else x=x.right;
            }
            else if (key<x.key) x=x.left;   
            else x=x.right; 
        }
        node.parent=y;
        if (y.isSent()) y.right=node;
        if (key<y.key) y.left=node;
        else if (key== y.key){
            if (address<=y.address) y.left=node;
            else y.right=node;
        }
        else y.right=node;
        return node;
    }

    public boolean Delete(Dictionary e)
    { 
        //if (!this.sanity()) return false;
        if (e==null) return false;
        if (this.goToRoot()==null) return false;
        BSTree element=this.goToRoot().Find(e.key,true);
        if (element==null) return false;
        boolean flag=false;
        while(element!=null && element.key==e.key){
            if (element.address==e.address && element.size==e.size) {
                flag=true;
                break;
            }
            else if (e.address<element.address) element=element.left;
            else element=element.right;
            
        }
        if (flag==false) return false;
        del(element);    

        return true;
    }

    static private void del(BSTree a){
        if (a.left==null && a.right==null) {
            
            if (a==a.parent.left) a.parent.left=null;
            else a.parent.right=null;
        }
        else if(a.left!=null && a.right==null){
            
            if (a==a.parent.left) {
                a.parent.left=a.left;
                a.left.parent=a.parent;
            }
            else {
                a.parent.right=a.left;
                a.left.parent=a.parent;
            }
        }
        else if(a.right!=null && a.left==null ){
            
            if (a==a.parent.left) {
                a.parent.left=a.right;
                a.right.parent=a.parent;
            }
            else {
                a.parent.right=a.right;
                a.right.parent=a.parent;
            }
        }
        else{
            
            BSTree temp=a.getNext();
            del(temp);
            a.key=temp.key;
            a.size=temp.size;
            a.address=temp.address;
            
        }
        
    }

        
    public BSTree Find(int key, boolean exact)
    { 
        //if (!this.sanity()) return null;
        BSTree current=this.goToRoot();
        if (exact){
            
            while (current!=null && current.key!=key){
                
                if (current.key>key) current=current.left;
                else current=current.right;
            }
            if (current==null) return null;
            else return current;
        }   
        else{
            current=this.getFirst();
            while (current!=null && current.key<key){
                current=current.getNext();
                
            }
            return current;
 
        }
    }

    public BSTree getFirst()
    { 
        BSTree current=this.goToRoot();
        //if (this.parent==null) current=this.right;
        if (current==null) return null;
        while(current.left!=null){
            current=current.left;
        }
        return current;
    }

    public BSTree getNext()
    { 
        BSTree current=this;
        
        if (current.right!=null){
            current=current.right;
            while(current.left!=null){
                current=current.left;
            }
            return current;    
        }
        else{
            while(current!=null && current.parent!=null && current!=current.parent.left ){
                current=current.parent;
            }
            if (current==null) return null;
            if (current.parent==null) return null;
            return current.parent;
        }    
    }

    public boolean sanity()
    { 
        BSTree root=this.goToRoot();
        if (root==null)
        {
            if (!this.isSent()) return false;
        }
        else{
            if (root.parent==null) return false;
            if (!root.parent.isSent()) return false;
            root=this.getFirst();
            while (root!=null){
                if (root.left!=null){
                    if (root!=root.left.parent) return false;
                    if ((root.key<root.left.key || (root.key==key && root.address<root.left.address))) return false;
                }
                 
                if (root.right!=null){
                    if (root!=root.right.parent) return false;
                    if (root.key>=root.right.key) return false;
                } 
                
                root=root.getNext();  
            }
        }
        
        return true;
    }

}