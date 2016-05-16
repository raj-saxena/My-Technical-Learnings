# Get dump without locking
mysqldump -u<user> -p<password> -h<host> --single-transaction <database> <tables...> > <filename>.sql


# Get dump conditionally without locking 
mysqldump -u<user> -p<password> -h<host> --single-transaction <database> <tables...> --opt --where="<condition> order by id desc limit 1000" > <filename>.sql



# Get dump conditionally one row per insert (Still batch)
mysqldump -u<user> -p<password> -h<host> --single-transaction <database> <tables...> | sed 's$VALUES ($VALUES\n($g' | sed 's$),($),\n($g' > <filename>.sql


