drop table if exists product ;
drop table if exists skipped_product ;

CREATE TABLE `product` (                                 
           `id` varchar(50) NOT NULL,    
           `name` varchar(50) DEFAULT NULL,                       
           `description` varchar(50) DEFAULT NULL,                    
           `price` bigint(50) DEFAULT NULL,                                     
           PRIMARY KEY (`id`)                          
         );

CREATE TABLE `skipped_product` ( `line` varchar(500) NOT NULL,    
           `line_number` varchar(10) DEFAULT NULL);