# Branch-and-Bound-Dynamic-Programming-and-Greedy-Algorithms
In this codes I have implemented four different algorithms for solving the 0-1 Knapsack Problem and evaluate their performances using real time measurements. The algorithms are: 
1. Brute-Force Enumeration. 
2. Backtracking. 
3. Branch-and-Bound.    
4. Dynamic Programming. 

And I have implemented three different branch-and-bound algorithms; each algorithm uses one of the following three upper bounds (UBs) to prune the tree:   
1. UB1: the sum of taken item values and undecided item values. I have computed this in at each node by subtracting the sum of untaken item values from the sum of all item values. The sum of untaken item values can be computed incrementally by updating this sum at each node in θ(1).   
2. UB2: the sum of taken item values and the values of the undecided items that fit in the remaining capacity at each node. This can be computed in O(n) time at each node by checking all the remaining items and adding the value of every item whose weight is less than or equal to the remaining capacity.   
3. UB3: solved the remaining sub-problem at each node as a Fractional Knapsack problem. This can be computed in O(n) time at each node if you sort the items before you start the search (in the preprocessing step).
