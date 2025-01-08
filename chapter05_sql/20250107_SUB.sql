-- UNION
-- 1. 컬럼 목록이 같은 데이터를 위아래로 결합
-- 데이터를 위 아래로 수직 결합해주는 기능 -> 컬럼의 형식과 개수가 같은 두 데이터 집합을 하나로 결합

-- 	JOIN 의 경우에는 여러 가지 조건을 미리 충족시켜줘야만함 -> ON

(SELECT * FROM users) UNION (SELECT * FROM users);

-- 이상의 쿼리의 문제점은 결합 기능을 지닌 UNION 의 결과값이 select * from users; 와 동일하다는 점임
-- 해당 이유는 UNION은 결합하는 두 결과 집합에 대한 '중복 제거 기능' 이 포함되어 있기 때문
-- 중복을 제거하지 않고 출력하는 명령어 : UNION ALL

(SELECT * FROM users) UNION ALL (SELECT * FROM users)
ORDER BY id;

-- UNION 은 중복 제거 가능 / UNION ALL 은 중복 포함 출력
-- 참고 UNION ALL 사용 시 SELECT 에서 컬럼 선별 예시
(SELECT id, phone, city, country	-- 일부 컬럼만 지정해서 출력
	FROM users)
UNION ALL
(SELECT id, phone, city, country	-- 기준이 되는 첫 번쨰 selcet 절에서 선택하는
	FROM users)						-- 컬럼의 종류 및 개수가 완벽히 일치해야함.
ORDER BY id;

-- 실무에서는 UNION ALL 이 더 권장됨. UNION 의 경우 중복 제거가 있는데, 대량의 데이터를 대상으로 중복 제거 할 때
-- 컴퓨터에 무리한 연산 부하를 줄 가능성이 있기 때문에
-- 일단 UNION ALL 을 통해서 최종 결과 형태를 확인함 -> UNION 을 적용하는 식으로 프로세스가 짜여있는 편

-- users 에서 country 가 Korea 인 회원 정보만 추출하고(1번 추출) Mexico인 회원 정보만 추출해서(2번 추출),
-- Mexico 인 회원 정보만 추출해서 (2번 추출) 결합해보는 테이블 출력
-- (단, 컬럼은 id, phone, city, country 만 출력하되, 최종 집합은 country 기준 알파벳순)

(SELECT id, phone, city, country
	FROM users
		WHERE country = "Korea")
UNION ALL
(SELECT id, phone, city, country
	FROM users
	WHERE country = "Mexico")
ORDER BY country
;

-- 문제
-- 1. orders에서 order_date 가 2015년 10월 건과 2015년 12월인 건을 select 를 각각 추출하고
-- 두 결과 집합을 UNION ALL 을 사용해 하나로 결합하시오 (단, 최종 결과는 최신순으로 정렬)

-- 정답 1
(SELECT *
	FROM orders
	WHERE substr(order_date,1,7) = "2015-10")
UNION ALL
(SELECT *
	FROM orders
	WHERE substr(order_date,1,7) = "2015-12")
ORDER BY order_date DESC;

-- 정답 2
(SELECT * FROM orders 
	WHERE order_date >= "2015-10-01" AND order_date < "2015-11-01")
UNION ALL
(SELECT * FROM orders 
	WHERE order_date >= "2015-12-01" AND order_date < "2016-01-01")
ORDER BY order_date DESC;



-- SQL 상에서의 문자열 비교 방식
-- 문자열을 왼쪽에서 오른쪽으로 한 문자씩 비교함
-- ASCII / 유니코드 값을 기준으로 비교함
-- 왼쪽부터 읽어오다가 다른 문자가 발견되는 그 즉시 그 값에 따라 크고 작음을 판별함

-- ex) "2015-10-01" vs "2015-11-01"
--	"2" == "2" / "0" == "0" / "1" == "1" / "5" == "5" / ... / (10월 까지 동일하게 크기 비교)
-- 그 다음 순간에 "0" == "1" 이 다른 시점에 들어갔을 때 크기 비교가 이루어짐

-- YYYY-MM-DD 형식으로 저장돼있다면 문자열 비교 결과와 실제 날짜 비교 결과가 동일하게 적용됨
-- 따라서 MM-DD-YYYY 형태로 저장돼있더라면 오류가 발생할 가능성이 높음




