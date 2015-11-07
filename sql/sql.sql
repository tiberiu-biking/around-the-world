set schema "PUBLIC";

select * from users;

select * from markers;

delete from markers;

insert into markers(id, userid, note, latitude, longitude, name)
select id, userid, note, latitude, longitude, name from markers1 where id != 390 and id != 10;

delete from markers where id = 4290;

update markers set date = sysdate; 

update markers set userid = 999998 where userid=9998;


select markerenti0_.id as id1_, 
       markerenti0_.date as date1_, 
       markerenti0_.latitude as latitude1_, 
       markerenti0_.longitude as longitude1_, 
       markerenti0_.name as name1_, 
       markerenti0_.note as note1_, 
       markerenti0_.userId as userId1_,
       userentity1_.NAME  
  from MARKERS markerenti0_ 
  cross join USERS userentity1_ 
  where markerenti0_.userId=userentity1_.id 
  --and userentity1_.name='t';