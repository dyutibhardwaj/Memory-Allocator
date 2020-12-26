import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Driver_alt {
    public static void main(String args[]) throws IOException{
        File myObj = new File("1myfile.txt");
        FileWriter fw=new FileWriter("output.txt");
        Scanner sc = new Scanner(myObj);
        int numTestCases;
        numTestCases = sc.nextInt();
        while (numTestCases-- > 0) {
            int size;
            size = sc.nextInt();
            A2DynamicMem obj = new A2DynamicMem(size,3);
            int numCommands = sc.nextInt();
            while (numCommands-- > 0) {
                String command;
                command = sc.next();
                int argument;
                
                int result = -5;
                switch (command) {
                    case "Allocate":
                        argument = sc.nextInt();
                        result = obj.Allocate(argument);
                        break;
                    case "Free":
                        argument = sc.nextInt();
                        result = obj.Free(argument);
                        break;
                    case "Defragment":
                        obj.Defragment();
                        break;
                    default:
                        break;
                }
                if (result==-5) fw.write("Defragmented");
                else{
                    String str = String.valueOf(result);
                    fw.write(str);
                }
                fw.write("\n");
                
                
                
            }

        }
        fw.close();
        sc.close();
    }
}