-- 2. users 에서 USA 에 거주 중이면서 마케팅 수신에 동의(1)한 회원 정보와 France 에 거주 중이면서 마케팅 수신에 동의하지 않은(0)
-- 회원 정보를 SELECT 로 각각 추출하고, 두 결과 집합을 UNION ALL 을 사용해 하나로 결합
-- (단, 최종 결과는 id, phone, city, country, is_marketing_agree 컬럼 추출 후 거주 국가 기준 알파벳 역순 출력)
(SELECT id, phone, city, country, is_marketing_agree
	FROM users
	WHERE is_marketing_agree = 1
	AND country = "USA")
UNION ALL
(SELECT id, phone, city, country, is_marketing_agree
	FROM users
	WHERE is_marketing_agree = 0
	AND country = "France")
ORDER BY country DESC;

-- 3. 같이 해결할거
-- 	UNION 을 활용하여 orderdetails 와 products 를 FULL OUTER JOIN 조건으로 결합하여 출력
--	(시험이나 실무에서는 거의 적용안함)
(SELECT * FROM orderdetails o LEFT JOIN products p ON o.product_id = p.id)
UNION
(SELECT * FROM orderdetails o RIGHT JOIN products p ON o.product_id = p.id)
;

-- 서브쿼리
-- SQL 쿼리 결과를 테이블처럼 사용하는 쿼리 속의 쿼리를 뜻함
-- 서브 쿼리는 작성한 쿼리를 소괄호로 감싸서 사용하는데, 실제로 테이블은 아니지만 테이블처럼 사용이 가능함

-- products 에서 name(제품명)과 price(정상가격)을 모두 불러오고 '평균 정상 가격을 새로운 컬럼'으로 각 행마다 출력

SELECT name, price, AVG(price) FROM products;
-- SELECT AVG(price) FROM Products; 를 하는 경우 전체 price / 행의 갯수로 나눈 데이터가 단 하나이므로 한 행만 출력됨

-- 이를 막기 위해 서브쿼리를 적용함

-- products 테이블의 name / price 를 불러오는 것은 기본적인 select 절임
-- 그런데 select 절에서는 단일 값을 반환하는 서브 쿼리가 올 수 있음

-- 스칼라 (scalar) 서브 쿼리 : 쿼리의 결과가 단일 값을 반환하는 서브 쿼리

SELECT name, price, (SELECT AVG(price) FROM products) FROM products;

SELECT name, price, 38.5455 AS avgPrc FROM products;

-- 특정한 단일 결과값을 각 행에 적용을 하고 싶다면 이상과 같은 하드코딩이 가능함
-- 하지만 정확 값을 얻기 위해서 사전에 쿼리문으로 SELECT AVG(price) FROM products; 가 요구된다는 점에서 효율적이지는 않고,
-- 실무 상황에서 실제 쿼리문을 실행시킨 이후에 확인해야 해서 서브쿼리를 작성하는 편이 권장됨

-- 스칼라 서브 쿼리를 작성할 때 '단일 값'이 반환되도록 작성해야 한다는 점에 유의하시오
-- 만약 2개 이상의 집계 값을 기존 테이블에 추가하여 출력하고 싶다면 스칼라 서브 쿼리를 따로 나누어서 작성해야만함

-- users 에서 city 별 회원 수를 카운트하고 회원 수가 3명 이상인 도시 명과 회원 수 출력 (단, 회원 수를 기준으로 내림차순 정리)

SELECT city, COUNT(DISTINCT id) FROM users GROUP BY city;		-- > 도시 별로 id 개수를 계산
	 															-- 
-- 1번 정답
SELECT city, COUNT(DISTINCT id) 
FROM users 
GROUP BY city
HAVING COUNT(DISTINCT id) >= 3
ORDER BY COUNT(DISTINCT id) DESC;



SELECT *
	FROM
	(
		SELECT city, COUNT(DISTINCT id) AS cntUser
		FROM users
		GROUP BY city
	) a
	WHERE cntUser >= 3
	ORDER BY cntUser DESC;

-- 1번이 서브쿼리 X
-- 2번이 서브쿼리인데 -> 해당 문제에서 눈여겨 볼 것은 여기서는 서브쿼리를 작성하는 데에 있어서 스칼라 서브 쿼리 형태로 작성하는 것이 중요하다 라는 것
-- 근데 해당 문제에서느 검증 결과 서브 쿼리 자체가 필요하지는 않음...
-- a 이 부분은 비표준 SQL 이기 때문에 일단 적어만 둠

