package com.kchen.reactive.demo.gateway.test

import java.util.concurrent.TimeUnit

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

/**
  * @author chenkunyun
  * @date 2019-05-26
  */
class GatewaySimulationTest extends Simulation {
  val httpProtocol = http // 4
    .baseUrl("http://localhost:5555") // 5
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")

  val scn = scenario("GatewayTest").forever() {
    exec(
      http("gateway")
        .get("/reactive-demo-auth/auth")
    )
  }

  setUp(scn.inject(atOnceUsers(200))).maxDuration(FiniteDuration.apply(1, "minutes"))
    .protocols(httpProtocol)
}
