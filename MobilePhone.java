public class MobilePhone{
	public int id;
	public boolean isOn;
	public Exchange baseStation;

	//MobilePhone constructor
	public MobilePhone(int number){
		this.id=number;
		isOn=false;
	}

	//returns id
	public int number(){
		return id;
	}
	
	//returns true if the mobile is on
	public boolean status(){
		return isOn;
	}

	//turns on the mobile phone
	public void switchOn(){
		isOn=true;
	}

	//turns off the mobile phone
	public void switchOff(){
		isOn=false;
	}

	//returns the base station of the mobile phone
	public Exchange location(){
		try{
			if(!isOn) throw new Exception();
			else return baseStation;
        }
		catch(Exception e){
			System.out.println("The mobile phone is switched off");
			return null;
		}
		
	}
}