-- orders 와 staff 를 활용해서 last_name 이 Kyle 이나 Scott 인 직원의 담당 주문을 출력
-- (단, 서브쿼리 형태를 활용)

-- 서브쿼리가 없는 형태
SELECT  o.staff_id, o.id, s.id, s.first_name, s.last_name
	FROM orders o INNER JOIN staff s ON o.staff_id = s.id
	WHERE s.last_name IN ("Kyle","Scott")
	ORDER BY s.id
	;



SELECT id
	FROM staff
	WHERE last_name IN ("Kyle","Scott"); 		-- 조건 절에 쓰일 경우에는 scalar 쿼리가 아니었다는 점에 주목		
-- 이상의 코드는 staff 테이블에서 id 값이 3, 5를 도출
-- 이걸 가지고 orders 테이블에 적용하는 형태로 작성

-- 서브쿼리가 있는 형태
SELECT *
	FROM orders
	WHERE staff_id IN
	(
		SELECT id
			FROM staff
			WHERE last_name IN ("Kyle","Scott")
	)
	ORDER BY staff_id
;


-- WHERE 절 내에서 필터링 조건 지정을 위해 중첩된 서브 쿼리를 작성 가능
-- WHERE 에서 IN 연산자와 함께 서브 쿼리를 활용 할 경우 :
--	컬럼 개수와 필터링 적용 대상 컬럼의 개수가 '일치' 해야만함

-- 이상의 코드에서 서브쿼리의 결과 도출되는 컬럼의 숫자 = 1 (staff 테이블의 id) / 행 = 2			


SELECT *
	FROM orders
	WHERE (staff_id, user_id) IN (		-- 필터링 대상 컬럼 개수 = 2
		SELECT id, user_id				-- 서브쿼리 컬럼 개수 = 2
			FROM staff
			WHERE last_name IN ("Kyle","Scott")
	)
	ORDER BY staff_id
;

-- 결과값으로 직원 정보 테이블에 존재하는 id, user_id 와 동일한 값이 orders 테이블의 staff_id, user_id 컬럼에 있을 경우 반환하여 출력됨
-- 이상의 쿼리문의 해석 -> 직원 자신이 자기 쇼핑몰에서 주문한 이력이 반환된 것

-- products 에서 discount_price 가 가장 비싼 제품 정보 출력
-- (단, products 의 전체 컬럼이 다 출력되어야함.)


-- 가장 비싼 제품 출력 쿼리
SELECT MAX(discount_price) FROM products;


-- 전체 쿼리
SELECT *
	FROM products
	WHERE discount_price IN (
		SELECT MAX(discount_price) 
		FROM products
	);



-- orders 에서 주문 월 (order_date 컬럼 활용) 이 2015년 7월인 주문 정보를 주문 상세 정보 테이블 orderdetails 에서
-- quantity 가 50 이상인 정보를 각각 서브 쿼리로 작성 후 INNER JOIN 하여 출력

-- 1. orders 주문 월이 2015년 7월인 주문 정보 쿼리
SELECT * 
	FROM orders 
	WHERE substr(order_date,1,7) = "2015-07";

-- 2. quantity 가 50 이상인 정보 쿼리
SELECT *
	FROM orderdetails
		WHERE quantity >= 50;

-- 1. INNER JOIN 2.
SELECT *
	FROM (
		SELECT * 
			FROM orders
			WHERE substr(order_date,1,7) = "2015-07" ) o -- 1 의 결과가 테이블이었기 때문에 별칭 o 를 끝에 표기
	INNER JOIN
	(SELECT *
		FROM orderdetails
		WHERE quantity >= 50) od		-- 2 역시 테이블이므로 1의 방식처럼 JOIN 시의 별칭 (od)을 따로 표기
	ON o.id = od.order_id
;

-- 서브 쿼리를 작성하기 위한 방안 중 하나는 서브 쿼리에 들어가게 될 쿼리문을 작성한 결과 값을 확인해야만함
-- 이후 해당 쿼리가 scalar냐 아니냐에 따라서 그 위치 역시 통제 가능
-- ex) scalar 인 경우에는 select 절에 들어가는 것 처럼
-- 이상의 경우에는 결과값이 테이블 형태로 나왔기 때문에 이를 기준으로 INNER JOIN 함
		
