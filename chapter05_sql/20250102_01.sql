SELECT * FROM users;
-- WHERE : 어떤 조건에 합치하는 것을 고를까 (조건문과 유사)
-- 이전까지는 테이블 전체 혹은 특정 컬럼에 관련된 부분들을 조회했지만
-- 현업에서는 데이터의 일부 컬럼을 가져오거나 상위 n의 데이터를 조회하는 것 뿐만 아니라
-- 특정 컬럼의 값이 A인 데이터만 가져오는 등 복잡한 쿼리를 작성 할 일이 있음

-- ex) 회원 정보 테이블 users 에서 거주 국가 (country) 가 한국 (korea) 인 회원만 추출하는 쿼리문
SELECT * 
	FROM users
	WHERE country = "Korea";

-- 문제 : 거주 국가가 한국이 아닌 회원만 추출하는 쿼리문을 작성하시오
SELECT *
	FROM users
	WHERE country != "Korea";

-- != ~가 아닌
-- 거주 국가가 한국이면서 회원 아이디가 10인 회원만 추출
SELECT *
	FROM users
	WHERE country = "Korea" AND id = 10;	-- id가 PK(Primary Key) 이기 때문에 굳이 조건문을 더 추가할 필요없
	
-- WHERE 절에서는 여러 조건을 동시에 적용할 수 있고, 조건에 개수에 따라 제한은 없음. 논리 연산은 AND / OR / BETWEEN 사용
	
-- 회원 정보 테이블 users에서 가입 일시 (created_at)가 2020-12-01 ~ 2011-01-01 까지인 회원 정보를 출력
	
SELECT *
	FROM users
	WHERE created_at BETWEEN "2010-12-10" AND "2011-01-01"
	;
-- WHERE 절을 작성하는데 있어서 컬럼영이 먼저 나오고 =, !=, between 등을 적용
-- SELECT 절 + FROM 테이블 명 + WHERE
-- between : 시작값과 종료값을 '포함'하는 범위 내의 데이터를 조회
--			 시간 값을 조회할 때에는 [컬럼명] BETWEEN [시작날짜] AND [종료날짜]
--			 마찬가지로 시작날짜와 종료날짜를 포함한 모든 것을 출력

-- 가입일시가 2010-12-01 부터 2011-01-01 까지인 회원 정보 출력
-- BETWEEN 을 사용하지 않고 정보 출력

SELECT *
	FROM users
	WHERE created_at >= "2010-12-01" AND created_at <= "2011-01-01"
	;
-- 이상의 쿼리를 작성한 이유 : BETWEEN A AND B 구문이 [초과/미만]이 아니라 [이상/이하] 임을 증명하기 위해서

-- 문제 : users 에서 거주 국가가 "Korea", "USA", "UK" 인 회원 정보를 추출
SELECT *
	FROM users
	WHERE country = "Korea" 
	OR    country = "USA" 
	OR	  country = "UK"
	;

SELECT *
	FROM users
	WHERE country IN ("Korea","USA","UK")
	;
		
-- 문제 : users 에서 거주 국가가 "Korea", "USA", "UK" 가 아닌 회원 정보를 추출
SELECT *
	FROM users
	WHERE country != "Korea" 
	AND   country != "USA" 
	AND	  country != "UK"
	;

SELECT *
	FROM users
	WHERE country NOT IN ("Korea","USA","UK")
	;

-- LIKE : 해당 전치사 뒤의 따옴표 (작은, 큰 상관 없이) 내에서는 와일드카드를 사용 가능
-- SQL 을 해석하는 컴퓨터가 LIKE 코드를 읽는 순간 와일드카드를 감지하는데 SQL 문에서의 와일드카드는 %로
-- 0개 이상의 임의의 문자열을 의미하는 메타문자 (metacharacter) 로 사용됨

-- users 에서 country 의 이름이 S로 시작하는 회원 정보 추출
SELECT *
	FROM users
	WHERE country LIKE "S%"
	;
-- 거주 국가가 S로 시작하는 국가 ((ex) South Korea) 모두를 출력
-- 거주 국가가 S로 끝나는 국가 정보 모두를 출력
SELECT *
	FROM users
	WHERE country LIKE "%S"
	;

-- 거주 국가명에 S가 들어가기만 하면 다 출력하는 쿼리
SELECT *
	FROM users
	WHERE country LIKE "%S%"
	;

-- users에서 country 이름이 S로 시작하지 않는 회원 정보만 추출
SELECT *
	FROM users
	WHERE country NOT LIKE("S%")
	;

-- IS : A IS B -> A는 B다 이라는 뜻이기 때문에
-- A 컬럼의 값이 B 이다 일 때 특히 null 일 때 '=' 대신 사용함
-- users에서 created_at 컬럼의 값이 null 인 결과 출력
SELECT *
	FROM users
	WHERE created_at IS NULL;

SELECT *
	FROM users
	WHERE created_at IS NOT NULL;	-- 참고
	
-- 문제 : 1. users 에서 country 가 Mexico 인 회원 정보 추출,
		-- 단, created_at, phone, city, country 컬럼만 추출할 것
SELECT created_at, phone, city, country
	FROM users
	WHERE country = "Mexico"
	;
		
-- 2. products 에서 id 가 20 이하이고 price 는 30 이상인 제품 정보 출력
	-- (price - discount_price)
	-- 단, 기존 컬럼 전부 출력하고 정상 가격에서 얼마나 할인 되었는 지를 discount_amount 라는 컬럼명으로 추출 할 것.
													  -- '전부 다' 의 기준은 기존 컬럼
SELECT *, (price - discount_price) AS 'discount_amount'
	FROM products
	WHERE price >= 30 AND id <= 20
	;

