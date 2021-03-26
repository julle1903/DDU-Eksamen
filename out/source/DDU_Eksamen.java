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

public class DDU_Eksamen extends PApplet {

ArrayList<Person> persons = new ArrayList<Person>();
ArrayList<InfectedDoor> doors = new ArrayList<InfectedDoor>();
PlayerController player;
LevelController level;



public void setup() {
    
    rectMode(CENTER);
    level = new LevelController();
    player = new PlayerController();
}

public void draw() {
    background(0);

    level.Generate();
    level.drawBG();

    for (Person p : persons) {
        p.Display();
        p.Move();
        p.Collide();
    }

    for (InfectedDoor d : doors) {
        d.Display();
        d.Move();
        d.Collide();
    }

    player.Display();
}


public void keyPressed(){
    player.Move();
}
class LevelController {
    int square = width/6;
    float spawnTime = 5;
    float timer;
    int[] yVals = new int[2];
    PImage[] bg = new PImage[2];

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
        for (int i = 0; i < yVals.length; i++) {
            yVals[i]+=1;
            if (yVals[i] > height) {
                yVals[i] = -height;
            }
        }
        for (int i = 0; i < bg.length; i++) {
            image(bg[i], 0, yVals[i]);
        }
    }

    public void Generate() {
        timer-=1/frameRate;

        if (timer < 0) {
        int l = PApplet.parseInt(random(3))+1;
            obsToGen(l);
            timer=spawnTime;
        }
    }

    public void obsToGen(int lane){
        switch (PApplet.parseInt(random(2))){
                case 0 :
                    persons.add(new Person(square*lane));
                break;

                case 1 :
                    doors.add(new InfectedDoor(square*lane));
                break;

                case 2 :
                    println("text");
                break;
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
        pos = new PVector(xPos,-size.y);
    }

    public void Display() {
        rectMode(CENTER);
            fill(0,0,200);
            rect(pos.x,pos.y,size.x,size.y);
    }
    
    public void Move() {
        pos.y +=1;
    }

    // void Collide() {
    //     if (pos.y > player.playerPos.y) {
    //         println("dead2");
    //     }
    // }
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
        pos.y +=1;
    }

    // void Collide() {
    //     if (pos.y > player.playerPos.y) {
    //         println("dead1");
    //     }
    // }
}


class PlayerController{
    PVector playerPos;
    float playerSize;
    int counter = 3;
    float nextPos;
    
    PlayerController(){
        playerPos = new PVector(0,0);
        playerSize = 50;    
        nextPos = level.square*counter;
    }

    public void Display(){
        counter = constrain(counter, 1,5);
        nextPos = level.square*counter;
        player.playerPos.x = round(lerp(player.playerPos.x, player.nextPos, 0.55f));
        fill(0);
        ellipse(playerPos.x, playerPos.y+600, playerSize, playerSize);
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

  public void settings() {  size(800,800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "DDU_Eksamen" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
