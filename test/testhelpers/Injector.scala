package testhelpers

import play.api.inject.guice.GuiceApplicationBuilder

import scala.reflect.ClassTag

/**
  * Provides dependency injection for test classes.
  * http://stackoverflow.com/q/34159857/56285
  */
object Injector {
  lazy val injector = (new GuiceApplicationBuilder).injector()

  def inject[T: ClassTag]: T = injector.instanceOf[T]
}