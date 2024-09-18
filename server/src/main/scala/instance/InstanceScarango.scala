package instance

import shared.mytypes.Institution

trait InstanceScarango[A] {


}


object InstanceScarango {
  def apply[A](implicit instance: InstanceScarango[A]): InstanceScarango[A] = instance

  implicit val instanceScarangoInstitution: InstanceScarango[Institution] = new InstanceScarango[Institution]{

  }
}