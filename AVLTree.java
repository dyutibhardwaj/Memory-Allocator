import java.util.*;
// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    
    
    
    private AVLTree goToRoot(){
        AVLTree current=this;
        if (this.isSent()) return this.right;
        while(current!=null && current.parent!=null && !current.parent.isSent()){
            current=current.parent;
        }
        return current;
    }

    private boolean isSent(){
        if (this.parent==null && this.left==null && this.key==-1 && this.address==-1 && this.size==-1) return true;
        return false;
    }
    
    private int height(AVLTree node){
        if (node==null) return -1;
        if (node.left==null && node.right==null) return 1;
        else if (node.left==null && node.right!=null) return 1+node.right.height;
        else if (node.left!=null && node.right==null) return 1+node.left.height;
        return (1+((node.left.height>=node.right.height)?node.left.height:node.right.height));    
    }

    private int h_imbalance(AVLTree node){
        if (node.left==null && node.right==null) return 0;
        else if (node.left==null && node.right!=null) return -node.right.height;
        else if (node.left!=null && node.right==null) return node.left.height;
        return (node.left.height-node.right.height);
    }

    private void right_rotate(AVLTree node){
        
        AVLTree temp=node.left;
        node.left=temp.right;
        if (node.left!=null) node.left.parent=node;
        temp.right=node;
        temp.parent=node.parent;
        if (node.parent!=null){
            if (node==node.parent.left) node.parent.left=temp;
            else node.parent.right=temp;
        }
        
        node.parent=temp;
        node.height=height(node);
        temp.height=height(temp);
    }

    private void left_rotate(AVLTree node){
        
        AVLTree temp=node.right;
        node.right=temp.left;
        if (node.right!=null) node.right.parent=node;
        temp.left=node;
        temp.parent=node.parent;
        if (node.parent!=null){
            if (node==node.parent.left) node.parent.left=temp;
            else node.parent.right=temp;
        }
        
        node.parent=temp;
        node.height=height(node);
        temp.height=height(temp);
    }

    private AVLTree BSTInsert(int address, int size, int key) 
    { 
        //if (!this.sanity()) return null;
        AVLTree node=new AVLTree(address, size, key);
        AVLTree x=this.goToRoot();
        AVLTree y=this;
        
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
    
    public AVLTree Insert(int address, int size, int key) 
    { 
        //if (!this.sanity()) return null;
        AVLTree elem=this.BSTInsert(address, size, key);
        AVLTree x=elem;
        x.height=height(x);
        AVLTree y=x.parent;
        //if (y==null) return null;//not a sane avl tree as no sentinel
        y.height=height(y);
        AVLTree z=y.parent;
        if (z==null) return x;
        z.height=height(z);
        while( !z.isSent() && z.parent!=null && h_imbalance(z)<=1 && h_imbalance(z)>=-1){
            x=y;
            y=z;
            z=z.parent;  
            z.height=height(z);  
        }
        if (z.isSent()) return elem;
        //left left case
        if (y==z.left && x==y.left) right_rotate(z);
        //left right case
        else if (y==z.left && x==y.right) {
            left_rotate(y);
            right_rotate(z);
        }
        //right right case
        else if (y==z.right && x==y.right) left_rotate(z);
        //right left case
        else if (y==z.right && z==y.left){
            right_rotate(y);
            left_rotate(z);
        }
        
        return elem;
    }

    public boolean Delete(Dictionary e)
    {
        //if (!this.sanity()) return false;
        if (e==null) return false;
        AVLTree current=this.getFirst();
        //System.out.println(current.key);
        
        while(current!=null && (current.key!=e.key || current.address!=e.address || current.size!=e.size)){
            current=current.getNext();
            //System.out.println(current.key);
        }    

        if (current==null) return false;
        AVLTree temp=del(current);
        //if (temp==null) return false //not sane avl tree as no sentinel
        
        AVLTree z=temp;

        AVLTree x,y;
        while(z!=null && z.parent!=null && !z.isSent()){
            z.height=height(z);
            //System.out.println(z.height);
            if (h_imbalance(z)>1 || h_imbalance(z)<-1) {
                if (h_imbalance(z)>1){
                    y=z.left;
                    //System.out.println(y.key);
                    //System.out.println(y.left.height + " " +y.right.height);
                    //left left
                    if (h_imbalance(y)>=0) {
                        //System.out.println(h_imbalance(y));
                        x=y.left;
                        //System.out.println(x.key);
                        right_rotate(z);
                    }
                    //left right
                    else{
                        x=y.right;
                        //System.out.println(x.key);
                        left_rotate(y);
                        //System.out.println(x.left.key + x.parent.key);
                        right_rotate(z);
                    }
                    
                }
                else {
                    y=z.right;
                    //System.out.println(y.key);
                    //right left
                    if (h_imbalance(y)>0) {
                        x=y.left;
                        //System.out.println(x.key);
                        right_rotate(y);
                        left_rotate(z);
                    }
                    //right right
                    else{
                        x=y.right;
                        //System.out.println(x.key);
                        left_rotate(z);
                    }
                }
            }
            z=z.parent;
        }
        return true;


    }

    static private AVLTree del(AVLTree a){
        AVLTree p=a.parent;
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
            
            AVLTree temp=a.getNext();
            p=temp.parent;
            del(temp);
            a.key=temp.key;
            a.size=temp.size;
            a.address=temp.address;
        }
        return p;
    }

    
        
    public AVLTree Find(int k, boolean exact)
    { 
        //if (!this.sanity()) return null;
        AVLTree current=this.goToRoot();
        
        if (exact){
            
            while (current!=null && current.key!=k){
                //System.out.println(current.address);
                if (current.key>k) current=current.left;
                else current=current.right;
                
            }
            if (current==null) return null;
            else return current;
        }
        else{
            
            while (current!=null && current.key<k){
                current=current.right;
                
            }
            if (current==null) return null;
            //System.out.println(current.key);
            while(current.left!=null && current.left.key>=k){
                current=current.left;
            }
            
            //if (current==null || current.parent==null) return null;
            return current;
 
        }
    }

    public AVLTree getFirst()
    { 
        AVLTree current=this.goToRoot();
        //if (this.parent==null) current=this.right;
        if (current==null) return null;
        while(current.left!=null){
            current=current.left;
        }
        return current;
    }

    public AVLTree getNext()
    {
        AVLTree current=this;
        
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
        AVLTree root=this.goToRoot();
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
                    if ((root.key<root.left.key || (root.key==root.left.key && root.address<root.left.address))) return false;
                }
                 
                if (root.right!=null){
                    if (root!=root.right.parent) return false;
                    if (root.key>=root.right.key) return false;
                } 

                if (!(h_imbalance(root)<=1 && h_imbalance(root)>=-1)) return false;
                
                root=root.getNext();  
            }
        }
        
        return true;
    }

    static void printLevelOrder(AVLTree root) 
    { 
        // Base Case 
        if(root == null) 
            return; 
          
        // Create an empty queue for level order tarversal 
        Queue<AVLTree> q =new LinkedList<AVLTree>(); 
          
        // Enqueue Root and initialize height 
        q.add(root); 
          
          
        while(true) 
        { 
              
            // nodeCount (queue size) indicates number of nodes 
            // at current level. 
            int nodeCount = q.size(); 
            if(nodeCount == 0) 
                break; 
              
            // Dequeue all nodes of current level and Enqueue all 
            // nodes of next level 
            while(nodeCount > 0) 
            { 
                AVLTree node = q.peek(); 
                System.out.print(node.key +" "); 
                q.remove(); 
                if(node.left != null) 
                    q.add(node.left); 
                if(node.right != null) 
                    q.add(node.right); 
                nodeCount--; 
            } 
            System.out.println(); 
        } 
    } 

    public static void main(String[] args) {
        AVLTree tree=new AVLTree();
        
        tree.Insert(1, 1, 10);
        printLevelOrder(tree.goToRoot());
        System.out.println();
        //System.out.println(111111);
        tree.Insert(1, 1, 20);
        printLevelOrder(tree.goToRoot());
        System.out.println();
        
        //System.out.println(222222);
        tree.Insert(1, 1, 15);
        printLevelOrder(tree.goToRoot());
        System.out.println();
        
        //System.out.println(3);
        tree.Insert(1, 1, 25);
        printLevelOrder(tree.goToRoot());
        System.out.println();
        
        //System.out.println(4);
        AVLTree a=tree.Insert(1, 1, 30);
        printLevelOrder(tree.goToRoot());
        System.out.println();
        
        //System.out.println(5);
        tree.Insert(1, 1, 16);
        printLevelOrder(tree.goToRoot());
        System.out.println();
        
        //System.out.println(6);
        tree.Insert(1, 1, 18);
        printLevelOrder(tree.goToRoot());
        System.out.println();
        
        //System.out.println(7);
        tree.Insert(1, 1, 19);
        printLevelOrder(tree.goToRoot());
        System.out.println();
        Dictionary test=new AVLTree(1,1,20);
        tree.Delete(test);
        System.out.println();
        
        
        printLevelOrder(tree.goToRoot());
        System.out.println();
    }
}