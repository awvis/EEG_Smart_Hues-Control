// music player 
import ddf.minim.*;
import ddf.minim.signals.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;

import javax.swing.*;



JFileChooser jfc;


PImage bg;
PImage seeker;
//PImage seeker2;
//PImage closeButton;
//PImage minimizeButton;
PImage prev;
PImage play;
PImage stop;
PImage next;
PImage openfile;
//PImage shuffle;
PImage repeat;
PImage repeatOn;
PImage pause;
PFont font;
PFont font1;

Minim minim;
AudioPlayer player;
FFT fft;
FFT fftLog;
double duration;
AudioMetaData meta;
boolean isPlaying;
boolean isRepeat;

public void setup(){
  size(375, 220, JAVA2D);
  
  minim = new Minim(this);
  player = minim.loadFile("1.mp3");
  player.play();
  duration = player.length();
  isPlaying = true;
  isRepeat = false;
  
  fft = new FFT(player.bufferSize(), player.sampleRate());
  fft.linAverages(128);
  fftLog = new FFT(player.bufferSize(), player.sampleRate());
  fftLog.logAverages(2, 1);
  rectMode(CORNERS);
  font1 = loadFont("nano.vlw");

  font = (loadFont("1.vlw"));

  
  bg = loadImage("main.png");
  seeker = loadImage("seeker.png");
 // seeker2 = loadImage("/Users/viswa/Documents/pfiles/data/seeker2.png");
 // closeButton = loadImage("/Users/viswa/Documents/pfiles/data/closeButton.png");
  //minimizeButton = loadImage("/Users/viswa/Documents/pfiles/data/minimizeButton.png");
  
  prev = loadImage("prev.png");
  play = loadImage("play.png");
  stop = loadImage("stop.png");
  next = loadImage("next.png");
  
  openfile = loadImage("openfile.png");
  //shuffle = loadImage("/Users/viswa/Documents/pfiles/data/shuffle.png");
  repeat = loadImage("repeat.png");
  pause = loadImage("pause.png");
  repeatOn = loadImage("repeat_on.png");  
  jfc = new JFileChooser();
}

public void mouseClicked(){
  if(mouseX > 15 && mouseX < 15+prev.width && mouseY > 104 && mouseY < 104+prev.width){
    println("Previous");
  }
  
  if(mouseX > 35 && mouseX < 35+play.width && mouseY > 104 && mouseY < 104+play.height){
    if(isPlaying){
      isPlaying = false;
      player.pause();
    }else{
      isPlaying = true;
      player.play();
    }
  }
  
  if(mouseX > 55 && mouseX < 55+stop.width && mouseY > 104 && mouseY < 104+stop.width){
    if(isPlaying){
      isPlaying = false;
      player.pause();
      player.rewind();
    }
  }
  
  if(mouseX > 75 && mouseX < 75+next.width && mouseY > 104 && mouseY < 104+next.height){
    println("Next");
  }
  
  //repeat
  if(mouseX > 210 && mouseX < 210+repeat.width && mouseY > 104 && mouseY < repeat.height + 104){
    println("Repeat");
    isRepeat = !isRepeat;
  }
    
  
  if(mouseX > 120 && mouseX < 120+openfile.width && mouseY > 104 && mouseY < 104+openfile.width){
    println("Open New file");
    int result = jfc.showOpenDialog(this);
    if( result == jfc.APPROVE_OPTION){
      String filename = jfc.getSelectedFile().getAbsolutePath();
      println(filename);
      player.close();
      player = minim.loadFile(filename);
      player.play();
    }
  }
}

public void draw(){
textFont(font);
textAlign(CENTER);
fill(1);
textSize(20);
text("BraWo : Brain @ Work", 190, 170);
  meta=player.getMetaData();
  image(bg,0,0);
  // draw the logarithmic averages
  fftLog.forward(player.mix);
  int w = int(fft.specSize()/50);
  int xoffset = (width - (width/3))/2;
  for(int i = 0; i < fftLog.avgSize(); i++)
  {
    fill(111,110,255,0);
    rect(xoffset+i*w+4, 85, xoffset+i*w + w+2, 85 - fftLog.getAvg(i));
  }
  fill(255);
  //seeker bar
  image(seeker, (int)(player.position()/duration*(bg.width-seeker.width)), 84);
  //image(seeker2, 300,105);
  
  //close & minimize
 // image(closeButton, 350, 5);
  //image(minimizeButton, 335,5);
  
  //player
  image(prev, 15, 105);
  image(play, 35, 105);
  image(stop, 55, 105);
  image(next, 75, 105);
  image(openfile, 120, 105);
 // image(shuffle, 160, 105);
  image(repeat, 210, 105);
  
  //text
  textFont(font, 20);
  text(meta.title(), (bg.width-textWidth(meta.title()))/2, 20);
  textSize(20);
  text(meta.author(), (bg.width-textWidth(meta.title()))/2, 40);
  
  int timeLeft = player.position()-player.length();
  String timeLeftStr = String.format("%02d:%02d", timeLeft/1000/60, -timeLeft/1000%60);
  text( timeLeftStr, (bg.width-textWidth(timeLeftStr))/2, 80);
  
  if(isPlaying){
    image(pause, 35, 104);
  }else{
    image(play, 35, 104);
  }

  if(isRepeat){
    image(repeatOn, 210, 104);
  }else{
    image(repeat, 210, 104);
  }
  
  if(isRepeat){
    if(player.position() >= player.length()){
      player.rewind();
      player.play();
    }
  }
}
