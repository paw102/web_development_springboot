-- 1. JOIN
-- 2. UNION
-- 3. subquery
-- 
-- 1-1. SQL 에서 JOIN 은 여러 테이블에서 데이터를 가져와 결합하는 기능임
-- RDBS 에서는 데이터의 중복을 피하고 쉽게 관리하기 위해 데이터를 여러 곳에 나누어 보관함
-- 	ex) 어떤 주문을 담당한 직원을 확인하고 싶은데, 주문 아이디 orders 테이블에 있고, 직원 이름은 staff 테이블에 있다면 어떤 방식으로 쿼리를 입력해야하는 지 작성
-- 		
-- 	이상에서 이루어지는 데이터 분산을 '데이터 정규화 (Data Normalization)' 이라고 하며 DB 에서 중복을 최소화하고 데이터의 일관성을 유지함.
-- 	
-- 	1) JOIN
-- 		지금까지는 테이블 하나의 데이터를 필터링 등을 다루는 연습을 함
-- 		지금부터는 둘 이상의 테이블을 함께 다루면서 해당 키워드를 통해 합쳐서 보는 연습 진행
-- 		
-- 		실습 데이터를 기준으로
-- 		ex) 국가별로 주문 건수를 알아보고 싶다면 -> users 와 orders 가 필요함
-- 			즉, 하나의 테이블만으로는 정확한 정보 출력이 불가능함
-- 		
-- 		일반적으로 JOIN 의 개념은 수학적 집합의 개념과 비슷하게 생각하면 됨.
-- 		두 개의 데이터 집합을 하나로 결합하는 기능을 지님
-- 		
-- 		즉, 기본적으로는 '서로 다른 두 테이블 간의 공통 부분인 키를 활용' 하여 테이블을 합침
		

-- users 와 orders 를 하나로 결합하여 출력 (단 주문 정보가 있는 회원의 정보만 출력)

SELECT *
	FROM users u INNER JOIN orders o ON u.id = o.user_id
	;

-- 이상의 SQL 문에 대한 해석
-- 기존에 from 다음에는 테이블 명 하나만 작성되었지만, 이제는 JOIN 연산을 위한 추가 문법이 적용됨
-- 회원 정보와 주문 정보를 하나로 결합하기 위해 users 와 orders 를 INNER JOIN (추후 설명)으로 묶은 후 '후속 조건' 으로 "주문 정보가 있는 회원의 정보" 만 출력하기 위해
-- u.id = o.user_id 를 사용함

-- users 의 PK 인 id 는 회원의 id 에 해당함
-- orders 의 PK 인 id 는 주문 id에 해당하고 2번째 컬럼의 user_id 는
-- PK 는 아니지만 JOIN 을 수행할 때 users 와 합치는 조건이 됨.


-- 여러 테이블을 하나의 FROM 에서 다룰 때에는 별칭을 사용 가능함 (ALIAS와는 다름 -> 컬럼명을 지정)
-- FROM users u 로 작성했을 때 이후에는 u만 썻을 경우 users 테이블을 의미
-- 그래서 이후에 FROM 절에서 다수의 테이블 명을 기입하게 될 경우에 별칭을 통해서 정리하여 SQL 문을 효율적으로 사용할 수 있게 됨.

-- 이상의 문제에서의 조건은 '주문 정보가 있는 회원의 정보만 출력'하는 것이므로 orders 내에 user_id 가 일부 기준이 되어야함.

-- 이유는 users 내에 있는 id 는 1부터 끝까지 있으니까
-- users 테이블에는 회원 id가 id 컬럼에 기록돼있고, orders 테이블에는 회원 id가 users_id 로 공통된 부분을 지정하는 컬럼이 존재하므로
-- 둘을 연결시킬 수 있는데 이때 사용하는 전치사가 'ON'임.



-- JOIN 적용 후 결과를 보기 좋게 정렬하도록 SQL 문 제어
-- 회원 id 를 기준으로 오름차순 하는 조건
-- ORDER BY 에서도 테이블 별칭으로 정렬할 컬럼을 지정 가능


