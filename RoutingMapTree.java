import java.util.*;

public class RoutingMapTree extends MobilePhoneSet{
	Exchange root;

	//constructor
	public RoutingMapTree(){
		root=new Exchange(0);
		root.parent=null;
	}

	//constructor with a given root
	public RoutingMapTree(Exchange root){
		this.root=root;
	}

	//checks if the it contains the node a 
	public boolean containsNode(Exchange a){

		if(a==root) return true;
		else{
			for(int j=1;j<=root.numChildren();j++){
				try{
				if(root.child(j).subtree().containsNode(a)) return true;
				}
				catch(Exception e){
					System.out.println("index out of bounds Exception");
				}
			}
			return false;
		}
	}

	//switches the phone a on and registers it with base station b and if the phone is not off it throws an error
	public void switchOn(MobilePhone a,Exchange b){
		try{
			if(!a.isOn){
				a.switchOn();
				a.baseStation=b;
				Exchange x=b;
				while(x!=root){
					x.mSet.Insert(a);
					x=x.parent();
				} 
				x.mSet.Insert(a); 
			}
			else{
				throw new Exception();
			}
		}
		catch(Exception e){
			System.out.println("The phone is already on.");
		}
	}

	//switches off the mobile phone a and throws error if a is off
	public void switchOff(MobilePhone a){
		try{
			if(a.status()){
				Exchange x=a.location();
				while(x!=root){
						x.mSet.Delete(a);
						x=x.parent();
				}
				x.mSet.Delete(a);
				a.switchOff();
				a.baseStation=null;
			}
			else{
					throw new Exception();
			}
		}
		catch(Exception e){
			System.out.println("The phone is already off.");
		}
	}

	//checks if any of the node contains mobile phone with id a
	public boolean containsMobile(int a){
		Node<MobilePhone> n=root.residentSet().l.head;
		while(n!=null){
			if(n.data.id==a){
				return true;
			}
			n=n.next;
		}
		return false;
	}

	//returns the mobile phone whose identifier is a 
	public MobilePhone returnMobile(int a){
		Node<MobilePhone> n=root.residentSet().l.head;
		while(n!=null){
			if(n.data.id==a)
				return n.data;
			else n=n.next;
		} return null;
	}

	//checks if it contains any Exchange node with id a
	public boolean containsNode(int a){
		if(a==root.id){
			return true;
		}
		else{
			for(int j=0;j<root.numChildren();j++){
				try{
				if(root.child(j).subtree().containsNode(a)) return true;
			}
			catch(Exception e){System.out.println("IndexOutOfBoundsException");}
		}
			return false;
		}
	}

	//returns the exchange node whose id is a 
	public Exchange returnEx(int a){
		if(root.id==a) return root;

		else{

			for(int j=0;j<root.numChildren();j++){
				try{
					Exchange x=root.child(j).subtree().returnEx(a);
					if(x!=null) {
						return x;}
					}
			catch(Exception e){System.out.println("IndexOutOfBoundsException");}
			}
		}
		return null;
	}


	//checks if any of the node contains mobile phone a 
	public boolean containsMobile(MobilePhone a){
		Node<MobilePhone> n=root.residentSet().l.head;
		while(n!=null){
			if(n.data==a){
				return true;
			}
			n=n.next;
		}
		return false;
	}


	/*Given a mobile phone
	m it returns the level 0 area exchange with which it is registered or
	throws an exception if the phone is not found or switched off.*/
	public Exchange findPhone(MobilePhone m){
		try{
			if(!containsMobile(m)) throw new Exception();
			if(!m.isOn) throw new Exception();
			return m.baseStation;
			
		}
		catch(Exception e){
			System.out.println("Mobile phone not found");
			return null;
		}
		
	}



	/*Given two level 0 area exchanges a and b this method returns the level i
	exchange with the smallest possible value of i which contains both a
	and b in its subtree. If a = b then the answer is a itself.*/

	public Exchange lowestRouter(Exchange a, Exchange b){
		
		if(a==b) return a;
		else{
			if(a!=root) a=a.parent;
			if(b!=root) b=b.parent;
			return lowestRouter(a,b);
		}
		
		
	}



	public ExchangeList routeCall(MobilePhone a, MobilePhone b){
		try{
			if(!containsMobile(a)) throw new Exception();
			if(!containsMobile(b)) throw new Exception();
			Exchange a1=a.baseStation;
			Exchange b1=b.baseStation;
			Exchange c= lowestRouter(a1,b1);
			ExchangeList l1=new ExchangeList();
			ExchangeList l2=new ExchangeList();
			Exchange n=a1;
			while(n!=c){
				l1.InsertRear(n);
				n=n.parent;
			}
			l1.InsertRear(c);
			//Insertion till the lowest router
			n=b1;
			while(n!=c){
				l2.InsertFront(n);
				n=n.parent;
			}
			ExchangeList l=l1.Join(l1,l2);
			return l;
		}
		catch(Exception e){
			System.out.println("Mobile phone not found");
			return null;
		}
	}



	public void movePhone(MobilePhone a, Exchange b){
		try{
			if(!a.isOn) throw new Exception();
			switchOff(a);
			switchOn(a,b);
		}
		catch(Exception e){
			System.out.println("The mobile phone is not on ");
		}
		

	}


