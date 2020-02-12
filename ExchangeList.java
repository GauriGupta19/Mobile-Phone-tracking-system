public class ExchangeList extends LinkedList<Exchange>{

	public ExchangeList Join(ExchangeList l1, ExchangeList l2){
		Node<Exchange> n=l1.head;
		if(n==null) return l2; 
		while(n.next!=null){
			n=n.next;
		}
		n.next=l2.head;
		return l1;
	}
}