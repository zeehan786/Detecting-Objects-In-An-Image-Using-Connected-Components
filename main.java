//Main
import java.io.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws IOException
    {
        String inputfilename = args[0];
        FileReader inputImage = null;
        BufferedReader buffinImage = null;
        Scanner input = null;
        String outputname = args[1];
        FileWriter outputwriter = null;
        BufferedWriter output = null;
        String outputname2 = args[2];
        FileWriter outputwriter2 = null;
        BufferedWriter output2 = null;
        String outputname3 = args[3];
        FileWriter outputwriter3 = null;
        BufferedWriter output3 = null;
        try
        {
            inputImage = new FileReader(inputfilename);
            buffinImage = new BufferedReader(inputImage);
            input = new Scanner(buffinImage);
            Scanner in = new Scanner(System.in);
            System.out.print("Enter your choice: ");
            String s = in.nextLine();
            System.out.println("You entered " + s);

            outputwriter = new FileWriter(outputname);
            output = new BufferedWriter(outputwriter);
            outputwriter2 = new FileWriter(outputname2);
            output2 = new BufferedWriter(outputwriter2);
            outputwriter3 = new FileWriter(outputname3);
            output3 = new BufferedWriter(outputwriter3);
            int numrows = 0, numcols = 0, minval = 0, maxval = 0;
            if(input.hasNextInt()) numrows = input.nextInt();
            else System.out.println("Invalid format of header");
            if(input.hasNextInt()) numcols = input.nextInt();
            else System.out.println("Invalid format of header");
            if(input.hasNextInt()) minval = input.nextInt();
            else System.out.println("Invalid format of header");
            if(input.hasNextInt()) maxval = input.nextInt();
            else System.out.println("Invalid format of header");
            boolean is4 = false;
            if(s.contentEquals("4")) is4 = true;
            CClabel CCobj = new CClabel(numrows, numcols, minval, maxval, is4); //array is dynamically  allocated in the constructor
            CCobj.loadimage(input);
            if(s.contentEquals("8"))
            {
                CCobj.Connect8Pass1();
                CCobj.ImageReformat(CCobj.zeroframedarray, output, "Using 8 Connected"
                        + " Component Algorithm\nPass 1");
                CCobj.PrintEQArray(output, "Pass 1");
                CCobj.Connect8Pass2();
                CCobj.ImageReformat(CCobj.zeroframedarray, output, "Pass 2");
                CCobj.PrintEQArray(output, "Pass 2");
            }
            else if(s.contentEquals("4"))
            {
                CCobj.Connect4Pass1();
                CCobj.ImageReformat(CCobj.zeroframedarray, output, "Using 4 Connected Component "
                                + "Algorithm\nPass 1");
                CCobj.PrintEQArray(output, "Pass 1");
                CCobj.Connect4Pass2();
                CCobj.ImageReformat(CCobj.zeroframedarray, output, "Pass 2");
                CCobj.PrintEQArray(output, "Pass 2");
            }
            else
            {
                System.out.println("Invalid entry"); System.exit(0);
            }
            CCobj.truecc = CCobj.ManageEqArray();
            CCobj.PrintEQArray(output, "After Equivalence Table Management");
            CCobj.Pass3();
            CCobj.ImageReformat(CCobj.zeroframedarray, output, "Pass 3");
            CCobj.PrintEQArray(output, "Pass 3");
//CCobj.ImageReformat(CCobj.zeroframedarray, output2, "Final Result");
///label 8 connectednesss or 4 connectedness
            CCobj.PrintImage(output2);
            CCobj.CCPropertyFile(output3);
            CCobj.DrawBoxes();
            output.write("True number of Connected Components: "+CCobj.truecc+"\n");
            CCobj.ImageReformat(CCobj.zeroframedarray,output, "Result of Draw Box");
            System.out.println("Done, check the output files");
        }
        finally
        {
            if(input!=null) input.close();
            if(output!=null) output.close();
            if(output2!=null) output2.close();
            if(output3!=null) output3.close();
        }
    }
}
