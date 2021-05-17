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
    void drawBG() {
        imageMode(CORNERS);
        speed += 0.001;
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
    void generate() {
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
    void genObs(){
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
        switch (int(random(3))){
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