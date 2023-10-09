import zio.*
//@main def hello: Unit =
//  println("Hello world!")
//  println(msg)
//
//def msg = "I was compiled by Scala 3. :)"
//

object MainApp extends ZIOAppDefault {
//  オニオンアーキテククチャにおけるアプリケーション層
// ZIO型は純関数型について、返り値・エラー・それ以外の依存関係や副作用をまとめて表現するもの
// `ZIO[R, E, A]`という形式で表現される`ZIO`型のそれぞれの型パラメータについて
//1. R: 環境の型（Environment） - `ZIO`が実行される際に必要とされる依存関係やコンテキスト
//2. E: エラーの型（Error） - `ZIO`が失敗した際に返されるエラータイプ
//3. A: 成功時の結果の型（Success） - `ZIO`が成功した際に返される結果のタイプ
//　ZIO[Any, Throwable, Unit] は失敗時にThrowableを返却し、通常Unitを返す関数であり、Anyに依存することを表している（Anyに依存=何にも依存しない）
  def run: ZIO[Any, Throwable, Unit] = ApplicationService.consoleOutput()
  // provideは型から依存性をいい感じに解決してくれる
    .provide(ZLayer.fromZIO(
//      ZIO.attemptは与えられたコードが失敗した時はZIOの失敗型Eに変換して返してくれる
      ZIO.attempt {
      import java.text.SimpleDateFormat
      //    任意の日付文字列
      val inpDateStr = "2023/09/01 12:00"
      val sdformat = new SimpleDateFormat("yyyy/MM/dd HH:mm")

      // Data型に変換してreturn
      sdformat.parse(inpDateStr)
    }), ApplicationServiceImpl.layer
  ).catchAll(failure => Console.printError("Failure:" + failure.toString))
  // ↑attemptがEを返した時の実装(catchAllはfailureエラーを処理する関数)
  // ZIOのエラーは３種類
  // failure: ZIO型でEとして定義されたエラー
  // defects: Eで定義されてない（予測されないプログラム内で発生するエラー）
  // fatal: アプリケーション外のJVM関係のエラー
  // defectエラーはcatchAllDefectで上記と同じようにハンドリング可能
}

// sbt runすると↓が出力される
//[info] running MainApp
//  2023/09/01 12:00:00 Hello, World!