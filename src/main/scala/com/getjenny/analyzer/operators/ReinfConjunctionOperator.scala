package com.getjenny.analyzer.operators

import com.getjenny.analyzer.expressions._

/**
  * Created by angelo on 18/01/2018.
  */

class ReinfConjunctionOperator(children: List[Expression]) extends AbstractOperator(children: List[Expression]) {
  override def toString: String = "conjunction(" + children.mkString(", ") + ")"
  def add(e: Expression, level: Int = 0): AbstractOperator = {
    if (level == 0) {
      new ReinfConjunctionOperator(e :: children)
    } else if(children.isEmpty){
      throw OperatorException("ReinfConjunction children list is empty")
    } else {
      children.headOption match {
        case c: Option[AbstractOperator] => new ReinfConjunctionOperator(c.get.add(e, level - 1) :: children.tail)
        case _ => throw OperatorException("ReinfConjunction: trying to add to smt else than an operator")
      }
    }
  }

  def evaluate(query: String, data: AnalyzersData = new AnalyzersData): Result = {
    def reinfConjunction(l: List[Expression]): Result = {
      val res = l.head.evaluate(query, data)
      if (l.tail == Nil) {
        println("SCORE_NIL: " + res.score * 1.1 + "(" + res.score + ")")
        Result(score = res.score * 1.1,
          AnalyzersData(
            item_list = data.item_list,
            extracted_variables = res.data.extracted_variables,
            data = res.data.data
          )
        )
      } else {
        val val1 = l.head.evaluate(query)
        val val2 = reinfConjunction(l.tail)
        println("SCORE_NOT_NIL: " + (val1.score * 1.1) * val2.score + "(" + val1.score + ")" + "(" + val2.score + ")")
        Result(score = (val1.score * 1.1) * val2.score,
          AnalyzersData(
            item_list = data.item_list,
            extracted_variables = res.data.extracted_variables,
            data = res.data.data
          )
        )
      }
    }
    reinfConjunction(children)
  }
}
