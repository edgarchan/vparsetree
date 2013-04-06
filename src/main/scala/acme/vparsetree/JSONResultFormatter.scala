package acme.vparsetree

import scala.collection.JavaConverters.asScalaBufferConverter
import org.parboiled.Node
import org.parboiled.buffers.InputBuffer
import org.parboiled.common.StringUtils
import org.parboiled.scala.ParsingResult
import org.parboiled.support.ParseTreeUtils

/**
 * Utilities to create a json string of a parsed grammar
 *
 * Date: 4/5/13
 * @author Edgar Chan
 */
trait JSONResultFormatter {

  def formatResult(result:ParsingResult[String]):Option[String]={
    Option(result.parseTreeRoot).map(
        pt =>  toJSONString(pt, result.inputBuffer, None)
    )
  }


  private def clean(str:String):String={
   StringUtils.escape(str.replaceAll("[\"|\']", ""))
  }


  private def toJSONString(nodo:Node[String], buff:InputBuffer, last:Option[Node[String]]):String={

     def toTxt(n:Node[String]) = clean(ParseTreeUtils.getNodeText(n, buff))

     val coma = last.filterNot(l=>l.equals(nodo)).map(i=>",").getOrElse("")

     val entry = nodo.getChildren.asScala match{
        case Seq() =>   {

	       (Option(toTxt(nodo)).filterNot( _.size == 0 ).map(f =>
	           f
	       ).getOrElse{
	         nodo.getLabel
	       }) + "\""

        }
        case s1 @ Seq(xs@_*) => {

	        clean(nodo.getLabel + " *** " + toTxt(nodo)) +  "\"" +
	           ({
	              if ( s1.forall( _.getChildren.isEmpty ) ){
	                ""
	              }else{
	                ",\"children\" : ["  +
	                  s1.map(n => (toJSONString(n, buff,  s1.lastOption)) ).mkString +
	                "]"
	              }
	           })
        }
    }

     s"""{ "name" : "$entry } $coma"""

   }

}