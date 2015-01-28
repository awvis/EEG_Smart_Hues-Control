import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class BrawoBrainAtWork extends PApplet {

// main screen which has Flow Chart



PImage bg;
int x,y;

// setup phase

public void setup(){
//set the window size
size(1000,600);
noStroke();

bg = loadImage("1.jpg");

}




public void draw(){
  background(bg);
  drawConstantGraphics(); 
}

// constant graphics
public void drawConstantGraphics()
{
//heading
//textFont(font);
textAlign(CENTER);
fill(0);
textSize(17);
//text("Brain Waves | Analysis | Automation", 330, 40);
//text("Ambient Environment", 340, 70);

}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BrawoBrainAtWork" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
