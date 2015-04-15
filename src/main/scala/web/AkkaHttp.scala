package web

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.Http
import akka.stream.ActorFlowMaterializer
import com.typesafe.config.ConfigFactory
import slick.driver.H2Driver.api._

object AkkaHttp extends App with Service {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorFlowMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

  val db = Database.forConfig("h2mem1")

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))
}