SELECT *
	FROM users u INNER JOIN orders o ON u.id = o.user_id
	ORDER BY u.id
	;

-- FROM 에서 JOIN 이 정렬된 후에 단일 테이블에 명령을 내리는 것 처럼 쿼리를 작성 가능
-- -> 이미 JOIN 을 통해 하나의 테이블로 구성된 것 처럼 간주되기 때문

-- 복수의 테이블이 하나로 결합되기 위해서는 두 테이블 간에 공통된 부분이 존재해야함
-- RDBS 에서는 이 부분을 키(key) 라고함
-- 키 값은 테이블에 반드시 1개 이상 존재하도록 설계되어 있고 (혹은 내가 설계 해야하고)
-- 테이블에서 개별 행을 유일하게 구분 지음. 따라서 키 값은 컬럼 내에 중복되지 않아여하며
-- 개별 행을 구분해야 하므로 null 값을 가질 수가 없음 (nullable = false 로 Entity 클래스에서 지정함)\

-- cf) 키 값은 테이블 내에서 고유한 값을 가지므로 한 테이블에서 개수를 계산할 때 중복되진 않는다
-- 하지만 여러 테이블을 조인한다면 '키 값도 중복될 수 있다' 예를 들어 회원 아이디가 7인 사람이 세 번 주문 했다면
-- 회원 정보 (users) 와 주문 정보 (orders) 를 결합한 결과에는 u.id = 7인 행에 3개 있을 것이다.
-- 이 경우 '한 번만이라도 주문한 회원 수'를 중복 없이 구하려면 회원 id 를 중복 제거 한 뒤에 회원 수를 count 할 필요가 있음


-- key 의 구분
-- 1. 기본 키 (primary key) : 하나의 테이블에서 가지는 고유한 값
-- 2. 외래 키 (Foreign key) : 다른 테이블에서 가지는 기존 테이블의 값

-- FK 는 다른 테이블의 고유한 키 값인 PK 를 참조함. (orders 에서의 o.id 가 FK에 해당)
-- users 의 u.id 가 PK 에 해당하기 때문에 FK 가 PK 를 참조해서 조건을 합치시켜 JOIN 함.

-- 예를 들어서 PK 값이 A, B, C 만 있다면 PK 의 값도 이 값만 가질 수 있고 또한 중복되지 않는다는 특징을 지니고 있음
-- 하지만 FK 의 경우에는 참조하고 있는 관계에 따라 참조 대상인 PK 값이 여러 번 나타날 수 있음
-- users 테이블에서는 id = 7 인 값이 하나만 존재하지만
-- orders 테이블에서는 users_id 가 PK 가 아니기 때문에 3건 존재함
-- 이를 JOIN 시켰을 때 u.id 로 ORDERD BY 하더라도 합친 테이블 상에서 제 1 컬럼 (즉 PK 컬럼) 에 동일 id 가 여러 번 출력 될 수 있음.




-- JOIN 의 종류
-- 1. INNER JOIN
-- : 두 테이블의 키 값이 일치하는 행의 정보만 가지고 옴 -> 집합 중 '교집합'에 해당
-- users 와 orders 를 하나로 결합하여 출력함 (단, 주문 정보가 있는 회원의 정보만 출력)
-- 이상의 문제에서 INNER JOIN 을 도출 할 수 있는 근거는 '(단, 주문 정보가 있는 회원의 정보만 출력)' 이 부분에 해당함

-- 2. LEFT JOIN
-- users 와 orders 를 하나로 결합해 출력 (단, 주문 정보가 있는 회원의 정보만 출력)

SELECT *
	FROM users u LEFT JOIN orders o ON u.id = o.user_id
	;

-- INNER JOIN 의 경우 두 테이블 간의 키 값 조건이 일치하는 행만 결과 값으로 가져옴
-- LEFT JOIN 은 INNER JOIN 의 결과값이 교집합 뿐만 아니라 JOIN 의 명령문의 왼쪽에 위치한 테이블의 모든 결과를 가져옴

-- 이상의 예시에서는 users 테이블이 왼쪽에 있으므로, users 테이블의 모든 결과값을 가져오는데
-- orders 테이블에 대응하는 값이 없는 경우 nul 값으로 출력됨


