package de.unibi.isy.eeg.eegrsbgateway;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import de.citec.dal.DALService;
import de.citec.dal.DeviceViewerFrame;
import de.citec.dal.data.Location;
import de.citec.dal.exception.RSBBindingException;
import de.citec.dal.hal.al.AmbientLightRemote;
import de.citec.dal.hal.al.RollershutterRemote;
import de.citec.dal.hal.devices.philips.PH_Hue_E27Controller;
import de.citec.dal.service.DALRegistry;
import de.citec.dal.util.DALException;
import de.citec.dal.util.Observable;
import de.citec.dal.util.Observer;
import java.awt.Color;
import rsb.*;
import java.io.IOException;
import java.lang.Double;
import java.lang.Runtime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rst.homeautomation.AmbientLightType;

public class RSB_Sender_HA {

    /**
     * The Object for RST for Ambient Light of the Intelligent Apartment
     */
    private Color light;
    /**
     * the values of light color for different states
     */
    public final static Color COLOR_0 = Color.PINK;
    public final static Color COLOR_1 = Color.WHITE;
    public final static Color COLOR_2 = Color.BLUE;
    public final static Color COLOR_3 = Color.RED;
    public final static Color COLOR_4 = Color.GREEN;
    public final static Color COLOR_5 = Color.YELLOW;

    /**
     * Decision making limit values for home automation
     */
    public final static double LIMIT_0 = 0.70;
    public final static double LIMIT_1 = 0.99; //0.7 - 1.0 Sleep State ********
    public final static double LIMIT_2 = 1.00;
    public final static double LIMIT_3 = 1.24; // 1.0 1.24 Concentrated ********
    public final static double LIMIT_4 = 1.25;
    public final static double LIMIT_5 = 2.30; // 1.25 2.30 Hyper Active *******

    private final AmbientLightRemote lightsControl;

  
    //external jar filepaths
    public String filepath1 = "/home/brawo/workspace/eegrsbgateway/src/jars/BrawoMusicPlayer/applet/BrawoMusicPlayer.jar";
    public String filepath2 = "/home/brawo/workspace/eegrsbgateway/src/jars/BrawoRelaxGame/applet/BrawoRelaxGame.jar";

    //boolean for java opening
    public static boolean is_running1 = false;
    public static boolean is_running2 = false;


    public RSB_Sender_HA() {

        lightsControl = new AmbientLightRemote();

        lightsControl.init(new Scope(SCOPE_LIGHTS));

        lightsControl.activate();
    }

    /**
     * Integer EEG value after processing of EEG data for decision And its set
     * and get data Functions
     */
    public double EEG_Value;

    public void setEEG_Value(Double Val) {
        EEG_Value = Val;
    }

    /**
     *
     * @throws java.io.IOException
     * @throws rsb.RSBException
     * @throws de.citec.dal.util.DALException
     */
    public void decision() throws IOException, RSBException, DALException {
        if (EEG_Value >= LIMIT_0 && EEG_Value < LIMIT_2) {

        //pink - //0.7 - 1.0 Sleep State ********
            light = COLOR_0;

            System.out.println("Most relaxed event");

        }
        if (EEG_Value >= LIMIT_2 && EEG_Value < LIMIT_3) {

        // blue light - // 1.0 1.24 Concentrated *******
            light = COLOR_2;
            System.out.println("Received event for Music Lightly relaxed before Sleep ");
            Process p = null;
            // execute the main screen

            if (!is_running1) {
                p = Runtime.getRuntime().exec("java -jar " + filepath1);
                is_running1 = true;
            } else {

                p.getOutputStream().close();
                p.getInputStream().close();
                p.getErrorStream().close();

            }
        }

        if (EEG_Value >= LIMIT_4 && EEG_Value < LIMIT_5) {

        // green light - // 1.25 2.30 Hyper Active *******
            light = COLOR_4;
            System.out.println("Received event for Game: ");
            Process p = null;
            // execute the main screen

            if (!is_running2) {
                p = Runtime.getRuntime().exec("java -jar " + filepath2);
                is_running2 = true;
            } else {

                p.getOutputStream().close();
                p.getInputStream().close();
                p.getErrorStream().close();

            }
            // total sleep state: so from current color change to lightest
            //lightsControl.setColor(light);
            // total active state: so from current color change to lightest
            //value = 100;
            //lightsControl.setColor(lightsControl.getData().getColor().toBuilder().setValue(value).build());

        }
        sendData();
    }

    /**
     * Scope for the Intelligent apartment automation system
     */
    String SCOPE_LIGHTS = "/home/amilab/ambientlight/testlight/";

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

