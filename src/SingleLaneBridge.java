
public class SingleLaneBridge {

    public static int maxRedcars ;
    public static int maxBluecars ;
    public static int arrivalFreq  ;
    public static int transitTime ;
    public static int sameCarsInBridge ;

    public static void main(String[] args)  {
        // TODO Auto-generated method stub

        //pass arguments in static variables
        try {
            maxRedcars =  Integer.parseInt(args[0])  ;
            maxBluecars =  Integer.parseInt(args[1]) ;
            arrivalFreq = Integer.parseInt(args[3]) ;
            transitTime = Integer.parseInt(args[4]);
            sameCarsInBridge = Integer.parseInt(args[5]) ;
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println("Enter valid command arguments !");
            System.out.println("[max Red cars] [maxBlue cars] [Bridge] [arrival frequency milliseconds] [transit time in milliseconds] [#same cars in Bridge]");
            System.exit(0);
        }
        String method = args[2] ;
        Bridge b = null  ;

        if (method.equals("Bridge"))
            b = new Bridge() ;
        else if (method.equals("SafeBridge"))
            b = new SafeBridge();
        else if (method.equals("FairBridge"))
            b = new FairBridge();
        else
            b = new AdjustedFairBridge();




        b.display();

        Thread red []   = new Thread[maxRedcars];
        Thread blue []  = new Thread[maxBluecars];
        int maxcars ;

        if(maxRedcars >= maxBluecars)
            maxcars = maxRedcars ;
        else
            maxcars = maxBluecars ;
        //start creating and running threads .
        for (int i = 0; i<maxcars; i++) {


            if(i<maxRedcars) {
                red[i] = new Thread(new RedCar(b,i));

                red[i].start();
            }

            if(i<maxBluecars) {
                blue[i] = new Thread(new BlueCar(b,i));

                blue[i].start();
            }
            //Main thread sleeps for arrival frequency
            try {
                Thread.sleep(arrivalFreq);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }


    }

}
