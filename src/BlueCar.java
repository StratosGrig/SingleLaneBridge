public class BlueCar implements Runnable {


    Bridge control;
    int id;


    BlueCar(Bridge b,  int id) {
        this.id = id;
        control = b;
        control.printArrrivalTimeBlue(this);
    }

    public void run() {
        try {
            control.blueEnter(this);
            control.transit();
            control.blueExit(this);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
        }
    }



}