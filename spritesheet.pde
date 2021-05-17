//Spritesheet class for easy handling of spritesheets
class Spritesheet{
  PImage sheet;
  //Scale variable to rescale sheet
  int scale = 100;
  
  Spritesheet(String filepath){
    //load img from file
    sheet = loadImage(filepath);
  }
  
  //Rescale sheet based on percentege
  void rescale(int percent){
    sheet.resize(sheet.width*percent/100,sheet.height*percent/100);
    scale = percent;
  }
  //Grabs the sprite at the x and y pos with width w and height h
  PImage imageAt(int x, int y, int w, int h){
    return sheet.get(x*scale/100,y*scale/100,w*scale/100,h*scale/100);
  }
  
  //Grabs mulitple sprites at x*i and y pos with width and height h
  PImage[] imagesAt(int x, int y, int w, int h, int nImg){
    
    PImage[] img = new PImage[nImg];
    
    for (int i = 0; i < nImg; i++){
      img[i] = sheet.get(x+i*w*scale/100,y*scale/100,w*scale/100,h*scale/100);
    }
    return img;
  }
}