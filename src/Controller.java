import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class Controller {

    public static void main(String[] args) throws InterruptedException {
        //Fill a n^2 grid
        int n = 0;
        int missingCellRow = 0;
        int missingCellColumn = 0;
        PrintWriter out;

        try {
            out = new PrintWriter("out.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Fatal Error: Either the out.csv file can't be accessed or it doesn't exists on your computer's file system");
            e.printStackTrace();
            return;
        }

        try
        {
            n = Integer.parseInt(args[0]);
            if (!isNPowerOfTwo(n))
                throw new InputMismatchException();

        } catch (InputMismatchException ex)
        {
            System.out.println("You need an integer that is a power of 2");
            Thread.sleep(1000);
            System.exit(0);
        }

        try
        {
            missingCellRow = Integer.parseInt(args[1]);
            if (missingCellRow >= n)
            {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException ex)
        {
            System.out.println("y coordinate is out of bounds or is not an integer");
            Thread.sleep(1000);
            System.exit(0);
        }

        try
        {
            missingCellColumn = Integer.parseInt(args[2]);
            if(missingCellColumn >= n)
                throw new InputMismatchException();
        } catch (InputMismatchException ex)
        {
            System.out.println("x coordinate is out of bounds or is not an integer");
            Thread.sleep(1000);
            System.exit(0);
        }

        String[][] grid = new String[n][n];



        grid[missingCellRow][missingCellColumn] = "*";

        placeLTiles(grid, n, 0, 0);

        printArray(grid, n, out);


    }

    public static boolean isNPowerOfTwo(int checkN)
    {
        if (checkN <= 1)
        {
            return false;
        } else if (checkN % 2 == 0)
        {
            if (checkN / 2 == 1)
            {
                return true;
            } else {
                checkN /= 2;
                return isNPowerOfTwo(checkN);
            }
        } else
        {
            return false;
        }
    }

    public static void placeLTiles(String[][] grid, int width, int startRow, int startColumn)
    {
        int secondHalf = width / 2;
        int topCenter = startRow + secondHalf - 1;
        int bottomCenter = startRow + secondHalf;
        int rightCenter = startColumn + secondHalf;
        int leftCenter = startColumn + secondHalf - 1;
        int missingRow = 0;
        int missingCol = 0;

        if(width == 2)
        {
            placeTileIn2by2(grid, width, startRow, startColumn, missingRow, missingCol);
        } else if (width != 1)
        {
            for(int i = startRow; i < startRow + width; i++)
            {
                for(int w = startColumn; w < startColumn + width; w++)
                {
                    if(grid[i][w] != null)
                    {
                        missingRow = i;
                        missingCol = w;
                    }
                }
            }

            if(missingRow < bottomCenter)
            {
                if(missingCol < rightCenter) // Top Left
                {
                    grid[bottomCenter][rightCenter] = "+";
                    grid[topCenter][rightCenter] = "|";
                    grid[bottomCenter][leftCenter] = "-";
                } else // Top Right
                {
                    grid[bottomCenter][leftCenter] = "+";
                    grid[topCenter][leftCenter] = "|";
                    grid[bottomCenter][rightCenter] = "-";
                }
            } else {
                if(missingCol < rightCenter) // Bottom Left
                {
                    grid[topCenter][rightCenter] = "+";
                    grid[bottomCenter][rightCenter] = "|";
                    grid[topCenter][leftCenter] = "-";
                } else //Bottom Right
                {
                    grid[topCenter][leftCenter] = "+";
                    grid[bottomCenter][leftCenter] = "|";
                    grid[topCenter][rightCenter] = "-";
                }
            }

            // TopLeft
            placeLTiles(grid, width / 2, startRow, startColumn);
            // TopRight
            placeLTiles(grid, width / 2, startRow, rightCenter);
            // BottomLeft
            placeLTiles(grid, width / 2, bottomCenter, startColumn);
            // BottomRight
            placeLTiles(grid, width / 2, bottomCenter, rightCenter);
        }
    }

    public static void placeTileIn2by2(String[][] grid, int width, int startRow, int startColumn, int missingRow, int missingCol)
    {
        for(int i = 0; i < width; i++)
        {
            for(int w = 0; w < width; w++)
            {
                if(grid[startRow + i][startColumn + w] != null)
                {
                    missingRow = startRow + i;
                    missingCol = startColumn + w;
                }
            }
        }

        if(missingRow < startRow + width / 2)
        {
            if(missingCol < startColumn + width / 2) // Top Left
            {
                grid[missingRow + 1][missingCol + 1] = "+";
                grid[missingRow][missingCol + 1] = "|";

            } else { // Top Right
                grid[missingRow + 1][missingCol - 1] = "+";
                grid[missingRow][missingCol - 1] = "|";
            }
            grid[missingRow + 1][missingCol] = "-";
        } else {
            if(missingCol < startColumn + width / 2) // Bottom Left
            {
                grid[missingRow - 1][missingCol + 1] = "+";
                grid[missingRow][missingCol + 1] = "|";
            } else { // Bottom Right
                grid[missingRow - 1][missingCol - 1] = "+";
                grid[missingRow][missingCol - 1] = "|";
            }
            grid[missingRow - 1][missingCol] = "-";
        }
    }


    public static void printArray(String[][] grid, int width, PrintWriter out)
    {
        for(int row = 0; row < width; row++)
        {
            for(int col = 0; col < width; col++)
            {
                out.print(grid[row][col] + " ");
            }
            out.println();
        }
        out.close();
    }
}
