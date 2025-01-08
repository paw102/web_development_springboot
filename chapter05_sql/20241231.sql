-- 주석 처리 하는 방법
-- 1. SELECT : 보여달라 / 조회하라
SELECT "Hello, SQL!";
-- select : 보여달라 / Hello, SQL! 이라는 str
SELECT 12+7;	-- sql 문을 통한 연산 가능

-- 결과 창의 첫 행에 각각 find, Insight, with SQL 을 3개의 칸에 걸쳐 순서대로 출력
-- 컬럼명을 순서대로 First, Second, Third 로 출력
SELECT 
	"Find" AS "First",
	"Insight" AS "Second",
	"With SQL" AS "Third";
--	이런 식으로 작성하는 이유는 사람들이 알아보기 쉽게 하기 위해서
-- 	가독성 무시하고 지맘대로 막 쓸 수 있긴함. 대신 보는 사람 입장에서는 거지같음.
--	as : alias - 데이터가 들어가는 부분에 대해 컬럼에 대한 별칭을 붙일 때 사용하는 구문
--	쉼표(,) 역할 : 처리 나열을 위해 사용
--	SQL 특징 : 따옴표 구분이 없음	-> 자기가 직접 가독성을 맞추거나 상사로부터 지시된 사항으로 입력해야함.

--	문제
--	1. SELECT 를 이용하여 28+891 의 결과 표시
-- 	2. SELECT 를 이용하여 19*27 의 결과 표시 단, 컬럼 명은 Result 로 표시
--	3. SELECT 를 이용하여 다음 세 가지의 결과를 각각 다른 컬럼을 결과 창에 표시 -> 37+172 (colName : Plus) , 25*78 (Times) , I Love SQL (Result)

SELECT 28+891;
SELECT 19*27 AS 'Result';
SELECT 
	37+172 AS 'Plus',
	25*78 AS 'Times',
	"I Love SQL" AS 'Result';

--	2. FROM : ~ 로 부터 + 테이블명
--  FROM 은 데이터가 저장된 위치를 나타냄.
SELECT *
	FROM users;
--	users.csv 파일에 있었던 모든 테이블과 컬럼과 값이 출력이 됐음을 확인 가능
--	* : asterisk = all : 와일드카드라는 표시 // java
--  select * from users; : users 테이블에 있는 모든 컬럼의 값을 조회하라

--	문제 : 제품 정보 테이블 products 에 있는 모든 데이터를 출력하시오.

SELECT *
	FROM products;
-- 한줄로 쓰는 것 또한 가능하지만 가독성의 이유로 개행을 해야함.
-- -> 차근차근 연습하기 위해서 한 줄로 쓸때도 있고 나눠서 쓸 때도 있긴함.

-- 또한 SQL 문은 대소문자를 구분하지 않음.
-- LIMIT : 갯수 제한을 거는 명령어 (어떠한 IDE를 쓰느냐에 따라서 TOP 일 때도 있긴함.)
SELECT *
	FROM products 
	LIMIT 3;
-- 이렇게 *를 사용해서 전체 정보를 조회하는 것을 Full Scan 이라고 하는데, 빈도가 높지는 않음..
-- 개수 제한을 걸기 위한 LIMIT 와 특정 컬럼을 조회하는 형태로 수업을 진행함.
-- SELECT 컬럼명1, 컬럼명2 from 테이블명;
-- 제품 정보 테이블인 products 에서 제품 아이디 (id), 제품명 (Name) 컬럼만 출력
-- products 에서 가격(price), 할인가(discount_price) 컬럼을 10개만 출력
SELECT id, name
	FROM products;

SELECT price, discount_price
	FROM products 
	LIMIT 10;
-- SQL 문의 경우에는 순서가 매우 명확하게 정해져있는 편이기 때문에 읽기는 쉽지만 익숙해지기 전까지 직접 작성하는 것이 까다로운 편

-- orderdetails 의 모든 정보 표시
SELECT *
	FROM orderdetails;

-- 회원 정보 테이블 users 에서 상위 7건만 표시
SELECT *
	FROM users
	LIMIT 7;

-- orders에서 id, user_id, order_date 컬럼의 데이터를 모두 표시
SELECT id, user_id, order_date
	FROM orders;
