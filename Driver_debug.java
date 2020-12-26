import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Driver_debug {
    StringBuilder s=new StringBuilder();
    public String traverse(A2DynamicMem obj,Dictionary d){
        StringBuilder out= new StringBuilder();
        out.append("[H,");
        Dictionary current=d.getFirst();
        while(current!=null){
            if(current==d){
                out.append("(").append(current.address).append("->").append(current.size+current.address).append(")").append(",");
            }
            else{
                out.append(current.address).append("->").append(current.size+current.address).append(",");
            }
            current=current.getNext();
        }
        out.append("T]");
        return(out.toString());
    }

    public void testAllocate(A2DynamicMem obj,int size){
        s.append("Allocate:"+size).append("\n");
        if(obj.type==1) {
            A1List freeBlk = (A1List) obj.freeBlk;
            A1List allocBlk = (A1List) obj.allocBlk;
            obj.Allocate(size);
            s.append("-------------------\n");
            s.append("Free Block List\n");
            s.append(traverse(obj,freeBlk)).append("\n");
            s.append("Allocated Block List\n");
            s.append(traverse(obj,allocBlk)).append("\n");
            s.append("-------------------\n");
        }
        if(obj.type==2) {
            BSTree freeBlk = (BSTree) obj.freeBlk;
            BSTree allocBlk = (BSTree) obj.allocBlk;
            obj.Allocate(size);
            s.append("-------------------\n");
            s.append("Free Block List\n");
            s.append(traverse(obj,freeBlk)).append("\n");
            s.append("Allocated Block List\n");
            s.append(traverse(obj,allocBlk)).append("\n");
            s.append("-------------------\n");
        }
    }
    public void testFree(A2DynamicMem obj,int address){
        s.append("Free:"+address).append("\n");
        if(obj.type==1) {
            A1List freeBlk = (A1List) obj.freeBlk;
            A1List allocBlk = (A1List) obj.allocBlk;
            obj.Free(address);
            s.append("-------------------\n");
            s.append("Free Block List\n");
            s.append(traverse(obj,freeBlk)).append("\n");
            s.append("Allocated Block List\n");
            s.append(traverse(obj,allocBlk)).append("\n");
            s.append("-------------------\n");
        }
        if(obj.type==2) {
            BSTree freeBlk = (BSTree) obj.freeBlk;
            BSTree allocBlk = (BSTree) obj.allocBlk;
            obj.Free(address);
            s.append("-------------------\n");
            s.append("Free Block List\n");
            s.append(traverse(obj,freeBlk)).append("\n");
            s.append("Allocated Block List\n");
            s.append(traverse(obj,allocBlk)).append("\n");
            s.append("-------------------\n");
        }
    }
    public void testDefragment(A2DynamicMem obj) {
        if (obj.type == 2) {
            BSTree freeBlk = (BSTree) obj.freeBlk;
            BSTree allocBlk = (BSTree) obj.allocBlk;
            s.append("Defragment\n");
            obj.Defragment();
            s.append("-------------------\n");
            s.append("Free Block List\n");
            s.append(traverse(obj,freeBlk)).append("\n");
            s.append("Allocated Block List\n");
            s.append(traverse(obj,allocBlk)).append("\n");
            s.append("-------------------\n");
        }
    }
    public static void main(String args[]) throws IOException {
        Driver_debug driver=new Driver_debug();
        Scanner temp=new Scanner(System.in);
        System.out.println("Enter bug line");
        int var=temp.nextInt();
        File myObj = new File("./A2_test.in");
        FileWriter fw=new FileWriter("./debout.txt");
        Scanner sc = new Scanner(myObj);

        int numTestCases;
        numTestCases = sc.nextInt();
        int i=0;
        while (numTestCases-- > 0) {
            i++;
            int size;
            size = sc.nextInt();
            A2DynamicMem obj = new A2DynamicMem(size,1);
            int numCommands = sc.nextInt();

            while (numCommands-- > 0) {
                    i++;
                String command;
                command = sc.next();
                int argument;
                argument = sc.nextInt();
                int result = -5;
                if(i>var-5 && i<var+5) {
                    switch (command) {
                        case "Allocate":
                            driver.testAllocate(obj, argument);
                            break;
                        case "Free":
                            driver.testFree(obj, argument);
                            break;
                        case "Defragment":
                            driver.testDefragment(obj);
                        default:
                            break;
                    }
                }
                else{
                    switch (command) {
                        case "Allocate":
                            obj.Allocate();
                            break;
                        case "Free":
                            obj.Free();
                            break;
                        case "Defragment":
                            obj.Defragment();
                        default:
                            break;
                    }
                }
            }
        }
        fw.write(driver.s.toString());
        fw.close();
        sc.close();
    }
}