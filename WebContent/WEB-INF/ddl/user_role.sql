
CREATE TABLE user_role (
    user_id VARCHAR2(30) NOT NULL,
    role_nm VARCHAR2(30) NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_nm)
);

-- 모든 사용자 에게 "MEMBER" 
INSERT INTO user_role ( user_id , role_nm)
select mem_id , 'MEMBER' from member ; 
-- 'b001' 의 직업과 동일한 사람 'MANAGER'
INSERT INTO user_role ( user_id , role_nm)
select mem_id , 'MANAGER' from member 
 where mem_job = (select mem_job from member where mem_id = 'b001') ; 

-- 'b001' 의 직업과 동일하고 생일이 '1974' 사람 'ADMIN'
INSERT INTO user_role ( user_id , role_nm)
select mem_id , 'ADMIN' from member 
 where mem_job = (select mem_job from member where mem_id = 'b001')
   and to_char(mem_bir,'YYYY') = '1974'; 

commit;

select * from user_role where user_id = 'b001';
-- 여러행의 정보를 하나의 문자열로 처리해주는 함수 
-- 11g 제공 집계함수  listagg 
select count(*) , listagg(role_nm, ',') within group (order by role_nm)
from user_role
where user_id = 'b001';

select user_id, role_nm 
    , listagg(role_nm, ',') within group (order by role_nm) 
      over(partition by user_id)
from user_role;

		SELECT LISTAGG(role_nm, ', ') WITHIN GROUP(ORDER BY role_nm )
		  FROM user_role
		 WHERE user_id = 'b001'



