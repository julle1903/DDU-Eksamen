//Imports
import java.util.*;

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

void setup() {
    size(800,800, P3D);

    level = new LevelController();
    player = new PlayerController();
    rectX = width/2;
    rectY = height/2+150;
}

void draw() {
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


    void restart() {
        persons = new ArrayList<Person>();
        doors = new ArrayList<InfectedDoor>();
        level = new LevelController();
        player = new PlayerController();
        d=0;
        stage = 1;

    }


    void keyPressed(){
        player.Move();
    }