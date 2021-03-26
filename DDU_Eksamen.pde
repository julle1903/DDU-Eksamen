ArrayList<Person> persons = new ArrayList<Person>();
ArrayList<InfectedDoor> doors = new ArrayList<InfectedDoor>();
PlayerController player;
LevelController level;



void setup() {
    size(800,800);
    rectMode(CENTER);
    level = new LevelController();
    player = new PlayerController();
}

void draw() {
    background(0);

    level.Generate();
    level.drawBG();

    for (Person p : persons) {
        p.Display();
        p.Move();
        p.Collide();
    }

    for (InfectedDoor d : doors) {
        d.Display();
        d.Move();
        d.Collide();
    }

    player.Display();
}


void keyPressed(){
    player.Move();
}