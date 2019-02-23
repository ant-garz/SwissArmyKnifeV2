import java.util.ArrayList;

class SleepFast extends Thread {
    private int sleepNumber;

    //non-default constructor
    SleepFast(int SleepNumberIn) {
        sleepNumber = SleepNumberIn;
    }
    public void sleep() {

        System.out.println(sleepNumber + " - Going to sleep");
        try {
            Thread.sleep(1000); // sleep for one second.
        }   catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        System.out.println("... " + sleepNumber + " - done sleeping.");
    }
 
    public void run() {
        sleep();
    }
}