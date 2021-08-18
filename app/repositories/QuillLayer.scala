package repositories

import java.io.Closeable

import com.zaxxer.hikari.HikariDataSource
import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.util.LoadConfig
import io.getquill.{JdbcContextConfig, Literal, MysqlZioJdbcContext}
import javax.sql.DataSource
import zio.{Has, ZLayer}

/**
  * Created by luping.qiu in 4:27 PM 2021/8/16
  */
object QuillLayer extends MysqlZioJdbcContext(Literal){

  lazy val config = JdbcContextConfig(LoadConfig("ctx")).dataSource

  lazy val hikariDataSource: HikariDataSource = new HikariDataSource(config)
  lazy val  zioDs: ZLayer[Any, Throwable, Has[DataSource with Closeable]] = DataSourceLayer.fromDataSource(hikariDataSource)



  lazy val zioDs: ZLayer[Any, Throwable, Has[DataSource with Closeable]] = DataSourceLayer.fromPrefix("ctx")


//}
