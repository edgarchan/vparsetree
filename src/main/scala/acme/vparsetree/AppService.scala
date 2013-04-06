package acme.vparsetree

import org.vertx.java.core.Handler
import org.vertx.java.core.eventbus.Message
import org.vertx.java.core.json.JsonObject
import org.vertx.java.platform.Verticle


/**
 * Date: 4/5/13
 * Time: 6:48 PM
 * @author Edgar Chan
 */
class AppService extends Verticle with Handler[Message[JsonObject]]{

  lazy val parser = new CalcParser with FormatParser{
                        override def initRule = InputLine
                    }

  override def start(){
    vertx.eventBus().registerHandler("acme.service", this)
  }

  override def handle(msg:Message[JsonObject]){
        val docto = msg.body.getObject("document")
        val exp   = docto.getField("tag").toString
        val ret   = parser.parse(exp)
        msg.reply(ret)
  }

}