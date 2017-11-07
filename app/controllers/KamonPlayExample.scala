/* ===================================================
 * Copyright © 2013-2014 the kamon project <http://kamon.io/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */
package controllers

import filters.{TraceLocalContainer, TraceLocalKey}
import kamon.Kamon
import kamon.play.action.TraceName
import kamon.trace.TraceLocal
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import play.libs.Akka

import scala.concurrent._



object KamonPlayExample extends Controller {

  val logger = Logger(this.getClass)
  val counter = Kamon.metrics.counter("my-counter")

  // tag:trace-name-action:start
  def automaticallyNamedTrace = Action {
    Ok("Automatically generated name.")
  }

  def namedTrace = TraceName("my-trace-name") {
    Action {
      Ok("This trace is named my-trace-name.")
    }
  }
  // tag:trace-name-action:end



  def sayHello = Action.async {
    Future {
      logger.info("Say hello to Kamon")
      Ok("Say hello to Kamon")
    }
  }

  //using the Kamon TraceName Action to rename the trace name in metrics
  def sayHelloWithTraceName = TraceName("my-trace-name") {
    Action.async {
      Future {
        logger.info("Say hello to Kamon with trace name")
        Ok("Say hello to Kamon with trace name")
      }
    }
  }

  def incrementCounter = Action.async {
    Future {
      logger.info("increment")
      counter.increment()
      Ok("increment")
    }
  }

  def updateTraceLocal = Action.async {
    Future {
      TraceLocal.store(TraceLocalKey)(TraceLocalContainer("MyTraceToken","MyImportantHeader"))
      logger.info("storeInTraceLocal")
      Ok("storeInTraceLocal")
    }
  }
}
