class InfectedPerson {
    PVector pos;
    PVector size;
    boolean dead = false;
    
    StripAnim infected;

    //Infected person constructor
    InfectedPerson(float xPos) {
        size = new PVector(64,64);
        pos = new PVector(xPos,0-size.y);

        infected = new StripAnim(sheet.imagesAt(1,1, 64, 64,6));
    }

    //Move and collide
    void move() {
        pos.y +=level.speed*1;
        if (pos.y > height + size.y)
            infectedPerson.remove(this);
    }

    void collide() {
        if (pos.y + size.y/2 > player.playerPos.y-player.playerSize/2 && pos.y - size.y/2 < player.playerPos.y+player.playerSize/2 &&
        pos.x + size.x/2 > player.playerPos.x-player.playerSize/2 && pos.x - size.x/2 < player.playerPos.x+player.playerSize/2) {
            infectedPerson.remove(this);
            player.score -= 1;
        }
    }

    //display
    void display() {
        imageMode(CENTER);
        image(infected.img(), pos.x,pos.y);
        infected.next(5);
    }
}


class OldMan {
    PVector pos;
    PVector size;
    boolean dead = false;
    
    StripAnim old;

    //OldMan constructor
    OldMan(float xPos) {
        size = new PVector(64,96);
        pos = new PVector(xPos,-size.y);

        old = new StripAnim(sheet.imagesAt(0, 64*3, 64, 64,4));
    }

    //move
    void move() {
        pos.y +=level.speed*1.2;
        if (pos.y > height + size.y)
            oldMan.remove(this);
    }

    //Collide old man with young man
    void collide() {        
        if (pos.y + size.y/2> player.playerPos.y-player.playerSize/2 && pos.y - size.y/2 < player.playerPos.y+player.playerSize/2 &&
        pos.x + size.x/2 > player.playerPos.x-player.playerSize/2 && pos.x - size.x/2 < player.playerPos.x+player.playerSize/2) {
            player.score -= 1;
            oldMan.remove(this);
        }
        for(int i = 0; i < infectedPerson.size();i++){
            if (pos.y + size.y/2> infectedPerson.get(i).pos.y-infectedPerson.get(i).size.y/2 && pos.y - size.y/2 < infectedPerson.get(i).pos.y+infectedPerson.get(i).size.y/2 &&
            pos.x + size.x/2 > infectedPerson.get(i).pos.x-infectedPerson.get(i).size.x/2 && pos.x - size.x/2 < infectedPerson.get(i).pos.x+infectedPerson.get(i).size.x/2) {
                oldMan.remove(this);
            }
        }
    }
    //Display
    void display() {
        imageMode(CENTER);
        image(old.img(), pos.x,pos.y);
        old.next(3);
    }
}


