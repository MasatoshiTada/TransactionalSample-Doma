CREATE TABLE test_entity(
  id INTEGER PRIMARY KEY,
  thrown VARCHAR(10),
  rollbackon VARCHAR(10),
  dontrollbackon VARCHAR(10),
  expected VARCHAR(10)
);