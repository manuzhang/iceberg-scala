package org.apache.iceberg.scalaapi

import org.apache.iceberg.{Schema, Table}
import org.apache.iceberg.catalog.{Catalog, Namespace, TableIdentifier}

object IcebergScala {

  def namespace(levels: String*): Namespace = Namespace.of(levels: _*)

  def identifier(namespace: Seq[String], name: String): TableIdentifier =
    TableIdentifier.of(Namespace.of(namespace: _*), name)

  def loadTable(catalog: Catalog, namespace: Seq[String], name: String): ScalaTable =
    ScalaTable(catalog.loadTable(identifier(namespace, name)))

  def createTable(catalog: Catalog, namespace: Seq[String], name: String, schema: Schema): ScalaTable =
    ScalaTable(catalog.createTable(identifier(namespace, name), schema))

  def from(table: Table): ScalaTable = ScalaTable(table)
}
