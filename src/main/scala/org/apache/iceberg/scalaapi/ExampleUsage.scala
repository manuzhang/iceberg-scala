package org.apache.iceberg.scalaapi

import org.apache.iceberg.{DataFile, MetadataTableType, Schema}
import org.apache.iceberg.catalog.Catalog
import org.apache.iceberg.types.Types

/**
 * Minimal end-to-end usage example for the Scala Iceberg wrappers.
 *
 * This is intended as reference code; callers should replace catalog/table identifiers
 * and write operations with project-specific logic.
 */
object ExampleUsage {

  def run(catalog: Catalog, newDataFiles: Seq[DataFile]): Unit = {
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
    val filter = ExpressionsScala.greaterThanOrEqual("order_id", 1000L)
    val files = table.scan.where(filter).select("order_id", "customer_id").planFiles()

    // Write path using Scala wrapper methods only.
    table.append(newDataFiles)
    table.deleteWhere(ExpressionsScala.lessThan("order_id", 0L))

    // Check record and snapshot metadata using Scala wrapper methods.
    val totalRecords = table.currentSnapshot
      .flatMap(snapshot => Option(snapshot.summary().get("total-records")))
      .getOrElse("unknown")
    val snapshotsMetadataTable = table.metadataTable(MetadataTableType.SNAPSHOTS)
    val snapshotTasks = snapshotsMetadataTable.scan.planFiles()

    println(s"Planned ${files.size} file scan task(s)")
    println(s"Current snapshot total records: $totalRecords")
    println(s"Planned ${snapshotTasks.size} snapshot metadata file task(s)")
  }
}
