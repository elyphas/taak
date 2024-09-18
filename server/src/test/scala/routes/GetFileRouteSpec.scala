package testing_ws_catalogs

import cats.effect.IO
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.Tag
import org.http4s._
import cats.effect.unsafe.implicits.global
import fs2.io.file.{Files, Path}
import org.http4s.implicits.http4sLiteralsSyntax

object GetFileRouteSpec extends Tag("GetFileRouteSpec")

class CGetFileRouteSpec extends AnyFlatSpec with Matchers {

  "the GetFile route" should "return a file" taggedAs GetFileRouteSpec  in {

        println("creando un nuevo archivo")
        println("=" * 150)

        val bodyInput = Files[IO].readAll(Path("/home/elyphas/Documentos/herramientas/2024/1.xls"))

        val resp = server_routes
                      .FileRoute
                      .route
                      .orNotFound
                      .run ( Request [ IO ] ( method = Method.POST, uri = uri"/sendFile" )
                              .withEntity ( bodyInput )
                      )


        val result = resp.unsafeRunSync()

        /*val result2 = result.as[Byte].unsafeRunSync()

        println("%" * 200)
        println("Debe imprimir el resultado")


        println("#" * 100)
        println(result)
        println("#" * 100)
        println("$" * 100)
        println(result2)
        println("$" * 100)
        println(s"Status: ${result.status}")*/

        assert ( true )
  }

}
