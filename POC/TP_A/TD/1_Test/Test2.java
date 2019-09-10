 
class Test2 extends Thread {
	String msg;
	
	public Test2(String s)
	{
		msg = s;
	}
	
	public void run()
	{
		try { sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}
		System.out.print(msg + " ");
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		Test2 a = new Test2("Hello");
		Test2 b = new Test2("World");
		a.start();
		b.start();
		a.join();
		b.join();
		System.out.print("\n");
	}
}