//Imports
import java.util.*;

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

void setup() {
    size(800,800, P3D);

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

void draw() {
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
            curFact = fact[int(random(fact.length))];
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
void restart() {
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


void keyPressed(){
    player.move();
}