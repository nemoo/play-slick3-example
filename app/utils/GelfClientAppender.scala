package utils

import javax.inject.Inject

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.graylog2.gelfclient._
import org.graylog2.gelfclient.transport.GelfTransport

@Inject
class GelfClientAppender(transport: GelfTransport, hostname: String) extends AppenderBase[ILoggingEvent]{


  println("GelfClientAppender instantiated")

  override def append(eventObject: ILoggingEvent) = {
    println("GelfClientAppender: append: " + eventObject.getMessage)
    append(convertToGelfMessage(eventObject))
  }

  def append(gelfMessage: GelfMessage): Unit = {
    println("GelfClientAppender: append: " + gelfMessage.getMessage)
    try
      transport.send(gelfMessage)
    catch {
      case e: InterruptedException =>
        e.printStackTrace()
    }
  }

  override def stop() = {
    println("GelfClientAppender stopped")
    transport.stop()
    started = false
  }

  private def convertToGelfMessage(event: ILoggingEvent) = {
    new GelfMessageBuilder(event.getFormattedMessage, hostname)
      .timestamp(event.getTimeStamp / 1000d)
      .level(toGelfMessageLevel(event.getLevel))
      .additionalField("threadname", event.getThreadName)
      .additionalField("logger", event.getLoggerName)
      .additionalField("environment", "local")
      .additionalField("instance", "playslick3example")
      .additionalField("node", "fakenode")
      .build
  }

  private def toGelfMessageLevel(level: Level) = level.toInt match {
    case Level.ERROR_INT =>
      GelfMessageLevel.ERROR
    case Level.WARN_INT =>
      GelfMessageLevel.WARNING
    case Level.DEBUG_INT =>
      GelfMessageLevel.DEBUG
    case _ =>
      GelfMessageLevel.INFO
  }

}