-- 서브 쿼리 정리하기
-- 쿼리 결과값을 메인 쿼리에서 값이나 조건으로 사용하고 싶을 때 사용

-- SELECT / FROM / WHERE 등 사용 위치에 따라 불리는 이름이 다름

-- 정리 1. SELECT 절에서의 사용
-- 형태
-- SELECT ..., ([서브쿼리]) AS [컬럼명]
-- ... 이하 생략

-- SELECT 에서는 '단일 집계 값'을 신규 컬럼으로 추가하기 우히ㅐ 서브 쿼리를 사용
-- 여러 개의 컬럼을 추가하고 싶을 때는 서브 쿼리를 여러 개 작성하면됨
-- 특징 : SELECT 의 서브 쿼리는 메인 쿼리의 FROM 에서 사용된 테이블이 아닌 테이블에서도 사용이 가능하기 때문에 불필요한 JOIN 수행을 줄일 수 있다는 장점이 있음


-- 정리 2. FROM 에서의 사용
-- 형태 :
-- SELECT ...
-- 	FROM ([서브쿼리]) a
-- ...

-- FROM 에서 사용되는 서브 쿼리 : 인라인 뷰, 마치 테이블처럼 서브 쿼리의 결과값을 사용 가능
-- 또한 FROM 에서 2 개 이상의 서브 쿼리를 활용하여 JOIN 연산 가능
-- 이때 JOIN 연산을 위해 별칭 생성이 가능한데 서브 쿼리가 끝나는 괄호 뒤에 공백을 한 칸 주고 원하는 별칭을 쓰면 됨 (orders o / orderdetails od 와 같은 방식) 


-- 특징
-- 	FROM 에서 서브 쿼리를 적절히 활용하면 적은 연산으로 같은 결과를 도출 가능함. 단, RDBS 기준 테이블 검색을 빠르게 할 수 있는 index(설명 참조) 개념이 있는데
-- 	서브 쿼리를 활용하면 인덱스를 사용하지 못하는 경우가 있으므로 주의해야함

-- index : 테이블의 검색 속도를 높이는 기능으로, 컬럼 값을 정렬하여 검색 시 더욱 빠르게 찾아내도록 하는 자료 구조


-- 정리 3. WHERE 에서의 사용
-- 형태 :
-- ...
-- WHERE [컬럼명][조건 연산자]([서브 쿼리])
-- ...

-- WHERE 에서 필터링을 위한 조건 값을 설ㅈ어하는데 서브 쿼리 사용 가능
-- 위의 예시에서는 IN 연산장를 사용했지만 다른 비교 연산자 또한 사용 가능함

-- 특징 : IN 연산자의 경우에는 다중 컬럼 비교를 할 때는 서브 쿼리에서 추출하는 컬럼의 개수와 WHERE 에 작성하는 필터링 대상 컬럼 개수가 일치해야 함.
--			-> 이 때 필터링 대상 컬럼이 2개 이상이면 () 로 묶어서 작성해야만함.

-- 1. 데이터 그룹화하기 (GROUP BY + 집계함수)
-- 2. 데이터 결과 집합 결합하기 (JOIN + 서브쿼리)
-- 3. 테이블 결합 후 그룹화하기 (JOIN + GROUP BY)
-- 4. 서브쿼리로 필터링 (WHERE절 + 서브쿼리)
-- 5. 같은 행동 반복 대상 추출 (LEFT JOIN)


-- 1. users 에서 created_at 컬럼을 활용하여 연도 별 가입 회원 수를 추출
SELECT substr(created_at,1,4), COUNT(DISTINCT id) AS signIn
	FROM users
	WHERE created_at IS NOT NULL
	GROUP BY substr(created_at,1,4)
;

-- 2. users 에서 country, city, is_auth 컬럼을 활용, 국가별, 도시별, 본인 인증한 회원 수를 추출

-- SUM 활용
SELECT country, city, SUM(is_auth) AS userAuthentication		-- is_auth 가 1이면 본인인증했다는 뜻이기 때문에 is_auth 들의 합이 (강사님 git 보셈 다 못적음)
	FROM users
	WHERE is_auth = 1
	GROUP BY country , city
	ORDER BY SUM(is_auth) DESC
	;


-- COUNT
SELECT country, city, COUNT(DISTINCT id) AS userAuthentication
	FROM users
	WHERE is_auth = 1
	GROUP BY country, city
	ORDER BY COUNT(DISTINCT id) DESC
	;
