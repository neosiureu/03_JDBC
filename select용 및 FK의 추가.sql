SELECT * FROM TB_MEMBER tm ;

ALTER TABLE TB_TODO
ADD MEMBER_NO NUMBER;

ALTER TABLE TB_TODO
ADD CONSTRAINT FK_TODO_MEMBER
FOREIGN KEY (MEMBER_NO)
REFERENCES TB_MEMBER(MEMBER_NO);