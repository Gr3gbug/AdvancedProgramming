import java.util.*;

class GCstrange {
    static List<GCstrange> dump = new LinkedList<>();    
    static final  int COUNT =  10000000;

    public static void main(String [] args) throws InterruptedException{
	GCstrange tmp = null;
	for (int i = 1; i < COUNT; i++){
	    if (i%(COUNT/100)==0) {
		System.out.println(i + " ==> " + dump.size());
		Thread.sleep(1000);
	    }
	    tmp = new GCstrange();
	    System.out.println("i'm in :) ");
	}
    }

    public GCstrange(){
    }
    protected void finalize(){
	dump.add(this);
    }	
}
    
