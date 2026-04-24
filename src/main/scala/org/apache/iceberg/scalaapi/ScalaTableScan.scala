package org.apache.iceberg.scalaapi

import scala.jdk.CollectionConverters._

import org.apache.iceberg.{FileScanTask, TableScan}
import org.apache.iceberg.expressions.Expression

final case class ScalaTableScan(private val scan: TableScan) {

  def java: TableScan = scan

  def useSnapshot(snapshotId: Long): ScalaTableScan = ScalaTableScan(scan.useSnapshot(snapshotId))

  def asOf(timestampMillis: Long): ScalaTableScan = ScalaTableScan(scan.asOfTime(timestampMillis))

  def where(filter: Expression): ScalaTableScan = ScalaTableScan(scan.filter(filter))

  def select(columns: String*): ScalaTableScan = ScalaTableScan(scan.select(columns: _*))

  def includeColumnStats(): ScalaTableScan = ScalaTableScan(scan.includeColumnStats())

  def ignoreResiduals(): ScalaTableScan = ScalaTableScan(scan.ignoreResiduals())

  def planFiles(): Seq[FileScanTask] = scan.planFiles().asScala.toSeq
}
