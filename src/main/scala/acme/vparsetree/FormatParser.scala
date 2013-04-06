package acme.vparsetree

import org.parboiled.scala.Parser
import org.parboiled.scala.rules.Rule0
import org.parboiled.scala.ParsingResult
import org.parboiled.scala.parserunners.ReportingParseRunner
import org.vertx.java.core.json.JsonObject

/**
 * Mix in this trait with a parser to get a json representation
 * of a parsing result
 *
 * Date: 4/5/13
 * @author Edgar Chan
 */
trait FormatParser extends JSONResultFormatter{
  this : Parser =>

  override def buildParseTree = true

  def initRule:Rule0

  def parseInput( input:String ):ParsingResult[String]={
    ReportingParseRunner(initRule).run(input)
  }

  def parse(exp:String):JsonObject={
     formatResult( parseInput(exp) )
          .map(
            r => new JsonObject(r)
          ).getOrElse(
                 new JsonObject("{}")
          )
  }

}