-- 즉 두 테이블의 교집합과 교집합에 속하지 않는 왼쪽 차집합을 불러옴
-- 왼쪽 테이블의 값을 전부 불러오기 때문에 LEFT JOIN / LEFT OUTER JOIN 이라고 부름


-- LEFT JOIN 과 INNER JOIN 은 함께 실무에서 자주 사용함. 데이텅를 결합하는 경우는 대부분 한쪽 데이터의 값을 보존해야 할 때가 많은데, 이번 예시 또한 그럼
-- 주문 정보가 없는 회원의 정보까지 출력하려면 LEFT JOIN 을 활용함 (블로그 글을 남긴 user와 한 번도 남기지 않은 user를 구분할 때 설명 들음)


-- '결합 후에' 컬럼 값에 접근 할 때에는 [테이블 별칭].[컬럼명] 으로 내부 컬럼에 접근함
-- 두 테이블에 동일한 컬럼이 있을 때 (대부분의 경우 PK 역할을 하는 idㄴ들은 다 있을테니)
-- 어떤 것을 지정했는 지 명확히 하기 위해서임
-- 이를 활용해서 SELECT 도 * 대신에 표시할 컬럼을 지정 할 수 있는데
-- u.id, u.username, o.order_date 처럼 컬럼이 속한 테이블 별칭을 . 앞에 명시하면됨


-- 예시
SELECT u.id, u.username, u.country, o.id, o.user_id, o.order_date
	FROM users u LEFT JOIN orders o ON u.id = o.user_id		-- SELECT 의 별칭을 여기서 지정
	ORDER BY u.id
	;

-- users 와 orders 를 하나로 결합해 출력 (단, 주문 정보가 없는 회원의 정보만 출력)
SELECT *
	FROM users u LEFT JOIN orders o ON u.id = o.user_id
	WHERE o.id IS NULL;	-- o.id / o.user_id / o.staff_id / o.order_date
	
-- users 와 orders 를 하나로 결합하고, 추가로 orderdetails 에 있는 데이터 출력(단, 주문 정보가 없는 회원의 주문 정보 또한 전부 출력 후, 컬럼 출력)
	-- u.id, u.username, u.phone, o.user_id, o.id, od.order_id, od.product_id -> JOIN 이 두 번 일어남
SELECT u.id, u.username, u.phone, o.user_id, o.id, od.order_id, od.product_id
	FROM users u LEFT JOIN orders o ON u.id = o.user_id -- 첫 번째 JOIN
	LEFT JOIN orderdetails od ON o.id = od.order_id -- 두 번째 JOIN
-- FROM 문 내에서는 JOIN 을 중접해서 횟수 제한 없이 사용 가능함
-- 첫 번째 JOIN의 ON 절 뒤에 두번째 JOIN 절 작성
	
	

-- user 와 orders 를 하나로 결합하여 출력(단, 회원 정보가 없는 주문 정보 또한 출력)
SELECT *
	FROM users u RIGHT JOIN orders o ON u.id = o.user_id;

-- RIGHT JOIN 은 기본적으로 LEFT JOIN 과 기능은 동일함. LEFT JOIN 에서는 왼쪽에 위치한 테이블의 모든 값을 가져옴
-- (즉, JOIN 했을 때를 기준으로 u.id 는 있지만 o.user_id 가 없는 경우도 출력된다는 것을 의미)
-- RIGHT JOIN 의 경우는 오른쪽 테이블의 위치한 값을 전부 가지고 온다는 것을 의미함.
-- 즉 여기서부터의 예제 쿼리의 결과값은 INNER JOIN 과 동일함

-- 이는 두 테이블 간의 '포함 관계' 에서 비롯됨
-- users 테이블에 id 가 없는 회원의 정보는 orders 테이블에 존재 할 수 없음
-- 따라서 orders 테이블의 user_id 컬럼은 모두 users 테이블의 id 컬럼에 있는 값에 해당함
-- 결합으로 봤을 때에는 orders 는 users 테이블에 종속되어있으므로 null 값이 출력 될 수 있음


