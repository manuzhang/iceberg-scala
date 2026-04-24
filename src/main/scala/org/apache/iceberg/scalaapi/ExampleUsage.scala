package org.apache.iceberg.scalaapi

import org.apache.iceberg.Schema
import org.apache.iceberg.catalog.Catalog
import org.apache.iceberg.expressions.Expression
import org.apache.iceberg.scalaapi.implicits._
import org.apache.iceberg.types.Types

/**
 * Minimal end-to-end usage example for the Scala Iceberg wrappers.
 *
 * This is intended as reference code; callers should replace catalog/table identifiers
 * and write operations with project-specific logic.
 */
object ExampleUsage {

  def run(catalog: Catalog): Unit = {
    val identifier = IcebergScala.identifier(Seq("prod", "analytics"), "orders")

    val schema = new Schema(
      Types.NestedField.required(1, "order_id", Types.LongType.get()),
      Types.NestedField.required(2, "customer_id", Types.LongType.get()),
      Types.NestedField.optional(3, "order_ts", Types.TimestampType.withZone())
    )

    val table =
      if (catalog.tableExists(identifier)) {
        IcebergScala.loadTable(catalog, Seq("prod", "analytics"), "orders")
      } else {
        IcebergScala.createTable(catalog, Seq("prod", "analytics"), "orders", schema)
      }

    // Read path using Scala-friendly wrappers.
    val filter: Expression = ExpressionsScala.greaterThanOrEqual("order_id", 1000L)
    val files = table.scan.where(filter).select("order_id", "customer_id").planFiles()

    // Write path still allows access to raw Java API when needed.
    val append = table.append
    // append.appendFile(dataFile)
    append.commit()

    table.deleteWhere(ExpressionsScala.lessThan("order_id", 0L))

    println(s"Planned ${files.size} file scan task(s)")

    // Access raw Java objects directly if required.
    val javaTable = table.java
    val javaScan = table.scan.java
    println(s"Java table name: ${javaTable.name()}, scan caseSensitive=${javaScan.isCaseSensitive}")
  }
}
