# Todo App (Spring Boot + MyBatis + Thymeleaf + Spring Security + MySQL)

초보자용 학습 프로젝트입니다. 회원가입/로그인 후 본인의 할일(Todo)을 카테고리별로 관리하는
가장 기본적인 CRUD 웹 애플리케이션입니다.

## 학습 포인트

- **Spring Security**: 폼 로그인, `UserDetailsService`/`UserDetails` 커스텀 구현,
  로그인한 사용자 = 본인 데이터만 조회/수정/삭제하는 소유권 체크 패턴
- **MyBatis**: `resultMap`의 `<association>`을 이용한 조인 매핑
  (`todo` LEFT JOIN `category` → `Todo.category` 객체로 자동 매핑)
- **Thymeleaf**: 폼 바인딩(`th:object`, `th:field`), 검증 에러 표시, `sec:authorize`를 이용한
  로그인 상태별 화면 분기
- **MySQL**: 실제 운영 DB에 연동 (H2가 아닌 MySQL 직접 사용)

## 프로젝트 구조

```
src/main/java/com/example/todoapp/
 ├─ config/        SecurityConfig, MemberDetails (UserDetails 구현체)
 ├─ domain/        Member, Category, Todo
 ├─ dto/           SignupForm, TodoForm, CategoryForm
 ├─ mapper/        MyBatis 매퍼 인터페이스
 ├─ service/        비즈니스 로직 + 소유권 검증
 └─ controller/    화면 컨트롤러 (REST가 아닌 서버사이드 렌더링)

src/main/resources/
 ├─ mapper/*.xml    MyBatis SQL (조인 매핑은 TodoMapper.xml 참고)
 ├─ templates/      Thymeleaf 화면
 └─ static/css/     스타일시트

schema.sql          MySQL 테이블 생성 스크립트 (공용 카테고리 3개 시드 포함)
```

## 실행 방법

### 1. MySQL 데이터베이스 준비

```sql
CREATE DATABASE todo_app CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

그 다음 `schema.sql` 내용을 `todo_app` 데이터베이스에 실행하세요.
(테이블 3개 생성 + 공용 카테고리 "업무/개인/공부" 시드 데이터가 들어갑니다.)

```bash
mysql -u root -p todo_app < schema.sql
```

### 2. 접속 정보 설정

`src/main/resources/application-local.yml.example` 파일을 복사해서
같은 폴더에 `application-local.yml` 로 이름을 바꾸고, 본인의 MySQL 아이디/비밀번호를 입력하세요.

```bash
cp src/main/resources/application-local.yml.example src/main/resources/application-local.yml
```

`application-local.yml`은 `.gitignore`에 등록되어 있어 git에는 올라가지 않습니다.

### 3. 실행

브라우저에서 http://localhost:8080 접속 → 자동으로 `/todos`(로그인 필요)로 이동합니다.

### 4. 사용 흐름

1. `/signup`에서 회원가입
2. 로그인
3. `/categories`에서 나만의 카테고리 추가 가능 (기본 제공되는 "업무/개인/공부"는 공용 카테고리라 삭제 불가)
4. `/todos`에서 할일 등록 → 카테고리 선택 → 목록에서 카테고리 필터링, 완료 체크, 수정/삭제

## MyBatis 조인 매핑 포인트 (TodoMapper.xml)

```xml
<resultMap id="todoResultMap" type="com.example.todoapp.domain.Todo">
    ...
    <association property="category" javaType="com.example.todoapp.domain.Category">
        <id property="id" column="category_id"/>
        <result property="name" column="category_name"/>
    </association>
</resultMap>

<select id="findAllByMember" resultMap="todoResultMap">
    SELECT t.*, c.name AS category_name
    FROM todo t
    LEFT JOIN category c ON t.category_id = c.id
    WHERE t.member_id = #{memberId}
    ...
</select>
```

`todo` 테이블만 조회하면 카테고리 이름을 알 수 없기 때문에, `category` 테이블을 LEFT JOIN 해서
가져온 `category_name` 컬럼을 `<association>`으로 `Todo.category.name`에 매핑합니다.
목록 화면(`todo/list.html`)에서 `todo.category.name`으로 바로 꺼내 쓸 수 있는 이유가 이것입니다.

## 다음에 도전해볼 만한 것 (선택)

- 카테고리별 할일 개수를 GROUP BY로 집계해서 카테고리 목록 옆에 표시하기
- 페이징 처리 (할일이 많아질 경우)
- 마감일 지난 할일 강조 표시
- 회원 탈퇴, 비밀번호 변경 기능
