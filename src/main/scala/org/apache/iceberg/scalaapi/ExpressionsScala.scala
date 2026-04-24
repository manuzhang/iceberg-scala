package org.apache.iceberg.scalaapi

import org.apache.iceberg.expressions.{Expression, Expressions}

object ExpressionsScala {
  def alwaysTrue: Expression = Expressions.alwaysTrue()
  def alwaysFalse: Expression = Expressions.alwaysFalse()

  def equal(column: String, value: Any): Expression = Expressions.equal(column, value)
  def notEqual(column: String, value: Any): Expression = Expressions.notEqual(column, value)
  def lessThan(column: String, value: Any): Expression = Expressions.lessThan(column, value)
  def lessThanOrEqual(column: String, value: Any): Expression = Expressions.lessThanOrEqual(column, value)
  def greaterThan(column: String, value: Any): Expression = Expressions.greaterThan(column, value)
  def greaterThanOrEqual(column: String, value: Any): Expression = Expressions.greaterThanOrEqual(column, value)
  def isNull(column: String): Expression = Expressions.isNull(column)
  def notNull(column: String): Expression = Expressions.notNull(column)
  def in(column: String, values: Any*): Expression = Expressions.in(column, values: _*)
  def notIn(column: String, values: Any*): Expression = Expressions.notIn(column, values: _*)

  def and(left: Expression, right: Expression): Expression = Expressions.and(left, right)
  def or(left: Expression, right: Expression): Expression = Expressions.or(left, right)
  def not(child: Expression): Expression = Expressions.not(child)
}
