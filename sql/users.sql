delete from markers where userId <> 999999 and userId <> 999998;
delete from users where id <> 999999 and id <> 999998;

select * from users;

select * from passwords;

delete from passwords where id = 9120;

alter table users drop column password;

commit;
--      entityUser.setPassword( HashUtil.getHash( entityUser.getPassword() ) );
--      entity.setPassword( HashUtil.getHash( entity.getPassword() ) );

insert into users values(999999, 'ro', 'ale@ale.com', 'ale', 'ale', 'w!0ew46erw_w*27~~ee4~*9w~es0x*2xss*r6e26~ee94*79es**we*2962xee~s');

insert into users values(999998, 'ro', 'tibi@tibi.com', 'tibi', 'tibi', '*e!769!~xews7204w67*9*0079rss4e602w2ew7!s~rxex!e2ew9*2xwee!e6_97');

insert into passwords values (999999, '*e972e9_76_4!0r*7*!s2~0ssx!~6x00~~0_rx6e0ww7ex4w!2we0werr_6!x6~_', 999999);
insert into passwords values (999998, '*e972e9_76_4!0r*7*!s2~0ssx!~6x00~~0_rx6e0ww7ex4w!2we0werr_6!x6~_', 999998);


--update users set password = '*e!769!~xews7204w67*9*0079rss4e602w2ew7!s~rxex!e2ew9*2xwee!e6_97';

update passwords set password = '*e972e9_76_4!0r*7*!s2~0ssx!~6x00~~0_rx6e0ww7ex4w!2we0werr_6!x6~_'; --x

update users set dropboxtoken = 'w!0ew46erw_w*27~~ee4~*9w~es0x*2xss*r6e26~ee94*79es**we*2962xee~s'; --a

update users set foursquaretoken = null; 

update users set country = 'w!0ew46erw_w*27~~ee4~*9w~es0x*2xss*r6e26~ee94*79es**we*2962xee~s' where id = 5590;

update users set country = 'Romania' where id = 5590;
  
update users set email = 't', password= where id = 999998;
 
update users set email = 'a' where id = 999999;

update users set username = email;

drop table users;

SELECT ID,DATE,LATITUDE,LONGITUDE,NAME,NOTE,USERID FROM "PUBLIC"."MARKERS1";

 __9s7e49se!02x922eew69*6e2!0!!w06r6e0_9442r_!6!~2r6*rx72~0092w0
