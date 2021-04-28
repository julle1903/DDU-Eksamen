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


    void drawBG() {
        speed += 0.001;
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

    void Generate() {
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


    void obsToGen(){
    int spawn = (int) random (1,5);
    ArrayList<Integer> l = new ArrayList<Integer>();
    for (int i = 0; i < spawn; i++){
        int lane = (int)random(1,6);

        if (!l.contains(lane)){
            l.add(lane);
        }
    }
    for (int i = 0; i < l.size(); i++){
        switch (int(random(2))){
                case 0 :
                    persons.add(new Person(square*l.get(i)));
                break;

                case 1 :
                    doors.add(new InfectedDoor(square*l.get(i)));
                break;
            }
        }
    }

    void powerupsToGen() {
    int spawn = (int) random (1,5);
    ArrayList<Integer> l = new ArrayList<Integer>();
    for (int i = 0; i < spawn; i++){
        int lane = (int)random(1,6);

        if (!l.contains(lane)){
            l.add(lane);
        }
    }
    for (int i = 0; i < l.size(); i++){
        switch (int(random(2))){
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