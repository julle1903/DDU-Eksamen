class Obstacle {
    Obstacle() {
    }
    
    void Display() {
    }

    void Move() {
    }

    void Collide() {

    }

}



class Person extends Obstacle {
    PVector pos;
    PVector size;
    boolean dead = false;

    Person(float xPos) {
        //2 typer en "infected" og en "Risikogruppe person"
        size = new PVector(20,20);
        pos = new PVector(xPos,0-size.y);
    }

    void Display() {
        rectMode(CENTER);
            fill(0,0,200);
            rect(pos.x,pos.y,size.x,size.y);
    }
    
    void Move() {
        pos.y +=level.speed;
    }

    void Collide() {
        if (pos.y > player.playerPos.y-player.playerSize/2 && pos.y < player.playerPos.y+player.playerSize/2 &&
        pos.x > player.playerPos.x-player.playerSize/2 && pos.x < player.playerPos.x+player.playerSize/2 ) {
            persons.remove(this);
            player.playerHealth -= 1;
            print(player.playerHealth);
        }
    }
}


class InfectedDoor extends Obstacle {
    PVector pos;
    PVector size;
    boolean dead = false;

    InfectedDoor(float xPos) {
        size = new PVector(20,20);
        pos = new PVector(xPos,-size.y);
    }

    void Display() {
        rectMode(CENTER);
        fill(200,0,0);
        rect(pos.x,pos.y,size.x,size.y);
    }

    void Move() {
        pos.y +=level.speed;
    }

    void Collide() {        
        if (pos.y > player.playerPos.y-player.playerSize/2 && pos.y < player.playerPos.y+player.playerSize/2 &&
        pos.x > player.playerPos.x-player.playerSize/2 && pos.x < player.playerPos.x+player.playerSize/2 ) {
            player.playerHealth -= 1;
            doors.remove(this);
            print(player.playerHealth);
        }
    }
}


