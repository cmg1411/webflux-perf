package com.example.mvc

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.ResultSet
import java.util.*

@RestController
class StudentController(
    val dao: StudentDAO,
) {

    @GetMapping("/v1/students")
    fun getStudents(): Iterable<Student> {
        val int = Random().nextInt(1,100000)
        return dao.findAllByName(int)
    }
}

@Entity
@Table(name="student")
class Student(
    @Id
    val id: Long,
    val name: String
)

@Service
class StudentDAO(
    val jdbcTemplate: JdbcTemplate
) {

    fun findAll(): List<Student> {
        return jdbcTemplate.query("SELECT * FROM student;", StudentRowMapper())
    }

    fun sleep(): List<Student> {
        return jdbcTemplate.query("SELECT sleep(1) as id, '1' as name;", StudentRowMapper())
    }

    fun getAllStudents(): List<Student> {
        return jdbcTemplate.query("SELECT * FROM student ORDER BY RAND() LIMIT 1;", StudentRowMapper())
    }

    fun select1(): List<Student> {
        return jdbcTemplate.query("SELECT 1 as id, '1' as name;", StudentRowMapper())
    }

    fun findAllByName(num: Int): List<Student> {
        return jdbcTemplate.query("SELECT * FROM student WHERE name = 'Student_$num';", StudentRowMapper())
    }
}

class StudentRowMapper : RowMapper<Student> {

    override fun mapRow(rs: ResultSet, rowNum: Int): Student {
        return Student(
            rs.getLong("id"),
            rs.getString("name")
        )
    }
}