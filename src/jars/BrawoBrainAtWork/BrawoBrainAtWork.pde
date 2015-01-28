// main screen which has Flow Chart



PImage bg;
int x,y;

// setup phase

void setup(){
//set the window size
size(1000,600);
noStroke();

bg = loadImage("1.jpg");

}




void draw(){
  background(bg);
  drawConstantGraphics(); 
}

// constant graphics
void drawConstantGraphics()
{
//heading
//textFont(font);
textAlign(CENTER);
fill(0);
textSize(17);
//text("Brain Waves | Analysis | Automation", 330, 40);
//text("Ambient Environment", 340, 70);

}
