package acme.vparsetree

import org.junit.Assert._
import org.junit.Test
import org.vertx.java.core.json.JsonObject

/**
 * Date: 4/5/13
 * Time: 9:16 PM
 * @author Edgar Chan
 */
class JSONResultFormatterTest {

  object parser extends CalcParser with FormatParser{
     override def initRule = InputLine
  }

  @Test
  def testInput(){

    val exp = "3+7*2"

    val rst = parser.parse(exp)

    val jsObject =
        new JsonObject(
          """
            |{"name":"InputLine *** 3+7*2","children":[{"name":"Expression *** 3+7*2","children":[{"name":"Term *** 3",
            |"children":[{"name":"Factor *** 3","children":[{"name":"Digits *** 3"}]},{"name":"ZeroOrMore"}]},{"name":"ZeroOrMore *** +7*2",
            |"children":[{"name":"Sequence *** +7*2","children":[{"name":"+"},{"name":"Term *** 7*2","children":[{"name":"Factor *** 7",
            |"children":[{"name":"Digits *** 7"}]},{"name":"ZeroOrMore *** *2","children":[{"name":"Sequence *** *2",
            |"children":[{"name":"*"},{"name":"Factor *** 2","children":[{"name":"Digits *** 2"}]}]}]}]}]}]}]},{"name":"EOI"}]}
          """.stripMargin
        )

    assertEquals(jsObject.toString , rst.toString)

  }

}
