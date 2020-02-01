package csc206_as4;

import java.util.*;

public class KnapsackSolution implements java.io.Closeable
{
	private boolean [] isTaken;
	private boolean [] isUndoTaken;
	private int value;
	private int wght;
	private KnapsackInstance inst;
	public int cur_val_Untakenval=0;
	public int cur_val_Takenval=0;
	public int cur_val_Takenwght=0;
	public int cur_val_Untakenwght=0;
	public int Total_Sum_Val=0;
	public int Total_Sum_Weight=0;
	public int Remaining_Cap = 0;
	public int[] ReorderArray;
	
	public KnapsackSolution(KnapsackInstance inst_)
	{
		int i;
		int itemCnt = inst_.GetItemCnt();
    
		inst = inst_;
		isTaken = new boolean[itemCnt + 1];
		isUndoTaken = new boolean[itemCnt + 1];
		ReorderArray = new int[itemCnt + 1];
		value = DefineConstants.INVALID_VALUE;
		
		Remaining_Cap = inst.GetCapacity();
    
		for (i = 1; i <= itemCnt; i++)
		{
			isTaken[i] = false;
			isUndoTaken[i] = false;
		}
		for(int j =1;j <= itemCnt; j++)
		{
		Total_Sum_Val += inst.GetItemValue(j);
		}
		for(int k =1;k <= itemCnt; k++)
		{
		Total_Sum_Weight += inst.GetItemWeight(k);
		}
	}
	public void close()
	{
		isTaken = null;
		isUndoTaken = null;
	}
	public void TakeItem(int itemNum)
	{
		isTaken[itemNum] = true;
		cur_val_Takenval += inst.GetItemValue(itemNum);
		cur_val_Takenwght += inst.GetItemWeight(itemNum);
		Remaining_Cap -= inst.GetItemWeight(itemNum);
	}
	public void DontTakeItem(int itemNum)
	{
		isTaken[itemNum] = false;
		cur_val_Untakenval += inst.GetItemValue(itemNum);
		cur_val_Untakenwght += inst.GetItemWeight(itemNum);
	}
	public void UndoDontTakeItem(int itemNum)
	{
		cur_val_Untakenval -= inst.GetItemValue(itemNum);
		cur_val_Untakenwght -= inst.GetItemWeight(itemNum);
	}
	public void UndoTakeItem(int itemNum)
	{
		isTaken[itemNum] = false;
		cur_val_Takenwght -= inst.GetItemWeight(itemNum);
		cur_val_Takenval -= inst.GetItemValue(itemNum);
		Remaining_Cap += inst.GetItemWeight(itemNum);
	}
	public int UndecidedFitItem(int itemNum)
	{
		int itemCnt = inst.GetItemCnt();
		int Undecided_Fit_Item_Val = 0;
		for(int i=itemNum; i<= itemCnt; i++)
		{
			if(inst.GetItemWeight(i) <= Remaining_Cap)
			{
				Undecided_Fit_Item_Val += inst.GetItemValue(i);
			}
		}
		return Undecided_Fit_Item_Val;
	}
	public int ComputeValue()
	{
		int i;
		int itemCnt = inst.GetItemCnt();
		int weight = 0;
    
		value = 0;
		for (i = 1; i <= itemCnt; i++)
		{
			if (isTaken[i] == true)
			{
				weight += inst.GetItemWeight(i);
				if (weight > inst.GetCapacity())
				{
					value = DefineConstants.INVALID_VALUE;
					break;
				}
				value += inst.GetItemValue(i);
			}
		}
		return value;
	}
	public int GetValue()
	{
		return value;
	}
	public void Reorder()
	{
		int itemCnt = inst.GetItemCnt();
		boolean[] isTaken1 = new boolean[isTaken.length];
		for(int i=0; i<=itemCnt; i++)
		{
			ReorderArray[i] = inst.GetItemWeight(i);	
			isTaken1[i] = isTaken[i];
			isTaken[i] = false;
		}
		
		for(int j = 1; j < isTaken1.length; j++)
		{
			if (isTaken1[j] == true)
			{
				for (int i = 1; i <= itemCnt; i++)
				{
					if(ReorderArray[j] == inst.Wght[i] && isTaken[i] == false)
					{
						isTaken[i] = true;
						break;
					}
				}
			}
		}
	}
	public void Print(String title)
	{
		int i;
		int itemCnt = inst.GetItemCnt();
    
		System.out.printf("\n%s: ",title);
		for (i = 1; i <= itemCnt; i++)
		{
			if (isTaken[i] == true)
			{
				System.out.printf("%d ",i);
			}
		}
		System.out.printf("\nValue = %d\n",value);
	}
	public void Copy(KnapsackSolution otherSoln)
	{
		int i;
		int itemCnt = inst.GetItemCnt();
    
		for (i = 1; i <= itemCnt; i++)
		{
			isTaken[i] = otherSoln.isTaken[i];
		}
		value = otherSoln.value;
	}
	public double Fractional(int itemNum)
	{
		double Total_Sum_Val1 = 0;
		int itemCnt = inst.GetItemCnt();
		int Rem_Cap = Remaining_Cap;
		for(int i =itemNum; i<=itemCnt; i++)
		{
			if(Rem_Cap - inst.GetItemWeight(i) >= 0)
			{
				Rem_Cap = Rem_Cap - inst.GetItemWeight(i);
				Total_Sum_Val1 += inst.GetItemValue(i);
			}
			else
			{
				double Fraction = (double)Rem_Cap / (double)inst.GetItemWeight(i);
				Total_Sum_Val1 += (double)inst.GetItemValue(i)*Fraction;
				break;
			}
		}
		return Total_Sum_Val1;
	}
	/*public double Fractional_1_Extra_Work(int itemNum)
	{
		double Total_Sum_Val2 = 0;
		int itemCnt = inst.GetItemCnt();
		int Rem_Cap1 = Remaining_Cap;
		if(isTaken[itemNum] == true)
		{
		for(int i =0; i<itemCnt; i++)
		{
			if(Rem_Cap1 - inst.GetItemWeight(i) >= 0)
			{
				Rem_Cap1 = Rem_Cap1 - inst.GetItemWeight(i);
				Total_Sum_Val2 += inst.GetItemValue(i);
			}
			else
			{
				double Fraction = ((double)(Rem_Cap1 / inst.GetItemWeight(i)));
				Total_Sum_Val2 += (double)(inst.GetItemValue(i)*Fraction);
				break;
			}
		}
		}
		else
		{
			
		}
		return Total_Sum_Val2;
	}*/
	public boolean equalsTo (KnapsackSolution otherSoln)
	{
		return value == otherSoln.value;
	}
	
	public void dispose()
	{
		isTaken = null;
	}	
}