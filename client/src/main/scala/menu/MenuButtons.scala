package manik.menubuttons

import outwatch._

object MenuButtons {

  /*
  def menuButtons = div( cls := "groupControls", marginTop := "20px",                                                                                                          
    button( id := "cmdSave", "Guardar", cls := "myButton",                                                                                                          
       onClick                                                                                                          
           .transformLifted { e: monix.reactive.Observable[MouseEvent] =>                                                                                                          
           e                                                                                                          
           .withLatestFrom(getMainItem)                                                                                                          
           { case (event@_, (item, isNew)) =>                                                                                                          
               println("Debe pasar por aqui por el evento Guardar")                                                                                                          
               (item, (if (isNew) InsertDoobie else UpdateDoobie))                                                                                                          
           }                                                                                                          
       } --> repo.eventDocument,                                                                                                          
    ),                                                                                                          
                                                                                                          
    button( id := "cmdSearch", "Buscar", cls := "myButton",                                                                                                          
       onClick.transformLifted { e: Observable[MouseEvent] =>                                                                                                          
           e.withLatestFrom(getMainItem){ case (event@_, (item, isNew)) =>                                                                                                          
               (item, FindDoobie)                                                                                                          
           }                                                                                                          
       } --> repo.eventDocument,    //eventOnItem                                                                                                          
    ),                                                                                                          
    button( id := "cmdDelete", "Borrar", cls := "myButton",                                                                                                          
       onClick.transformLifted { e: Observable[MouseEvent] =>                                                                                                          
           e.withLatestFrom(getMainItem){ case (event@_, (item, isNew)) =>                                                                                                          
                 (item, DeleteDoobie)                                                                                                          
           }                                                                                                          
       } --> repo.eventDocument,    //eventOnItem                                                                                                          
    ),                                                                                                          
    buton( id := "cmdNew", "Nuevo", cls := "myButton",                                                                                                          
            onClick.use("") --> onNew )                                                                                                          
  )*/ 
}
