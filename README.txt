/**********************************************************************
 *  readme.txt template                                                   
 *  Kd-tree
**********************************************************************/

Name:    Grimur Garpsson
Login:   grimur 14@ru.is
Section instructor: 
Guðmundur Bjarni Kristinsson

Partner name:     Raquelita Ros Aguilar
Partner login:    raquelita15@ru.is
Partner section instructor:
HMV

/**********************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 **********************************************************************/

A simple helper class that included: size, root node, left and right pointers as well as a boolean value for veritcal or not.
It had a constructor in it so a newly made node would have all the values it was created with and its posiiton in the tree all
in the right place.

/**********************************************************************
 *  Describe your method for range search in a kd-tree.
 **********************************************************************/

A private helper method was created as is appropriate for data security. In said helper method we had several special cases:
If the input node is null, if the input node is vertical and if its not. 

If its vertical the method compares x-coordinates to see if they go outside the min or max. If it isn't vertical the method compares
y-coordinates to see if they are outside of min or max.

This is done each time as the function recursively calls itself and searches. If the node is null then it stops.

/**********************************************************************
 *  Describe your method for nearest neighbor search in a kd-tree.
 **********************************************************************/

A private helper method was created as is appropriate for data security. In said helper method we had several special cases:
If the current node is null, if the closest point is null and if the closest point is closer to the target than the current point.

The last special case updates which is nearest while the other two special cases are to end the recursive tree search implemented in
this method.

/**********************************************************************
 *  Give the total memory usage in bytes (using tilde notation and 
 *  the standard 64-bit memory cost model) of your 2d-tree data
 *  structure as a function of the number of points N. Justify your
 *  answer below.
 *
 *  Include the memory for all referenced objects (deep memory),
 *  including memory for the nodes, points, and rectangles.
 **********************************************************************/

bytes per Point2D: 32 bytes

bytes per RectHV: 256 bytes

bytes per KdTree of N points (using tilde notation):   ~352N
[include the memory for any referenced Node, Point2D and RectHV objects]


/**********************************************************************
 *  Give the expected running time in seconds (using tilde notation)
 *  to build a 2d-tree on N random points in the unit square.
 *  Use empirical evidence by creating a table of different values of N
 *  and the timing results. (Do not count the time to generate the N 
 *  points or to read them in from standard input.)
 **********************************************************************/

Insert 1000	0,02 sec
Insert 10k	0,21 sec
Insert 100k	2,01 sec

~N

/**********************************************************************
 *  How many nearest neighbor calculations can your brute-force
 *  implementation perform per second for input100K.txt (100,000 points)
 *  and input1M.txt (1 million points), where the query points are
 *  random points in the unit square? Explain how you determined the
 *  operations per second. (Do not count the time to read in the points
 *  or to build the 2d-tree.)
 *
 *  Repeat the question but with the 2d-tree implementation.
 **********************************************************************/

                     calls to nearest() per second
                     brute force           2d-tree


input100K.txt		593,68 per second (168,44 sec runtime, 100k/168,44 = 593,68)
input1M.txt			590,23 per second (1694,25 sec runtime, 1M/1694,25 = 590,23)

/**********************************************************************
 *  Known bugs / limitations.
 **********************************************************************/


/**********************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and d�mat�mar, but do
 *  include any help from people (including course staff, 
 *  classmates, and friends) and attribute them by name.
 **********************************************************************/

Raquelita discussed the project description with a friend, Mr. Cabrera,
that helped her understand this algorithm better. 

/**********************************************************************
 *  Describe any serious problems you encountered.                    
 **********************************************************************/

It took us a lot longer to get our time testing to work as it should then we anticipated.

/**********************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **********************************************************************/

I contributes all of PointSET as well as the insert and contains methods of KdTree and wrote the readme and tested the runtimes.

My partner contributed the rest.


/**********************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **********************************************************************/