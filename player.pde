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
    void move(){
        if (key == 'd' || keyCode == RIGHT) {
            counter++;
        } 

        if (key == 'a' || keyCode == LEFT) {
            counter--;
        }
    }

    //Draw player
    void display(){
        counter = constrain(counter, 1,5);
        nextPos = level.square*counter;
        player.playerPos.x = round(lerp(player.playerPos.x, player.nextPos, 0.55));
        
        rectMode(CENTER);

        fill(0);
        imageMode(CENTER);
        image(anim.img(), playerPos.x, playerPos.y-32);
        anim.next(3);
    }

}