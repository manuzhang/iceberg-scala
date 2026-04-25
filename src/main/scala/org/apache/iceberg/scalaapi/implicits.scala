package org.apache.iceberg.scalaapi

import org.apache.iceberg.{Table, TableScan}

object implicits {
  implicit class RichTable(private val table: Table) extends AnyVal {
    def asScala: ScalaTable = ScalaTable(table)
  }

  implicit class RichTableScan(private val scan: TableScan) extends AnyVal {
    def asScala: ScalaTableScan = ScalaTableScan(scan)
  }
}
