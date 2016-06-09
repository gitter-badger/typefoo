scalaVersion := "2.11.8"

name := "typefoo"
organization := "typefoo"
version := "0.1"

fork := true
coverageEnabled := true

import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

SbtScalariform.scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value.setPreference(DoubleIndentClassDeclaration, true)

scapegoatVersion := "1.1.0"

wartremoverErrors ++= {
  import Wart._
  Seq(Any, Any2StringAdd, AsInstanceOf, EitherProjectionPartial, Enumeration, ExplicitImplicitTypes, 
    FinalCaseClass, IsInstanceOf, ListOps, Nothing, Null, Option2Iterable, OptionPartial, Product, Return,
    Serializable, TryPartial)
}

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "com.chuusai" %% "shapeless" % "2.3.1" % "test"
)

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-explaintypes",
  "-Ywarn-unused-import",
  "-encoding", "UTF-8",
  "-feature",
  "-Xlog-reflective-calls",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-infer-any",
  "-Ywarn-unused",
  "-Ywarn-value-discard",
  "-Xlint",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Xfuture"
)

initialCommands := """
class Witness[T](val x: T)
object Witness{
  def apply[T](x: T): Witness[T] = new Witness(x)
}
"""

addCompilerPlugin("org.psywerx.hairyfotr" %% "linter" % "0.1.13")

import TodoListPlugin._
compileWithTodolistSettings
testWithTodolistSettings
