# Vapeur
SR03 Project

## SETUP SQLITE DATABASE
Create the database

```bash
sqlite3 vapeur < vapeur.sql
```

Connect to the database

```bash
sqlite3 vapeur
```

In order to use our scripts you have to specify the sqlite path of the database(here vapeur).

```bash
export SQLITE\_PATH="/path/to/db"
```



