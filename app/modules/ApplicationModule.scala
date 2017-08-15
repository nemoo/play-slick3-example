package modules

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import play.api.{Configuration, Environment, Mode}
import utils._


class ApplicationModule( environment: Environment,
                         configuration: Configuration) extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    if (environment.mode == Mode.Prod)
      bind[WeatherService].to[WeatherServiceImpl]
    else
      bind[WeatherService].to[WeatherServiceMock]
  }
}
