/**SafetyCheck class for checking pretend request allocation and 
the safeness of the state
*/
public class SafetyCheck
{
    
    private int numOfCustomers;
    private int sizeofResources;
    private int custId;
    private int[] avail;
    private int[][] needs;  
    private int[][] allocs; 
    private int[] reqArr;
    private int[] safeSequence;


    /**Constructor for SafetyCheck
    *@param - m - the number of resources from the Bank class
    *@param - n - the number of customers/thread from the Bank class
    *@param - id - ID of the customer
    *@param - av[] - avail array from the Bank class
    *@param - nds[] - needs array from the Bank class
    *@param - alcs - allocs array from the Bank class
    *@param - req - request array from the Bank class
    */
    public SafetyCheck(int n, int m, int id, int[] av, int[][] nds, int[][]alcs, int[]req)
    {

        numOfCustomers = n;
        sizeofResources = m;
        custId = id;

        avail = new int[sizeofResources];
        needs = new int[numOfCustomers][sizeofResources];
        allocs = new int[numOfCustomers][sizeofResources];
        reqArr = new int[sizeofResources];
        safeSequence = new int[numOfCustomers];

        
        for (int i = 0; i < sizeofResources; i++)
        {
            avail[i] = av[i];
        }
        

        for (int i = 0; i < numOfCustomers; i++) {
            for (int j = 0; j < sizeofResources; j++)
            {
                needs[i][j] = nds[i][j];
            }
        }

        for (int i = 0; i < numOfCustomers; i++) {
            for (int j = 0; j < sizeofResources; j++)
            {
                allocs[i][j] = alcs[i][j];
            }
        }

        for (int j = 0; j < sizeofResources; j++)
        {
            reqArr[j] = req[j];
        }



    }

    /**Method to check for pretend request allocation and calls checkSafety to check the state of that allocation
    *@return - returns boolean safe, returns false if state is unsafe and true if it is safe
    */
    public boolean pretendRequest() 
    {

        boolean safe = true;

        for(int j = 0; j < reqArr.length; j++)
        {
            avail[j] = avail[j] - reqArr[j];
            allocs[custId][j] = allocs[custId][j] + reqArr[j];
            needs[custId][j] = needs[custId][j] - reqArr[j];
                        
        }

        safe = checkSafety();
        return safe;
    }

    /**Method to check for safety for a given allocation state
    *@return - returns boolean safe, returns false if state is unsafe and true if it is safe
    */
    public boolean checkSafety()
    {
        int[]flag = new int[numOfCustomers]; //f[i]
        int count1 = 0; //counter1
        int[]placeholder = new int[sizeofResources];
        int flagCount = 0; //safe seq variable counter
        boolean safe = true;


        //First set flag[i] = 0 for all processes
        for(int i=0; i<numOfCustomers; i++)
        {
            flag[i] = 0;
            //System.out.println("This is the flag value for customer" + i + " " + flag[i]);
        }

        //Label to break the outer while loop later so that it doesn't do infinite loops
        Label:
        while(flagCount != numOfCustomers)
        {
            
            count1++;

            /*Find a process Pi such that flag[i] = 0 and needs[i] <= available[i]
            */
            for (int i =0; i<numOfCustomers; i++)
            {   

                if(flag[i] == 0)
                {
                    int count2 = 0; //counter2

                    //System.out.println();

                    for(int j=0; j<sizeofResources; j++)
                    {


                        if(needs[i][j] <= avail[j])
                        {
                            
                            count2++;
                        }

                        else 
                            break;

                    }

                    if(count2 == sizeofResources)
                    {

                        flag[i] = 1;
                        safeSequence[flagCount] = i;
                        for(int k = 0; k < sizeofResources; k++)
                            avail[k] = avail[k] + allocs[i][k];
                        flagCount++;
                        
                    }

                } //end if(flag[i] = 0)


            } //end outer for loop

            
            if(count1 == numOfCustomers)
            {


                for (int i=0; i<numOfCustomers; i++)
                {
                    if(flag[i] == 0)
                    {
                        for(int j = 0; j < sizeofResources; j++)
                        {
                            if(needs[i][j] >= avail[j])
                            {
                                //System.out.println("System is unsafe");
                                safe = false;
                                break Label;
                            }
                        }
                    }
                }

            } //end if(count == numOfCustomers)

        } //end while


        if(safe) //if state is safe
        {

            //Print safe sequence array if state is safe
            System.out.println("Bank - Safe sequence: ");
            System.out.print("[ ");
            for(int i = 0; i < safeSequence.length; i++){

                System.out.print(safeSequence[i] + " ");

            }

            System.out.println("]");
            return true;
        }

        else //if state is unsafe
        {
            System.out.println("Bank - Safe Sequence not found");
            return false;
        }

    } //end checkSafety

} //end class
















