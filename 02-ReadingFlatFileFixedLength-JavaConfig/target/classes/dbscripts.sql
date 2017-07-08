drop table if exists product ;

CREATE TABLE `product` (                                 
           `id` varchar(50) NOT NULL,    
           `name` varchar(50) DEFAULT NULL,                       
           `description` varchar(50) DEFAULT NULL,                    
           `price` bigint(50) DEFAULT NULL,                                     
           PRIMARY KEY (`id`)                          
         );

