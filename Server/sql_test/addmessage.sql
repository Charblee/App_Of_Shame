use shame_app;
insert into texts (chat_id, text) select 1, "test 2" where exists(select 1 from chats where chat_id=1);