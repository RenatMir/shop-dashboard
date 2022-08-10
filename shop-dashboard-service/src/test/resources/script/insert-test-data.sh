export PGPASSWORD=postgres && psql -U postgres -d postgres --file=/script/sql/create-tables.sql
export PGPASSWORD=postgres && psql -U postgres -d postgres --file=/script/sql/insert-test-data.sql

