insert into profile values (1, true, true, true, false,'pera', true);
insert into profile values (2, true, true, true, false,'mika', true);
insert into profile values (3, true, true, true, false,'zika', true);
insert into profile values (4, true, true, true, false,'pera3', true);
insert into profile values (5, true, true, true, false,'koja', true);

insert into follow values(1	,false	,false	,false,	3	,2);

alter sequence profile_id_seq restart with 6;
alter sequence follow_id_seq restart with 2;