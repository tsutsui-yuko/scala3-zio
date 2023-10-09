import zio._

// オニオンアーキテクチャにおけるドメインモデル層
// ZIOのドメイン層（Service）はtraitで、アプリケーションが必要とする機能やビジネスロジックを定義する
trait ApplicationService {
  def consoleOutput(): ZIO[Any, Throwable, Unit]
}

// サービスの機能を呼び出しやすいようにアクセサメソッドをコンパニオンオブジェクトで作る
// 当然、アクセサメソッドはApplicationServiceに依存する
object ApplicationService {
  def consoleOutput(): ZIO[ApplicationService, Throwable, Unit] =
//    ZIO.serviceWithはZIOの依存性注入パターンの中で、サービスのメソッドにアクセスするための関数を作成するツール
//    これにより、サービスのメソッド（ここではconsoleOutput）をZIOエフェクトとして扱いやすくなり、依存性の管理がしやすくなる
    ZIO.serviceWithZIO[ApplicationService](_.consoleOutput())
}