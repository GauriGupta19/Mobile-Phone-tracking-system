public class Exchange extends ExchangeList{
	public int id;
	public Exchange parent;
	private ExchangeList children;
	public MobilePhoneSet mSet;

	//constructor
	public Exchange(int number){
		id=number;
		mSet= new MobilePhoneSet();
		children= new ExchangeList();
	}

	//returns the parent
	public Exchange parent(){
		return parent;
	}

	//returns the number of children
	public int numChildren(){
		Node<Exchange> n=children.head;
		int count=0;
		while(n!=null){
			count++;
			n=n.next;
		}
		return count;
	}

	//returns the ith child starting from 0 else throws exception if the index is out of bound
	public Exchange child(int i) throws Exception {
		Node<Exchange> n=children.head;
			if(i>=numChildren()){
				throw new Exception();
			}
			else{
			for(int j=0;j<i;j++){
				n=n.next;
			}
			return n.data;
		}
	}

	//adds a as the (i+1)th child the exchange node
	public void addChild(Exchange a){
		this.children.InsertRear(a);
		a.parent=this;
	}

	//checks if it is the root
	public boolean isRoot(){
		return parent==null;
	}

	//returns the ith subtree and handles the exception when i is out of bounds
	public RoutingMapTree subtree(int i){
		try{
		Exchange root=child(i);
		RoutingMapTree t=new RoutingMapTree(root);
		if(root!=null) return t;
		else return null;
	}
		catch(Exception e){
			System.out.println("index out of bounds Exception");
			return null;
		}
	}

	//returns the subtree which has root as the given node
	public RoutingMapTree subtree(){
		RoutingMapTree t=new RoutingMapTree(this);
		return t;
	}

	//returns the MobilePhoneSet
	public MobilePhoneSet residentSet(){
	return mSet;
	}

}