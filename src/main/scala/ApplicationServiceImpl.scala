import zio.*

import java.text.SimpleDateFormat
import java.util.Date

// オニオンアーキテクチャにおけるアプリケーション層
// サービスの実装
//　ApplicationServiceを継承（依存）しており、さらにDataに依存している

case class ApplicationServiceImpl(currentDate: Date) extends ApplicationService {
  override def consoleOutput(): ZIO[Any, Throwable, Unit] =
    Console.printLine(
      s"${new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(currentDate)} Hello, World!"
    )
}

/** failureを返却するパターン
case class ApplicationServiceImpl(currentDate: Date) extends ApplicationService {
  override def consoleOutput(): ZIO[Any, Throwable, Unit] =
    for {
      _ <- ZIO.fail(new Exception("This is a failure test"))
      _ <- Console.printLine(
        s"${new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(currentDate)} Hello, World!")
    } yield ()
}
 */

/** defectを返却するパターン
case class ApplicationServiceImpl(currentDate: Date) extends ApplicationService {
  override def consoleOutput(): ZIO[Any, Throwable, Unit] =
    for {
      _ <- ZIO.die(new ArithmeticException("divide by zero"))
      _ <- Console.printLine(
        s"${new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(currentDate)} Hello, World!")
    } yield ()
} */



// サービスの依存性を注入
object ApplicationServiceImpl {
  val layer: ZLayer[Date, Nothing, ApplicationService] =
// ZLayerはサービスの生成、依存性の注入や依存性の組み合わせを行える
// この実装では、ApplicationServiceとDateに依存性関係を組み合わせてる（多分）
// ZLayerは、通常、外部リソース（例：データベース、API接続、設定）やサービスの実装を提供するために使用される
    ZLayer {
      for {
        currentDate: Date <- ZIO.service[Date]
      } yield ApplicationServiceImpl(currentDate)
    }
}