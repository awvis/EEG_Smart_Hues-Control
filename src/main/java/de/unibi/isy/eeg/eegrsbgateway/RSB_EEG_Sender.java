/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.isy.eeg.eegrsbgateway;

import java.util.Random;
import rsb.Factory;
import rsb.Informer;

/**
 *
 * @author viswa
 */
public class RSB_EEG_Sender {

    //String value = 12;
    public static String scope = "/UBiCI/string/alphabeta/";

    public void setScope(String val) {
        scope = val;
    }

    public String getScope() {
        return scope;
    }

    public static void main(final String[] args) throws Throwable {
double start = 0.7;
double end = 2.3;        
// Get remote server object to call exposed request methods of
        // participants

        // Get a factory instance to create RSB objects.
        final Factory factory = Factory.getInstance();

        final Informer<Object> informer = factory.createInformer(scope);

        // Activate the informer to be ready for work
        informer.activate();
            // Event event = new Event();
        // Send and event using a method that accepts the data and
        // automatically creates an appropriate event internally.
        //String val = Integer.toString(value);
        //Object val = (Object)value;
         //note a single Random object is reused here
   
     
      String value = "0.5";
     
    for (int i=1;i<10;i++){
     informer.send(value);
     value = value + 0.1;
    }
        // As there is no explicit removal model in java, always manually
        // deactivate the informer if it is not needed anymore
        informer.deactivate();

    }

}
