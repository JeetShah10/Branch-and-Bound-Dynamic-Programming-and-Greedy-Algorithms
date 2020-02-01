package csc206_as4;

import java.util.*;

public class KnapsackInstance implements java.io.Closeable
{
	private int itemCnt; //Number of items
	private int cap; //The capacity
	private int[] weights; //An array of weights
	private int[] values; //An array of values

	public KnapsackInstance(int itemCnt_)
	{
		itemCnt = itemCnt_;

		weights = new int[itemCnt + 1];
		values = new int[itemCnt + 1];
		cap = 0;
	}
	public void close()
	{
		weights = null;
		values = null;
	}

	public void Generate()
	{
		int i;
        int wghtSum;

		weights[0] = 0;
		values[0] = 0;

		wghtSum = 0;
		for(i=1; i<= itemCnt; i++)
		{
			weights[i] = Math.abs(RandomNumbers.nextNumber()%100)+1;
			values[i] = weights[i] + 10;
			wghtSum += weights[i];
		}
		cap = wghtSum/2;
	}

	public int GetItemCnt()
	{
		return itemCnt;
	}
	public int GetItemWeight(int itemNum)
	{
		return weights[itemNum];
	}
	public int GetItemValue(int itemNum)
	{
		return values[itemNum];
	}
	public int GetCapacity()
	{
		return cap;
	}
	public void Print()
	{
		int i;

		System.out.printf("Number of items = %d, Capacity = %d\n",itemCnt, cap);
		System.out.print("Weights: ");
		for (i = 1; i <= itemCnt; i++)
		{
			System.out.printf("%d ",weights[i]);
		}
		System.out.print("\nValues: ");
		for (i = 1; i <= itemCnt; i++)
		{
			System.out.printf("%d ",values[i]);
		}
		System.out.print("\n");
	}
	
	public double[] WghtVal;
	public int[] Wght;
	public int[] Val;
	
	public void Sort()
	{
		WghtVal = new double[itemCnt+1];
		Wght = new int[itemCnt+1];
		Val = new int[itemCnt+1];
		boolean flag = true;
		
		double temp;
		int temp1;
		int temp2;
		for(int i=1; i<=itemCnt; i++)
		{
			Wght[i] = weights[i];
		}
		for(int i=1; i<=itemCnt; i++)
		{
			Val[i] = values[i];
		}
		for(int i=1; i<=itemCnt; i++)
		{
			WghtVal[i] = (double)values[i]/(double)weights[i];
		}
		for(int j=1;j<=itemCnt;j++)
		{
			for(int i=1; i<=itemCnt-j; i++)
			{
				if(WghtVal[i] < WghtVal[i+1])
				{
					temp = WghtVal[i];
					WghtVal[i] = WghtVal[i+1];
					WghtVal[i+1] = temp;
					temp1 = weights[i];
					weights[i] = weights[i+1];
					weights[i+1] = temp1;
					temp2 = values[i];
					values[i] = values[i+1];
					values[i+1] = temp2;
				}
			}
		}
	}
	public void GetOriginalArray()
	{
		for(int i=1; i<=itemCnt; i++)
		{
			 weights[i] = Wght[i];
		}
		for(int i=1; i<=itemCnt; i++)
		{
			 values[i] = Val[i];
		}
	}
}
