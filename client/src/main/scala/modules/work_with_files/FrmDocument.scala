package modules.work_with_files

import colibri.Observer
import org.scalajs.dom.{Blob, URL, document, html}
import outwatch._
import outwatch.dsl.{span => sp, _}

import repositories.Repository.repoSendFile

class FrmDocument  {

  protected val titleForm: String = "Entidades"

  private val txtFile = {
    form (
      div (
        label(forId := "myfile","Selecciona un archivo:", cls := "labelinputfile" ),
        input ( tpe := "file", idAttr := "myfile", name := "myfile",
          cls := "labelinputfile",
          onInput.map { _.target.asInstanceOf[html.Input].files(0) } --> repoSendFile.event,
        )
      )
    )

  }

  private val downloadFile: Observer[List[Blob]] = Observer.create[List[Blob]] { ls =>
    val link = document.createElement("a").asInstanceOf[html.Anchor]
    val url = URL.createObjectURL( ls.head )
    link.href = url
    link.target = "_blank"
    link.setAttribute("download","nameoffile.xlsx")
    document.body.appendChild(link)
    link.click()
    URL.revokeObjectURL(url)
    document.body.removeChild(link)
  }

  def render: VNode = div(idAttr := "pageForm",
          div(cls := "inner-shadow", sp (titleForm)),
          VMod.managedElement{ elem =>
            repoSendFile.onEvent.unsafeSubscribe(downloadFile)
          },

          div(
            txtFile
          )

        )
}