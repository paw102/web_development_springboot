-- users 에서 country 별 회원 수 구하기
SELECT country, COUNT(DISTINCT id) AS uniqueUserCnt
	FROM users
	GROUP BY country
	;
-- 뒤에 나오는 컬럼명을 기준으로 그룹화해서
-- country 를 표시하고 COUNT() 적용한 컬럼도 표시해서 조회한다는 뜻

SELECT * FROM users;

-- users 에서 country 가 Korea 인 회원 중에서 마케팅 수신에 동의한 회원 수를 구하여 출력
-- 표시 컬럼은 country, uniqueUserCnt
SELECT country, COUNT(DISTINCT id) AS uniqueUserCnt
	FROM users
	WHERE country ="Korea" AND is_marketing_agree = 1
;
-- users 에서 country 별로 마케팅 수신 동의한 회원 수와 동의하지 않은 회원 수를 구해 출력
-- 표시 컬럼은 country, uniqueUserCnt (GROUP BY 사용)
SELECT country, is_marketing_agree, COUNT(DISTINCT id) AS uniqueUserCnt
	FROM users
	GROUP BY country, is_marketing_agree
	ORDER BY country, uniqueUserCnt DESC
	;
-- 국가별로는 오름차순(default 라서 미표기), uniqueUserCnt 를 기준으로는 내림차순
-- SELECT 절 - FROM 테이블 명 - GROUP BY 절 - ORDER BY 절 : 순서

-- GROUP BY 에 두 개 이상의 기준 컬럼을 추가하면 데이터가 여러 그룹으로 나뉨.
-- 아르헨티나를 기준으로 했을 때 마케팅 수신 동의 여부가 0인 회원 수와 마케팅 수신 여부가 1인 회원 수를 기준으로 나누어져 있음을 알 수 있음

-- 예를 들어 위의 쿼리문의 경우, country 를 기준으로 먼저 그룹화가 이루어지고 그 후에 is_marketing_agree 기준으로 그룹화됨

-- 그래서 GROUP BY 에 여러 기준을 설정하면 컬럼 순서에 따라 결과가 달라짐.
-- 따라서 '중요한 기준을 앞에 배치'해서 시각화와 그룹화에 대한우선순위를 결정할 필요 있음

-- GROUP BY 정리
-- 1) 주어진 컬럼을 기준으로 데이터를 그룹화하여 '집계함수'와 함께 사용
-- 2) GROUP BY 뒤에는 그룹화할 컬럼명을 입력, 그룹화 한 컬럼에 집계 함수를 적용하여 그룹 별 계산을 수행
-- 3) 형식 : GROUP BY 컬럼명1 , 컬럼명2, ...
-- 4) GROUP BY 에서 두 개 이상의 기준 컬럼을 조건으로 추가하여 여러 그룹으로 분할 가능한데, 컬럼 순서에 따라 결과에 영향을 미치므로 우선순위를 정할 필요 있음

-- users 에서 국가 내 도시 별 회원 수를 구하여 출력
-- 단, 국가명은 알파벳 순서대로 정렬, 같은 국가 내에서는 회원 수 기준으로 내림차순 정렬
-- 표시 컬럼 : country, city, uniqueUserCnt (WHERE X)
SELECT country, city, COUNT(DISTINCT id) AS uniqueUserCnt 
	FROM users
	GROUP BY country , city 
	ORDER BY country ASC, uniqueUserCnt DESC
	;

-- SUBSTR(컬럼명, 시작값, 글자갯수)
-- users 에서 월별 (e.g. 2013-10) 가입 회원 수 출력
-- 가입 일시 컬럼 활용, 최신순으로 정렬
SELECT SUBSTR(created_at, 1, 7) AS mon, count(DISTINCT id) AS uniqueUserCnt
	FROM users
	GROUP BY SUBSTR(created_at, 1, 7)		-- 점검 후 다시 사용(mon)
	ORDER BY mon DESC
	;


