package core.shape.Helper

object AngleHelper {




  /*
   * TODO: This doesn't work at all. Remove it.
   */
  @Deprecated
  def changeWithRotation(xangle: Double, side: Int): (Double, Double) ={
    val yangle = 90-xangle
    var (xx, yy) = if(yangle!=0) (1.0 , xangle/yangle) else  (0.0, 1.0)


    if(xx<yy && xx<0){
      yy+=xx
      xx= -yy
      yy=0
    }else if(xx>yy && yy<0){
      xx+=yy
      yy= -xx
      xx=0
    }
    println(s"$xangle -- $xx, $yangle -- $yy")
    val chunk = side/(xx+yy)



    (chunk*Math.abs(xx), chunk*Math.abs(yy) )

  }

}
