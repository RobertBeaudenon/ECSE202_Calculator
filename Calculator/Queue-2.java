package ass4;

//Robert Beaudenon , 260725065
public class Queue {
	public listNode back;
	public listNode front;

	public boolean isEmpty() {
		if (back == null && front == null) {
			return true;
		}
		return false;
	}

	//insert something inside the queue
	public void Enqueue(String newStr)//insertion of a string newStr
	{
		listNode mn = new listNode(newStr);   //constructor : 2parts :
		//first part is for the pointer second one is to allocate
		// First we check if the queue is empty
		if(back == null)//queue is empty : we can  check it
			//with front or back doesn't matter
		{
			back = mn;  //both front and back is pointing to the isolated node mn
			front = mn;

		}else   //when the queue is not empty 
		{


			back.previous = mn;   //previous  of back points now   to mn
			mn.next = back;  //next of mn points now to back
			back = mn; //and back is now moved to 
			//mn  the new name of mn is back because when inserted in 
			//the back mn becomes the first element of the queue
		}
	}

	//delete or serve the front 
	public String Dequeue()
	{
		// This checks whether we a have front node or not
		if(front == null)
		{
			System.out.println("Error: the queue is empty");
			return null;
		}

		// Take the result string 
		String result_str = front.keyVal;//if the value is 
		//in this node then return the string form the front ,
		//because in this case we have one node

		if(front.previous == null) //if no one is behind front , then queue is empty 
		{			
			front = null; //like the queue is empty the we set front and back to null
			back = null;
		}else//of the queue is not  empty
		{
			listNode pre_front = front.previous;
			pre_front.next = null;//next of the prefront is now equal 
			//to null because front was removed form the queue 
			front = pre_front; 
		}

		return result_str;
	}
}


	
	
	
	
