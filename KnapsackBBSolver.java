package csc206_as4;

import java.sql.Array;

// Branch-and-Bound solver
public class KnapsackBBSolver extends KnapsackBFSolver
{

protected UPPER_BOUND ub;
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
		if(crntSoln.Total_Sum_Val - crntSoln.cur_val_Untakenval <= bestSoln.GetValue())
		{
			return;
		}
		crntSoln.DontTakeItem(itemNum);
		FindSolns(itemNum + 1);
		crntSoln.UndoDontTakeItem(itemNum);
	
		crntSoln.TakeItem(itemNum);
		FindSolns(itemNum + 1);
		crntSoln.UndoTakeItem(itemNum);
	}
	public void FindSolnsUB2(int itemNum)
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
		if(crntSoln.UndecidedFitItem(itemNum) + crntSoln.cur_val_Takenval <= bestSoln.GetValue())
		{
			return;
		}
		crntSoln.DontTakeItem(itemNum);
		FindSolnsUB2(itemNum + 1);
		crntSoln.UndoDontTakeItem(itemNum);
	
		crntSoln.TakeItem(itemNum);
		FindSolnsUB2(itemNum + 1);
		crntSoln.UndoTakeItem(itemNum);
	}
	public void FindSolnsUB3(int itemNum)
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
		if(crntSoln.Fractional(itemNum) + crntSoln.cur_val_Takenval <= bestSoln.GetValue())
		{
			return;
		}
		crntSoln.DontTakeItem(itemNum);
		FindSolnsUB3(itemNum + 1);
		crntSoln.UndoDontTakeItem(itemNum);
	
		crntSoln.TakeItem(itemNum);
		FindSolnsUB3(itemNum + 1);
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
	public KnapsackBBSolver(UPPER_BOUND ub_)
	{
		ub = ub_;
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
		if(ub == UPPER_BOUND.UB1)
		{
		FindSolns(1);
		}
		if(ub == UPPER_BOUND.UB2)
		{
		FindSolnsUB2(1);
		}
		if(ub == UPPER_BOUND.UB3)
		{
		FindSolnsUB3(1);
		}
	}
}