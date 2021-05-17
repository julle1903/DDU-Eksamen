//Stripanimation class for animating sprites
class StripAnim{
  PImage[] strip;
  int frames;
  int curFrame;
  float frameTime;

  StripAnim(PImage[] imgList){
    strip = imgList;
    frames = imgList.length;
  }
  
  //Goes to the next frame (rate) times a second
  void next(float rate){
    frameTime-=rate/frameRate;

    if (frameTime<0){
      curFrame++;
      if (curFrame >= frames){
        curFrame = 0;
      }
      frameTime=1;
    }
  }
  
  //Returns current image for displaying
  PImage img(){
    return strip[curFrame];
  }
}