-- 이상을 이유로 많은 기업에서는 RIGHT JOIN 대신에 LEFT JOIN 을 사용하도록 권장함.



-- users 와 orders 의 '모든 가능한 행의 조합'을 만들어내는 쿼리 작성
SELECT *
	FROM users u CROSS JOIN orders o -- 모든 가능한 행 조합 -> CROSS JOIN 을 유추
	ORDER BY u.id;

-- CROSS JOIN - 두 테이블 간의 집합을 조합해 만들 수 있는 모든 경우의 수를 생성하는 방식으로 카티시안 제곱 (Cartisian Product)를 의미함.
-- u.id 를 기준으로 정렬 (얼마나 많은 조합이 나오는 지 시각적으로 만든거)

-- u.id 와 o.users_id 를 연결하는 등의 조건 없이 두 테이블의 모든 행을 합쳐서 만들 수 있는 모든 경우의 수를 생성한 것임.

-- 즉, 10행의 테이블과 20행의 테이블을 CROSS JOIN 하면 200 행이 됨.
-- 해당 경우 CROSS JOIN 은 모든 경우의 수를 구하므로 ON 조건을 굳이 설정하지는 않음

-- JOIN 명령어는 근본적으로 두 테이블의 행을 서로 조합하는 과정에 해당하는데, ON 조건을 활용하면 전체 경우의 수에서 어떤 행만 가져올 수 있을 지 정할 수 있음
-- 실제 환경에서는 CROSS JOIN 을 제한하는 편 (컴퓨터에 많은 연산만 수행시킬 뿐, 실제 값은 NULL 값만 출력되는게 수두룩함.)(걍 웬만하면 쓰지마셈)

SELECT *
	FROM users;

select *
	FROM orders o;

SELECT *
	FROM orderdetails;

-- 1. users 와 staff 를 참고하여 회원 중 직원인 사람의 회원 id, 이메일, 거주 도시, 거주 국가, 성, 이름을 한 화면에 출력
SELECT u.id, u.username, u.country, u.city ,s.first_name, s.last_name 
	FROM users u INNER JOIN staff s ON u.id = s.user_id;

-- 2. staff 와 orders 를 참고하여 직원 아이디가 3번, 5번인 직원의 담당 주문을 출력 (단, 직원 id, 직원 성, 직원 이름, 주문 아이디, 주문 일자만 출력) -> WHERE 절 사용
SELECT s.id, s.first_name, s.last_name, o.id, o.order_date 
	FROM staff s LEFT JOIN orders o ON s.id = o.staff_id
	WHERE s.id = 3 OR s.id = 5;
-- 	WHERE s.id IN (3,5);

-- 3. users 와 orders 를 참고하여 회원 국가 별 주문 건수를 내림차순으로 출력
SELECT u.country, COUNT(DISTINCT o.id)
	FROM users u INNER JOIN orders o ON u.id = o.user_id
	GROUP BY u.country 		-- 국가별로 확인했기 때문에
	ORDER BY COUNT(DISTINCT o.id) DESC;
	
-- 4. orders 와 orderdetails, products 를 참고하여 회원 아이디별 주문 금액의 총합을 정상 가격과 할인 가격 기준으로 각각 구하시오
-- (단, 정상 각격 주문 금액의 총합 기준으로 내림차순 정리)
SELECT o.user_id, 
	SUM(p.price* od.quantity) AS sumPrice,
	SUM(p.discount_price * od.quantity) AS sumDiscountPrice
	FROM 
		orders o 
	LEFT JOIN 
		orderdetails od 
	ON o.id = od.order_id
	INNER JOIN 
		products p 
	ON od.product_id = p.id
	GROUP BY o.user_id		-- 회원 아이디 별로 정렬하라고 했기 때문
	ORDER BY SUM(p.price* od.quantity) DESC;

-- JOIN 총 정리
-- JOIN : 두 테이블을 하나로 결합할 때 사용(정규화를 통해서 DB 관리를 효율화 했기 때문에 하나로 합치게 될 때 사용하는 명령어)

