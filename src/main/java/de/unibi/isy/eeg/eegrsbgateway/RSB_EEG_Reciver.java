package de.unibi.isy.eeg.eegrsbgateway;

import de.citec.jps.core.JPService;
import de.citec.jps.properties.JPHardwareSimulationMode;
import de.citec.jul.exception.CouldNotPerformException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rsb.AbstractEventHandler;
import rsb.Event;
import rsb.Factory;
import rsb.Listener;
import rsb.RSBException;

public class RSB_EEG_Reciver extends AbstractEventHandler {

    public Object EEG_Value;
    public Double Val;
    public Double Vall;
    public static int counter = 0;
    public static boolean is_running1 = false;

    public final RSB_Sender_HA ha;

    double average = 0.0;

    private List<Double> values = new ArrayList<Double>();

    public RSB_EEG_Reciver() throws CouldNotPerformException, InterruptedException {
        ha = new RSB_Sender_HA();
    }

    public double getSum() {
        double sum = 0;
        //for (Double Val:values)
        for (int i = 0; i < values.size(); i++) {
            sum = sum + values.get(i);
        }
        return sum;
    }

    public int getCount() {
        return values.size();
    }

    public void addValue(Double value) {
        values.add(Val);
    }

    public Double getAverage() {
        return getSum() / getCount();
    }

    @Override
    public void handleEvent(final Event event) {

        EEG_Value = event.getData();
        counter++;
        Val = Double.valueOf((String) EEG_Value);
        addValue(Val);
        if (counter == 10) {
            Vall = getAverage();
            ha.setEEG_Value(Vall);
            try {
                ha.decision();
                counter = 0;
                values.clear();
            } catch (IOException | RSBException | CouldNotPerformException ex) {
                Logger.getLogger(RSB_EEG_Reciver.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Sent Val" + Vall);
        }
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

        // execute the main screen
        Process p = null;
        if (!is_running1) {
            p = Runtime.getRuntime().exec("java -jar " + filepath3);
            is_running1 = true;
        } else {

            p.getOutputStream().close();
            p.getInputStream().close();
            p.getErrorStream().close();

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
