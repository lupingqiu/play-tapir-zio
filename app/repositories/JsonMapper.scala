package repositories

import java.sql.Timestamp
import java.time.LocalDateTime

import play.api.db.slick.HasDatabaseConfig
import slick.jdbc.JdbcProfile

/**
  * Created by luping.qiu in 11:22 PM 2021/8/6
  */
trait JsonMapper extends HasDatabaseConfig[JdbcProfile] {

  import profile.api._

  implicit def timeMapper = MappedColumnType.base[LocalDateTime, Timestamp](
    dt => Timestamp.valueOf(dt),
    ts => ts.toLocalDateTime
  )

}