	//perform various input actions
	public String performAction(String actionMessage){
		Scanner in=new Scanner(actionMessage);
		String s= in.next();

		//adds the exchange with id b to the exchange with id a and if it doesnot has any node with id a it thows exception
		if(s.equals("addExchange")){
			int a=in.nextInt();
			int b=in.nextInt();
			try{
				if(containsNode(a)){
					Exchange b1=new Exchange(b);
					Exchange a1=returnEx(a);
					if(a1==null){System.out.println("no exchange found");}
					if(a1!=null){
						a1.addChild(b1);
					}
				}
				else{
					throw new Exception();
				}
			}
			catch(Exception e){
				System.out.println("There is no exchange with identifier a");
			}
		}

		// switches ON the mobile phone a at Exchange b. If the mobile did not exist earlier, creates a new mobile phone 
		//with identifier a. If there is no Exchange with an identifier b throws an exception
		if(s.equals("switchOnMobile")){
			int a=in.nextInt();
			int b=in.nextInt();
			try{
				if(containsNode(b)){
					Exchange b1=returnEx(b);
					if(b1==null){System.out.println("no exchange found");}
					if(b1!=null){
						if(containsMobile (a)){
							MobilePhone temp=returnMobile(a);
							if(temp==null){System.out.println("no mobile found");}
							if(temp!=null)
							switchOn(temp,b1);
						}
						else{
							MobilePhone a1=new MobilePhone(a);
							switchOn(a1,b1);
						}
					}
				}
				
			}
			catch(Exception e){
				System.out.println(" There is no exchange with identifier b");
			}
		}

		// switches OFF the mobile phone a. If there is no mobile phone with identifier a, then throws an Exception
		if(s.equals("switchOffMobile")){
			try{
				int a=in.nextInt();
				if(containsMobile(a)){
					MobilePhone temp=returnMobile(a);
					if(temp==null){System.out.println("no mobile found");}
					if(temp!=null)
						switchOff(temp);
				}
			}
			catch(Exception e){
				System.out.println(" There is no mobile phone with identifier a");
			}
		}

		// prints the identifier of the Exchange which is the (b)th child of Exchange a. If b is invalid number throws exception
		if(s.equals("queryNthChild")){
			int a=in.nextInt();
			int b=in.nextInt();
			try{
				Exchange a1=returnEx(a);
				if(a1==null){System.out.println("no exchange found");}
				if(a1!=null){
					actionMessage=actionMessage+": " +a1.child(b).id;
				}
			}
			catch(Exception e){
				System.out.println("index out of bounds Exception");
			}
			System.out.println(actionMessage);
		}

		//prints the identifier of all the mobilephones which are part of the resident set of the Exchange with identifier a.
		if(s.equals("queryMobilePhoneSet")){
			try{
				int a=in.nextInt();
				Exchange a1=returnEx(a);
				if(a1==null){System.out.println("no exchange found");}
				if(a1!=null){
					MobilePhoneSet b1=a1.mSet;
					Node<MobilePhone> n=b1.l.head;
					actionMessage=actionMessage+": ";
					while(n.next!=null){
						actionMessage=actionMessage+n.data.id+", ";
						n=n.next;
					}
				actionMessage=actionMessage+n.data.id;
				}
				System.out.println(actionMessage);
			}
			catch(Exception e){ System.out.println("NullPointerException");}
		}

		if(s.equals("findPhone")){
			try{
				int a=in.nextInt();
				if(!containsMobile(a)) 
					 actionMessage= "queryFindPhone "+a+": "+"Error - No mobile phone with identifier "+a+" found in the network";
				MobilePhone m =returnMobile(a);
				Exchange x=findPhone(m);
				actionMessage="queryFindPhone "+a+": "+x.id;
			}
			catch(Exception e){
				System.out.println("Null pointer Exception");
			}
			System.out.println(actionMessage);
		}

		if(s.equals("lowestRouter")){
			try{
				int a=in.nextInt();
				int b=in.nextInt();
				Exchange a1=returnEx(a);
				Exchange b1=returnEx(b);
				Exchange x= lowestRouter(a1,b1);
				actionMessage=( "queryLowestRouter "+a+" "+b + ": " + x.id );
			}
			catch(Exception e){
				System.out.println("Null pointer Exception");
			}
			System.out.println(actionMessage);
		}

		if(s.equals("findCallPath")){
			try{
				int a=in.nextInt();
				int b=in.nextInt();
				if(!containsMobile(a))  
					actionMessage= "queryFindCallPath "+a+" "+b+ ": "+"Error - Mobile phone with identifier "+a+" is currently switched off";
				if(!containsMobile(b))  
					actionMessage="queryFindCallPath "+a+" "+b+ ": "+"Error - Mobile phone with identifier "+b+" is currently switched off";
				else{
					MobilePhone a1= returnMobile(a);
					MobilePhone b1= returnMobile(b);
					
					ExchangeList l=routeCall(a1,b1);
					ExchangeList.Node<Exchange> n =l.head;
					actionMessage="queryFindCallPath "+a+" "+b+ ": ";
					while(n.next!=null){
						actionMessage=actionMessage+n.data.id+", ";
						n=n.next;
					}
					actionMessage=actionMessage+n.data.id;
				}
				
			}
			catch(Exception e){
				System.out.println("Null pointer Exception");
			}
			
			System.out.println(actionMessage);
		}

		if(s.equals("movePhone")){
			try{
				int a=in.nextInt();
				int b=in.nextInt();
				MobilePhone a1= returnMobile(a);
				Exchange b1=returnEx(b);
				movePhone(a1,b1);
			}
			catch(Exception e){
				System.out.println("null pointer exception");
			}
		}

	
	return actionMessage; //returns the output of the perform action
	}
}