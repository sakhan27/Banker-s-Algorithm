# Banker's Algorithm
**Introduction**

Banker’s algorithm is a design process, developed by Edsger Dijkstra. It was designed for
operating systems and was originally published (in Dutch) in EWD108. As the name suggests, it
is used by banking institutions to account for liquidity constraints.
Banker’s algorithm is a resource allocation avoidance, and it tests for the safety of
resource states by simulating the allocation of pre-determined maximum possible amounts of all
resources, and then makes a “safe-state’ check in order to test for all possible deadlock
situations/conditions for all pending activities. This is done in order to decide whether the new
allocation would be in a safe state or not.
Banker’s algorithm’s main objective is to avoid deadlock. It is used in banking systems in
order to identify whether a loan could be sanctioned or not. For example, there are certain
amount of account holders at any given time with a sum-total of a certain amount of money. If a
loan is be sanctioned from the bank at any given moment, Banker’s algorithm subtracts the
amount of loan from the total amount the bank has and checks the difference against the total
amount. It processes the loan only if the bank would have enough amount left if all the account
holders were to withdraw their respective total amounts from the bank simultaneously.

**Usage**

The program comprises of several instances of thread executions to simulate the
Banker’s algorithm and the Resource-Request algorithm. Whenever a customer request is
granted, the customer holds the resources for a random period (1-5 seconds) and releases the
resources once all their requests are completed.
At the same time, other customer threads are also competing for resources. This causes
the possibility of thread deadlocks if the resources are granted in such a manner that blocks the
customer from fulfilling their needs since other customers might be holding on their resources at
the same time. This problem leads to the need for managing thread safety during the execution of
the program to closely manage the allocation of resources to avoid this dilemma.
The Banker's algorithm is run by the operating system whenever a process requests
resources. The algorithm prevents deadlock by denying or postponing the request if it determines
that accepting the request could put the system in an unsafe state (one where deadlock could
occur). When a new process enters a system, it must declare the maximum number of instances
of each resource type that may not exceed the total number of resources in the system.


**User Manual**

The Project consists of the following four Java classes:

Project.java

Customer.java

Bank.java

SafetyCheck.java

To compile this program, simply enter:

  *javac Project.java*

To run the program, enter the following command:

  *java Project m n*

where m is the number of resource types and n is the number of customers. The program only
accepts integer values for m and n in the range of 1 to 10.
