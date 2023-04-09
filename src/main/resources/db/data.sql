-- 유저 데이터
INSERT INTO users (id, email, user_image_uri, username)
    VALUES (1, 'donggi@kakao.com', 'donggi.jpg', 'donggi'),
           (2, 'honux@kakao.com', 'honux.jpg', 'honux'),
           (3, 'ron2@kakao.com', 'ron2.jpg', 'willseok'),
           (4, 'kukim@kakao.com', 'kukim.jpg', 'kukim'),
           (5, 'jino@kakao.com', 'jino.jpg', 'jino');

-- 회고 게시글 데이터
INSERT INTO comma (id, created_at, updated_at, content, deleted, title, username, user_id)
    VALUES (1, '2007-12-03 10:30:12', '2007-12-03 10:30:12', '1번째 회고 게시글입니다.', false, '회고', 'donggi', 1),
           (2, '2007-12-03 10:31:12', '2007-12-03 10:31:12', '2번째 회고 게시글입니다.', false, '회고', 'donggi', 1),
           (3, '2007-12-03 10:32:12', '2007-12-03 10:32:12', '3번째 회고 게시글입니다.', false, '회고', 'donggi', 1),
           (4, '2007-12-03 10:33:12', '2007-12-03 10:33:12', '4번째 회고 게시글입니다.', false, '회고', 'donggi', 1),
           (6, '2007-12-03 10:36:12', '2007-12-03 10:36:12', '6번째 회고 게시글입니다.', false, '회고', 'honux', 2),
           (7, '2007-12-03 10:37:12', '2007-12-03 10:37:12', '7번째 회고 게시글입니다.', false, '회고', 'willseok', 3),
           (8, '2007-12-03 10:38:12', '2007-12-03 10:38:12', '8번째 회고 게시글입니다.', false, '회고', 'kukim', 4),
           (9, '2007-12-03 10:39:12', '2007-12-03 10:39:12', '9번째 회고 게시글입니다.', false, '회고', 'jino', 5),
           (10, '2007-12-03 10:40:12', '2007-12-03 10:40:12', '10번째 회고 게시글입니다.', false, '회고', 'honux', 2),
           (11, '2007-12-03 10:41:12', '2007-12-03 10:41:12', '11번째 회고 게시글입니다.', false, '회고', 'willseok', 3);

-- 댓글 데이터
INSERT INTO comment (id, created_at, updated_at, comma_id, content, deleted, user_id, username)
    VALUES (1, '2007-12-03 10:31:12', '2007-12-03 10:31:12', 1, '1번째 댓글입니다.', false, 1, 'donggi');
