# iceberg-scala

Apache Iceberg Scala API.

## Goal

This project provides a lightweight Scala-first wrapper over the Iceberg Java API.
The wrapper keeps access to the underlying Java types, while exposing a more idiomatic Scala API:

- `Option` instead of nullable values.
- `Seq` instead of Java collections.
- Immutable wrapper types for fluent scan building.

## Modules

- `IcebergScala`: entrypoint helpers for namespaces, identifiers, loading/creating tables.
- `ScalaTable`: Scala wrapper around `org.apache.iceberg.Table`.
- `ScalaTableScan`: Scala wrapper around `org.apache.iceberg.TableScan`.
- `ExpressionsScala`: Scala helpers for building Iceberg expressions.
- `implicits`: extension methods to convert Java Iceberg types to Scala wrappers.

## Example

```scala
import org.apache.iceberg.scalaapi.{ExpressionsScala, IcebergScala}
import org.apache.iceberg.scalaapi.implicits._

val orders = IcebergScala.loadTable(catalog, Seq("prod", "analytics"), "orders")

val scan = orders
  .scan
  .where(ExpressionsScala.greaterThanOrEqual("order_ts", 1711929600000L))
  .select("order_id", "customer_id", "order_ts")

val plannedFiles = scan.planFiles()
```

## Additional example

A runnable reference object is available at `src/main/scala/org/apache/iceberg/scalaapi/ExampleUsage.scala`.
It demonstrates table creation-or-load, scan planning, and write/delete operations using only the Scala wrapper API.
