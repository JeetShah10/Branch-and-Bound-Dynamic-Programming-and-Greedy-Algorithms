package csc206_as4;

import java.util.*;

// Dynamic programming solver
public class KnapsackDPSolver implements java.io.Closeable
{
	private KnapsackInstance inst;
	private KnapsackSolution soln;
	int cap;
	int n;
	
	public void dp(int itemNum)
	{
	int[][] t = new int[n+1][cap+1];
	for(int j=0;j<=cap;j++)
	{
		t[0][j] = 0;
	}
	for(int i=1;i<=n;i++)
	{
		for(int j=0;j<=cap;j++)
		{
			if(inst.GetItemWeight(i)>j)
			{
				t[i][j] = t[i-1][j];
			}
			else
			{
				t[i][j] = Math.max(inst.GetItemValue(i) + t[i-1][j-inst.GetItemWeight(i)],t[i-1][j]);
			}
		}	
	}
	
	int j=cap;
	for(int i=n;i>=1;i--)
	{
		if(t[i][j] > t[i-1][j])
		{
			soln.TakeItem(i);
			j = j - inst.GetItemWeight(i);
		}
	}
	soln.ComputeValue();
}

	public KnapsackDPSolver()
	{
		
	}
	public void close()
	{
		
	}
	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_)
	{
		inst = inst_;
		soln = soln_;
		cap = inst.GetCapacity();
		n = inst.GetItemCnt();
		dp(1);
		
	}
}