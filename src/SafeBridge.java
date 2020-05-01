import java.time.Instant;

public class SafeBridge extends Bridge {

    private int nred  = 0; //red cars passing the bridge 
    private int nblue = 0; //blue cars passing the bridge 

    private int maxRedID = -1 ; // maximum red car id that has passed or passing	
    private int maxBlueID = -1;// maximum blue car id that has passed or passing 


    /*A red car(thread) WAITS while one or more(depending from sameCarsInBridge variable) blue cars(threads) are passing the bridge OR  
    one or more(depending from sameCarsInBridge variable) red cars(threads) are passing the bridge OR there are cars with smaller id that should pass first */
    synchronized void redEnter(RedCar car) throws InterruptedException {
        while (nblue>0 || nred == SingleLaneBridge.sameCarsInBridge || car.id  != maxRedID + 1) wait();
        ++nred;
        maxRedID = car.id;

        System.out.println("Red Car " + car.id + " Passing at " + Instant.now());
        System.out.flush();

    }

    /* A red car(thread) exit the bridge and wakes up ALL the other threads 
    when # same cars passing the bridge  all of them  must pass the bridge and then notify other threads .*/
    synchronized void redExit(RedCar car){
        --nred;
        if (nred== 0 )
            notifyAll();

        for(int i=0;i<85;i++) //spaces
            System.out.print(" ");
        System.out.println("Red Car " + car.id + " Passed at " + Instant.now());
        System.out.flush();
    }

    /*A blue car WAITS while one or more(depending from sameCarsInBridge variable) red cars are passing the bridge OR 
    one or more(depending from sameCarsInBridge variable) blue cars are passing the bridge OR there are cars with smaller id that should pass first*/
    synchronized void blueEnter(BlueCar car) throws InterruptedException {
        while (nred>0 || nblue == SingleLaneBridge.sameCarsInBridge || car.id != maxBlueID + 1 ) wait();
        ++nblue;

        maxBlueID = car.id;
        for(int i=0;i<85;i++) //spaces
            System.out.print(" ");
        System.out.println("Blue Car " + car.id + " Passing at " + Instant.now());
        System.out.flush();
    }

    /*A a blue car(thread) exit the bridge and wakes up ALL the other threads  
    when # same cars passing the bridge all of them  must pass the bridge and then notify other threads .*/
    synchronized void blueExit(BlueCar car){
        --nblue;
        if (nblue== 0)
            notifyAll();

        System.out.println("Blue Car " + car.id + " Passed at " +Instant.now());
        System.out.flush();

    }
}