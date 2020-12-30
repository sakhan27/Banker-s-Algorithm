class Bank {

        private int numOfResources; //'m' from command line -
        private int numOfCustomers; //'n' from command line -
        /** Number of available resources at any given moment */
    private int[] avail;
    /** Maximum number of each resource type to be requested from the
    * customers */
    private int[][] maxes;
    /** Resources currently allocated to customers */
    private int[][] allocs;
    /** Resources yet to be requested by each customer */
    private int[][] needs;

    /**Constructor for Bank
    *@param - m - the number of resources from the command line
    *@param - n - the number of customers/thread from the command line
    */
        public Bank(int m, int n)
        {

                numOfResources = m;
                numOfCustomers = n;

                //Set the initial avail resources and print them out
        avail = new int[numOfResources];

        System.out.println("Bank - Initial Resources Available: ");
        System.out.print("[ ");

        for (int i=0; i<m; i++)
        {
            int rand = (int)(Math.random() * (10 - 4) + 4);
            avail[i] = rand;
            System.out.print(avail[i] + " ");
        }

        System.out.print("]");
        System.out.println();
                System.out.print("Bank - Max:");
                initializeArrays();

        }

    /**Method for initializing the following arrays - maxes, allocs, and needs
    */
        public void initializeArrays()
        {
            //Set maxes and print out in the beginning

            maxes = new int[numOfCustomers][numOfResources];

            for (int i=0; i<numOfCustomers; i++)
            {
                System.out.println();
                System.out.print("[ ");
                for(int j=0; j<numOfResources; j++)
                {
                    int rand = (int)(Math.random() * 10);

                    if(rand <= avail[j])
                    {
                        maxes[i][j] = rand;
                        System.out.print(maxes[i][j]+" ");
                    }
                    else
                        j--;
                }

                System.out.print("]");
            }

            System.out.println();

            //Set needs to max initially
            needs = new int[numOfCustomers][numOfResources];
            needs = maxes.clone();

            //Initialize allocs array to zero
            allocs = new int[numOfCustomers][numOfResources];

            for (int i=0; i<numOfCustomers; i++)
            {
            for(int j=0; j<numOfResources; j++)
            {
            allocs[i][j] = 0;

            }


            }
        } //end setMax

    /**Method to get requests from the customer and check request algorithm and safe state before allocating resources
    *@param - array[] - request array that is being passed from the customer thread
    *@param - id - the ID of the customer
    *@param - reqNum - the request number
    */
    public synchronized void getReqs(int[] array, int id, int reqNum)
    {
        boolean flag = true;

        System.out.println("Customer "+ id +" making request:");
        System.out.print("[ ");
        int[]reqArr = array.clone();
        for(int k = 0; k < reqArr.length; k++)
            System.out.print(reqArr[k] + " ");

        System.out.print("]");
        System.out.println();

        for(int i = 0; i < reqArr.length; i++)
        {
            if(reqArr[i] > avail[i])
            {

                flag = false;
                break;
            }
        }

        SafetyCheck check = new SafetyCheck(numOfCustomers, numOfResources, id,
                                            avail, needs, allocs, reqArr);

        //If request <= available and the pretend allocation is in a safe state
        if(flag && check.pretendRequest())
        {
                // Allocating the resources
                for(int k = 0; k < reqArr.length; k++)
                {
                    avail[k] = avail[k] - reqArr[k];
                    allocs[id][k] = allocs[id][k] + reqArr[k];
                    needs[id][k] = needs[id][k] - reqArr[k];
                }

                System.out.print("Bank - Allocation matrix: ");

                for (int i=0; i<numOfCustomers; i++)
                {
                    System.out.println();
                    System.out.print("[ ");

                    for(int j=0; j<numOfResources; j++)
                    {
                        System.out.print(allocs[i][j] + " ");
                    }

                    System.out.print("]");
                }

                System.out.println();

                System.out.println("Customer " + id + " request "
                                   + reqNum + " granted");
        } //end if(request > avail)

        else
        {
            System.out.println("Bank: Customer "+ id + " must wait");

            try
            {
                wait();
                getReqs(reqArr, id, reqNum); //try to pass the reqs again after wait is done
            }
            catch(InterruptedException e) {}
        } //end else
    } //end getreqs

    /**Release method for releasing the reources after the thread wakes up
    *@param - id - the ID of the customer
    */
    public synchronized void releaseResources(int id)
    {
        System.out.println("Customer " + id + " releasing resources: ");
        System.out.print("[ ");
        for(int j = 0; j < numOfResources; j++)
            System.out.print(allocs[id][j] + " ");
        System.out.println("]");

        //Release the resources
        for(int i = 0; i < numOfResources; i++)
        {
            avail[i] = avail[i] + allocs[id][i];
            allocs[id][i] = 0;
        }

        notifyAll(); //notify all threads after releasing the resources

    } //end releaseResources


    /**Accessor for needs array
    *@return - the current needs array
    */
    public int[][] getNeeds()
    {
        return needs;
    }

    /**Accessor for avail array
    *@return - the current available array
    */
    public int[] getAvail()
    {
        return avail;
    }

    /**Accessor for allocs array
    *@return - the current allocation array
    */
    public int[][] getAllocs()
    {
       return allocs;
    }

} //end Bank


