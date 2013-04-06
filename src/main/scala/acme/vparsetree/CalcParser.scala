package acme.vparsetree

import org.parboiled.scala._

/**
 * Date: 4/5/13
 * Time: 6:40 PM
 * @author Edgar Chan
 */
trait CalcParser extends Parser {

  def InputLine = rule { Expression ~ EOI }

  def Expression: Rule0 = rule { Term ~ zeroOrMore(anyOf("+-") ~ Term) }

  def Term = rule { Factor ~ zeroOrMore(anyOf("*/") ~ Factor) }

  def Factor = rule { Digits | Parens }

  def Parens = rule { "(" ~ Expression ~ ")" }

  def Digits = rule { oneOrMore(Digit) }

  def Digit = rule { "0" - "9" }

}