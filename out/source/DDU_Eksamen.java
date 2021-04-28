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


//
ArrayList<Powerup> powerups = new ArrayList<Powerup>();
ArrayList<Person> persons = new ArrayList<Person>();
ArrayList<InfectedDoor> doors = new ArrayList<InfectedDoor>();
PlayerController player;
LevelController level;
int stage = 0;
float rectX, rectY, rectSize = 80;

//distance
float d = 0;

public void setup() {
    

    level = new LevelController();
    player = new PlayerController();
    rectX = width/2;
    rectY = height/2+150;
}

public void draw() {
    rectMode(CENTER);
    textAlign(CENTER);
    switch(stage) {
        case 0:
        background(0);
        text("Corona run", width/2, height/2);
        text("Press the button to begin the game", width/2, height/2+20);
        rect(rectX, rectY-rectSize, rectSize, rectSize/2);
        
        if (mousePressed && (mouseX > rectX-rectSize/2  && mouseX < rectX + rectSize/2 
            && mouseY < rectY - (rectSize/2) && mouseY > rectY - (rectSize + rectSize/4))) {
            stage = 1;
        }
        break;

        case -1:
        background(0);
        textSize(20);
        text("You got fucked by Corona", width/2, height/2);
        text("Press the button to restart", width/2, height/2+20);
        rect(rectX, rectY-rectSize, rectSize, rectSize/2);
        
        if (mousePressed && (mouseX > rectX-rectSize/2  && mouseX < rectX + rectSize/2 
            && mouseY < rectY - (rectSize/2) && mouseY > rectY - (rectSize + rectSize/4))) {
                restart();
        }
        break;

        case 1:
        background(0);

        if (player.playerHealth < 1) {
            stage = -1;
        }
        level.Generate();
        level.drawBG();

        for (Person p : persons) {
            p.Display();
            p.Move();
        }

        //door collision
        for (int i = 0; i < persons.size(); i++) {
            persons.get(i).Collide();
        }

        for (InfectedDoor d : doors) {
            d.Display();
            d.Move();
        }
        
        //door collision
        for (int i = 0; i < doors.size(); i++){
            doors.get(i).Collide();
        }

        for (int i = 0; i < powerups.size(); i++){
            powerups.get(i).Move();
        }

        for (Powerup p : powerups){
            p.Display();
        }

        player.Display();
        textSize(40);
        fill(255);
        text(nf(d,0,1), width/2, 60);
        break;
        }
    } 


    public void restart() {
        persons = new ArrayList<Person>();
        doors = new ArrayList<InfectedDoor>();
        level = new LevelController();
        player = new PlayerController();
        d=0;
        stage = 1;

    }


    public void keyPressed(){
        player.Move();
    }
class LevelController {
    int square = width/6;
    float spawnTimeObstacles = 3;
    float spawnTimePowerups = 3;
    float timer;
    float[] yVals = new float[2];
    PImage[] bg = new PImage[2];

    float speed = 1;

    LevelController() {
        timer = 0;
        PImage img = loadImage("vej2.png");
        for (int i = 0; i < bg.length; i++) {
            bg[i] = img;
        }
        yVals[0] = 0;
        yVals[1] = height;
    }


    public void drawBG() {
        speed += 0.001f;
        for (int i = 0; i < yVals.length; i++) {
            yVals[i]+=speed;
            if (yVals[i] > height) {
                yVals[i] = -height;
            }
        }
        for (int i = 0; i < bg.length; i++) {
            image(bg[i], 0, yVals[i]);
        }
        d += speed/100;
    }

    public void Generate() {
        timer-=1/frameRate;

        if (timer < 0) {
            obsToGen();
            timer=spawnTimeObstacles - speed/5;
        }
        if (timer < 0) {
            obsToGen();
            timer=spawnTimePowerups - speed/5;
        }
    }


    public void obsToGen(){
    int spawn = (int) random (1,5);
    ArrayList<Integer> l = new ArrayList<Integer>();
    for (int i = 0; i < spawn; i++){
        int lane = (int)random(1,6);

        if (!l.contains(lane)){
            l.add(lane);
        }
    }
    for (int i = 0; i < l.size(); i++){
        switch (PApplet.parseInt(random(2))){
                case 0 :
                    persons.add(new Person(square*l.get(i)));
                break;

                case 1 :
                    doors.add(new InfectedDoor(square*l.get(i)));
                break;
            }
        }
    }