-- 정리 1. 기본 형식
-- FROM [테이블명1] a(별칭) [INNER/LEFT/RIGHT/CROSS JOIN] [테이블명2] b (별칭)
-- ON [JOIN 조건] -> 선호되는 조건 방식 PK = FK 의 결합

-- JOIN 명령어는 FROM 에서 수행됨 쿼리 진행 상 FROM 이 가장 많이 수행되므로 일단 JOIN 이 수행된 뒤에 다른 명령어들이 수행됨.
-- JOIN 사용 시 결합 할 두 테이블 사이에 원하는 JOIN 명령어를 작성하고 테이블 별칭을 설정함(AS 아님)
-- 또한 두 테이블 사이에 공통 된 컬럼 값이 키 값이 존재해야만 JOIN 으로 결합 할 수 있음
-- 키 값은 여러 개가 있을 수 있으며, 어떤 값을 기준으로 할 지 ON 에서 명시함
-- 다중 키 값을 설정할 때는 ON 에서 각 조건을 AND 로 연결함

-- 정리 2 JOIN 중첩
-- FROM [테이블명1] a (별칭) [INNER/LEFT/RIGHT/CROSS JOIN] [테이블명2] b(별칭)
-- ON [JOIN 조건1]
-- [INNER/LEFT/RIGHT/CROSS JOIN] [테이블명3] c(별칭)
-- ON [JOIN 조건2]


-- FROM 내에서 JOIN 을 여러 번 중첩 사용 가능. 앞의 JOIN 의 ON 절 뒤에 새로운 JOIN 명령어를 작성하면 제 3의 테이블과 결합 가능 -> 순서대로 적용한다는 점이 중요함
-- 횟수 제한은 없지만 테이블 크기에 따라 많은 연산이 필요할 수 있어 사전에 필요한 연산인지 점검 할 필요가 있음

-- 정리 3. INNER JOIN 
-- FROM [테이블명1] a [INNER JOIN] [테이블명2] b
-- ON a.key = b.key

-- INNER JOIN 은ㅇ각 테이블의 키 값이 '일치하는 행만' 가져옴. 집합 생각하면 이해됨
-- 가장 기본적인 JOIN 으로 간혹 INNER 를 생략하고 JOIN만 쓰기도함. (default JOIN 구문) 하지만 실무에서는 웬만하면 가독성을 위해 INNER 붙임

-- 정리 4. OUTER JOIN (LEFT / RIGHT)
-- FROM [테이블명1] a [LEFT/RIGHT/FULL JOIN] [테이블명2] b
-- ON a.key = b.key

-- OUTER JOIN 은 LEFT OUTER JOIN, RIGHT OUTER JOIN, FULL OUTER JOIN을 포함하는 용어
-- OUTER 생략 가능

-- LEFT JOIN : 왼쪽 테이블의 모든 데이터 값을 결과에 포함 (보통 자주씀)
-- RIGHT JOIN : 오른쪽 테이블의 모든 데이터 값을 결과에 포함 (잘 안씀)
-- FULL OUTER JOIN : 왼쪽과 오른쪽에 있는 테이블의 모든 값을 결과에 포함
					-- LEFT + RIGHT 의 결과 값이 중복없이 결합된 형태
					-- DB에 따라서 지원하지 않는 경우도 많음

-- 정리 5. CROSS JOIN
-- FROM [테이블명1] a [CROSS JOIN] [테이블명2] b
-- CROSS JOIN은 두 테이블을 결합했을 때 각 테이블의 행으로 만들 수 있는 모든 조합을 결과값을 있는대로 펼치는 연산 -> 카테시안 연산이라함 (쓸 때 주의하셈)


-- 주의 사항 : FULL OUTER JOIN vs. CROSS JOIN
-- FULL OUTER JOIN 의 경우 ON 조건에 부합할 때만 결과 값을 도출하는 반면에,
-- CROSS JOIN 은 모든 경우의 수를 출력하기 때문에 ON 조건을 명시하는 일이 거의 없음

-- 정리 : 실무에서는 INNER JOIN, LEFT OUTER JOIN 을 기본으로 사용하며, OUTER 또한 명시하는 회사도 있음.