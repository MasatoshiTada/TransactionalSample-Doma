# TransactionalSample-Doma

Test program of `@javax.transaction.Transactional`.

Using JDBC, JPA, Doma.

## JDBC Resource

Named `jdbc/sandbox`.

This is XADataSource. Database is PostgreSQL 9.4.

## Create Table

See `<PROJECT_ROOT>/SQL/create_table.sql`.

## How To Build

```bash
$ gradlew clean build
```

## How To Run

Use `Payara Web ML 4.1.1.161`.