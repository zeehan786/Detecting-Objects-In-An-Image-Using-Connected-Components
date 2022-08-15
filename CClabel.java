//CClabel
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
public class CClabel
{
    public int numrows = 0, numcols = 0, minval = 0, maxval = 0;
    public int newLabel = 0;
    public int truecc = 0;
    public int minrow = 9999, mincol = 9999, maxcol = 0, maxrow = 0;
    public int newmin = 0 , newmax = 0;
    public boolean is4 = false;
    public int zeroframedarray[][];
    public int EQArray[];
    public Property PropertyFile[];
    public static LinkedList<Integer> link = new LinkedList<Integer>();
    public CClabel(int nr, int nc, int mv, int mxv, boolean isfour)
    {
        this.numrows = nr;
        this.numcols = nc;
        this.minval = mv;
        this.maxval = mxv;
        this.is4 = isfour;
        int eqsize = (numrows * numcols)/4;
        this.zeroframedarray = new int[numrows + 2] [numcols + 2];
        this.EQArray = new int[eqsize];
        for(int i = 0; i < this.numrows + 2; i++)
        {
            for(int j = 0; j < this.numcols + 2; j++)
            {
                this.zeroframedarray [i][j] = 0;
            }
        }
        this.EQArray[0] = 0;
    }
    public void loadimage(Scanner m)
    {
        for(int i = 1; i <= this.numrows; i++)
        {
            for(int j = 1; j <= this.numcols; j++)
            {
                if(m.hasNextInt())
                    this.zeroframedarray[i][j] = m.nextInt();
            }
        }
    }
    public void Connectedness()
    {
        int c = 1;
        if(c==4)
        {
        }
        else if(c==8)
        {
        }
        else System.out.println(c+" is a invalid choice");
    }
    public void Connect4Pass1()
    {
        for(int i = 1; i <= this.numrows; i++)
        {
            for(int j = 1; j <= this.numcols; j++)
            {
                int pixel = this.zeroframedarray[i][j];
                if(pixel>0)
                {
                    int a = this.zeroframedarray[i-1][j]; int b = this.zeroframedarray[i][j-1];// int
                    //c = zeroarray[i-1][j]; int d = zeroarray[i-1][j];
//Case 1
                    if(a==0&&b==0)
                    {
                        newLabel++;
                        this.zeroframedarray[i][j] = newLabel;
                        this.EQArray[newLabel] = newLabel;
                    }
                    else if(a==b)
                    {
                        this.zeroframedarray[i][j] = a;
                    }
                    else if(a==0)
                    {
                        this.zeroframedarray[i][j] = b;
                    }
                    else if(b==0)
                    {
                        this.zeroframedarray[i][j] = a;
                    }
                    else if(a!=b)
                    {
                        int minlabel = Math.min(a, b);
                        this.zeroframedarray[i][j] = minlabel;
                        this.EQArray[Math.max(a, b)] = minlabel;
                    }
                }
            }
        }
    }
    public void showArray()
    {
        for(int i = 1; i <= this.numrows; i++)
        {
            for(int j = 1; j <= this.numcols; j++)
            {
                System.out.print(this.zeroframedarray[i][j]+" ");
            }
            System.out.println();
        }
    }
    public void Connect4Pass2()
    {
        for(int i = this.numrows; i >= 0; i--)
        {
            for(int j = this.numcols; j >= 0; j--)
            {
                int pixel = this.zeroframedarray[i][j];
                int minlabel =0;
                if(pixel>0)
                {
                    int a = this.zeroframedarray[i+1][j]; int b = this.zeroframedarray[i][j+1];
                    if(a==0&&b==0)
                    {
                    }
                    else if(Conn4Pass2Case2(pixel, a, b))
                    {
                    }
                    else if(a==0)
                    {
                        minlabel = Math.min(pixel, b);
                        if(pixel>minlabel)
                        {
                            this.zeroframedarray[i][j] = minlabel;
                            this.EQArray[pixel] = minlabel;
                        }
                    }
                    else if(b==0)
                    {
                        minlabel = Math.min(pixel, a);
                        if(pixel>minlabel)
                        {
                            this.zeroframedarray[i][j] = minlabel;
                            this.EQArray[pixel] = minlabel;
                        }
                    }
                    this.zeroframedarray[i][j] = this.EQArray[this.zeroframedarray[i][j]];
                }
            }
        }
    }
    public void Connect8Pass1()
    {
        for(int i = 1; i <= this.numrows; i++)
        {
            for(int j = 1; j <= this.numcols; j++)
            {
                int pixel = this.zeroframedarray[i][j];
                if(pixel>0)
                {
                    link = new LinkedList<Integer>();
                    int a = this.zeroframedarray[i-1][j-1], b = this.zeroframedarray[i-1][j],
                            c = this.zeroframedarray[i-1][j+1], d =
                            this.zeroframedarray[i][j-1];
                    if(a==0&&b==0&&c==0&&d==0)
                    {
                        newLabel++;
                        this.zeroframedarray[i][j] = newLabel;
                        this.EQArray[newLabel] = newLabel;
                    }
                    else if(this.IsSameLabel(a, b, c, d))
                    {
                        this.zeroframedarray[i][j] = link.get(0);
                    }
                    else
                    {
                        int min = this.GetMin();
                        this.zeroframedarray[i][j] = min;
                        for(int k = 0; k<link.size();k++)
                        {
                            this.EQArray[link.get(k)] = min;
                        }
                    }
                }
            }
        }
    }
    public void Connect8Pass2()
    {
        for(int i = this.numrows; i >= 0; i--)
        {
            for(int j = this.numcols; j >= 0; j--)
            {
                int pixel = this.zeroframedarray[i][j];
                if(pixel>0)
                {
                    link = new LinkedList<Integer>();
                    int e = this.zeroframedarray[i][j+1], f = this.zeroframedarray[i+1][j-1],
                            g = this.zeroframedarray[i+1][j], h =
                            this.zeroframedarray[i+1][j+1];
                    if(e==0&&f==0&&g==0&&h==0)
                    {
                    }
                    else if(this.IsSameLabel(e, f, g, h)&&pixel==e)
                    {
                    }
                    else
                    {
                        link.add(pixel);
                        int min = this.GetMin();
                        if(pixel>min)
                        {
                            this.EQArray[pixel] = min;
                            this.zeroframedarray[i][j] = min;
                        }
                    }
                    this.zeroframedarray[i][j] = this.EQArray[this.zeroframedarray[i][j]];
                }
            }
        }
    }
    public boolean IsSameLabel(int a, int b, int c, int d)
    {
        if(a!=0) link.add(a); if(b!=0) link.add(b);
        if(c!=0) link.add(c); if(d!=0) link.add(d);
        if(link.size()==1)
            return true;
        for(int i=0; i<link.size(); i++)
        {
            for(int j=0; j<link.size();j++)
            {
                if(link.get(i)!=link.get(j))
                    return false;
            }
        }
        return true;
    }
    public int GetMin()
    {
        int min = 9999;
        for(int i=0; i<link.size(); i++)
        {
            if(link.get(i)<min)
                min = link.get(i);
        }
        return min;
    }
    public boolean Conn4Pass2Case2(int pixel, int a, int b)
    {
        if(a==0)
            if(pixel==b)
                return true;
            else return false;
        if(b==0)
            if(a==pixel)
                return true;
            else return false;
        if(a==b||b==pixel||a==pixel)
            return true;
        else return false;
    }
    //public boolean Case3()
    public void ImageReformat(int zero[][], BufferedWriter outputimage, String caption) {
        int maxlength = Integer.toString(this.newLabel).length();
        try
        {
            outputimage.write(caption+"\n");
            for(int i = 1; i <= this.numrows; i++)
            {
                for(int j = 1; j <= this.numcols; j++)
                {
                    int input = zero[i][j];
                    int length = Integer.toString(input).length();
//outputimage.write(Integer.toString(input));
                    if(input!=0)
                        outputimage.write(Integer.toString(input));
                    else
                        length = 0;
                    for(int k=length; k <= maxlength;k++)
                        outputimage.write(" ");
                }
                outputimage.write("\n");
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void PrintEQArray(BufferedWriter outputimage, String caption)
    {
        try
        {
            outputimage.write(caption+"\nNew Label: "+this.newLabel+"\nEquivalence Table:\n");
            for(int i=1; i<=newLabel;i++)
            {
                outputimage.write(i+" "+this.EQArray[i]+"\n");
            }
            outputimage.write("\n");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public int ManageEqArray()
    {
        int readlabel = 0;
        for(int index = 1; index <= this.newLabel; index++)
        {
            if(index != this.EQArray[index])
                this.EQArray[index] = this.EQArray[this.EQArray[index]];
            else
            {
                readlabel = readlabel + 1;
                this.EQArray[index] = readlabel;
            }
        }
        return readlabel;
    }
    public void Pass3()
    {
        this.PropertyFile = new Property[this.truecc+1];
        for(int i=1; i <= this.truecc; i++)
        {
            this.PropertyFile[i] = new Property();
        }
        newmax = truecc;
        for(int i = 1; i <= this.numrows; i++)
        {
            for(int j = 1; j <= this.numcols; j++)
            {
                int pixel = this.zeroframedarray[i][j];
                if(pixel > 0)
                {
                    this.zeroframedarray[i][j] = this.EQArray[pixel];
                    pixel = this.EQArray[pixel];
//System.out.println("pixel = "+pixel);
                    this.PropertyFile[pixel].label = pixel;
                    if(this.PropertyFile[pixel].minrow!=0&&i<this.PropertyFile[pixel].minrow)
                        this.PropertyFile[pixel].minrow = i;
                    if(this.PropertyFile[pixel].mincol!=0&&j<this.PropertyFile[pixel].mincol)
                        this.PropertyFile[pixel].mincol = j;
                    if(this.PropertyFile[pixel].maxrow!=this.numrows&&i>this.PropertyFile[pixel].maxrow) this.PropertyFile[pixel].maxrow = i;
                    if(this.PropertyFile[pixel].maxcol!=this.numcols&&j>this.PropertyFile[pixel].maxcol)
                        this.PropertyFile[pixel].maxcol = j;
                    this.PropertyFile[pixel].numpixels = this.PropertyFile[pixel].numpixels + 1;
                }
            }
        }
    }
    public void PrintImage(BufferedWriter labelfile)
    {
        try
        {
            if(this.is4) labelfile.write("Using 4 Connected Component Algorithm\n");
            else labelfile.write("Using 8 Connected Component Algorithm\n");
            labelfile.write(this.numrows+" "+this.numcols+" "+this.newmin+" "+this.newmax+"\n"); int maxlength = Integer.toString(this.truecc).length();
            for(int i = 1; i <= this.numrows; i++)
            {
                for(int j = 1; j <= this.numcols; j++)
                {
                    int pixel = this.zeroframedarray[i][j];
                    int length = Integer.toString(pixel).length();
                    labelfile.write(Integer.toString(pixel));
                    for(int k=length; k <= maxlength;k++)
                        labelfile.write(" ");
                }
                labelfile.write("\n");
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void CCPropertyFile(BufferedWriter propertyfile)
    {
        try
        {
            if(this.is4) propertyfile.write("Using 4 Connected Component Algorithm\n");
            else propertyfile.write("Using 8 Connected Component Algorithm\n");
            propertyfile.write(this.numrows+" "+this.numcols+" "+this.newmin+" "+this.newmax+"\n"
                    + "Number of Connected Components: "+this.truecc+"\n");
            for(int i = 1; i <= this.truecc;i++)
            {
                propertyfile.write(this.PropertyFile[i].label+"\n"+this.PropertyFile[i].numpixels+"\n"+
                                (this.PropertyFile[i].minrow-1)+" "+(this.PropertyFile[i].mincol-1)+"\n"+(this.PropertyFile[i].maxrow-1)+" "
                        +(this.PropertyFile[i].maxcol-1)+"\n_________________\n");
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void DrawBoxes()
    {
        for(int i = 1; i <= this.truecc;i++)
        {
            int mincol = this.PropertyFile[i].mincol;
            int minrow = this.PropertyFile[i].minrow;
            int label = this.PropertyFile[i].label;
            while(mincol <= this.PropertyFile[i].maxcol)
            {
                this.zeroframedarray[this.PropertyFile[i].minrow][mincol] = label;
                mincol++;
            }
            mincol = this.PropertyFile[i].mincol;
            while(mincol <= this.PropertyFile[i].maxcol)
            {
                this.zeroframedarray[this.PropertyFile[i].maxrow][mincol] = label;
                mincol++;
            }
            while(minrow <= this.PropertyFile[i].maxrow)
            {
                this.zeroframedarray[minrow][this.PropertyFile[i].mincol] = label;
                minrow++;
            }
            minrow = this.PropertyFile[i].minrow;
            while(minrow <= this.PropertyFile[i].maxrow)
            {
                this.zeroframedarray[minrow][this.PropertyFile[i].maxcol] = label;
                minrow++;
            }
        }
    }
}
