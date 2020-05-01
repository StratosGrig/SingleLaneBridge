import java.time.Instant;

public class FairBridge extends Bridge {

    private int nred  = 0; //red cars passing the bridge
    private int nblue = 0; //blue cars passing the bridge
    private int waitblue = 0; //blue cars waiting to pass the bridge
    private int waitred = 0; // red cars waiting to pass the bridge
    private boolean blueturn = true;

    private int maxRedID = -1 ; // maximum red car id that has passed or passing
    private int maxBlueID = -1;// maximum blue car id that has passed or passing

    /*A red car(thread) waits while one or more(depending from sameCarsInBridge variable) blue cars(threads) are passing the bridge OR
      one or more(depending from sameCarsInBridge variable) red cars(threads) are passing the bridge OR there are cars with smaller id that should pass first OR
      it is blue cars's turn and there are blue cars waiting to pass */
    synchronized void redEnter(RedCar car) throws InterruptedException {
        ++waitred;
        while (nblue>0 || nred == SingleLaneBridge.sameCarsInBridge || car.id  != maxRedID + 1|| (waitblue>0 && blueturn)) wait();
        --waitred;
        ++nred;
        maxRedID = car.id;

        System.out.println("Red Car " + car.id + " Passing at " + Instant.now());
        System.out.flush();
    }


    /* A red car(thread) exit the bridge and wakes up ALL the other threads
   when # same cars passing the bridge all of them  must pass the bridge and then notify other threads .*/
    synchronized void redExit(RedCar car){
        --nred;
        blueturn = true;
        if (nred==0)
            notifyAll();

        for(int i=0;i<85;i++) //spaces
            System.out.print(" ");
        System.out.println("Red Car " + car.id + " Passed at " + Instant.now());
        System.out.flush();
    }



    /*A blue car(thread) waits while one or more(depending from sameCarsInBridge variable) red cars(threads) are passing the bridge OR
    one or more(depending from sameCarsInBridge variable) blue cars(threads) are passing the bridge OR there are cars with smaller id that should pass first OR
    it is red cars's turn and there are red cars waiting to pass */
    synchronized void blueEnter(BlueCar car)  throws InterruptedException {
        ++waitblue;
        while (nred>0 || nblue == SingleLaneBridge.sameCarsInBridge || car.id != maxBlueID + 1 || (waitred>0 && !blueturn)) wait();
        --waitblue;
        ++nblue;
        maxBlueID = car.id;

        for(int i=0;i<85;i++) //spaces
            System.out.print(" ");
        System.out.println("Blue Car " + car.id + " Passing at " + Instant.now());
        System.out.flush();
    }

    /* A blue car(thread) exit the bridge and wakes up ALL the other threads
    when # same cars passing the bridge all of them  must pass the bridge and then notify other threads .*/
    synchronized void blueExit(BlueCar car){
        --nblue;
        blueturn = false;
        if (nblue==0)
            notifyAll();

        System.out.println("Blue Car " + car.id + " Passed at " +Instant.now());
        System.out.flush();
    }
}

