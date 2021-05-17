class Mask{

    //mask vars
    StripAnim mask;

    PVector pos = new PVector(0,0);
    PVector size;

    //Constructor for masls
    Mask(int powerupPos){
        size = new PVector(64,32);
        pos.x = powerupPos;
        pos.y = -size.y;

        //Resize sprites individually
        PImage[] strip = sheet.imagesAt(0, 64, 64, 64,1);
        for (int i = 0; i < strip.length;i++){
            strip[i].resize(64,64);
        }

        mask = new StripAnim(strip);
    }

    //powerup and move
    void power(){
        player.score += 2;

    }

    void move(){
        pos.y +=level.speed;

        //Check player collision
        if (pos.y + size.y/2> player.playerPos.y-player.playerSize/2 && pos.y - size.y/2 < player.playerPos.y+player.playerSize/2 &&
        pos.x + size.x/2 > player.playerPos.x-player.playerSize/2 && pos.x - size.x/2 < player.playerPos.x+player.playerSize/2) {
            power();
            masks.remove(this);
        }
    }
    
    //Display
    void display(){
        imageMode(CENTER);
        image(mask.img(), pos.x,pos.y);
    }
}

class Sprit{
    //Sprit vars
    StripAnim sprite;

    PVector pos = new PVector(0,0);
    PVector size;

    //Sprit constructor
    Sprit(int powerupPos){
        size = new PVector(64,90);
        pos.x = powerupPos;
        pos.y = -size.y;

        sprite = new StripAnim(sheet.imagesAt(0, 64*5, 64, 64,7));
    }

    //Powerup and move
    void power(){
        player.score += 1;
    }

    void move(){
        pos.y +=level.speed;

        //Check player collision
        if (pos.y + size.y/2> player.playerPos.y-player.playerSize/2 && pos.y - size.y/2 < player.playerPos.y+player.playerSize/2 &&
        pos.x + size.x/2 > player.playerPos.x-player.playerSize/2 && pos.x - size.x/2 < player.playerPos.x+player.playerSize/2) {
            power();
            sprit.remove(this);
        }
    }

    //display
    void display(){
        imageMode(CENTER);
        image(sprite.img(), pos.x,pos.y);
        sprite.next(4);
    }
}