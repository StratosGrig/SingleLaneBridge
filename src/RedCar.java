public class RedCar implements Runnable {


    Bridge control;
    int id;

    RedCar(Bridge b,  int id) {
        this.id = id;
        control = b;
        control.printArrrivalTimeRed(this);
    }

    public void run() {
        try {
            control.redEnter(this);
            control.transit();
            control.redExit(this);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
        }
    }




}
