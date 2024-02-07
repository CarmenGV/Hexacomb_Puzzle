
public class HexacombPuzzle2 {
	
	public static boolean hexaPuzzle(int[][] hexacombs, int indexStart, int indexEnd) {
	    try
	    {
	    		boolean foundSolution = false;
	    		if(indexStart == indexEnd)
			{
				return foundSolution;
			} else
			{
				for(int i = indexStart; i <= indexEnd; ++i)
				{
					//Swap current hexacomb with first hexacomb
					int[] saveOriginal = hexacombs[indexStart];
					hexacombs[indexStart] = hexacombs[i];
					hexacombs[i] = saveOriginal;
					
					//Generate all permutations
					foundSolution = permuteCheck(hexacombs);
					if(foundSolution)
					{
						System.out.println("A solution was found!");
						printSolution(hexacombs);
						return foundSolution;
					}
					hexaPuzzle(hexacombs, indexStart + 1, indexEnd);
					
					//Swap hexacombs back into their original positions
					saveOriginal = hexacombs[indexStart];
					hexacombs[indexStart] = hexacombs[i];
					hexacombs[i] = saveOriginal;
				}
			}
	    		return foundSolution;
	    }
	    catch (Exception e) 
	    {
	        System.out.println(e);
	        return false;
	    }
	}
	
	public static boolean permuteCheck(int[][]hexacombs)
	{
		boolean sequenceMatch = false;
		boolean arrMatch = false;
		int[] hexA = hexacombs[0];
		int index = -1;
		for(int side = 0; side <= 5; ++side)
		{
			arrMatch = false;
			int[] hexB = hexacombs[1];
			int[] hexC = hexacombs[2];
			int[]sequence = findSequence(hexA, hexB, side);
			if(sequence != null)
			{
				sequenceMatch = hexMatch(hexC, sequence[0], sequence[1]);
				if(sequenceMatch)
				{
					index = side;
					for(int arr = 2; arr <= 6; ++arr)
					{
						index = (index+1)%6;
						hexB = hexacombs[arr];
						hexC = hexacombs[(arr+1)%7];
						if(arr == 6)
						{
							hexC = hexacombs[(arr+2)%7];
						}
						sequence = findSequence(hexA, hexB, index);
						if(sequence == null)
						{
							arrMatch = false;
							break;
						} else
						{
							sequenceMatch = hexMatch(hexC, sequence[0], sequence[1]);
							if(!sequenceMatch)
							{
								arrMatch =false;
								break;
							}
							else
							{
								arrMatch = true;
							}
						}
					}
				}
				if(arrMatch)
				{
					break;
				}
			}
		}
		return arrMatch;
	}
	
	public static void printSolution (int[][] hexacombs)
	{
		System.out.println("Solution Configuration: ");
		for(int arr = 0; arr <= 6; ++arr)
		{
			System.out.print("Hexacomb " + (arr + 1) + " = ");
			for(int side = 0; side <= 5; ++side)
			{
				if(side == 0)
				{
					System.out.print("{");
				}if(side != 5)
				{
					System.out.print(hexacombs[arr][side] + ", ");
				} else
				{
					System.out.print(hexacombs[arr][side] + "}");
					if(arr == 0)
					{
						System.out.print(" (center hexacomb)");
					}
				}
			}
			System.out.println("");
		}
	}
	
	//****************************************************
	// 		HELPER METHODS
	//****************************************************
	
	//************* METHOD: FIND SEQUENCE *****************
	// Method that finds set of numbered sides that next Hexacomb must match
	public static int[] findSequence (int[] hexA, int[] hexB, int index)
	{
		int hexAvalue = hexA[index];
		int hexAindex = index;
		int hexAnext;
		int hexBvalue = -1;
		int hexBindex = -1;
		int hexBprev;
		
		//Find the first matching side between hexA and hexB
		for(int i = 0; i <= 5; ++i)
		{
			if(hexAvalue == hexB[i])
			{
				hexBvalue = hexB[i];
				hexBindex = i;
				break;
			}
		}
		
		//Get sequence of next matching hexagon
		hexAnext = hexA[(hexAindex + 1)%6];
		hexBprev = hexB[(hexBindex + 5)%6];
		
		//Check sequenced numbers are not equal; if yes, no solution
		if(hexAnext == hexBprev || hexBprev == -1)
		{
			return null;
		} else
		{
			int[] sequence = {hexBprev, hexAnext};
			return sequence;
		}
		
	}
	
	//************* METHOD: FIND MATCH *****************
	// Method that finds set of numbered sides that next Hexacomb must match
	public static boolean hexMatch(int[] hexA, int seq1, int seq2)
	{
		boolean isMatch = false;
		for(int side = 0; side <= 5; ++side)
		{
			if(hexA[side] == seq1)
			{
				if(hexA[(side+5)%6] == seq2)
				{
					isMatch = true;
					break;
				}
			}
		}
		return isMatch;
	}
	

	public static void main(String[] args)
	{
		//Original Hexacombs
		int[] hexa1 = {1,2,3,4,5,6};
		int[] hexa2 = {1,6,4,2,5,3};
		int[] hexa3 = {1,6,5,4,3,2};
		int[] hexa4 = {1,4,6,2,3,5};
		int[] hexa5 = {1,6,5,3,2,4};
		int[] hexa6 = {1,4,3,6,5,2};
		int[] hexa7 = {1,6,2,4,5,3};
		
		//Hexacombs with solution
		int[] hex1 = {1,4,3,6,5,2};
		int[] hex2 = {1,6,2,4,5,3};
		int[] hex3 = {1,6,5,4,3,2};
		int[] hex4 = {1,6,4,2,5,3};
		int[] hex5 = {1,4,6,2,3,5};
		int[] hex6 = {6,2,3,4,5,1};
		int[] hex7 = {1,6,4,3,2,5};
		//Repeating Hexacombs
		int[] comb1  = {1,2,3,4,5,6};
		
		int[][] hexSolution = {hex1, hex2, hex3, hex4, hex5, hex6, hex7};
		int[][] hex2Solution = {hexa1, hexa2, hexa3, hexa4, hexa5, hexa6, hexa7};
		int[][] hexRepeat = {comb1, comb1, comb1, comb1, comb1, comb1, comb1}; 
		
		boolean result1 = hexaPuzzle(hexSolution, 0, 6);
		if(!result1)
		{
			System.out.println("No solution was found for hexSolution set of hexacombs.");
		}
		
		System.out.println("");
		
		boolean result2 = hexaPuzzle(hex2Solution, 0, 6);
		if(!result2)
		{
			System.out.println("No solution was found for hex2solution set of hexacombs.");
		}
		
		System.out.println("");
		
		boolean result3 = hexaPuzzle(hexRepeat, 0, 6);
		if(!result3)
		{
			System.out.println("No solution was found for the repeating set of hexacombs, hexRepeat.");
		}
	}

}