-- 1. orderdetails 에서 order_id 별 주문 수량 quantity 의 총 합 출력
	-- 주문 수량의 총합이 내림차순으로 정렬
SELECT order_id, SUM(quantity)
	FROM orderdetails
	GROUP BY order_id 
	ORDER BY SUM(quantity) DESC
;
-- 2. orders 에서 staff_id 별, user_id 별로 주문건수(COUNT(*)) 를 출력 할 것
	-- 단, staff_id 기준 오름차순, 주문 건수별 기준 내림차순
SELECT staff_id, user_id, COUNT(*)
	FROM orders
	GROUP BY staff_id, user_id 
	ORDER BY staff_id ASC, COUNT(*) DESC
	;
	
-- 3. orders 에서 월 별로 주문한 회원 수를 출력 (order_date 컬럼 이용, 최신순으로 정렬)
SELECT SUBSTR(order_date, 1, 7), COUNT(DISTINCT user_id)
	FROM orders
	GROUP BY order_date 
	ORDER BY order_date DESC
	;

-- HAVING
-- GROUP BY 를 이용해 데이터를 그룹화하고 해당 그룹별로 집계 연산 수행하여 국가 별 회원 수를 도출 할 수 있었음 (COUNT())
-- 하지만 예를 들어 회원 수가 n 명 이상인 국가의 회원 수만 보는 등의 조건을 걸어버리면 어떻게 해야 하느냐?

-- WHERE 절을 이용하는 방법이 있지만 추가적인 개념에 대해 짚고 넘어가야함
-- 언제나 WHERE 절을 쓰는 것이 용이하지 않다는 점부터 생각하고 HAVING 을 학습 할 예정

-- users 에서 country 가 Korea, USA, France 인 회원 숫자 도출
SELECT country, COUNT(DISTINCT id)
	FROM users
	WHERE country IN("Korea", "USA", "France")
	GROUP BY country
	;

-- WHERE 를 통해서 원하는 국가만 먼저 필터링하고 GROUP BY 를 함
-- 여기서 필터링 조건은 Country 컬럼의 데이터 값에 해당함

-- 만약에 회원 수가 8명 이상인 국가의 회원 수만 출력하려면?
-- SELECT country, COUNT(DISTINCT id)
-- 	FROM users
-- 	WHERE COUNT(DISTINCT id) >= 8
-- 	; 오류 발생

-- 회원 수가 8명 이상인 국가의 회원 수만 출력
SELECT country, COUNT(DISTINCT id)
	FROM users
	GROUP BY country
	HAVING COUNT(DISTINCT id) >= 8
	ORDER BY 2 DESC
	;

-- 1. WHERE 에서 필터링을 시도할 때 오류가 발생하는 이유 :
	-- WHERE는 GROUP BY 보다 먼저 실행되기 때문에 그룹화를 진행하기 전에 행을 필터링 함
	-- 하지만 집계 함수로 계산된 값의 경우에는 그룹화 이후에 이루어지기 때문에
	-- 순서 상으로 GROUP BY 보다 뒤에 있어야해서 WHERE 절 도입이 불가능함

-- 2. 즉, GROUP BY 를 사용한 집계 값을 필터링 할 때는 -> HAVING 사용



-- orders 담당 직원 별 주문 건수와 회원 수를 계산하고 주문 건수가 10건 이상이면서 주문 회원 수가 40명 이하인 데이터만 출력 (단, 주문 건수 기준으로 내림차순 정렬)
-- (staff_id, users_id, id 컬럼 이용)
SELECT staff_id, COUNT(DISTINCT id), COUNT(user_id)
	FROM orders
	GROUP BY staff_id
	HAVING COUNT(DISTINCT id) >= 10 AND COUNT(user_id) <= 40
	ORDER BY COUNT(DISTINCT id) DESC
	;

