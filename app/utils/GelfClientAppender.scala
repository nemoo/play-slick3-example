package utils

import javax.inject.Inject

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.graylog2.gelfclient._
import org.graylog2.gelfclient.transport.GelfTransport

@Inject
class GelfClientAppender extends AppenderBase[ILoggingEvent]{

  val hostname = "example.com"
  val port = 12
  val gelfConfiguration = new GelfConfiguration(hostname, port)
    .transport(GelfTransports.UDP)
    .reconnectDelay(500)
    .queueSize(512)
    .connectTimeout(1000)
    .tcpNoDelay(false)
    .sendBufferSize(0) // causes the socket default to be used

  private val transport: GelfTransport = GelfTransports.create(gelfConfiguration)



//  val lc: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
//  val rootLogger: Logger = lc.getLogger(Logger.ROOT_LOGGER_NAME)
//
//  gelfClientAppender = new GelfClientAppender(transport, canonicalHostName)
//
//  gelfClientAppender.setContext(lc)
//
//  gelfClientAppender.start
//
//  rootLogger.addAppender(gelfClientAppender)




  override def append(eventObject: ILoggingEvent) = append(convertToGelfMessage(eventObject))

  def append(gelfMessage: GelfMessage): Unit = {
    try
      transport.send(gelfMessage)
    catch {
      case e: InterruptedException =>
        e.printStackTrace()
    }
  }

  private def convertToGelfMessage(event: ILoggingEvent) = {
    new GelfMessageBuilder(event.getFormattedMessage, hostname)
      .timestamp(event.getTimeStamp / 1000d)
      .level(toGelfMessageLevel(event.getLevel))
      .additionalField("threadname", event.getThreadName)
      .additionalField("logger", event.getLoggerName)
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
