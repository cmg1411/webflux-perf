package com.example.webflux

import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.Random

@RestController
class StudentController(
    val studentGetterR2dbc: StudentGetterR2dbc,
) {

    @GetMapping("/all/r2dbc")
    fun r2dbc(): Flux<Student> {
        val int = Random().nextInt(1,100000)
        return studentGetterR2dbc.findAllByName("Student_$int")
    }

    @GetMapping("/all/r2dbc2")
    fun r2dbc2(): Mono<Student> {
        val int = Random().nextInt(1,1000)
//        return studentGetterR2dbc.findAllByName("Student_$int")
        return studentGetterR2dbc.select2("student_$int")
    }
}

@Repository
interface StudentGetterR2dbc : ReactiveCrudRepository<Student, Long> {

    @Query("SELECT sleep(0.1) as id, '1' as name;")
    fun sleep(): Flux<Student>

    @Query("SELECT student.* FROM student ORDER BY RAND() LIMIT 1;")
    fun find123(): Flux<Student>

    @Query("SELECT 1 as id, '1' as name;")
    fun select1(): Flux<Student>

    fun findAllByName(name: String): Flux<Student>

    @Query("SELECT * from student where name = :1 ;")
    fun select2(name: String): Mono<Student>


}


@Table("student")
class Student(
    @Id
    val id: Long,
    val name: String
)

//mvc + jdbc, webflux + r2dbc 두개의 성능(tps) 비교
//
//findAll() 하는 단순 컨트롤러 호출
//데이터 3개 들어있다고 가정 -> mvc : , webflux :
//데이터 10000개 들어있다고 가정 -> mvc : , webflux :
//
//테스트 환경은 로컬 컴퓨터에 docker 띄웠습니다. app cpu 2개, mysql cpu 2개
//r2dbc, jdbc connection pool 100개로 통일
//R2dbc asyncer 드라이버 사용
//부하 : thread 1000 rampup 10 loop 100