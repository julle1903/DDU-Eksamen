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

    void Display(){
        counter = constrain(counter, 1,5);
        nextPos = level.square*counter;
        player.playerPos.x = round(lerp(player.playerPos.x, player.nextPos, 0.55));
        fill(0);
        ellipse(playerPos.x, playerPos.y+600, playerSize, playerSize);
    }

    void Move(){
        if (key == 'd' || keyCode == RIGHT) {
            counter++;
        } 

        if (key == 'a' || keyCode == LEFT) {
            counter--;
        }
    }
}