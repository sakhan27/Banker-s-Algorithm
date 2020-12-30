/** Main class for the project
*/

import java.util.*;

public class Project {

	public static void main (String[] args){

		int m = 0; //Initializing number of intial resources
		int n = 0; //Initializing number of customers


		//For taking inputs m & n from the command line
		if (args.length > 0)
		{
			try {
				m = Integer.parseInt(args[0]);
				n = Integer.parseInt(args[1]);

				if (m < 1 || m > 10 || n < 1 || n > 10)
				{
					System.out.println("Integer inputs must be between 1 and 10. Exiting program...");
					System.exit(1);
				}
			}
			catch (NumberFormatException e)
			{
				System.err.println("The two inputs must be integer inputs. Exiting program...");
				System.exit(1);
			}
		}

		else {
			System.out.println("No integer inputs provided. Exiting program...");
			System.exit(1);
		}



		Bank bank = new Bank(m, n); //Instantiation of Bank 

		Customer[] c = new Customer[n]; //Instantiation of Customer 

		//Create the customer threads
		for(int i=0; i<n; i++)
			c[i] = new Customer(i,m,bank);

		//Start the threads
		for (int i=0; i<n; i++)
			c[i].start();


		//Join the customer threads
		try 
		{
			for (int i=0; i<n; i++)
			{
				c[i].join();
			}
		}

		catch (InterruptedException e) {};



		//For printing the final available and allocation arrays
		int[] localAvail = bank.getAvail().clone();
		int[][] localAllocs = bank.getAllocs().clone();

		System.out.println("Final Available vector: ");
		System.out.print("[ ");
		for (int i = 0; i < m; i++)
			System.out.print(localAvail[i] + " ");
		 System.out.print("]");


		System.out.println();

		System.out.print("Final Allocation matrix: ");


		for (int i=0; i<n; i++)
        {
            System.out.println();
            System.out.print("[ ");

            for(int j=0; j<m; j++)
            {
                System.out.print(localAllocs[i][j]+" ");

            }

             System.out.print("]");

        }

        System.out.println();
        System.out.println();


	} //end main

} //end Project class