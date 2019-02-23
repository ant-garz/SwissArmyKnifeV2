/**
@author Anthony Garza
Project: Swiss Army Knife
Purpose: This application is meant to act as a command line application that takes HTTP requests and stores data returned from the request 
        in an arraylist of strings. It will also  take a URL as an input parameter, open JSON referenced by the URL, parse the file for additional URLs,
        make an HTTP request to get each of the files identified in the file, and print the results of each of the additional URLs to the console.
        This is the Second version of SAK for week 6 of OOP. 

        DISCLAIMER: I am not the author of the 3 Sleep Java files. All credit for that code goes to Prof. Pogue, who has given us permission to utilize the  
        Sleep, SleepFast, and SleepFastImplementsRunnable java files. In this SAK java file, I have include pieces of Prof. Pogues files. I am not the orignal author of the SLeep files.
        I did use them and geared them towards my SAK file. The three sleep methods are hybrids I've tweaked from Prof. Pogues original files.
 */

import java.util.ArrayList;
 public class sak {
    public static void main(String[] args){
        System.out.println("Starting Swiss Army Knife...");

        //declare timer variables
        long startTimer;
        long endTimer; 

        if (args.length < 1) {
            //starts off the application if user only types "java sak" by automatically calling help method.
            System.out.println("This application needs at least one argument to function properly.");
            System.out.println("Please reference the -Help method below for more information on how to use the application.\n");

            System.out.println("Help Method Executing...\n");
            
            //Call Help Method with timer for execution
            //time help method execution take nanotime then convert to microseconds by dividing returned value by 1000000
            startTimer = System.nanoTime();
            Help.helpGuide();
            endTimer = System.nanoTime();
            System.out.printf("Help Method Exeuction Time Elapsed: %.2f milliseconds", ((endTimer - startTimer) / 1E6));
        }
        //calling Help method
        else if (args[0].equalsIgnoreCase("-Help")) {
            System.out.println("Help Method Executing...\n");

            //Call Help Method with timer for execution
            startTimer = System.nanoTime();
            Help.helpGuide();          
            endTimer = System.nanoTime();
            System.out.printf("Help Method Exeuction Time Elapsed: %.2f milliseconds", ((endTimer - startTimer) / 1E6));
        }
        //calling HttpRequest Method
        else if (args[0].equalsIgnoreCase("-HttpRequest")) {
            System.out.println("HttpRequest Method Executing...\n");

            //call HttpRequest Method with timer for execution
            startTimer = System.nanoTime(); //starting timer above the nested if statement so if it fails out then it still validly counts execution time
            if (args.length < 2) {
                System.out.println("Please enter in a valid URL in order for the HttpRequest Method to properly work.\n");
                endTimer = System.nanoTime(); //end timer and print execution time elapsed
                System.out.printf("HttpRequest Method Exeuction Time Elapsed: %.2f milliseconds", ((endTimer - startTimer) / 1E6));
            } 
            else {
                //look for URL as second parameter of command line argument
                String URL = args[1];
                HttpRequest urlRequest = new HttpRequest();
                if (urlRequest.readURL(URL)) {
                    System.out.println(urlRequest);
                    endTimer = System.nanoTime(); //end timer and print execution time elapsed
                    System.out.printf("HttpRequest Method Exeuction Time Elapsed: %.2f milliseconds", ((endTimer - startTimer) / 1E6));
                }
                else {
                    //end timer for invalid URL in second parameter
                    endTimer = System.nanoTime(); //end timer and print execution time elapsed
                    System.out.printf("HttpRequest Method Exeuction Time Elapsed: %.2f milliseconds", ((endTimer - startTimer) / 1E6));
                }
            }
        }
        //calling HttpRequestIndex Method
        else if (args[0].equalsIgnoreCase("-HttpRequestIndex")) {
            System.out.println("HttpRequestIndex Method Executing...\n");

            //call HttpRequestIndex Method with timer for execution
            startTimer = System.nanoTime(); //starting timer above the nested if statement so if it fails out then it still validly counts execution time
            if (args.length < 2) {
                System.out.println("Please enter in a valid URL in order for the HttpRequestIndex Method to properly work.\n");
                endTimer = System.nanoTime(); //end timer and print execution time elapsed
                System.out.printf("HttpRequestIndex Method Exeuction Time Elapsed: %.2f milliseconds", ((endTimer - startTimer) / 1E6));
            } 
            else {
                //look for URL as second parameter of command line argument
                String URL = args[1];
                HttpRequestIndex urlRequest = new HttpRequestIndex();
                if (urlRequest.readAllURL(URL)) {
                    System.out.println(urlRequest);
                    endTimer = System.nanoTime(); //end timer and print execution time elapsed
                    System.out.printf("HttpRequestIndex Method Exeuction Time Elapsed: %.2f milliseconds", ((endTimer - startTimer) / 1E6));
                }
                else {
                    //end timer for invalid URL in second parameter
                    endTimer = System.nanoTime(); //end timer and print execution time elapsed
                    System.out.printf("HttpRequestIndex Method Exeuction Time Elapsed: %.2f milliseconds", ((endTimer - startTimer) / 1E6));
                }
            }
        }
        //calling Sleep Method
        else if (args[0].equalsIgnoreCase("-Sleep")) {
            System.out.println("Sleep Method Executing...\n");

            //call Sleep Method with timer for execution
            startTimer = System.nanoTime(); //starting timer above the nested if statement so if it fails out then it still validly counts execution time
            for(int i = 0; i < 2; i++){
                Sleep sleepy = new Sleep(i);

                System.out.println("\nSleep");

                long start = System.currentTimeMillis();    

                sleepy.sleep(); 
                System.out.println("Elapsed Time in milliseconds: " + (System.currentTimeMillis() - start + "\n"));
            }
            endTimer = System.nanoTime();
            System.out.printf("Sleep Method Exeuction Time Elapsed: %.2f milliseconds", ((endTimer - startTimer) / 1E6));

        }
        //calling SleepFast Method
        else if (args[0].equalsIgnoreCase("-SleepFast")) {
            System.out.println("SleepFast Method Executing...\n");
            long start = System.currentTimeMillis();

            //call Sleep Method with timer for execution
            startTimer = System.nanoTime(); //starting timer above the nested if statement so if it fails out then it still validly counts execution time

            //Non-threaded Sleep instance
            System.out.println("\nNon-threaded Sleep");
            start = System.currentTimeMillis();    
            for(int i = 0; i < 2; i++){
                SleepFast mySleepFast = new SleepFast(i);

                mySleepFast.sleep();
            }
            System.out.println("Elapsed Time in milliseconds: " + (System.currentTimeMillis() - start + "\n")); //end of timer for non-threaded instance
            
            //Threaded Sleep instance with 2 
            System.out.println("\nThreaded Sleep");
            start = System.currentTimeMillis();
            SleepFast mySleepFast0 = new SleepFast(0);
            SleepFast mySleepFast1 = new SleepFast(1);

            mySleepFast0.start();
            mySleepFast1.start();

            try {
                mySleepFast0.join();
                mySleepFast1.join();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
            System.out.println("Elapsed Time in milliseconds: " + (System.currentTimeMillis() - start + "\n"));

            //Sleeping ArrayList for next two sleep calls
            ArrayList<SleepFast> sleeperList = new ArrayList<SleepFast>();
            for (int k = 2; k < 12; k++) {
                sleeperList.add(new SleepFast(k));
            }
    
            //Non-threaded ArrayList
            System.out.println("\nNon-Threaded ArrayList sleep: ");
            start = System.currentTimeMillis(); 
            for (SleepFast s : sleeperList) {
                s.sleep();
            }
            System.out.println("Elapsed Time in milliseconds: " + (System.currentTimeMillis() - start + "\n"));

            //Threaded ArrayList
            System.out.println("\nThreaded ArrayList sleep: ");
            start = System.currentTimeMillis();
            for (SleepFast s : sleeperList) {
                s.start();
            }        
            try {
                for (SleepFast s : sleeperList) {
                    s.join();
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
            System.out.println("Elapsed Time in milliseconds: " + (System.currentTimeMillis() - start + "\n"));

            endTimer = System.nanoTime();
            System.out.printf("SleepFast Method Exeuction Time Elapsed: %.2f milliseconds", ((endTimer - startTimer) / 1E6));

        }

        //calling SleepFastImplementsRunnable Method
        else if (args[0].equalsIgnoreCase("-SleepFastImplementsRunnable")) {
            System.out.println("SleepFastImplementsRunnable Method Executing...\n");
            long start = System.currentTimeMillis();

            //call Sleep Method with timer for execution
            startTimer = System.nanoTime(); //starting timer above the nested if statement so if it fails out then it still validly counts execution time

            //Non-threaded Sleep instance
            System.out.println("\nNon-threaded Sleep");
            start = System.currentTimeMillis();    
            for(int i = 0; i < 2; i++){
                SleepFastImplementsRunnable mySleepFast = new SleepFastImplementsRunnable(i);

                mySleepFast.sleep();
            }
            System.out.println("Elapsed Time in milliseconds: " + (System.currentTimeMillis() - start + "\n")); //end of timer for non-threaded instance
            
            //Threaded Sleep instance with 2 
            //create 2 new SleepFastImplementsRunnable objects to toss into threads
            SleepFastImplementsRunnable sleep0 = new SleepFastImplementsRunnable(0);
            SleepFastImplementsRunnable sleep1 = new SleepFastImplementsRunnable(1);
            Thread t0 = new Thread(sleep0);
            Thread t1 = new Thread(sleep1);
            System.out.println("\nThreaded Sleep");
            start = System.currentTimeMillis(); 
            t0.start();
            t1.start();

            try {
                t0.join();
                t1.join();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
            System.out.println("Elapsed Time in milliseconds: " + (System.currentTimeMillis() - start + "\n"));

            //Sleeping ArrayList for next two sleep calls
            ArrayList<SleepFastImplementsRunnable> sleeperList = new ArrayList<SleepFastImplementsRunnable>();
            for (int k = 2; k < 12; k++) {
                sleeperList.add(new SleepFastImplementsRunnable(k));
            }
    
            //Non-threaded ArrayList
            System.out.println("\nNon-Threaded ArrayList sleep: ");
            start = System.currentTimeMillis(); 
            for (SleepFastImplementsRunnable s : sleeperList) {
                s.sleep();
            }
            System.out.println("Elapsed Time in milliseconds: " + (System.currentTimeMillis() - start + "\n"));

            //Threaded ArrayList
            ArrayList<Thread> threadList = new ArrayList<Thread>();
            for (SleepFastImplementsRunnable s: sleeperList) {
                threadList.add(new Thread(s));
            }

            start = System.currentTimeMillis();
            for (Thread t : threadList) {
                t.start();
            }        
            try {
                for (Thread t : threadList) {
                    t.join();
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
            System.out.println("Elapsed Time in milliseconds: " + (System.currentTimeMillis() - start + "\n"));

            endTimer = System.nanoTime();
            System.out.printf("SleepFastImplementsRunnable Method Exeuction Time Elapsed: %.2f milliseconds", ((endTimer - startTimer) / 1E6));

        }
    }
}