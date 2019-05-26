package com.kchen.reactive.demo.auth.test

import java.util.concurrent.TimeUnit

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

/**
  * @author chenkunyun
  * @date 2019-05-26
  */
class AuthSimulationTest extends Simulation {
  val httpProtocol = http // 4
    .baseUrl("http://localhost:8769") // 5
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")

  val scn = scenario("AuthTest").forever() {
    exec(
      http("auth")
        .get("/auth")
    ).pause(Duration.apply(5, TimeUnit.MILLISECONDS))
  }

  setUp(scn.inject(atOnceUsers(200))).maxDuration(FiniteDuration.apply(1, "minutes"))
    .protocols(httpProtocol)
}