    public void powerupsToGen() {
    int spawn = (int) random (1,5);
    ArrayList<Integer> l = new ArrayList<Integer>();
    for (int i = 0; i < spawn; i++){
        int lane = (int)random(1,6);

        if (!l.contains(lane)){
            l.add(lane);
        }
    }
    for (int i = 0; i < l.size(); i++){
        switch (PApplet.parseInt(random(2))){
                case 0 :
                    powerups.add(new Mask(square*l.get(i)));
                break;

                case 1 :
                    powerups.add(new Mask(square*l.get(i)));
                break;
            }
        }
    }
}
class Obstacle {
    Obstacle() {
    }
    
    public void Display() {
    }

    public void Move() {
    }

    public void Collide() {

    }

}



class Person extends Obstacle {
    PVector pos;
    PVector size;
    boolean dead = false;

    Person(float xPos) {
        //2 typer en "infected" og en "Risikogruppe person"
        size = new PVector(20,20);
        pos = new PVector(xPos,0-size.y);
    }

    public void Display() {
        rectMode(CENTER);
            fill(0,0,200);
            rect(pos.x,pos.y,size.x,size.y);
    }
    
    public void Move() {
        pos.y +=level.speed;
    }

    public void Collide() {
        if (pos.y > player.playerPos.y-player.playerSize/2 && pos.y < player.playerPos.y+player.playerSize/2 &&
        pos.x > player.playerPos.x-player.playerSize/2 && pos.x < player.playerPos.x+player.playerSize/2 ) {
            persons.remove(this);
            player.playerHealth -= 1;
            print(player.playerHealth);
        }
    }
}


class InfectedDoor extends Obstacle {
    PVector pos;
    PVector size;
    boolean dead = false;

    InfectedDoor(float xPos) {
        size = new PVector(20,20);
        pos = new PVector(xPos,-size.y);
    }

    public void Display() {
        rectMode(CENTER);
        fill(200,0,0);
        rect(pos.x,pos.y,size.x,size.y);
    }

    public void Move() {
        pos.y +=level.speed;
    }

    public void Collide() {        
        if (pos.y > player.playerPos.y-player.playerSize/2 && pos.y < player.playerPos.y+player.playerSize/2 &&
        pos.x > player.playerPos.x-player.playerSize/2 && pos.x < player.playerPos.x+player.playerSize/2 ) {
            player.playerHealth -= 1;
            doors.remove(this);
            print(player.playerHealth);
        }
    }
}


class PlayerController{
    PVector playerPos;
    float playerSize;
    int counter = 3;
    float nextPos;
    int playerHealth = 3;
    
    PlayerController(){
        playerPos = new PVector(0,600);
        playerSize = 50;    
        nextPos = level.square*counter;
    }

    public void Display(){
        counter = constrain(counter, 1,5);
        nextPos = level.square*counter;
        player.playerPos.x = round(lerp(player.playerPos.x, player.nextPos, 0.55f));
        fill(0);
        ellipse(playerPos.x, playerPos.y, playerSize, playerSize);
    }

    public void Move(){
        if (key == 'd' || keyCode == RIGHT) {
            counter++;
        } 

        if (key == 'a' || keyCode == LEFT) {
            counter--;
        }
    }
}
class Powerup{

    PVector pos = new PVector(0,0);
    float size;

    Powerup(int powerupPos){
        pos.x = powerupPos;
        pos.y = -size;
        size = 10;
    }

    public void Move(){
        pos.y++;

        //Check player collision
        if (pos.x < player.playerPos.x + player.playerSize/2+size/2 &&
        pos.x > player.playerPos.x - player.playerSize/2+size/2 &&
        pos.y < player.playerPos.y + 15 &&
        pos.y > player.playerPos.y - 15){
            Power();
            powerups.remove(this);
        }
    }
    public void Power(){
        
    }

    public void Display(){

    }
}

class Mask extends Powerup{

    Mask(int powerupPos){
        super(powerupPos);
        pos.x = powerupPos;
        pos.y = -size;
        size = 10;
    }

    public void Power(){

    }
    
    public void Display(){

    }
}

class Slower extends Powerup{
        Slower(int powerupPos){
        super(powerupPos);
        pos.x = powerupPos;
        pos.y = -size;
        size = 10;
    }

    public void Power(){

    }

    public void Display(){

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
