package modules

import com.google.inject.{AbstractModule, Provides}
import com.mohiva.play.silhouette.api.services.AuthenticatorService
import com.mohiva.play.silhouette.api.{Environment, EventBus, Silhouette, SilhouetteProvider}
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import net.codingwell.scalaguice.ScalaModule
import utils.{AuthEnv, UserService}

import scala.concurrent.ExecutionContext.Implicits.global

class SilhouetteModule extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[Silhouette[AuthEnv]].to[SilhouetteProvider[AuthEnv]]
  }

  @Provides
  def provideEnvironment(
                          authenticatorService: AuthenticatorService[CookieAuthenticator],
                          eventBus: EventBus,
                          userService: UserService
                        ): Environment[AuthEnv] = {
    Environment[AuthEnv](userService, authenticatorService, Seq.empty, eventBus)
  }
}
