package testhelpers

import play.api.db.DBApi
import play.api.db.evolutions.Evolutions

object EvolutionHelper {

  def clean() = {
    val dbapi = Injector.inject[DBApi]
    Evolutions.cleanupEvolutions(dbapi.database("default"))
  }

}
