package shared
package mytypes

case class User (
      user: String
)

object User {

    def apply (
              user: String = ""
        ): User = new User (
                          user = user
                    )

}