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
        pos = new PVector(xPos,-size.y);
    }

    void Display() {
        rectMode(CENTER);
            fill(0,0,200);
            rect(pos.x,pos.y,size.x,size.y);
    }
    
    void Move() {
        pos.y +=1;
    }

    // void Collide() {
    //     if (pos.y > player.playerPos.y) {
    //         println("dead2");
    //     }
    // }
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
        pos.y +=1;
    }

    // void Collide() {
    //     if (pos.y > player.playerPos.y) {
    //         println("dead1");
    //     }
    // }
}