-- 3. users 에서 country 가 Korea, Canada, Belgium 도 아닌 회원의 정보를 모두 추출 할 것.
	-- 단, OR 연산자를 사용하지 않고 출력할 것
SELECT *
	FROM users
	WHERE country NOT IN ('Korea', 'Canada', 'Belgium');
	
-- 4. products 에서 Name 이 N으로 시작하는 제품 정보를 전부 출력 할 것
	-- 단, id, name, price 컬럼만 출력할 것
SELECT id, name, price
	FROM products
	WHERE name LIKE "N%"
	;
	
-- 5. orders 에서 order_date 가 2015-07-01 부터 2015-10-31 까지가 아닌 정보만 출력 할 것.
SELECT *
	FROM orders
	WHERE order_date NOT BETWEEN '2015-07-01' AND '2015-10-31'
	;
-- 2. SELECT * , (price - discount_price) AS 'discount_amount' -> ',' 찍은 후 새 컬럼 추가 가능
-- 5. NOT BETWEEN 순임.

-- ORDER BY 
-- 현재까지 WHERE 을 이용해서 특정한 조건에 합치하는 데이터들을 조회하는 SQL 문에 대해 학습
-- 이상의 경우 저장 된 순서대로 정렬된 결과만 볼 수 있음. -> id 라는 PK 값에 따라 출력됨.
-- 이번에는 가져온 데이터를 원하는 순서대로 정렬하는 방법에 관한 것임.

-- users 에서 id 를 기준으로 오름차순 정렬
SELECT *
	FROM users
	ORDER BY id ASC; 	-- ASC : Ascending 의 의미로 '오름차순'의 의미 
	
-- users 에서 id 를 기준으로 내림차순 정렬
SELECT *
	FROM users
	ORDER BY id DESC;	-- DESC : Descending 의 의미로 '내림차순'의 의미
	
-- 이상에서 볼 수 있듯이 ORDER BY 는 컬럼을 기준으로 행 데이터를 ASC 혹은 DESC 로 정렬 할 때 사용
	-- 숫자의 경우는 쉽게 알 수 있지만, 문자의 경우는 ASCII 코드 를 기준으로함.
	
-- users 에서 city 를 기준으로 내림차순 하여 전체 정보 출력
SELECT *
	FROM users
	ORDER BY city DESC;

-- users 에서 created_at 을 기준으로 오림차순 하여 전체 정보 출력
SELECT *
	FROM users
	ORDER BY created_at ASC;

-- users 에서 첫 번째 컬럼 기준으로 오름차순 정렬하여 출력
SELECT *
	FROM users
	ORDER BY 1 ASC;

-- 특징 : 컬럼명 대신에 컬럼 순서를 기준으로 하여 정렬이 가능함.
		-- 컬럼명을 몰라도 정렬할 수 있다는 장점이 있지만 주의 할 필요가 있음

-- users 에서 username, phone, city, country, id 컬럼을 순서대로 출력
-- 첫 번째 컬럼 기준으로 오름차순 정렬
SELECT username, phone, city, country, id
	FROM users
	ORDER BY 1 ASC
	;

-- 아까와 같이 ORDER BY 1 ASC 로 했지만 정렬이 id 가 아닌 username 기준으로 ASCII Code 가 적용되어 정렬 방식이 숫자부터 시작하게됨
-- 즉, ORDER BY 는 데이터 추출이 끝난 후를 기점으로 적용이 된다는 점을 명심해야함

-- users 에서 city, id 컬럼만 출력하되, city 기준 내림차순, id 기준 오름차순으로 정렬
SELECT city, id
	FROM users
	ORDER BY city DESC , id ASC
	;
-- 컬럼 별로 다양하게 오름차순 혹은 내림차순 적용이 가능함

-- 정리
-- 1. 데이터를 가져올 때 지정된 컬럼을 기준으로 정렬함
-- 2. 형식 : ORDER BY '컬럼명' ASC / DESC
-- 3. 2개 이상의 정렬 기준을 쉼표(,) 를 기준으로 각각 지정 가능
	  -- 이상의 경우 컬럼이 우선하여 정렬됨.
-- 4. 2개 이상의 정렬 기준을 지정 할 때 각각 지정 가능
	  -- 이상의 경우 각 컬럼 당 명시적으로 ASC 혹은 DESC 를 지정해주어야함.
	  -- 하지만 지정하지 않을 경우 default 로 ASC 적용

-- 문제
-- 1. products 에서 price 가 비싼 제품부터 순서대로 모든 컬럼 출력
SELECT *
	FROM products
	ORDER BY price DESC
	;

-- 2. orders 에서 order_date 기준 최신순으로 모든 컬럼 출력
SELECT *
	FROM orders
	ORDER BY order_date DESC
	;

-- 3. orderdetails 에서 product_id 를 기준 내림차순, 같은 제품 아이디 내에서는 quantity 기준 오름차순으로 모든 컬럼 출력
SELECT *
	FROM orderdetails
	ORDER BY product_id DESC , quantity ASC		-- ASC 는 선택 사항 : default 가 오름차순이라
	;

-- 배운 것을 기준으로 실무에서의 사용
-- select, where, order by 를 사용하여 다양한 데이터를 추출하는데,

-- 1. 배달 서비스 예시
	-- 1) 2023-08-01에 주문한 내역 중 쿠폰 할인이 적용된 내역 추출
SELECT *
	FROM 주문정보
	WHERE 주문일자 = '2023-08-01'
		AND 쿠폰할인금액 > 0
	;

	-- 2) 마포구에서 1인분 배달이 가능한 배달 음식점 추출
SELECT *
	FROM 음식점정보
	WHERE 지역 = "마포구"
		AND 1인분가능여부 = 1;		-- 1 / 0 을 쓸 경우에는 TRUE / FALSE 라는 의미
		
