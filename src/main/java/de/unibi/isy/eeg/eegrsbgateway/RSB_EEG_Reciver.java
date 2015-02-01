package de.unibi.isy.eeg.eegrsbgateway;

import de.citec.dal.DALService;
import de.citec.dal.DeviceViewerFrame;
import de.citec.dal.data.Location;
import de.citec.dal.exception.RSBBindingException;
import de.citec.dal.hal.devices.philips.PH_Hue_E27Controller;
import de.citec.dal.service.DALRegistry;
import de.citec.dal.util.DALException;
import de.citec.jps.core.JPService;
import de.citec.jps.properties.JPHardwareSimulationMode;
import java.io.IOException;
import rsb.AbstractEventHandler;
import rsb.Event;
import rsb.Factory;
import rsb.Listener;
import java.util.logging.Level;
import java.lang.Double;
import java.util.logging.Logger;
import rsb.RSBException;

public class RSB_EEG_Reciver extends AbstractEventHandler {
   
    RSB_Sender_HA ha;

    public Object EEG_Value;
    public Double Val;
  
 @Override
    public void handleEvent(final Event event) {
        
        EEG_Value = event.getData();
        Val = Double.valueOf((String) EEG_Value);
        
        ha = new RSB_Sender_HA();
        ha.setEEG_Value(Val);
        try { 
            ha.dec();
        } catch (IOException | RSBException | DALException ex) {
            Logger.getLogger(RSB_EEG_Reciver.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Sent Val" + Val);
        }
    
       
    
    /**
     * The scope for EEG Integer Value
     */

    public static String scope = "/UBiCI/string/alphabeta/";
    //  public static String scope = "/eeg/result";
    public static String filepath3 = "/home/brawo/workspace/eegrsbgateway/src/jars/BrawoBrainAtWork/applet/BrawoBrainAtWork.jar";
       
    public static void main(final String[] args) throws Throwable {


        //Device code import        
        JPService.setApplicationName("DeviceManager");
        JPService.registerProperty(JPHardwareSimulationMode.class);
        JPService.parseAndExitOnError(args);
        
        eeg n = new eeg();

        // execute the main screen
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("java -jar " + filepath3);

        } finally {
            if (p != null) {
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

class eeg {

    public eeg() {
        new DALService(new eeg.DeviceInitilizer()).activate();
        new DeviceViewerFrame().setVisible(true);

    }

    class DeviceInitilizer extends DALService.DeviceInitializer {

        @Override
        public void initDevices(DALRegistry registry) {
            Location amilab = new Location("amilab");
            try {
                registry.register(new PH_Hue_E27Controller("PH_Hue_E27_100", "testlight", amilab));
            } catch (RSBBindingException ex) {
                Logger.getLogger(RSB_EEG_Reciver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
