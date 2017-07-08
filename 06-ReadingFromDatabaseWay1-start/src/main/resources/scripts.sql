drop table if exists productOld ;

CREATE TABLE `productOld` (                                 
           `id` varchar(50) NOT NULL,    
           `name` varchar(50) DEFAULT NULL,                       
           `description` varchar(50) DEFAULT NULL,                    
           `price` bigint(50) DEFAULT NULL,                                     
           PRIMARY KEY (`id`)                          
         );

insert into productOld values('PR....210','BlackBerry 8100 Pearl','desc1',1000);
insert into productOld values('PR....211','Sony Ericsson W810i','desc2',2000);
insert into productOld values('PR....212','Samsung MM-A900M Ace','desc3',3000);
insert into productOld values('PR....213','Toshiba M285-E 14','desc4',4000);
insert into productOld values('PR....214','Nokia 2610 Phone','desc5',5000);
insert into productOld values('PR....215','CN Clogs Beach/Garden Clog','desc6',6000);
insert into productOld values('PR....216','AT&T 8525 PDA','desc7',7000);
insert into productOld values('PR....217','Canon Digital Rebel XT 8MP Digital SLR Camera','desc8',8000);
insert into productOld values('PR....218','Sony ','desc9',9000);
insert into productOld values('PR....219','Videocon','desc10',10000);

drop table if exists product ;

CREATE TABLE `product` (                                 
           `id` varchar(50) NOT NULL,    
           `name` varchar(50) DEFAULT NULL,                       
           `description` varchar(50) DEFAULT NULL,                    
           `price` bigint(50) DEFAULT NULL,                                     
           PRIMARY KEY (`id`)                          
         );

