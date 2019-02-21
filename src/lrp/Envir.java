package lrp;

import java.io.*;
import java.util.*;

public class Envir {
	
	String cwd = System.getProperty("user.dir");
	
	public int size = 20;
	public int matrix[][] = new int[size][size];
	
	public void read(int index)
	{
		Scanner reader = null;
		try {
			reader = new Scanner(new File(cwd + "/ExtData/level" + index + ".txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				int val = reader.nextInt();
				setMatrix(i, j, val);
			}
		}
	}
	public void setMatrix(int i, int j, int x)
	{
		this.matrix[i][j] = x;
	}
	public void printMatrix()
	{
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
