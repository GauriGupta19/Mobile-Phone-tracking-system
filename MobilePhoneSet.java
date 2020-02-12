public class MobilePhoneSet extends Myset<MobilePhone>{
	
	//MobilePhoneSet constructor
	public MobilePhoneSet(){
		super();
	}

	//returns the union of two MobilePhoneSets
	public MobilePhoneSet Union(MobilePhoneSet a){
		MobilePhoneSet USet= new MobilePhoneSet();
		USet=a;
		Node<MobilePhone> n=l.head;
		while(n!=null){
			try{
				USet.Insert(n.data);
			}
			catch(Exception e){}
			n=n.next;
		}
		
		return USet;
	}

	//returns the intersection of two MobilePhoneSets
	public MobilePhoneSet Intersection(MobilePhoneSet a){
		MobilePhoneSet ISet = new MobilePhoneSet();
		Node<MobilePhone> n=l.head;
		while(n!=null){
			if(a.IsMember(n.data)){
				ISet.Insert(n.data);
				n=n.next;
			}
			else n=n.next;
		}
		return ISet;
	}
}	