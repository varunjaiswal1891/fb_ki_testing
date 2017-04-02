package thread;

class Mythread implements Runnable
{
	String str;
	Mythread (String str){
		this.str=str;
	}
	public void run()
	{
		for(int i=0;i<5;i++)
		{
		System.out.println(str+" ");	
		}
		try{
			Thread.sleep(5000);
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}
	}
}

public class Multi_tasking {

	
	  public static void main(String args[])
	  {
		  Mythread obj1= new Mythread("hello");
	       Mythread obj2= new Mythread ("hii");
	       Thread t1= new Thread(obj1);
	       Thread t2= new Thread(obj2);
	  	 t2.start();
	       t1.start();  
	
	  }
	   
}
