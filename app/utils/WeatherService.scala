package utils

import javax.inject._
import play.api.Logger


trait WeatherService {
  def forecast(city: String): Long
}

@Singleton
class WeatherServiceImpl @Inject() extends WeatherService{

  val logger = Logger(this.getClass())

  override def forecast(city: String): Long ={

    val fahrenheit = 55
    logger.debug(s"real temperature: $fahrenheit")
    fahrenheit
  }
}

@Singleton
class WeatherServiceMock @Inject() extends WeatherService{

  val logger = Logger(this.getClass())

  override def forecast(city: String): Long ={

    val fahrenheit = 80
    logger.debug(s"mocked temperature: $fahrenheit")
    fahrenheit
  }
}