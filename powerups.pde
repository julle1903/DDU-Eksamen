class Powerup{

    PVector pos = new PVector(0,0);
    float size;

    Powerup(int powerupPos){
        pos.x = powerupPos;
        pos.y = -size;
        size = 10;
    }

    void Move(){
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
    void Power(){
        
    }

    void Display(){

    }
}

class Mask extends Powerup{

    Mask(int powerupPos){
        super(powerupPos);
        pos.x = powerupPos;
        pos.y = -size;
        size = 10;
    }

    void Power(){

    }
    
    void Display(){

    }
}

class Slower extends Powerup{
        Slower(int powerupPos){
        super(powerupPos);
        pos.x = powerupPos;
        pos.y = -size;
        size = 10;
    }

    void Power(){

    }

    void Display(){

    }
}