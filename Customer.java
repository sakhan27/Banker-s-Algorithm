import java.util.Random;

class Customer extends Thread{

    private int id;                //id of the customer
    private int numOfResources; //number of resource types
    private Bank Bank;            //Bank object


    /**Constructor for Customer
    *@param - i - the id of the customer from main
    *@param - m - the number of resources from main
    *@param - b - Bank object from main
    */
    public Customer(int i,int m, Bank b)
    {
        id = i;
        Bank = b;
        numOfResources = m;
    }


    /** Run method for Customer threads
        Creates the resource requests here and calls the Bank request method
        Releases the resources after the requests are done by calling the Bank release method
    */
    public void run()
    {
        Random rand = new Random();
        int max_sleep = 5000; //max sleep time
        int min_sleep = 1000; //min sleep time
        int[] max = Bank.getNeeds()[id].clone();
        int[] request = new int[numOfResources]; //req array to be passed
        int reqRand = 3;
        int rand2; //random int for thread.sleep()

        /* generate random requests and hold them for random amounts of
         * time */
        for (int reqCounter = 1; reqCounter <= reqRand; reqCounter++) {
            int[] needs = Bank.getNeeds()[id].clone();
            /* if request is all zeros, redo the request until it isn't */
            boolean is_req_empty = true;
            while (is_req_empty) {
                /* random generation for each resource */
                for(int i = 0; i < request.length; i++)
                {
                    if (reqCounter < reqRand) {
                        /* make sure not all resources are requested before
                         * required number of requests has been made */
                        int req = rand.nextInt((max[i] / reqRand) + 1);
                        request[i] = req;
                    } else { // request all remaining resources at end
                        request[i] = needs[i];
                    }
                    if (request[i] != 0) {
                        is_req_empty = false;
                    }
                }
            }

            Bank.getReqs(request, id, reqCounter);

            rand2 = (int)(Math.random()*(max_sleep - min_sleep))
                          + min_sleep;
            try
            {
                Thread.sleep(rand2);
            }
            catch(Exception e) {}
        }

        Bank.releaseResources(id);    //release the resources after all the requests are done
    }
}
