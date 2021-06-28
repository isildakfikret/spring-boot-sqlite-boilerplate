CREATE TABLE IF NOT EXISTS Members
(
  id        TEXT NOT NULL,
  firstName TEXT NOT NULL,
  lastName  TEXT NOT NULL,
  age       INTEGER,
  CONSTRAINT MembersPK PRIMARY KEY (id) ON CONFLICT IGNORE
);
