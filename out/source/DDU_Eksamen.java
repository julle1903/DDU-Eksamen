import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class DDU_Eksamen extends PApplet {

//Imports


//Game variables
ArrayList<Mask> masks = new ArrayList<Mask>();
ArrayList<Sprit> sprit = new ArrayList<Sprit>();
ArrayList<InfectedPerson> infectedPerson = new ArrayList<InfectedPerson>();
ArrayList<OldMan> oldMan = new ArrayList<OldMan>();
PlayerController player;
LevelController level;
int stage = 0;
float rectX, rectY, rectSize = 100;
Spritesheet sheet;

//Fact loading
boolean factLoaded;
String[] fact;
String curFact;

//UI
Spritesheet UI;
StripAnim bg;
StripAnim starterButton;
StripAnim mainOverskrift;
StripAnim gameoverOverskrift;
StripAnim gameoverButton;
StripAnim gameoverbg;
StripAnim victoryOverskrift;
StripAnim victoryButton;
StripAnim victorybg;

//distance
float d = 0;

public void setup() {
    

    level = new LevelController();

    rectX = width/2;
    rectY = height/2+300;

    //loading and modifying spritesheet
    sheet = new Spritesheet("spritesheet.png");
    UI = new Spritesheet("Ui.png");

    sheet.rescale(250);
    player = new PlayerController();

    fact = loadStrings("facts.csv");

    //Main Screen anim
    bg = new StripAnim(UI.imagesAt(0, 83, 800, 800,32));
    starterButton = new StripAnim(UI.imagesAt(0, 2611, 180, 180,1));
    mainOverskrift = new StripAnim(UI.imagesAt(0, 0, 625, 80,1));

    //Gameover Screen anim
    gameoverOverskrift = new StripAnim(UI.imagesAt(0, 1687, 299, 38,1));
    gameoverbg = new StripAnim(UI.imagesAt(1, 885, 800, 800,29));

   
    PImage[] gameoverStrip = UI.imagesAt(0, 2865, 370, 68,1);
        for (int i = 0; i < gameoverStrip.length;i++){
            gameoverStrip[i].resize(180,50);
        }
    gameoverButton = new StripAnim(gameoverStrip);

    //Victory anim
    victoryOverskrift = new StripAnim(UI.imagesAt(0, 2793, 399, 70,1));
    victorybg = new StripAnim(UI.imagesAt(0, 1727, 800 , 800,1));
    
    PImage[] victoryStrip = UI.imagesAt(0, 2529, 806, 80,1);
        for (int i = 0; i < victoryStrip.length;i++){
            victoryStrip[i].resize(300,60);
        }
    victoryButton = new StripAnim(victoryStrip);
    
}

public void draw() {
    rectMode(CENTER);
    textAlign(CENTER);
    switch(stage) {
        case 0:
        //Main menu animation
        imageMode(CENTER);
        image(bg.img(), height/2,width/2);
        bg.next(12); 
        
        //Draw title
        image(mainOverskrift.img(),width/2, height/2);

        fill(5, 255, 5);

        //Draw start button
        imageMode(CENTER);
        image(starterButton.img(), rectX, rectY-rectSize);

        //Draw help box
        fill(255, 190);
        rect(width/2, height/2 + 110, rectSize * 6, rectSize+20, 8);
        fill(0);
        textSize(20);
        textLeading(18);
        text("Du skal vise at du kan komme igennem hovedgaden\nuden at være en risiko for dig selv eller de andre borgere.\nDu skal undgå de tydeligt syge personer\nog risiko personerne (de ældre).\nUndervejs vil du opnå point for din indsats.\nMasker = 2 point og Sprit = 1 point.", width/2, height/2+70);

        //Start button
        if (mousePressed && (mouseX > rectX-rectSize/2  && mouseX < rectX + rectSize/2 
            && mouseY < rectY - (rectSize/2) && mouseY > rectY - (rectSize + rectSize/4))) {
            stage = 1;
        }
        break;

        //Gameover screen
        case -1:
        //Draw background image
        imageMode(CENTER);
        image(gameoverbg.img(), height/2, width/2);
        gameoverbg.next(8);

        //Load covid fact
        textSize(20);
        fill(255, 5, 5);
        if (!factLoaded){
            curFact = fact[PApplet.parseInt(random(fact.length))];
            factLoaded = true;
        }
        
        //display covid fact and game over text
        fill(255, 190);
        rect(width/2, height/2 + 100, rectSize * 6, rectSize, 8);
        fill(0);
        text("Fact om COVID 19:",width/2, height/2 + 75);
        text(curFact, width/2, height/2+480, width/4*3, height);
        text("Try again", rectX, rectY-rectSize);

        //draw gameover title
        imageMode(CENTER);
        image(gameoverOverskrift.img(), width/2,height/2 - 150);

        //draw restart button
        imageMode(CENTER);
        image(gameoverButton.img(), rectX, rectY-rectSize);


        //check mouse button for restart
        if (mousePressed && (mouseX > rectX-rectSize/2  && mouseX < rectX + rectSize/2 
            && mouseY < rectY - (rectSize/2) && mouseY > rectY - (rectSize + rectSize/4))) {
                restart();
        }
        break;

        case 1:
        background(0);

        if (player.score < 0) {
            stage = -1;
        }
        level.generate();
        level.drawBG();

        //Game for loops for graphics and collision seperated to avoid concurrentmodificationexception
        for (InfectedPerson p : infectedPerson) {
            p.display();
        }
        
        //infected person
        for (int i = 0; i < infectedPerson.size();i++){
            infectedPerson.get(i).move();
        }

        for (int i = 0; i < infectedPerson.size(); i++) {
            infectedPerson.get(i).collide();
        }

        //Oldman
        for (OldMan o : oldMan) {
            o.display();
        }
        for (int i = 0; i < oldMan.size();i++){
            oldMan.get(i).move();
        }

        for (int i = 0; i < oldMan.size(); i++){
            oldMan.get(i).collide();
        }

        //Masks
        for (int i = 0; i < masks.size(); i++){
            masks.get(i).move();
        }

        for (Mask m : masks){
            m.display();
        }

        //Sprit
        for (int i = 0; i < sprit.size(); i++){
            sprit.get(i).move();
        }

        for (Sprit s : sprit){
            s.display();
        }

        //Draw player
        player.display();

        //Draw user interface
        textSize(40);
        fill(255);
        text(nf(d,0,1), width/2, 60);
        text("Point "+player.score, width/4, 60);
        if (d >=1000)
            stage = 2;
        
        break;
        

        //Victory screen
        case 2:
        //Draw victory background
        imageMode(CENTER);
        image(victorybg.img(), width/2,height/2);
        
        imageMode(CENTER);
        image(victoryOverskrift.img(), width/2, height/3);

        //Congratulation text
        textSize(20);
        fill(0);
        text("Tillykke, du er kommet igennem gågaden!", width/2, height/2);
        text("Du har nu vist at du kan komme igennem gågaden og samtidig overholde Corona restriktionerne.", width/2, height/2+410, width/4*3, height);
        text("Du opnåede " + player.score + " point, prøv at se om du kan gøre det bedre!", width/2, height/2+90);
        text("Prøv igen", width/2, height/2+140);
        fill(255);
        
        imageMode(CENTER);
        image(victoryButton.img(), rectX, rectY-rectSize);

        //restart button
        if (mousePressed && (mouseX > rectX-(rectSize/4*3)  && mouseX < rectX + (rectSize/4*3) 
            && mouseY < rectY - (rectSize/2) && mouseY > rectY - (rectSize + rectSize/4))) {
                restart();
        }
        break;

    }
} 

//Restart function - resets vars
public void restart() {
    infectedPerson = new ArrayList<InfectedPerson>();
    oldMan = new ArrayList<OldMan>();
    sprit = new ArrayList<Sprit>();
    masks = new ArrayList<Mask>();
    level = new LevelController();
    player = new PlayerController();
    d=0;
    stage = 1;
    factLoaded = false;
}


public void keyPressed(){
    player.move();
}
class LevelController {
    //Vars
    int square = width/6;
    float spawnTimeObstacles = 3;
    float spawnTimePowerups = 3;
    float timer;
    float[] yVals = new float[2];
    PImage[] bg = new PImage[2];

    float speed = 1;

    LevelController() {
        //Load images and set timer for obs generation
        timer = 0;
        PImage img = loadImage("vej.png");
        for (int i = 0; i < bg.length; i++) {
            bg[i] = img;
        }
        yVals[0] = 0;
        yVals[1] = height;
    }

    //Draw scrolling background by moving bottom background to the top, when it goes below the bottom of the screen
    public void drawBG() {
        imageMode(CORNERS);
        speed += 0.001f;
        for (int i = 0; i < yVals.length; i++) {
            yVals[i]+=speed;
        }
        for (int i = 0; i < yVals.length; i++){
            if (yVals[i] > height) {
                yVals[i] = -height;
            }
        }
        for (int i = 0; i < bg.length; i++) {
            image(bg[i], 0, yVals[i]);
        }
        d += speed/100;
    }

    //Generate obstacles timber
    public void generate() {
        timer-=1/frameRate;

        if (timer < 0) {
            genObs();
            timer=spawnTimeObstacles - speed/5;
        }
        if (timer < 0) {
            genObs();
            timer=spawnTimePowerups - speed/5;
        }
    }

    //Generate obstacles
    public void genObs(){
    int spawn = (int) random (1,5);
    ArrayList<Integer> l = new ArrayList<Integer>();
    for (int i = 0; i < spawn; i++){
        int lane = (int)random(1,6);

        if (!l.contains(lane)){
            l.add(lane);
        }
    }

    //Generate random obstacle or powerup
    for (int i = 0; i < l.size(); i++){
        switch (PApplet.parseInt(random(3))){
            //If Obstacle is generated
                case 0 :
                    infectedPerson.add(new InfectedPerson(square*l.get(i)));
                break;

                case 1 :
                    oldMan.add(new OldMan(square*l.get(i)));
                break;

                //If powerup is generated
                case 2 :
                    if (random(2)<1) {
                        if(random(2)<1) {
                            masks.add(new Mask(square*l.get(i))); 
                        } else {
                            sprit.add(new Sprit(square*l.get(i))); 
                        }
                    } else {
                       if (random(2)<1) {
                           infectedPerson.add(new InfectedPerson(square*l.get(i)));
                       } else {
                           oldMan.add(new OldMan(square*l.get(i)));
                       }
                    }
                break;
            }
        }
    }
}
class InfectedPerson {
    PVector pos;
    PVector size;
    boolean dead = false;
    
    StripAnim infected;

    //Infected person constructor
    InfectedPerson(float xPos) {
        size = new PVector(64,64);
        pos = new PVector(xPos,0-size.y);

        infected = new StripAnim(sheet.imagesAt(1,1, 64, 64,6));
    }

    //Move and collide
    public void move() {
        pos.y +=level.speed*1;
        if (pos.y > height + size.y)
            infectedPerson.remove(this);
    }

    public void collide() {
        if (pos.y + size.y/2 > player.playerPos.y-player.playerSize/2 && pos.y - size.y/2 < player.playerPos.y+player.playerSize/2 &&
        pos.x + size.x/2 > player.playerPos.x-player.playerSize/2 && pos.x - size.x/2 < player.playerPos.x+player.playerSize/2) {
            infectedPerson.remove(this);
            player.score -= 1;
        }
    }

    //display
    public void display() {
        imageMode(CENTER);
        image(infected.img(), pos.x,pos.y);
        infected.next(5);
    }
}


class OldMan {
    PVector pos;
    PVector size;
    boolean dead = false;
    
    StripAnim old;

    //OldMan constructor
    OldMan(float xPos) {
        size = new PVector(64,96);
        pos = new PVector(xPos,-size.y);

        old = new StripAnim(sheet.imagesAt(0, 64*3, 64, 64,4));
    }

    //move
    public void move() {
        pos.y +=level.speed*1.2f;
        if (pos.y > height + size.y)
            oldMan.remove(this);
    }

    //Collide old man with young man
    public void collide() {        
        if (pos.y + size.y/2> player.playerPos.y-player.playerSize/2 && pos.y - size.y/2 < player.playerPos.y+player.playerSize/2 &&
        pos.x + size.x/2 > player.playerPos.x-player.playerSize/2 && pos.x - size.x/2 < player.playerPos.x+player.playerSize/2) {
            player.score -= 1;
            oldMan.remove(this);
        }
        for(int i = 0; i < infectedPerson.size();i++){
            if (pos.y + size.y/2> infectedPerson.get(i).pos.y-infectedPerson.get(i).size.y/2 && pos.y - size.y/2 < infectedPerson.get(i).pos.y+infectedPerson.get(i).size.y/2 &&
            pos.x + size.x/2 > infectedPerson.get(i).pos.x-infectedPerson.get(i).size.x/2 && pos.x - size.x/2 < infectedPerson.get(i).pos.x+infectedPerson.get(i).size.x/2) {
                oldMan.remove(this);
            }
        }
    }
    //Display
    public void display() {
        imageMode(CENTER);
        image(old.img(), pos.x,pos.y);
        old.next(3);
    }
}


class PlayerController{
    //Player count
    PVector playerPos;
    float playerSize;
    int counter = 3;
    float nextPos;
    int score = 0;

    StripAnim anim;

    //Player constructor
    PlayerController(){
        playerPos = new PVector(0,600);
        playerSize = 96;    
        nextPos = level.square*counter;

        anim = new StripAnim(sheet.imagesAt(0, 64*4, 64, 64,4));
    }


    //Move player
    public void move(){
        if (key == 'd' || keyCode == RIGHT) {
            counter++;
        } 

        if (key == 'a' || keyCode == LEFT) {
            counter--;
        }
    }

    //Draw player
    public void display(){
        counter = constrain(counter, 1,5);
        nextPos = level.square*counter;
        player.playerPos.x = round(lerp(player.playerPos.x, player.nextPos, 0.55f));
        
        rectMode(CENTER);

        fill(0);
        imageMode(CENTER);
        image(anim.img(), playerPos.x, playerPos.y-32);
        anim.next(3);
    }

}
class Mask{

    //mask vars
    StripAnim mask;

    PVector pos = new PVector(0,0);
    PVector size;

    //Constructor for masls
    Mask(int powerupPos){
        size = new PVector(64,32);
        pos.x = powerupPos;
        pos.y = -size.y;

        //Resize sprites individually
        PImage[] strip = sheet.imagesAt(0, 64, 64, 64,1);
        for (int i = 0; i < strip.length;i++){
            strip[i].resize(64,64);
        }

        mask = new StripAnim(strip);
    }

    //powerup and move
    public void power(){
        player.score += 2;

    }

    public void move(){
        pos.y +=level.speed;

        //Check player collision
        if (pos.y + size.y/2> player.playerPos.y-player.playerSize/2 && pos.y - size.y/2 < player.playerPos.y+player.playerSize/2 &&
        pos.x + size.x/2 > player.playerPos.x-player.playerSize/2 && pos.x - size.x/2 < player.playerPos.x+player.playerSize/2) {
            power();
            masks.remove(this);
        }
    }
    
    //Display
    public void display(){
        imageMode(CENTER);
        image(mask.img(), pos.x,pos.y);
    }
}

class Sprit{
    //Sprit vars
    StripAnim sprite;

    PVector pos = new PVector(0,0);
    PVector size;

    //Sprit constructor
    Sprit(int powerupPos){
        size = new PVector(64,90);
        pos.x = powerupPos;
        pos.y = -size.y;

        sprite = new StripAnim(sheet.imagesAt(0, 64*5, 64, 64,7));
    }

    //Powerup and move
    public void power(){
        player.score += 1;
    }

    public void move(){
        pos.y +=level.speed;

        //Check player collision
        if (pos.y + size.y/2> player.playerPos.y-player.playerSize/2 && pos.y - size.y/2 < player.playerPos.y+player.playerSize/2 &&
        pos.x + size.x/2 > player.playerPos.x-player.playerSize/2 && pos.x - size.x/2 < player.playerPos.x+player.playerSize/2) {
            power();
            sprit.remove(this);
        }
    }

    //display
    public void display(){
        imageMode(CENTER);
        image(sprite.img(), pos.x,pos.y);
        sprite.next(4);
    }
}
//Spritesheet class for easy handling of spritesheets
class Spritesheet{
  PImage sheet;
  //Scale variable to rescale sheet
  int scale = 100;
  
  Spritesheet(String filepath){
    //load img from file
    sheet = loadImage(filepath);
  }
  
  //Rescale sheet based on percentege
  public void rescale(int percent){
    sheet.resize(sheet.width*percent/100,sheet.height*percent/100);
    scale = percent;
  }
  //Grabs the sprite at the x and y pos with width w and height h
  public PImage imageAt(int x, int y, int w, int h){
    return sheet.get(x*scale/100,y*scale/100,w*scale/100,h*scale/100);
  }
  
  //Grabs mulitple sprites at x*i and y pos with width and height h
  public PImage[] imagesAt(int x, int y, int w, int h, int nImg){
    
    PImage[] img = new PImage[nImg];
    
    for (int i = 0; i < nImg; i++){
      img[i] = sheet.get(x+i*w*scale/100,y*scale/100,w*scale/100,h*scale/100);
    }
    return img;
  }
}
//Stripanimation class for animating sprites
class StripAnim{
  PImage[] strip;
  int frames;
  int curFrame;
  float frameTime;

  StripAnim(PImage[] imgList){
    strip = imgList;
    frames = imgList.length;
  }
  
  //Goes to the next frame (rate) times a second
  public void next(float rate){
    frameTime-=rate/frameRate;

    if (frameTime<0){
      curFrame++;
      if (curFrame >= frames){
        curFrame = 0;
      }
      frameTime=1;
    }
  }
  
  //Returns current image for displaying
  public PImage img(){
    return strip[curFrame];
  }
}



  public void settings() {  size(800,800, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "DDU_Eksamen" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
