insert into profile values (1, true, true, true, false,'pera', true);
insert into profile values (2, true, true, true, false,'mika', true);
insert into profile values (3, false, false, false, false,'zika', false);
insert into profile values (4, true, true, true, false,'pera3', false);

insert into follow values(1	,false	,false	,false,	3	,2);

-- alter sequence profile_id_seq restart with 6;
alter sequence follow_id_seq restart with 2;