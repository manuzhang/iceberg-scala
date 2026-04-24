package org.apache.iceberg.scalaapi

import scala.jdk.CollectionConverters._

import org.apache.iceberg.{DataFile, PartitionSpec, Schema, Snapshot, SortOrder, Table, Transaction}
import org.apache.iceberg.expressions.Expression

final case class ScalaTable(private val table: Table) {

  def java: Table = table

  def name: String = table.name()

  def schema: Schema = table.schema()

  def spec: PartitionSpec = table.spec()

  def sortOrder: SortOrder = table.sortOrder()

  def currentSnapshot: Option[Snapshot] = Option(table.currentSnapshot())

  def snapshots: Seq[Snapshot] = table.snapshots().asScala.toSeq

  def scan: ScalaTableScan = ScalaTableScan(table.newScan())

  def scan(filter: Expression): ScalaTableScan = ScalaTableScan(table.newScan().filter(filter))

  def append(dataFiles: Iterable[DataFile]): Unit = {
    val append = table.newAppend()
    dataFiles.foreach(append.appendFile)
    append.commit()
  }

  def deleteWhere(filter: Expression): Unit = {
    table.newDelete().deleteFromRowFilter(filter).commit()
  }

  def transaction: Transaction = table.newTransaction()
}