-- HAVING 정리
-- 순서 상 GROUP BY 뒤쪽에 위치하며, GROUP BY 와 집계함수로 그룹별로 집계한 값을 필터링 할 때 사용
-- WHERE 과 동일하게 필터링 기능을 수행하지만, 적용 영역의 차이 때문에 주의 할 필요 있음
-- WHERE 은 'FROM 에서 불러온 데이터'을 필터링하는 반면에, 
-- HAVING 은 GROUP BY 가 '실행된 이후의 집계 함수 값' 을 필터링 함
-- 따라서 HAVING 은 GROUP BY 가 SELECT 문 내에 없다면 사용할 수가 없음

-- SELECT 문의 실행 순서
-- SELECT 컬럼명		-5
-- FROM 테이블명 		-1
-- WHERE 조건1		-2
-- GROUP BY 컬럼명	-3
-- HAVING 조건2		-4
-- ORDER BY 컬럼명	-6
-- 
-- 1. 컴퓨터는 가장 먼저 FROM 을 읽어 데이터가 저장된 위치에 접근 후, 테이블의 존재 유무를 따진 후 테이블을 확인 하는 작업을 실행
-- 2. WHERE 를 실행하여 가져올 데이터의 범위 확인
-- 3. DB 에서 가져올 범위가 결정된데이터에 한하여 집게 연산을 적용할 수 있게 그룹으로 데이터를 나눈다.
-- 4. HAVING 은 바로 그 다음 실행되면서 이미 GROUP BY 를 통해 집계 연산 적용이 가능한 상태이기 때문에 4 단계에서 집계 연산을 수행함.
-- 5. 이후 SELECT 를 통해 특정 컬럼, 혹은 집계 함수 적용 컬럼을 조회하여 값을 보여주는데,
-- 6. ORDER BY 를 통해 특정 컬럼 및 연산 결과를 통한 오름차순 및 내림차순으로 나열함.


SELECT country, COUNT(DISTINCT id)
FROM users
WHERE country IN ("USA", "Brazil", "Korea", "Argentina", "Mexico")
GROUP BY country 
;

-- 연습 문제
-- 1. orders 에서 회원 별 주문 건수 추출 (단, 주문 건수가 7건 이상인 회원의 정보만 추출, 주문 건수 기준으로 내림차순 정렬, user_id 와 주문 아이디 (id) 컬럼 활용)
SELECT COUNT(DISTINCT id), user_id
	FROM orders
	GROUP BY user_id 
	HAVING COUNT(DISTINCT id) >= 7
	ORDER BY user_id DESC
	;
-- 2. users 에서 country 별 city 수와 국가별 회원 수 (id) 를 추출 단, 도시 수 5개 이상, 회원 수 3명 이상인 정보 추출 (도시, 회원 수 내림차순 정렬)
SELECT country , COUNT(DISTINCT city), COUNT(DISTINCT id)
	FROM users
	GROUP BY country
	HAVING COUNT(DISTINCT city) >= 5 AND COUNT(DISTINCT id) >= 3 
	ORDER BY city DESC, id DESC
	;

-- 3. users 에서 USA, Brazil, Korea, Argentina, Mexico 에 거주 중인 회원 수를 국가별로 추출 (회원 수 5 명 이상, 회원 수 기준 내림차순, where , having 둘 다 씀)
SELECT country, COUNT(DISTINCT id)
	FROM users
	WHERE country IN ("USA", "Brazil", "Korea", "Argentina", "Mexico")
	GROUP BY country 
	HAVING COUNT(DISTINCT id) >= 5
	ORDER BY id DESC
	;
	

-- SQL 실무 환경에서의 GROUP BY & HAVING
-- 2025-01-03 의 음식 분류 별 (한식, 중식, 분식, ...) 주문 건수 집계
-- SELECT 음식분류, COUNT(DISTINCT 주문아이디), AS 주문건수
-- 	FROM 주문정보
-- 	WHERE 주문시간 (월) = "2025-01"
-- 	GROUP BY 음식분류
-- 	ORDER BY 주문건수 DESC
-- 	;
