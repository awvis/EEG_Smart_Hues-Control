package de.unibi.isy.eeg.eegrsbgateway;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import de.citec.dal.hal.al.AmbientLightRemote;
import de.citec.dal.hal.al.RollershutterRemote;
import de.citec.dal.util.DALException;
import de.citec.dal.util.Observable;
import de.citec.dal.util.Observer;
import java.awt.Color;
import rsb.*;
import java.io.IOException;
import java.lang.Runtime;
import rst.homeautomation.AmbientLightType;

public class RSB_Sender_HA {

    /**
     * The Object for RST for Ambient Light of the Intelligent Apartment
     */
    private Color light;
    /**
     * the values of light color for different states
     */
    public final static Color COLOR_0 = Color.BLACK;
    public final static Color COLOR_1 = Color.WHITE;
    public final static Color COLOR_2 = Color.BLUE;
    public final static Color COLOR_3 = Color.RED;
    public final static Color COLOR_4 = Color.GREEN;
    public final static Color COLOR_5 = Color.YELLOW;

    /**
     * Decision making limit values for home automation
     */
    public final static int LIMIT_0 = 0;
    public final static int LIMIT_1 = 10;
    public final static int LIMIT_2 = 20;
    public final static int LIMIT_3 = 30;
    public final static int LIMIT_4 = 40;
    public final static int LIMIT_5 = 50;
    public final static int LIMIT_6 = 60;

    private final AmbientLightRemote lightsControl;
    

    //external jar filepaths
    public String filepath1 = "/Users/viswa/NetBeansProjects/eegrsbgateway/src/jars/BrawoMusicPlayer/applet/BrawoMusicPlayer.jar";
    public String filepath2 = "/Users/viswa/NetBeansProjects/eegrsbgateway/src/jars/BrawoRelaxGame/applet/BrawoRelaxGame.jar";
   
    public RSB_Sender_HA() {
        lightsControl = new AmbientLightRemote();
       

        lightsControl.init(new Scope(SCOPE_LIGHTS));
      

        lightsControl.activate();
       
        lightsControl.addObserver(new Observer<AmbientLightType.AmbientLight>() {

            @Override
            public void update(Observable<AmbientLightType.AmbientLight> source, AmbientLightType.AmbientLight data) throws Exception {
                System.out.println("color change:" + data.getColor());
                //data.getState().getState;
            }
        });
        /*  try {
         lightsControl.setColor(lightsControl.getData().getColor().toBuilder().setValue(10).build());
         } catch (DALException ex) {
         Logger.getLogger(RSB_Sender_HA.class.getName()).log(Level.SEVERE, null, ex);
         } */
    }

   

    /**
     * Integer EEG value after processing of EEG data for decision And its set
     * and get data Functions
     */
    private int EEG_Value;

    public void setEEG_Value(Integer Val) {
        EEG_Value = Val;
    }

    public int getEEG_Value() {
        return EEG_Value;
    }

    /**
     * function for making decision on the basis of eeg integer value
     *
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws rsb.RSBException
     * @throws de.citec.dal.util.DALException
     */
    public void decision() throws IOException, InterruptedException, RSBException, DALException {
           
        if (EEG_Value >= LIMIT_0 && EEG_Value < LIMIT_1) {
            System.out.println("Received event Light/Shutter Event 1");
           
            light = COLOR_0;
        }
        if (EEG_Value >= LIMIT_1 && EEG_Value < LIMIT_2) {
            System.out.println("Received event Light/Shutter Event 2");
            
            light = COLOR_1;
        }
        if (EEG_Value >= LIMIT_2 && EEG_Value < LIMIT_3) {
            System.out.println("Received event Light/Shutter Event 3");
           
            light = COLOR_2;
        }
        if (EEG_Value >= LIMIT_3 && EEG_Value < LIMIT_4) {
            System.out.println("Received event Light/Shutter Event 4");
           
            light = COLOR_3;
        }
        if (EEG_Value >= LIMIT_4 && EEG_Value < LIMIT_5) {
            System.out.println("Received event ");
         Process p = null; 
            // execute the main screen
         try
    {
        p = Runtime.getRuntime().exec("java -jar " + filepath1);
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
        }
        if (EEG_Value >= LIMIT_5 && EEG_Value < LIMIT_6) {
            System.out.println("Received event ");
Process p = null; 
            // execute the main screen
         try
    {
        p = Runtime.getRuntime().exec("java -jar " + filepath2);
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
        }
        sendData();
    }

    /**
     * Scope for the Intelligent apartment automation system
     */
    String SCOPE_LIGHTS = "/home/wardrobe/ambientlight/hallway_0/";
  

    /**
     * Function to send data via RSB to Automate Intelligent apartment
     *
     * @throws rsb.RSBException
     * @throws de.citec.dal.util.DALException
     */
    public void sendData() throws RSBException, DALException {
        System.out.println("sendData");
        // Get a factory instance to create RSB objects.
        //brightness
        lightsControl.setColor(light);
       

    }

}
//set.powerstate
