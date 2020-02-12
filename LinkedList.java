	public class LinkedList<Object>{
	//Node class
	class Node<Object>{
		 Object data;
		Node<Object> next;
		Node<Object> prev;
	//Node constructor
		public Node(Object d){
			data=d;
			next=null;
			prev=null;
		}
	}	
		
	public Node<Object> head;

	//LinkedList Constructor
	public LinkedList(){
		head=null;
	}

	//checks if the linkedlist is empty
	public boolean IsEmpty(){
		return head==null;

	}

	//checks if a is the member of the linkedlist
	public boolean IsMember(Object o){
		Node<Object> n=head;
		while(n!=null){
			if(n.data==o)
				return true;
			else n=n.next;
		}
		return false;
	}

	//inserts a at the front of the linkedlist
	public void InsertFront(Object o){
		Node<Object> x=new Node<Object>(o);
		x.next=head;
		head=x;
	}

	//inserts a at the rear end of the linkedlist
	public void InsertRear(Object o){
		Node<Object> n=head;
		Node<Object> x=new Node<Object>(o);
		if(n==null){
			head=x;
		}
		else{
		while(n.next!=null) n=n.next;
		n.next=x;
		x.prev=n;
		}
	}

	//deletes a if it in the list else throws exception
	public void Delete(Object o){
		Node<Object> n=head; 
		try{
			if(n==null){  //when the linkedlist is empty
				throw new Exception();
			}
			if(n.next==null && n.data==o) head=null;   //if the LinkedList has only one node which is to be deleted
			
			else if(n.data==o){ //if the node to be delted is the head of the linkedlist
				
				head=n.next;
				head.prev=null;
			}
			else {
				while(n!=null){
					if(n.data==o) break;
					n=n.next;
				}
				if(n.next!=null){    //if the node to be deleted is not the last node
					n.next.prev=n.prev;
				}
				if(n.prev!=null){   //if the node to be deleted is not the first node
					n.prev.next=n.next;
				}

			}
			return;
		}

		catch(Exception e){
			System.out.println("The list is empty");
		}
	}

}