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


    void drawBG() {
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

    void Generate() {
        timer-=1/frameRate;

        if (timer < 0) {
        int l = int(random(3))+1;
            obsToGen(l);
            timer=spawnTime;
        }
    }

    void obsToGen(int lane){
        switch (int(random(2))){
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