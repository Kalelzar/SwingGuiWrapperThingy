package core.component

trait MenuElement extends BasicComponent{

  def onClick(mouseButton: Int, f: (Int) => (Unit) ): Unit ={
    f(mouseButton)
  }

  def whenBoxed(index: Int, menu: Menu)

}
