package acme.vparsetree

import org.vertx.java.platform.Verticle
import org.vertx.java.core.json.JsonObject

/**
 * Main verticle to bootstrap the web-server mod and AppService verticle
 *
 * Date: 4/5/13
 * @author Edgar Chan
 */
class AppVerticle extends Verticle{

  override def start() {

    val webappconf =
      """
        |{
        |  "webappconf" : {
        |          "port": 8080,
        |          "ssl": false,
        |          "bridge": true,
        |          "inbound_permitted": [
        |            {
        |              "address" : "acme.service",
        |              "match" : {
        |                "action" : "incoming"
        |              }
        |            }
        |          ],
        |
        |          "outbound_permitted": [
        |            {}
        |          ]
        |        }
        |}
      """.stripMargin

    val config   = new JsonObject(webappconf)
	  val webconf  = config.getObject("webappconf")

	  container.deployModule("io.vertx~mod-web-server~2.0.0-SNAPSHOT", webconf)
	  container.deployVerticle("acme.vparsetree.AppService")

  }

}
