package de.unibi.isy.eeg.eegrsbgateway;

import rsb.AbstractEventHandler;
import rsb.Event;
import rsb.Factory;
import rsb.Listener;
import rsb.transport.*;
import java.lang.Runtime;
import rsb.patterns.EventCallback;
import rsb.patterns.LocalServer;

public class RSB_EEG_Reciver extends AbstractEventHandler  {

    RSB_Sender_HA ha;

    public Object EEG_Value;
    public Integer Val;

    public void handleEvent(final Event event) {
        EEG_Value = event.getData();

        // convert the object to Integer for the function
        Val = Integer.valueOf((String) EEG_Value);

        System.out.println("Received Viswa " + Val);
        // Send Value to the other side
        ha = new RSB_Sender_HA();
        ha.setEEG_Value(Val);

        // print out the value after the conversion
        try {
            ha.decision();
            System.out.println("Received" + Val);
        } catch (Throwable e) {
            System.out.println("Error in sending to Home Automation" + e);
        } 

    } 
   
        /**
     * The scope for EEG Integer Value
     */
    public static String scope = "/eeg/result";
    public static String filepath3 = "/Users/viswa/NetBeansProjects/eegrsbgateway/src/jars/BrawoBrainAtWork/applet/BrawoBrainAtWork.jar";
    
    public static void main(final String[] args) throws Throwable {
       
        Process p = null; 
            // execute the main screen
         try
    {
        p = Runtime.getRuntime().exec("java -jar " + filepath3);
    }
    finally
    {
        if (p != null)
        {
           p.getOutputStream().close();
           p.getInputStream().close();
           p.getErrorStream().close();
        }
    }
       
 
         // Get a factory instance to create new RSB objects.
        final Factory factory = Factory.getInstance();

        // Create a Listener instance on the specified scope that will
        // receive events and dispatch them asynchronously to all
        // registered handlers; activate the listener.
        final Listener listener = factory.createListener(scope);
        listener.activate();
        try {
            // Add an EventHandler that will print events when they
            // are received.

            listener.addHandler(new RSB_EEG_Reciver(), true);

            // Wait for events.
            while (true) {
                Thread.sleep(1);
            }
        } finally {
            // Deactivate the listener after use.
            listener.deactivate();
        } 
    }

}
