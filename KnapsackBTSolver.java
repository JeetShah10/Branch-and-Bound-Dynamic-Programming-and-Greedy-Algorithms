package csc206_as4;

import java.util.*;

// Backtracking solver
public class KnapsackBTSolver extends KnapsackBFSolver
{
	protected KnapsackInstance inst;
	protected KnapsackSolution crntSoln;
	protected KnapsackSolution bestSoln;

	public void FindSolns(int itemNum)
	{
		if(crntSoln.cur_val_Takenwght > inst.GetCapacity())
		{
			return;
		}
		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		crntSoln.DontTakeItem(itemNum);
		FindSolns(itemNum + 1);
		crntSoln.UndoDontTakeItem(itemNum);
		
		crntSoln.TakeItem(itemNum);
		FindSolns(itemNum + 1);
		crntSoln.UndoTakeItem(itemNum);
	}
	public void CheckCrntSoln()
	{
		int crntVal = crntSoln.ComputeValue();
		//System.out.print("\nChecking solution ");
		//crntSoln.Print(" ");

		if (crntVal == DefineConstants.INVALID_VALUE)
		{
			return;
		}
    
		if (bestSoln.GetValue() == DefineConstants.INVALID_VALUE) //The first solution is initially the best solution
		{
			bestSoln.Copy(crntSoln);
		}
		else
		{
			if (crntVal > bestSoln.GetValue())
			{
				bestSoln.Copy(crntSoln);
			}
		}
	}
	
	public KnapsackBTSolver()
	{
		crntSoln = null;
	}
	public void close()
	{
		if (crntSoln != null)
		{
			crntSoln = null;
		}
	}
	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_)
	{
		inst = inst_;
		bestSoln = soln_;
		crntSoln = new KnapsackSolution(inst);
		FindSolns(1);    
	}
}